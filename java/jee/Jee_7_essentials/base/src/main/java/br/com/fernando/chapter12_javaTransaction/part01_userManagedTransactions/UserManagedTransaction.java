package br.com.fernando.chapter12_javaTransaction.part01_userManagedTransactions;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class UserManagedTransaction {

    // The UserTransaction interface enables the application to control transaction boundaries programmatically.
    // This interface is typically used in EJBs with bean-managed transactions (BMT)

    @Named
    public static class Service {

        // You can obtain UserTransaction using injection:
        @Inject
        private UserTransaction ut;

        public void execute() throws Exception {
            // or through a JNDI lookup using the name java:comp/UserTransaction:
            final Context context = new InitialContext();
            final UserTransaction ut = (UserTransaction) context.lookup("java:comp/UserTransaction");

            // The begin method starts a global transaction and associates the transaction with the calling thread.
            ut.begin();

            // The commit method completes the transaction associated with the current thread.
            // All statements within begin and commit execute in the transaction scope;
            ut.commit();

            // When the commit method completes, the thread is no longer associated with a transaction.

            // Support for nested transactions is not required.
        }

        public void executeNew() throws Exception {
            // You can change the timeout value associated with the transaction started by the current thread with the begin method:
            ut.setTransactionTimeout(3);

            ut.begin();

            // A transaction can be rolled back via the rollback method:
            ut.rollback();
            // When the rollback method completes, the thread is no longer associated with a transaction.
        }
    }

    // =========================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private Service service;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            try {

                service.execute();

                service.executeNew();

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
            war.addClasses(ServletPrincipal.class, Service.class);

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
