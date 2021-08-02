package br.com.fernando.chapter08_enterpriseJavaBeans.part03_singletonSessionBeans;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
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

public class SingletonSessionBeans01 {

    // A singleton session bean is a session bean component that is instantiated once per application and provides easy access to shared state.
    // If the container is distributed over multiple virtual machines, each application will have one instance of the singleton for each JVM.
    // A singleton session bean is explicitly designed to be shared and supports concurrency
    //
    // The container is responsible for when to initialize a singleton bean instance.
    // However, you can optionally mark the bean for eager initialization by annotating it with @Startup:
    @Startup // If don't use this annotation, it'll be create when someone request it.
    @Singleton
    public static class MySingletonBeanManagedConcurrency {

        volatile StringBuilder builder;

        // The container now initializes all such startup-time singletons, executing the code marked in @PostConstruct,
        // before the application becomes available and any client request is serviced.
        @PostConstruct
        private void postConstruct() {
            builder = new StringBuilder();
            System.out.println("postConstruct");
        }

        public String readSomething() {
            return "current timestamp: " + new Date();
        }

        public String writeSomething(String something) {
            synchronized (builder) {
                builder.append(something);
            }
            return builder.toString() + " : " + new Date();
        }
    }

    // You can specify an explicit initialization of singleton session beans using @DependsOn:
    @Singleton
    public static class Foo {

        @PostConstruct
        private void postConstruct() {
            System.out.println("Foo.PostConstruct!");
        }
    }

    // The container ensures that Foo bean is initialized before Bar bean.
    @DependsOn("Foo")
    @Singleton
    public static class Bar {

        @PostConstruct
        private void postConstruct() {
            System.out.println("Bar.PostConstruct!");
        }

        public void doSomething() {
            System.out.println("Bar do something!");
        }
    }

    @Startup
    @Singleton
    public static class MySingleton {

        // Bean-managed concurrency requires the developer to manage concurrency using Java
        // language–level synchronization primitives such as synchronized and volatile.
        volatile StringBuilder builder;

        // A singleton bean supports PostConstruct and PreDestroy life-cycle callback methods
        @PostConstruct
        private void postConstruct() {
            System.out.println("postConstruct");
            builder = new StringBuilder();
        }

        // A singleton bean always supports concurrent access. By default, a singleton bean is marked for container-managed concurrency, but alternatively
        // may be marked for beanmanaged concurrency.
        //
        // Container-managed concurrency is based on method-level locking metadata where each method is associated
        // with either a Read (shared) or Write (exclusive) lock.
        //
        // A Read lock allows concurrent invocations of the method.
        @Lock(LockType.READ)
        public String readSomething() {
            return "current timestamp: " + new Date();
        }

        // A Write lock waits for the processing of one invocation to complete before allowing the next invocation to proceed.
        // By default, a Write lock is associated with each method of the bean.
        @Lock(LockType.WRITE)
        public String writeSomething(String something) {
            builder.append(something);
            return builder.toString() + " : " + new Date();
        }
    }

    @WebServlet(urlPatterns = { "/TestServlet" })
    public static class TestServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private MySingleton bean;

        @Inject
        private MySingletonBeanManagedConcurrency bean2;

        @EJB
        private Bar bar;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");

            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Singleton Bean</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Singleton Bean</h1>");
                out.println("<h2>Container-managed Concurrency</h2>");
                out.println(bean.readSomething() + "<br>");
                out.println(bean.writeSomething("Duke") + "<br>");
                out.println("<h2>Bean-managed Concurrency</h2>");
                out.println(bean2.readSomething() + "<br>");
                out.println(bean2.writeSomething("Duke"));
                out.println("</body>");
                out.println("</html>");
            }

            bar.doSomething();
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
            ejb.addClasses(MySingletonBeanManagedConcurrency.class, MySingleton.class, Foo.class, Bar.class);

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
