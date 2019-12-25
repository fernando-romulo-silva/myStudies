package br.com.fernando.chapter08_enterpriseJavaBeans.part07_transactions;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.BeforeCompletion;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Remove;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
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

public class Transactions04 {

    @Stateful
    @TransactionManagement(value = TransactionManagementType.CONTAINER)
    public static class CartBean {

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

        // There are two ways to roll back a container-managed transaction.
        //
        // First, if a system exception is thrown, the container will automatically roll back the transaction.
        //
        // Second, by invoking the setRollbackOnly method of the EJBContext interface, the bean method instructs the container to roll back the transaction.
        // If the bean throws an application exception, the rollback is not automatic but can be initiated by a call to setRollbackOnly.
        @Resource
        private EJBContext context;

        public void methodWithException() {
            try {

            } catch (Throwable th) {
                context.setRollbackOnly();
            }

            // When to use UserTransaction's setRollbackOnly() and UserTransaction's rollback()
            // Summary of what is legal:
            //
            // BMT:
            // EJBContext.getUserTransaction()
            // UserTransaction.begin
            // UserTransaction.commit
            // UserTransaction.rollback
            // UserTransaction.setRollbackOnly()
            // UserTransaction.getStatus()
            // 
            // CMT:
            // EJBContext.setRollbackOnly()
            // EJBContext.getRollbackOnly()
        }

        public ArrayList<String> getItems() {
            return items;
        }

        @AfterBegin
        private void afterBegin() {
            System.out.println("A new transaction has started.");
        }

        @BeforeCompletion
        private void beforeCompletion() {
            System.out.println("A transaction is about to be committed.");
        }

        @AfterCompletion
        private void afterCompletion(boolean committed) {
            System.out.println("A transaction commit protocol has completed, and tells the instance whether the transaction has been committed or rolled back , based on committed value : " + committed);
        }
    }

    @Singleton
    @TransactionManagement(TransactionManagementType.CONTAINER)
    public static class CartProcess {

        @EJB
        private CartBean cartBean;

        @TransactionAttribute(TransactionAttributeType.NEVER) // NON TRANSACTION (NEVER, SUPPORTS,
        public void executeCartProcess() {

            cartBean.addItem("Smart Watch");
            cartBean.addItem("iPhone");
            cartBean.addItem("Shoes");

            cartBean.checkOut();
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

            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
            }

            System.out.println("info: Executed");

            // Info: A new transaction has started.
            // Info: Smart Watch item added to cart
            // Info: A transaction is about to be committed.
            // Info: a transaction commit protocol has completed, and tells the instance whether the transaction has been committed or rolled back , based on committed value : true
            // Info: A new transaction has started.
            // Info: iPhone item added to cart
            // Info: A transaction is about to be committed.
            // Info: a transaction commit protocol has completed, and tells the instance whether the transaction has been committed or rolled back , based on committed value : true
            // Info: A new transaction has started.
            // Info: Shoes item added to cart
            // Info: A transaction is about to be committed.
            // Info: a transaction commit protocol has completed, and tells the instance whether the transaction has been committed or rolled back , based on committed value : true
            // Info: A new transaction has started.
            // Info: Cart checkout...
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
