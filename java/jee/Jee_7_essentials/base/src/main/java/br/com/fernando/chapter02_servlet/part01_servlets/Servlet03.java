package br.com.fernando.chapter02_servlet.part01_servlets;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Servlet03 extends javax.servlet.http.HttpServlet {

    private static final long serialVersionUID = 1L;

    // You can also define a servlet using the servlet and servlet-mapping elements in the
    // deployment descriptor of the web application, web.xml. You can define the Account Servlet using web.xml,
    // look at (web-part02AccountServlet03)
    //
    // The annotations cover most of the common cases, so web.xml is not required in those cases. But some cases, such as ordering of servlets,
    // can only be done using web.xml. If the metadata-complete element in web.xml is true, then the annotations in the class are not processed:
    //
    // ... metadata-complete="true"
    //
    // The values defined in the deployment descriptor override the values defined using annotations.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head>");
            out.println("<title>MyServlet</title>");
            out.println("</head><body>");
            out.println("<h1>My First Servlet</h1>");
            out.println("</body></html>");

            // The ServletContext provides detail about the execution environment of the servlets and is used to communicate with the
            // container—for example, by reading a resource packaged in the web application, writing to a logfile, or dispatching a request.
            ServletContext context = request.getServletContext();

            // A servlet can send an HTTP cookie, named JSESSIONID, to the client for session tracking. This cookie may be marked as HttpOnly,
            // which ensures that the cookie is not exposed to client-side scripting code, and thus helps mitigate certains kinds of crosssite
            // scripting attacks:
            SessionCookieConfig sessionCookieConfig = context.getSessionCookieConfig();
            sessionCookieConfig.setHttpOnly(true);

            // The HttpSession interface can be used to view and manipulate information about a session such as the session identifier and creation time, 
            // and to bind objects to the session.

            HttpSession session = request.getSession(true);

            // The session.setAttribute and session.getAttribute methods are used to bind objects to the session:

            session.setAttribute("name", "My name is");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Servlet03.class);
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter02_servlet/part01_servlets/web-Servlet03.xml"));
            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/account03"));

            System.out.println(response);

        } catch (final IOException ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
