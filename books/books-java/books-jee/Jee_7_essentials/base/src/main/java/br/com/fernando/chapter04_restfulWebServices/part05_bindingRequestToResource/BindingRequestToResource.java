package br.com.fernando.chapter04_restfulWebServices.part05_bindingRequestToResource;

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
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Providers;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class BindingRequestToResource {

    // By default, a new resource is created for each request to access a resource. 
    // The resource method parameters, fields, or bean properties are bound by way of xxxParam annotations added during object creation time.
    @Path("persons")
    public static class PersonResource {

        // You can obtain more details about the application deployment context and the context of 
        // individual requests using the @Context annotation.
        //
        // * Application provides access to application configuration information.
        @Context
        private Application app;

        // * UriInfo provides access to application and request URI information.
        @Context
        private UriInfo uri;

        // * HttpHeaders provides access to HTTP header information either as a Map or as convenience methods. 
        // Note that @HeaderParam can also be used to bind an HTTP header to a resource method parameter, field, or bean property.
        @Context
        private HttpHeaders headers;

        // * Request provides a helper to request processing and is typically used with Response to dynamically build the response.
        @Context
        private Request request;

        // * SecurityContext provides access to security-related information about the current request.
        @Context
        private SecurityContext security;

        // * Providers supplies information about runtime lookup of provider instances based on a set of search criteria.
        @Context
        private Providers providers;

        @GET
        @Produces("text/plain")
        public String getList(
                // * @CookieParam binds the value of a cookie:
                @CookieParam("JSESSIONID") final String sessionId,
                // * @HeaderParam binds the value of an HTTP header:
                @HeaderParam("Accept") final String acceptHeader) { //

            // This code binds the value of the "JSESSIONID" cookie to the resource method parameter sessionid 

            final StringBuilder builder = new StringBuilder();

            builder.append("JSESSIONID: ").append(sessionId).append("<br>Accept: ").append(acceptHeader);

            return builder.toString();
        }

        // * @FormParam binds the value of a form parameter contained within a request entity body. 
        // Its usage is displayed in “Binding HTTP Methods  on page 77.

        @GET
        @Path("matrix")
        @Produces("text/plain")
        public String getList(
                // * @MatrixParam binds the name/value parameters in the URI path:
                @MatrixParam("start") final int start, @MatrixParam("end") final int end) {
            final StringBuilder builder = new StringBuilder();

            builder //
                    .append("start: ") // 
                    .append(start) // 
                    .append("<br>end: ") //
                    .append(end);

            return builder.toString();
        }

        @GET
        @Path("context")
        @Produces("text/plain")
        public String getList() {

            final StringBuilder builder = new StringBuilder();

            builder.append("Application.classes: ") //
                    .append(app.getClasses()) //
                    .append("<br>Path: ") //
                    .append(uri.getPath()); //

            for (final String header : headers.getRequestHeaders().keySet()) {
                builder //
                        .append("<br>Http header: ") //
                        .append(headers.getRequestHeader(header));
            }

            builder.append("<br>Headers.cookies: ") //
                    .append(headers.getCookies()) //
                    .append("<br>Request.method: ") //
                    .append(request.getMethod()) //
                    .append("<br>Security.isSecure: ") //
                    .append(security.isSecure()); //

            return builder.toString();
        }
    }

    @WebServlet(urlPatterns = { "/TestServlet" })
    public static class TestServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");

            final PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Request Binding</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Request Binding</h1>");

            final Client client = ClientBuilder.newClient();

            final WebTarget target = client.target("http://" //
                    + request.getServerName() //
                    + ":" //
                    + request.getServerPort() //
                    + request.getContextPath() //
                    + "/webresources/persons");

            out.print("GETTing @CookieParam ...<br>");
            out.print(target.request().get(String.class));

            out.print("<br><br>GETTing @MatrixParam ...<br>"); // 
            out.print(target.path("matrix") //  
                    .matrixParam("start", "5") //
                    .matrixParam("end", "10") // 
                    .request().get(String.class));

            out.print("<br><br>GETTing @Context ...<br>");
            out.print(target.path("context").request().get(String.class));

            out.println("<br>... done.<br>");

            out.println("</body>");
            out.println("</html>");
        }

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            processRequest(request, response);
        }

        @Override
        protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            processRequest(request, response);
        }

        @Override
        public String getServletInfo() {
            return "Short description";
        }
    }

    @ApplicationPath("webresources")
    public static class ApplicationConfig extends Application {

    }

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(PersonResource.class, ApplicationConfig.class, TestServlet.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            war.addWebResourceFiles(EmbeddedResource.add("index.html", "src/main/resources/chapter04_restfulWebServices/part03_bindingHttpMethods/index.html"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);
            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            System.out.println("the end"); // use the browser

            // http://localhost:8080/embeddedJeeContainerTest/TestServlet

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
