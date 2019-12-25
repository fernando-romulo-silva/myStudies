package br.com.fernando.chapter09_contextDependencyInjection.part07_scopesAndContexts;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class ScopesAndContexts {

    // https://stackoverflow.com/questions/7031885/how-to-choose-the-right-bean-scope
    //
    // A bean is said to be in a scope and is associated with a context.
    //
    // The associated context manages the life cycle and visibility of all beans in that scope.
    //
    // A bean is created once per scope and then reused. When a bean is requested in a particular scope, a new instance is created if it does not exist already.
    //
    // If it does exist, that instance is returned instead.
    //
    // The runtime makes sure the bean in the right scope is created, if required; the client does not have to be scope-aware.
    //
    // -------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // # javax.faces.flow.@ViewScoped - View scoped beans have the same life time as the view which initially referenced them.
    // This means that their scope is shorter that session, but greater than request.
    // As soon as a view references a view scoped bean, the CDI container will create a new bean instance and reuse it across the view life time.
    //
    // http://localhost:18080/embeddedJeeContainerTest/viewScoped.xhtml
    //
    @Named
    @ViewScoped
    public static class TestViewScopedBean implements Serializable {

        private static final long serialVersionUID = 1L;

        private List<String> items;

        private String item;

        @PostConstruct
        private void init() {
            items = new ArrayList<>();
            items.add("Item 1");
            items.add("Item 2");
            items.add("Item 3");
        }

        public void addItem() {
            if (item != null && !item.isEmpty()) {
                items.add(item);
                item = null;
            }
        }

        public String getItem() {
            return item;
        }

        public void setItem(final String item) {
            this.item = item;
        }

        public List<String> getItems() {
            return items;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // # javax.faces.view.@FlowScoped - These beans are backing beans for JSF Faces Flows.
    // With JSF 2.2, the JavaServer Faces technology allows you to create a set of pages with the new scope FlowScoped,
    // that scope is greater than request scope and less than session scope.
    //
    // http://localhost:18080/embeddedJeeContainerTest/flowScoped.xhtml
    //
    @Named
    @FlowScoped("order")
    public static class OrderBean implements Serializable {

        private static final long serialVersionUID = 1L;

        private int itemCount;

        private String address;

        public int getItemCount() {
            return itemCount;
        }

        public void setItemCount(final int itemCount) {
            this.itemCount = itemCount;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(final String address) {
            this.address = address;
        }

        public String getReturnValue() {
            return "/flowScoped";
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // # javax.enterprise.context.@RequestScoped - A bean is scoped to a request.
    // The bean is available during a single request and destroyed when the request is complete.
    //
    // The Soup class is an injectable POJO, defined as @RequestScoped.
    // This means that an instance will be created only once for every request and will be shared by all the beans injecting it.
    @RequestScoped
    public static class Soup {

        private String name = "Soup of the day";

        @PostConstruct
        public void afterCreate() {
            System.out.println("Soup created");
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }

    // The Chef class is a simple session bean with an injected Soup field.
    @Stateless
    public static class Chef {

        @Inject
        private Soup soup;

        // Normally, the soup parameter would be passed as a prepareSoup() argument, but for the need of this example it's passed by the request context.
        public Soup prepareSoup() {
            return soup;
        }
    }

    @Stateless
    public static class Waiter {

        // A Soup insance will be created in this method and will be shared throughout the request with the Chef bean.
        @Inject
        private Soup soup;

        @EJB
        private Chef chef;

        // The Waiter session bean receives a request from the test class via the orderSoup() method.
        public String orderSoup(final String name) {
            soup.setName(name);
            // The method passes the request to the Chef bean.
            // It then returns the name of the soup to the test class.
            return chef.prepareSoup().getName();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // # javax.enterprise.context.@SessionScoped - A bean is scoped to a session.
    // The bean is shared between all requests that occur in the same HTTP session, holds state throughout the session, and is
    // destroyed when the HTTP session times out or is invalidated.
    //
    // http://localhost:18080/embeddedJeeContainerTest/sessionScoped.xhtml
    //
    @Named
    @SessionScoped
    public static class CountBean implements Serializable {

        private static final long serialVersionUID = 1L;

        private int count;

        private Map<String, Object> sessionMap;

        @Inject
        private InitBean initBean;

        @PostConstruct
        public void init() {
            System.out.println("CountBean#Initializing counter with @PostConstruct ...");
            count = initBean.getInit();

            // JSF 2.0-2.2
            final FacesContext context = FacesContext.getCurrentInstance();
            sessionMap = context.getExternalContext().getSessionMap();

            sessionMap.put("id", 1L);
        }

        public void countActionVoid() {
            System.out.println("CountBean#countActionVoid() - Increasing counter ...");
            count++;
        }

        public String countActionAndForward() {
            System.out.println("CountBean#countActionAndForward() - Increasing counter ...");
            count++;
            return "sessionScoped";
        }

        public String countActionAndRedirect() {
            System.out.println("CountBean#countActionAndRedirect() - Increasing counter ...");
            count++;
            return "sessionScoped?faces-redirect=true;";
        }

        /*
         * public void setInitBean(InitBean initBean) { this.initBean = initBean; }
         */

        public int getCount() {
            return count;
        }

        public void setCount(final int count) {
            this.count = count;
        }
    }

    @Named
    @SessionScoped
    public static class InitBean implements Serializable {

        private static final long serialVersionUID = 1L;

        private int init;

        public InitBean() {
            init = 5;
        }

        public int getInit() {
            return init;
        }

        public void setInit(final int init) {
            this.init = init;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // # javax.enterprise.context.@ConversationScoped - A bean is scoped to a conversation and is of two types: transient or long-running.
    // By default, a bean in this scope is transient, is created with a JSF request, and is destroyed at the end of the request.
    // A transient conversation can be converted to a long-running one via Conversation.begin.
    // This long-running conversation can be ended via Conversation.end.
    // Multiple parallel conversations can run within a session, each uniquely identified by a string-valued identifier that is either set by
    // the application or generated by the container.
    // This allows multiple tabs in a browser to hold state corresponding to a conversation, unike session cookies, which are shared across tabs.
    //
    // http://localhost:18080/embeddedJeeContainerTest/conversationScoped01.xhtml
    //

    @Named
    @ConversationScoped
    public static class ConversationBean implements Serializable {

        private static final long serialVersionUID = 1L;

        @Inject
        private Conversation conversation;

        private int counter;

        // Will only be called once
        // during bean initialization
        @PostConstruct
        public void init() {
            counter = 0;
            System.out.println("ConversationBean.init");
        }

        public void initConversation() {
            if (!FacesContext.getCurrentInstance().isPostback() && conversation.isTransient()) {

                conversation.begin();
            }
        }

        public void increment() {
            counter++;
        }

        public String handleFirstStepSubmit() {
            return "conversationScoped02?faces-redirect=true";
        }

        public String endConversation() {
            if (!conversation.isTransient()) {
                conversation.end();
            }
            return "conversationScoped01?faces-redirect=true";
        }

        public int getCounter() {
            return counter;
        }

        public void setCounter(int counter) {
            this.counter = counter;
        }

        public Conversation getConversation() {
            return conversation;
        }

    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // # javax.enterprise.context.@ApplicationScoped - A bean is scoped to an application.
    // The bean is created when the application is started, holds state throughout the application, and is destroyed when the application is shut down.
    //
    public static class User {

        private final String name;

        private final String version;

        public User(final String name, final String version) {
            super();
            this.name = name;
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }

        @Override
        public String toString() {
            return "name: " + name + " version: " + version;
        }
    }

    @ApplicationScoped
    public static class ProvisioningDataForApplicationLifecycle {

        private final Map<String, User> users = new HashMap<>(); // + getter

        public void init(@Observes @Initialized(ApplicationScoped.class) final Object init) {
            users.put("cdi", new User("cdi", "1.1"));
            users.put("deltaspike", new User("deltaspike", "1.3"));
        }

        public void destroy(@Observes @Destroyed(ApplicationScoped.class) final Object init) {

            System.out.println(users); // when you shutdown the application

            users.clear();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // # javax.enterprise.context.@Dependent - The bean inherits the scope of the bean that injected it.
    // This is the default scope of the bean that does not explicitly declare a scope.
    //
    // -------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // A contextual reference to the bean is not a direct reference to the bean (unless it is in @Dependent scope).
    //
    // Instead, it is a client proxy object.
    //
    // This client proxy is responsible for ensuring that the bean instance that receives a method invocation is the instance that is associated with the current context.
    //
    // This allows you to invoke the bean in the current context instead of using a stale reference.
    //
    // If the bean is in @Dependent scope, then the client holds a direct reference to its instance. A new instance of the bean is bound to the life cycle
    // of the newly created object.
    // A bean in @Dependent scope is never shared between multiple injection points.

    // ==========================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private Waiter waiter;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            waiter.orderSoup("Tomato Soup");
        }
    }

    // https://turngeek.github.io/javaeeinaday/chapter/7-one-to-rule-them-all-cdi/

    //
    //
    // You can define a new scope using the extensible context model (@Contextual, @CreationalContext, and @Context interfaces), but that is generally
    // not required by an application developer.

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);

            war.addWebResourceFiles("src/main/resources/chapter09_contextDependencyInjection/part07_scopesAndContexts/order", "order");
            war.addWebResourceFiles(EmbeddedResource.add("flowScoped.xhtml", "src/main/resources/chapter09_contextDependencyInjection/part07_scopesAndContexts/flowScoped.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("sessionScoped.xhtml", "src/main/resources/chapter09_contextDependencyInjection/part07_scopesAndContexts/sessionScoped.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("viewScoped.xhtml", "src/main/resources/chapter09_contextDependencyInjection/part07_scopesAndContexts/viewScoped.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("conversationScoped01.xhtml", "src/main/resources/chapter09_contextDependencyInjection/part07_scopesAndContexts/conversationScoped01.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("conversationScoped02.xhtml", "src/main/resources/chapter09_contextDependencyInjection/part07_scopesAndContexts/conversationScoped02.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("conversationScoped03.xhtml", "src/main/resources/chapter09_contextDependencyInjection/part07_scopesAndContexts/conversationScoped03.xhtml"));

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter09_contextDependencyInjection/part07_scopesAndContexts/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("faces-config.xml", "src/main/resources/faces-config.xml"));

            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));

            war.addClasses( //
                    ServletTest.class, //
                    TestViewScopedBean.class, //
                    OrderBean.class, //
                    Soup.class, Chef.class, Waiter.class, //
                    CountBean.class, InitBean.class, //
                    ConversationBean.class, //
                    User.class, ProvisioningDataForApplicationLifecycle.class //
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
