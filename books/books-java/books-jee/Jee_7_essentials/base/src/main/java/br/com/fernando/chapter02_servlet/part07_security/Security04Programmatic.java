package br.com.fernando.chapter02_servlet.part07_security;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
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

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.jeecontainer.resources.MyEmbeddedJeeRealmFile;
import org.myembedded.jeecontainer.resources.MyEmbeddedJeeRealmFile.MyEmbeddedJeeRealmFileBuilder;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Security04Programmatic {

    // ==================================================================================================================================================================
    @WebServlet(urlPatterns = { "/LoginServlet" })
    public static class LoginServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            response.setContentType("text/html;charset=UTF-8");
            String user = request.getParameter("user");
            String password = request.getParameter("password");

            if (user == null || password == null) {
                throw new IllegalStateException("You can't log in!");
            }

            // Goes directly to the container's identity store, by-passing any authentication mechanism
            request.login(user, password);

            // boolean authenticate(HttpServletResponse response)
            // Use the container login mechanism configured for the ServletContext to authenticate the user making this request. If you call this method, the container 
            // will send such a response that will cause the browser to prompt the user to enter his/her user name and password.
            //
            // void login(username, password)
            // You call this method when you get the username and password from the user. ServletException - if the configured login mechanism does not support 
            // username password authentication, or if a non-null caller identity had already been established (prior to the call to login), or if validation of 
            // the provided username and password fails.
            //
            // void logout()
            // Once you call this method, getUserPrincipal, getRemoteUser, and getAuthType methods return null.

            userDetails(response.getWriter(), request);
        }

        private void userDetails(PrintWriter out, HttpServletRequest request) {
            out.println("isUserInRole?" + request.isUserInRole("ROLE01"));
            out.println("getRemoteUser?" + request.getRemoteUser());
            out.println("getUserPrincipal?" + request.getUserPrincipal());
            out.println("getAuthType?" + request.getAuthType());

            // Programmatic methods are:
            // getRemoteUser: which determines the user name with which the client authenticated. The getRemoteUser method returns the name of the remote user 
            // (the caller) associated by the container with the request. If no user has been authenticated, this method returns null.
            //
            // isUserInRole: which determines whether a remote user is in a specific security role. If no user has been authenticated, this method returns false. 
            // This method expects a String user role-name parameter.
            // 
            // getUserPrincipal: which determines the principal name of the current user and returns a java.security.Principal object. If no user has been authenticated, 
            // this method returns null. Calling the getName method on the Principal returned by getUserPrincipal returns the name of the remote user.            
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            processRequest(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            processRequest(request, response);
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(LoginServlet.class);
            //
            war.addWebResourceFiles(EmbeddedResource.add("index.jsp", "src/main/resources/chapter02_servlet/part07_security/indexProgrammatic.jsp"));

            // web inf files
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter02_servlet/part07_security/web_Security04Programmatic.xml"));
            war.addWebInfFiles(EmbeddedResource.add("glassfish-web.xml", "src/main/resources/chapter02_servlet/part07_security/glassfish-web.xml"));
            // export
            final File warFile = war.exportToFile(APP_FILE_TARGET);

            final Path realFilePath = Paths.get(Security05Digest.class.getResource("/chapter02_servlet/part07_security/keyfile").toURI());

            final MyEmbeddedJeeRealmFile realm = new MyEmbeddedJeeRealmFileBuilder("myRealm", realFilePath) //
                    .withDigestAlgorithm("SSHA256") // Default
                    .withEncoding("Hex") // Default
                    .build();

            embeddedJeeServer.addFileRealm(realm);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // access the browser
            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
