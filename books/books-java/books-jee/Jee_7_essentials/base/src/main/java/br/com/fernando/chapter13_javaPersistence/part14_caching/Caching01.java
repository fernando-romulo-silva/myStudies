package br.com.fernando.chapter13_javaPersistence.part14_caching;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.time.StopWatch;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class Caching01 {

    // JPA provides two levels of caching.
    //
    // The entities are cached by the entity manager at the first level in the persistence context.
    // The entity manager guarantees that within a single persistence context, for any particular database row, there will only be one object instance.
    // However, the same entity could be managed in another transaction, so appropriate locking should be used.
    //
    // The scope of the persistence context is one EntityManager.
    // This section describes a level 2 (L2) cache of entity objects, which is managed by the EntityManagerFactory and shared by all its EntityManager objects.
    // The broader scope of this cache makes it useful in applications that use many short term EntityManager instances.
    //
    // Second-level caching by the persistence provider can be enabled by the value of the shared-cache-mode element in persistence.xml.
    //
    // <property name="javax.persistence.sharedCache.mode" value="ALL"/>
    //
    // The javax.persistence.sharedCache.mode property can be set to one of the following values:
    //
    // ALL = All entities and entity-related state are cached.
    //
    // NONE = No entities or entity-related state is cached.
    //
    // ENABLE_SELECTIVE = Only cache entities marked with @Cacheable(true) are cached.
    //
    // DISABLE_SELECTIVE = Only cache entities that are not marked @Cacheable(false) are cached.
    //
    // UNSPECIFIED = Persistence-provider-specific defaults apply.

    @Entity
    @Cacheable(false)
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

    @Entity
    @Cacheable // or @Cacheable(true)
    @Table(name = "STUDENT")
    public static class StudentCacheable implements Serializable {

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

    public static void cache01(final List<String> classes, final Properties props) {

	props.put("javax.persistence.sharedCache.mode", "NONE");
	final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", classes, props));
	final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();

	final StopWatch watch = new StopWatch();

	// ------------------------------------------------------------------------------------
	final EntityManager em01 = factory.createEntityManager();

	watch.start();
	em01.createQuery("select s from Caching01$Student s", Student.class).getResultList();
	watch.stop();

	System.out.println("Non Cacheable First Time Elapsed: " + watch.getTime());

	watch.reset();

	em01.clear(); // clear the first level
	em01.close();

	// ------------------------------------------------------------------------------------
	final EntityManager em02 = factory.createEntityManager();

	watch.start();
	em02.createQuery("select s from Caching01$Student s", Student.class).getResultList();
	watch.stop();

	System.out.println("Non Cacheable Second Time Elapsed: " + watch.getTime());

	watch.reset();

	em02.clear(); // clear the first level
	em02.close();

	// ------------------------------------------------------------------------------------
	final EntityManager em03 = factory.createEntityManager();

	watch.start();
	em03.createQuery("select s from Caching01$Student s", Student.class).getResultList();
	watch.stop();

	System.out.println("Non Cacheable Third Time Elapsed: " + watch.getTime());

	watch.reset();
	em03.clear(); // clear the first level
	em03.close();

	// ------------------------------------------------------------------------------------
	final EntityManager em04 = factory.createEntityManager();

	watch.start();
	em04.createQuery("select s from Caching01$Student s", Student.class).getResultList();
	watch.stop();

	System.out.println("Non Cacheable Fourth Time Elapsed: " + watch.getTime());

	watch.reset();
	em04.clear(); // clear the first level
	em04.close();

	factory.close();
    }

    public static void cache02(final List<String> classes, final Properties props) {

	final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", classes, props));
	final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();

	final StopWatch watch = new StopWatch();

	// ------------------------------------------------------------------------------------
	final EntityManager em01 = factory.createEntityManager();

	watch.start();
	em01.createQuery("select s from Caching01$StudentCacheable s", StudentCacheable.class).getResultList();
	watch.stop();

	System.out.println("Cacheable First Time Elapsed: " + watch.getTime());
	watch.reset();

	em01.clear(); // clear the first level
	em01.close();

	// ------------------------------------------------------------------------------------
	final EntityManager em02 = factory.createEntityManager();

	watch.start();
	em02.createQuery("select s from Caching01$StudentCacheable s", StudentCacheable.class).getResultList();
	watch.stop();

	System.out.println("Cacheable Second Time Elapsed: " + watch.getTime());
	watch.reset();

	em02.clear(); // clear the first level
	em02.close();

	// ------------------------------------------------------------------------------------
	final EntityManager em03 = factory.createEntityManager();

	watch.start();
	em03.createQuery("select s from Caching01$StudentCacheable s", StudentCacheable.class).getResultList();
	watch.stop();

	System.out.println("Cacheable Third Time Elapsed: " + watch.getTime());
	watch.reset();

	em03.clear(); // clear the first level
	em03.close();

	// ------------------------------------------------------------------------------------
	final EntityManager em04 = factory.createEntityManager();

	watch.start();
	em04.createQuery("select s from Caching01$StudentCacheable s", StudentCacheable.class).getResultList();
	watch.stop();

	System.out.println("Cacheable Fourth Time Elapsed: " + watch.getTime());
	watch.reset();

	em04.clear(); // clear the first level
	em04.close();

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

	props.put("javax.persistence.sharedCache.mode", "ENABLE_SELECTIVE");
	props.put("javax.persistence.retrieveMode.mode", "ENABLE_SELECTIVE");

	props.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");

	// Execute only one, because the database is caching

	try (final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

	    embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
	    embeddedRdbms.addSchema("APP", dataBaseName);
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);
	    embeddedRdbms.executeFileScripts(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD, "src/main/resources/chapter13_javaPersistence/part14_caching/load.sql");

	    cache01(Arrays.asList(Student.class.getName()), props);
	    // Non Cacheable First Time Elapsed: 400
	    // Non Cacheable Second Time Elapsed: 190
	    // Non Cacheable Third Time Elapsed: 82
	    // Non Cacheable Fourth Time Elapsed: 27
	}

	System.gc();

	try (final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

	    embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
	    embeddedRdbms.addSchema("APP", dataBaseName);
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);
	    embeddedRdbms.executeFileScripts(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD, "src/main/resources/chapter13_javaPersistence/part14_caching/load.sql");
	    
	    cache02(Arrays.asList(StudentCacheable.class.getName()), props);
	    // Cacheable First Time Elapsed: 612
	    // Cacheable Second Time Elapsed: 253
	    // Cacheable Third Time Elapsed: 37
	    // Cacheable Fourth Time Elapsed: 17
	}

    }

}
