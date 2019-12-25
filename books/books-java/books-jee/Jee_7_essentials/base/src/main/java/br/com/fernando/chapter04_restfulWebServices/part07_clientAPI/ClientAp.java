package br.com.fernando.chapter04_restfulWebServices.part07_clientAPI;

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
import java.util.concurrent.Future;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

import br.com.fernando.chapter04_restfulWebServices.part06_entityProviders.EntityProviders01.MyObject;
import br.com.fernando.chapter04_restfulWebServices.part06_entityProviders.EntityProviders01.MyReader;
import br.com.fernando.chapter04_restfulWebServices.part06_entityProviders.EntityProviders01.MyWriter;

public class ClientAp {

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

    @Path("orders")
    public static class MyResource {

        @GET
        @Path("{oid}")
        @Produces({ "application/xml", "application/json" })
        public Response getOrder(@PathParam("oid") int id) {
            final Order order = findOrder(id);

            return Response.status(Response.Status.OK).entity(order).build();
        }

        @POST
        @Consumes({ "application/xml", "application/json" })
        @Produces(MediaType.TEXT_PLAIN)
        public String createOrder(final Order newOrder) {

            ORDERS.add(newOrder);

            return "create " + newOrder + " OK";
        }

        @DELETE
        @Path("{id}")
        @Produces(MediaType.TEXT_PLAIN)
        public String deleteOrder(@PathParam("id") int id) {
            final Order order = findOrder(id);

            ORDERS.remove(order);

            return "delete " + order + " OK";
        }
    }

    @Path("endpoint")
    public static class EndPoint {

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public MyObject echoObject(final MyObject mo) {
            return mo;
        }
    }

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

    public static Order findOrder(int id) {

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

    @ApplicationPath("webresources")
    public static class MyApplication extends Application {

    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(MyResource.class, EndPoint.class, MyApplication.class, MyWriter.class, MyReader.class, MyObject.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);
            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // JAX-RS 2 adds a new Client API that can be used to access web resources and provides integration with JAX-RS providers.

            // ----------------------------------------------------------------------------------------
            // * ClientBuilder is the entry point to the client API.
            // It is used to obtain an instance of Client that uses method chaining to build and execute client requests in order to consume the responses returned.
            // Client s are heavyweight objects that manage the client-side communication infrastructure.
            // It is therefore recommended that you construct only a small number of Client instances in the application.
            final Client client = ClientBuilder.newClient();

            final Order order = client //
                    // * We create WebTarget by specifying the URI of the web resource.
                    // We then use these targets to prepare client request invocation by resolving the URI template, using the resolveTemplate method for different names.
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/orders") //
                    // We can specify additional query or matrix parameters here using the queryParam and matrixParam methods, respectively.
                    .path("{oid}") //
                    .resolveTemplate("oid", 1) //
                    // * We build the client request by invoking the request method.
                    .request() //
                    // * We invoke the HTTP GET method by calling the get method. The Java type of the response entity is specified as the parameter to the invoked method.
                    .get(Order.class);
            System.out.println(order);

            // ----------------------------------------------------------------------------------------

            // We can make HTTP POST or PUT requests by using the post or put methods and HTTP DELETE request by identifying the resource with the URI and
            // using the delete method

            String newOrderResult = client.target("http://localhost:8080/embeddedJeeContainerTest/webresources/orders") //
                    .request() //
                    .post(Entity.entity(new Order(100, "Order100"), "application/json"), String.class);

            System.out.println(newOrderResult);

            //

            String deleteOrderResult = client.target("http://localhost:8080/embeddedJeeContainerTest/webresources/orders") //
                    .path("{oid}") //
                    .resolveTemplate("oid", 1).request() //
                    .delete(String.class);

            System.out.println(deleteOrderResult);

            // ----------------------------------------------------------------------------------------
            client // We need to explicity register entity providers using the register method:
                    .register(MyReader.class).register(MyWriter.class);

            WebTarget target = client.target("http://localhost:8080/embeddedJeeContainerTest/webresources/endpoint");
            MyObject mo = target.request().post(Entity.entity(new MyObject("Duke", 18), MediaType.APPLICATION_JSON), MyObject.class);
            System.out.println(mo);

            // By default, the client is invoked synchronously but can be invoked asynchronously if we call the async method as part of
            // the fluent builder API and (optionally)
            Future<Order> future01 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/orders") //
                    .path("{oid}") //
                    .resolveTemplate("oid", 1) //
                    .request() //
                    // the call to get after async is called returns immediately without blocking the caller's thread.
                    .async() //
                    .get(Order.class);

            // The response is a Future instance that can be used to monitor or cancel asynchronous invocation or retrieve results.
            Order orderFromFuture01 = future01.get();
            System.out.println(orderFromFuture01);

            // ----------------------------------------------------------------------------------------
            // Optionally, InvocationCallback can be registered to receive the events from the asynchronous invocation processing:
            //
            Future<Order> future02 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/orders") //
                    .path("{oid}") //
                    .resolveTemplate("oid", 1) //
                    .request() //
                    .async() //
                    // register an instance of InvocationCallback
                    .get(new InvocationCallback<Order>() {

                        // The completed method is called on successful completion of invocation, and the response data is made available in the parameter "o"
                        @Override
                        public void completed(Order o) {
                            System.out.println("Complete!");
                        }

                        // The failed method is called when the invocation is failed for any reason, and the parameter t contains failure details.
                        @Override
                        public void failed(Throwable t) {
                            System.out.println("Failed!");
                        }
                    });

            Order orderFromFuture02 = future02.get();
            System.out.println(orderFromFuture02);

            // ----------------------------------------------------------------------------------------
            // A more generic client request can be prepared and executed at a later time.
            // This enables a separation of concerns between the creator and the submitter:
            //
            Invocation i1 = client // In this code, a GET request is prepared and stored as i1
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/orders") //
                    .path("{oid}") //
                    .resolveTemplate("oid", 1) //
                    .request() //
                    .buildGet();

            // These requests are then executed at a later time via the invoke method and the result retrieved.
            final Response r01 = i1.invoke();
            // In the first case, a more generic Response is returned, which can then be used to extract the result and other metadata about it
            final Object entity01 = r01.getEntity();
            System.out.println(entity01);
            //
            // or
            //
            // an Order instance is returned because of the type specified before.
            final Order entityResult02 = i1.invoke(Order.class);
            System.out.println(entityResult02);

            // ----------------------------------------------------------------------------------------
            //
            // In this code, we submit the Invocations for asynchronous execution using the submit method.
            // A Future response object is returned in both cases. After waiting for some time, we can obtain the first result by calling
            // the get method on the returned Future.
            //
            Invocation i2 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/orders") //
                    .path("{oid}") //
                    .resolveTemplate("oid", 1) //
                    .request() //
                    .buildGet();

            Future<Response> f02 = i2.submit();
            Response r02 = f02.get();
            Object entityResult03 = r02.getEntity();
            // or
            r02.readEntity(Order.class);

            System.out.println(entityResult03);
            //
            // or
            //
            Future<Order> f03 = i2.submit(Order.class);
            Order entityResult04 = f03.get();
            System.out.println(entityResult04);

            // -------------
            System.out.println("the end"); // use the browser

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
