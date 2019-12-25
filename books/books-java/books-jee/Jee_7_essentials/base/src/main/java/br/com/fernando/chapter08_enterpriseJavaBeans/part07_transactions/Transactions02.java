package br.com.fernando.chapter08_enterpriseJavaBeans.part07_transactions;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;

import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.BeforeCompletion;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
import org.myembedded.pack.EmbeddedEar;
import org.myembedded.pack.EmbeddedEjb;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;
import org.myembedded.pack.JeeVersion;

/**
 * @see br.com.fernando.chapter12_javaTransaction.part02_containerManagedTransactions.ContainerManagedTransactions
 */
public class Transactions02 {

    // ==================================================================================================================================================================
    // A session bean using a container-managed transaction can use @TransactionAttribute to specify transaction attributes on the bean class or the method.
    //
    // The absence of TransactionAttribute on the bean class is equivalent to the specification of TransactionAttribute(REQUIRED) on the bean.
    //
    // Specifying the TransactionAttribute on a bean class means that it applies to all applicable methods of the bean.
    @Stateful
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public static class ServiceBean {

        private String transaction;

        // The @TransactionAttribute values and meaning are defined in:
        //
        // ----------------------------------------------------------------------
        // SUPPORTS
        //
        // * If the client calls with a transaction context, then it behaves as REQUIRED. That is the method executes within the client’s transaction.
        //
        // * If the client calls without a transaction context, then it behaves as NOT_SUPPORTED. If the client is not associated with a transaction, the container
        // does not start a new transaction before running the method.
        //
        // Because the transactional behavior of the method may vary, you should use the Supports attribute with caution.
        @TransactionAttribute(TransactionAttributeType.SUPPORTS)
        public void transactionSupports() {
            transaction = "Transaction Supports";
            System.out.println("@ Executing bean " + transaction + " method");
        }

        // ----------------------------------------------------------------------
        // NOT_SUPPORTED
        //
        // * If the client is running within a transaction and invokes the enterprise bean’s method, the container suspends the client’s transaction
        // before invoking the method. After the method has completed, the container resumes the client’s transaction.
        //
        // * If the client calls without a transaction context, then no new transaction context is created.
        //
        // Use the NotSupported attribute for methods that don't need transactions. Because transactions involve overhead, this attribute may improve performance.
        @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
        public void transactionNotSupported() {
            transaction = "Transaction Not Supported";
            System.out.println("@ Executing bean " + transaction + " method");
        }

        // ----------------------------------------------------------------------
        // REQUIRES_NEW
        //
        // * If the client calls with a transaction context, then the suspended transaction is resumed after the new transaction has committed.
        // The container takes the following steps:
        //
        // ** Suspends the client’s transaction
        // ** Starts a new transaction
        // ** Delegates the call to the method
        // ** Resumes the client’s transaction after the method completes
        //
        // * The container always starts a new transaction context before delegating a call to the business method and attempts to commit the transaction
        // when the business process has completed.
        //
        // You should use the RequiresNew attribute when you want to ensure that the method always runs within a new transaction.
        @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
        public void transactionRequiresNew() {
            transaction = "Transaction Requires New";
            System.out.println("@ Executing bean " + transaction + " method");
        }

        // ----------------------------------------------------------------------
        // REQUIRED
        //
        // * If the client calls with a transaction context, then it is propagated, the method executes within the client’s transaction.
        //
        // * If the client is not associated with a transaction, the container starts a new transaction before running the method.
        //
        // The Required attribute is the implicit transaction attribute for all enterprise bean methods running with container-managed transaction demarcation.
        // You typically do not set the Required attribute unless you need to override another transaction attribute.
        // Because transaction attributes are declarative, you can easily change them later.
        @TransactionAttribute(TransactionAttributeType.REQUIRED)
        public void transactionRequired() {
            transaction = "Transaction Required";
            System.out.println("@ Executing bean " + transaction + " method");
        }

        // ----------------------------------------------------------------------
        // MANDATORY
        //
        // * Always called in client's transaction context. If the client is running within a transaction and invokes
        // the enterprise bean's method, the method executes within the client's transaction
        //
        // * If the client calls without a transaction context, then the container throws the javax.ejb.EJBTransactionRequiredException
        //
        // Use the Mandatory attribute if the enterprise bean’s method must use the transaction of the client.
        @TransactionAttribute(TransactionAttributeType.MANDATORY)
        public void transactionMandatory() {
            transaction = "Transaction Mandatory";
            System.out.println("@ Executing bean " + transaction + " method");
        }

        // -----------------------------------------------------------------------
        // NEVER
        //
        // * If the client calls without a transaction context, then it behaves as NOT_SUPPORTED, the container does not start a new transaction before running the method.
        //
        // * The client is required to call without a transaction context. If the client calls with a transaction context, then the container throws javax.ejb.EJBException.
        @TransactionAttribute(TransactionAttributeType.NEVER)
        public void transactionNever() {
            transaction = "Transaction Never";
            System.out.println("@ Executing bean " + transaction + " method");
        }

        // ---------------------------------------------------------
        @AfterBegin
        private void afterBegin() {
            System.out.println("@ T1 A new bean transaction has started, I don't know yet");
        }

        @BeforeCompletion
        private void beforeCompletion() {
            System.out.println("@ T2 A bean " + transaction + " is about to be committed");
        }

        @AfterCompletion
        private void afterCompletion(boolean committed) {
            System.out.println("@ T3 A bean " + transaction + " commit protocol has completed, and tells the instance whether the transaction has been committed or rolled back , based on committed value : " + committed);
        }
    }

