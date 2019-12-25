package br.com.fernando.chapter02_servlet.part08_resourcePackaging;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedJar;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class ResourcePackaging {

    // ==================================================================================================================================================================
    @WebServlet("/servletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            try (final PrintWriter out = response.getWriter()) {

                out.println("<html><head>");
                out.println("<title>Servlet Test!</title>");
                out.println("</head><body>");
                out.println("<h1>Servlet Test!</h1>");
                out.println("</body></html>");

                final ServicePrincipal service = new ServicePrincipal();
                System.out.println(service.doSomething());

            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ServicePrincipal {

        public String doSomething() {
            return "Done!";
        }

    }

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            // You can access resources bundled in the .war file using the ServletContext.getResource and ServletContext.getResourceAsStream methods.
            // The resource path is specified as a string with a leading. 
            //
            // This path is resolved relative to the root of the context or relative to the META-INF/resources directory of the JAR files bundled in the WEB-INF/lib directory

            // Normally, if stylesheets and image directories need to be accessed in the servlet, you need to manually extract them in the root of the 
            // web application.	    

            // application.war
            // -> WEB-INF
            // ----> web.xml
            // ----> lib
            // -------> libJar.jar
            //
            // ----> classes
            // -------> br.com.fernando.chapter02_servlet.part08_resourcePackaging.ResourcePackaging$ServletPrincipal.class
            //
            // -> index.jsp
            // -> pages
            // ----> login.jsp
            // 
            // -> images
            //-----> principal.jpg
            //
            // Servlet 3.0 allows the library to package the resources in the META-INF/resources directory:
            //
            // libJar.jar
            // -> br.com.fernando.chapter02_servlet.part08_resourcePackaging.ResourcePackaging$ServicePrincipal.class
            // -> META-INF
            // -----> resources
            // ---------> css
            // -------------> common.css
            // ---------> images
            // -------------> header.png
            // -------------> footer.png
            //
            // In this case, the resources need not be extracted in the root of the application and can be accessed directly instead. 
            // This allows resources from third-party JARs bundled in META-INF/resources to be accessed directly instead of manually extracted.

            final EmbeddedJar libJar = new EmbeddedJar("libJar");
            libJar.addClasses(ServicePrincipal.class);
            libJar.addMetaInfFiles(EmbeddedResource.add("common.css", "src/main/resources/chapter02_servlet/part08_resourcePackaging/common.css", "resources/css"));
            libJar.addMetaInfFiles(EmbeddedResource.add("header.png", "src/main/resources/chapter02_servlet/part08_resourcePackaging/header.png", "resources/images"));
            libJar.addMetaInfFiles(EmbeddedResource.add("footer.png", "src/main/resources/chapter02_servlet/part08_resourcePackaging/footer.png", "resources/images"));

            libJar.exportToFile(APP_FILE_TARGET);

            final EmbeddedWar application = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            application.addClasses(ServletPrincipal.class);
            application.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter02_servlet/part08_resourcePackaging/web.xml"));
            application.addWebResourceFiles(EmbeddedResource.add("index.jsp", "src/main/resources/chapter02_servlet/part08_resourcePackaging/index.jsp"));
            application.addWebResourceFiles(EmbeddedResource.add("login.jsp", "src/main/resources/chapter02_servlet/part08_resourcePackaging/login.jsp", "pages"));

            application.addWebResourceFiles(EmbeddedResource.add("principal.jpg", "src/main/resources/chapter02_servlet/part08_resourcePackaging/principal.jpg", "images"));

            application.addLibs(APP_FILE_TARGET + "/libJar.jar");

            final File warFile = application.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // final HttpClient httpClient = HttpClientBuilder.create().build();
            // final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/servletPrincipal"));

            // System.out.println(response);
            System.out.println("Access on http://localhost:8080/embeddedJeeContainerTest/index.jsp");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
