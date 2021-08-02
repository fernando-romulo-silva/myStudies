package br.com.fernando.chapter02_servlet.part02_filters;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
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

public class Filter01 {

    // ==================================================================================================================================================================
    // A servlet filter may be used to update the request and response payload and header information from and to the servlet.
    // It is important to realize that filters do not create the response—they only modify or adapt the requests and responses.
    @WebFilter( //
            // You can associate filters with a servlet or with a group of servlets and static content by specifying a URL pattern //
            urlPatterns = "/*", //
            // The @WebInitParam may be used to specify initialization parameters here as well.
            initParams = { @WebInitParam(name = "type", value = "checking") }, //
            servletNames = { "myServlet", "anotherServlet" } // you can define which servlet it'll filter
    )

    // A filter and the target servlet always execute in the same invocation thread. Multiple filters may be arranged in a filter chain
    public static class LoggingFilter implements javax.servlet.Filter {

        public void doFilter(HttpServletRequest request, HttpServletResponse response) {
            System.out.println("I'm here! {LoggingFilter.doFilter 1} ");
        }

        // For initialization of your filter, implement the init() method, specified in the Filter interface. First, create or retrieve a 
        // javax.servlet.FilterConfig object, which init() takes as input.
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            System.out.println("I'm here! {LoggingFilter.init} ");
        }

        // This method takes a request object, a response object, and a javax.servlet.FilterChain object created by the servlet container. 
        // Implement whatever processing you want, and (typically) call the doFilter() method of the filter chain object to invoke the next entity in the chain. 
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("I'm here! {LoggingFilter.doFilter 2} ");

            long start = System.currentTimeMillis();
            System.out.println("Milliseconds in: " + start);

            chain.doFilter(request, response); // next filter mapped on same url!

            long end = System.currentTimeMillis();
            System.out.println("Milliseconds out: " + end);
        }

        // Implement the destroy() method, specified in the Filter interface, to clean up resources or do anything special before the filter is taken out of service
        @Override
        public void destroy() {
            System.out.println("I'm here! {LoggingFilter.destroy} ");
        }
    }

    // ==================================================================================================================================================================
    // You can also define a filter using <filter> and <filter-mapping> elements in the deployment descriptor (chapter02/web-Filter01.xml)
    public static class AuthenticationFilter implements javax.servlet.Filter {

        public void doFilter(HttpServletRequest request, HttpServletResponse response) {
            System.out.println("I'm here! {MyFilter01.doFilter 1} ");
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            System.out.println("I'm here! {MyFilter01.init} ");
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("I'm here! {MyFilter01.doFilter 2} ");
        }

        @Override
        public void destroy() {
            System.out.println("I'm here! {MyFilter01.destroy} ");
        }
    }

    // ==================================================================================================================================================================
    // In addition to declaring filters using @WebFilter and web.xml, you can define them programmatically using ServletContext.addFilter methods.
    // You can do this from the ServletContainerInitializer.onStartup method or the ServletContextListener.contextInitialized method.
    // The addFilter method returns ServletRegistration.Dynamic, which can then be used to add mapping for URL patterns, set initialization parameters,
    // and handle other configuration items:
    //
    public static class MyInitializer implements ServletContainerInitializer {

        @Override
        public void onStartup(Set<Class<?>> clazz, ServletContext context) {
            FilterRegistration.Dynamic reg = context.addFilter("BaseFilter", "br.com.fernando.chapter02_servlet.part02_filters.Filter01$EncryptionFilter");
            reg.addMappingForUrlPatterns(null, false, "/");
        }
    }

    public static class EncryptionFilter implements javax.servlet.Filter {

        public void doFilter(HttpServletRequest request, HttpServletResponse response) {
            System.out.println("I'm here! {EncryptionFilter.doFilter 1} ");
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            System.out.println("I'm here! {EncryptionFilter.init} ");
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("I'm here! {EncryptionFilter.doFilter 2} ");
        }

        @Override
        public void destroy() {
            System.out.println("I'm here! {EncryptionFilter.destroy} ");
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedJar jar = new EmbeddedJar("contextInitializer");
            jar.addClasses(EncryptionFilter.class, MyInitializer.class);
            jar.addMetaInfFiles(EmbeddedResource.add("javax.servlet.ServletContainerInitializer", "src/main/resources/chapter02_servlet/part02_filters/javax.servlet.ServletContainerInitializer", "services"));
            jar.exportToFile(APP_FILE_TARGET);

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(servletTest.class, LoggingFilter.class, AuthenticationFilter.class);
            war.addLibs(APP_FILE_TARGET + "/contextInitializer.jar");
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter02_servlet/part02/web-Filter01.xml"));
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
    public static class servletTest extends HttpServlet {

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
