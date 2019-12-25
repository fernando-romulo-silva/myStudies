package br.com.fernando.chapter02_servlet.part05_nonblocking_io;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedWar;

import br.com.fernando.utils.FileDownloadResponseHandler;
import br.com.fernando.utils.ProgressEntityWrapper;
import br.com.fernando.utils.ProgressListener;

public class NonBlockingIO {

    private static final String URI_NON_BLOCK_IO = "http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/nonBlockIoServlet";

    private static final String URI_BLOCK_IO = "http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/blockIoServlet";

    public static final String BIG_FILE_ZIP_FOR_TEST = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "DevDownloads" + File.separator + "DCEVM-full-7.jar";

    public static final String BIG_FILE_ZIP_NEW_NAME = "GWT-Programming-Cookbook-NEW.pdf";

    // ==================================================================================================================================================================
    @WebServlet(urlPatterns = { "/blockIoServlet" }, asyncSupported = true)
    // @MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 50)
    public static class BlockIoServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        // If the incoming data is blocking or streamed slower than the server can read, then the server thread is waiting for that data.
        // The same can happen if the data is written to ServletOutputStream. This restricts the scalability of the Web Container.
        //
        @Override
        public void doPost(final HttpServletRequest request, final HttpServletResponse response) {

            final AsyncContext asyncContext = request.startAsync(request, response);

            try (final InputStream input = request.getInputStream()) {

                byte[] b = new byte[1024];
                int len = -1;

                while ((len = input.read(b)) != -1) { // ---> BLOCKING!
                    System.out.println(len);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            asyncContext.complete();
        }

        @Override
        public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            final AsyncContext asyncContext = request.startAsync(request, response);

            final int ARBITARY_SIZE = 1048;

            response.setContentType("application/zip");
            response.setHeader("Content-disposition", "attachment; filename=" + BIG_FILE_ZIP_NEW_NAME);

            try (// 
                    final InputStream in = new FileInputStream(BIG_FILE_ZIP_FOR_TEST);
                    final OutputStream out = response.getOutputStream()) {

                byte[] buffer = new byte[ARBITARY_SIZE];

                int numBytesRead;
                while ((numBytesRead = in.read(buffer)) > 0) { // ---> BLOCKING!
                    out.write(buffer, 0, numBytesRead);
                }
            }

            asyncContext.complete();
        }
    }

    // ==================================================================================================================================================================
    // Nonblocking I/O allows developers to read data as it becomes available or write data when it's possible to do so. This increases not only the scalability of the Web Container
    // but also the number of connections that can be handled simultaneously. Nonblocking I/O only works with async request processing in Servlets, Filters01, and Upgrade
    // Processing.
    @WebServlet(urlPatterns = "/nonBlockIoServlet", asyncSupported = true)
    public static class NonBlockIoReaderServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            // Async I/O requires an async servlet
            final AsyncContext context = request.startAsync();

            final ServletInputStream input = request.getInputStream();

