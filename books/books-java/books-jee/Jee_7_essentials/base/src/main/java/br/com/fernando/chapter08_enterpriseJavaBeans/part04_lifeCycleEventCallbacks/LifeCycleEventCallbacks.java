package br.com.fernando.chapter08_enterpriseJavaBeans.part04_lifeCycleEventCallbacks;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InterceptorBinding;
import javax.interceptor.InvocationContext;
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

public class LifeCycleEventCallbacks {

    // ==================================================================================================================================================================
    // Life-cycle callback interceptor methods may be defined for session beans and messagedriven beans.
    //
    // The @AroundConstruct, @PostConstruct, @PreDestroy, @PostActivate, and @PrePassivate annotations are used to define interceptor methods for
    // life-cycle callback events.
    //
    // An AroundConstruct life-cycle callback interceptor method may be defined on an interceptor class only.
    //
    // All other interceptor methods can be defined on an interceptor class and/or directly on the bean class.
    //
    // This callback interceptor method may be defined only on interceptor classes and/or superclasses of interceptor classes and cannot be
    // defined on the target class.
    //
    // An interceptor binding also has the java.lang.annotation.Inherited annotation, to specify that the annotation can be inherited from superclasses. 
    //
    // The @Inherited annotation also applies to custom scopes (not discussed in this tutorial) but does not apply to qualifiers.
    @Inherited
    // An interceptor binding type may declare other interceptor bindings.
    @InterceptorBinding
    @Retention(RUNTIME)
    @Target({ CONSTRUCTOR, METHOD, TYPE })
    public static @interface MyInterceptorBinding {
    }

    // Interceptor binding can be defined as:
    @MyInterceptorBinding
    @Interceptor
    @Priority(Interceptor.Priority.APPLICATION + 10)
    public static class MyInterceptor implements Serializable {

        private static final long serialVersionUID = 1L;

        @AroundInvoke
        public Object logMethodEntry(final InvocationContext invocationContext) throws Exception {
            System.out.println("Entering method: " //
                    + invocationContext.getMethod().getName() + " in class " //
                    + invocationContext.getMethod().getDeclaringClass().getName()); //

            return invocationContext.proceed();
        }
    }

    // ==================================================================================================================================================================
    // And finally the interceptor can be specified on the bean like so:
    @Dependent
    @MyInterceptorBinding // The validateConstructor method is called every time MyStatefulBean's constructor is called.
    @Stateful
    public static class MyStatefulBean {

        private List<String> list;

        @Inject
        private AnotherBean anotherbean;

        //
        public MyStatefulBean() {
            System.out.println("MyStatefulBean.ctor");
        }

        // The PostConstruct annotation is used on a method that needs to be executed after dependency injection is done to perform any initialization
        // and before the first business method invocation on the bean instance.
        //
        // This life-cycle callback interceptor method for different types of session beans occurs in the following transaction contexts:
        //
        // * For a stateful session bean, it executes in a transaction context determined by the life-cycle callback method's transaction attribute.
        //
        // * For a stateless session bean, it executes in an unspecified transaction context
        //
        // * For a singleton session bean, it executes in a transaction context determined by the bean's transaction management type and any
        // applicable transaction attribute.
        //
        // In this code, the bean's default constructor is called first, then AnotherBean is injected, and finally the postConstruct method is called
        // before any of the business methods (addItem, removeItem, items) can be called.
        //
        // The method annotated with PostConstruct is invoked even if the class does not request any resources to be injected
        // Only one method can be annotated with @PostConstruct:
        @PostConstruct
        private void postConstruct() {
            list = new ArrayList<>();
            System.out.println("MyStatefulBean.postConstruct");

            System.out.println(anotherbean.getName());
        }

        // The PreDestroy annotation is used on methods as a callback notification to signal that the instance is in the process of being removed by the container.
        // In this code, the preDestroy method is called before the instance is removed by the container.
        //
        // This life-cycle callback interceptor method for different types of session beans occurs in the following transaction contexts:
        // * For a stateless session bean, it executes in an unspecified transaction context.
        //
        // * For a stateful session bean, it executes in a transaction context determined by the life-cycle callback method's transaction attribute.
        //
        // * For a singleton session bean, it executes in a transaction context determined by the bean's transaction management type and any applicable transaction attribute.
        @PreDestroy
        private void preDestroy() {
            System.out.println("MyStatefulBean.preDestroy");
        }

