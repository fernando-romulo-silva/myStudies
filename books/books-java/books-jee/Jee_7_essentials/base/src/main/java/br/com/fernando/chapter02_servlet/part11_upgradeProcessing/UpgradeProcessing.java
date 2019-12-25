package br.com.fernando.chapter02_servlet.part11_upgradeProcessing;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.WebConnection;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class UpgradeProcessing {

    public static final String CRLF = "\r\n";

    // Section 14.42 of HTTP 1.1 (RFC 2616) defines an upgrade mechanism that allows you to transition from HTTP 1.1 to some other, incompatible protocol
    //
    // The servlet container provides an HTTP upgrade mechanism. However, the servlet container itself does not have any knowledge about the upgraded protocol.
    // The protocol processing is encapsulated in the HttpUpgradeHandler.
    // Data reading or writing between the servlet container and the HttpUpgradeHandler is in byte streams.
    //
    // ==================================================================================================================================================================
    @WebServlet(urlPatterns = { "/UpgradeServlet" })
    public static class UpgradeServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");

            // This code is dangerous, so the request.upgrade need execute before this code, because will throw this exception
            // Execute it without debug mode.
            //
            // java.lang.IllegalStateException: getWriter() has already been called for this response
            // http://www.java67.com/2016/02/javalangillegalstateexception-getwriter-has-already-been-called-for-this-response.html

            try (PrintWriter out = response.getWriter()) {
                out.println("<h1>Servlet UpgradeServlet at " + request.getContextPath() + "</h1>");

                // The request looks for the Upgrade header and makes a decision based upon its value.
                // In this case, the connection is upgraded if the Upgrade header is equal to echo.
                if (request.getHeader("Upgrade").equals("echo")) {
                    response.setStatus(HttpServletResponse.SC_SWITCHING_PROTOCOLS);
                    response.setHeader("Connection", "Upgrade");
                    response.setHeader("Upgrade", "echo");

                    // The protocol processing is encapsulated in the HttpUpgradeHandler. Data reading or writing between
                    // the servlet container and the HttpUpgradeHandler is in byte streams.
                    request.upgrade(MyProtocolHandler.class);

                    System.out.println("Request upgraded to MyProtocolHandler");
                }
            }
        }
    }

    //
    // This interface encapsulates the upgrade protocol processing. The upgrade protocol allows us to switch from the HTTP base protocol to another new protocol.
    public static class MyProtocolHandler implements HttpUpgradeHandler {

        @Override
        public void init(WebConnection wc) {
            System.out.println("MyProtocolHandler :: init");

            try (ServletInputStream input = wc.getInputStream(); ServletOutputStream output = wc.getOutputStream();) {
                output.write(("upgrade" + CRLF).getBytes());
                output.write(("received" + CRLF).getBytes());
                output.write("END".getBytes());
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        }

        @Override
        public void destroy() {
            // throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("MyProtocolHandler :: destroy");
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(UpgradeServlet.class, MyProtocolHandler.class);

            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter02_servlet/part11_upgradeProcessing/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("glassfish-web.xml", "src/main/resources/chapter02_servlet/part11_upgradeProcessing/glassfish-web.xml"));

            war.addWebResourceFiles(EmbeddedResource.add("index.jsp", "src/main/resources/chapter02_servlet/part11_upgradeProcessing/index.jsp"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // ----------------------------------------------------------------------------------------------------
            // For secure issues, headers cannot be sent directly by the browser unless you use a web agent.
            // A web agent can be written in Java. Here's an example that sends an upgrade header to the UpgradeServlet :
            //
            try (Socket socket = new Socket("localhost", HTTP_PORT)) {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.write("GET /" + EMBEDDED_JEE_TEST_APP_NAME + "/UpgradeServlet HTTP/1.1" + CRLF);
                out.write("Host: localhost:" + HTTP_PORT + CRLF);
                out.write("Upgrade: echo" + CRLF);
                out.write("Connection: Upgrade" + CRLF);
                out.write(CRLF);
                out.flush();
            }
            //
            System.out.println("test");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
