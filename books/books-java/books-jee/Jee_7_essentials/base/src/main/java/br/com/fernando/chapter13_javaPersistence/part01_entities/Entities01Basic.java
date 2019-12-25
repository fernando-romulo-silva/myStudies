package br.com.fernando.chapter13_javaPersistence.part01_entities;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class Entities01Basic {

    // A POJO with a no-arg public constructor is used to define the mapping with one or more relational database tables.
    // Each such class is annotated with @Entity, and the instance variables that follow JavaBeans-style properties represent the persistent state of the entity.
    @Entity
    @Table(name = "STUDENT")
    public static class Student implements Serializable {
        // The class implements a Serializable interface, and that allows it to be passed by value through a remote interface.

        @Transient
        private static final long serialVersionUID = 1L;

        // The identity is defined by the field 'id' and is annotated with @Id. 
        // A composite primary key may also be defined where the primary key corresponds to one or more fields of the entity class.
        @Id
        int id;

        // The mapping between the table column and the field name is derived following reasonable defaults and can be overridden by annotations.
        @Column
        String name;

        @Column(name = "GRADE")
        String grade;

        @Column(name = "PHONE")
        String phone;

        @Embedded
        ContactStudent contactStudent;

        // It means that the collection is not a collection of entities, but a collection of simple types (Strings, etc.) or a collection of embeddable elements (class annotated with @Embeddable).
        //
        // It also means that the elements are completely owned by the containing entities: 
        // they're modified when the entity is modified, deleted when the entity is deleted, etc. They can't have their own lifecycle.
        @ElementCollection
        // @ElementCollection can also be applied to an embeddable class.
        @CollectionTable(name = "COURSE", joinColumns = @JoinColumn(name = "STUDENT_ID"))
        List<Course> courses;

        // The @ElementCollection annotation signifies that a student's courses are listed in a different table. 
        // By default, you derive the table name by combining the name of the owning class, the string, and the field name. 
        // @CollectionTable can be used to override the default name of the table, and @AttributeOverrides can be used to override the default column names. 
        @ElementCollection
        @CollectionTable(name = "PHONE", joinColumns = @JoinColumn(name = "STUDENT_ID"))
        @Column(name = "PH_NO")
        List<String> phoneNumbers;

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();

            builder.append("Student [id=").append(id) //
                    .append(", name=").append(name) //
                    .append(", grade=").append(grade) //
                    .append(", phone=").append(phone) //
                    .append(", contactStudent=").append(contactStudent) //
                    .append(", courses=").append(courses) //
                    .append(", phoneNumbers=").append(phoneNumbers) //
                    .append("]");

            return builder.toString();
        }

        // This class has a no-arg constructor by default, as no other constructors are defined.

    }

    // The persistent fields or properties of an entity may be of the following types:
    //
    // Java primitive type
    // java.lang.String
    // java.math.BigInteger and java.math.BigDecimal
    // java.util.Date and java.util.Calendar; the @Temporal annotation may be specified on fields of type java.util.Date and java.util.Calendar to indicate the temporal type of the field
    // java.sql.Date, java.sql.Time, and java.sql.Timestamp
    // byte[], Byte[], char[], Character[], enums, and other Java serializable types
    // Entity types, collections of entity types, embeddable classes, and collections of basic and embeddable classes
    //
    //
    // Course is a POJO class that does not have a persistent identity of its own and exclusively belongs to the Student class. 
    // This class is called as an embeddable class, is identified by @Embedded on the field in the entity class, 
    // and is annotated with @Embeddable in the class definition:
    @Embeddable
    public static class Course {

        int id;

        String name;

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();

            builder.append("Course [id=").append(id) //
                    .append(", name=").append(name) //
                    .append("]");

            return builder.toString();
        }
    }

    @Embeddable
    @AttributeOverrides({ //
            @AttributeOverride(name = "firstName", column = @Column(name = "contact_first_name")), //
            @AttributeOverride(name = "lastName", column = @Column(name = "contact_last_name")), //
            @AttributeOverride(name = "phone", column = @Column(name = "contact_phone")) //
    })
    public static class ContactStudent {

        String firstName;

        String lastName;

        String phone;

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();

            builder.append("ContactStudent [firstName=").append(firstName) //
                    .append(", lastName=").append(lastName) //
                    .append(", phone=").append(phone) //
                    .append("]");

            return builder.toString();
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

        final List<String> classes = Arrays.asList(Course.class.getName(), ContactStudent.class.getName(), Student.class.getName());

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
            st1.phoneNumbers = Arrays.asList("111-111-1111", "222-222-222");

            final Student st2 = new Student();
            st2.id = 2;
            st2.name = "Morgan Philips";
            st2.phoneNumbers = Arrays.asList("333-333-3333");

            final ContactStudent cs1 = new ContactStudent();
            cs1.firstName = "Fist name";
            cs1.lastName = "Last name";
            cs1.phone = "2349883";

            st1.contactStudent = cs1;

            final Course c1 = new Course();
            c1.id = 1;
            c1.name = "Course 1";

            final Course c2 = new Course();
            c2.id = 2;
            c2.name = "Course 2";

            st2.courses = Arrays.asList(c1, c2);

            em.persist(st1);
            em.persist(st2);

            em.getTransaction().commit();

            // --------------------------------------------------------------------------------------------------------

            final List<Student> books = em.createQuery("SELECT s FROM Entities01Basic$Student s", Student.class).getResultList();
            System.out.println(books);

            em.close();

            factory.close();
        }
    }
}
