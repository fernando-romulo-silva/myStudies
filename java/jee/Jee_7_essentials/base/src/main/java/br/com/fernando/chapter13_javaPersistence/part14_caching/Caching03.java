package br.com.fernando.chapter13_javaPersistence.part14_caching;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.Cache;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class Caching03 {

    @Entity
    @Cacheable(true)
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

    // Using the Cache Interface
    // The shared cache is represented by the Cache interface.

    // =================================================================================================================================================================
    public static void cache01(final List<String> classes, final Properties props) {

	props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
	props.put("javax.persistence.cache.storeMode", CacheStoreMode.USE);

	final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", classes, props));
	final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();

	// ------------------------------------------------------------------------------------

	final EntityManager em01 = factory.createEntityManager();
	em01.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
	em01.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.USE);
	
	//
	final Map<String, Object> properties = new HashMap<>();
	properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
	properties.put("javax.persistence.cache.storeMode", CacheStoreMode.USE);

	Student student = em01.find(Student.class, 1, properties);

	System.out.println(student);

	// A Cache instance can be obtained by using the EntityManagerFactory's getCache method:
	final Cache cache = factory.getCache();
	
	// The Cache object enables checking if a specified entity object is cached:
	boolean isCached = cache.contains(Student.class, 1);

	System.out.println("Student 1: " + isCached);

	// Remove a specific entity object from the shared cache:
	cache.evict(Student.class, 1);

	isCached = cache.contains(Student.class, 1);

	System.out.println("Student 1: " + isCached);

	// Remove all the instances of a specific class from the cache:
	cache.evict(Student.class);

	// Clear the shared cache by removing all the cached entity objects:
	cache.evictAll();

	em01.clear();
	em01.close();

	factory.close();
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
	}
    }

}
