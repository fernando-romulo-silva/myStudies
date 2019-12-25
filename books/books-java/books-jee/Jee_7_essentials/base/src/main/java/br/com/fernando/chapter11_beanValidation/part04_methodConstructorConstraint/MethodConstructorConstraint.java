package br.com.fernando.chapter11_beanValidation.part04_methodConstructorConstraint;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Configuration;
import javax.validation.Constraint;
import javax.validation.ConstraintTarget;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ExecutableValidator;
import javax.validation.executable.ValidateOnExecution;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class MethodConstructorConstraint {

    // Bean Validaton 1.1 allows us to specify constraints on arbitrary methods and constructors, and/or the parameters of a POJO by directly adding constraint annotations.
    //
    // This allows us to describe and validate the contract by ensuring that the preconditions must be met by the caller before the method or constructor may be invoked. 
    // This also ensures that the postconditions are guaranteed to the caller after a method or constructor invocation returns. 
    // This enables a programming style known as Programming by Contract (PbC)
    //
    //---------------------------------------------------------------------------------------------------------------------------------------
    @Target({ ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE, ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = DateRangeValidator.class)
    @Documented
    public static @interface DateRangeParams {

        String message()

        default "'start date' must be less than 'end date'. Found: 'start date'=${validatedValue[0]}, 'end date'=${validatedValue[1]}";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;
    }

    @SupportedValidationTarget(ValidationTarget.PARAMETERS)
    public static class DateRangeValidator implements ConstraintValidator<DateRangeParams, Object[]> {

        @Override
        public void initialize(final DateRangeParams constraintAnnotation) {
        }

        @Override
        public boolean isValid(final Object[] value, final ConstraintValidatorContext context) {

            if (value == null || value.length != 2 || !(value[0] instanceof Date) || !(value[1] instanceof Date)) {
                return false;
            }

            return ((Date) value[0]).before((Date) value[1]);
        }
    }

    public static class Name {

        private final String text;

        public Name(final String text) {
            super();
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    // Annotating the methods or constructors with parameter or return value constraints does not automatically enforce these constraints. 
    // The declared constraints need to be explicitly triggered via a method interceptor or a similar mechanism. 
    // In Java EE, typically you achieve this by using CDI interceptors or dynamic proxies.
    //
    // Meeting all the constraints ensures that the method or constructor is called if the caller has satisified the preconditions and returns to 
    // the caller if the postconditions are guaranteed. If the specified constraints are not met, then a ConstraintViolationException is thrown.
    @RequestScoped
    //
    //
    // By default, only constructors and nongetter methods are validated. 
    // You can change this default behavior by specifying @ValidateOnExecution on the class or the method to be validated:
    // 
    // NONE -> No constructors or methods
    // CONSTRUCTORS -> Only constructors
    // NON_GETTER_METHODS -> All methods except the ones following the JavaBeans getter pattern
    // GETTER_METHODS -> All methods following the JavaBeans getter pattern
    // ALL -> All constructors and methods (this is the default value)
    @ValidateOnExecution(type = { ExecutableType.ALL, ExecutableType.CONSTRUCTORS })
    //
    // In this code, all constructors and methods are validated. 
    // If @ValidateOnExecution is not specified on the class, then only the constructor and the addName method are validated.
    //
    // Static methods are ignored by validation. 
    // Putting constraints on a static method is not portable.
    public static class AddressBook {

        // You can specify the constraints using either actual Java annotations or an XML constraint mapping file.
        // ExecutableType.IMPLICIT
        // The addNames method takes two parameters. 
        // The first parameter, priority, cannot be null and is also validated by a custom constraint, @PriorityCode. 
        // The second parameter, names, cannot be null and the list must have a minimum of 1 element and a maximum of 10 elements.
        public void addNames(@NotNull final String priority, @NotNull @Size(min = 1, max = 10) final List<Name> names) {
            // ...
        }

        @NotNull
        public String getName(@NotNull @Past final Date dob) {
            return "Okay";
        }

        // Method getNames takes a parameter, dob, that cannot be null and must be in the past. The return value also cannot be null.
        // This method retrieves the list of names born between startDob and endDob. 
        // Cross-parameter constraints can be declared on a method and allow you to express constraints based on the value of several method parameters.
        // @DateRangeParams is a cross-parameter constraint that checks that the startDob is before endDob. 
        // It is often useful to combine constraints directly placed on individual parameters and cross-parameter constraints.
        @DateRangeParams
        public List<Name> getNames(@NotNull final Date startDob, @NotNull final Date endDob) {
            // ...
            return new ArrayList<>();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    @RequestScoped
    public static class MyBean {

        public String sayHello(@Size(max = 3) final String name) {
            return "Hello " + name;
        }

        @Future
        public Date showDate(final boolean correct) {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, correct ? 5 : -5);
            return cal.getTime();
        }

        public String showList(@NotNull @Size(min = 1, max = 3) final List<String> list, @NotNull final String prefix) {
            final StringBuilder builder = new StringBuilder();

            for (final String s : list) {
                builder.append(prefix).append(s).append(" ");
            }

            return builder.toString();
        }

        // @NotNull(validationAppliesTo = ConstraintType.PARAMETERS)
        // @NotNull
        // public void concat(final String str1, final String str2) {
        // str1.concat(str2);
        // }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    public static class MyParameter {

        @NotNull
        public String value; // = "notNull";

        public String getValue() {
            return value;
        }

        public void setValue(final String value) {
            this.value = value;
        }
    }

    @RequestScoped
    public static class MyBean2 {

        private final MyParameter value;

        @Inject
        public MyBean2(@Valid final MyParameter value) {
            this.value = value;
        }

        protected MyBean2() {
            this(null);
        }

        public MyParameter getValue() {
            return value;
        }

    }

    // ==========================================================================================================================================================
    public static void javaSe() throws Exception {

        final Configuration<?> config = Validation.byDefaultProvider().configure();
        final ValidatorFactory factory = config.buildValidatorFactory(); // Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final ExecutableValidator methodValidator = validator.forExecutables();

        // ------------------------------------------------------------------------------------------------
        // MethodParametersConstraintsTest MyBean
        final Set<ConstraintViolation<MyBean>> constraintsViolationBean = new HashSet<>();
        final MyBean bean = new MyBean();
        // ----
        final Method methodSayHello = MyBean.class.getMethod("sayHello", String.class);
        final Object[] parameterValuesSayHello = { "12345" };
        constraintsViolationBean.addAll(methodValidator.validateParameters(bean, methodSayHello, parameterValuesSayHello));
        // ----
        final Method methodShowDate = MyBean.class.getMethod("showDate", boolean.class);
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        constraintsViolationBean.addAll(methodValidator.validateReturnValue(bean, methodShowDate, cal.getTime()));
        // ----
        final Method methodShowList = MyBean.class.getMethod("showList", List.class, String.class);
        final Object[] parameterValuesShowList = { Arrays.asList("", "", "", ""), null };
        constraintsViolationBean.addAll(methodValidator.validateParameters(bean, methodShowList, parameterValuesShowList));

        for (final ConstraintViolation<MyBean> constraintViolationBean : constraintsViolationBean) {
            System.out.println(constraintViolationBean);
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private MyBean myBean;

        @Inject
        private MyBean2 myBean2;

        @Inject
        private AddressBook addressBook;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            try {

                myBean.sayHello("Steve");

                myBean.showDate(true);

                myBean.showList(Arrays.asList("bla", "bla bla"), "123");

            } catch (final Exception e) {
                e.printStackTrace();
            }

            try {
                myBean2.getValue();
            } catch (final Exception e) {
                e.printStackTrace();
            }

            try {
                final Date startDob = new Date();

                final Date endDob = new Date(startDob.getTime() + 10000);

                addressBook.getNames(startDob, endDob);

            } catch (final Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void javaEE() throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter11_beanValidation/part03_validationGroups/web.xml"));
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter11_beanValidation/part03_validationGroups/index.xhtml"));

            war.addClasses( //
                    ServletTest.class, //
                    DateRangeParams.class, DateRangeValidator.class, Name.class, AddressBook.class, //
                    MyBean.class, //
                    MyParameter.class, MyBean2.class //
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
