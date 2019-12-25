package br.com.fernando.chapter02_servlet.part10_handlingMultipartRequests;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class HandlingMultipartRequests {

    // Use multipart/form-data when your form includes any <input type="file"> elements
    //
    // @MultipartConfig may be specified on a servlet, indicating that it expects a request of type multipart/form-data. The HttpServletRequest.getParts and .getPart 
    // methods then make the various parts of the multipart request available:

    @WebServlet(urlPatterns = { "/FileUploadServlet" })
    @MultipartConfig(location = "/tmp")
    public static class FileUploadServlet extends HttpServlet {
        //
        // * @MultipartConfig is specified on the class, indicating that the doPost method will receive a request of type multipart/form-data.
        // * The location attribute is used to specify the directory location where the files are stored.
        // * The getParts method provides a Collection of parts for this multipart request.
        // * part.write is used to write this uploaded part to disk.

        private static final long serialVersionUID = 1L;

        protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>File Upload Servlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>File Upload Servlet</h1>");
                out.println("Receiving the uploaded file ...<br>");
                out.println("Received " + request.getParts().size() + " parts ...<br>");
                String fileName = "";
                for (Part part : request.getParts()) {
                    fileName = part.getSubmittedFileName();
                    out.println("... writing " + fileName + " part<br>");
                    part.write(fileName);
                    out.println("... written<br>");
                }
                out.println("... uploaded to: /tmp/" + fileName);
                out.println("</body>");
                out.println("</html>");
            }
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            processRequest(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            processRequest(request, response);
        }

        @Override
        public String getServletInfo() {
            return "Short description";
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(FileUploadServlet.class);
            war.addWebResourceFiles(EmbeddedResource.add("index.jsp", "src/main/resources/chapter02_servlet/part10_handlingMultipartRequests/index.jsp"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            //	    final HttpClient httpClient = HttpClientBuilder.create().build();
            //	    final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/FileUploadServlet"));
            //	    System.out.println(response);

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
