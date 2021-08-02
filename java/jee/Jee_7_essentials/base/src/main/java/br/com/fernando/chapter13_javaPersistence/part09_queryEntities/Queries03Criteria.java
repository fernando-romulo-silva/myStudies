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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Author;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.AuthorValue;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Author_;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.BlogPost;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Book;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Publication;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Publisher;

public class Queries03Criteria {

    // https://www.objectdb.com/java/jpa/query/criteria

    // JPQL queries are defined as strings, similarly to SQL.
    // JPA criteria queries, on the other hand, are defined by instantiation of Java objects that represent query elements.
    //
    // A major advantage of using the criteria API is that errors can be detected earlier, during compilation rather than at runtime.
    // On the other hand, for many developers string based JPQL queries, which are very similar to SQL queries, are easier to use and understand.
    //
    // For simple static queries - string based JPQL queries (e.g. as named queries) may be preferred.
    // For dynamic queries that are built at runtime - the criteria API may be preferred.

    public static void executeQueries(EntityManager em) {

	// Criteria Query Structure
	//
	// Queries in JPA (as in SQL) are composed of clauses. Because JPQL queries and criteria queries use equivalent clauses.
	//
	// Specific details about criteria query clauses are provided in the following page sections:
	//

	// SELECT clause (select, distinct, multiselect, array, tuple, construct)
	// FROM clause (from, join, fetch)
	// WHERE clause (where)
	// GROUP BY / HAVING clauses (groupBy, having, count, sum, avg, min, max, ...)
	// ORDER BY clause (orderBy, Order, asc, desc).

	CriteriaBuilder cb01 = em.getCriteriaBuilder();
	CriteriaQuery<Author> cq01 = cb01.createQuery(Author.class);
	Root<Author> root01 = cq01.from(Author.class);

	// use metadata class to define the where clause
	cq01.where(cb01.like(root01.get(Author_.firstName), "J%"));

	final List<Author> result01 = em.createQuery(cq01).getResultList();
	System.out.println(result01);

	// ---------------------------------------------------------------------
	// Generating metamodel classes
	// OK, wait a second before you go ahead and start to create metamodel classes for your entities.
	// There is no need for that.
	// The JPA specification suggests to use an annotation processor to generate the metamodel classes and that is what the different implementations do.
	// Unfortunately each and every implementation provides its own way for it.
	//
	// I describe the required maven build configuration for Hibernate below.
	// dding the Hibernate Static Metamodel Generator to your build process is extremely simple.
	// You only need to add it to your build classpath.
	// The following code snippet shows the required dependency declaration for a maven build.
	//
	// <dependency>
	// <groupId>org.hibernate</groupId>
	// <artifactId>hibernate-jpamodelgen</artifactId>
	// </dependency>

	// ---------------------------------------------------------------------

	final CriteriaBuilder cb02 = em.getCriteriaBuilder();
	final CriteriaQuery<AuthorValue> cq02 = cb02.createQuery(AuthorValue.class);
	final Root<Author> root02 = cq02.from(Author.class);

	cq02.select(cb02.construct(AuthorValue.class, root02.get(Author_.id), root02.get(Author_.firstName)));

	final List<AuthorValue> result02 = em.createQuery(cq02).getResultList();
	System.out.println(result02);

	// Criteria Query Expressions
	// JPA query clauses are composed of expressions. Because JPQL queries and criteria queries use equivalent expressions
	// Specific details about criteria query expressions are provided in the following page sections:
	//
	// Literals and Dates (literal, nullLiteral, currentDate, ...).
	// Paths, navigation and types (get, type).
	// Arithmetic expressions (sum, diff, prod, quot, mod, abs, neg, sqrt).
	// String expressions (like, length, locate, lower, upper, concat, substring, ...).
	// Collection expressions (isEmpty, isNotEmpty, isMember, isNotMember, size).
	// Comparison expressions (equal, notEqual, gt, ge, lt, le, between, isNull, ...)
	// Logical expressions (and, or, not, isTrue).

	// ---------------------------------------------------------------------
	// Polymorphic queries
	// One feature provided by JPA inheritance is the ability to fetch entities by their associated base class.
	// This is called a polymorphic query, and the following query selects both the Post and Announcement entities:

	final CriteriaBuilder cb03 = em.getCriteriaBuilder();

	final CriteriaQuery<Publication> cq03 = cb03.createQuery(Publication.class);

	final Root<Publication> r03 = cq03.from(Publication.class);

	cq03.where( //
		cb03.equal(r03.get("id"), 1L) //
	);

	final List<Publication> result03 = em.createQuery(cq03).getResultList();
	System.out.println(result03);

	// ---------------------------------------------------------------------
	// Subclass filtering
	// Now, if you want to select only the Post subclass, you can change the query Root like this:

	final CriteriaBuilder cb04 = em.getCriteriaBuilder();

	final CriteriaQuery<Book> cq04 = cb04.createQuery(Book.class);
	final Root<Book> r04 = cq04.from(Book.class);

	cq04.where( //
		cb03.equal(r04.get("id"), 1L) //
	);

	final List<Book> result04 = em.createQuery(cq04).getResultList();
	System.out.println(result04);

	// However, if we want to modify the first query so that we can dynamically filter the Publication entities by the subclass type,
	// we can use the type method of the Path Criteria API class for this task:

	final Class<? extends Publication> sublcass = BlogPost.class;

	final CriteriaBuilder cb05 = em.getCriteriaBuilder();

	final CriteriaQuery<Publication> cq05 = cb05.createQuery(Publication.class);
	final Root<Publication> r05 = cq05.from(Publication.class);

	cq05.where( //
		cb05.and( //
			cb05.equal(r04.get("title"), "Post 01"), //
			cb05.equal(r05.type(), sublcass) //
		));

	final List<Publication> result05 = em.createQuery(cq05).getResultList();
	System.out.println(result05);
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
