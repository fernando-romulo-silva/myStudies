package br.com.fernando.chapter09_contextDependencyInjection.part01_discoveryBeans;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;

import javax.enterprise.inject.Vetoed;
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

import br.com.fernando.chapter09_contextDependencyInjection.part01_discoveryBeans.exclude01.ExcludeBean01;
import br.com.fernando.chapter09_contextDependencyInjection.part01_discoveryBeans.exclude02.ExcludeBean02;
import br.com.fernando.chapter09_contextDependencyInjection.part01_discoveryBeans.exclude02.sub_exclude01.ExcludeBean03;
import br.com.fernando.chapter09_contextDependencyInjection.part01_discoveryBeans.exclude02.sub_exclude02.ExcludeBean04;

public class DiscoveryBeans {

    // Bean classes are deployed in bean archives.
    //
    // all -> All types in the archive are considered for injection.
    //
    // annotated -> Only types with bean-defining annotations will be considered for injection.
    //
    // none -> Disable CDI.

    // A bean archive that does not contain beans.xml but contains one or more bean classes with a bean-defining annotation,
    // or one or more session beans, is considered an implicit bean archive.
    //
    // A bean with a declared scope type is said to have a bean-defining annotation.
    //
    // A version number of 1.1 (or later), with the bean-discovery-mode of all (look at chapter09_contextDependencyInjection/beans01.xml)
    // A bean archive that contains a beans.xml file with version 1.1 (or later) must specify the bean-discovey-mode attribute.
    // The default value for the attribute is annotated.
    //
    // It must be present in the following locations:
    //
    // The WEB-INF or WEB-INF/classes/META-INF directory for web applications.
    //
    // The META-INF directory for EJB modules or JAR files.
    //
    // The beans.xml can define exclude filters to exclude beans and package names from discovery.
    //
    // You define these by using <exclude> elements as children of the <scan> element (look at chapter09_contextDependencyInjection/beans02.xml)
    //
    //
    // You can prevent a bean from injection by adding this annotation:
    // In this code, the SimpleGreeting bean is not considered for injection.
    @Vetoed
    public static class SomeBean {

        public String doSomething() {
            return "something";
        }
    }
    //
    // All beans in a package may be prevented from injection (br.com.fernando.chapter09_contextDependencyInjection.part01_discoveryBeans.exclude02.sub_exclude02.package-info.java):
    //
    // @Vetoed
    // package br.com.fernando.chapter09_contextDependencyInjection.part01_discoveryBeans.exclude02.sub_exclude02;
    //
    // import javax.enterprise.inject.Vetoed;
    //
    // This code in package-info.java in the br.com.fernando.chapter09_contextDependencyInjection.part01_discoveryBeans.exclude02.sub_exclude02 package will prevent all beans from this package from injection.
    //
    // Java EE components, such as stateless EJBs or JAX-RS resource endpoints, can be marked with @Vetoed to prevent them from being considered beans.

    // ==========================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

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

            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter09_contextDependencyInjection/part01_discoveryBeans/beans01.xml"));
            war.addClasses(ServletTest.class, SomeBean.class, ExcludeBean01.class, ExcludeBean04.class, ExcludeBean03.class, ExcludeBean02.class);
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
