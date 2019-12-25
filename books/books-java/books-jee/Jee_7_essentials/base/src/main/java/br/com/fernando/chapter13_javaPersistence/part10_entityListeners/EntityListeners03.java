package br.com.fernando.chapter13_javaPersistence.part10_entityListeners;

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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.ExcludeDefaultListeners;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class EntityListeners03 {

    // ==================================================================================================================================================================
    // Please look at src/main/resources/chapter13_javaPersistence/part10_entityListeners/orm.xml

    @Entity
    @Table(name = "MOVIE_LISTENER")
    public static class Movie implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	Integer id;

	@Column(name = "NAME")
	String name;

	@Column(name = "ACTORS")
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
    }

    public static class MovieListener {

	public void newMovieLoad(Movie movie) {
	    System.out.println("## Movie loaded: " + movie.name);
	}
    }

    public static class MyListener {

	public void deleteObjectAlertAfter(Object object) {
	    System.out.println("## Object deleted: " + object);
	}
    }

    // ==================================================================================================================================================================
    // Default listeners are applied by default to all the entity classes.
    // The @ExcludeDefaultListeners annotation can be used to exclude an entity class and all its descendant classes from using the default listeners:

    @Entity
    @ExcludeDefaultListeners
    public static class NoDefaultListenersForThisEntity {
    }

    @Entity
    public static class NoDefaultListenersForThisEntityEither extends NoDefaultListenersForThisEntity {
    }

    // ==================================================================================================================================================================
    @Stateless
    public static class Service {

	@PersistenceContext(name = "MyPU")
	EntityManager em;

	public void persist(Movie e) {
	    em.persist(e);
	}

	public List<Movie> get() {
	    final List<Movie> resultList = em.createQuery("SELECT e FROM EntityListeners03$Movie e", Movie.class).getResultList();

	    Movie m = resultList.get(0);

	    em.remove(m);

	    return resultList;
	}
    }

    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	Service service;

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
	    System.out.println(service.get());
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
	    war.addMetaInfFiles(EmbeddedResource.add("persistence.xml", "src/main/resources/chapter13_javaPersistence/part10_entityListeners/persistence.xml"));
	    war.addMetaInfFiles(EmbeddedResource.add("orm.xml", "src/main/resources/chapter13_javaPersistence/part10_entityListeners/orm.xml"));
	    war.addMetaInfFiles(EmbeddedResource.add("load.sql", "src/main/resources/chapter13_javaPersistence/part10_entityListeners/load.sql"));
	    war.addMetaInfFiles(EmbeddedResource.add("create.sql", "src/main/resources/chapter13_javaPersistence/part10_entityListeners/create.sql"));
	    war.addMetaInfFiles(EmbeddedResource.add("drop.sql", "src/main/resources/chapter13_javaPersistence/part10_entityListeners/drop.sql"));

	    war.addClasses(ServletPrincipal.class, Service.class, Movie.class);

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
