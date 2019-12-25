package br.com.fernando.chapter09_contextDependencyInjection.part05_interceptors;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_DATA_SOURCE_NAME;
import static br.com.fernando.Util.HSQLDB_JDBC_DRIVER;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.annotation.Priority;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InterceptorBinding;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.RollbackException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource;
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource.MyEmbeddedDataSourceBuilder;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;
import org.myembedded.rdbms.MyEmbeddedRdbms;

public class CdiInterceptors01 {

    // Interceptors are used to implement cross-cutting concerns, such as logging, auditing, and security, from the business logic.
    //
    // Basically, an interceptor is a class whose methods are invoked when business methods on a target class are invoked, life-cycle
    // events such as methods that create/destroy the bean occur, or an EJB timeout method occurs.
    //
    // The CDI specification defines a type-safe mechanism for associating interceptors to beans using interceptor bindings.
    //
    //
    // We need to define an interceptor binding type in order to intercept a business method.
    // We can do this by specifying the @InterceptorBinding meta-annotation:
    @InterceptorBinding
    @Retention(RUNTIME)
    @Target({ METHOD, TYPE }) // @Target defines the program element to which this interceptor can be applied. Method or type (class, interface, or enum)
    public static @interface Logging {
    }

    // The interceptor is implemented as follows:
    @Interceptor
    @Logging
    // Adding the @Interceptor annotation marks this class as an interceptor, and @Logging specifies that this is an
    // implementation of the earlier defined interceptor binding type.
    //
    // By default, all interceptors are disabled.
    // They need to be explicitly enabled and ordered via the @Priority annotation, along with a priority value on the interceptor class:
    @Priority(Interceptor.Priority.APPLICATION + 10)
    public static class LoggingInterceptor {

        // @AroundInvoke indicates that this interceptor method interposes on business methods.
        @AroundInvoke
        public Object log(final InvocationContext context) throws Exception {

            // InvocationContext provides context information about the intercepted invocation and operations and can be used to control
            // the behavior of the invocation chain.
            //
            //
            // Name of the business method invoked and the parameters passed to it can be retrieved from the context.

            final String name = context.getMethod().getName();
            final String params = context.getParameters().toString();

            System.out.println("LoggingInterceptor, method: " + name);
            System.out.println("LoggingInterceptor, params: " + params);

            return context.proceed();
        }
    }

    @Logging
    public static class SimpleGreeting {

        public String greet(final String name) {
            return "Hello " + name;
        }

        public String farewell(final String name) {
            return "Bye " + name;
        }

    }

    public static class FancyGreeting {

        // Alternatively, we can log individual methods by attaching the interceptor:
        @Logging
        public String greet(final String name) {
            return "Hi dear " + name;
        }
    }

    @Stateless
    public static class Service01 {

        @Inject
        private FancyGreeting fancyGreeting;

        @Inject
        private SimpleGreeting simpleGreeting;

        public void execute() {

            fancyGreeting.greet("Paul");

            simpleGreeting.greet("Mary");

            simpleGreeting.farewell("Mary");
        }
    }

    // The priority values are defined in the javax.interceptor.Interceptor.Priority class:
    //
    // Interceptor.Priority.PLATFORM_BEFORE = 0
    //
    // -> Interceptors defined by the Java EE Platform specifications that are to be executed at the beginning of the interceptor chain
    //
    // Interceptor.Priority.LIBRARY_BEFORE = 1000
    //
    // -> Interceptors defined by extension libraries that are intended to be executed earlier in the interceptor chain
    //
    // Interceptor.Priority.APPLICATION = 2000
    //
    // -> Interceptors defined by applications
    //
    // Interceptor.Priority.LIBRARY_AFTER = 3000
    //
    // -> Interceptors defined by extension libraries that are intended to be executed later in the interceptor chain
    //
    // Interceptor.Priority.PLATFORM_AFTER = 4000
    //
    // Interceptors with the smaller priority values are called first.
    // If more than one interceptor has the same priority, the relative order of these interceptor is undefined.
    //
    //
    // The LoggingInterceptor defined earlier is executed after all application-specific interceptors are called but before interceptors defined by extension libraries.
    //
    // -------------------------------------------------------------------------------------------------------------------------
    @InterceptorBinding
    @Retention(RUNTIME)
    @Target({ METHOD, TYPE })
    public static @interface MyTransactional {

    }

    @Interceptor
    @MyTransactional
    // By default, all interceptors are disabled.
    // You can also explicitly enable interceptors by specifying them in beans.xml. (please look at chapter09_contextDependencyInjection/part05_interceptors/beans.xml)
    public static class MyTransactionCdiInterceptor implements Serializable {

        private static final long serialVersionUID = 1L;

        // Interceptors also support dependency injection.
        // An interceptor that adds basic transactional behavior to a managed bean may be defined thusly
        @Resource(lookup = "java:comp/UserTransaction")
        private UserTransaction tx;

