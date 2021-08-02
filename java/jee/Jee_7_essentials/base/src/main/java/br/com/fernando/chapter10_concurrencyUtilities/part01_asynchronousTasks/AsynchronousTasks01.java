package br.com.fernando.chapter10_concurrencyUtilities.part01_asynchronousTasks;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.inject.Named;
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

public class AsynchronousTasks01 {

    public static class Product {

        private final Long id;

        public Product(Long id) {
            super();
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }

    // javax.enterprise.concurrent.ManagedExecutorService is a managed version of java.util.concurrent.ExecutorService
    // and provides methods for submitting asynchronous tasks in a Java EE environment.

    // You can obtain an instance of ManagedExecutorService with a JNDI lookup using resource environment references.
    // A default ManagedExecutorService is available under the JNDI name java:comp/DefaultManagedExecutorService.

    @Named
    // CDI beans may be submitted as tasks, but it is recommended that they not be in @RequestScoped, @SessionScoped, or @ConversationScoped. 
    //
    // CDI beans with the @Application or @Dependent scope can be used as tasks. 
    // It is possible that the task could be running beyond the life cycle of the submitting component, such as when the component is destroyed.
    public static class Service {

        public void execute() throws Exception {

            // You can obtain ManagedExecutorService as follows:
            final InitialContext ctx = new InitialContext();

            // ManagedExecutorService supports at most one quality of service. 
            // This ensures that a task will execute at most one time.
            final ManagedExecutorService executor = (ManagedExecutorService) ctx.lookup("java:comp/env/concurrent/myExecutor");

            // There are two differences between a task implementing Callable and Runnable:
            // A Callable task can return a result, whereas a Runnable task does not.
            // A Callable task can throw checked exceptions, whereas a Runnable task cannot.
            //
            // Each task runs within the application component context that submitted the task.
            //
            // The unit of work that needs to be executed concurently is called a task.
            // A task is a concrete implementation of the java.lang.Runnable interface:
            Runnable task01 = new Runnable() {

                @Override
                public void run() {
                    System.out.println("Run!");
                }
            };

            // A task may be defined as a concrete implementation of the java.util.concurrent.Callable interface:
            Callable<Product> task02 = new Callable<Product>() {
                // Product is an application-specific class.

                // The call method is called to compute the result of the task, and throws an exception if unable to do so.
                @Override
                public Product call() throws Exception {
                    final Product product = new Product(10L);

                    return product;
                }
            };

            // You submit task instances to a ManagedExecutorService using any of the submit, execute, invokeAll, or invokeAny methods.

            // For Runnable
            executor.execute(task01);

            // For Callable
            final Future<Product> future = executor.submit(task02);
            future.isDone();

            // The invokeAll method executes all the submitted Collection<? extends Callable<T>> tasks and returns a List<Future<T>> 
            // holding their status and results, when completed:

            final List<Future<Product>> invokeAll = executor.invokeAll(Arrays.asList(task02));

            for (final Future<Product> f : invokeAll) {
                System.out.println(f.isDone());
            }

            // The invokeAny method executes any of the submitted Collection<? extends Callable<T>> tasks, and returns the result of one 
            // that has completed successfully (i.e., without throwing any exception), if any do:
            final Product result = executor.invokeAny(Arrays.asList(task02));
            System.out.println(result);

            // The invokeAny method has another variation where the timeout can be specified.
        }

        // or You can also inject ManagedExecutorService into the application using @Resource:
        @Resource(lookup = "java:comp/DefaultManagedExecutorService")
        ManagedExecutorService executor;

        // You can obtain an application-specific ManagedExecutorService by adding the following fragment to web.xml,
        // chapter10_concurrencyUtilities/part01_asynchronousTasks/web.xml
    }

    // ==================================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private Service service;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                service.execute();
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
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter10_concurrencyUtilities/part01_asynchronousTasks/web.xml"));

            war.addClasses( //
                    ServletTest.class, //
                    Service.class, Product.class //
            );

            final File appFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, appFile.getAbsolutePath());

            // ------------ Client -----------------------------------------------------------
            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/Servlet"));

            System.out.println(response);

            Thread.sleep(5000); // 5 seconds only for the server finishs its jobs...

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
