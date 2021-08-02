package br.com.fernando.chapter10_concurrencyUtilities.part04_dynamicContextualObjects;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ContextService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class DynamicContextualObjects {

    // Application component container contexts, such as classloading, namespace, and security, can be associated with an object instance via ContextService.
    //
    // Dynamic proxy capabilities found in the java.lang.reflect package are used for the context association.
    //
    // The object becomes a contextual object, and whenever a method on the contextual object is invoked, the method executes with the thread context of
    // the associated application component instance.
    //
    // The JNDI naming context, classloader, and security context is propagated to the proxied object.
    //
    // Proxy methods suspend any transactional context on the thread and allow components to manually control global transaction demarcation boundaries.
    // The UserTransaction instance can be used if a transaction is required within the proxy object.
    //
    // This allows non−Java EE service callbacks, such as JMS MessageListeners, to run in the context of the container instead of the implementation
    // provider’s undefined thread context.
    // This also allows customized Java SE platform ExecutorService to be used.

    // ---------------------------------------------------------------------------------------------------------------------------------
    // A contextual instance of a Runnable executed with Java SE ExecutorService can be created as:
    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("MyRunnable.run");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    public static interface MyWork {

        public void myWork();
    }

    // If the object instance supports multiple interfaces, then you can specify the interfaces for which 
    // the contextual proxy needs to be created as follows:
    public static class MyRunnableWork implements Runnable, MyWork {

        @Override
        public void run() {
            System.out.println("MyRunnableWork.run");
        }

        @Override
        public void myWork() {
            System.out.println("MyRunnablework.myWork");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    public static class TestService {

        // @Resource(name = "DefaultManagedThreadFactory")
        @Resource
        // A default ManagedThreadFactory are obtained via @Resource.
        private ManagedThreadFactory factory;

        protected void execute() throws Exception {
            System.out.println("Creating contextual proxy");

            // You can obtain an instance of ContextService with the JNDI lookup using resource environment references. 
            // A default ContextService is available under the JNDI name java:comp/DefaultContextService.
            final InitialContext ctx = new InitialContext();
            final ContextService contextService = (ContextService) ctx.lookup("java:comp/env/concurrent/myContextService");
            //
            // MyRunnable is the object to be proxied.
            final MyRunnable myRunnable = new MyRunnable();
            //
            // You create contextual object proxy instances with a ContextService instance using the createContextualProxy method.
            //
            // Contextual object proxies will run as an extension of the application component instance that created the proxy 
            // and may interact with Java EE container resources.
            final Runnable proxy = contextService.createContextualProxy(myRunnable, Runnable.class);

            System.out.println("Creating Java SE style ExecutorService");

            // The Java SE−style ExecutorService is obtained with ManagedThreads and submits the contextual proxy.
            final ExecutorService executor = Executors.newFixedThreadPool(10, factory);

            System.out.println("Submitting the task");

            Future<?> f = executor.submit(proxy);

            System.out.println(f.get());

            System.out.println("done");

            System.out.println("Check server.log for output from the task.");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------

    public static class TestMultipleInterfaceService {

        // @Resource(name = "java:comp/DefaultManagedThreadFactory")
        @Resource
        private ManagedThreadFactory factory;

        // You can also inject ContextService into the application using @Resource:
        // @Resource(name = "java:comp/DefaultContextService")
        @Resource
        private ContextService contextService;

        protected void execute() throws Exception {
            System.out.println("Creating contextual proxy (with multiple interfaces)");

            // MyRunnableWork is the object to be proxied and implements the Runnable and MyWork interfaces.
            final MyRunnableWork myRunnableWork = new MyRunnableWork();

            // You create the contextual proxy by passing both the interfaces, and the return type is Object.
            final Object proxy = contextService.createContextualProxy(myRunnableWork, Runnable.class, MyWork.class);

            System.out.println("Calling MyWork interface");

            // You can invoke methods on a non-Runnable interface by casting to the MyWork interface.
            ((MyWork) proxy).myWork();

            System.out.println("Creating Java SE style ExecutorService");

            ExecutorService executor = Executors.newFixedThreadPool(10, factory);

            System.out.println("Submitting the task<br>");

            // You can submit a proxied instance to the ExecutorService by casting to the Runnable interface.
            final Future<?> f = executor.submit((Runnable) proxy);

            System.out.println(f.get());

            System.out.println("done");

            System.out.println("Check server.log for output from the task.");
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private TestService service01;

        @Inject
        private TestMultipleInterfaceService service02;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {

                service01.execute();

                service02.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);

            // WEB-INF
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter10_concurrencyUtilities/part04_dynamicContextualObjects/web.xml"));

            war.addClasses( //
                    ServletTest.class, MyRunnable.class, //
                    MyWork.class, MyRunnableWork.class, //
                    TestService.class, //
                    TestMultipleInterfaceService.class //
            );

            final File appFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, appFile.getAbsolutePath());

            // ------------ Client -----------------------------------------------------------
            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/Servlet"));

            System.out.println(response);

            Thread.sleep(10000); // 10 seconds only for the server finishs its jobs...

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
