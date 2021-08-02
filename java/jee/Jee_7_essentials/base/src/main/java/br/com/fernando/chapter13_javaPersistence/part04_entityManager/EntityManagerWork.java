package br.com.fernando.chapter13_javaPersistence.part04_entityManager;

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
import java.io.Serializable;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

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

public class EntityManagerWork {

    // ==========================================================================================================================================================
    // The term resource manager comes from the Java Transaction API and denotes a component that manipulates one resource
    // (for example a concrete database that is manipulated by using its JDBC driver).
    // Per default a container-managed persistence context is of type SynchronizationType.SYNCHRONIZED, i.e.
    // this persistence context automatically joins the current JTA transaction and updates to the persistence context are
    // propagated to the underlying resource manager.

    @Entity
    @Table(name = "PERSON")
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

    // ==========================================================================================================================================================
    // Before JPA 2.1 one could implement a conversation that spans multiple method calls with a @Stateful session bean as described by Adam Bien here:
    @Stateful
    @TransactionAttribute(TransactionAttributeType.NEVER)
    // In order to prevent that to happen for most of the bean's methods, the annotation
    // @TransactionAttribute(TransactionAttributeType.NEVER) tells the container to not open any transaction for this bean.
    //
    // Therefore the methods persist() and list() run without a transaction.
    public static class ControllerOlder {

        // As the persistence context is per default also of type SYNCHRONIZED it will automatically join any transaction that is running
        // when any of the session bean's methods are called.
        //
        // By default, a container-managed persistence context is scoped to a single transaction, and entities are detached at the end of a transaction.
        // For stateful session beans, the persistence context may be marked to span multiple transactions and is called an extended persistence context.
        // The entities stay managed across multiple transactions in this case. An extended persistence context can be created:
        @PersistenceContext(name = "myPU")
        EntityManager entityManager;

        public Person persist() {
            final Person p = new Person();
            p.firstName = "Martin";
            p.lastName = "Developer";
            return entityManager.merge(p);
        }

        public List<Person> list() {
            return entityManager.createQuery("select p from EntityManagerWork$Person p", Person.class).getResultList();
        }

        // This behavior is different for the method commit(). Here the annotation @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) tells the container
        // to create a new transaction before the method is called and therefore the bean's EntityManager will join it automatically.
        @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
        public void commit() {
            System.out.println("Commit!");
        }

        @Remove
        public void remove() {

        }
    }

    // ==========================================================================================================================================================
    // JPA 2.1 introduces SynchronizedType.UNSYNCHRONIZED.
    // With the new type SynchronizationType.UNSYNCHRONIZED the code above can be rewritten as depicted in the following listing:
    @Stateful
    public static class Controller {

        // Per default a container-managed persistence context is of type SynchronizationType.SYNCHRONIZED i.e. this persistence context automatically joins the current JTA
        // transaction and updates to the persistence context are propagated to the underlying resource manager.
        //
        // The persistence context is of type EXTENDED and therefore lives longer than the JTA transactions it is attached to.
        @PersistenceContext(name = "myPU", type = PersistenceContextType.EXTENDED, synchronization = SynchronizationType.UNSYNCHRONIZED)
        EntityManager entityManager;
        // Now that the EntityManager won't automatically join the current transaction, we can omit the @TransactionAttribute annotations.
        // Any running transaction won't have an impact on the EntityManager until we explicitly join it. This is now done in the method commit()
        // and could even be done on the base on some dynamic logic.

        public Person persist() {
            final Person p = new Person();
            p.firstName = "Martin";
            p.lastName = "Developer";
            return entityManager.merge(p);
        }

        public List<Person> list() {
            return entityManager.createQuery("select p from EntityManagerWork$Person p", Person.class).getResultList();
        }

        public void commit() {
            // Such a persistence context is not enlisted in any JTA transaction unless explicitly joined to that transaction by the application
            entityManager.joinTransaction();
            // The persistence context remains joined to the transaction until the transaction commits or rolls back.
            // The persistence context remains unsynchronized after that and explicitly needs to join a transaction in the new scope.
            // The application can invoke the persist, merge, remove, and refresh entity life-cycle operations on an unsynchronized persistence context.
            // After joining the transaction, any changes in persistence context may be flushed to the database either explicitly by application via flush or by the provider.
            // If not explicitly flushed, then the persistence provider may defer flushing until commit time depending on the operations invoked and the flush mode setting in effect.
        }

        @Remove
        public void remove() {

        }
    }

    // ==========================================================================================================================================================
    @Path("rest")
    @Produces("text/json")
    @SessionScoped
    public static class RestResource implements Serializable {

        private static final long serialVersionUID = 1L;

        @Inject
        private Controller controller;

        @GET
        @Path("persist")
        public Person persist(@Context final HttpServletRequest request) {
            return controller.persist();
        }

        @GET
        @Path("list")
        public List<Person> list() {
            return controller.list();
        }

        @GET
        @Path("commit")
        public void commit() {
            controller.commit();
        }

        @PreDestroy
        public void preDestroy() {

        }
    }

    // ==========================================================================================================================================================

    @ApplicationPath("webresources")
    public static class MyApplication extends Application {
    }

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        final String dataBaseName = EMBEDDED_JEE_TEST_APP_NAME + "DB";
        final String dataBaseUrl = "jdbc:hsqldb:hsql://localhost:" + DATA_BASE_SERVER_PORT + "/" + dataBaseName;

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer(); //
                final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

            // ------------ Database -----------------------------------------------------------
            embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
            embeddedRdbms.start(DATA_BASE_SERVER_PORT);

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addMetaInfFiles(EmbeddedResource.add("persistence.xml", "src/main/resources/chapter13_javaPersistence/part04_entityManager/persistence02.xml"));

            war.addClasses( //
                    Person.class, //
                    Controller.class, ControllerOlder.class, //
                    RestResource.class, MyApplication.class //
            );

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
            final HttpResponse response01 = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/webresources/rest/persist"));
            System.out.println(response01);

            final HttpResponse response02 = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/webresources/rest/commit"));
            System.out.println(response02);

            final HttpResponse response03 = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/webresources/rest/list"));
            System.out.println(response03);

            Thread.sleep(5000); // 5 seconds only for the server finishs its jobs...

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
