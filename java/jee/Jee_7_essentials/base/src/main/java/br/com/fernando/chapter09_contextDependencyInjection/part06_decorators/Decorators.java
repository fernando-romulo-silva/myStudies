package br.com.fernando.chapter09_contextDependencyInjection.part06_decorators;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
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

public class Decorators {

    // ---------------------------------------------------------------------------------------------------------------------------------
    // Decorators are used to implement business concerns.
    //
    // Interceptors are unaware of the business semantics of the invoked bean and thus are more widely applicable;
    //
    // Decorators complement interceptors, as they are business-semantics-aware and applicable to beans of a particular type.
    //
    // In order of execution, the interceptors for a method are called BEFORE the decorators that apply to the method.
    //
    // ---------------------------------------------------------------------------------------------------------------------------------
    public static interface Greeting {

        String greet(String name);
    }

    // A decorator is a bean that implements the bean it decorates and is annotated with the @Decorator stereotype.
    //
    // The decorator class may be abstract, as it may not be implementing all methods of the bean.
    @Decorator
    public static class MySimpleDecorator implements Greeting {

        // A decorator class has a delegate injection point that is an injection point for the same type as the bean it decorates.
        //
        // The delegate injection point follows the normal rules for injection and therefore must be an injected field,
        // initializer method parameter, or bean constructor method parameter:
        @Inject
        @Delegate
        @Any
        // This delegate injection point specifies that the decorator is bound to all beans that implement Greeting.
        private Greeting greeting;

        @Override
        public String greet(String name) {
            return greeting.greet(name + " very much!");
        }
    }

    // By default, all decorators are disabled and need to be explicitly enabled and ordered via beans.xml.
    // You can enable a decorator by specifying it in beans.xml (chapter09_contextDependencyInjection/part06_decorators/beans.xml)
    // The order of the decorator declarations using <class> determines the decorator ordering.
    //
    // or by the @Priority annotation, along with a priority value on the decorator class:
    //
    // @Priority(Interceptor.Priority.APPLICATION + 10)
    //
    // Decorators that occur earlier in the list are called first.
    @Decorator
    public static class MyFancyDecorator implements Greeting {

        @Inject
        @Delegate
        @Any
        private Greeting greeting;

        @Override
        public String greet(String name) {
            return greeting.greet(name + " I'm glad you are here!");
        }

    }

    public static class SimpleGreeting implements Greeting {

        @Override
        public String greet(String name) {
            return "Hello " + name;
        }

    }

    public static class Service01 {

        @Inject
        private Greeting greeting;

        public void execute() {
            System.out.println(greeting.greet("Michele"));
        }
    }

    // ==========================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private Service01 service01;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            service01.execute();
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
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter09_contextDependencyInjection/part06_decorators/beans.xml"));

            war.addClasses( //
                    ServletTest.class, //
                    Service01.class, Greeting.class, MySimpleDecorator.class, MyFancyDecorator.class, SimpleGreeting.class //
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
