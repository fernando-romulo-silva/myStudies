package br.com.fernando.chapter04_restfulWebServices.part04_multipleResourceRepresentations;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class MultipleResourceRepresentations {

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

    @Path("orders01")
    public static class OrderResource01 {

        // By default, a RESTful resource is published or consumed with the */* MIME type.
        //
        // The following resource method will be dispatched if the HTTP Accept header specifies any application MIME type such as
        // application/xml, application/json, or any other media type:
        @GET
        @Path("{oid}")
        @Produces("application/*")
        public Response getOrder(@PathParam("oid") final int id) {
            final Order order = findOrder(id);

            return Response.status(Response.Status.OK).entity(order).build();
        }
    }

    @Path("orders02")
    @Produces(MediaType.TEXT_PLAIN)
    public static class OrderResource02 {

        // A RESTful resource can restrict the media types supported by request and response using
        // the @Consumes and @Produces annotations, respectively.
        @GET
        @Path("{oid}")
        // The resource method can generate an XML or JSON representation of Order
        @Produces({ "application/xml", "application/json" })
        public Response getOrder(@PathParam("oid") final int id) {
            final Order order = findOrder(id);

            // JAX-RS will convert your objects to JSON, no need to do it manually.
            return Response.status(Response.Status.OK).entity(order).build();
        }

        // JAX-RS 2.0 allows you to indicate a media preference on the server side using the qs (short for equality on server) parameter.
        // The 'qs' is a floating-point number with a value in the range of 0.000 through 1.000 and indicates the relative quality of a
        // representation compared to the others available, independent of the client's capabilities.
        // A representation with a qs value of 0.000 will never be chosen. A representation with no qs parameter value is given a qs factor of 1.0:
        @POST
        @Path("{oid}")
        @Consumes({ "application/xml; qs=0.75", "application/json; qs=1" })
        public Response createOrder(@FormParam("id") final int id, @FormParam("name") final String name) {
            final Order newOrder = new Order(id, name);

            ORDERS.add(newOrder);

            return Response.status(Response.Status.OK).build();
        }

        // If a client issues a request with no preference for a particular representation or with an Accept header of application/*;, 
        // then the server will select a representation with a higher qs value—application/json in this case.
    }

    // ----------------------------------------------------------------------------------------------------------------------
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

        public void setId(final int id) {
            this.id = id;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }

    @ApplicationPath("webresources")
    public static class ApplicationConfig extends Application {

    }

    public static Order findOrder(final int id) {

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

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Order.class, ApplicationConfig.class, OrderResource01.class, OrderResource02.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));
            war.addWebResourceFiles(EmbeddedResource.add("index.html", "src/main/resources/chapter04_restfulWebServices/part03_bindingHttpMethods/index.html"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);
            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            System.out.println("the end"); // use the browser

            // http://localhost:8080/embeddedJeeContainerTest/webresources/orders02/2

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
