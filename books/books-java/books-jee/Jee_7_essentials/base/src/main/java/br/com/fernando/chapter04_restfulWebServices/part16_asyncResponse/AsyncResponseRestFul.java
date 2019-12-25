package br.com.fernando.chapter04_restfulWebServices.part16_asyncResponse;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.ConnectionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class AsyncResponseRestFul {

    // https://dzone.com/articles/jax-rs-20-asynchronous-server-and-client
    // https://docs.oracle.com/cd/E19776-01/820-4867/ghrst/index.html
    // =====================================================================================================================================================
    // Asynchronous feature of JAVA EE EJB was used.
    // @Asynchronous annotation tells the enterprise bean container to handle this method asynchronously and acts as a worker thread.
    //
    @Stateless
    @Path("/execute01")
    public static class AsynchronousResource01 {

        private static final AtomicBoolean ALREADY_DOING = new AtomicBoolean(false);

        @EJB
        private Service service;

        @GET
        @Asynchronous
        public void asyncRestMethod(@Suspended final AsyncResponse asyncResponse) {

            if (ALREADY_DOING.compareAndSet(false, true)) {

                asyncResponse.resume("Okay, I'll do it.");

                try {

                    final String result = service.longTimeTask();

                    System.out.println(result);

                } catch (final Throwable ex) {
                    System.out.println(ex);
                } finally {
                    ALREADY_DOING.getAndSet(false);
                }

            } else {
                asyncResponse.resume("Wait! I'm already doing it!");
            }
        }
    }

    // =====================================================================================================================================================
    @Path("/execute02")
    public static class AsynchronousResource02 {

        @EJB
        private Service service;

        @Resource(lookup = "java:comp/DefaultManagedThreadFactory")
        private ManagedThreadFactory threadFactory;

        @GET
        public void asyncRestMethod(@Suspended final AsyncResponse asyncResponse) {

            asyncResponse.setTimeoutHandler(new MyTimeoutHnadler());

            asyncResponse.setTimeout(40, TimeUnit.SECONDS);

            final Thread thread = threadFactory.newThread(new Runnable() {

                @Override
                public void run() {
                    String result = service.longTimeTask();
                    asyncResponse.resume(result);
                }

            });

            thread.start();
        }
    }

    // =====================================================================================================================================================
    @Path("/execute03")
    public static class AsynchronousResource03 {

        @EJB
        private Service service;

        @Resource
        private ManagedExecutorService managedExecutorService;

        @GET
        public void asyncRestMethod(@Suspended final AsyncResponse asyncResponse) {

            asyncResponse.register(new MyCompletionCallback(), new MyConnectionCallback());

            managedExecutorService.submit(new Runnable() {

                @Override
                public void run() {
                    String result = service.longTimeTask();
                    asyncResponse.resume(result);
                }

            });
        }
    }

    static final class MyConnectionCallback implements ConnectionCallback {

        @Override
        public void onDisconnect(final AsyncResponse disconnected) {
            // Connection lost or closed by the client!
        }
    }

    static final class MyCompletionCallback implements CompletionCallback {

        @Override
        public void onComplete(final Throwable throwable) {
            if (throwable == null) {
                // Everything is good. Response has been successfully
                // dispatched to client
            } else {
                // An error has occurred during request processing
            }
        }
    }

    static final class MyTimeoutHnadler implements TimeoutHandler {

        @Override
        public void handleTimeout(AsyncResponse asyncResponse) {
            asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("TIME OUT !").build());
        }
    }

    // =====================================================================================================================================================
    @Stateless
    public static class Service {

        public String longTimeTask() {

            try {
                System.out.println("Service is doing it!");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "done";
        }
    }

    // =====================================================================================================================================================
    @ApplicationPath("webresources")
    public static class MyApplication extends Application {
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(//
                    Service.class, MyApplication.class, // 
                    MyConnectionCallback.class, MyCompletionCallback.class, MyTimeoutHnadler.class, //
                    AsynchronousResource01.class, AsynchronousResource02.class, AsynchronousResource03.class //
            );

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // ---------------------------------------------------------------------------------------
            final Client client = ClientBuilder.newClient();

            final String result01 = client //
                    .target("http://localhost:" + HTTP_PORT + "/embeddedJeeContainerTest/webresources/execute01") //
                    .request() //
                    .get(String.class);

            System.out.println(result01);

            // ----------------------------------------------------------------------------------------
            final String result02 = client //
                    .target("http://localhost:" + HTTP_PORT + "/embeddedJeeContainerTest/webresources/execute02") //
                    .request() //
                    .get(String.class);

            System.out.println(result02);//

            // ----------------------------------------------------------------------------------------
            final String result03 = client //
                    .target("http://localhost:" + HTTP_PORT + "/embeddedJeeContainerTest/webresources/execute03") //
                    .request() //
                    .get(String.class);

            System.out.println(result03); // 

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();

        // resource-validation
    }

}
