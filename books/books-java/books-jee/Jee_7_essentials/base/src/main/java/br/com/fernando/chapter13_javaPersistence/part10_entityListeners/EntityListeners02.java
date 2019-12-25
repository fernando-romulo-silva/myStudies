package br.com.fernando.chapter13_javaPersistence.part10_entityListeners;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import javax.validation.constraints.Size;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class EntityListeners02 {

    // JPA 2.1 supports dependency injection.
    // For a Java EE 7 archive where CDI is enabled by default, additional life-cycle callback methods annotated
    // with @PostConstruct and @PreDestroy may be used.
    //
    // The callback methods may be defined on a separate class and associated with this entity via @EntityListeners.
    // The method signature in that case will be different:

    @Entity
    @Table(name = "MOVIE_LISTENER")
    @EntityListeners(MovieListener.class)
    public static class Movie implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	Integer id;

	@Size(min = 1, max = 50)
	@Column(name = "NAME")
	String name;

	@Override
	public boolean equals(Object o) {
	    if (this == o) {
		return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
		return false;
	    }

	    Movie movie = (Movie) o;

	    return Objects.equals(id, movie.id);
	}

	@Override
	public int hashCode() {
	    return Objects.hashCode(id);
	}
    }

    // Multiple entity listeners may be defined for an entity class or mapped superclass.
    // Each listener is invoked in the same order as it is specified in the EntityListeners annotation.
    //
    // The argument is the entity instance for which the callback method is invoked.
    //
    // You can define the callback listeners using the XML descriptors bundled in META-INF/orm.xml
    public static class MovieListener {
	public static CountDownLatch entityListenersCountDownLatch = new CountDownLatch(26);

	public static boolean postLoadInvoked;
	public static boolean prePersistInvoked;
	public static boolean postPersistInvoked;
	public static boolean preUpdateInvoked;
	public static boolean postUpdateInvoked;
	public static boolean preRemoveInvoked;
	public static boolean postRemoveInvoked;

	@PostLoad
	public void newMovieLoad(Movie movie) {
	    postLoadInvoked = true;
	    entityListenersCountDownLatch.countDown();
	    System.out.println("## Movie loaded: " + movie.name);
	}

	@PrePersist
	public void newMovieAlertBefore(Movie movie) {
	    prePersistInvoked = true;
	    entityListenersCountDownLatch.countDown();
	    System.out.println("## Ready to create new movie: " + movie.name);
	}

	@PostPersist
	public void newMovieAlertAfter(Movie movie) {
	    postPersistInvoked = true;
	    entityListenersCountDownLatch.countDown();
	    System.out.println("## New movie created: " + movie.name);
	}

	@PreUpdate
	public void updateMovieAlertBefore(Movie movie) {
	    preUpdateInvoked = true;
	    entityListenersCountDownLatch.countDown();
	    System.out.println("## Ready to update movie: " + movie.name);
	}

	@PostUpdate
	public void updateMovieAlertAfter(Movie movie) {
	    postUpdateInvoked = true;
	    entityListenersCountDownLatch.countDown();
	    System.out.println("## Movie updated: " + movie.name);
	}

	@PreRemove
	public void deleteMovieAlertBefore(Movie movie) {
	    preRemoveInvoked = true;
	    entityListenersCountDownLatch.countDown();
	    System.out.println("## Ready to delete movie: " + movie.name);
	}

	@PostRemove
	public void deleteMovieAlertAfter(Movie movie) {
	    postRemoveInvoked = true;
	    entityListenersCountDownLatch.countDown();
	    System.out.println("## Movie deleted: " + movie.name);
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
	final List<String> classes = Arrays.asList(Movie.class.getName());

	try (final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

	    embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
	    embeddedRdbms.addSchema("APP", dataBaseName);
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);

	    final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", classes, props));
	    final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();
	    final EntityManager em = factory.createEntityManager();

	    // -------------------------------------------------------------------------------------------------------

	    em.getTransaction().begin();

	    final Movie m1 = new Movie();
	    m1.id = 1;
	    m1.name = "Lindsey Craft";

	    em.persist(m1);

	    em.flush();

	    em.getTransaction().commit();

	    em.clear();

	    // --------------------------------------------------------------------------------------------------------

	    final List<Movie> movies = em.createQuery("SELECT s FROM EntityListeners02$Movie s", Movie.class).getResultList();
	    System.out.println(movies);

	    // --------------------------------------------------------------------------------------------------------
	    final Movie mNew = em.find(Movie.class, 1);

	    em.getTransaction().begin();

	    mNew.name = "New Name";

	    // The method studentUpdateAlert is called after the entity has been updated.
	    em.merge(mNew);

	    em.flush();

	    em.getTransaction().commit();

	    // --------------------------------------------------------------------------------------------------------
	    em.getTransaction().begin();

	    // The method studentDeleteAlert is called before the entity is deleted.
	    em.remove(mNew);

	    em.flush();

	    em.getTransaction().commit();

	    // --------------------------------------------------------------------------------------------------------

	    em.close();

	    factory.close();
	}
    }
}
