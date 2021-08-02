package br.com.fernando.chapter02_servlet.part09_errorMapping;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;

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

public class ErrorMapping {

    // ==================================================================================================================================================================
    // An HTTP error code or an exception thrown by a serlvet can be mapped to a resource  bundled with the application to customize the appearance of content 
    // when a servlet generates an error. This allows fine-grained mapping of errors from your web application to custom pages.
    // Please look at /chapter02/part09_errorMapping/web.xml.
    //
    @WebServlet("/servletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            throw new RuntimeException();
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            processRequest(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            processRequest(request, response);
        }

        @Override
        public String getServletInfo() {
            return "Short description";
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(ServletPrincipal.class);
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter02_servlet/part09_errorMapping/web.xml"));
            war.addWebResourceFiles(EmbeddedResource.add("index.jsp", "src/main/resources/chapter02_servlet/part09_errorMapping/index.jsp"));
            war.addWebResourceFiles(EmbeddedResource.add("error-exception.jsp", "src/main/resources/chapter02_servlet/part09_errorMapping/error-exception.jsp"));
            war.addWebResourceFiles(EmbeddedResource.add("error-404.jsp", "src/main/resources/chapter02_servlet/part09_errorMapping/error-404.jsp"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/servletPrincipal"));

            System.out.println(response);

        } catch (final IOException ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
