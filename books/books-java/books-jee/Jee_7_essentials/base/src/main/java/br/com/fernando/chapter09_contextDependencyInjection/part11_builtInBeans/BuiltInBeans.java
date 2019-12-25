package br.com.fernando.chapter09_contextDependencyInjection.part11_builtInBeans;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class BuiltInBeans {

    public static interface Greeting {
        public String greet(String name);
    }

    // A Java EE or embeddable EJB container must provide the following built-in beans, all of which have the qualifer @Default:

    @Stateless
    @TransactionManagement(TransactionManagementType.BEAN)
    public static class SimpleGreeting implements Greeting {

        // A bean with bean type javax.servlet.http.HttpServletRequest, allowing injection of a reference to the HttpServletRequest:
        @Inject
        HttpServletRequest httpServletRequest;

        // A bean with bean type javax.servlet.http.HttpSession, allowing injection of a reference to the HttpSession:
        @Inject
        HttpSession httpSession;

        // A bean with bean type javax.servlet.ServletContext, allowing injection of a reference to the ServletContext:
        @Inject
        ServletContext servletContext;

        // A bean with bean type javax.transaction.UserTransaction, allowing injection of a reference to the JTA UserTransaction:
        @Inject
        UserTransaction ut;

        // A bean with bean type javax.security.Principal, allowing injection of a Principal representing the current caller identity:
        @Inject
        Principal principal;
        // In this code, a Principal instance is injected in an EJB. 
        // The injected instance can be used to check the security principal name.

        @Override
        public String greet(final String name) {
            try {

                ut.begin();

                System.out.println("context path (HttpServletRequest): " + httpServletRequest.getContextPath());
                System.out.println("session id: " + httpSession.getId());
                System.out.println("context path (ServletContext): " + servletContext.getContextPath());
                System.out.println("user transaction status: " + ut.getStatus());
                System.out.println("security principal: " + principal.getName());

                ut.commit();
                // In this code, a UserTransaction instance is injected in a bean-managed transaction EJB. 
                // The injected instance can be used to start and commit/roll back the transaction.

            } catch (final Exception ex) {
                Logger.getLogger(SimpleGreeting.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "Hello " + name;
        }

    }

    @WebServlet(name = "TestServlet", urlPatterns = { "/TestServlet" })
    public static class TestServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private Greeting greeting;

        // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");

            try (final PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet TestServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet TestServlet at " + request.getContextPath() + "</h1>");
                out.println(greeting.greet("Duke"));
                out.println("<br><br>Check for output in server.log");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

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
