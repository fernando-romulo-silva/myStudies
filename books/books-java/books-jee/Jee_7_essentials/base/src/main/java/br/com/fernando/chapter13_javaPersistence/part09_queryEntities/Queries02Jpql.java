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
import javax.persistence.TypedQuery;

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

public class Queries02Jpql {

    // =================================================================================================================================================================
    // Selection - The From clause
    public static void executeQueries(final EntityManager em) {

	// ----------------------------------------------------------------------------------------------------
	// Operators for collection expressions:
	//
	// Is empty: author.books IS EMPTY
	// Restricts the query result to all Authors that don’t have any associated Book entities.
	// You can negate the operator (IS NOT EMPTY) to restrict the query result to all Authors with associated Book entities.
	//
	// Size: size(author.books) > 2
	// Restricts the query result to all Authors who are associated with more than 2 Book entities.
	//
	// Member of: myBook member of author.books
	// Restricts the query result to all Authors who are associated with a specific Book entity.
	//
	// You can use one or more of the operators to restrict your query result.
	// The following query returns all Author entities with a firstName attribute that contains the String “and” and an
	// id attribute greater or equal 20 and who have written at least 5 books.

	final List<Author> resultList08 = em.createQuery("SELECT a FROM Queries00Entities$Author a WHERE a.firstName like '%and%' and a.id >= 2 and size(publications) >= 1", Author.class).getResultList();
	System.out.println(resultList08);

	// ----------------------------------------------------------------------------------------------------
	// Scalar values
	//
	// Scalar value projections are very similar to the projections you know from SQL.
	// Instead of database columns, you select one or more entity attributes or the return value of a function call with your query.

	final TypedQuery<Object[]> query09 = em.createQuery("SELECT a.firstName, a.lastName FROM Queries00Entities$Author a", Object[].class);
	
	final List<Object[]> resultList09 = query09.getResultList();
	
	for (Object[] objects : resultList09) {
	    for (Object object : objects) {
		System.out.println(object);
	    }
	}

	// ----------------------------------------------------------------------------------------------------
	// Constructor references
	// JPQL allows you to define a constructor call in the SELECT clause. You can see an example of it in the following code snippet.
	// You just need to provide the fully qualified class name and specify the constructor parameters of an existing constructor.

	final List<AuthorValue> resultList10 = em.createQuery("SELECT new br.com.fernando.chapter13_javaPersistence.part09_queryEntities.Queries00Entities$AuthorValue(a.id, a.firstName) FROM  Queries00Entities$Author a ", AuthorValue.class).getResultList();
	System.out.println(resultList10);

	// ----------------------------------------------------------------------------------------------------
	// Distinct query results
	// You probably know SQL’s DISTINCT operator which removes duplicates from a projection.
	// JPQL supports this operator as well.

	final List<Object[]> resultList11 = em.createQuery("SELECT DISTINCT a.lastName FROM Queries00Entities$Author a", Object[].class).getResultList();
	System.out.println(resultList11);

	// Functions
	// Functions are another powerful feature of JPQL that you probably know from SQL.
	// It allows you to perform basic operations in the WHERE and SELECT clause.
	// You can use the following functions in your query:
	//
	// upper(String s): transforms String s to upper case
	//
	// lower(String s): transforms String s to lower case
	//
	// current_date(): returns the current date of the database
	//
	// current_time(): returns the current time of the database
	//
	// current_timestamp(): returns a timestamp of the current date and time of the database
	//
	// substring(String s, int offset, int length): returns a substring of the given String s
	//
	// trim(String s): removes leading and trailing whitespaces from the given String s
	//
	// length(String s): returns the length of the given String s
	//
	// locate(String search, String s, int offset): returns the position of the String search in s. The search starts at the position offset
	//
	// abs(Numeric n): returns the absolute value of the given number
	//
	// sqrt(Numeric n): returns the square root of the given number
	//
	// mod(Numeric dividend, Numeric divisor): returns the remainder of a division
	//
	// treat(x as Type): downcasts x to the given Type
	//
	// size(c): returns the size of a given Collection c
	//
	// index(orderedCollection): returns the index of the given value in an ordered Collection

	// ----------------------------------------------------------------------------------------------------
	// The GROUP BY
	// When you use aggregate functions, like count(), in your SELECT clause, you need to reference all entity attributes
	// that are not part of the function in the GROUP BY clause.
	//
	final List<Object[]> resultList12 = em.createQuery("SELECT a.lastName, COUNT(a) FROM Queries00Entities$Author a GROUP BY a.lastName", Object[].class).getResultList();
	System.out.println(resultList12);

	// ----------------------------------------------------------------------------------------------------
	// The HAVING
	// The HAVING clause is similar to the WHERE clause and allows you to define additional restrictions for your query.
	// The main difference is that the restrictions specified in a HAVING clause are applied to a group and not to a row.
	//
	final List<Object[]> resultList13 = em.createQuery("SELECT a.lastName, COUNT(a) AS cnt FROM Queries00Entities$Author a GROUP BY a.lastName HAVING a.lastName LIKE 'B%'", Object[].class).getResultList();
	System.out.println(resultList13);
	
	// ----------------------------------------------------------------------------------------------------
	// ORDER BY
	// You can define the order in which the database shall return your query results with an ORDER BY clause. 
	// Its definition in JPQL is similar to SQL.
	
	final List<Author> resultList14 = em.createQuery("SELECT a FROM Queries00Entities$Author a ORDER BY a.lastName ASC, a.firstName DESC", Author.class).getResultList();
	System.out.println(resultList14);
	
	// ----------------------------------------------------------------------------------------------------
	// Subselects
	// A subselect is a query embedded into another query. 
	// It’s a powerful feature you probably know from SQL. 
	// Unfortunately, JPQL supports it only in the WHERE clause and not in the SELECT or FROM clause.
	// Subqueries can return one or multiple records and can use the aliases defined in the outer query.

	final List<Author> resultList15 = em.createQuery("SELECT a FROM Queries00Entities$Author a WHERE (SELECT count(b) FROM Queries00Entities$Book b WHERE a MEMBER OF b.authors ) > 1", Author.class).getResultList();
	System.out.println(resultList15);

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
