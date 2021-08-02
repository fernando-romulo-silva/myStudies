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

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Author;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.BlogPost;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Book;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Publication;
import br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities.Publisher;

public class Queries01Jpql {

    // =================================================================================================================================================================
    // Selection - The From clause
    public static void executeQueries(final EntityManager em) {

	// ----------------------------------------------------------------------------------------------------
	// The syntax of a JPQL FROM clause is similar to SQL but uses the entity model instead of table or column names.
	// The following code snippet shows a simple JPQL query in which I select all Author entities.
	final List<Author> authors = em.createQuery("SELECT a FROM Queries00Entities$Author a", Author.class).getResultList();
	System.out.println(authors);

	// ----------------------------------------------------------------------------------------------------
	// If you want to select data from more than one entity, e.g., all authors and the books they’ve written, you have to join the entities in the FROM clause.
	// The easiest way to do that is to use the defined associations of an entity like in the following code snippet.
	@SuppressWarnings("unchecked")
	final List<Object[]> resultList01 = em.createQuery("SELECT a, b FROM Queries00Entities$Author a JOIN a.publications b ").getResultList();

	for (Object[] objects : resultList01) {
	    for (Object object : objects) {
		System.out.println(object);
	    }
	}

	// ----------------------------------------------------------------------------------------------------
	// JOINs of unrelated entities are not supported by the JPA specification, but you can use a theta join which creates a cartesian product and restricts it in the
	// WHERE clause to the records with matching foreign and primary keys.
	// I use this approach in the following example to join the Book with the Publisher entities.
	@SuppressWarnings("unchecked")
	final List<Object[]> resultList02 = em.createQuery("SELECT b, p FROM Queries00Entities$Book b, Queries00Entities$Publisher p ").getResultList();

	for (Object[] objects : resultList02) {
	    for (Object object : objects) {
		System.out.println(object);
	    }
	}

	// ----------------------------------------------------------------------------------------------------
	// Left Outer Joins
	// The query returned only the Author entities with associated Book entities but not the ones for which the database doesn’t contain a Book entity.
	// If you want to include the authors without published books, you have to use a LEFT JOIN, like in the following code snippet.

	@SuppressWarnings("unchecked")
	final List<Object[]> resultList03 = em.createQuery("SELECT a, b FROM Queries00Entities$Author a LEFT JOIN a.publications b").getResultList();

	for (Object[] objects : resultList03) {
	    for (Object object : objects) {
		System.out.println(object);
	    }
	}

	// ----------------------------------------------------------------------------------------------------
	// Path expressions or implicit joins
	// Path expressions create implicit joins and are one of the benefits provided by the entity model.
	// You can use the ‘.’ operator to navigate to related entities as I do in the following code snippet.
	final List<Book> resultList04 = em //
		.createQuery("SELECT b FROM Queries00Entities$Book b WHERE b.publisher.name LIKE '%es%'", Book.class) //
		.getResultList();
	System.out.println(resultList04);

	// ----------------------------------------------------------------------------------------------------
	// Polymorphism
	// When you choose an inheritance strategy that supports polymorphic queries, your query selects all instances of the specified class and its subclasses.
	// With the model in the example for this blog post, you can, for example, select all Publication entities, which are either Book or BlogPost entities.
	//
	final List<Publication> resultList05 = em //
		.createQuery("SELECT b FROM Queries00Entities$Publication b ", Publication.class) //
		.getResultList();
	System.out.println(resultList05);

	// Or you can select a specific subtype of a Publication, like a BlogPost.
	final List<BlogPost> resultList06 = em //
		.createQuery("SELECT b FROM Queries00Entities$BlogPost b ", BlogPost.class) //
		.getResultList();
	System.out.println(resultList06);

	// ----------------------------------------------------------------------------------------------------
	// Downcasting
	// Since JPA 2.1, you can also use the TREAT operator for downcasting in FROM and WHERE clauses.
	// I use that in the following code snippet to select all Author entities with their related Book entities.
	// As you can see in the model, the publications association defines an association between the Author and the Publication entity.
	// So without the TREAT operator, the query would return all Author entities with their associated Book or BlogPost entities.

	@SuppressWarnings("unchecked")
	final List<Object[]> resultList07 = em.createQuery("SELECT a, p FROM Queries00Entities$Author a JOIN treat (a.publications AS Queries00Entities$Book) p").getResultList();
	System.out.println(resultList07);

	// ----------------------------------------------------------------------------------------------------
	// Restriction Where Clause
	// The syntax is very similar to SQL, but JPQL supports only a small subset of the SQL features.
	// If you need more sophisticated features for your query, you can use a native SQL query.
	//
	// JPQL supports a set of basic operators to define comparison expressions.
	// Most of them are identical to the comparison operators supported by SQL, and you can combine them with the logical operators AND, OR and
	// NOT into more complex expressions.
	//
	// Operators for single-valued expressions:
	//
	// Equal: author.id = 10
	//
	// Not equal: author.id <> 10
	//
	// Greater than: author.id > 10
	//
	// Greater or equal then: author.id => 10
	//
	// Smaller than: author.id < 10
	//
	// Smaller or equal then: author.id <= 10
	//
	// Between: author.id BETWEEN 5 and 10
	//
	// Like: author.firstName LIKE ‘%and%’. The % character represents any character sequence.
	// This example restricts the query result to all Authors with a firstName that contains the String ‘and’,
	// like Alexander or Sandra.
	// You can use an _ instead of % as a single character wildcard.
	// You can also negate the operator with NOT to exclude all Authors with a matching firstName.
	//
	// Is null: author.firstName IS NULL. You can negate the operator with NOT to restrict the query result to all Authors who’s firstName IS NOT NULL.]
	//
	// In: author.firstName IN ('John', 'Jane') Restricts the query result to all Authors with the first name John or Jane.
	//

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
