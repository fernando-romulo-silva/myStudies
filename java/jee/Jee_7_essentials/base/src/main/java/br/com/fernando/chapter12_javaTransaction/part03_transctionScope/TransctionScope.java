package br.com.fernando.chapter12_javaTransaction.part03_transctionScope;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;
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

public class TransctionScope {
    // It defines a bean instance whose life cycle is scoped to the currently active JTA transaction. 
    //
    // If multiple instances of such a bean are injected within a transaction, the underlying CDI proxy refers to the same instance, 
    // ensuring that only one instance of the bean is injected.
    // 
    @TransactionScoped // The object with this annotation will be associated with the current active JTA transaction when the object is used.
    public static class MyTransactionScopedBean implements Serializable {

        private static final long serialVersionUID = 1L;

        public String getId() {
            return this + "";
        }
    }

    // ======================================================================================================
    @Named
    public static class MyTransactionalBean {

        @Inject
        private MyTransactionScopedBean bean1;

        @Inject
        private MyTransactionScopedBean bean2;

        @Inject
        private UserTransaction ut;

        public String id1;

        public String id2;

        @Transactional
        public void withTransaction() {
            id1 = bean1.getId();
            id2 = bean2.getId();
        }

        public void withoutTransaction() {
            id1 = bean1.getId();
            id2 = bean2.getId();
        }

        public void withManualTransaction() throws Exception {

            // The transaction scope is active when the return from a call to UserTransaction.getStatus or TransactionManager.getStatus is one of the following states:
            ut.begin();

            id1 = bean1.getId();
            id2 = bean2.getId();

            System.out.println("Actual status: " + ut.getStatus());

            // The transaction scope is active when the return from a call to UserTransaction.getStatus or TransactionManager.getStatus is one of the following states:
            //
            // Status.STATUS_ACTIVE
            // 
            // Status.STATUS_MARKED_ROLLBACK
            // 
            // Status.STATUS_PREPARED
            // 
            // Status.STATUS_UNKNOWN
            // 
            // Status.STATUS_PREPARING
            // 
            // Status.STATUS_COMMITTING
            // 
            // Status.STATUS_ROLLING_BACK

            ut.commit();
        }
    }

    // =========================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private MyTransactionalBean bean;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            bean.withTransaction();
            final String firstId1 = bean.id1;

            bean.withTransaction();
            final String secondId1 = bean.id1;

            System.out.println("bean1 should change between scenarios: " + firstId1 + "  " + secondId1);

            try {

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
            war.addClasses(ServletPrincipal.class, MyTransactionalBean.class, MyTransactionScopedBean.class);

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
