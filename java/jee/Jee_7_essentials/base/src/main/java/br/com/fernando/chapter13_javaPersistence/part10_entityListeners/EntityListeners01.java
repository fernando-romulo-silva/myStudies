package br.com.fernando.chapter13_javaPersistence.part10_entityListeners;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class EntityListeners01 {

    // An entity goes through multiple life-cycle events such as load, persist, update, and remove.
    // You can place the annotations listed in above methods in the entity class or mapped superclass
    // to receive notification of these life-cycle events.
    //

    @Entity
    @Table(name = "STUDENT")
    public static class Student implements Serializable {

	// The callback methods can have public, private, protected, or package-level access, but must not be static or final.
	
	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	int id;

	@Column(name = "NAME")
	String name;

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("Student [id=").append(id).append(", name=").append(name).append("]");
	    return builder.toString();
	}

	// -----------------------------------------------------
	@PostLoad
	// Executed after an entity has been loaded in the current persistence context or an entity has been refreshed.
	public void studentLoaded() {
	    System.out.println("studentLoaded");
	}

	// -----------------------------------------------------
	@PrePersist
	// Executed before the entity manager persist operation is actually executed or cascaded.
	// If entities are merged, then this is invoked after the entity state has been copied.
	// This method is synchronous with the persist operation.
	public void newStudentAlertBefore() {
	    System.out.println("newStudentAlertBefore");
	}

	@PostPersist
	// Executed after the entity has been persisted or cascaded.
	// Invoked after the database INSERT is executed. Generated key values are available in this method.
	public void newStudentAlert() {
	    System.out.println("newStudentAlert");
	}

	// -----------------------------------------------------
	@PreUpdate
	// Executed before the database UPDATE operation.
	public void updateStudentAlertBefore() {
	    System.out.println("updateStudentAlertBefore");
	}

	@PostUpdate
	// Executed after the database UPDATE operation.
	public void studentUpdateAlert() {
	    System.out.println("studentUpdateAlert");
	}

	// -----------------------------------------------------
	@PreRemove
	// Executed before the entity manager remove operation is actually executed or cascaded.
	// This method is synchronous with the remove operation.
	public void studentDeleteAlert() {
	    System.out.println("studentDeleteAlert");
	}

	@PostRemove
	// Executed after the entity has been removed. Invoked after the database DELETE is executed.
	public void deleteStudentAlertAfter() {
	    System.out.println("deleteStudentAlertAfter");
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
	final List<String> classes = Arrays.asList(Student.class.getName());

	try (final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

	    embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
	    embeddedRdbms.addSchema("APP", dataBaseName);
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);

	    final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", classes, props));
	    final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();
	    final EntityManager em = factory.createEntityManager();

	    // -------------------------------------------------------------------------------------------------------

	    em.getTransaction().begin();

	    final Student st1 = new Student();
	    st1.id = 1;
	    st1.name = "Lindsey Craft";

	    // The method newStudentAlert is called after the entity has been persisted to the data store.
	    em.persist(st1);

	    em.flush();

	    em.getTransaction().commit();

	    em.clear();

	    // --------------------------------------------------------------------------------------------------------

	    // The method studentLoaded is called after the entity has been loaded into the current persistence context or after the refresh operation has been applied to it.
	    final List<Student> students = em.createQuery("SELECT s FROM EntityListeners01$Student s", Student.class).getResultList();
	    System.out.println(students);

	    // --------------------------------------------------------------------------------------------------------
	    final Student st1New = em.find(Student.class, 1);

	    em.getTransaction().begin();

	    st1New.name = "New Name";

	    // The method studentUpdateAlert is called after the entity has been updated.
	    em.merge(st1New);

	    em.flush();

	    em.getTransaction().commit();

	    // --------------------------------------------------------------------------------------------------------
	    em.getTransaction().begin();

	    // The method studentDeleteAlert is called before the entity is deleted.
	    em.remove(st1New);
	    
	    em.flush();

	    em.getTransaction().commit();
	    
	    // --------------------------------------------------------------------------------------------------------

	    em.close();

	    factory.close();
	}
    }
}
