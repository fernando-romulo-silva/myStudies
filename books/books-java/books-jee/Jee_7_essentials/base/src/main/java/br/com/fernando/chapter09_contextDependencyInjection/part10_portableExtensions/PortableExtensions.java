package br.com.fernando.chapter09_contextDependencyInjection.part10_portableExtensions;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
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
import org.myembedded.pack.EmbeddedJar;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class PortableExtensions {

    // ==========================================================================================================================================================
    // CDI exposes an Service Provider Interface (SPI) allowing portable extensions to extend the functionality of the container easily.
    // A portable extension may integrate with the container by:
    //
    // Providing its own beans, interceptors, and decorators to the container;
    //
    // Injecting dependencies into its own objects using the dependency injection service;
    //
    // Providing a contextual implementation for a custom scope
    //
    // Augmenting or overriding the annotation-based metadata with metadata from some other source
    //
    // The extension needs to implement the Extension marker interface.
    public static class MyExtension implements Extension {

        // The bean can listen to a variety of container life-cycle events
        //
        // ProcessAnnotatedType: For each Java class or interface discovered in the application, before the annotations are read
        //
        // BeanManager provides operations for obtaining contextual references for beans, along with many other operations of use to portable extensions.
        public <T> void processAnnotatedType(@Observes final ProcessAnnotatedType<T> pat, final BeanManager bm) {

            final Logger logger = Logger.getAnonymousLogger();

            logger.log(Level.INFO, "CDI Extension Processing Annotation -> {0}", pat.getAnnotatedType().getJavaClass().getName());
        }

        // BeforeBeanDiscovery: Before the bean discovery process begins
        //
        // AfterBeanDiscovery: After the bean discovery process is complete
        //
        // AfterDeploymentValidation: After no deployment problems are found and before contexts are created and requests processed
        //
        // BeforeShutdown: After all requests are finished processing and all contexts destroyed
        //
        // ProcessInjectionTarget: For every Java EE component class supporting injection
        //
        // ProcessProducer: For each producer method or field of each enabled bean
    }

    // ==========================================================================================================================================================

    public static interface Greeting {

        public String greet(String name);
    }

    public static class SimpleGreeting implements Greeting {

        // BeanManager is also available for field injection and can be looked up by the name java:comp/BeanManager.
        @Resource(lookup = "java:comp/BeanManager")
        private BeanManager beanManager;

        @Override
        public String greet(final String name) {

            System.out.println(beanManager.getBeans("greeting"));

            return "Hello " + name;
        }
    }

    @WebServlet(urlPatterns = { "/TestServlet" })
    public static class TestServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private Greeting greeting;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            System.out.println(greeting.greet("Duke"));
        }
    }

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedJar jar = new EmbeddedJar("cdiExtensions");
            jar.addClasses(MyExtension.class);

            // You then need to register this extension using the service provider mechanism by creating a file named
            // META-INF/services/javax.enterprise.inject.spi.Extension.
            //
            // This file contains the fully qualified name of the class implementing the extension
            jar.addMetaInfFiles(EmbeddedResource.add("javax.enterprise.inject.spi.Extension", "src/main/resources/chapter09_contextDependencyInjection/part10_portableExtensions/javax.enterprise.inject.spi.Extension", "services"));
            jar.exportToFile(APP_FILE_TARGET);

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addLibs(APP_FILE_TARGET + "/cdiExtensions.jar");

            // WEB-INF
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));

            war.addClasses( //
                    TestServlet.class, //
                    SimpleGreeting.class, Greeting.class //
            );

            final File appFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, appFile.getAbsolutePath());

            // ------------ Client -----------------------------------------------------------
            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/TestServlet"));

            System.out.println(response);

            Thread.sleep(5000); // 5 seconds only for the server finishs its jobs...

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
