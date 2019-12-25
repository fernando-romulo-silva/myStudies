package br.com.fernando.chapter13_javaPersistence.part14_caching;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.time.StopWatch;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class Caching02 {

    // Using the Shared Cache
    //
    // The shared cache (when enabled) provides the following functionality automatically:
    //
    // On retrieval - shared cache is used for entity objects that are not in the persistence context.
    // If an entity object is not available also in the shared cache - it is retrieved from the database
    // and added to the shared cache.
    //
    // On commit - new and modified entity objects are added to the shared cache.
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
    }

    // =================================================================================================================================================================
    // javax.persistence.cache.retrieveMode
    //
    // The "javax.persistence.cache.retrieveMode" property specifies if the shared cache is used on retrieval.
    // Two values are available for this property as constants of the CacheRetrieveMode enum:
    //
    // CacheRetrieveMode.USE - cache is used.
    //
    // CacheRetrieveMode.BYPASS - cache is not used. Direct access data base
    //
    public static void cache01(final List<String> classes, final Properties props) {

	props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
	final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", classes, props));
	final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();

	final StopWatch watch = new StopWatch();

	// ------------------------------------------------------------------------------------
	// The default setting is USE. It can be changed for a specific EntityManager:

	final EntityManager em01 = factory.createEntityManager();
	em01.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);

	watch.start();

	final TypedQuery<Student> query01 = em01.createQuery("select s from Caching02$Student s", Student.class);
	// Before executing a query:
	query01.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);

	query01.getResultList();
	watch.stop();

	System.out.println("Non Cacheable First Time Elapsed: " + watch.getTime());

	watch.reset();
	em01.clear(); // clear the first level
	em01.close();

	// ------------------------------------------------------------------------------------
	final EntityManager em02 = factory.createEntityManager();

	watch.start();
	em02.createQuery("select s from Caching02$Student s", Student.class).getResultList();
	watch.stop();

	System.out.println("Non Cacheable Second Time Elapsed: " + watch.getTime());

	watch.reset();
	em02.clear(); // clear the first level
	em02.close();

	// ------------------------------------------------------------------------------------
	final EntityManager em03 = factory.createEntityManager();
	// For retrieval by type and primary key:
	watch.start();
	em03.find(Student.class, 1, Collections.<String, Object>singletonMap("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS));
	watch.stop();

	em03.clear(); // clear the first level
	em03.close();

	factory.close();
    }

    public static void cache02(final List<String> classes, final Properties props) {

	props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
	final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", classes, props));
	final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();

	final StopWatch watch = new StopWatch();

	// ------------------------------------------------------------------------------------
	final EntityManager em01 = factory.createEntityManager();
	em01.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

	watch.start();
	em01.createQuery("select s from Caching02$Student s", Student.class).getResultList();
	watch.stop();

	System.out.println("Cacheable First Time Elapsed: " + watch.getTime());
	watch.reset();

	em01.clear(); // clear the first level
	em01.close();

	// ------------------------------------------------------------------------------------

	final EntityManager em02 = factory.createEntityManager();

	watch.start();
	final TypedQuery<Student> query02 = em02.createQuery("select s from Caching02$Student s", Student.class);
	// Before executing a query:
	query02.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

	query02.getResultList();
	watch.stop();

	System.out.println("Non Cacheable First Time Elapsed: " + watch.getTime());

	watch.reset();
	em02.clear(); // clear the first level
	em02.close();
	
	factory.close();
    }

    // =================================================================================================================================================================
    // javax.persistence.cache.storeMode
    //
    // The "javax.persistence.cache.storeMode" property specifies if new data should be added to the cache on commit and on retrieval.
    // The property has three valid values, which are defined as constants of the CacheStoreMode enum:
    //
    // CacheStoreMode.BYPASS - cache is not updated with new data.
    //
    // CacheStoreMode.USE - new data in stored in the cache - but only for entity objects that are not in the cache already.
    //
    // CacheStoreMode.REFRESH - new data is stored in the cache - refreshing entity objects that are already cached.
    //
    // The difference between CacheStoreMode.USE and CacheStoreMode.REFRESH is when bypassing the cache in retrieval operations.
    // In this case, an entity object that is already cached is updated using the fresh retrieved data only when CacheStoreMode.REFRESH is used.
    // This might be useful when the database might be updated by other applications (or using other EntityManagerFactory instances).

    public static void cache03(final List<String> classes, final Properties props) {

	props.put("javax.persistence.cache.storeMode", CacheStoreMode.USE);

	final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", classes, props));
	final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();

	final StopWatch watch = new StopWatch();

	// ------------------------------------------------------------------------------------
	final EntityManager em01 = factory.createEntityManager();
	em01.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.USE);

	watch.start();
	em01.createQuery("select s from Caching02$Student s", Student.class).getResultList();
	watch.stop();

	System.out.println("Cacheable First Time Elapsed: " + watch.getTime());
	watch.reset();

	// ------------------------------------------------------------------------------------

	em01.getTransaction().begin();

	final Student s = new Student();
	s.id = 10000;
	s.name = "NO NAME";
	s.age = 10000;

	em01.persist(s); // persiste on the cache too

	em01.flush();
	em01.getTransaction().commit();

	em01.clear(); // clear the first level
	em01.close();
	factory.close();
	// ------------------------------------------------------------------------------------
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
	
	props.put("javax.persistence.sharedCache.mode", "ALL");
	props.put("javax.persistence.retrieveMode.mode", "ALL");
	
	props.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");

	try (final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

	    embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
	    embeddedRdbms.addSchema("APP", dataBaseName);
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);
	    embeddedRdbms.executeFileScripts(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD, "src/main/resources/chapter13_javaPersistence/part14_caching/load.sql");

	    // Execute only one, because the database is caching
	    cache01(Arrays.asList(Student.class.getName()), props);
	    cache02(Arrays.asList(Student.class.getName()), props);
	    cache03(Arrays.asList(Student.class.getName()), props);
	}

    }

}
