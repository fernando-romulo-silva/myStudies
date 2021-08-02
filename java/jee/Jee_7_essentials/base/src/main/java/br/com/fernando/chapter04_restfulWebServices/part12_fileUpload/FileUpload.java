package br.com.fernando.chapter04_restfulWebServices.part12_fileUpload;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class FileUpload {

    @Path("endpoint")
    public static class MyResource {

        @POST
        @Path("upload")
        @Consumes(MediaType.APPLICATION_OCTET_STREAM)
        @Produces(MediaType.TEXT_PLAIN)
        public Response postOctetStream(InputStream content) {
            try (Reader reader = new InputStreamReader(content)) {
                int totalsize = 0;
                int count = 0;
                final char[] buffer = new char[256];
                while ((count = reader.read(buffer)) != -1) {
                    totalsize += count;
                }
                return Response.ok(totalsize).build();
            } catch (IOException e) {
                e.printStackTrace();
                return Response.serverError().build();
            }
        }

        @POST
        @Path("upload2")
        @Consumes({ MediaType.APPLICATION_OCTET_STREAM, "image/png" })
        @Produces(MediaType.TEXT_PLAIN)
        public Response postImageFile(File file) {
            try (Reader reader = new FileReader(file)) {
                int totalsize = 0;
                int count = 0;
                final char[] buffer = new char[256];
                while ((count = reader.read(buffer)) != -1) {
                    totalsize += count;
                }
                return Response.ok(totalsize).build();
            } catch (IOException e) {
                e.printStackTrace();
                return Response.serverError().build();
            }
        }
    }

    @ApplicationPath("webresources")
    public static class MyApplication extends Application {
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(MyApplication.class, MyResource.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // ---------------------------------------------------------------------------------------
            // create a temp file
            File tempFile = File.createTempFile("javaee7samples", ".png");
            // fill the file with 1KB of content
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                for (int i = 0; i < 1000; i++) {
                    outputStream.write(0);
                }
            }
            // ---------------------------------------------------------------------------------------
            final Client client = ClientBuilder.newClient();

            final Long uploadedFileSize = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/endpoint/upload") //
                    .request() //
                    .post(Entity.entity(tempFile, MediaType.APPLICATION_OCTET_STREAM), Long.class);

            System.out.println(uploadedFileSize); // size = 1000

            // ----------------------------------------------------------------------------------------
            final Long uploadedFileSize2 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/endpoint/upload2") //
                    .request() //
                    .post(Entity.entity(tempFile, MediaType.APPLICATION_OCTET_STREAM), Long.class);

            System.out.println(uploadedFileSize2); // size = 1000

            System.out.println("the end");

            // jaxrs-security
            // jsonp

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
