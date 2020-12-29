package br.com.fernando.chapter13_javaPersistence.part18_metaModelApi;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type.PersistenceType;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class MetaModelApi {

    // https://www.objectdb.com/java/jpa/persistence/metamodel

    // The JPA Metamodel API provides the ability to examine the persistent object model and retrieve details on managed classes and
    // persistent fields and properties, similarly to the ability that Java reflection provides for general Java types.
    //
    //
    // The Metamodel Interface
    public static void metamodelInterface(final EntityManager em) {

	// The main interface of the JPA Metamodel API is Metamodel.
	// It can be obtained either by the EntityManagerFactory's getMetamodel method or by the EntityManager's getMetamodel method (both methods are equivalent).

	Metamodel metamodel = em.getMetamodel();

	// The Metamodel interface provides several methods for exploring user defined persistable types
	// (which are referred to as managed types) in the persistent object model.

	// Get all the managed classes:
	// (entity classes, embeddable classes, mapped super classes)
	Set<ManagedType<?>> allManagedTypes = metamodel.getManagedTypes();

	System.out.println("allManagedTypes " + allManagedTypes.size());

	// Get all the entity classes:
	Set<EntityType<?>> allEntityTypes = metamodel.getEntities();
	System.out.println("allEntityTypes " + allEntityTypes.size());

	// Get all the embeddable classes:
	Set<EmbeddableType<?>> allEmbeddableTypes = metamodel.getEmbeddables();
	System.out.println("allEmbeddableTypes " + allEmbeddableTypes.size());

	// If managed classes are not listed in the persistence unit then only known managed types are returned.
	// This includes all the types whose instances are already stored in the database.
	// Three additional methods can be used to retrieve a specific type by its Class instance:
	// Get a managed type (entity, embeddable or mapped super classes):

	ManagedType<Student> type1 = metamodel.managedType(Student.class);
	System.out.println("Type 1 " + type1);

	// Get an entity type:
	EntityType<Student> type2 = metamodel.entity(Student.class);
	System.out.println("Type 2 " + type2);

	// Get an embeddable type:
	EmbeddableType<Course> type3 = metamodel.embeddable(Course.class);
	System.out.println("Type 3 " + type3);
    }

    // Type Interface Hierarchy
    public static void interfaceHerarchy(final EntityManager em) {

	final Metamodel metamodel = em.getMetamodel();

	final ManagedType<Student> manageType = metamodel.managedType(Student.class);
	// Get a managed type (entity, embeddable or mapped super classes):

	// Types are represented in the Metamodel API by descendant interfaces of the Type interface:

	// BasicType - represents system defined types.
	//
	// ManagedType is an ancestor of interfaces that represent user defined types:
	//
	// -> EmbeddableType - represents user defined embeddable classes.
	//
	// -> IdentifiableType is as a super interface of:
	//
	// ---> MappedSuperclassType - represents user defined mapped super classes.
	// ---> EntityType - represents user defined entity classes.
	//
	// The Type interfaces provides a thin wrapper of Class with only two methods:

	// Get the underlying Java representation of the type:
	final Class<?> cls = manageType.getJavaType();
	System.out.println("JavaType: " + cls);

	// Get one of BASIC, EMBEDDABLE, ENTITY, MAPPED_SUPERCLASS:
	final PersistenceType kind = manageType.getPersistenceType();
	System.out.println("Kind: " + kind.name());

	// The ManagedType interface adds methods for exploring managed fields and properties (which are referred to as attributes).
	// Get all the attributes - including inherited:
	final Set<Attribute<? super Student, ?>> attributes1 = manageType.getAttributes();
	System.out.println("Attributes Person + Student: " + attributes1.size());

	// Get all the attributes - excluding inherited:
	final Set<Attribute<Student, ?>> attributes2 = manageType.getDeclaredAttributes();
	System.out.println("Attributes Student: " + attributes2.size());

	// Get a specific attribute - including inherited:
	final Attribute<? super Student, ?> strAttr1 = manageType.getAttribute("name");
	System.out.println("Attribute Name: " + strAttr1.getName());

	// Get a specific attribute - excluding inherited:
	final Attribute<Student, ?> strAttr2 = manageType.getDeclaredAttribute("grade");
	System.out.println("Attribute Grade: " + strAttr2.getName());

	// ----------------------------------------------------------------------------------------------

	final EntityType<Student> studendEntityType = metamodel.entity(Student.class);

	final EntityType<Person> personEntityType = metamodel.entity(Person.class);

	// ----------------------------------------------------------------------------------------------

	// Get the super type:
	IdentifiableType<? super Student> superType = studendEntityType.getSupertype();
	System.out.println("SuperType " + superType.toString());

	// Checks if the type has a single ID attribute:
	boolean hasSingleId = studendEntityType.hasSingleIdAttribute();
	System.out.println("Has single id? " + hasSingleId);

	// Gets a single ID attribute - including inherited:
	SingularAttribute<? super Person, Integer> id1 = personEntityType.getId(Integer.class);
	System.out.println("id attribute including inherited: " + id1);

	// Gets a single ID attribute - excluding inherited:
	SingularAttribute<Person, Integer> id2 = personEntityType.getDeclaredId(Integer.class);
	System.out.println("id attribute excluding inherited: " + id2);

	// Checks if the type has a version attribute:
	boolean hasVersion = studendEntityType.hasVersionAttribute();
	System.out.println("Has version? " + hasVersion);

	// Gets the version attribute - including inherited:
	SingularAttribute<? super Student, Long> v1 = studendEntityType.getVersion(Long.class);
	System.out.println("Version attribute including inherited: " + v1);

	// Gets the version attribute - excluding inherited:
	SingularAttribute<Person, Long> v2 = personEntityType.getDeclaredVersion(Long.class);

	// Finally, the EntityType interface adds only one additional method for getting the entity name:
	System.out.println("Version attribute excluding inherited: " + v2.getName());
    }

    public static void attributeInterfaceHierarchy(final EntityManager em) {

	final Metamodel metamodel = em.getMetamodel();

	final ManagedType<Student> manageType = metamodel.managedType(Student.class);

	// Managed fields and properties are represented by the Attribute interfaces and its descendant interfaces:
	//
	// SingularAttribute - represents single value attributes.
	//
	// PluralAttribute is an ancestor of interfaces that represent multi value attributes:
	//
	// ---> CollectionAttribute - represents attributes of Collection types.
	// ---> SetAttribute - represents attributes of Set types.
	// ---> ListAttribute - represents attributes of List types.
	// ---> MapAttribute - represents attributes of Map types.

	final Attribute<? super Student, ?> attr = manageType.getAttribute("name");

	// Get the field (or property) name:
	final String name = attr.getName();
	System.out.println("Name: " + name);

	// Get Java representation of the field (or property) type:
	final Class<?> attrType = attr.getJavaType();
	System.out.println("Type: " + attrType.getName());

	// Get Java reflection representation of the field (or property) type:
	final Member member = attr.getJavaMember();
	System.out.println("Member: " + member.getName());

	// Get the type in which this field (or property) is defined:
	final ManagedType<?> entityType = attr.getDeclaringType();
	System.out.println("entityType: " + entityType.getPersistenceType());

    }

    @Entity
    @Table(name = "PERSON")
    @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
    public static class Person implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	Integer id;

	@Column(name = "NAME")
	String name;

	@ElementCollection
	@CollectionTable(name = "PHONE", joinColumns = @JoinColumn(name = "STUDENT_ID"))
	@Column(name = "PH_NO")
	List<String> phoneNumbers;

	@Version
	Long version;

    }

    @Entity
    @Table(name = "STUDENT")
    public static class Student extends Person implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Column(name = "GRADE")
	String grade;

	@Column(name = "PHONE")
	String phone;

	@Embedded
	ContactStudent contactStudent;

	@ElementCollection
	@CollectionTable(name = "COURSE", joinColumns = @JoinColumn(name = "STUDENT_ID"))
	List<Course> courses;

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
    }

    @Embeddable
    public static class Course {

	Integer id;

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
    private static void loadData(final EntityManager em) {
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

	final List<String> classes = Arrays.asList(Course.class.getName(), ContactStudent.class.getName(), Student.class.getName());

	try (final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

	    embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
	    embeddedRdbms.addSchema("APP", dataBaseName);
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);

	    final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", classes, props));
	    final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();
	    final EntityManager em = factory.createEntityManager();

	    loadData(em);

	    metamodelInterface(em);

	    interfaceHerarchy(em);
	    
	    attributeInterfaceHierarchy(em);

	    em.close();

	    factory.close();
	}
    }

}
