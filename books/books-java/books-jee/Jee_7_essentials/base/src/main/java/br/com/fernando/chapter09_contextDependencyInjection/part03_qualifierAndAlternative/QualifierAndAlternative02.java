package br.com.fernando.chapter09_contextDependencyInjection.part03_qualifierAndAlternative;

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

public class QualifierAndAlternative02 {

    public static interface AutoService {

        String getService();
    }

    // @Named annotation is used for giving names for classes which implements the interface, and it is optional.
    // String-based qualifier, required for usage in Expression Language
    //
    // Notes:
    //
    // 1 - @Named annotation is commonly used if there are more than one implementation for an interface.
    // Thus, it provides to give and inject by their names.
    //
    // 2 - If there is only one implementation of an interface and @Named annotation is used, then the name of the bean
    // is determined as camelCase style of class name.
    //
    // 3 - We can use @Default and @Alternative annotations instead of giving names to them.
    //
    // 4 - If there is only one implementation of an interface, compiler will inject it as the default one.
    // So, there is no need to use @Named, @Default or @Alternative annotations.
    //
    @Named("bmwAutoService")
    public static class BMWAutoService implements AutoService {

        @Override
        public String getService() {
            return "You chose BMW auto service";
        }
    }

    @Named("fordAutoService")
    public static class FordAutoService implements AutoService {

        @Override
        public String getService() {
            return "You chose Ford auto service";
        }
    }

    @Named("hondaAutoService")
    public static class HondaAutoService implements AutoService {

        @Override
        public String getService() {
            return "You chose Honda auto service";
        }
    }

    @Stateless
    public static class GreetingService02 {

        @Inject
        @Named("bmwAutoService")
        private AutoService bmwAutoService;

        @Inject
        @Named("hondaAutoService")
        private AutoService hondaAutoService;

        @Inject
        @Named("fordAutoService")
        private AutoService fordAutoService;

        public void carService() {
            System.out.println(bmwAutoService.getService());
            System.out.println(hondaAutoService.getService());
            System.out.println(fordAutoService.getService());
        }
    }

    // The use of @Named as an injection point qualifier is not recommended, except in the case of 
    // integration with legacy code that uses string-based names to identify beans.
    //
    // ==========================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private GreetingService02 service02;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            service02.carService();
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

            war.addClasses( //
                    ServletTest.class, GreetingService02.class, //
                    AutoService.class, BMWAutoService.class, FordAutoService.class, HondaAutoService.class //
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
