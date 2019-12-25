package br.com.fernando.chapter08_enterpriseJavaBeans.part07_transactions;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.BeforeCompletion;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.SessionSynchronization;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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

public class Transactions03 {

    // ==================================================================================================================================================================
    // https://examples.javacodegeeks.com/enterprise-java/ejb3/transactions/ejb-transaction-management-example/
    //
    //
    // Transaction Management Type in EJB
    //
    // The Enterprise Bean Provider and the client application programmer are not
    // exposed to the complexity of distributed transactions.
    //
    @Stateful
    @TransactionManagement(value = TransactionManagementType.CONTAINER)
    public static class CartBean implements SessionSynchronization {

        // The SessionSynchronization interface, which is optional, allows stateful session bean instances to receive transaction synchronization notifications. 
        //  For example, you could synchronize the instance variables of an enterprise bean with their corresponding values in the database. 
        // The container invokes the SessionSynchronization methods (afterBegin, beforeCompletion, and afterCompletion) at each of the main stages of a transaction.

        private ArrayList<String> items;

        @PostConstruct
        public void init() {
            items = new ArrayList<>();
            System.out.println("CartBean: init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("CartBean: destroy");
        }

        @Remove
        public void checkOut() {
            System.out.println("Cart checkout...");
        }

        public void addItem(String item) {
            getItems().add(item);
            System.out.println(item + " item added to cart");
        }

        public void removeItem(String item) {
            getItems().remove(item);
            System.out.println(item + " item removed from cart");
        }

        public ArrayList<String> getItems() {
            return items;
        }

        // The afterBegin method informs the instance that a new transaction has begun. The container invokes afterBegin immediately before it invokes the business method.
        @AfterBegin
        public void afterBegin() {
            System.out.println("A new transaction has started.");
        }

        // The container invokes the beforeCompletion method after the business method has finished but just before the transaction commits. 
        // The beforeCompletion method is the last opportunity for the session bean to roll back the transaction (by calling setRollbackOnly).
        @BeforeCompletion
        public void beforeCompletion() {
            System.out.println("A transaction is about to be committed.");
        }

        // The afterCompletion method indicates that the transaction has completed. 
        // This method has a single boolean parameter whose value is true if the transaction was committed and false if it was rolled back.
        @AfterCompletion
        public void afterCompletion(boolean committed) {
            System.out.println("a transaction commit protocol has completed, and tells the instance whether the transaction has been committed or rolled back , based on committed value : " + committed);
        }
    }

    @Singleton
    @TransactionManagement(TransactionManagementType.BEAN)
    public static class CartProcess {

        @Resource
        private UserTransaction ut;

        public void executeCartProcess() {
            try {
                Context c = new InitialContext();
                CartBean cartBean = (CartBean) c.lookup("java:global/embeddedJeeContainerTest/embeddedJeeContainerTestejb/CartBean");

                ut.begin();
                cartBean.addItem("Smart Watch");
                cartBean.addItem("iPhone");
                cartBean.addItem("Shoes");

                System.out.println("Cart Item Size : " + cartBean.getItems().size());
                ut.commit();

                cartBean.checkOut();

            } catch (NamingException ex) {
                Logger.getLogger(CartProcess.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException ex) {
                try {
                    ut.rollback();
                    Logger.getLogger(CartProcess.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalStateException | SecurityException | SystemException ex1) {
                    Logger.getLogger(CartProcess.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }

    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private CartProcess cartProcess;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            cartProcess.executeCartProcess();

            response.getWriter().println("Executed..");
            // Info: CartBean: init
            // Info: A new transaction has started.
            // Info: Smart Watch item added to cart
            // Info: iPhone item added to cart
            // Info: Shoes item added to cart
            // Info: Cart Item Size : 3
            // Info: A transaction is about to be committed.
            // Info: a transaction commit protocol has completed, and tells the instance whether the transaction has been committed or rolled back , based on committed value : true
            // Info: A new transaction has started.
            // Info: Cart checkout...
            // Info: CartBean: destroy
        }
    }

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addClasses(ServletPrincipal.class);

            final EmbeddedEjb ejb = new EmbeddedEjb(EMBEDDED_JEE_TEST_APP_NAME);
            ejb.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            ejb.addClasses(CartBean.class, CartProcess.class);

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
