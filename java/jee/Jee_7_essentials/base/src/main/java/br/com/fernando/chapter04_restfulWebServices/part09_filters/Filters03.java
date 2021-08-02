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

import javax.annotation.Priority;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Priorities;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Filters03 {

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

    // Dynamically bound to a specific resource/method via DynamicFeature
    // A non−globally bound filter (i.e., a filter annotated with @NameBinding) can be dynamically bound to a resource or a method within a resource via DynamicFeature. 
    // For example, the following feature binds all resource methods in MyResource that are annotated with @GET:
    @Provider
    public static class DynamicServerLogggingFilterFeature implements DynamicFeature {

        @Override
        public void configure(ResourceInfo ri, FeatureContext fc) {
            if (MyResource.class.isAssignableFrom(ri.getResourceClass()) && ri.getResourceMethod().isAnnotationPresent(GET.class)) {
                fc.register(new ServerRequestLoggingFilter());
                fc.register(new ServerResponseLoggingFilter());
            }
            // This feature is marked with @Provider and is thus automatically discovered by the JAX-RS runtime. 
            // Dynamic binding is ignored on filters marked with @PreMatch.
        }
    }

    // Multiple filters may be implemented at each extension point and arranged in filter chains. 
    // Filters in a chain are sorted based on their priorities and are executed in order.
    // 
    // Filters in a chain are sorted based on their priorities and are executed in order.     
    // Priorities are defined through the @javax.annotation.Priority annotation and represented by integer numbers.
    //
    // The priorities for ClientRequestFilter and ContainerRequestFilter are sorted in ascending order; the lower the number, the higher the priority. 
    // The priorities for ContainerResponseFilter and ClientResponseFilter are sorted in descending order; the higher the number, the higher the priority. 
    // These rules ensure that response filters are executed in reverse order of request filters
    // 
    // The Priorities class defines built-in priorities for security, decoders/encoders, and more. 
    // The default binding priority is Priorities.USER.
    @Priority(Priorities.USER)
    public static class ServerRequestLoggingFilter implements ContainerRequestFilter {

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
    }

    @Priority(Priorities.HEADER_DECORATOR)
    public static class ServerResponseLoggingFilter implements ContainerResponseFilter {

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

    // ClientRequestFilter and ContainerRequestFilter can stop the execution of their corresponding chains by calling abortWith(Response).

    @ApplicationPath("webresources")
    public static class MyApplication extends Application {
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Order.class, MyApplication.class, MyResource.class, DynamicServerLogggingFilterFeature.class, ServerRequestLoggingFilter.class, ServerResponseLoggingFilter.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final Client client = ClientBuilder.newClient();

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
