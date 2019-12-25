package br.com.fernando.chapter13_javaPersistence.part03_persistenceContext_and_typeSafe;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_DATA_SOURCE_NAME;
import static br.com.fernando.Util.HSQLDB_JDBC_DRIVER;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource;
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource.MyEmbeddedDataSourceBuilder;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;
import org.myembedded.rdbms.MyEmbeddedRdbms;

public class PersistenceContextTypeSafe {

    // The persistence context is propagated across multiple transactions for a container-managed entity manager,
    // and the container is responsible for managing the life cycle of the entity manager.

    // An application-managed entity manager is obtained by the application from an entity manager factory:

    // A new isolated persistence context is created when a new entity manager is requested, and the application is responsible for managing the life cycle of the entity manager.
    //
    /// An entity manager and persistence context are not required to be threadsafe.
    // This requires an entity manager to be obtained from an entity manager factory in Java EE components that are not required to be threadsafe, such as servlets.

    // ================================================================================================================================

    @Entity
    @Table(name = "MOVIE_PU_TYPESAFE")
    @NamedQueries({ //
	    @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM PersistenceContextTypeSafe$Movie m"), //
    })
    public static class Movie implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	Integer id;

	@NotNull
	@Size(min = 1, max = 50)
	String name;

	@NotNull
	@Size(min = 1, max = 200)
	String actors;

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

	@Override
	public String toString() {
	    return "Movie{" + "id=" + id + ", name='" + name + '\'' + ", actors='" + actors + '\'' + '}';
	}
    }

    @Qualifier
    @Retention(RUNTIME)
    @Target({ METHOD, FIELD, PARAMETER, TYPE })
    public static @interface DefaultDatabase {
    }

    @ManagedBean
    public static class ProducerBean {

	@Produces
	@PersistenceContext(unitName = "defaultPU")
	@DefaultDatabase
	private static EntityManager defaultEM;
    }

    @Stateless
    public static class MySessionBean {

	@Inject
	@DefaultDatabase
	private EntityManager defaultEM;

	public List<Movie> listMovies() {
	    return defaultEM.createNamedQuery("Movie.findAll", Movie.class).getResultList();
	}
    }

    // ==================================================================================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(name = "defaultPU")
	EntityManager persistenceContextEM;

	@Inject
	@DefaultDatabase
	private EntityManager defaultEM;

	@EJB
	MySessionBean bean;

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

	    final List<Movie> movies = bean.listMovies();

	    System.out.println(movies);

	    System.out.println("=====================================================================");

	    final List<Movie> defaultFindAll = defaultEM.createNamedQuery("Movie.findAll", Movie.class).getResultList();
	    System.out.println(defaultFindAll.isEmpty());

	    System.out.println("Equals :" + Arrays.equals(movies.toArray(), defaultFindAll.toArray()));
	    System.out.println("=====================================================================");

	    final List<Movie> persistenceContextFindAll = persistenceContextEM.createNamedQuery("Movie.findAll", Movie.class).getResultList();

	    System.out.println("Equals :" + Arrays.equals(movies.toArray(), persistenceContextFindAll.toArray()));

	}
    }

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
	startVariables();

	final String dataBaseName = EMBEDDED_JEE_TEST_APP_NAME + "DB";
	final String dataBaseUrl = "jdbc:hsqldb:hsql://localhost:" + DATA_BASE_SERVER_PORT + "/" + dataBaseName;

	try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer(); //
		final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

	    embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);

	    final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
	    war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
	    war.addMetaInfFiles(EmbeddedResource.add("persistence.xml", "src/main/resources/chapter13_javaPersistence/part03_persistenceContext_and_typeSafe/persistence.xml"));
	    war.addMetaInfFiles(EmbeddedResource.add("load.sql", "src/main/resources/chapter13_javaPersistence/part03_persistenceContext_and_typeSafe/load.sql"));
	    war.addMetaInfFiles(EmbeddedResource.add("create.sql", "src/main/resources/chapter13_javaPersistence/part03_persistenceContext_and_typeSafe/create.sql"));
	    war.addMetaInfFiles(EmbeddedResource.add("drop.sql", "src/main/resources/chapter13_javaPersistence/part03_persistenceContext_and_typeSafe/drop.sql"));
	    

	    war.addClasses(ServletPrincipal.class, ProducerBean.class, Movie.class, DefaultDatabase.class, MySessionBean.class);

	    final File appFile = war.exportToFile(APP_FILE_TARGET);

	    final MyEmbeddedDataSource dataSource = new MyEmbeddedDataSourceBuilder(EMBEDDED_JEE_TEST_DATA_SOURCE_NAME, dataBaseUrl)//
		    .withCredential(DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD) //
		    .withDataBaseDriverClass(HSQLDB_JDBC_DRIVER) //
		    .build();

	    embeddedJeeServer.addDataSource(dataSource);
	    embeddedJeeServer.start(HTTP_PORT);

	    embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, appFile.getAbsolutePath());

	    // ------------ Client -----------------------------------------------------------
	    final HttpClient httpClient = HttpClientBuilder.create().build();
	    final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));

	    System.out.println(response);

	    Thread.sleep(5000); // 5 seconds only for the server finishs its jobs...

	} catch (final Exception ex) {
	    System.out.println(ex);
	}

	downVariables();
    }
}
