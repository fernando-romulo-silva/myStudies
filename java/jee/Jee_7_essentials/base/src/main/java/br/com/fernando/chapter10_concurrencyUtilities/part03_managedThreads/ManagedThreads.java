package br.com.fernando.chapter10_concurrencyUtilities.part03_managedThreads;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
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

public class ManagedThreads {

    // ---------------------------------------------------------------------------------------------------------------------------------
    // javax.enterprise.concurrent.ManagedThreadFactory can be used to create managed threads for execution in a Java EE environment.
    //
    // You can create new threads from this factory using newThread(Runnable r). For a task definition:
    public static class MyTask implements Runnable {

        private int id;

        public MyTask(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("Running Task: " + id);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    public static class TestJNDIService {

        protected void execute() throws Exception {

            System.out.println("Getting ManagedThreadFactory using JNDI lookup");

            final InitialContext ctx = new InitialContext();

            // You can obtain an instance of ManagedThreadFactory with a JNDI lookup using resource environment references. 
            // A default ManagedThreadFactory is available under the JNDI name java:comp/DefaultManagedThreadFactory.
            ManagedThreadFactory factory = (ManagedThreadFactory) ctx.lookup("java:comp/env/concurrent/myFactory");
            // ManagedExecutorService executor = (ManagedExecutorService) ctx.lookup("concurrent/myExecutor");

            System.out.println("Getting ManageableThread");

            // A new thread can be created and started:
            Thread thread = factory.newThread(new MyTask(2));
            // The returned threads implement the ManageableThread interface.
            thread.start();

            // The container context information is propagated to the thread’s Runnable. 
            // Naming context, class loader, and security context information is propagated to the thread. 
            // Use a UserTransaction instance if a transaction is required.

            System.out.println("Thread started");

            System.out.println("all tasks submitted");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    public static class TestResourceNoNameService {

        // You can also inject ManagedThreadFactory into the application using @Resource:
        @Resource(name = "concurrent/myFactory")
        private ManagedThreadFactory factory;

        // You can obtain an application-specific ManagedThreadFactory by adding the following fragment to web.xml

        protected void execute() throws Exception {
            System.out.println("Getting ManagedThreadFactory using @Resource with no name");

            System.out.println("Getting ManageableThread");

            Thread thread = factory.newThread(new MyTask(1));

            System.out.println("Starting thread");

            thread.start();

            System.out.println("Thread started");

            System.out.println("Check server.log for output from the task.");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    public static class TestResourceService {

        @Resource(name = "DefaultManagedThreadFactory")
        private ManagedThreadFactory factory;

        protected void execute() throws Exception {
            System.out.println("Getting ManagedThreadFactory using @Resource");

            System.out.println("Getting ManageableThread");

            Thread thread = factory.newThread(new MyTask(1));

            System.out.println("Starting thread");

            thread.start();

            System.out.println("Thread started");
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private TestResourceNoNameService service01;

        @Inject
        private TestJNDIService service02;

        @Inject
        private TestResourceService service03;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {

                service01.execute();

                service02.execute();

                service03.execute();

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

            // You can obtain an application-specific ManagedThreadFactory by adding the following fragment to chapter10_concurrencyUtilities/part03_managedThreads/web.xml
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter10_concurrencyUtilities/part03_managedThreads/web.xml"));

            war.addClasses( //
                    ServletTest.class, MyTask.class, //
                    TestResourceNoNameService.class, TestJNDIService.class, TestResourceService.class //
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
