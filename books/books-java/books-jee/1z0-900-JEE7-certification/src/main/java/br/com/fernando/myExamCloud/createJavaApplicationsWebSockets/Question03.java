package br.com.fernando.myExamCloud.createJavaApplicationsWebSockets;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptor;
import javax.interceptor.InterceptorBinding;
import javax.websocket.OnMessage;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

public class Question03 {

    // Which one is true?
    //
    //
    // Choice A
    // Websocket implementations part of the Java EE platform are required to support field, method, and constructor injection using the
    // javax.inject.Inject annotation into all websocket endpoint classes, as well as the use of interceptors for these classes.
    //
    // Choice B
    // Websocket implementations part of the Java EE platform are required to support HTTPS and Digital Certificates.
    //
    // Choice C
    // Websocket endpoints running in the Java EE platform may not required to support field, method, and constructor injection using the javax.inject.Inject
    // annotation into all websocket endpoint classes, as well as the use of interceptors for these classes.
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // Choice A is correct.
    //
    // Websocket endpoints running in the Java EE platform must have full dependency injection support as described in the CDI specification Websocket implementations
    // part of the Java EE platform are required to support field, method, and constructor injection using the javax.inject.Inject annotation into all websocket
    // endpoint classes, as well as the use of interceptors for these classes.

    @Inherited
    @InterceptorBinding
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    public @interface Logged {
    }
    
    @Logged
    @Interceptor
    @Priority(Interceptor.Priority.APPLICATION)
    public class LoggedInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;
    }
    
    @Named
    private class Service01 {

    }

    @Named
    private class Service02 {

    }

    @Logged
    @ServerEndpoint("/receive")
    public class ReceiveEndpoint {

	@Inject
	private Service01 service01;

	private final Service02 service02;

	@Inject
	public ReceiveEndpoint(final Service02 service02) {
	    this.service02 = service02;
	}

	@OnMessage
	public void textMessage(Session session, String msg) {
	    System.out.println("Text message: " + msg);
	}

	@OnMessage
	public void binaryMessage(Session session, ByteBuffer msg) {
	    System.out.println("Binary message: " + msg.toString());
	}

	@OnMessage
	public void pongMessage(Session session, PongMessage msg) {
	    System.out.println("Pong message: " + msg.getApplicationData().toString());
	}
    }
}
