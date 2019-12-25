package br.com.fernando.chapter04_restfulWebServices.part08_mappingExceptions;

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
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class MappingExceptions {

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

    // An application-specific exception may be thrown from within the resource method and propagated to the client. 

    @Path("orders")
    public static class MyResource {

        @GET
        @Path("{oid}")
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        public Response getOrder(@PathParam("oid") int id) {
            final Order order = findOrder(id);

            // The method getOrder may look like:
            if (order == null) {
                throw new OrderNotFoundException();
            }

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

    // The application can supply checked or exception mapping to an instance of the Response class. 
    // Let's say the application throws the following exception if an order is not found:
    public static class OrderNotFoundException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public OrderNotFoundException() {
        }

        public OrderNotFoundException(int id) {
            super("Order not found: " + id);
        }

        public OrderNotFoundException(String msg) {
            super(msg);
        }
    }

    // The exception mapper will look like:
    @Provider
    public static class OrderNotFoundExceptionMapper implements ExceptionMapper<OrderNotFoundException> {

        @Override
        public Response toResponse(OrderNotFoundException exception) {
            return Response //
                    .status(Response.Status.PRECONDITION_FAILED) //
                    .entity("Response not found") //
                    .build();
        }

        // This ensures that the client receives a formatted response instead of just the exception being propagated from the resource.
    }

    @ApplicationPath("webresources")
    public static class MyApplication extends Application {
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Order.class, MyApplication.class, MyResource.class, OrderNotFoundException.class, OrderNotFoundExceptionMapper.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final Client client = ClientBuilder.newClient();

            try {

                final Order order = client //
                        .target("http://localhost:8080/embeddedJeeContainerTest/webresources/orders") //
                        .path("{oid}") //
                        .resolveTemplate("oid", 1000) // don't exist order with id 1000
                        .request() //
                        .get(Order.class);

                System.out.println(order);

            } catch (final ClientErrorException ex) {
                System.out.println(ex.getResponse().getStatus()); // must be 412
            } catch (final Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
