package br.com.fernando.chapter13_javaPersistence.part09_queryEntities;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.prepareDataBase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Author;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.AuthorValue;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.BlogPost;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Book;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Publication;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Publisher;

public class Queries04Native {

    // =================================================================================================================================================================
    public static void executeQueries(final EntityManager em) {
	// The EntityManager interface provides a method called createNativeQuery for it.
	// This method returns an implementation of the Query interface which is the same as if you call the createQuery method to create a JPQL query.
	//
	// I didn’t provide any mapping information for the result and so the EntityManager returns a List of Object[] which need to be handled afterwards.

	final Query q01 = em.createNativeQuery("SELECT a.FIRST_NAME, a.LAST_NAME FROM Author a");

	@SuppressWarnings("unchecked")
	final List<Object[]> resultList01 = q01.getResultList();

	for (Object[] object : resultList01) {
	    System.out.println("Author " + object[0] + " " + object[1]);
	}

	// ----------------------------------------------------------
	// Parameter binding
	// This provides several advantages:
	//
	// You do not need to worry about SQL injection,
	// The persistence provider maps your query parameters to the correct types
	// The persistence provider can do internal optimizations to provide a better performance.

	final Query q02 = em.createNativeQuery("SELECT a.FIRST_NAME, a.LAST_NAME FROM Author a WHERE a.FIRST_NAME = ?");
	q02.setParameter(1, "Author 01 First");
	final Object[] result02 = (Object[]) q02.getSingleResult();

	System.out.println("Author " + result02[0] + " " + result02[1]);
	//
	// Hibernate also supports named parameter bindings for native queries but as I already said, this is not defined by the specification
	// and might not be portable to other JPA implementations.

	Query q03 = em.createNativeQuery("SELECT a.FIRST_NAME, a.LAST_NAME FROM Author a WHERE a.FIRST_NAME = :lastName");
	q03.setParameter("lastName", "Author 01 First");
	Object[] result03 = (Object[]) q03.getSingleResult();

	System.out.println("Author " + result03[0] + " " + result03[1]);

	// ----------------------------------------------------------
	// Result handling
	// As you have seen in the previous code snippets, your native Query returns an Object[] or a List of Object[].
	// You can change that, if you provide additional mapping information to the EntityManager.
	// By doing this you can tell the EntityManager to map the result into managed entities, scalar values of specific types or POJOs.
	// The simplest way to map the result of a native query into a managed entity is to select all properties of the entity and provide its as a
	// parameter to the createNativeQuery method.

	final Query q04 = em.createNativeQuery("SELECT a.ID, a.FIRST_NAME, a.LAST_NAME FROM Author a", Author.class);
	@SuppressWarnings("unchecked")
	final List<Author> result04 = q04.getResultList();

	for (Author a : result04) {
	    System.out.println("Author " + a.firstName + " " + a.lastName);
	}

	// All other mappings, like the following one which maps the query result into a POJO, need to be defined as SQLResultSetMappings.

	final StringBuilder sb = new StringBuilder();
	sb.append("SELECT a.ID, a.FIRST_NAME, a.LAST_NAME, count(b.ID) as NUM_BOOKS ");
	sb.append("  FROM AUTHOR a ");
	sb.append("  JOIN PUBLICATION_AUTHOR ba on ba.AUTHOR_ID = a.ID");
	sb.append("  JOIN BOOK b ON b.ID = ba.PUBLICATION_ID ");
	sb.append(" GROUP BY a.ID, a.FIRST_NAME, a.LAST_NAME ");

	// Look at br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Author
	final Query q05 = em.createNativeQuery(sb.toString(), "AuthorValueMapping");

	@SuppressWarnings("unchecked")
	final List<AuthorValue> resultList05 = q05.getResultList();

	for (final AuthorValue a : resultList05) {
	    System.out.println("Author " + a.firstName + " " + a.lastName + " wrote " + a.numBooks + " books.");
	}

    }

    // =================================================================================================================================================================
    public static void main(final String[] args) throws Exception {

	final String dataBaseName = EMBEDDED_JEE_TEST_APP_NAME + "DB";
	final String dataBaseUrl = "jdbc:hsqldb:hsql://localhost:" + DATA_BASE_SERVER_PORT + "/" + dataBaseName;

	final Properties props = new Properties();
	props.put("javax.persistence.schema-generation.database.action", "create");
	props.put("javax.persistence.jdbc.url", dataBaseUrl);
	props.put("javax.persistence.jdbc.user", DATA_BASE_SERVER_LOGIN);
	props.put("javax.persistence.jdbc.password", DATA_BASE_SERVER_PASSWORD);
	props.put("javax.persistence.jdbc.driver", "org.hsqldb.jdbcDriver");
	// hibernate
	props.put("hibernate.show_sql", "true");
	props.put("hibernate.format_sql", "true");
	props.put("hibernate.use_sql_comments", "true");

	final List<String> classes = Arrays.asList(Publisher.class.getName(), Publication.class.getName(), Book.class.getName(), BlogPost.class.getName(), Author.class.getName());

	try (final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

	    embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
	    embeddedRdbms.addSchema("APP", dataBaseName);
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);

	    final HibernatePersistenceUnitInfo persistenceUnitInfo = new HibernatePersistenceUnitInfo("appName", classes, props);
	    final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(persistenceUnitInfo);

	    final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();
	    final EntityManager em = factory.createEntityManager();

	    prepareDataBase(em);
	    executeQueries(em);

	    em.close();

	    factory.close();
	}
    }
}
