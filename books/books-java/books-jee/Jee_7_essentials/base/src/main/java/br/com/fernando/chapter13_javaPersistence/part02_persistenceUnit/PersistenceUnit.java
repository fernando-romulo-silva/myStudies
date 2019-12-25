package br.com.fernando.chapter13_javaPersistence.part02_persistenceUnit;

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

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class PersistenceUnit {

    @Entity
    @Table(name = "PERSON")
    @NamedQueries({ //
            @NamedQuery( //
                    name = "Person.findAll", //
                    query = "SELECT e FROM Person e") //
    })
    public static class Person {

        @Id
        @GeneratedValue
        @Column(name = "ID")
        long id;

        @Column(name = "FIRST_NAME")
        String firstName;

        @Column(name = "LAST_NAME")
        String lastName;

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();

            builder.append("Person [id=").append(id) //
                    .append(", firstName=").append(firstName) //
                    .append(", lastName=").append(lastName) //
                    .append("]");

            return builder.toString();
        }
    }

    // An entity is managed within a persistence context.
    // Each entity has a unique instance for any persistent entity identity within the context.
    // Within the persistence context, the entity instances and their life cycles are managed by the entity manager.
    // The entity manager may be container-managed or application-managed.

    public static class Service {
        //
        // A container-managed entity manager is obtained by the application directly through dependency injection:
        @PersistenceContext
        EntityManager em01;

        // The entity manager can also be obtained via JNDI:
        @PersistenceContext(name = "myPU")
        EntityManager em02;

        public void execute() {

            try {
                final Context context = new InitialContext();

                // or lookup
                final EntityManager em = (EntityManager) context.lookup("java:comp/env/myPU");

                System.out.println("People: " + em.createNamedQuery("Person.findAll", Person.class).getResultList());

            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        // The entity managers, together with their configuration information, the set of entities managed by the entity managers, and metadata that specifies mapping
        // of the classes to the database, are packaged together as a persistence unit.
        //
        // Look at chapter13_javaPersistence\part04_entityManager\persistence.xml.
    }

    // ==================================================================================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        Service service;

        @Override
        protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

            service.execute();

        }
    }

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        final String dataBaseName = EMBEDDED_JEE_TEST_APP_NAME + "DB";
        final String dataBaseUrl = "jdbc:hsqldb:hsql://localhost:" + DATA_BASE_SERVER_PORT + "/" + dataBaseName;

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer(); //
                final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

            embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
            embeddedRdbms.start(DATA_BASE_SERVER_PORT);

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addMetaInfFiles(EmbeddedResource.add("persistence.xml", "src/main/resources/chapter13_javaPersistence/part02_persistenceUnit/persistence01.xml"));

            war.addClasses(ServletPrincipal.class, Service.class, Person.class);

            final File appFile = war.exportToFile(APP_FILE_TARGET);

            final MyEmbeddedDataSource dataSource = new MyEmbeddedDataSourceBuilder(EMBEDDED_JEE_TEST_DATA_SOURCE_NAME, dataBaseUrl)//
                    .withCredential(DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD) //
                    .withDataBaseDriverClass(HSQLDB_JDBC_DRIVER) //
                    .build();

            embeddedJeeServer.addDataSource(dataSource);
            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, appFile.getAbsolutePath());

            // ------------ Client -----------------------------------------------------------
            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));

            System.out.println(response);

            Thread.sleep(5000); // 5 seconds only for the server finishs its jobs...

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
