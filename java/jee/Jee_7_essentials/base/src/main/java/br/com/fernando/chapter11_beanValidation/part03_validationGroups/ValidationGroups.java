package br.com.fernando.chapter11_beanValidation.part03_validationGroups;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.Serializable;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.GroupSequence;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class ValidationGroups {

    // By default, all constraints are defined in the Default validation group.
    // Also by default, all validation constraints are executed and in no particular order.
    //
    // A constraint may be defined in an explicitly created validation group in order to perform partial
    // validation of the bean or control the order in which constraints are evaluated.

    // A validation group is defined as an interface:
    public static interface GroupUserName {
    }

    public static interface GroupAddress {
    }

    public static interface ZipCodeGroup {
    }

    // Groups can inherit other groups through interface inheritance. You can define a new group that consists of Default and ZipCodeGroup:
    public static interface DefaultZipCodeGroup extends Default, ZipCodeGroup {
    }

    // @GroupSequence is used to define a sequence of groups in which the groups must be validated. 
    // This can be useful where simple validation constraints such as @NotNull or @Size can be validated before more complex constraints are enforced.
    //
    // If one of the groups from the sequence generates a constraint violation, the subsequent groups are not processed.
    // Specifying @GroupSequence on a class changes the default validation group for that class.
    @GroupSequence({ Default.class, GroupUserName.class, GroupAddress.class, DefaultZipCodeGroup.class })
    public static interface OrderedChecks {
    }

    public static class User {

        // This validation group can now be assigned to a constraint definition:
        @NotNull(groups = GroupUserName.class)
        String firstName;

        // By default, the Default validation group is not included if an explicit set of groups is specified:
        @NotNull(groups = { Default.class, GroupUserName.class })
        String lastName;

	// In this code, firstName and lastName will be validated only when the GroupAddress validation group is targeted for validation.
        //
        @NotNull(groups = GroupAddress.class)
        String streetAddress;

        //
        @NotNull(groups = GroupAddress.class)
        String country;

        // This new validation group can now be specified as part of the constraint, and is semantically equivalent to specifying two groups separately:
        @NotNull(groups = { DefaultZipCodeGroup.class, GroupAddress.class }) // 
        @Size(min = 5, groups = GroupAddress.class)
        String zipCode;

        @NotNull
        String userId;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(final String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(final String lastName) {
            this.lastName = lastName;
        }

        public String getStreetAddress() {
            return streetAddress;
        }

        public void setStreetAddress(final String streetAddress) {
            this.streetAddress = streetAddress;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(final String country) {
            this.country = country;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(final String zipCode) {
            this.zipCode = zipCode;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(final String userId) {
            this.userId = userId;
        }
    }

    // --------------------------------------------------------------------------------------------------
    // Partial validation of a bean may be required when validation of certain fields is optional or resource intensive.
    // And finally, pass the validation group in the JSF page using f:validateBean
    //
    // The fully qualified class name of the validation group needs to be specified in the validationGroups attribute of f:validateBean. 
    // Other pages will specify the corresponding validation group.
    /**
     * <pre>
     *     <h:form>
     *         <h:outputLabel value="Firts Name: " for="firstName" />
     *         <h:inputText value="#{userMB.user.name}" id="firstName">
     *              <f:validateBean validationGroups="br.com.fernando.chapter11_beanValidation.part03_validationGroups.ValidationGroups$GroupAddress"/>
     *         </h:inputText>
     *     
     *         <h:commandButton value="Save" type="button" action="#{userMB.save}" />
     *     </h:form>
     * </pre>
     */

    @ViewScoped
    @Named
    public static class UserMB implements Serializable {

        private static final long serialVersionUID = 1L;

        private User user;

        @PostConstruct
        public void postConstruct() {
            user = new User();
        }

        public User getUser() {
            return user;
        }

        public void setUser(final User user) {
            this.user = user;
        }

        public void save() {
            System.out.println("FirstName: " + this.user.getFirstName());
        }
    }

    // ==========================================================================================================================================================
    public static void javaSe() throws Exception {
        final User user = new User();
        user.setFirstName("Jennifer");
        // user.setLastName("Wilson");

        final Configuration<?> config = Validation.byDefaultProvider().configure();
        final ValidatorFactory factory = config.buildValidatorFactory(); // Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        // ------------------------------------------------
        final Set<ConstraintViolation<User>> groupUserNameConstraintViolations = validator.validate(user, GroupUserName.class);

        if (groupUserNameConstraintViolations.size() > 0) {
            for (final ConstraintViolation<User> constraintViolation : groupUserNameConstraintViolations) {
                System.out.println(constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage());
            }
        } else {
            System.out.println(user);
        }

        // -------------------------------------------------
        final Set<ConstraintViolation<User>> groupAddressConstraintViolations = validator.validate(user, GroupAddress.class);

        if (groupUserNameConstraintViolations.size() > 0) {
            for (final ConstraintViolation<User> constraintViolation : groupAddressConstraintViolations) {
                System.out.println(constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage());
            }
        } else {
            System.out.println(user);
        }
    }

    // ==========================================================================================================================================================
    public static void javaEE() throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter11_beanValidation/part03_validationGroups/web.xml"));
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter11_beanValidation/part03_validationGroups/index.xhtml"));

            war.addClasses( //
                    OrderedChecks.class, GroupUserName.class, GroupAddress.class, ZipCodeGroup.class, DefaultZipCodeGroup.class, //
                    User.class, UserMB.class //
            );

            final File appFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, appFile.getAbsolutePath());

            // ------------ Client -----------------------------------------------------------
            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/Servlet"));

            System.out.println(response);

            Thread.sleep(10000); // 10 seconds only for the server finishs its jobs...

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        javaSe();
    }

}
