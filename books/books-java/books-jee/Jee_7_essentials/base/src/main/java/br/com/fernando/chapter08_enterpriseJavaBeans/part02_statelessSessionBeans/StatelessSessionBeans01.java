package br.com.fernando.chapter08_enterpriseJavaBeans.part02_statelessSessionBeans;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
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
import org.myembedded.pack.EmbeddedEar;
import org.myembedded.pack.EmbeddedEjb;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;
import org.myembedded.pack.JeeVersion;

public class StatelessSessionBeans01 {

    // ==================================================================================================================================================================
    // A stateless session bean does not contain any conversational state for a specific client. 
    // All instances of a stateless bean are equivalent, so the container can choose to delegate a client-invoked method to any available instance. 
    // 
    // Since stateless session beans do not contain any state, they don't need to be passivated.
    //
    // This is a POJO marked with the @Stateless annotation. That's all it takes to convert a POJO to a stateless session bean. 
    // All public methods of the bean may be invoked by a client.
    //
    // This style of bean declaration is called as a no-interface view. Such a bean is only locally accessible to clients packaged in the same archive. 
    @Stateless
    public static class AccountSessionBean {

        private float amount = 0;
        // As stateless beans do not store any state, the container can pool the instances, and all of them are treated equally from a client's perspective. 
        // Any instance of the bean can be used to service the client's request.

        // The PostConstruct and PreDestroy lifecycle callback methods are available for stateless session beans.
        // 
        // The PostConstruct callback method is invoked after the no-args constructor is invoked
        // and all the dependencies have been injected, and before the first business method is invoked on the bean. 
        // This method is typically where all the resources required for the bean are initialized.
        @PostConstruct
        public void postConstruct() {
            System.out.println("PostConstruct!");
        }

        // The PreDestroy life-cycle callback is called before the instance is removed by the container. 
        // This method is where all the resources acquired during PostConstruct are released.
        @PreDestroy
        public void preDestroy() {
            System.out.println("PreDestroy!");
        }

        public String withdraw(float amount) {
            this.amount -= amount;
            return "Withdrawn: " + amount;
        }

        public String deposit(float amount) {
            this.amount += amount;
            return "Deposited: " + amount;
        }

        public float getAmount() {
            return this.amount;
        }
    }

    @Stateless
    public static class AccountSessionBeanWithInterface implements Account {

        @Override
        public String withdraw(float amount) {
            return "Withdrawn: " + amount;
        }

        @Override
        public String deposit(float amount) {
            return "Deposited: " + amount;
        }
    }

    // If the bean needs to be remotely accessible, it must define a separate business interface annotated with @Remote:
    @Remote
    public static interface Account {

        public String withdraw(float amount);

        public String deposit(float amount);
    }

    // ==================================================================================================================================================================
    @WebServlet(urlPatterns = { "/TestServletWithInterface" })
    public static class TestServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private Account accountBean;

        @EJB
        private AccountSessionBean accountSessionBean;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Stateless Bean (with Interface)</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Stateless Bean (with Interface)</h1>");
            out.println("<h2>Withdraw and Deposit</h2>");
            out.println(accountBean.deposit((float) 5.0));
            out.println(accountSessionBean.withdraw((float) 5.0));
            out.println("</body>");
            out.println("</html>");
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter14_jms/beans.xml"));
            war.addClasses(TestServlet.class);

            final EmbeddedEjb ejb = new EmbeddedEjb(EMBEDDED_JEE_TEST_APP_NAME);
            ejb.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            ejb.addMetaInfFiles(EmbeddedResource.add("glassfish-ejb-jar.xml", "src/main/resources/chapter08_enterpriseJavaBeans/glassfish-ejb-jar.xml"));
            ejb.addClasses(Account.class, AccountSessionBeanWithInterface.class, AccountSessionBean.class);

            final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
            ear.addModules(war);
            ear.addModules(ejb);

            final File earFile = ear.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, earFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));

            System.out.println(response);

            final Context context = new InitialContext();
            // Context compEnv = (Context) new InitialContext().lookup("java:comp/env");
            // service = (HelloService)new InitialContext().lookup("java:comp/env/ejb/HelloService");

            final Account sut = (Account) context.lookup("java:global/embeddedJeeContainerTest/embeddedJeeContainerTestejb/AccountSessionBeanWithInterface");

            // must be empty, because it's another session
            System.out.println(sut.withdraw(5.0F));

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
