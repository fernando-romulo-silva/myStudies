package br.com.fernando.chapter02_servlet.part03_eventListeners;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedJar;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class EventListners02 {

    // ==================================================================================================================================================================
    // 6 - The HttpSessionBindingListener is used to listen to events when an object is bound to or unbound from a session:
    // You don't need to put @WebListener or web.xml, how use it?
    // TODO how it works?
    public class MyHttpSessionBindingListener implements HttpSessionBindingListener {

        @Override
        public void valueBound(HttpSessionBindingEvent event) {
            HttpSession session = event.getSession();
            // . . . event.getName();
            // . . . event.getValue();
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyHttpSessionBindingListener::valueBound");
            System.out.println("---------------------------------------------------------------------");
            System.out.println(session.getId());
        }

        @Override
        public void valueUnbound(HttpSessionBindingEvent event) {
            // . . .
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyHttpSessionBindingListener::valueUnbound");
            System.out.println("---------------------------------------------------------------------");
        }
    }

    // ==================================================================================================================================================================
    // 7 - AsyncListener is used to manage async events such as completed, timed out, or an error.
    // You don't need to put @WebListener or web.xml, how use it?
    // TODO how it works?
    public static class MyAsyncListener implements AsyncListener {

        @Override
        public void onComplete(AsyncEvent event) throws IOException {
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyAsyncListener::onComplete");
            System.out.println("---------------------------------------------------------------------");
        }

        @Override
        public void onTimeout(AsyncEvent event) throws IOException {
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyAsyncListener::onTimeout");
            System.out.println("---------------------------------------------------------------------");
        }

        @Override
        public void onError(AsyncEvent event) throws IOException {
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyAsyncListener::onError");
            System.out.println("---------------------------------------------------------------------");
        }

        @Override
        public void onStartAsync(AsyncEvent event) throws IOException {
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyAsyncListener::onStartAsync");
            System.out.println("---------------------------------------------------------------------");
        }
    }

    //
    // ==================================================================================================================================================================
    // 8 - The ServletRequestListener listens to the events from resources in that request:
    // You don't need to put @WebListener or web.xml, how use it?
    // TODO how it works?
    public class MyRequestListener implements ServletRequestListener {

        @Override
        public void requestDestroyed(ServletRequestEvent sre) {

            ServletRequest request = sre.getServletRequest();
            // . . .
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyRequestListener::requestDestroyed");
            System.out.println("---------------------------------------------------------------------");
            System.out.println(request.getLocalName());
        }

        @Override
        public void requestInitialized(ServletRequestEvent sre) {
            // . . .
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyRequestListener::requestInitialized");
            System.out.println("---------------------------------------------------------------------");
        }
    }

    // ==================================================================================================================================================================
    // 9 - The ServletRequestAttributeListener is used to listen for attribute changes in the request.
    // We'll add this listener by xml
    public static class MyServletRequestAttributeListener implements ServletRequestAttributeListener {

        @Override
        public void attributeAdded(ServletRequestAttributeEvent srae) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyServletRequestAttributeListener::attributeAdded");
            System.out.println("---------------------------------------------------------------------");
        }

        @Override
        public void attributeRemoved(ServletRequestAttributeEvent srae) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyServletRequestAttributeListener::attributeRemoved");
            System.out.println("---------------------------------------------------------------------");
        }

        @Override
        public void attributeReplaced(ServletRequestAttributeEvent srae) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" MyServletRequestAttributeListener::attributeReplaced");
            System.out.println("---------------------------------------------------------------------");
        }
    }

    // ==================================================================================================================================================================
    // You can define them programmatically using ServletContext.addListener methods. You can do this from the ServletContainerInitializer.onStartup or
    // ServletContextListener.contextInitialized method.
    public static class MyInitializer implements ServletContainerInitializer {

        @Override
        public void onStartup(Set<Class<?>> clazz, ServletContext context) {
            context.addListener("br.com.fernando.chapter02_servlet.part03_eventListeners.EventListners02$MyContextListener");
        }
    }

    // ==================================================================================================================================================================
    // We'll add it programmatically
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

    // ==================================================================================================================================================================
    // It added on xml
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

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedJar jar = new EmbeddedJar("contextInitializer");
            jar.addClasses(MyContextListener.class, MyInitializer.class);
            jar.addMetaInfFiles(EmbeddedResource.add("javax.servlet.ServletContainerInitializer", "src/main/resources/chapter02_servlet/part03_eventListeners/javax.servlet.ServletContainerInitializer", "services"));
            jar.exportToFile(APP_FILE_TARGET);

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(ServletTest.class, MyAsyncListener.class, MyHttpSessionBindingListener.class, MyRequestListener.class, MyServletRequestAttributeListener.class, MyServletContextAttributeListener.class);
            war.addLibs(APP_FILE_TARGET + "/contextInitializer.jar");
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter02_servlet/part03_eventListeners/web-Listener01.xml"));

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
