package br.com.fernando.chapter04_restfulWebServices.part09_filters;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.NameBinding;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Filters02 {

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

    // On the server side, the filters can be registered in four different ways:
    //
    // 1 - Globally bound to all resources and all methods in them:
    // By default, if no annotation is specified on the filter, then it is globally bound—that is, it all applies to methods on all resources in an application.
    //
    // 2 - Statically bound to a specific resource/method via the meta-annotation @NameBinding
    // A filter may be statically targeted to a resource class or method via the metaannotation @NameBinding as follows:
    //
    @NameBinding // @NameBinding annotation is ignored on filters marked with @PreMatch.
    @Target({ ElementType.TYPE, ElementType.METHOD })
    @Retention(value = RetentionPolicy.RUNTIME)
    public static @interface ServerLogged {
    }

    @Path("orders")
    @ServerLogged // This annotation then needs to be specified on the filter implementation and the resource class and/or method
    public static class MyResource {

        // If the annotation '@ServerLogged' is specified on a resource, then the filter is applied to all methods of the resource.
        // If the annotation is specified on a specific resource method, then the filter is applied only when that particular method is invoked.
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

    // ContainerRequestFilter comes in two flavors: pre-match and post-match.
    // A prematch filter is applied globally on all resources before the resource is matched with the incoming HTTP request.
    // A pre-match filter is typically used to update the HTTP method in the request and is capable of altering the matching algorithm.
    //
    // A post-match filter is applied after the resource method has been matched.
    //
    @Provider
    // You can convert any ContainerRequestFilter to a pre-match filter by adding the @PreMatching annotation.
    @PreMatching
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

    @Provider
    // This '@ServerLogged' annotation then needs to be specified on the filter implementation and the resource class and/or method:
    @ServerLogged
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

    //
    // 3 - Globally bound to all resources and methods via the meta-annotation @NameBinding
    // The annotation may be specified on the Application ('MyApplication') class and then the filter becomes globally enabled on all methods for all resources
    @ApplicationPath("webresources")
    // @ServerLogged
    public static class MyApplication extends Application {
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Order.class, MyApplication.class, MyResource.class, ServerRequestLoggingFilter.class, ServerResponseLoggingFilter.class);

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
