package br.com.fernando.chapter02_servlet.part06_webfragments;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedJar;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class WebFragment {

    // ==================================================================================================================================================================
    // A web fragment is part or all of the web.xml file included in a library or framework JAR's META-INF directory.
    // If this framework is bundled in the WEB-INF/lib directory, the container will pick up and configure the framework without requiring the developer to do
    // it explicitly.
    //
    // It can include almost all of the elements that can be specified in web.xml.
    // However, the top-level element must be web-fragment and the corresponding file must be called webfragment.xml.
    // This allows logical partitioning of the web application, look at /chpter02/part06_webFragment/webfragment01.xml

    // The <absolute-ordering> element in web.xml (chapter02/part06_webFragment/web.xml) is used to specify the exact order in which the resources should be loaded,
    // and the <ordering> element within web-fragment.xml (chapter02/part06_webFragment/webfragmentXX.xml is used to specify relative ordering.
    //
    // The two orders are mutually exclusive, and absolute ordering overrides relative.
    //
    //
    // ==================================================================================================================================================================
    // WebFragment01
    public static class MyFilter01 implements javax.servlet.Filter {

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

            chain.doFilter(request, response);
        }

        @Override
        public void destroy() {
            System.out.println("I'm here! {MyFilter01.destroy} ");
        }
    }

    // ==================================================================================================================================================================
    // WebFragment02
    public static class MyFilter02 implements javax.servlet.Filter {

        public void doFilter(HttpServletRequest request, HttpServletResponse response) {
            System.out.println("I'm here! {MyFilter02.doFilter 1} ");
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            System.out.println("I'm here! {MyFilter02.init} ");
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("I'm here! {MyFilter02.doFilter 2} ");

            chain.doFilter(request, response);
        }

        @Override
        public void destroy() {
            System.out.println("I'm here! {MyFilter02.destroy} ");
        }
    }

    // ==================================================================================================================================================================
    // WebFragment03
    public static class MyFilter03 implements javax.servlet.Filter {

        public void doFilter(HttpServletRequest request, HttpServletResponse response) {
            System.out.println("I'm here! {MyFilter03.doFilter 1} ");
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            System.out.println("I'm here! {MyFilter03.init} ");
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("I'm here! {MyFilter03.doFilter 2} ");

            chain.doFilter(request, response);
        }

        @Override
        public void destroy() {
            System.out.println("I'm here! {MyFilter03.destroy} ");
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try ( //
                final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer(); //
                final CloseableHttpClient httpPostClient = HttpClients.createDefault()) {

            // ------------------------------
            // --- webFragment01.jar
            final EmbeddedJar webFragment01Jar = new EmbeddedJar("webFragment01");
            webFragment01Jar.addClasses(MyFilter01.class);
            webFragment01Jar.addMetaInfFiles(EmbeddedResource.add("web-fragment.xml", "src/main/resources/chapter02_servlet/part06_webFragment/webfragment01.xml"));
            webFragment01Jar.addMetaInfFiles(EmbeddedResource.add("FragIndex01.jsp", "src/main/resources/chapter02_servlet/part06_webFragment/FragIndex01.jsp"));
            webFragment01Jar.exportToFile(APP_FILE_TARGET);

            // --- webFragment02.jar
            final EmbeddedJar webFragment02Jar = new EmbeddedJar("webFragment02");
            webFragment02Jar.addClasses(MyFilter02.class);
            webFragment02Jar.addMetaInfFiles(EmbeddedResource.add("web-fragment.xml", "src/main/resources/chapter02_servlet/part06_webFragment/webfragment02.xml"));
            webFragment02Jar.exportToFile(APP_FILE_TARGET);

            // --- webFragment03.jar
            final EmbeddedJar webFragment03Jar = new EmbeddedJar("webFragment03");
            webFragment03Jar.addClasses(MyFilter03.class);
            webFragment03Jar.addMetaInfFiles(EmbeddedResource.add("web-fragment.xml", "src/main/resources/chapter02_servlet/part06_webFragment/webfragment03.xml"));
            webFragment03Jar.exportToFile(APP_FILE_TARGET);

            // --- war
            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter02_servlet/part06_webFragment/web.xml"));
            war.addClasses(ServletPrincipal.class);
            war.addLibs(APP_FILE_TARGET + "/webFragment01.jar", APP_FILE_TARGET + "/webFragment02.jar", APP_FILE_TARGET + "/webFragment03.jar");
            final File warFile = war.exportToFile(APP_FILE_TARGET);

            // ------------------------------
            // --- server
            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // ------------------------------
            // --- request
            final HttpClient httpClient01 = HttpClientBuilder.create().build();
            final HttpResponse response01 = httpClient01.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/servletPrincipal"));
            System.out.println(response01);

        } catch (final IOException ex) {
            System.out.println(ex);
        }

        downVariables();
    }

    //
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
                out.println("<h1>My Second Servlet!</h1>");
                out.println("</body></html>");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
