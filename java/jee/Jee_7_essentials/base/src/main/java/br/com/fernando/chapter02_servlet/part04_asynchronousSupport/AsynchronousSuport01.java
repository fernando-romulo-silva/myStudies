package br.com.fernando.chapter02_servlet.part04_asynchronousSupport;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class AsynchronousSuport01 {

    // ==================================================================================================================================================================
    // Consider a servlet that has to wait for a JDBC connection to be available from the pool, receiving a JMS message or reading a resource from the filesystem.
    // Waiting for a long-running process to return completely blocks the thread waiting, sitting, and doing nothing which is not an optimal usage of your server
    // resources.
    //
    // This is where the server can be asynchronously processed such that the control (or thread) is returned to the container to perform other tasks while waiting
    // for the long-running process to complete.
    //
    //
    public static class MyAsyncService implements Runnable {

        AsyncContext ac;

        public MyAsyncService(final AsyncContext ac) {
            this.ac = ac;
        }

        @Override
        public void run() {
            // . . .
            // A long time work

            try {
                Thread.sleep(5000);
            } catch (final InterruptedException ex) {
                System.out.println(ex);
            }

            // Then you can complete the asynchronous request by calling AsyncContext.complete (explicit)
            // ac.complete();

            // or dispatching to another  resource (implicit).
            ac.dispatch("/asyncOutput.jsp");
            // The container completes the invocation of the asynchronous request in the latter case.
        }
    }

    // AsyncListener is registered to listen for events when the request processing is complete, has timed out, 
    // or resulted in an error. 
    static final class MyAsyncListener implements AsyncListener {

        @Override
        public void onComplete(final AsyncEvent ae) {
            System.out.println("MyAsyncListener :: 'onComplete' For Request?= " + ae.getAsyncContext().getRequest().getAttribute("ServletName"));
        }

        @Override
        public void onTimeout(final AsyncEvent ae) {
            System.out.println("MyAsyncListener :: 'onTimeout' For Request");
        }

        @Override
        public void onError(final AsyncEvent ae) {
            System.out.println("MyAsyncListener :: 'onError' For Request");
        }

        @Override
        public void onStartAsync(final AsyncEvent ae) {
            System.out.println("MyAsyncListener :: 'onStartAsync'");
        }
    }

    // The asynchronous behavior needs to be explicitly enabled on a servlet. You achieve this by adding the asyncSupported attribute on @WebServlet:
    @WebServlet(urlPatterns = "/servletTest", asyncSupported = true)
    // You can also enable the asynchronous behavior by setting the <async-supported> element to true in web.xml or calling 
    // ServletRegistration.setAsyncSupported (true) during programmatic registration.
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        public void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
            doGet(req, resp);
        }

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            final Date dateObj = new Date();
            final PrintWriter out = response.getWriter();

            response.setContentType("text/html");
            request.setAttribute("receivedAt", dateObj);
            out.println("Request Time?= " + request.getAttribute("receivedAt"));

            // --------------------------------------------------------------------------------------------------

            // You can then start the asynchronous processing in a separate thread using the startAsync method on the request.
            // This method returns AsyncContext, which represents the execution context of the asynchronous request.
            final AsyncContext asynContext = request.startAsync();
            final ServletRequest servReq = asynContext.getRequest();

            /**** Adding 'AsyncListener' Named As 'MyAsyncListener' ****/
            asynContext.addListener(new MyAsyncListener());
            asynContext.setTimeout(6000);

            final boolean isAsyncSupported = request.isAsyncSupported();
            // This Will Return True
            out.println("<br>AsyncSupported?= " + isAsyncSupported);

            // Then you can complete the asynchronous request by calling AsyncContext.complete, int this case, it is on MyAsyncService.run
            final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);
            executor.execute(new MyAsyncService(asynContext));

            final boolean isAsyncStarted = servReq.isAsyncStarted();
            // This Will Return True
            out.println("<br>AsyncStarted?= " + isAsyncStarted);
        }
    }

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(ServletTest.class, MyAsyncListener.class, MyAsyncService.class);
            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter02_servlet/part04/asyncOutput.jsp"), EmbeddedResource.add("src/main/resources/index.jsp"));
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
}
