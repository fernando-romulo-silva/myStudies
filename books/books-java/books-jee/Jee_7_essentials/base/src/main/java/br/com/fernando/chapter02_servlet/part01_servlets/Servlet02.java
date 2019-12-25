package br.com.fernando.chapter02_servlet.part01_servlets;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedWar;

// A servlet is a web component hosted in a servlet container and generates dynamic content.
// The fully qualified class name is the default servlet name, and may be overridden using
// the name attribute of the annotation. The servlet may be deployed at multiple URLs:
@WebServlet( //
        // The fully qualified class name is the default servlet name, and may be overridden using
        // the name attribute of the annotation. The servlet may be deployed at multiple URLs:
        urlPatterns = { "/account02", "/accountServlet02" }, //
        //
        // The @WebInitParam can be used to specify an initialization parameter:
        initParams = { @WebInitParam(name = "type", value = "checking") }

)
public class Servlet02 extends javax.servlet.http.HttpServlet {

    private static final long serialVersionUID = 1L;

    // ==================================================================================================================================================================
    // The HttpServlet interface has one doXXX method to handle each of HTTP GET, POST, PUT, DELETE, HEAD, OPTIONS, and TRACE requests.
    // Typically the developer is concerned with overriding the doGet and doPost methods. The following code shows a servlet handling the GET request:

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        // * The HttpServletRequest and HttpServletResponse capture the request/response with the web client.
        //
        // * The request parameters; HTTP headers; different parts of the path such as host, port, and context; and much more information is
        // available from HttpServletRe quest.

        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head>");
            out.println("<title>MyServlet</title>");
            out.println("</head><body>");
            out.println("<h1>My First Servlet</h1>");
            out.println("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

        // Request parameters may be passed in GET and POST requests. In a GET request, these parameters are passed in the query string as
        // name/value pairs. Here is a sample URL to invoke the servlet explained earlier with request parameters
        // . . ./account?tx=10
        //
        String txValue = request.getParameter("tx");
        System.out.println("request parameter \"txValue\":" + txValue);
    }

    // Initialization parameters, also known as init params, may be defined on a servlet to store startup and configuration information. 
    // As explained earlier, @WebInitParam is used to specify init params for a servlet:
    @Override
    public void init(ServletConfig config) throws ServletException {
        type = config.getInitParameter("type");
        System.out.println("init param \"type\":" + type);
    }

    String type = null;

    // ==================================================================================================================================================================
    public static void execute() throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Servlet02.class);
            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final HttpClient httpClient01 = HttpClientBuilder.create().build();
            final HttpResponse response01 = httpClient01.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/account02?tx=10"));
            System.out.println(response01);

            final HttpClient httpClient02 = HttpClientBuilder.create().build();
            final HttpResponse response02 = httpClient02.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/accountServlet02?tx=10"));
            System.out.println(response02);

        } catch (final IOException ex) {
            System.out.println(ex);
        }

        downVariables();
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        execute();
    }
}
