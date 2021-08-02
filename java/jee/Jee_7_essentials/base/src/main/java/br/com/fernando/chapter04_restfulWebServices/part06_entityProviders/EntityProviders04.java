package br.com.fernando.chapter04_restfulWebServices.part06_entityProviders;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
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

public class EntityProviders04 {

    @Path("endpoint")
    public static class MyResource {

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public MyObject echoObject(MyObject mo) {
            return mo;
        }
    }

    @Provider
    @Consumes(MediaType.APPLICATION_JSON)
    public static class MyReader implements MessageBodyReader<MyObject> {

        @Override
        public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
            return MyObject.class.isAssignableFrom(type);
        }

        @Override
        public MyObject readFrom(Class<MyObject> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
            MyObject mo = new MyObject();
            JsonParser parser = Json.createParser(in);
            while (parser.hasNext()) {
                switch (parser.next()) {
                case KEY_NAME:
                    String key = parser.getString();
                    parser.next();
                    switch (key) {
                    case "name":
                        mo.setName(parser.getString());
                        break;
                    case "age":
                        mo.setAge(parser.getInt());
                        break;
                    default:
                        break;
                    }
                    break;
                default:
                    break;
                }
            }
            return mo;
        }
    }

    @Provider
    @Produces(MediaType.APPLICATION_JSON)
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
            JsonGenerator gen = Json.createGenerator(out);
            gen.writeStartObject().write("name", t.getName()).write("age", t.getAge()).writeEnd();
            gen.flush();
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

        private String name;
        private int age;

        public MyObject() {
        }

        public MyObject(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
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
            client.register(MyReader.class);

            final MyObject result01 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/endpoint") //
                    .request() //
                    .post(Entity.entity(new MyObject("Duke", 18), MediaType.APPLICATION_JSON), MyObject.class);

            System.out.println(result01); // foo

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
