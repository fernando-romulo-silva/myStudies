package br.com.fernando.chapter04_restfulWebServices.part01_resource;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.ConnectionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Resource02 {

    static final ArrayList<Order> ORDERS = new ArrayList<>(Arrays.asList( //
            new Order(1, "order01"), //
            new Order(2, "order02"), //
            new Order(3, "order03"), //
            new Order(4, "order04"), //
            new Order(5, "order05"), //
            new Order(6, "order06"), //
            new Order(7, "order07"), //
            new Order(8, "order08"), //
            new Order(9, "order09"), //
            new Order(10, "order10") //
    ));

    @Path("orders")
    @Produces(MediaType.APPLICATION_XML)
    public static class OrderResource {

        @Resource(lookup = "java:comp/DefaultManagedExecutorService")
        private ManagedExecutorService executor;
        // or  ManagedThreadFactory threadFactory = (ManagedThreadFactory) new InitialContext().lookup("java:comp/DefaultManagedThreadFactory");

        // webresources/orders01
        @GET
        public void getAll(@Suspended final AsyncResponse ar) {
            //
            // * The getAll method is marked to produce an asynchronous response.
            // We identify this by injecting a method parameter of the class AsyncResponse using the new
            // annotation @Suspended . The return type of this method is void .

            // You can receive request processing completion events by registering an implementation of CompletionCallback
            ar.register(new MyCompletionCallback());

            // You can receive connection-related life-cycle events by registering an implementation of ConnectionCallback
            ar.register(new MyConnectionCallback());

            // By default, a resource method is required to wait and produce a response before returning to the JAX-RS implementation,
            // which then returns control to the client.
            //
            // JAX-RS 2 allows for an asynchronous endpoint that informs the JAX-RS implementation that a response is not readily available
            // upon return but will be produced at a future time.
            //
            // It achieves this by first suspending the client connection and later resuming when the response is available;
            //
            // * This method returns immediately after forking a new thread, likely using ManagedExecutorService as defined by Concurrency Utilities for Java EE.
            // The client connection is suspended at this time.
            //
            // * The new thread executes the long-running operation and resumes the connection by calling resume when the response is ready.

            executor.submit(new Runnable() {

                @Override
                public void run() {

                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                    }

                    final GenericEntity<List<Order>> entity = new GenericEntity<List<Order>>(ORDERS) {
                    };
                    ar.resume(entity);
                }
            });
        }
    }

    // In this code, the onComplete method is invoked when the request processing is finished,
    // after a response is processed and is sent back to the client, or when an unmapped throwable has been propagated to the hosting I/O container

    static class MyCompletionCallback implements CompletionCallback {

        @Override
        public void onComplete(final Throwable t) {
            System.out.println("onComplete");
        }
    }

    // In this code, the onDisconnect method is invoked in case the container detects that the remote client connection associated with the
    // asynchronous response has been disconnected
    static class MyConnectionCallback implements ConnectionCallback {

        @Override
        public void onDisconnect(AsyncResponse ar) {
            System.out.println("onDisconnect");
        }

    }

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Order implements Serializable {

        private static final long serialVersionUID = 1L;

        private int id;

        private String name;

        Order() {
            super();
        }

        public Order(final int id, final String name) {
            super();
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @ApplicationPath("webresources")
    public static class ApplicationConfig extends Application {

    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Order.class, ApplicationConfig.class, OrderResource.class, MyCompletionCallback.class, MyConnectionCallback.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse responseOrders = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/webresources/orders"));
            System.out.println(EntityUtils.toString(responseOrders.getEntity(), "UTF-8"));

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
