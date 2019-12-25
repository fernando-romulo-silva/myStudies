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

public class Queries05Named {

    // =================================================================================================================================================================
    public static void executeQueries(final EntityManager em) {

	// -----------------------------------------------------------------------
	final Query q01 = em.createNamedQuery("selectAuthorNames");
	@SuppressWarnings("unchecked")
	final List<Object[]> resultList01 = q01.getResultList();

	for (final Object[] object : resultList01) {
	    System.out.println("Author " + object[0] + " " + object[1]);
	}

	// -----------------------------------------------------------------------
	final Query q02 = em.createNamedQuery("selectAuthorEntities");
	@SuppressWarnings("unchecked")
	final List<Author> result02 = q02.getResultList();

	for (final Author a : result02) {
	    System.out.println("Author " + a.firstName + " " + a.lastName);
	}

	// -----------------------------------------------------------------------
	final Query q03 = em.createNamedQuery("selectAuthorValue");
	@SuppressWarnings("unchecked")
	final List<AuthorValue> resultList03 = q03.getResultList();

	for (final AuthorValue a : resultList03) {
	    System.out.println("Author " + a.firstName + " " + a.lastName + " wrote " + a.numBooks + " books.");
	}

	// -----------------------------------------------------------------------

	final Query q04 = em.createNamedQuery("selectAuthors");
	@SuppressWarnings("unchecked")
	final List<Author> result04 = q04.getResultList();

	for (final Author a : result04) {
	    System.out.println("Author " + a.firstName + " " + a.lastName);
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
