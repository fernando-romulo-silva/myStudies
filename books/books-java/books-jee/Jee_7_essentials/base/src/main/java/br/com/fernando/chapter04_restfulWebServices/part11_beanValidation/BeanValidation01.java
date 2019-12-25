package br.com.fernando.chapter04_restfulWebServices.part11_beanValidation;

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

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class BeanValidation01 {

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

        @NotNull
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

    // Bean Validation 1.1 allows declarative validation of resources.
    // The constraints can be specified in any location in which the JAX-RS binding annotations are allowed, with the exception of constructors and property setters:
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Person {

        @NotNull
        @Size(min = 1)
        @FormParam("firstName")
        private String firstName;

        @FormParam("lastName")
        private String lastName;

        @Email
        @FormParam("email")
        private String email;

        public Person() {
            super();
        }

        public Person(final String firstName, final String lastName) {
            super();
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        @Override
        public String toString() {
            return "Person: firstName: " + firstName + " lastName: " + lastName;
        }
    }

    @Path("persons")
    public static class PersonResource {

        @POST
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)

        // Alternatively, if the request entity is mapped to a bean that is decorated with constraint annotations already, 
        // then @Valid can be used to trigger the validation:
        public String savePerson(@Valid @BeanParam Person person) {

            System.out.println("Person: " + person);

            // return Response.status(Response.Status.OK).entity(person.toString()).build();
            return "Done";
        }
    }

    // JAX-RS constraint validations are carried out in the Default validation group only. Processing other validation groups is not required.

    @Path("orders")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public static class MyResource {

        // You can map request entity bodies to resource method parameters and validate them
        // by specifying the constraint on the resource method parameter:

        @GET
        @Path("id/{oid}")
        public Response getOrder(@Min(1) @Max(10) @PathParam("oid") int id) {
            final Order order = findOrder(id);
            return Response.status(Response.Status.OK).entity(order).build();
        }

        @GET
        @Path("name/{oname}")
        public Response getOrder(@Size(min = 3) @PathParam("oname") String name) {
            final Order order = findOrder(name);
            return Response.status(Response.Status.OK).entity(order).build();
        }

        private Order findOrder(String name) {

            final Iterator<Order> iterator = ORDERS.iterator();
            Order result = null;

            while (iterator.hasNext() && result == null) {
                result = iterator.next();
                if (!StringUtils.equalsIgnoreCase(name, result.getName())) {
                    result = null;
                }
            }
            return result;
        }

        private Order findOrder(int id) {

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

    @ApplicationPath("webresources")
    public static class MyApplication extends Application {
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Order.class, MyApplication.class, MyResource.class, Person.class, PersonResource.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final Client client = ClientBuilder.newClient();

            // ----------------------------------------------------------------------------------------------------
            final Order order = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/orders/id") //
                    .path("{oid}") //
                    .resolveTemplate("oid", 4) //
                    .request() //
                    .get(Order.class);

            System.out.println(order);

            // ----------------------------------------------------------------------------------------------------

            final Form form = new Form();

            form.param("firstName", "Maria") //
                    .param("lastName", "Silva")//
                    .param("email", "maria@gmail.com") //
            ;

            String newOrderResult = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/persons") //
                    .request(MediaType.APPLICATION_FORM_URLENCODED) //
                    .post(Entity.form(form), String.class);

            System.out.println(newOrderResult);

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
