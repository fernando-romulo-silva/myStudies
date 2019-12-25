package br.com.fernando.chapter04_restfulWebServices.part10_Interceptors;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Interceptor {

    static final ArrayList<Order> ORDERS = new ArrayList<>(Arrays.asList( //
            new Order(1, "order01"), //
            new Order(2, "order02"), //
            new Order(3, "order03"), //
            new Order(4, "order04"), //
            new Order(5, "order05"), //
            new Order(6, "order06"), //
            new Order(7, "order07"), //
            new Order(8, "order08"), //
            new Order(9, "order09"), //
            new Order(10, "order10") //
    ));

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Order implements Serializable {

        private static final long serialVersionUID = 1L;

        private int id;

        private String name;

        Order() {
            super();
        }

        public Order(final int id, final String name) {
            super();
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Order: id" + id + " name:" + name;
        }
    }

    // Entity interceptors are mainly concerned with marshaling and unmarshaling of HTTP message bodies.
    // They implement ReaderInterceptor or WriterInterceptor, or both.
    //
    //
    // WriterInterceptor operates on the outbound request on the client side and on the outbound response on the server side:
    @Provider
    public static class MyWriterInterceptor implements WriterInterceptor {

        @Override
        public void aroundWriteTo(WriterInterceptorContext wic) throws IOException, WebApplicationException {

            System.out.println("\n==================================================================\n");
            System.out.println("<start>MyWriterInterceptor");

            wic.setOutputStream(new FilterOutputStream(wic.getOutputStream()) {

                final ByteArrayOutputStream baos = new ByteArrayOutputStream();

                @Override
                public void write(int b) throws IOException {
                    baos.write(b);
                    super.write(b);
                }

                @Override
                public void close() throws IOException {
                    System.out.println("MyWriterInterceptor --> " + baos.toString());
                    super.close();
                }
            });

            wic.proceed();

            System.out.println("\n<end>MyWriterInterceptor");
            System.out.println("\n==================================================================\n");
        }
    }

    // ReaderInterceptor operates on the outbound response on the client side and on the inbound request on the server side:
    // @Provider
    public static class MyReaderInterceptor implements ReaderInterceptor {

        @Override
        public Object aroundReadFrom(ReaderInterceptorContext ric) throws IOException, WebApplicationException {

            System.out.println("\n==================================================================\n");
            System.out.println("<start>ReaderInterceptor");

            final InputStream old = ric.getInputStream();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int c;
            while ((c = old.read()) != -1) {
                baos.write(c);
            }
            System.out.println("MyReaderInterceptor --> " + baos.toString());

            ric.setInputStream(new ByteArrayInputStream(baos.toByteArray()));

            System.out.println("\n<end>ReaderInterceptor");
            System.out.println("\n==================================================================\n");

            return ric.proceed();
        }
    }

    @Path("orders")
    public static class MyResource {

        @GET
        @Path("{oid}")
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        public Response getOrder(@PathParam("oid") int id) {
            final Order order = findOrder(id);
            return Response.status(Response.Status.OK).entity(order).build();
        }

        public Order findOrder(int id) {

            final Iterator<Order> iterator = ORDERS.iterator();
            Order result = null;
            while (iterator.hasNext() && result == null) {
                result = iterator.next();
                if (result.getId() != id) {
                    result = null;
                }
            }
            return result;
        }
    }

    // As with filters, there is an interceptor chain for each kind of entity interceptor.
    // Entityinterceptors in a chain are sorted based on their priorities and are executed in order.
    // Priorities are defined via the @javax.annotation.Priority annotation and represented by integer numbers.
    // The Priorities class defines built-in priorities for security, decoders/encoders, and more.
    // The default binding priority is Priorities.USER.
    // The priorities are sorted in ascending order; the lower the number, the higher the priority.

    @ApplicationPath("webresources")
    public static class MyApplication extends Application {
    }

    // ------------------------------------------
    // Request Flow
    // 
    // Client Side
    // Client -> [Client Request Filter] -> [Client Writer Interceptor] -> { Internet }
    //
    // Server Side
    // { Internet } -> [@PreMatch Filter] -> (Request Matching) -> [Container Request Filter] -> [Server Reader Interceptor] -> Resource
    //
    // ------------------------------------------
    // Response Flow
    //
    // Server Side
    // Resource -> [Container Response Filter] -> [Server Writer Interceptor] -> { Internet }
    //
    // Client Side
    // { Internet } -> [Client Response Filter] -> [Client Reader Interceptor] -> Client
    // ------------------------------------------

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Order.class, MyApplication.class, MyResource.class, MyWriterInterceptor.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final Client client = ClientBuilder.newClient();
            client.register(MyReaderInterceptor.class);

            final Order order = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/orders") //
                    .path("{oid}") //
                    .resolveTemplate("oid", 4) //
                    .request() //
                    .get(Order.class);

            System.out.println(order);

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