        // The PrePassivate annotation can only be used on a stateful session bean.
        // This annotation designates a method to receive a callback before a stateful session bean is passivated;
        //
        // This life-cycle callback interceptor method executes in a transaction context determined by the life-cycle callback method's transaction attribute.
        //
        // These methods are ignored for stateless or singleton session beans.
        @PrePassivate
        private void prePassivate() {
            System.out.println("MyStatefulBean.prePassivate");
        }

        // The PostActivate annotation can only be used on a stateful session bean.
        // This annotation designates a method to receive a callback after a stateful session bean is activated;
        //
        // This life-cycle callback interceptor method executes in a transaction context determined by the life-cycle callback method's transaction attribute.
        //
        // These methods are ignored for stateless or singleton session beans.
        @PostActivate
        private void postActivate() {
            System.out.println("MyStatefulBean.postActivate");
        }

        // Life-cycle callback interceptor methods may throw system runtime exceptions, but not application exceptions.
        //
        // -----------------------------------------------------------
        // business methods
        public void addItem(final String item) {
            list.add(item);
            System.out.println("MyStatefulBean.addItem");
        }

        public void removeItem(final String item) {
            list.remove(item);
            System.out.println("MyStatefulBean.removeItem");
        }

        public List<String> items() {
            return list;
        }
    }

    @Stateless
    @MyInterceptorBinding
    public static class MyStatelessBean {

        public MyStatelessBean() {
            System.out.println("MyStatelessBean.ctor");
        }

        @PostConstruct
        private void postConstruct() {
            System.out.println("MyStatelessBean.postConstruct");
        }

        @PreDestroy
        private void preDestroy() {
            System.out.println("MyStatelessBean.preDestroy");
        }

        public void method1() {
            System.out.println("MyBean.method1");
        }
    }

    public static class AnotherBean {

        public String getName() {
            return "My name is AnotherBean";
        }
    }

    // ==================================================================================================================================================================
    @WebServlet(urlPatterns = { "/TestServlet" })
    public static class TestServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private MyStatefulBean stateful;

        // Something strange is happening, if I use @Inject annotation, it won't work
        //
        // To do @Inject work, I have to use the src/main/resources/chapter08_enterpriseJavaBeans/part04_lifeCycleEventCallbacks/beans.xml file.
        // <interceptors>
        // <class>br.com.fernando.chapter08_enterpriseJavaBeans.part04_lifeCycleEventCallbacks.LifeCycleEventCallbacks01$MyAroundConstructInterceptor</class>
        // </interceptors>
        @Inject
        private MyStatelessBean stateless;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            response.setContentType("text/html;charset=UTF-8");

            try (PrintWriter out = response.getWriter()) {

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet TestServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Stateful bean: adding 3 items, removing 1, adding a new one</h1>");

                stateful.addItem("red");
                stateful.addItem("yellow");
                stateful.addItem("green");
                stateful.removeItem("yellow");
                stateful.addItem("blue");

                for (final String s : stateful.items()) {
                    out.println(s + "<br>");
                }

                out.println("<h1>Stateless bean: calling a method</h1>");
                stateless.method1();
                out.println("<p><p>Look for output in server.log");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter08_enterpriseJavaBeans/part04_lifeCycleEventCallbacks/beans.xml"));
            war.addClasses(TestServlet.class);

            final EmbeddedEjb ejb = new EmbeddedEjb(EMBEDDED_JEE_TEST_APP_NAME);
            ejb.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter08_enterpriseJavaBeans/part04_lifeCycleEventCallbacks/beans.xml"));
            ejb.addMetaInfFiles(EmbeddedResource.add("glassfish-ejb-jar.xml", "src/main/resources/chapter08_enterpriseJavaBeans/glassfish-ejb-jar.xml"));
            ejb.addClasses(MyInterceptorBinding.class, AnotherBean.class, MyInterceptor.class, MyStatefulBean.class, MyStatelessBean.class);

            final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
            ear.addModules(war);
            ear.addModules(ejb);

            final File earFile = ear.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, earFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/TestServlet"));

            System.out.println(response);

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
