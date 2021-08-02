package br.com.fernando.chapter04_restfulWebServices.part01_resource;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Resource01 {

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

    //  OrderResource is a POJO class and is published as a RESTful resource at the orders path when we add the class-level @Path annotation.
    // 
    @Path("orders01")
    @Produces(MediaType.APPLICATION_XML)
    public static class OrderResource01 {

        // webresources/orders01
        //
        // The getAll resource method, which provides a list of all orders, is invoked when we access this resource using the HTTP GET method; 
        // we identify it by specifying the @GET annotation on the method.
        @GET
        public List<Order> getAll() {
            return ORDERS;
        }

        // webresources/orders01/1
        //
        // The @Path annotation on the getOrder resource method marks it as a subresource that is accessible at orders/{oid} 
        @GET
        @Path("{oid}")
        // The @PathParam can also be used to bind template parameters to a resource class field.
        public Order getOrder(@PathParam("oid") int id) {
            // The curly braces around oid identify it as a template parameter and bind its value at runtime to the id parameter of the getOrder resource method.

            final Iterator<Order> iterator = ORDERS.iterator();
            Order result = null;
            while (iterator.hasNext() && result == null) {
                result = iterator.next();
                if (result.getId() != id) {
                    result = null;
                }
            }
            return result;

            // You can access a list of all the orders by issuing a GET request to:
            // http://localhost:8080/embeddedJeeContainerTest/webresources/orders01/2

            // Here, the value '2' will be passed to getOrder 's method parameter id . The resource method will locate the order with the correct order number and 
            // return back the Order class. 
            // The @XmlRootElement annotation ensures that an automatic mapping from Java to XML occurs following JAXB mapping and an XML representation of the resource
            // is returned.
        }
    }

    @Path("orders02")
    @Produces(MediaType.APPLICATION_XML)
    public static class OrderResource02 {

        // A URI may pass HTTP query parameters using name/value pairs. 
        // You can map these to resource method parameters or fields using the @QueryParam annotation. 
        // If the resource method getAll is updated such that the returned results start from a specificorder number, the number of orders returned can also be specified:
        //
        @GET
        public List<Order> getAll(@DefaultValue("1") @QueryParam("from") Integer from, @QueryParam("to") Integer to) {
            return ORDERS.subList(from, to);

            // and the resource is acessed as:
            //http://localhost:8080/embeddedJeeContainerTest/webresources/orders02?from=2&to=3
        }

        @GET
        @Path("all")
        @Produces({ "application/xml", "application/json" })
        public List<Order> getList() {
            Orders orders = new Orders();
            orders.add(new Order(100, "Order100"));
            orders.add(new Order(102, "Order101"));
            orders.add(new Order(103, "Order102"));
            return orders;
        }

        @Path("array")
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public JsonArray echoArray(final JsonArray ja) {
            return ja;
        }

        @Path("object")
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public JsonObject echoObject(final JsonObject jo) {
            return jo;
        }

        @Path("link")
        @GET
        public Response get() throws URISyntaxException {
            return Response.ok().link("http://oracle.com", "parent"). // 
                    link(new URI("http://jersey.java.net"), "framework"). //
                    links( //
                            Link.fromUri("test1").rel("test1").build(), //
                            Link.fromUri("test2").rel("test2").build(), //
                            Link.fromUri("test3").rel("test3").build())
                    .build(); //
        }
    }

    //  The Order class is marked with the @XmlRootElement annotation, allowing a conversion between Java and XML.
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
    }

    @XmlRootElement(name = "orders", namespace = "orders")
    public static class Orders extends ArrayList<Order> {

        private static final long serialVersionUID = 1L;

        @XmlElement(name = "orders")
        public List<Order> getOrders() {
            return this;
        }
    }

    // The Application class is defined:
    @ApplicationPath("webresources")
    public static class ApplicationConfig extends Application {

    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Order.class, Orders.class, ApplicationConfig.class, OrderResource01.class, OrderResource02.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();

            // -------------------------------------------------------------------------------------------
            final JsonObject jsonObject = Json.createObjectBuilder() //
                    .add("apple", "red") //
                    .add("banana", "yellow") //
                    .build();
            final StringEntity requestEntityJsonObject = new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON);

            final HttpPost postMethodJsonObject = new HttpPost("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/webresources/orders02/object");
            postMethodJsonObject.setEntity(requestEntityJsonObject);
            System.out.println(httpClient.execute(postMethodJsonObject));

            // -------------------------------------------------------------------------------------------
            final JsonArray jsonArray = Json.createArrayBuilder() //
                    .add(Json.createObjectBuilder() //
                            .add("apple", "red")) //
                    .add(Json.createObjectBuilder() //
                            .add("banana", "yellow")) //
                    .build();
            final StringEntity requestEntityJsonArray = new StringEntity(jsonArray.toString(), ContentType.APPLICATION_JSON);

            final HttpPost postMethodJsonArray = new HttpPost("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/webresources/orders02/array");
            postMethodJsonArray.setEntity(requestEntityJsonArray);
            System.out.println(httpClient.execute(postMethodJsonArray));

            // -------------------------------------------------------------------------------------------

            final HttpResponse responseOrders01 = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/webresources/orders01"));
            System.out.println(EntityUtils.toString(responseOrders01.getEntity(), "UTF-8"));

            final HttpResponse responseOrders01Id = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/webresources/orders01/3"));
            System.out.println(EntityUtils.toString(responseOrders01Id.getEntity(), "UTF-8"));

            final HttpResponse responseOrders02Params = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/webresources/orders02?from=2&to=3"));
            System.out.println(EntityUtils.toString(responseOrders02Params.getEntity(), "UTF-8"));

            final HttpResponse responseOrders03Params = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/webresources/orders02/all"));
            System.out.println(EntityUtils.toString(responseOrders03Params.getEntity(), "UTF-8"));

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
