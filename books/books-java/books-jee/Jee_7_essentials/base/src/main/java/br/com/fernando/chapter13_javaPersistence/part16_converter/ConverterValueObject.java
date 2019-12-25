package br.com.fernando.chapter13_javaPersistence.part16_converter;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class ConverterValueObject {

    // JPA allows the developer to specify methods to convert between the database and the Java representation of an attribute.
    //
    // A Converter supports type conversion of all basic attributes defined by entity classes, mapped superclasses, or embeddable classes.
    //
    // The only exceptions are Id attributes, version attributes, relationship attributes and attributes annotated as Temporal or Enumerated.
    @Entity
    @Table(name = "EMPLOYEE_SCHEMA_CONVERTER")
    @NamedQueries({ //
	    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM ConverterValueObject$Employee e") //
    })
    public static class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	int id;

	@Column(length = 50, name = "NAME")
	String name;

	@Convert(converter = CreditCardConverter.class)
	CreditCard card;

	public Employee() {
	}

	public Employee(String name) {
	    this.name = name;
	}

	public Employee(String name, CreditCard card) {
	    this.name = name;
	    this.card = card;
	}

	public boolean equals(Object o) {
	    if (this == o) {
		return true;
	    }

	    if (!(o instanceof Employee)) {
		return false;
	    }

	    final Employee employee = (Employee) o;

	    if (card != null ? !card.equals(employee.card) : employee.card != null)
		return false;

	    if (!name.equals(employee.name))
		return false;

	    return true;
	}

	@Override
	public int hashCode() {
	    return Objects.hashCode(id);
	}

	@Override
	public String toString() {
	    return name + " (" + card + ")";
	}
    }

    public static class CreditCard implements Serializable {

	private static final long serialVersionUID = 1L;

	String cardNumber;

	public CreditCard() {
	}

	public CreditCard(String cardNumber) {
	    this.cardNumber = cardNumber;
	}

	@Override
	public String toString() {
	    return cardNumber;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) {
		return true;
	    }

	    if (!(o instanceof CreditCard)) {
		return false;
	    }

	    final CreditCard that = (CreditCard) o;

	    if (cardNumber != null ? !cardNumber.equals(that.cardNumber) : that.cardNumber != null)
		return false;

	    return true;
	}

	@Override
	public int hashCode() {
	    return cardNumber != null ? cardNumber.hashCode() : 0;
	}
    }

    // A Converter must implement the javax.persistence.AttributeConverter<X, Y> interface, where X is the class of the entity representation and Y the class of the database representation of the attribute.
    // Additionally a Converter has to be annotated with the javax.persistence.Converter annotation.
    @Converter
    public static class CreditCardConverter implements AttributeConverter<CreditCard, String> {

	@Override
	public String convertToDatabaseColumn(CreditCard attribute) {
	    return attribute.toString();
	}

	@Override
	public CreditCard convertToEntityAttribute(String card) {
	    return new CreditCard(card);
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

	props.put("javax.persistence.sharedCache.mode", "ENABLE_SELECTIVE");
	props.put("javax.persistence.retrieveMode.mode", "ENABLE_SELECTIVE");

	props.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");

	try (final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

	    embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
	    embeddedRdbms.addSchema("APP", dataBaseName);
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);
	    embeddedRdbms.executeFileScripts(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD, "src/main/resources/chapter13_javaPersistence/part16_converter/create.sql");
	    embeddedRdbms.executeFileScripts(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD, "src/main/resources/chapter13_javaPersistence/part16_converter/load.sql");

	    final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", Arrays.asList(Employee.class.getName()), props));
	    final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();

	    // ------------------------------------------------------------------------------------
	    final EntityManager em = factory.createEntityManager();

	    System.out.println(em.createNamedQuery("Employee.findAll", Employee.class).getResultList());

	    // ------------------------------------------------------------------------------------
	    Employee emp01 = new Employee();
	    emp01.id = 10;
	    emp01.name = "Paul";
	    emp01.card = new CreditCard("111-1111");

	    // ------------------------------------------------------------------------------------
	    em.getTransaction().begin();

	    em.persist(emp01);

	    em.getTransaction().commit();

	    em.clear();

	    // ------------------------------------------------------------------------------------
	    System.out.println(em.find(Employee.class, 10));

	    em.close();

	    factory.close();
	}
    }

}