        // https://www.google.com/search?q=%22with+METHOD+as+its+%40Target%22&oq=%22with+METHOD+as+its+%40Target%22&aqs=chrome..69i57.2632j0j7&sourceid=chrome&ie=UTF-8
        // https://stackoverflow.com/questions/36360197/unable-to-use-interceptor-binding-interface-with-targetmethod-type-for-pos
        // // A life-cycle callback interceptor can be implemented as follows:
        // @PostConstruct
        // public void init(final InvocationContext context) {
        // System.out.println("MyTransactionCdiInterceptor init");
        // }

        @AroundInvoke
        public Object manageTransaction(final InvocationContext context) throws Exception {

            try {
                tx.begin();
                final Object retVal = context.proceed();
                tx.commit();
                return retVal;
            } catch (final RollbackException e) {
                throw e;
            } catch (final RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public static class TransactionalSimpleGreeting {

        // Defining interceptor bindings provides one level of indirection but removes the dependency from the actual interceptor implementation class.
        @MyTransactional
        public String greet(final String name) {
            return "Hello " + name;
        }

    }

    // The Java Transaction API introduces a new @javax.transaction.Transactional annotation that enables an application to declaratively control transaction
    // boundaries on CDI-managed beans, as well as classes defined as managed beans, such as servlets, JAX-RS resource classes, and JAX-WS service endpoints.
    @Transactional
    public static class TransactionalFancyGreeting {

        public String greet(final String name) {
            // INFO: In REQUIRED TransactionalInterceptor
            return "Hi dear " + name;
        }
    }

    // CDI Bean
    public static class Service02 {

        @Inject
        private TransactionalSimpleGreeting simpleGreeting;

        @Inject
        private TransactionalFancyGreeting fancyGreeting;

        public void execute() {
            System.out.println(fancyGreeting.greet("Ana"));

            System.out.println(simpleGreeting.greet("Phoebe"));
        }
    }

    // ----------------------------------------------------------------------------------------------------
    public static class MyTransactionEjbInterceptor {

        @Resource(lookup = "java:comp/UserTransaction")
        private UserTransaction tx;

        @AroundInvoke
        public Object manageTransaction(final InvocationContext context) throws Exception {

            try {
                tx.begin();
                final Object retVal = context.proceed();
                tx.commit();
                return retVal;
            } catch (final RollbackException e) {
                throw e;
            } catch (final RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }

    // The EJB needs to use the MyTransactionEjbInterceptor interceptor to handle transactions and has to switch to the "Bean Managed Transactions" strategy 
    // (otherwise you get a: Caused by: javax.naming.NameNotFoundException: Lookup of java:comp/UserTransaction not allowed for Container managed Transaction beans)
    @Stateless
    @Interceptors(MyTransactionEjbInterceptor.class)
    @TransactionManagement(TransactionManagementType.BEAN)
    public static class Service03 {

        public void execute() {
            System.out.println("Hi guys!");
        }
    }

    // ==========================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private Service01 service01;

        @Inject // CDI Bean
        private Service02 service02;

        @EJB
        private Service03 service03;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            service01.execute();
            System.out.println("------------------------------------------------------------------------------");
            service02.execute();
            System.out.println("------------------------------------------------------------------------------");
            service03.execute();
        }
    }

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try ( //
                final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer(); //
                final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms(); //
        ) {
            // ------------ Database -----------------------------------------------------------
            final String dataBaseName = EMBEDDED_JEE_TEST_APP_NAME + "DB";
            final String dataBaseUrl = "jdbc:hsqldb:hsql://localhost:" + DATA_BASE_SERVER_PORT + "/" + dataBaseName;
            embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
            embeddedRdbms.start(DATA_BASE_SERVER_PORT);

            // ------------ Packages -----------------------------------------------------------
            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter09_contextDependencyInjection/part05_interceptors/beans.xml"));

            war.addClasses( //
                    ServletTest.class, //
                    Service01.class, Logging.class, LoggingInterceptor.class, SimpleGreeting.class, FancyGreeting.class, //
                    Service02.class, MyTransactional.class, TransactionalSimpleGreeting.class, TransactionalFancyGreeting.class, MyTransactionCdiInterceptor.class, //
                    Service03.class, MyTransactionEjbInterceptor.class);

            final File appFile = war.exportToFile(APP_FILE_TARGET);

            // ------------ jee Server -----------------------------------------------------------
            final MyEmbeddedDataSource dataSource = new MyEmbeddedDataSourceBuilder(EMBEDDED_JEE_TEST_DATA_SOURCE_NAME, dataBaseUrl) //
                    .withDataBaseDriverClass(HSQLDB_JDBC_DRIVER) //
                    .build();
            embeddedJeeServer.addDataSource(dataSource);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, appFile.getAbsolutePath());

            // ------------ Client -----------------------------------------------------------
            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/Servlet"));

            System.out.println(response);

            Thread.sleep(5000); // 5 seconds only for the server finishs its jobs...

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
