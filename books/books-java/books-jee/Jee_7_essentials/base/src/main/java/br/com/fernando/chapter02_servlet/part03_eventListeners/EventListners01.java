package br.com.fernando.chapter02_servlet.part03_eventListeners;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedWar;

public class EventListners01 {

    // ==================================================================================================================================================================
    // Event listeners provide life-cycle callback events for ServletContext, HttpSession, and ServletRequest objects. These listeners are classes that
    // implement an interface that supports event notifications for state changes in these objects. Each class is annotated with @WebListener, declared
    // in web.xml, or registered via one of the ServletContext.addListener methods.
    //
    // There may be multiple listener classes listening to each event type, and they may be specified in the order in which the container invokes the
    // listener beans for each event type. The listeners are notified in the reverse order during application shutdown.
    //
    //
    // ==================================================================================================================================================================
    // 1 - Servlet context listeners listen to the events from resources in that context:
    @WebListener
    public static class MyContextListener implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            ServletContext context = sce.getServletContext();

            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyContextListener::contextInitialized");
            System.out.println("---------------------------------------------------------------------");

            System.out.println(context.getContextPath());
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyContextListener::contextDestroyed");
            System.out.println("---------------------------------------------------------------------");
        }
    }

    //
    // ==================================================================================================================================================================
    // 2 - The ServletContextAttributeListener is used to listen for attribute changes in the context:
    @WebListener
    public static class MyServletContextAttributeListener implements ServletContextAttributeListener {

        @Override
        public void attributeAdded(ServletContextAttributeEvent event) {
            // . . . event.getName();
            // . . . event.getValue();

            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyServletContextAttributeListener::attributeAdded");
            System.out.println("---------------------------------------------------------------------");
        }

        @Override
        public void attributeRemoved(ServletContextAttributeEvent event) {
            // . . .

            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyServletContextAttributeListener::attributeRemoved");
            System.out.println("---------------------------------------------------------------------");
        }

        @Override
        public void attributeReplaced(ServletContextAttributeEvent event) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyServletContextAttributeListener::attributeReplaced");
            System.out.println("---------------------------------------------------------------------");
        }
    }

    //
    // ==================================================================================================================================================================
    // 3 - The HttpSessionListener listens to events from resources in that session:
    @WebListener
    public static class MySessionListener implements HttpSessionListener {

        @Override
        public void sessionCreated(HttpSessionEvent hse) {
            HttpSession session = hse.getSession();
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MySessionListener::sessionCreated");
            System.out.println("---------------------------------------------------------------------");
            System.out.println(session.getId());
        }

        @Override
        public void sessionDestroyed(HttpSessionEvent hse) {
            // . . .
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MySessionListener::sessionDestroyed");
            System.out.println("---------------------------------------------------------------------");
        }
    }

    // ==================================================================================================================================================================
    // 4 - The HttpSessionAttributeListener is used to listen for attribute changes in the session:
    @WebListener
    public static class MyHttpSessionAttributeListener implements HttpSessionAttributeListener {

        @Override
        public void attributeAdded(HttpSessionBindingEvent event) {
            HttpSession session = event.getSession();
            // . . . event.getName();
            // . . . event.getValue();
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyHttpSessionAttributeListener::attributeAdded");
            System.out.println("---------------------------------------------------------------------");
            System.out.println(session.getId());
        }

        @Override
        public void attributeRemoved(HttpSessionBindingEvent event) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyHttpSessionAttributeListener::attributeRemoved");
            System.out.println("---------------------------------------------------------------------");
        }

        @Override
        public void attributeReplaced(HttpSessionBindingEvent event) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyHttpSessionAttributeListener::attributeReplaced");
            System.out.println("---------------------------------------------------------------------");
        }
    }

    //
    // ==================================================================================================================================================================
    // 5 - The HttpSessionActivationListener is used to listen for events when the session is passivated or activated:
    // You don't need to put @WebListener or web.xml, how use it? 
    // TODO how it works?
    public static class MyHttpSessionActivationListener implements HttpSessionActivationListener, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public void sessionWillPassivate(HttpSessionEvent hse) {
            // ... hse.getSession();
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyHttpSessionActivationListener::sessionWillPassivate");
            System.out.println("---------------------------------------------------------------------");
        }

        @Override
        public void sessionDidActivate(HttpSessionEvent hse) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyHttpSessionActivationListener::sessionDidActivate");
            System.out.println("---------------------------------------------------------------------");
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses( //
                    ServletTest.class, MyContextListener.class, MyServletContextAttributeListener.class, //
                    MySessionListener.class, MyHttpSessionActivationListener.class, MyHttpSessionAttributeListener.class);
            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/servletTest"));

            System.out.println(response);

        } catch (final IOException ex) {
            System.out.println(ex);
        }

        downVariables();
    }

    //
    // ==================================================================================================================================================================
    @WebServlet("/servletTest")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            try (final PrintWriter out = response.getWriter()) {

                out.println("<html><head>");
                out.println("<title>Servlet Test!</title>");
                out.println("</head><body>");
                out.println("<h1>My Second Servlet!</h1>");
                out.println("</body></html>");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
