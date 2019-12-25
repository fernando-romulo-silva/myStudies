package br.com.fernando.chapter02_servlet.part07_security;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.executeRequestHttp;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.jeecontainer.resources.MyEmbeddedJeeRealmFile;
import org.myembedded.jeecontainer.resources.MyEmbeddedJeeRealmFile.MyEmbeddedJeeRealmFileBuilder;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Security02BasicOmission {

    // ==================================================================================================================================================================
    public static class SecurityServletTest01 extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            // Only ROLE03 and ROLE04 roles!
            System.out.println("I'm here! {SecurityServletTest01.doPost} ");
        }

        @Override
        public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            // Only ROLE01

            // Some introductory HTML...
            String remoteUser = request.getRemoteUser();

            if (request.isUserInRole("ROLE01")) {
                System.out.println("I'm using ROLE01!");
            }

            // See if the client is allowed
            System.out.println("I'm here! {SecurityServletTest01.doGet} " + remoteUser);
        }

        @Override
        public void doDelete(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            // Only ROLE01 and ROLE04!
            System.out.println("I'm here! {SecurityServletTest01.doDelete} ");
        }

        @Override
        public void doPut(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            // Only ROLE05!
            System.out.println("I'm here! {SecurityServletTest01.doPut} ");
        }

        @Override
        protected void doOptions(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            // Everyone!
            System.out.println("I'm here! {SecurityServletTest01.doOptions} ");
        }

        @Override
        protected void doTrace(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            // No one, only ROLE01
            System.out.println("I'm here! {SecurityServletTest01.doTrace} ");
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/servletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            try (final PrintWriter out = response.getWriter()) {

                out.println("<html><head>");
                out.println("<title>Servlet Test!</title>");
                out.println("</head><body>");
                out.println("<h1>Servlet Test!</h1>");
                out.println("</body></html>");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            // classes
            war.addClasses(SecurityServletTest01.class, ServletPrincipal.class);

            // web inf files
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter02_servlet/part07_security/web_Security02BasicOmission.xml"));
            war.addWebInfFiles(EmbeddedResource.add("glassfish-web.xml", "src/main/resources/chapter02_servlet/part07_security/glassfish-web.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            final Path realFilePath = Paths.get(Security05Digest.class.getResource("/chapter02_servlet/part07_security/keyfile").toURI());

            final MyEmbeddedJeeRealmFile realm = new MyEmbeddedJeeRealmFileBuilder("myRealm", realFilePath) //
                    .withDigestAlgorithm("SSHA256") // Default
                    .withEncoding("Hex") // Default
                    .build();

            embeddedJeeServer.addFileRealm(realm);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final String url01 = "http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/private01/securityServletTest01";

            System.out.println("// GET ----------------------------------------------------------------------------------------------- ");
            executeRequestHttp("user01", "changeit", HttpGet.class, url01); // user01 ROLE01 // OK
            executeRequestHttp("user02", "changeit", HttpGet.class, url01); // user02 ROLE02 // Forbidden
            executeRequestHttp(HttpGet.class, url01); // Unauthorized
            System.out.println("// --------------------------------------------------------------------------------------------------- ");
            //
            System.out.println("// POST ---------------------------------------------------------------------------------------------- ");
            executeRequestHttp("user04", "changeit", HttpPost.class, url01); // user04 ROLE04 // OK
            executeRequestHttp("user03", "changeit", HttpPost.class, url01); // user03 ROLE03 // OK
            executeRequestHttp("user01", "changeit", HttpPost.class, url01); // user01 ROLE01 // Forbidden
            executeRequestHttp("user02", "changeit", HttpPost.class, url01); // user02 ROLE02 // Forbidden
            System.out.println("// --------------------------------------------------------------------------------------------------- ");
            //
            System.out.println("// DELETE -------------------------------------------------------------------------------------------- ");
            executeRequestHttp("user01", "changeit", HttpDelete.class, url01); // user01 ROLE01 // OK
            executeRequestHttp("user04", "changeit", HttpDelete.class, url01); // user04 ROLE04 // OK
            executeRequestHttp("user03", "changeit", HttpDelete.class, url01); // user03 ROLE03 // Forbidden
            System.out.println("// --------------------------------------------------------------------------------------------------- ");
            //
            System.out.println("// PUT ----------------------------------------------------------------------------------------------- ");
            executeRequestHttp("user05", "changeit", HttpPut.class, url01); // user05 ROLE05 // OK
            executeRequestHttp("user01", "changeit", HttpPut.class, url01); // user01 ROLE01 // Forbidden
            System.out.println("// --------------------------------------------------------------------------------------------------- ");
            //
            System.out.println("// TRACE --------------------------------------------------------------------------------------------- ");
            executeRequestHttp("user01", "changeit", HttpTrace.class, url01); // user01 ROLE01 // TRACE method is not allowed
            executeRequestHttp("user02", "changeit", HttpTrace.class, url01); // user02 ROLE02 // TRACE method is not allowed
            System.out.println("// --------------------------------------------------------------------------------------------------- ");
            //
            System.out.println("// OPTIONS ------------------------------------------------------------------------------------------- ");
            executeRequestHttp(HttpOptions.class, url01); // no user // OK
            executeRequestHttp("user02", "changeit", HttpOptions.class, url01); // user02 ROLE02 // OK
            System.out.println("// --------------------------------------------------------------------------------------------------- ");
            //
            System.out.println("// GET WITHOUT SECURITY ------------------------------------------------------------------------------ ");
            final String url02 = "http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/servletPrincipal";
            executeRequestHttp(HttpGet.class, url02); // OK            

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
