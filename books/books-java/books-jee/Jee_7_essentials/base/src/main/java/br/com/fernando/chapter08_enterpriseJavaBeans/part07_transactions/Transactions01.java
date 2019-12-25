package br.com.fernando.chapter08_enterpriseJavaBeans.part07_transactions;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

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

public class Transactions01 {

    /**
     * Container-Managed Transaction Demarcation
     * 
     * <pre>
     *  +---------------------+----------------+----------------+----------------+----------------+--+ 
     *  |        Scope        |   Stateless    |    Stateful    |   Singleton    | MessageDriven  |  |
     *  +---------------------+----------------+----------------+----------------+----------------+--+
     *  | Constructer, DI     | UT             | UT             | UT             | UT             |  |
     *  | PostConstructor     | UT             | TX[RN, NS]     | TX[RE, RN, NS] | UT             |  |
     *  | PreDestroy          | UT             | TX[RN, NS]     | TX[RE, RN, NS] | UT             |  |
     *  | PrePassivate        | XX             | TX[RN, NS]     | XX             | XX             |  |
     *  | PosActivate         | XX             | TX[RN, NS]     | XX             | XX             |  |
     *  | AfterBegin          | XX             | TX[RN, NS, MN] | XX             | XX             |  |
     *  | BeforeCompletion    | XX             | TX[RN, NS, MN] | XX             | XX             |  |
     *  | AfterCompletion     | XX             | UT             | XX             | XX             |  |
     *  | Business Method     | TX             | TX             | TX             | TX[RE, NS]     |  |
     *  | TimeOut Method      | TX[RE, RN, NS] | XX             | TX[RE, RN, NS] | TX[RE, RN, NS] |  |
     *  | Asynchronous Method | TX[RE, RN, NS] | TX[RE, RN, NS] | TX[RE, RN, NS] | XX             |  |
     *  +---------------------+----------------+----------------+----------------+----------------+--+
     * 
     * UT - Unspecifified Transaction Contex
     * TX - Transaction Context
     * 
     * RN - Requires New 
     * NS - Not supported
     * RE - Requiered
     * MN - Mandatory
     * </pre>
     */

    // ==================================================================================================================================================================
    //
    // The @TransactionManagement annotation is used to declare whether the session bean or message-driven bean uses a bean-managed or container-managed transaction.
    // The value of this annotation is either CONTAINER (the default) or BEAN.
    //
    // A bean may use programmatic transaction in the bean code, which is called a beanmanaged transaction.
    @Stateless
    @TransactionManagement(TransactionManagementType.BEAN) // Bean Managed Transaction ( BMT )
    public static class AccountSessionBean {

        // A bean-managed transaction requires you to specify @TransactionManagement(BEAN) on the class and use the javax.transaction.UserTransaction interface.
        @Resource
        private UserTransaction transaction;

        // Within the business method, a transaction is started with UserTransaction.begin and committed with UserTransaction.commit:
        public float deposit() throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {

            transaction.begin();

            System.out.println("In Transaction!");

            transaction.commit();

            return 0.0f;
        }
    }

    // Alternatively, a declarative transaction may be used in which the transactions are managed automatically by the container; this is called a containermanaged transaction.
    // By default, transactions in a bean are container-managed.
    //
    // Container-managed transaction is the default and does not require you to specify any additional annotations on the class.
    // The EJB container implements all the low-level transaction protocols, such as the two-phase commit protocol between a transaction manager and a database system
    // or messaging provider, to honor the transactional semantics. The changes to the underlying resources are all committed or rolled back.
    @Stateless
    @TransactionManagement(TransactionManagementType.CONTAINER) // Container Managed Transaction ( CMT )
    public static class UserBean {

        public void showUser() {
            System.out.println("Show User!");
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private AccountSessionBean accountSessionBean;

        @EJB
        private UserBean userBean;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            try {

                accountSessionBean.deposit();

                userBean.showUser();

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
            ejb.addClasses(AccountSessionBean.class, UserBean.class);

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
