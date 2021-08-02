package br.com.fernando.chapter11_beanValidation.part01_builtInConstraints;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class BuiltInConstraints01 {

    public static class Item {

    }

    // Bean Validation offers a built-in set of constraint definitions that can be used on beans.
    //
    // Multiple constraints can be specified on a bean to ensure different validation requirements are met.
    //
    // These constraints can also be used for composing other constraints.
    //
    public static class Bean {

        // -------------------------------------------------------------------------------------------------------------------------
        // Annotated element must be null and can be applied to any type:
        @Null
        private String httpErrorCode;
        // The httpErrorCode field captures the HTTP status code from a RESTful endpoint.

        // -------------------------------------------------------------------------------------------------------------------------
        // Annotated element must not be null and can be applied to any type:
        @NotNull
        private String name;
        // name captures the name of, say, a customer. Specifying @NotNull will trigger a validation error
        // if the instance variable is assigned a null value.

        // -------------------------------------------------------------------------------------------------------------------------
        // The annotated element must be true and can be applied to boolean or Boolean types only:
        @AssertTrue
        boolean isConnected;
        // isConnected can be a field in a class managing resource connections.

        // -------------------------------------------------------------------------------------------------------------------------
        // The annotated element must be false and can be applied to boolean or Boolean types only:
        @AssertFalse
        private Boolean isWorking;
        // isWorking can be a field in an Employee class.

        // -------------------------------------------------------------------------------------------------------------------------
        // The annotated element must be a number whose value is higher or equal to the specified minimum.
        // byte, short, int, long, Byte, Short, Integer, Long, BigDecimal, and BigInteger are supported types:

        @Min(10)
        private int quantityMin;

        @DecimalMin("0.1")
        private float priceMin;

        // -------------------------------------------------------------------------------------------------------------------------
        // The annotated element must be a number whose value is lower or equal to the specified maximum.
        // byte, short, int, long, Byte, Short, Integer, Long, BigDecimal, and BigInteger are supported types:

        @Max(20)
        private int quantityMax;

        @DecimalMax("15.50")
        private float priceMax;

        // Multiple constraints may be specified on the same field:
        @Min(10)
        @Max(20)
        private int quantity;

        // -------------------------------------------------------------------------------------------------------------------------
        // The annotated element size must be between the specified boundaries.
        // String, Collection, Map, and Array are supported types:

        @Size(min = 5, max = 9)
        private String zipCode;

        // The length of the string is used for validation critieria.
        // min and max define the length of the targeted field, specified values included.
        // By default, min is 0 and max is 2147483647.

        @Size(min = 1)
        private List<Item> items;
        // The List.size method is used for validation in this case.

        // -------------------------------------------------------------------------------------------------------------------------
        // The annotated element must be a number within the accepted range.
        // byte, short, int, long, Byte, Short, Integer, Long, BigDecimal, BigInteger, and String are supported types:

        @Digits(integer = 3, fraction = 0)
        private int age01;

        // integer defines the maximum number of integral digits and fraction defines the number of fractional digits for this number.
        // So 1, 28, 262, and 987 are valid values.
        //
        // Specifying multiple constraints may make this field more meaningful:
        @Min(18)
        @Max(25)
        @Digits(integer = 3, fraction = 0)
        private int age02;

        // -------------------------------------------------------------------------------------------------------------------------
        // The annotated element must be a date in the past.
        // The present time is defined as the current time according to the virtual machine.
        // Date and Calendar are supported:

        @Past
        private Date birthdate;

        // -------------------------------------------------------------------------------------------------------------------------
        // The annotated element must be a date in the future.
        // The present time is defined as the current time according to the virtual machine.
        // Date and Calendar are supported:

        @Future
        private Date retirementDate;

        // -------------------------------------------------------------------------------------------------------------------------
        // The annotated string must match the specified regular expression:

        @Pattern(regexp = "[0-9]*")
        private String oldZip;

        // The regular expression says that only digits from 0 to 9 are permitted.
        // You can make this field more meaningful by adding the @Size constraint:

        @Pattern(regexp = "[0-9]*")
        @Size(min = 5, max = 5)
        private String newZip;

        @Override
        public String toString() {
            return "Bean [name=" + name + "]";
        }
    }

    // ==========================================================================================================================================================
    // Java SE
    public static void javaSe() throws Exception {

        Locale.setDefault(Locale.US);// Locale.FRANCE
        final Configuration<?> config = Validation.byDefaultProvider().configure();
        final ValidatorFactory factory = config.buildValidatorFactory(); // Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        final Bean bean = new Bean();

        final Set<ConstraintViolation<Bean>> constraintsViolation = validator.validate(bean);

        for (final ConstraintViolation<Bean> constraintViolation : constraintsViolation) {

            System.out.println(constraintViolation);
        }
    }

    // ==========================================================================================================================================================
    // Java EE
    //
    // Integrating with frameworks - Bean Validator is intended to be used to implement multi-layered data validation,
    // where constraints are expressed in a single place (the annotated domain model) and checked in various different
    // layers of the application. For this reason there are multiple integration points with other technologies.
    //
    // Integrating with other frameworks - CDI
    public static class Service {

        @Inject
        Bean bean;

        @Inject
        ValidatorFactory validatorFactory;

        @Inject
        Validator validator;

        public void execute() {

            try {

                final Bean localBean = new Bean();
                System.out.println("localBean " + localBean);

                final Set<ConstraintViolation<Bean>> constraintsViolationLocalBean = validator.validate(localBean);

                for (final ConstraintViolation<Bean> constraintViolationLocalBean : constraintsViolationLocalBean) {
                    System.out.println(constraintViolationLocalBean);
                }

                // -------------------------------------------------------

                System.out.println("bean " + bean);

                final Set<ConstraintViolation<Bean>> constraintsViolationBean = validator.validate(bean);

                for (final ConstraintViolation<Bean> constraintViolationBean : constraintsViolationBean) {
                    System.out.println(constraintViolationBean);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private Service service;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {

                service.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void javaEE() throws Exception {
        startVariables();

        Locale.setDefault(Locale.FRANCE);// Locale.FRANCE

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/web.xml"));
            war.addJavaResourceFiles(EmbeddedResource.add("src/main/resources/chapter11_beanValidation/part01_builtInConstraints/ValidationMessages_en.properties"));
            war.addJavaResourceFiles(EmbeddedResource.add("src/main/resources/chapter11_beanValidation/part01_builtInConstraints/ValidationMessages_fr.properties"));

            war.addClasses( //
                    ServletTest.class, Service.class, //
                    Item.class, Bean.class //
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
        javaEE();
    }
}
