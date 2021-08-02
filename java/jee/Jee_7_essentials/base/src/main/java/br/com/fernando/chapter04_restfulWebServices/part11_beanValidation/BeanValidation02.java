package br.com.fernando.chapter04_restfulWebServices.part11_beanValidation;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import java.io.File;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class BeanValidation02 {

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Name {

        @NotNull
        @Size(min = 1)
        @XmlElement(required = true)
        private String firstName;

        @NotNull
        @Size(min = 1)
        @XmlElement(required = true)
        private String lastName;

        @Email
        @XmlElement(required = true)
        private String email;

        public Name() {
        }

        public Name(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------------------------
    @Path(NameAddResource.PATH)
    public static class NameAddResource {

        static final String PATH = "/nameadd";

        @POST
        @Consumes(APPLICATION_XML)
        public String addUser(@Valid Name name) {
            return name.getFirstName() + " " + name.getLastName() + " with email " + name.getEmail() + " added";
        }
    }

    @Path("/names1")
    public static class NameResource1 {

        @NotNull
        @Size(min = 1)
        @FormParam("firstName")
        private String firstName;

        @NotNull
        @Size(min = 1)
        @FormParam("lastName")
        private String lastName;

        private String email;

        @FormParam("email")
        public void setEmail(@Email String email) {
            this.email = email;
        }

        @Email
        public String getEmail() {
            return email;
        }

        @POST
        @Consumes("application/x-www-form-urlencoded")
        public String registerUser() {
            return firstName + " " + lastName + " with email " + email + " registered";
        }
    }

    @Path("/names2")
    public static class NameResource2 {

        @POST
        @Consumes("application/x-www-form-urlencoded")
        public String registerUser( //
                @NotNull @FormParam("firstName") String firstName, //
                @NotNull @FormParam("lastName") String lastName, //
                @Email @FormParam("email") String email) { //

            return firstName + " " + lastName + " with email " + email + " registered";
        }
    }

    @Path("/names")
    @NotNullAndNonEmptyNames
    public static class NameResource3 {

        @FormParam("firstName")
        private String firstName;

        @FormParam("lastName")
        private String lastName;

        private String email;

        @FormParam("email")
        public void setEmail(@Email String email) {
            this.email = email;
        }

        // @Email
        public String getEmail() {
            return email;
        }

        @POST
        @Consumes("application/x-www-form-urlencoded")
        public String registerUser() {
            return firstName + " " + lastName + " with email " + email + " registered";
        }

    }

    // -----------------------------------------------------------------------------------------------------------------------------------
    public static class EmailValidator implements ConstraintValidator<Email, String> {

        @Override
        public void initialize(Email constraintAnnotation) {
            System.out.println("EmailValidator.initialize");
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            System.out.println("EmailValidator.isValid: " + value);
            return value != null && value.contains("@") && value.contains(".com");
        }
    }

    @Documented
    @Target({ ANNOTATION_TYPE, METHOD, FIELD, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Constraint(validatedBy = EmailValidator.class)
    @Size(min = 5, message = "{org.javaee7.jaxrs.resource_validation.min_size}")
    @NotNull(message = "{org.javaee7.jaxrs.resource_validation.cannot_be_null}")
    public static @interface Email {

        String message() default "{org.javaee7.jaxrs.resource_validation.invalid_email}";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    @Documented
    @Target({ ANNOTATION_TYPE, TYPE })
    @Retention(RUNTIME)
    @Constraint(validatedBy = EmailValidator.class)
    @Size(min = 1, message = "{org.javaee7.jaxrs.resource_validation.min_size}")
    @NotNull(message = "{org.javaee7.jaxrs.resource_validation.cannot_be_null}")
    public static @interface NotNullAndNonEmptyNames {

        String message() default "{org.javaee7.jaxrs.resource_validation.non_null_non_empty_names}";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    // -----------------------------------------------------------------------------------------------------------------------------------
    @ApplicationPath("webresources")
    public static class MyApplication extends Application {

        static final String PATH = "webresources";
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(NotNullAndNonEmptyNames.class, MyApplication.class, Email.class, EmailValidator.class, Name.class, NameAddResource.class, NameResource1.class, NameResource2.class, NameResource3.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final Client client = ClientBuilder.newClient();

            // ----------------------------------------------------------------------------------------------------
            final Name name = new Name(//
                    "Sheldon", //
                    "Cooper", //
                    "random@example.com"); //

            final Response response01 = client //
                    .target("http://localhost:8080/" + EMBEDDED_JEE_TEST_APP_NAME + "/" + MyApplication.PATH + NameAddResource.PATH) //
                    .request() //
                    .post(Entity.xml(name));

            System.out.println(response01);

            // ----------------------------------------------------------------------------------------------------
            name.setEmail("missing-at-symbol.com");

            final Response response02 = client //
                    .target("http://localhost:8080/" + EMBEDDED_JEE_TEST_APP_NAME + "/" + MyApplication.PATH + NameAddResource.PATH) //
                    .request() //
                    .post(Entity.xml(name));

            System.out.println(response02);

            // ----------------------------------------------------------------------------------------------------

            final List<WebTarget> targets = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                targets.add(client.target("http://localhost:8080/" + EMBEDDED_JEE_TEST_APP_NAME + "/webresources/names" + (i + 1)));
            }

            for (final WebTarget target : targets) {

                System.out.println("----------------------------------------------------------------------------------------");
                System.out.println(target.getUri());

                System.out.println("POSTing with valid data ...");

                final MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
                map.add("firstName", "Sheldon");
                map.add("lastName", "Cooper");
                map.add("email", "random@example.com");

                Response r = target.request().post(Entity.form(map));
                System.out.println(r);

                // ----------------------------------------------------------------------------------------
                System.out.println();

                System.out.println("POSTing with invalid (null) \"firstName\" ...");

                map.putSingle("firstName", null);
                r = target.request().post(Entity.form(map));
                System.out.println(r);

                // ----------------------------------------------------------------------------------------
                System.out.println();

                System.out.println("POSTing with invalid (null) \"lastName\" ...");

                map.putSingle("firstName", "Sheldon");
                map.putSingle("lastName", null);
                r = target.request().post(Entity.form(map));
                System.out.println(r);

                // ----------------------------------------------------------------------------------------
                System.out.println();
                System.out.println("POSTing with invalid (missing @) email \"email\" ...");
                map.putSingle("lastName", "Cooper");
                map.putSingle("email", "randomexample.com");
                r = target.request().post(Entity.form(map));
                System.out.println(r);

                // ----------------------------------------------------------------------------------------
                System.out.println();
                System.out.println("POSTing with invalid (missing .com) email \"email\"");
                map.putSingle("email", "random@examplecom");
                r = target.request().post(Entity.form(map));
                System.out.println(r);
            }

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
