package br.com.fernando.chapter13_javaPersistence.part07_updateEntities;

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
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class UpdateEntities {

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

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("Student [id=").append(id).append(", name=").append(name).append("]");
	    return builder.toString();
	}
    }

    @StaticMetamodel(Student.class)
    public static class Student_ {

	public static volatile SingularAttribute<Student, Integer> id;

	public static volatile SingularAttribute<Student, String> name;

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
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);

	    final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", classes, props));
	    final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();
	    final EntityManager em = factory.createEntityManager();

	    // -------------------------------------------------------------------------------------------------------

	    em.getTransaction().begin();

	    final Student st1 = new Student();
	    st1.id = 1;
	    st1.name = "Lindsey Craft";

	    final Student st2 = new Student();
	    st2.id = 2;
	    st2.name = "Morgan Philips";

	    final Student st3 = new Student();
	    st3.id = 3;
	    st3.name = "Paul";

	    em.persist(st1);
	    em.persist(st2);
	    em.persist(st3);
	    
	    em.flush();

	    em.getTransaction().commit();
	    em.clear();

	    // --------------------------------------------------------------------------------------------------------
	    // To update an existing entity, you need to first find it, change the fields, and call the EntityManager.merge method:
	    em.getTransaction().begin();

	    final Student studentOld1 = em.find(Student.class, 1);
	    System.out.println("Old student: " + studentOld1);

	    studentOld1.name = "Michael Silva";
	    em.merge(studentOld1);

	    em.getTransaction().commit();

	    final Student studentNew1 = em.find(Student.class, 1);
	    System.out.println("New student 1: " + studentNew1);

	    // --------------------------------------------------------------------------------------------------------
	    // You can then update the entity using JPQL:

	    em.getTransaction().begin();

	    final Query queryJpql = em.createQuery("UPDATE UpdateEntities$Student s SET s.name = :name WHERE s.id = :id");
	    queryJpql.setParameter("name", "Paul Little");
	    queryJpql.setParameter("id", 2);
	    int numberLineUpdate01 = queryJpql.executeUpdate();
	    System.out.println("Updated: " + numberLineUpdate01);
	    

	    em.getTransaction().commit();

	    final Student studentNew2 = em.find(Student.class, 2);
	    System.out.println("New student 2: " + studentNew2);

	    // --------------------------------------------------------------------------------------------------------
	    // You can use Criteria:

	    em.getTransaction().begin();

	    final CriteriaBuilder builder = em.getCriteriaBuilder();
	    final CriteriaUpdate<Student> updateCriteria = builder.createCriteriaUpdate(Student.class);
	    final Root<Student> updateRoot = updateCriteria.from(Student.class);

	    updateCriteria.where(builder.equal(updateRoot.get(Student_.id), 3));
	    updateCriteria.set(updateRoot.get(Student_.name), "Paul Little");

	    final Query queryCriteria = em.createQuery(updateCriteria);
	    int numberLineUpdate02 = queryCriteria.executeUpdate();
	    System.out.println("Updated: " + numberLineUpdate02);

	    em.getTransaction().commit();

	    final Student studentNew3 = em.find(Student.class, 2);
	    System.out.println("New student 3: " + studentNew3);

	    // --------------------------------------------------------------------------------------------------------
	    // Native queries

	    em.getTransaction().begin();

	    final Query queryNative = em.createNativeQuery("UPDATE STUDENT SET NAME = ? WHERE ID = ?");
	    queryNative.setParameter(1, "Mia Xun");
	    queryNative.setParameter(2, 3);
	    int numberLineUpdate03 = queryCriteria.executeUpdate();
	    System.out.println("Updated: " + numberLineUpdate03);

	    em.getTransaction().commit();

	    final Student studentNew4 = em.find(Student.class, 2);
	    System.out.println("New student 3: " + studentNew4);
	    // --------------------------------------------------------------------------------------------------------

	    // A simple JPQL statement to query all the Student entities and retrieve the results looks like:
	    final List<Student> books = em.createQuery("SELECT s FROM UpdateEntities$Student s", Student.class).getResultList();
	    System.out.println(books);

	    em.close();

	    factory.close();
	}
    }

}
