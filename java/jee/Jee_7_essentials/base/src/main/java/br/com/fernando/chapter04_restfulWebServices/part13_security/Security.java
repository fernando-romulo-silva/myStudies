package br.com.fernando.chapter04_restfulWebServices.part13_security;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Priorities;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.jeecontainer.resources.MyEmbeddedJeeRealmFile;
import org.myembedded.jeecontainer.resources.MyEmbeddedJeeRealmFile.MyEmbeddedJeeRealmFileBuilder;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Security {

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

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Order other = (Order) obj;
            if (id != other.id)
                return false;
            return true;
        }
    }

    @Path("orders")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public static class MyResource {

        @RolesAllowed({ "ROLE01", "ROLE03" })
        @GET
        @Path("id/{oid}")
        public Order getOrder(@PathParam("oid") int id) {
            final Order order = findOrder(id);
            return order;
        }

        @RolesAllowed("ROLE02")
        @GET
        @Path("name/{oname}")
        public Order getOrder(@PathParam("oname") String name) {
            final Order order = findOrder(name);
            return order;
        }

        @PermitAll
        @GET
        public Response getCustomers() {

            final GenericEntity<List<Order>> entity = new GenericEntity<List<Order>>(ORDERS) {
            };

            return Response.ok(entity).build();
        }

        @DELETE
        @Path("{id}")
        public void delete(@PathParam("id") int id) {
            final Order order = findOrder(id);
            ORDERS.remove(order);
        }

        // ===========================================================
        private Order findOrder(final String name) {

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

    //   Jersey JAX-RS implementations ...
    //  
    //    @ApplicationPath("webresources")
    //    public static class MyResourceConfig extends ResourceConfig {
    //        public MyResourceConfig() {
    //            super(MyResource.class);
    //            register(RolesAllowedDynamicFeature.class);
    //        }
    //    }
    //
    //  Others implementations can use the org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature code ...
    //
    @Provider
    @Priority(Priorities.AUTHORIZATION)
    public static class AuthorizationFilter implements ContainerRequestFilter {

        @Context
        private ResourceInfo resourceInfo;

        @Override
        public void filter(final ContainerRequestContext requestContext) throws IOException {

            Method method = resourceInfo.getResourceMethod();

            // @DenyAll on the method takes precedence over @RolesAllowed and @PermitAll
            if (method.isAnnotationPresent(DenyAll.class)) {
                refuseRequest();
            }

            // @RolesAllowed on the method takes precedence over @PermitAll
            RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
            if (rolesAllowed != null) {
                performAuthorization(rolesAllowed.value(), requestContext);
                return;
            }

            // @PermitAll on the method takes precedence over @RolesAllowed on the class
            if (method.isAnnotationPresent(PermitAll.class)) {
                // Do nothing
                return;
            }

            // @DenyAll can't be attached to classes

            // @RolesAllowed on the class takes precedence over @PermitAll on the class
            rolesAllowed = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
            if (rolesAllowed != null) {
                performAuthorization(rolesAllowed.value(), requestContext);
            }

            // @PermitAll on the class
            if (resourceInfo.getResourceClass().isAnnotationPresent(PermitAll.class)) {
                // Do nothing
                return;
            }

            // Authentication is required for non-annotated methods
            if (!isAuthenticated(requestContext)) {
                refuseRequest();
            }
        }

        private void performAuthorization(final String[] rolesAllowed, final ContainerRequestContext requestContext) {

            if (rolesAllowed.length > 0 && !isAuthenticated(requestContext)) {
                refuseRequest();
            }

            for (final String role : rolesAllowed) {
                if (requestContext.getSecurityContext().isUserInRole(role)) {
                    return;
                }
            }

            refuseRequest();
        }

        private boolean isAuthenticated(final ContainerRequestContext requestContext) {
            // Return true if the user is authenticated or false otherwise           
            // An implementation could be like:
            return requestContext.getSecurityContext().getUserPrincipal() != null;
        }

        private void refuseRequest() {
            throw new IllegalStateException("You don't have permissions to perform this action.");
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(MyResource.class, Order.class, MyApplication.class, AuthorizationFilter.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/part13_Security/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("glassfish-web.xml", "src/main/resources/chapter04_restfulWebServices/part13_Security/glassfish-web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final java.nio.file.Path fileRealmPath = Paths.get(Security.class.getResource("/chapter04_restfulWebServices/part13_Security/keyfile").toURI());
            final MyEmbeddedJeeRealmFile realm = new MyEmbeddedJeeRealmFileBuilder("myRealm", fileRealmPath) //
                    .withDigestAlgorithm("SSHA256") // Default
                    .withEncoding("Hex") // Default
                    .build();

            embeddedJeeServer.addFileRealm(realm);

            // https://dennis-xlc.gitbooks.io/restful-java-with-jax-rs-2-0-en/en/part1/chapter15/client_security.html
            // https://www.baeldung.com/httpclient-4-basic-authentication#raw
            // https://stackoverflow.com/questions/22095289/basic-authentication-with-resteasy-client

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // ---------------------------------------------------------------------------------------
            final Client client = ClientBuilder.newClient();
            client.register(new BasicAuthenticator("user02", "changeit"));

            Response serviceResponse01 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/orders") //
                    .request(MediaType.APPLICATION_XML) //
                    .get(Response.class);

            if (serviceResponse01.getStatus() == 200) {

                final List<Order> newOrderResult = serviceResponse01.readEntity(new GenericType<List<Order>>() {
                });

                System.out.println(newOrderResult);
            } else {
                System.out.println(serviceResponse01);
            }

            // ----------------------------------------------------------------------------------------------------
            Response serviceResponse02 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/orders/id") //
                    .path("{oid}") //
                    .resolveTemplate("oid", 4) //
                    .request() //
                    .get(Response.class);

            if (serviceResponse02.getStatus() == 200) {
                final Order order = serviceResponse02.readEntity(Order.class);
                System.out.println(order);
            } else {
                System.out.println(serviceResponse02);
            }

            System.out.println("the end");

            // jaxrs-security
            // jsonp

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

    public static class BasicAuthenticator implements ClientRequestFilter {

        private final String user;

        private final String password;

        public BasicAuthenticator(final String user, final String password) {
            this.user = user;
            this.password = password;
        }

        @Override
        public void filter(ClientRequestContext requestContext) throws IOException {
            MultivaluedMap<String, Object> headers = requestContext.getHeaders();
            final String basicAuthentication = getBasicAuthentication();
            headers.add("Authorization", basicAuthentication);
        }

        private String getBasicAuthentication() {
            String token = this.user + ":" + this.password;
            try {
                return "BASIC " + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                throw new IllegalStateException("Cannot encode with UTF-8", ex);
            }
        }
    }

}
