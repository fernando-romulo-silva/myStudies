package br.com.fernando.chapter09_contextDependencyInjection.part04_producerAndDisposer;

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
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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

public class ProducerAndDisposer {

    // ---------------------------------------------------------------------------------------------
    // The producer methods provide runtime polymorphism where the concrete type of the bean to be injected may vary at runtime, 
    // the injected object may not even be a bean, and objects may require custom initialization. 
    public static class StringListProducer {

        // This is similar to the well-known factory pattern.
        @Produces
        public List<String> getGreetings() {
            final List<String> response = new ArrayList<String>();

            response.add("bla bla bla");

            return response;
        }
    }

    @Stateless
    public static class Service01 {

        // By default, a bean is injected in @Dependent scope, 
        @Inject
        @Dependent // it's a redundant
        private List<String> list;

        public void execute() {

            System.out.println(list); // injected!
        }
    }

    // ---------------------------------------------------------------------------------------------
    public static interface User {

        String getLogin();

        String getPassword();
    }

    @Default
    public static class RootUser implements User {

        private final String login;

        private final String password;

        public RootUser() {
            super();
            this.login = DATA_BASE_SERVER_LOGIN;
            this.password = DATA_BASE_SERVER_PASSWORD;
        }

        @Override
        public String getLogin() {
            return login;
        }

        @Override
        public String getPassword() {
            return password;
        }
    }

    // The following code shows how a Connection bean is available for injection in request scope:
    public static class ConnectionProducer {

        @Resource(lookup = "jdbc/" + EMBEDDED_JEE_TEST_DATA_SOURCE_NAME)
        private DataSource myDataSource;

        // but we can change it by explicitly specifying the required scope.
        @Produces
        @RequestScoped
        public Connection connect(final User user) {
            try {

                System.out.println("Creating Connection ...");

                return myDataSource.getConnection(user.getLogin(), user.getPassword());
            } catch (final SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }

        // Some objects that are made available for injection via @Produces may require explicit destruction. 
        // For example, the JMS factories and destinations need to be closed. Here is a code example that shows how the Connection produced earlier may be closed:
        public void close(@Disposes final Connection connection) {
            try {
                System.out.println("Disposing Connection ...");

                connection.close();
            } catch (final SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    @Stateless
    public static class Service02 {

        @Inject
        private Connection connection;

        public void execute() {
            try {
                System.out.println(connection.isClosed());
            } catch (final SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------
    @Qualifier
    @Retention(RUNTIME)
    @Target({ METHOD, FIELD, PARAMETER, TYPE })
    public static @interface CustomerDatabase {

    }

    public static class CustomerDatabaseProducer {

        // All such references can be unified in a single file as:
        @Produces
        @Resource(lookup = "jdbc/" + EMBEDDED_JEE_TEST_DATA_SOURCE_NAME)
        @CustomerDatabase
        private DataSource customerDatabase;
    }

    @Stateless
    public static class Service03 {

        // where CustomerDatabase is a qualifier. The DataSource can now be injected as:
        @Inject
        @CustomerDatabase
        private DataSource customerDatabase;

        public void execute() {
            System.out.println(customerDatabase);
        }
    }

    // Similarly, JMS factories and destinations can be injected in a type-safe way.

    // ==========================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private Service01 service01;

        @EJB
        private Service02 service02;

        @EJB
        private Service03 service03;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            service01.execute();

            service02.execute();

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
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));

            war.addClasses( //
                    ServletTest.class, // 
                    Service01.class, StringListProducer.class, // 
                    Service02.class, ConnectionProducer.class, User.class, RootUser.class, //
                    Service03.class, CustomerDatabase.class, CustomerDatabaseProducer.class //
            );

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
