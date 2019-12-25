package br.com.fernando.chapter13_javaPersistence.part11_storedProcedures;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class StoredProcedures {

    // =================================================================================================================================================================
    //
    // JPA 2.1 adds the capability to execute queries that invoke stored procedures defined in the database.
    // You can specify stored procedures either using @NamedStoredProcedureQuery or dynamically.
    //
    public static void execute01(final EntityManager em) {

	long personId = 1l;

	System.out.printf("-- Moving person to history table id: %s --%n", personId);

	// There are different variants of the EntityManager.createXXXStoredProcedureQuery methods that return a StoredProcedureQuery for executing a stored procedure.
	// The name specified in the annotation is used in EntityManager.createNamedStoredProcedureQuery.
	final StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("MOVE_TO_HISTORY");

	// Similar to @NamedQuery, there is @NamedStoredProcedureQuery, which specifies and names a stored procedure, its parameters, corresponding parameter modes
	// (IN, OUT, INOUT, REF_CURSOR) and its result type, if any.

	procedureQuery.registerStoredProcedureParameter("person_id_in", Integer.class, ParameterMode.IN);
	procedureQuery.registerStoredProcedureParameter("status_out", Boolean.class, ParameterMode.OUT);

	// Unlike @NamedQuery, @NamedStoredProcedureQuery names a stored procedure that exists in the database rather than providing a stored procedure definition.

	em.getTransaction().begin();

	procedureQuery.setParameter("person_id_in", (int) personId);

	boolean execute = procedureQuery.execute();
	
	// it returns false if it is an update count or there are no other results other than through the INOUT and OUT parameters, if any.
	if (!execute) {
	    // The getUpdateCount method may be called to obtain the results if it is an update count.
	    System.out.println("Update Count: "+ procedureQuery.getUpdateCount());
	}

	// If a single result set plus any other results are passed back via the INOUT and OUT parameters, 
	// then you can obtain the results using the getResultList and getSingleResult methods.
	//
	// The results from getResultList, getSingleResult, and getUpdateCount must be processed before the INOUT or OUT parameter values are extracted.	
	
	final Object status_out = procedureQuery.getOutputParameterValue("status_out");
	System.out.println("Out status: " + status_out);

	em.getTransaction().commit();
    }

    public static void execute02(final EntityManager em) {
	System.out.println("-- Fetching person History --");

	final StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("fetchPersonHistory");

	boolean execute = procedureQuery.execute();

	// If the execute method returns true, then the first result is a result set;
	while (!execute && procedureQuery.hasMoreResults()) {
	    execute = procedureQuery.execute();
	}

	@SuppressWarnings("unchecked")
	final List<Object[]> resultList = procedureQuery.getResultList();

	for (final Object[] record : resultList) {
	    for (final Object field : record) {
		System.out.print(field + " ");
	    }
	}

	System.out.println();
    }

    // =================================================================================================================================================================
    @Entity
    @Table(name = "PERSON")
    // Unlike @NamedQuery, @NamedStoredProcedureQuery names a stored procedure that exists in the database rather than providing a stored procedure definition.
    //
    // In this code, name uniquely defines this stored procedure query element within a persistence unit, and procedureName identifies the name of the stored procedure in the database.
    @NamedStoredProcedureQuery(name = "fetchPersonHistory", procedureName = "FETCH_PERSON_HISTORY")
    public static class Person {

	public Person() {
	    super();
	}

	public Person(long id, String firstName, String lastName, String address) {
	    super();
	    this.id = id;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.address = address;
	}

	@Id
	@Column(name = "ID")
	long id;

	@Column(name = "FIRST_NAME")
	String firstName;

	@Column(name = "LAST_NAME")
	String lastName;

	@Column(name = "ADDRESS")
	String address;

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();

	    builder.append("Person [id=").append(id) //
		    .append(", firstName=").append(firstName) //
		    .append(", lastName=").append(lastName) //
		    .append(", address=").append(address) //
		    .append("]");

	    return builder.toString();
	}
    }

    // =================================================================================================================================================================
    public static void main(final String[] args) throws Exception {

	final String dataBaseName = EMBEDDED_JEE_TEST_APP_NAME + "DB";
	final String dataBaseUrl = "jdbc:hsqldb:hsql://localhost:" + DATA_BASE_SERVER_PORT + "/" + dataBaseName;

	final Properties props = new Properties();
	props.put("javax.persistence.jdbc.url", dataBaseUrl);
	props.put("javax.persistence.jdbc.user", DATA_BASE_SERVER_LOGIN);
	props.put("javax.persistence.jdbc.password", DATA_BASE_SERVER_PASSWORD);
	props.put("javax.persistence.jdbc.driver", "org.hsqldb.jdbcDriver");
	final List<String> classes = Arrays.asList(Person.class.getName());

	try (final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

	    embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
	    embeddedRdbms.addSchema("APP", dataBaseName);
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);
	    embeddedRdbms.executeFileScripts(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD, "src/main/resources/chapter13_javaPersistence/part11_storedProcedures/procedure.sql");

	    final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", classes, props));
	    final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();
	    final EntityManager em = factory.createEntityManager();

	    // -------------------------------------------------------------------------------------------------------

	    em.getTransaction().begin();

	    em.persist(new Person(1L, "Paul", "Smith", "464 Gorsuch Drive"));
	    em.persist(new Person(2L, "Mary", "Cortez", "112 Yellow Hill"));
	    em.persist(new Person(3L, "Ana", "Silva", "64 Zella Park"));
	    em.persist(new Person(4L, "Jonh", "White", "No address"));

	    em.getTransaction().commit();

	    // --------------------------------------------------------------------------------------------------------

	    em.clear();

	    execute01(em);

	    em.clear();

	    execute02(em);

	    System.out.println("-- finding Person entities -  --");
	    final TypedQuery<Person> query = em.createQuery("SELECT p from StoredProcedures$Person p", Person.class);
	    final List<Person> resultList = query.getResultList();
	    System.out.println(resultList);

	    em.close();

	    factory.close();
	}
    }

}
