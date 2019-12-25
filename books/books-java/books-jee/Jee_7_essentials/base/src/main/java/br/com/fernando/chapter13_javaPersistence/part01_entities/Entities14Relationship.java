package br.com.fernando.chapter13_javaPersistence.part01_entities;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class Entities14Relationship {

    // The @MapKeyJoinColumn requires a third entity, like Manufacturer so that you have an association from Owner to Car
    // and car has also an association to a Manufacturer, so that you can group all Owner's Cars by Manufacturer:

    @Entity
    @Table(name = "OWNER")
    public static class Owner {

	@Id
	@Column(name = "ID")
	long id;
	
	@Column(name = "NAME")
	String name;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
	@MapKeyJoinColumn(name = "MANUFACTURER_ID")
	Map<Manufacturer, Car> cars = new HashMap<>();

	Owner() {
	    super();
	}

	public Owner(long id, String name) {
	    super();
	    this.id = id;
	    this.name = name;
	}

	public void addCar(final Car car) {
	    cars.put(car.manufacturer, car);
	    car.owner = this;
	}

	public void removeCar(final Car car) {
	    cars.remove(car.manufacturer);
	    car.owner = null;
	}
	
	@Override
	public String toString() {
	    final StringBuilder builder = new StringBuilder();

	    builder.append("Car [id=").append(id) //
		    .append(", name=").append(name) //
		    .append(", cars=").append(cars) //
		    .append("]");

	    return builder.toString();
	}	
    }

    @Entity
    @Table(name = "CAR")
    public static class Car {

	@Id
	@Column(name = "ID")
	long id;

	@Column(name = "NAME")
	String name;

	@ManyToOne
	@JoinColumn(name = "OWNER_ID")
	Owner owner;

	@ManyToOne
	@JoinColumn(name = "MANUFACTURER_ID")
	Manufacturer manufacturer;

	Car() {
	    super();
	}

	public Car(final long id, final String name, final Owner owner, final Manufacturer manufacturer) {
	    super();
	    this.id = id;
	    this.name = name;
	    this.owner = owner;
	    this.manufacturer = manufacturer;
	}

	@Override
	public boolean equals(final Object o) {

	    if (this == o)
		return true;

	    if (!(o instanceof Car))
		return false;

	    return Objects.equals(id, ((Car) o).id);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id);
	}

	@Override
	public String toString() {
	    final StringBuilder builder = new StringBuilder();

	    builder.append("Car [id=").append(id) //
		    .append(", name=").append(name) //
		    .append(", manufacturer=").append(manufacturer) //
		    .append("]");

	    return builder.toString();
	}
    }

    @Entity
    @Table(name = "MANUFACTURER")
    public static class Manufacturer {

	@Id
	@Column(name = "ID")
	long id;

	@Column(name = "NAME")
	String name;

	Manufacturer() {
	    super();
	}

	public Manufacturer(long id, String name) {
	    super();
	    this.id = id;
	    this.name = name;
	}

	@Override
	public boolean equals(final Object o) {

	    if (this == o)
		return true;

	    if (!(o instanceof Manufacturer))
		return false;

	    return Objects.equals(id, ((Manufacturer) o).id);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id);
	}

	@Override
	public String toString() {
	    final StringBuilder builder = new StringBuilder();

	    builder.append("Manufacturer [id=").append(id) //
		    .append(", name=").append(name) //
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

	final List<String> classes = Arrays.asList(Owner.class.getName(), Car.class.getName(), Manufacturer.class.getName());

	try (final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

	    embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
	    embeddedRdbms.addSchema("APP", dataBaseName);
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);

	    final HibernatePersistenceUnitInfo info = new HibernatePersistenceUnitInfo("appName", classes, props);
	    final Map<String, Object> configuration = new HashMap<>();
	    final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(info), configuration).build();
	    final EntityManager em = factory.createEntityManager();

	    em.getTransaction().begin();
	    
	    final Manufacturer manufacturer01 = new Manufacturer(1L, "Fiat");
	    final Manufacturer manufacturer02 = new Manufacturer(2L, "Ford");

	    em.persist(manufacturer01);
	    em.persist(manufacturer02);
	    

	    final Owner owner01 = new Owner(1L, "Paul");
	    final Owner owner02 = new Owner(2L, "Mary");
	    
	    owner01.addCar(new Car(1L, "Uno 147", owner01, manufacturer01));
	    owner01.addCar(new Car(2L, "Scort", owner01, manufacturer02));
	    owner02.addCar(new Car(3L, "Palio", owner02, manufacturer01));

	    em.persist(owner01);
	    em.persist(owner02);

	    em.getTransaction().commit();

	    final List<Owner> posts = em.createQuery("SELECT p FROM Entities14Relationship$Owner p", Owner.class).getResultList();
	    System.out.println(posts);

	    final List<Car> postComments = em.createQuery("SELECT p FROM Entities14Relationship$Car p", Car.class).getResultList();
	    System.out.println(postComments);

	    em.close();

	    factory.close();
	}
    }

}
