package com.apress.prospring5.ch18.web;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Scanner;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.netty.http.server.HttpServer;

/**
 * Created by iuliana.cosmina on 8/6/17.
 */
public class Server {
    public static final String HOST = "localhost";

    public static final int PORT = 8080;

    private static Logger logger = LoggerFactory.getLogger(Server.class);

    @Autowired
    SingerHandler singerHandler;

    public RouterFunction<ServerResponse> routingFunction() {

	return route(GET("/test"), serverRequest -> ok() //
		.body(fromValue("works!"))) //
			.andRoute(GET("/singers"), singerHandler.list) //
			.andRoute(GET("/singers/{id}"), singerHandler::show) //
			.andRoute(POST("/singers"), singerHandler.save) //
			.filter((request, next) -> {
			    logger.info("Before handler invocation: " + request.path());
			    return next.handle(request);
			});
    }

    public void startReactorServer() throws InterruptedException {
	RouterFunction<ServerResponse> route = routingFunction();

	var httpHandler = RouterFunctions.toHttpHandler(route);

	var adapter = new ReactorHttpHandlerAdapter(httpHandler);

	var server = HttpServer.create().host(HOST).port(PORT).handle(adapter).bindNow();

	System.out.println("press enter");

	Scanner sc = new Scanner(System.in);
	try (sc) {
	    sc.next();
	    server.disposeNow();
	}
    }

    public void startTomcatServer() throws LifecycleException {
	RouterFunction<?> route = routingFunction();
	HttpHandler httpHandler = toHttpHandler(route);

	Tomcat tomcatServer = new Tomcat();
	tomcatServer.setHostname(HOST);
	tomcatServer.setPort(PORT);
	Context rootContext = tomcatServer.addContext("", System.getProperty("java.io.tmpdir"));
	ServletHttpHandlerAdapter servlet = new ServletHttpHandlerAdapter(httpHandler);
	Tomcat.addServlet(rootContext, "httpHandlerServlet", servlet);
	rootContext.addServletMappingDecoded("/", "httpHandlerServlet");
	tomcatServer.start();
	logger.info("Embedded Tomcat server started...");
    }

}
