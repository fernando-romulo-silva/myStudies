package br.com.fernando.chapter12_javaTransaction.part02_containerManagedTransactions;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

/**
 * @see br.com.fernando.chapter08_enterpriseJavaBeans.part07_transactions.Transactions02
 */
public class ContainerManagedTransactions {

    // ======================================================================================================
    // Container Managed Transactions (CMT)
    //
    // With container-managed transaction demarcation, the container demarcates transactions per instructions
    // provided by the developer in metadata annotations or in the deployment descriptor.
    //
    // With CMT, transactions are started and completed (with either a commit or rollback) by the container.
    //
    // CMT Bean ---(tx coming from CMT bean)---> CMT Bean
    // BMT Bean ---(tx coming from BCMT bean)---> CMT Bean
    //
    // JTA 1.2 introduces the @javax.transaction.Transactional annotation.
    //
    // It enables an application to declaratively control transaction boundaries on CDI-managed beans, as well as classes defined as managed beans,
    // such as servlets, JAX-RS resource classes, and JAX-WS service endpoints.
    //
    // The annotation can be specified at both the class and method level, where method-level annotations override those at the class level:
    //
    @Transactional
    public static class MyBean {

    }

    // ======================================================================================================
    @RequestScoped
    public static class MyTransactionalTxTypeBean {

        // In this code, all methods of the bean are executed in a transaction:
        @Transactional
        public void myMethod2() {

        }

        // This support is provided via an implementation of CDI interceptors that conduct the necessary suspending, resuming, etc.
        // The Transactional interceptor interposes on business method invocations and life-cycle events.
        // Life-cycle methods are invoked in an unspecified transaction context unless the method is annotated explicitly with @Transactional.
        //
        //
        //
        @Transactional(Transactional.TxType.REQUIRED) // (Default)
        // Outside a transaction context:
        // The interceptor must begin a new JTA transaction, the managed bean method execution must then continue inside this transaction context,
        // and the transaction must be completed by the interceptor
        //
        // Inside a transaction context:
        // The current transaction context must be suspended, a new JTA transaction will begin, the managed bean method execution must then continue
        // inside this transaction context, the transaction must be completed, and the previously suspended transaction must be resumed.
        public void required() {
            System.out.println(getClass().getName() + "Transactional.TxType.REQUIRED");
        }

        @Transactional(Transactional.TxType.MANDATORY)
        // Outside a transaction context:
        // A TransactionalException with a nested TransactionRequiredException must be thrown.
        //
        // Inside a transaction context:
        // The managed bean method execution will then continue under that context.
        public void mandatory() {
            System.out.println(getClass().getName() + "Transactional.TxType.MANDATORY");
        }

        // Outside a transaction context:
        //
        // Inside a transaction context:
        @Transactional(Transactional.TxType.SUPPORTS)
        public void supports() {
            System.out.println(getClass().getName() + "Transactional.TxType.SUPPORTS");
        }

        // Outside a transaction context:
        // The managed bean method execution must then continue outside a transaction context.
        //
        // Inside a transaction context:
        // The managed bean method execution must then continue inside this transaction context.
        @Transactional(Transactional.TxType.REQUIRES_NEW)
        public void requiresNew() {
            System.out.println(getClass().getName() + "Transactional.TxType.REQUIRES_NEW");
        }

        // Outside a transaction context:
        // The managed bean method execution must then continue outside a transaction context.
        //
        // Inside a transaction context:
        // The current transaction context must be suspended, the managed bean method execution must then continue outside a transaction context,
        // and the previously suspended transaction must be resumed by the interceptor that suspended it after the method execution has completed.
        @Transactional(Transactional.TxType.NOT_SUPPORTED)
        public void notSupported() {
            System.out.println(getClass().getName() + "Transactional.TxType.NOT_SUPPORTED");
        }

        // Outside a transaction context:
        // The managed bean method execution must then continue outside a transaction context.
        //
        // Inside a transaction context:
        // A TransactionalException with a nested InvalidTransactionException must be thrown.
        @Transactional(Transactional.TxType.NEVER)
        public void never() {
            System.out.println(getClass().getName() + "Transactional.TxType.NEVER");
        }
    }

    // =========================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private MyTransactionalTxTypeBean myTransactionalTx;

        @Inject
        UserTransaction ut;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            try {

                myTransactionalTx.myMethod2();

                myTransactionalTx.required();

                myTransactionalTx.mandatory();

                myTransactionalTx.supports();

                myTransactionalTx.requiresNew();

                myTransactionalTx.notSupported();

                myTransactionalTx.never();

            } catch (final Exception e) {
                throw new ServletException(e);
            }
        }
    }

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addClasses(ServletPrincipal.class, MyTransactionalTxTypeBean.class);

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));

            System.out.println(response);

        } catch (final IOException ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
