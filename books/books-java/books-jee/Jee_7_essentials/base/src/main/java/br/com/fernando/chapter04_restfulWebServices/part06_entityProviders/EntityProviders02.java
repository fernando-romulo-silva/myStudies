package br.com.fernando.chapter04_restfulWebServices.part06_entityProviders;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class EntityProviders02 {

    @Path("fruits")
    public static class MyResource {

        private final String[] response = { "apple", "banana", "mango" };

        @POST
        @Consumes(MyObject.MIME_TYPE)
        public String postWithCustomMimeType(MyObject mo) {
            System.out.println("endpoint invoked (getFruit(" + mo.getIndex() + "))");

            return response[Integer.valueOf(mo.getIndex()) % 3];
        }

        @POST
        @Path("index")
        @Consumes("text/plain")
        public String postSimple(int index) {
            return response[index % 3];
        }
    }

    @Provider
    @Consumes(MyObject.MIME_TYPE)
    public static class MyReader implements MessageBodyReader<MyObject> {

        @Override
        public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
            return MyObject.class.isAssignableFrom(type);
        }

        @Override
        public MyObject readFrom(Class<MyObject> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
            try {
                ObjectInputStream ois = new ObjectInputStream(in);
                return (MyObject) ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MyReader.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    @Provider
    @Produces(MyObject.MIME_TYPE)
    public static class MyWriter implements MessageBodyWriter<MyObject> {

        @Override
        public boolean isWriteable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
            return MyObject.class.isAssignableFrom(type);
        }

        @Override
        public long getSize(MyObject t, Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
            // As of JAX-RS 2.0, the method has been deprecated and the
            // value returned by the method is ignored by a JAX-RS runtime.
            // All MessageBodyWriter implementations are advised to return -1 from
            // the method.

            return -1;
        }

        @Override
        public void writeTo(MyObject t, Class<?> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, Object> mm, OutputStream out) throws IOException, WebApplicationException {
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(t);
        }
    }

    @ApplicationPath("webresources")
    public static class MyApplication extends Application {

        // @Override
        // public Set<Class<?>> getClasses() {
        // Set<Class<?>> resources = new java.util.HashSet<>();
        // resources.add(MyResource.class);
        // resources.add(MyReader.class);
        // resources.add(MyWriter.class);
        // return resources;
        // }

    }

    public static class MyObject implements Serializable {

        private static final long serialVersionUID = 1L;

        public static final String MIME_TYPE = "application/myType";

        private int index;

        public MyObject() {
        }

        public MyObject(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(MyObject.class, MyApplication.class, MyResource.class, MyWriter.class, MyReader.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // ---------------------------------------------------------------------------------------
            final Client client = ClientBuilder.newClient();
            client.register(MyWriter.class);

            final String result01 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/fruits") //
                    .request() //
                    .post(Entity.entity(new MyObject(1), MyObject.MIME_TYPE), String.class);

            System.out.println(result01); // foo

            // ----------------------------------------------------------------------------------------
            final String result02 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/fruits") //
                    .path("index").request() //
                    .post(Entity.text("1"), String.class);

            System.out.println(result02); // foo

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
