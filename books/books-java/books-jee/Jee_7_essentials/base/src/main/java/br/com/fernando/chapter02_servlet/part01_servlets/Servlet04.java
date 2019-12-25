package br.com.fernando.chapter02_servlet.part01_servlets;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedJar;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Servlet04 {

    // A servlet may forward a request to another servlet if further processing is required.
    // You can achieve this by dispatching the request to a different resource using RequestDispatcher , which can be obtained from
    // HttpServletRequest.getRequestDispatcher or ServletContext.getRequestDispatcher .
    //
    // ==================================================================================================================================================================
    @WebServlet("/servletOne")
    public static class ServletOne extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            // The former can accept a relative path, whereas the latter can accept a path relative to the current context only
            // ( another servlet deployed in the same context) :
            // request.getRequestDispatcher("servletTwo").forward(request, response);
            //
            // or
            //
            // The ServletContext.getContext method can be used to obtain ServletContext for foreign contexts.
            response.sendRedirect("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/servletTwo");
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/servletTwo")
    public static class ServletTwo extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            try (final PrintWriter out = response.getWriter()) {

                out.println("<html><head>");
                out.println("<title>ServletTwo!</title>");
                out.println("</head><body>");
                out.println("<h1>My Second Servlet!</h1>");
                out.println("</body></html>");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ==================================================================================================================================================================
    // In addition to declaring servlets using @WebServlet and web.xml, you can define them programmatically using ServletContext.addServlet methods.
    // You can do this from the ServletContainerInitializer.onStartup or ServletContextListener.contextInitialized method.
    //
    // http://learningviacode.blogspot.com/2016/02/servlet-3x-handlestypes-annotation.html
    @HandlesTypes({ HttpServlet.class, Filter.class })
    public static class MyInitializer implements ServletContainerInitializer {

        @Override
        public void onStartup(Set<Class<?>> clazz, ServletContext context) {
            ServletRegistration.Dynamic reg = context.addServlet("ServletThree", "br.com.fernando.chapter02_servlet.part01_servlets.Servlet04$ServletThree");
            reg.addMapping("/servletThree");
        }
    }

    public static class ServletThree extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            try (final PrintWriter out = response.getWriter()) {

                out.println("<html><head>");
                out.println("<title>ServletThree!</title>");
                out.println("</head><body>");
                out.println("<h1>My Third Servlet!</h1>");
                out.println("</body></html>");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedJar jar = new EmbeddedJar("contextInitializer");
            jar.addClasses(ServletThree.class, MyInitializer.class);
            jar.addMetaInfFiles(EmbeddedResource.add("javax.servlet.ServletContainerInitializer", "src/main/resources/chapter02_servlet/part01_servlets/javax.servlet.ServletContainerInitializer", "services"));
            jar.exportToFile(APP_FILE_TARGET);

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(ServletOne.class, ServletTwo.class);
            war.addLibs(APP_FILE_TARGET + "/contextInitializer.jar");
            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/servletThree"));

            System.out.println(response);

        } catch (final IOException ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