            // Invoking setXXXListener methods indicates that nonblocking I/O is used instead of traditional.
            input.setReadListener(new MyReadListener(input, context));
        }

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            // Async I/O requires an async servlet
            final AsyncContext context = request.startAsync();

            final ServletOutputStream output = response.getOutputStream();

            // Invoking setXXXListener methods indicates that nonblocking I/O is used instead of traditional.
            output.setWriteListener(new MyWriteListener(output, context));
        }

    }

    // Servlet 3.1 achieves nonblocking I/O by introducing two new interfaces: ReadListener and WriteListener. These listeners have callback methods that are 
    // invoked when the content is available to be read or can be written without blocking
    static final class MyReadListener implements ReadListener {

        private final ServletInputStream input;

        private final AsyncContext context;

        public MyReadListener(ServletInputStream input, AsyncContext context) {
            this.input = input;
            this.context = context;
        }

        // * The onDataAvailable callback method is called whenever data can be read without blocking.
        @Override
        public void onDataAvailable() throws IOException {
            System.out.println("MyReadListener :: onDataAvailable");

            try {
                StringBuilder sb = new StringBuilder();
                int len = -1;
                byte b[] = new byte[1024];

                // isReady() must be checked before each read. If it returns false all currently
                // available data has been read so the thread should be relinquished.
                // if isFinished() returns true this is an indication that all data has been read.

                while (input.isReady() && !input.isFinished() && (len = input.read(b)) != -1) {
                    // Save the input data for later processing.
                    String data = new String(b, 0, len);
                    sb.append(data);
                }

                //
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }

        // * The onAllDataRead callback method is invoked whenever data for the current request is completely read.
        @Override
        public void onAllDataRead() throws IOException {

            // Process the inbound data
            context.getResponse().getWriter().write("...the response...");

            System.out.println("MyReadListener :: onAllDataRead");
            context.complete();
        }

        // * The onError callback is invoked if there is an error processing the request
        @Override
        public void onError(Throwable tx) {
            System.out.println("MyReadListener :: onError");
            System.out.println(tx);
            context.complete();
        }
    }

    static final class MyWriteListener implements WriteListener {

        final ServletOutputStream output;

        final AsyncContext context;

        public MyWriteListener(final ServletOutputStream output, final AsyncContext context) {
            super();
            this.output = output;
            this.context = context;
        }

        @Override
        public void onError(Throwable tx) {
        }

        @Override
        public void onWritePossible() throws IOException {
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer(); final CloseableHttpClient httpPostClient = HttpClients.createDefault();) {

            // --- war
            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(BlockIoServlet.class, NonBlockIoReaderServlet.class, MyReadListener.class);

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            // --- server
            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // --- post a file
            // ---------------------------------------------------------------------------------------
            final MultipartEntityBuilder postBuilder = MultipartEntityBuilder.create();
            postBuilder.addBinaryBody("file", new File(BIG_FILE_ZIP_FOR_TEST), ContentType.APPLICATION_OCTET_STREAM, BIG_FILE_ZIP_NEW_NAME);
            final HttpEntity multipartPost = postBuilder.build();

            // listener for progress
            final ProgressListener pListener = new ProgressListener() {

                @Override
                public void progress(float percentage) {
                    System.out.println("percentage " + percentage);
                }
            };

            // post blockIoServlet!
            final HttpPost httpPostIoBlock = new HttpPost(URI_BLOCK_IO);
            httpPostIoBlock.setEntity(new ProgressEntityWrapper(multipartPost, pListener));
            final CloseableHttpResponse responseHttpPostBlockIo = httpPostClient.execute(httpPostIoBlock);
            System.out.println(responseHttpPostBlockIo.getStatusLine());

            // post nonBlockIoServlet!
            final HttpPost httpPostNonIoBlock = new HttpPost(URI_NON_BLOCK_IO);
            httpPostNonIoBlock.setEntity(new ProgressEntityWrapper(multipartPost, pListener));
            final CloseableHttpResponse responseHttpPostNonBlockIo = httpPostClient.execute(httpPostNonIoBlock);
            System.out.println(responseHttpPostNonBlockIo.getStatusLine());

            // --- get a file
            // ---------------------------------------------------------------------------------------
            final CloseableHttpClient httpGetClient = HttpClients.custom() //
                    .setRedirectStrategy(new LaxRedirectStrategy()) // adds HTTP REDIRECT support to GET and POST methods 
                    .build(); //

            HttpGet httpGetIoBlock = new HttpGet(URI_BLOCK_IO); // we're using GET but it could be via POST as well
            File downloaded = httpGetClient.execute(httpGetIoBlock, new FileDownloadResponseHandler(new File(System.getProperty("user.home") + File.separator + BIG_FILE_ZIP_NEW_NAME)));

            System.out.println(downloaded.exists());

        } catch (final IOException ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