    // -------------------------------------------------------------------------------
    @Stateful
    public static class ServiceClient {

        private String transaction;

        @EJB
        ServiceBean serviceBean;

        @TransactionAttribute(TransactionAttributeType.REQUIRED)
        public void executeTransactionSupportsWithTransacion() {
            transaction = "Transaction Supports";

            System.out.println("# Begin client method: " + transaction + " with transacion");

            serviceBean.transactionSupports();

            System.out.println("# End client method: " + transaction + " with transacion");
        }

        @TransactionAttribute(TransactionAttributeType.NEVER) //
        public void executeTransactionSupportsWithoutTransacion() {
            transaction = "Transaction Supports";

            System.out.println("# Begin client method: " + transaction + " without transacion");

            serviceBean.transactionSupports();

            System.out.println("# End client method: " + transaction + " without transacion");
        }

        // ------------------------------------------------------------------------------------------------
        @TransactionAttribute(TransactionAttributeType.REQUIRED)
        public void executeTransactionNotSupportedWithTransacion() {
            transaction = "Transaction Not Supported";
            System.out.println("# Begin client method: " + transaction + " with transacion");

            serviceBean.transactionNotSupported();

            System.out.println("# End client method: " + transaction + " with transacion");
        }

        @TransactionAttribute(TransactionAttributeType.NEVER) //
        public void executeTransactionNotSupportedWithoutTransacion() {
            transaction = "Transaction Not Supported";

            System.out.println("# Begin client method: " + transaction + " without transacion");

            serviceBean.transactionNotSupported();

            System.out.println("# End client method: " + transaction + " without transacion");
        }

        // ------------------------------------------------------------------------------------------------
        @TransactionAttribute(TransactionAttributeType.REQUIRED)
        public void executeTransactionRequiresNewWithTransacion() {
            transaction = "Transaction Requires New";
            System.out.println("# Begin client method: " + transaction + " with transacion");

            serviceBean.transactionRequiresNew();

            System.out.println("# End client method: " + transaction + " with transacion");
        }

        @TransactionAttribute(TransactionAttributeType.NEVER) //
        public void executeTransactionRequiresNewWithoutTransacion() {
            transaction = "Transaction Requires New";

            System.out.println("# Begin client method: " + transaction + " without transacion");

            serviceBean.transactionRequiresNew();

            System.out.println("# End client method: " + transaction + " without transacion");
        }

        // ------------------------------------------------------------------------------------------------
        @TransactionAttribute(TransactionAttributeType.REQUIRED)
        public void executeTransactionRequiredWithTransacion() {
            transaction = "Transaction Required";
            System.out.println("# Begin client method: " + transaction + " with transacion");

            serviceBean.transactionRequired();

            System.out.println("# End client method: " + transaction + " with transacion");
        }

        @TransactionAttribute(TransactionAttributeType.NEVER) //
        public void executeTransactionRequiredWithoutTransacion() {
            transaction = "Transaction Required";

            System.out.println("# Begin client method: " + transaction + " without transacion");

            serviceBean.transactionRequired();

            System.out.println("# End client method: " + transaction + " without transacion");
        }

        // ------------------------------------------------------------------------------------------------
        @TransactionAttribute(TransactionAttributeType.REQUIRED)
        public void executeTransactionMandatoryWithTransacion() {
            transaction = "Transaction Mandatory";
            System.out.println("# Begin client method: " + transaction + " with transacion");

            serviceBean.transactionMandatory();

            System.out.println("# End client method: " + transaction + " with transacion");
        }

