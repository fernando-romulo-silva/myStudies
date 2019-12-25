package br.com.fernando.chapter04_restfulWebServices.part14_singleton;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

public class Singleton02 {

    @Singleton
    @Path("application")
    public static class ApplicationSingletonResource {

        private List<String> strings;

        public ApplicationSingletonResource() {
            strings = new ArrayList<>();
        }

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String getAll() {
            return strings.toString();
        }

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("{id}")
        public String getString(@PathParam("id") int id) {
            return strings.get(id);
        }

        @POST
        @Consumes(MediaType.TEXT_PLAIN)
        public void postString(String content) {
            strings.add(content);
        }

        @PUT
        @Consumes(MediaType.TEXT_PLAIN)
        public void putToList(String content) {
            strings.add(content);
        }

        @DELETE
        @Path("{content}")
        public void deleteFromList(@PathParam("content") String content) {
            if (strings.contains(content)) {
                strings.remove(content);
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
            war.addClasses(MyApplication.class, ApplicationSingletonResource.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // ---------------------------------------------------------------------------------------
            final Client client = ClientBuilder.newClient();

            final Response result01 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/application") //
                    .request() //
                    .post(Entity.text("passion fruit"));

            System.out.println(result01); // 

            // ----------------------------------------------------------------------------------------
            final Response result02 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/application") //
                    .request() //
                    .post(Entity.text("kiwi"));

            System.out.println(result02); // 

            // ----------------------------------------------------------------------------------------
            final String result03 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/application") //
                    .request() //
                    .get(String.class);

            System.out.println(result03); //            

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();

        // readerwriter-injection
        // readerwriter-json
    }
}
