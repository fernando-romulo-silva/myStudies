package br.com.fernando.chapter08_enterpriseJavaBeans.part01_statefulSessionBeans;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
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

public class StatefulSessionBeans01 {

    // =======================================================================================================================================================
    // A stateful session bean contains conversational state for a specific client.
    // The state is stored in the session bean instance's field values, its associated interceptors and their instance field values, and all the objects and their instances'
    // field values, reachable by following Java object references.
    //
    //
    // This is a POJO marked with the @Stateful annotation. That's all it takes to convert a POJO to a stateful session bean.
    // All public methods of the bean may be invoked by a client.
    @Stateful
    public static class CartBean implements Cart {

        private List<String> items;

        public CartBean() {
            items = new ArrayList<>();
        }

        @Override
        public void addItem(String item) {
            items.add(item);
        }

        @Override
        public void removeItem(String item) {
            items.remove(item);
        }

        @Override
        public void purchase() {
            // . . .
            System.out.println("Purchase!");
        }

        // The method remove is marked with the @Remove annotation.
        // A client may remove a stateful session bean by invoking the remove method.
        // Calling this method will result in the container calling the method marked with the @PreDestroy annotation.
        // Removing a stateful session bean means that the instance state specific to that client is gone.
        @Remove
        public void remove() {
            items = null;
        }

        @Override
        public List<String> getItems() {
            return items;
        }
    }

    // This style of bean declaration is called as a no-interface view.
    // Such a bean is only locally accessible to clients packaged in the same archive. If the bean needs to be remotely accessible, it must define a
    // separate business interface annotated with @Remote:
    @Remote
    public static interface Cart {

        public void addItem(String item);

        public void removeItem(String item);

        public void purchase();

        public List<String> getItems();
    }

    // EJB 3.2 relaxed the default rules for designating implementing interfaces as local or remote.
    // The bean class must implement the interface or the interface must be designated as a local or remote business interface of the bean by means of
    // the Local or Remote annotation or in the deployment descriptor.
    //
    // In this code, the bean NewCartBean only exposes one remote interface, Cart. Payment is local
    // If the bean is implementing two interfaces:
    //
    // EJB 3.2 adds the capability to opt out of passivation.
    // For example, a stateful session bean may contain nonserializable attributes, which would lead to runtime exceptions during
    // passivation, or passivation and acivation of such instances may cause degradation of an application performance:
    @Stateful(passivationCapable = false) // In this code, the stateful EJB will not be passivated
    public static class NewCartBean implements Cart, Payment {

        private List<String> items;

        public NewCartBean() {
            items = new ArrayList<>();
        }

        // The PostConstruct and PreDestroy lifecycle callback methods are available for stateful session beans.
        @PostConstruct
        public void postConstruct() {
            System.out.println("PostConstruct!");
        }

        @PreDestroy
        public void preDestroy() {
            System.out.println("PreDestroy!");
        }

        // The @PrePassivate life-cycle callback method is invoked to clean up resources before the bean is passivated, and the
        // PostActivate callback method is invoked to restore the resources
        @PrePassivate
        public void prePassivate() {
            System.out.println("PrePassivate!");
        }

        @PostActivate
        public void postActivate() {
            System.out.println("PrePassivate!");
        }

        @Override
        public void addItem(String item) {
            items.add(item);
        }

        @Override
        public void removeItem(String item) {
            items.remove(item);
        }

        @Override
        public void purchase() {
            System.out.println("Purchase!");
        }

        @Override
        public List<String> getItems() {
            return items;
        }

        // It won't be expose as a remote
        @Override
        public void pay() {
            System.out.println("Pay!");
        }
    }

    //
    @Local
    public static interface Payment {

        void pay();

    }

    // =======================================================================================================================================================
    @Stateful
    public static class ReentrantStatefulBean {

        @Resource
        private SessionContext sessionConext;

        public void initialMethod() {
            sessionConext.getBusinessObject(ReentrantStatefulBean.class).reentrantMehthod();
        }

        public void reentrantMehthod() {

        }
    }

    // =======================================================================================================================================================
    @WebServlet(urlPatterns = { "/ServletPrincipal" })
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        // Cannot be injected using @Inject
        // Now the bean is injected via the interface:
        @EJB(beanName = "CartBean")
        // We have two Cart's implementations, we need specific someone 
        private Cart bean;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");

            try (final PrintWriter out = response.getWriter()) {

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Adding/Removing items from Stateful Bean (with Interface)</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Adding/Removing items from Stateful Bean (with Interface)</h1>");
                out.println("<h2>Adding items</h2>");

                // A client of this stateful session bean can access this bean:
                bean.addItem("apple");
                bean.addItem("banana");
                bean.addItem("mango");
                bean.addItem("kiwi");

                bean.addItem("passion fruit");
                out.println("added");
                out.println("<h2>Listing items</h2>");
                out.println(bean.getItems());
                out.println("<h2>Removing item</h2>");
                bean.removeItem("banana");
                out.println("removed");
                out.println("<h2>Listing items</h2>");
                out.println(bean.getItems());
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter14_jms/beans.xml"));
            war.addClasses(ServletPrincipal.class);

            final EmbeddedEjb ejb = new EmbeddedEjb(EMBEDDED_JEE_TEST_APP_NAME);
            ejb.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            ejb.addMetaInfFiles(EmbeddedResource.add("glassfish-ejb-jar.xml", "src/main/resources/chapter08_enterpriseJavaBeans/glassfish-ejb-jar.xml"));
            ejb.addClasses(ReentrantStatefulBean.class, Cart.class, CartBean.class, NewCartBean.class, Payment.class);

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

            final Cart cart01 = (Cart) context.lookup("java:global/embeddedJeeContainerTest/embeddedJeeContainerTestejb/CartBean");

            // must be empty, because it's another session
            System.out.println(cart01.getItems());

            // given
            final List<String> items = Arrays.asList("apple", "banana", "mango", "kiwi", "passion fruit");
            for (final String item : items) {
                cart01.addItem(item);
            }

            cart01.removeItem("banana");

            // then
            System.out.println(cart01.getItems());

            final Cart cart02 = (Cart) context.lookup("java:global/embeddedJeeContainerTest/embeddedJeeContainerTestejb/CartBean");

            // must be empty, because it's another session
            System.out.println(cart02.getItems());

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
