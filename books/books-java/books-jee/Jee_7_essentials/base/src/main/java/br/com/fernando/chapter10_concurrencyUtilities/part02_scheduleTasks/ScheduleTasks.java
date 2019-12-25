package br.com.fernando.chapter10_concurrencyUtilities.part02_scheduleTasks;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.Trigger;
import javax.inject.Inject;
import javax.naming.Context;
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

public class ScheduleTasks {

    // ---------------------------------------------------------------------------------------------------------------------------------
    // javax.enterprise.concurrent.ManagedScheduledExecutorService provides a managed version of ScheduledExecutorService
    // and can be used to schedule tasks at specified and periodic times.
    //
    // You can obtain an application-specific ManagedScheduledExecutorService by adding the following
    // fragment to hapter10_concurrencyUtilities/part02_scheduleTask/web.xml
    public static class Product {

        private int id;

        public Product() {
        }

        public Product(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    // Each task can implement the ManagedTask and ManagedTaskListener interfaces to provide identity information about the task, 
    // get notification of life-cycle events of the task, or provide additional execution properties.
    //
    // This behaves in a similar manner to ManagedExecutorService.

    public static class MyCallableTask implements Callable<Product> {

        private int id;

        public MyCallableTask(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public Product call() {
            System.out.println("Running Callable Task: " + id);
            return new Product(id);
        }
    }

    public static class MyRunnableTask implements Runnable {

        private int id;

        public MyRunnableTask(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("Running Runnable Task: " + id);
        }
    }

    public static class Service01 {

        // You can also inject ManagedScheduledExecutorService into the application using @Resource:
        // @Resource(name = "concurrent/myScheduledExecutor2")
        @Resource(name = "DefaultManagedScheduledExecutorService")
        private ManagedScheduledExecutorService executor;

        public void execute() throws Exception {

            System.out.println("Schedule tasks");

            // You submit task instances to a ManagedScheduledExecutorService using any of the submit, execute, invokeAll, invokeAny,
            // schedule, scheduleAtFixedRate, or scheduleWithFixedDelay methods.
            //
            //
            // <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) creates and executes a one-shot
            // ScheduledFuture that becomes enabled after the given delay.
            final ScheduledFuture<Product> future = executor.schedule(new MyCallableTask(5), 5, TimeUnit.SECONDS);

            while (true) {
                if (future.isDone()) {
                    break;
                } else {
                    System.out.println("Checking Callable Future, waiting for 1 sec");
                    Thread.sleep(1000);
                }
            }

            System.out.println("Callable Task completed: " + future.get().getId());

            System.out.println("Scheduling tasks using Runnable");

            // ScheduledFuture<V> schedule(Runnable command, long delay, TimeUnit unit) creates and executes a one-shot action that
            // becomes enabled after the given delay.
            ScheduledFuture<?> f = executor.schedule(new MyRunnableTask(10), 5, TimeUnit.SECONDS);
            //
            // This code will schedule the task after five seconds of initial delay.
            // The returned ScheduledFuture can be used to check the status of the task, cancel the execution, and retrieve the result.

            while (true) {
                if (f.isDone()) {
                    break;
                } else {
                    System.out.println("Checking Runnable Future, waiting for 1 sec");
                    Thread.sleep(1000);
                }
            }

            System.out.println("Runnable Task completed: " + future.get().getId());
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    public static class MyTrigger implements Trigger {

        private final Date firetime;

        public MyTrigger(final Date firetime) {
            this.firetime = firetime;
        }

        @Override
        public Date getNextRunTime(LastExecution le, Date taskScheduledTime) {
            if (firetime.before(taskScheduledTime)) {
                return null;
            }
            return firetime;
        }

        @Override
        public boolean skipRun(LastExecution le, Date scheduledRunTime) {
            return firetime.before(scheduledRunTime);
        }
    }

    public static class Service02 {

        // @Resource(name = "concurrent/myScheduledExecutor2")
        @Resource(name = "DefaultManagedScheduledExecutorService")
        private ManagedScheduledExecutorService executor;

        public void execute() throws Exception {

            System.out.println("Schedule tasks with a trigger");

            // <V> ScheduledFuture<V> schedule(Callable<V> callable, Trigger trigger) creates and executes a task based on a Trigger.
            for (int i = 0; i < 5; i++) {
                executor.schedule(new MyRunnableTask(i), new MyTrigger(new Date(System.currentTimeMillis() + 30000)));
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------

    public static class Service03 {

        // @Resource(name = "concurrent/myScheduledExecutor2")
        // @Resource(name = "DefaultManagedScheduledExecutorService")
        @Resource
        private ManagedScheduledExecutorService executor;

        public void execute() throws Exception {

            System.out.println("Schedule tasks with fixed delay");

            // ScheduledFuture <?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) 
            // creates and executes a periodic action that becomes enabled first after the given initial delay, and subsequently with the 
            // given delay between the termination of one execution and the commencement of the next:

            executor.scheduleWithFixedDelay(new MyRunnableTask(5), 2, 3, TimeUnit.SECONDS);

            // This code will schedule the task after an initial delay of two seconds and then every three seconds after that.
            //
            // A new task is started only after the previous task has terminated successfully.
            //
            // If any execution of the task encounters an exception, subsequent executions are suppressed.
            //
            // Otherwise, the task will only terminate via cancellation or termination of the executor.

            System.out.println("Check server.log for output");

            System.out.println("Runnable Task submitted");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------

    public static class Service04 {

        public void execute() throws Exception {

            System.out.println("Schedule at fixed rate");

            // You can obtain an instance of ManagedScheduledExecutorService with a JNDI lookup using resource environment references.
            final InitialContext initialContext = new InitialContext();

            // java:comp/DefaultManagedScheduledExecutorService
            final Context context = (Context) initialContext.lookup("java:comp/env");
            final ManagedScheduledExecutorService executor = (ManagedScheduledExecutorService) context.lookup("concurrent/myScheduledExecutor");

            // ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
            // creates and executes a periodic action that becomes enabled first after the given initial delay, and subsequently with the given period;
            //
            // that is, executions will commence after initialDelay, then initialDelay+period, then initialDelay + 2 * period, and so on:

            final ScheduledFuture<?> f = executor.scheduleAtFixedRate(new MyRunnableTask(5), 2, 3, TimeUnit.SECONDS);
            // The returned ScheduledFuture can be used to check the status of the task, cancel the execution, and retrieve the result.

            // This code will schedule the task after an initial delay of two seconds and then every three seconds after that.
            //
            // If any execution of the task encounters an exception, subsequent executions are suppressed.
            //
            // If any execution of this task takes longer than its period, then subsequent executions may start late, but will not concurrently execute.
            //
            // Otherwise, the task will only terminate via cancellation or termination of the executor.

            Thread.sleep(1000);

            f.cancel(true);
            //
            System.out.println("Runnable Task completed");
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private Service01 service01;

        @Inject
        private Service02 service02;

        @Inject
        private Service03 service03;

        @Inject
        private Service04 service04;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {

                service01.execute();

                service02.execute();

                service03.execute();

                service04.execute();

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
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter10_concurrencyUtilities/part02_scheduleTask/web.xml"));

            war.addClasses( //
                    ServletTest.class, Product.class, //
                    Service01.class, MyCallableTask.class, MyRunnableTask.class, MyTrigger.class, //
                    Service02.class, Service03.class, Service04.class//
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
