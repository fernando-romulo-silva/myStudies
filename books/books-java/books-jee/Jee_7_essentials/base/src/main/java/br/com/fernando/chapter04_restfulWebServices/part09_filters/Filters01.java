package br.com.fernando.chapter04_restfulWebServices.part09_filters;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Priority;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Priorities;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Filters01 {

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

    // JAX-RS 2 defines extension points to customize the request/response processing on both the client and server side.
    // These are used to extend an implementation in order to provide capabilities such as logging, confidentiality, and authentication.
    //
    // The two kinds of extension points are: filters and entity interceptors.
    // * Filters01 are mainly used to modify or process incoming and outgoing request or response headers.
    // * Entity interceptors are mainly concerned with marshaling and unmarshaling of HTTP message bodies.
    //
    // Filters01 can be configured on the client and server, giving us four extension points for filters, defined by four interfaces:
    //
    // * ClientRequestFilter
    // * ClientResponseFilter
    // * ContainerRequestFilter
    // * ContainerResponseFilter
    //
    // ContainerRequestFilter and ContainerResponseFilter are serverside filters
    // In this code, the filter is a provider class and thus must be marked with @Provider. This ensures that the filters are automatically discovered.
    // If the filter is not marked with @Provider, it needs to be explicitly registered in the MyApplication class:
    // @Provider
    @Priority(Priorities.USER)
    // A server-side filter that will log the headers received as part of the request and sent in the response message can be implemented:
    public static class ServerLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

        @Override
        public void filter(ContainerRequestContext crc) throws IOException {
            System.out.println("\n==================================================================\n");
            System.out.println("<start>ContainerRequestFilter");

            System.out.println(crc.getMethod() + " " + crc.getUriInfo().getAbsolutePath());

            for (String key : crc.getHeaders().keySet()) {
                System.out.println(key + ": " + crc.getHeaders().get(key));
            }
            crc.getHeaders().add("serverHeader", "serverHeaderValue");

            System.out.println("\n<end>ContainerRequestFilter");
            System.out.println("\n==================================================================\n");
        }

        @Override
        public void filter(ContainerRequestContext crc, ContainerResponseContext crc1) throws IOException {
            System.out.println("\n==================================================================\n");
            System.out.println("<start>ContainerResponseFilter");

            for (String key : crc1.getHeaders().keySet()) {
                System.out.println(key + ": " + crc1.getHeaders().get(key));
            }

            System.out.println("\n<end>ContainerResponseFilter");
            System.out.println("\n==================================================================\n");
        }
    }

    @ApplicationPath("webresources")
    public static class MyApplication extends Application {

        // This ensures that the filters are automatically discovered. If the filter is not marked with
        // @Provider, it needs to be explicitly registered in the Application class:
        @Override
        public Set<Class<?>> getClasses() {
            Set<Class<?>> resources = new java.util.HashSet<>();
            resources.add(ServerLoggingFilter.class);
            resources.add(MyResource.class);
            return resources;
        }
    }

    // As the names indicate, ClientRequestFilter and ClientResponseFilter are clientside filters
    //
    // The client-side or server-side filters may be implemented by the same class or different classes:
    public static class ClientLoggingFilter implements ClientRequestFilter, ClientResponseFilter {

        // This code shows a simple client-side filter that will log the headers sent as part of the request and received in the response message.
        //
        // ClientRequestContext provides request-specific information for the filter, such as the request URI, message headers,
        // and message entity or request-scoped properties. ClientResponseContext provides response-specific information for the filter,
        // such as message headers, the message entity, or response-scoped properties.
        @Override
        public void filter(ClientRequestContext crc) throws IOException {
            System.out.println("\n==================================================================\n");
            System.out.println("<start>ClientRequestFilter");
            System.out.println(crc.getMethod() + " " + crc.getUri());

            for (Entry<String, List<Object>> e : crc.getHeaders().entrySet()) {
                System.out.print(e.getKey() + ": " + e.getValue());
            }

            crc.getHeaders().add("clientHeader", "clientHeaderValue");

            System.out.println("\n<end>ClientRequestFilter");
            System.out.println("\n==================================================================\n");
        }

        @Override
        public void filter(ClientRequestContext crc, ClientResponseContext crc1) throws IOException {

            System.out.println("\n==================================================================\n");
            System.out.println("<start>ClientResponseFilter");

            for (Entry<String, List<String>> entry : crc1.getHeaders().entrySet()) {
                System.out.print(entry.getKey() + ": " + entry.getValue());
            }

            System.out.println("\n<end>ClientResponseFilter");
            System.out.println("\n==================================================================\n");
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Order.class, MyApplication.class, MyResource.class, ServerLoggingFilter.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final Client client = ClientBuilder.newClient();
            // On the client side, you can register this filter using the client-side API:
            client.register(ClientLoggingFilter.class);

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
