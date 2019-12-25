package br.com.fernando.chapter04_restfulWebServices.part03_bindingHttpMethods;

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
import java.util.List;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class BindingHttpMethods {

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

    // JAX-RS provides support for binding standard HTTP GET, POST, PUT, DELETE, HEAD, and OPTIONS methods using the corresponding annotations

    @Path("orders")
    @Produces(MediaType.TEXT_PLAIN)
    public static class OrderResource {

        // Consider the following HTML form, which takes the order identifier and customer name and creates an order by posting the form to
        // webresources/orders:
        /**
         * <code>
         * 
         * <form method="post" action="webresources/orders">
         *  Order Number: <input type="text" name="id"/><br/>
         *  Customer Name: <input type="text" name="name"/><br/>
         *  <input type="submit" value="Create Order"/>
         * </form>
         * 
         * </code>
         */
        // The updated resource definition uses the following annotations:
        @POST
        @Consumes("application/x-www-form-urlencoded") // MediaType.APPLICATION_FORM_URLENCODED
        public String createOrder(@FormParam("id") int id, @FormParam("name") String name) {

            // The @FormParam annotation binds the value of an HTML form parameter to a resource
            // method parameter or a field. The name attribute in the HTML form and the value of the
            // @FormParam annotation are exactly the same to ensure the binding

            final Order newOrder = new Order(id, name);

            ORDERS.add(newOrder);

            return "create OK";
        }

        // The resource method is marked as a subresource, and {id} is bound to the resource
        // method parameter id. The contents of the body can be any XML media type as defined
        // by @Consumes and are bound to the content method parameter.
        @PUT
        @Path("{id}")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        // @Consumes("*/xml") // @Consumes(MediaType.TEXT_XML)
        public String putXml(@PathParam("id") int id, @FormParam("name") String newName) {

            // curl -i -X PUT -d "New Order" http://localhost:8080/embeddedJeeContainerTest/webresources/orders/1

            final Order order = findOrder(id);

            int index = ORDERS.indexOf(order);

            order.setName(newName);

            ORDERS.add(index, order);

            return "update OK";
        }

        @DELETE
        @Path("{id}")
        public void delete(@PathParam("id") int id) {
            // The resource method is marked as a subresource, and {id} is bound to the resource
            // method parameter id. A DELETE request to this resource may be issued as:
            //
            // curl -i -X DELETE http://localhost:8080/embeddedJeeContainerTest/webresources/orders/1
            final Order order = findOrder(id);

            ORDERS.remove(order);
        }

        // The HEAD and OPTIONS methods receive automated support from JAX-RS
        //
        // The HTTP HEAD method is identical to GET except that no response body is returned.
        // This method is typically used to obtain metainformation about the resource without requesting the body.
        @HEAD
        @Path("{id}")
        public void headOrder(@PathParam("id") int id) {
            System.out.println("HEAD");

            // This method is often used for testing hypertext links for validity, accessibility, and recent modification.
            // A HEAD request to this resource may be issued as:
            //
            // curl -i -X HEAD http://localhost:8080/embeddedJeeContainerTest/webresources/orders/1
        }

        // If no method is designated with @OPTIONS, the JAX-RS runtime generates an automatic response using the annotations
        // on the matching resource class and methods. The default response typically works in most cases
        //
        // The HTTP Allow response header provides information about the HTTP operations permitted. 
        // The Content-Type header is used to specify the media type of the body, if any is included.
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

    @ApplicationPath("webresources")
    public static class ApplicationConfig extends Application {

    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Order.class, ApplicationConfig.class, OrderResource.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));
            war.addWebResourceFiles(EmbeddedResource.add("index.html", "src/main/resources/chapter04_restfulWebServices/part03_bindingHttpMethods/index.html"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final List<NameValuePair> form = new ArrayList<>();
            form.add(new BasicNameValuePair("name", "OrderNew"));

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpPut request = new HttpPut("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/webresources/orders/2");
            request.setEntity(new UrlEncodedFormEntity(form, Consts.UTF_8));

            final HttpResponse response = httpClient.execute(request);
            System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
