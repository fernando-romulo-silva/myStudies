package br.com.fernando.chapter13_javaPersistence.part19_detachedEntityObjects;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class DetachedEntityObjects {

    // Detached entity objects are objects in a special state in which they are not managed by any EntityManager but still represent objects in the database.
    //
    // Compared to managed entity objects, detached objects are limited in functionality:
    //
    // * Many JPA methods do not accept detached objects
    //
    // * Retrieval by navigation from detached objects is not supported, so only persistent fields that have been loaded before detachment should be used.
    //
    // * Changes to detached entity objects are not stored in the database unless modified detached objects are merged back into an EntityManager to become managed again.
    //
    //
    // Detached objects are useful in situations in which an EntityManager is not available and for transferring objects between different EntityManager instances.
    //
    @Entity
    @Table(name = "ADDRESS")
    public static class Address implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	int id;

	@Column(name = "STREET")
	String street;

	@Column(name = "NUMBER")
	String number;

    }

    @Entity
    @Table(name = "PHONE")
    public static class Phone implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	int id;

	@Column(name = "NUMBER")
	String number;

    }

    // =================================================================================================================================================================
    // Explicit Detach
    //
    public static void explicitDetach(final EntityManager em) {
	// When a managed entity object is serialized and then deserialized, the deserialized entity object (but not the original serialized object) is constructed as
	// a detached entity object since is not associated with any EntityManager.
	//

	final Student st1 = em.find(Student.class, 1);

	// In addition, in JPA 2 we can detach an entity object by using the detach method:
	em.detach(st1);

	em.clear();
    }

    // =================================================================================================================================================================
    // Cascading
    //
    @Entity
    @Table(name = "STUDENT")
    public static class Student implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	int id;

	@Column(name = "NAME")
	String name;

	@Column(name = "AGE")
	int age;

	// ------------------------------------------------------------------------------
	// Cascading Detach
	// Marking a reference field with CascadeType.DETACH (or CascadeType.ALL, which includes DETACH) indicates that detach operations should be cascaded automatically
	// to entity objects that are referenced by that field (multiple entity objects can be referenced by a collection field):
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
	Address address;
	// In the example above, the Student entity class contains an address field that references an instance of Address, which is another entity class.
	//
	// Due to the CascadeType.DETACH setting, when an Student instance is detached the operation is automatically cascaded to the referenced Address instance,
	// which is then automatically detached as well.
	//
	// Cascading may continue recursively when applicable (e.g. to entity objects that the Address object references, if any).

	// ------------------------------------------------------------------------------
	// Cascading Merge
	// Marking a reference field with CascadeType.MERGE (or CascadeType.ALL, which includes MERGE) indicates that merge operations should be cascaded automatically to
	// entity objects that are referenced by that field (multiple entity objects can be referenced by a collection field):
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "PHONE_ID", referencedColumnName = "ID")
	Phone phone;
	// In the example above, the Student entity class contains an phone field that references an instance of Phone, which is another entity class.
	//
	// Due to the CascadeType.MERGE setting, when an Student instance is merged the operation is automatically cascaded to the referenced Phone instance, which is
	// then automatically merged as well.
	//
	// Cascading may continue recursively when applicable (e.g. to entity objects that the Phone object references, if any).
    }

    //
    // =================================================================================================================================================================
    // Bulk Detach
    public static void bulckDetach(final EntityManager em) {
	final List<Student> students = em.createQuery("select p from DetachedEntityObjects$Student p", Student.class).getResultList();

	System.out.println("Amount :" + students.size());

	// The following operations clear the entire EntityManager's persistence context and detach all managed entity objects:
	//
	// Invocation of the close method, which closes an EntityManager.
	// Invocation of the clear method, which clears an EntityManager's persistence context.

	em.clear();
    }

    // =================================================================================================================================================================
    // Explicit Merger
    //
    // The content of the specified detached entity object is copied into an existing managed entity object with the same identity (i.e. same type and primary key).
    public static void explicitMerge(final EntityManager em) {

	final Student st1 = em.find(Student.class, 1);

	em.detach(st1);

	em.clear();

	// Detached objects can be attached to any EntityManager by using the merge method:
	em.merge(st1);

	// If the EntityManager does not manage such an entity object yet a new managed entity object is constructed.
	// The detached object itself, however, remains unchanged and detached.
    }

    // =================================================================================================================================================================
    private static void loadData(final EntityManager em) {
	em.getTransaction().begin();

	final Student st1 = new Student();
	st1.id = 1;
	st1.name = "Lindsey Craft";

	final Address ad1 = new Address();
	ad1.id = 1;
	ad1.street = "First Avenue";
	ad1.number = "15";

	final Phone ph1 = new Phone();
	ph1.id = 1;
	ph1.number = "1345344982";

	st1.address = ad1;
	st1.phone = ph1;

	final Student st2 = new Student();
	st2.id = 2;
	st2.name = "Morgan Philips";

	final Address ad2 = new Address();
	ad2.id = 2;
	ad2.street = "White Avenue";
	ad2.number = "122";

	final Phone ph2 = new Phone();
	ph2.id = 2;
	ph2.number = "683344982";

	st2.phone = ph2;
	st2.address = ad2;

	em.persist(ph1);
	em.persist(ph2);

	em.persist(ad1);
	em.persist(ad2);

	em.persist(st1);
	em.persist(st2);

	em.getTransaction().commit();

	em.clear();
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

	try (final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

	    embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
	    embeddedRdbms.addSchema("APP", dataBaseName);
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);

	    final List<String> classes = Arrays.asList(Student.class.getName(), Address.class.getName(), Phone.class.getName());
	    final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", classes, props));
	    final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();
	    final EntityManager em = factory.createEntityManager();

	    loadData(em);

	    explicitDetach(em);

	    bulckDetach(em);

	    explicitMerge(em);

	    em.close();

	    factory.close();
	}
    }
}
