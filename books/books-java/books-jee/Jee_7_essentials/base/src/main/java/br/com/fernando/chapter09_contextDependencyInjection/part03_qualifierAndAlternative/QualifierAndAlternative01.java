package br.com.fernando.chapter09_contextDependencyInjection.part03_qualifierAndAlternative;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Qualifier;
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

public class QualifierAndAlternative01 {

    // ------------------------------------------------------------------------------------------------------------------------------
    // Qualifier allows you to uniquely specify a bean to be injected among its multiple implementations.
    // For example, this code declares a new qualifier, @Fancy:
    @Qualifier
    @Retention(RUNTIME)
    @Target({ METHOD, FIELD, PARAMETER, TYPE })
    public static @interface Fancy {
    }

    public static interface Greeting {

        public String greet(String name);
    }

    // This defines a new implementation of the Greeting interface:
    @Fancy
    public static class FancyGreeting implements Greeting {

        public String greet(String name) {
            return "Nice to meet you, hello" + name;
        }
    }

    @Alternative
    public static class SimpleGreeting implements Greeting {

        @Override
        public String greet(String name) {
            return "Hi " + name;
        }
    }

    // @Default qualifier on all beans without an explicit qualifier, except @Named
    @Default
    public static class DefaultGreeting implements Greeting {

        @Override
        public String greet(String name) {
            return "Good morning " + name;
        }
    }

    @Stateless
    public static class Service01 {

        @Inject
        @Fancy // and injects it in the GreetingService by specifying @Fancy as the qualifer:
        private Greeting greetingFancy; // will inject the FancyGreeting implementation.

        // Using the SimpleGreeting and FancyGreeting implementations defined earlier, the injection points are explained as follows:
        @Inject
        private Greeting greeting01;

        @Inject
        @Default
        private Greeting greeting02;

        @Inject
        @Any // Default qualifier on all beans except @New
        @Default
        private Greeting greeting03;
        // The three injection points are equivalent, as each bean has @Default and @Any (except for @New) qualifiers and specifying them
        // does not provide any additional information. The DefaultGreeting bean is injected in each statement.

        public void execute() {

            System.out.println(greeting01.greet("Paul"));
            System.out.println(greeting02.greet("Paul"));
            System.out.println(greeting03.greet("Paul"));

            System.out.println(greetingFancy.greet("Paul"));
        }
    }

    //-------------------------------------------------------------------------------------------------------------------
    @Qualifier
    @Retention(RUNTIME)
    @Target({ METHOD, FIELD, PARAMETER, TYPE })
    public static @interface MasterFancy {
    }

    public static interface MasterGreeting {
        public String greet(String name);
    }

    // The beans marked with @Alternative are unavailable for injection, lookup, or EL resolution.
    @Alternative
    public static class MasterSimpleGreeting implements MasterGreeting {

        @Override
        public String greet(String name) {
            return "Master Hi " + name;
        }
    }

    // @MasterFancy overwrite the beans.xml, it'll be injected
    @Alternative
    public static class MasterFancyGreeting implements MasterGreeting {

        @Override
        public String greet(String name) {
            return "Master Nice to meet you, " + name;
        }
    }

    @Default
    public static class MasterDefaultGreeting implements MasterGreeting {

        @Override
        public String greet(String name) {
            return "Good morning " + name;
        }
    }

    // @Alternative allows us to package multiple implementations of a bean with the same qualifiers in the .war file and selectively
    // enable them by changing the deployment descriptor based upon the environment.
    // For example, this can allow you to target separate beans for injection in development, testing, and production environments
    // by enabling the classes in beans.xml. This provides deployment-type polymorphism.    
    @Stateless
    public static class Service02 {
        // Now the following injection will give an error about unresolved dependency:
        @Inject
        private MasterGreeting masterGreeting;
        // because both the beans are disabled for injection. We can resolve this error by explicitly enabling
        // one of the beans in beans.xml 
        // (please look at src/main/resources/chapter09_contextDependencyInjection/part03_qualifierAndAlternative/beans.xml)

        public void execute() {

            System.out.println(masterGreeting.greet("Mary")); // will inject the MasterFancyGreeting instance because of beans.xml
        }
    }

    // ----------------------------------------------------------------------------------------
    // This removes any direct dependency to any particular implementation of the interface.
    // Qualifiers may take parameters for further discrimination. Multiple qualifiers may be specified at an injection point.
    public static enum AutoType {
        Bmw,
        Ford,
        Honda
    }

    @Qualifier
    @Retention(RUNTIME)
    @Target({ METHOD, TYPE, PARAMETER, FIELD })
    public static @interface Auto {

        AutoType type() default AutoType.Bmw;
    }

    public static interface AutoService {

        String getService();
    }

    @Auto(type = AutoType.Bmw)
    public static class BMWAutoService implements AutoService {

        @Override
        public String getService() {
            return "You chose BMW auto service";
        }
    }

    @Auto(type = AutoType.Ford)
    public static class FordAutoService implements AutoService {

        @Override
        public String getService() {
            return "You chose Ford auto service";
        }
    }

    @Auto(type = AutoType.Honda)
    public static class HondaAutoService implements AutoService {

        @Override
        public String getService() {
            return "You chose Honda auto service";
        }
    }

    @Stateless
    public static class Service03 {

        @Inject
        @Auto(type = AutoType.Bmw)
        private AutoService bmwAutoService;

        @Inject
        @Auto(type = AutoType.Honda)
        private AutoService hondaAutoService;

        @Inject
        @Auto(type = AutoType.Ford)
        private AutoService fordAutoService;

        public void execute() {
            System.out.println(bmwAutoService.getService());
            System.out.println(hondaAutoService.getService());
            System.out.println(fordAutoService.getService());
        }
    }

    // ==========================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private Service01 service01;

        @EJB
        private Service02 service02;

        @EJB
        private Service03 service03;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            service01.execute();
            service02.execute();
            service03.execute();
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
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter09_contextDependencyInjection/part03_qualifierAndAlternative/beans.xml"));
            // war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));

            war.addClasses( //
                    ServletTest.class, // 
                    Service01.class, Fancy.class, Greeting.class, FancyGreeting.class, SimpleGreeting.class, DefaultGreeting.class, //
                    Service02.class, MasterFancy.class, MasterGreeting.class, MasterFancyGreeting.class, MasterSimpleGreeting.class, MasterDefaultGreeting.class, Service03.class, Auto.class, AutoType.class, AutoService.class, BMWAutoService.class, FordAutoService.class, HondaAutoService.class //
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
