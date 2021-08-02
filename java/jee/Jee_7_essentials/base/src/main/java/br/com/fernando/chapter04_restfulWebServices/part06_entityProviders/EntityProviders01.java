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
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class EntityProviders01 {

    // JAX-RS defines entity providers that supply mapping services between on-the-wire representations and their associated Java types. 
    // The entities, also known as "message payload" or "payload," represent the main part of an HTTP message. 
    // These are specified as method parameters and return types of resource methods. 
    // Several standard Java types —such as String , byte[] , javax.xml.bind.JAXBElement , java.io.InputStream ,  java.io.File , and others—have a predefined mapping 
    // and are required by the specification.

    @Path("endpoint")
    public static class EndPoint {
        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public MyObject echoObject(final MyObject mo) {
            return mo;
        }
    }

    // Applications may provide their own mapping to custom types using the MessageBodyReader and MessageBodyWriter interfaces. This allows us to extend the 
    // JAX-RS runtime easily to support our own custom entity providers.
    //
    //
    // The MessageBodyReader interface defines the contract for a provider that supports the conversion of a stream to a Java type.
    //
    // If we do not specify @XmlRootElement on MyResource, then we need to define the mapping between XML to Java and vice versa. 
    //
    // Java API for XML Processing can be used to define the mapping between the Java type to XML and vice versa.
    // 
    // Similarly, Java API for JSON Processing can be used to define the two-way mapping between Java and JSON.
    //
    @Provider // The implementation class needs to be marked with @Provider to make it discoverable by the JAX-RS runtime on the server side
    @Consumes(MediaType.APPLICATION_JSON) // @Consumes ensures that this entity provider will only support the specified media type.
    public static class MyReader implements MessageBodyReader<MyObject> {

        @Override
        public boolean isReadable(final Class<?> type, final Type type1, final Annotation[] antns, final MediaType mt) {
            return MyObject.class.isAssignableFrom(type);
        }

        // This method is using the Streaming API defined by Java API for JSON Processing to read 'MyObject' o from the InputStream in for the HTTP entity
        @Override
        public MyObject readFrom(Class<MyObject> type, // 
                final Type type1, // 
                final Annotation[] antns, //
                final MediaType mt, MultivaluedMap<String, String> mm, //
                final InputStream in) throws IOException, WebApplicationException { //

            final MyObject mo = new MyObject();
            final JsonParser parser = Json.createParser(in);

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

    // MessageBodyWriter interface defines the contract for a provider that supports the conversion of a Java type to a stream.
    @Provider
    @Produces(MediaType.APPLICATION_JSON) //  @Produces ensures that this entity provider will only support the specified media type:
    public static class MyWriter implements MessageBodyWriter<MyObject> {

        @Override
        public boolean isWriteable(final Class<?> type, final Type type1, final Annotation[] antns, final MediaType mt) {
            return MyObject.class.isAssignableFrom(type);
        }

        @Override
        public long getSize(final MyObject t, final Class<?> type, final Type type1, final Annotation[] antns, final MediaType mt) {
            // As of JAX-RS 2.0, the method has been deprecated and the 
            // value returned by the method is ignored by a JAX-RS runtime. 
            // All MessageBodyWriter implementations are advised to return -1 from 
            // the method.
            return -1;
        }

        // The method is using the Streaming API defined by Java API for JSON Processing to write 'MyObject' o to the underlying OutputStream out for the HTTP entity
        @Override
        public void writeTo(MyObject t, //
                final Class<?> type, // 
                final Type type1, //
                final Annotation[] antns, //
                final MediaType mt, //
                final MultivaluedMap<String, Object> mm, //
                final OutputStream out) throws IOException, WebApplicationException {

            JsonGenerator gen = Json.createGenerator(out);

            gen.writeStartObject().write("name", t.getName()).write("age", t.getAge()).writeEnd();

            gen.flush();
        }
    }

    public static class MyObject {

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

        @Override
        public String toString() {
            return "MyObject: age" + age + " name:" + name;
        }
    }

    @ApplicationPath("webresources")
    public static class MyApplication extends Application {
    }

    @WebServlet(urlPatterns = { "/TestServlet" })
    public static class TestServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>JAX-RS Reader/Writer w/ JSON</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>JAX-RS Reader/Writer w/ JSON</h1>");

            Client client = ClientBuilder.newClient();
            client //
                    .register(MyReader.class) //
                    .register(MyWriter.class);

            WebTarget target = client.target("http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/webresources/endpoint");
            out.println("POST request");

            MyObject mo = target.request().post(Entity.entity(new MyObject("Duke", 18), MediaType.APPLICATION_JSON), MyObject.class);
            out.println("Received response: " + mo.getName() + ", " + mo.getAge() + "<br><br>");
            out.println("Message exchanged using application/json type.");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(EndPoint.class, MyApplication.class, MyWriter.class, MyReader.class, MyObject.class, TestServlet.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);
            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            System.out.println("the end"); // use the browser

            // http://localhost:8080/embeddedJeeContainerTest/TestServlet

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
