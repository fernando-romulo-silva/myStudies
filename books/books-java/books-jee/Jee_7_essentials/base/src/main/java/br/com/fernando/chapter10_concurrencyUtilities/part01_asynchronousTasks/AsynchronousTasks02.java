package br.com.fernando.chapter10_concurrencyUtilities.part01_asynchronousTasks;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedTask;
import javax.enterprise.concurrent.ManagedTaskListener;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class AsynchronousTasks02 {

    public static boolean FOUND_TRANSACTION_SCOPED;

    // -------------------------------------------------------------------------------------------------------------------------------
    public static class MyTask implements Runnable, ManagedTask, ManagedTaskListener {

        @Override
        public void run() {
            System.out.println("MyTask.run");
        }

        // ----------------------------------------------------------------------------------------------
        // javax.enterprise.concurrent.ManagedTask
        //
        // Each task can implement the ManagedTask interface to provide identity information about the task, get notification of
        // life-cycle events of the task, or provide additional execution properties.
        @Override
        public Map<String, String> getExecutionProperties() {

            // The execution properties provide additional information to ManagedExecutorService when executing this task.
            // Some standard property keys are defined in this class, such as the ManagedTask.IDENTITY_NAME property, which can be used to uniquely identify a task.

            final Map<String, String> properties = new HashMap<>();
            properties.put(ManagedTask.IDENTITY_NAME, "MyTask");

            return properties;
        }

        @Override
        public ManagedTaskListener getManagedTaskListener() {
            return this;
        }

        // ----------------------------------------------------------------------------------------------
        // javax.enterprise.concurrent.ManagedTaskListener
        // A task can also implement the ManagedTaskListener interface to receive life-cycle event notifications.
        // All listeners run without an explicit transaction (they do not enlist in the application component’s transaction).
        // UserTransaction instance can be used if transactions are required.
        //
        // Called before the task is about to start
        @Override
        public void taskStarting(Future<?> future, ManagedExecutorService service, Object object) {
            System.out.println("taskStarting");
        }

        // Called after the task is submitted to the Executor
        @Override
        public void taskSubmitted(Future<?> future, ManagedExecutorService service, Object object) {
            System.out.println("taskSubmitted");
        }

        // Called when a task’s Future has been cancelled any time during the life of a task
        @Override
        public void taskAborted(Future<?> future, ManagedExecutorService service, Object object, Throwable exception) {
            System.out.println("taskAborted");
        }

        // Called when a submitted task has completed running, successful or otherwise
        @Override
        public void taskDone(Future<?> future, ManagedExecutorService service, Object object, Throwable exception) {
            System.out.println("taskDone");
        }
    }

    public static class Service01 {

        @Resource(name = "DefaultManagedExecutorService")
        private ManagedExecutorService executor;

        public void execute() throws Exception {

            executor.submit(new MyTask());

        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    //
    // The security context of the calling thread is propagated to the called thread.
    //
    // The transaction context is not propagated.
    public static class MyTaskWithTransaction implements Runnable {

        @Override
        public void run() {

            // Transactions can be explicitly started and committed or rolled back via javax.transaction.UserTransaction:
            try {
                InitialContext context = new InitialContext();
                UserTransaction tx = (UserTransaction) context.lookup("java:comp/UserTransaction");

                tx.begin();

                System.out.println("MyTaskWithTransaction.run");

                tx.commit();
            } catch (Exception ex) {

            }
        }
    }

    // Transaction Bean Scoped

    @TransactionScoped
    public static class MyTransactionScopedBean implements Serializable {

        private static final long serialVersionUID = 1L;

        // Transaction here
        public boolean isInTx() {
            return true;
        }
    }

    public static class MyTaskWithTransactionBean implements Runnable, Serializable {

        private static final long serialVersionUID = 1L;

        private int id;

        @Inject
        private MyTransactionScopedBean bean;

        public MyTaskWithTransactionBean() {
        }

        public MyTaskWithTransactionBean(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        @Transactional
        public void run() {
            // a Call to a TX Scoped bean should fail if outside a tx
            try {
                FOUND_TRANSACTION_SCOPED = bean.isInTx();

                System.out.println("MyTaskWithTransactionBean.run");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class Service02 {

        @Resource(name = "DefaultManagedExecutorService")
        private ManagedExecutorService executor;

        @Inject
        // Inject so we have a managed bean to handle the TX
        private MyTaskWithTransactionBean myTaskWithTransactionBean;

        public void execute() throws Exception {

            executor.submit(new MyTaskWithTransaction());

            executor.submit(myTaskWithTransactionBean);
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
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter10_concurrencyUtilities/part01_asynchronousTasks/web.xml"));

            war.addClasses( //
                    ServletTest.class, //
                    Service01.class, MyTask.class, //
                    Service02.class, MyTaskWithTransaction.class, MyTransactionScopedBean.class, MyTaskWithTransactionBean.class //
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
