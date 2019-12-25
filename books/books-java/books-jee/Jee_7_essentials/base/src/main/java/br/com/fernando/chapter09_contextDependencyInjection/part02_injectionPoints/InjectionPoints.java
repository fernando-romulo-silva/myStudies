package br.com.fernando.chapter09_contextDependencyInjection.part02_injectionPoints;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class InjectionPoints {

    public static interface Greeting {

        public String greet(String name);
    }

    public static class SimpleGreeting implements Greeting {

        public String greet(String name) {
            return "Hello " + name;
        }
    }

    // You can inject a bean at a field, method, or constructor using @Inject.

    @Stateless
    public static class GreetingService01 {

        // @Inject specifies the injection point, Greeting specifies what needs to be injected,
        // and greeting is the variable that gets the injection.
        @Inject
        private Greeting greeting;

        public String greet(String name) {
            return greeting.greet(name);
        }

        public void doGreet() {
            System.out.println(greeting.greet("John"));
        }
    }

    @Stateless
    public static class GreetingService02 {

        // A bean may define one or more methods as targets of injection as well:
        private Greeting greeting;

        @Inject
        public void setGreeting(Greeting greeting) {
            this.greeting = greeting;
        }

        public String greet(String name) {
            return greeting.greet(name);
        }

        public void doGreet() {
            System.out.println(greeting.greet("John"));
        }

    }

    @Stateless
    public static class GreetingService03 {

        private final Greeting greeting;

        // Finally, a bean can have at most one constructor marked with @Inject:
        @Inject
        public GreetingService03(final Greeting greeting) {
            // This constructor may have any number of parameters, and all of them are injection points.
            // A constructor marked with @Inject need have public access.
            // This allows a bean with constructor injection to be immutable.
            this.greeting = greeting;
        }

        // All method parameters are then automatically injected.
        public void doGreet() {
            System.out.println(greeting.greet("John"));
        }
    }

    // Here is the bean initialization sequence:
    //
    // 1 - Default constructor or the one annotated with @Inject
    //
    // 2 - All fields of the bean annotated with @Inject
    //
    // 3 - All methods of the bean annotated with @Inject (the call order is not portable, though)
    //
    // 4 - The @PostConstruct method, if any

    // ==========================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private GreetingService01 service01;

        @EJB
        private GreetingService02 service02;

        @EJB
        private GreetingService03 service03;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            service01.doGreet();
            service02.doGreet();
            service03.doGreet();
        }
    }

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try ( //
                final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer(); //
        ) {

            // ------------ Packages -----------------------------------------------------------
            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));

            war.addClasses(ServletTest.class, GreetingService01.class, GreetingService02.class, GreetingService03.class, //
                    Greeting.class, SimpleGreeting.class //
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