        @TransactionAttribute(TransactionAttributeType.NEVER) //
        public void executeTransactionMandatoryWithoutTransacion() {
            transaction = "Transaction Required";

            System.out.println("# Begin client method: " + transaction + " without transacion");

            try {
                serviceBean.transactionMandatory();
            } catch (Exception ex) {
                System.out.println("# End client method: " + transaction + " without transacion, TransactionRequiredLocalException exception");
            }
        }

        // ------------------------------------------------------------------------------------------------
        @TransactionAttribute(TransactionAttributeType.REQUIRED)
        public void executeTransactionNeverWithTransacion() {
            transaction = "Transaction Never";
            System.out.println("# Begin client method: " + transaction + " with transacion");

            try {
                serviceBean.transactionNever();

            } catch (Exception ex) {
                System.out.println("# End client method: " + transaction + " with transacion, EJBException exception");
            }
        }

        @TransactionAttribute(TransactionAttributeType.NEVER) //
        public void executeTransactionNeverWithoutTransacion() {
            transaction = "Transaction Never";

            System.out.println("# Begin client method: " + transaction + " without transacion");

            serviceBean.transactionNever();
        }

        // ------------------------------------------------------------------------------------------------
        @AfterBegin
        private void afterBegin() {
            System.out.println("# T1 A new client transaction has started.");
        }

        @BeforeCompletion
        private void beforeCompletion() {
            System.out.println("# T2 A client transaction is about to be committed.");
        }

        @AfterCompletion
        private void afterCompletion(boolean committed) {
            System.out.println("# T3 A client transaction commit protocol has completed, and tells the instance whether the transaction has been committed or rolled back , based on committed value : " + committed);
        }
    }

    //
    // The container-transaction element in the deployment descriptor may be used instead of annotations to specify the transaction attributes.
    // The values specified in the deployment descriptor override or supplement the transaction attributes specified in the annotation.
    //
    // Only the NOT_SUPPORTED and REQUIRED transaction attributes may be used for messagedriven beans.
    //
    // A JMS message is delivered to its final destination after the transaction is committed, so the client will not receive the reply within the same transaction.
    //
    // By default, the methods designated with @PostConstruct, @PreDestroy, @PrePassivate, and @PostActivate are executed in an unspecified transactional context.
    //
    //
    // EJB 3.2 specifies that for a stateful session bean with container-managed transaction demarcation, these methods can have REQUIRES_NEW and NOT_SUPPORTED attributes.
    //
    // ==================================================================================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private ServiceClient service;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            try {

                System.out.println("================ Transaction Supports With Transaction ========================================================================");
                service.executeTransactionSupportsWithTransacion();

                System.out.println("================ Transaction Supports Without Transaction =====================================================================");
                service.executeTransactionSupportsWithoutTransacion();

                System.out.println("================ Transaction Not Supported With Transaction ===================================================================");
                service.executeTransactionNotSupportedWithTransacion();

                System.out.println("================ Transaction Not Supported Without Transaction ================================================================");
                service.executeTransactionNotSupportedWithoutTransacion();

                System.out.println("================ Transaction Requires New With Transaction ====================================================================");
                service.executeTransactionRequiresNewWithTransacion();

                System.out.println("================ Transaction Requires New Without Transaction =================================================================");
                service.executeTransactionRequiresNewWithoutTransacion();

                System.out.println("================ Transaction Required With Transaction ========================================================================");
                service.executeTransactionRequiredWithTransacion();

                System.out.println("================ Transaction Required Without Transaction =====================================================================");
                service.executeTransactionRequiredWithoutTransacion();

                System.out.println("================ Transaction Mandatory With Transaction =======================================================================");
                service.executeTransactionMandatoryWithTransacion();

                System.out.println("================ Transaction Mandatory Without Transaction ====================================================================");
                service.executeTransactionMandatoryWithoutTransacion();

                System.out.println("================ Transaction Never With Transaction =======================================================================");
                service.executeTransactionNeverWithTransacion();

                System.out.println("================ Transaction Never Without Transaction ====================================================================");
                service.executeTransactionNeverWithoutTransacion();

                System.out.println("===============================================================================================================================");
            } catch (final Exception e) {
                throw new ServletException(e);
            }
        }
    }

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addClasses(ServletPrincipal.class);

            final EmbeddedEjb ejb = new EmbeddedEjb(EMBEDDED_JEE_TEST_APP_NAME);
            ejb.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            ejb.addClasses(ServiceClient.class, ServiceBean.class);

            final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
            ear.addModules(war);
            ear.addModules(ejb);

            final File earFile = ear.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, earFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));

            System.out.println(response);

        } catch (final IOException ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
