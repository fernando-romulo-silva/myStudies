package br.com.fernando.chapter13_javaPersistence.part13_transactionsLocking;

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
import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.Column;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource;
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource.MyEmbeddedDataSourceBuilder;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;
import org.myembedded.rdbms.MyEmbeddedRdbms;

public class TransactionsLocking01None {

    // The EntityManager.persist, .merge, .remove, and .refresh methods must be invoked within a transaction context when an entity manager
    // with a transaction-scoped persistence context is used.
    //
    //
    // By default, optimistic concurrency control is assumed. Optimistic locking allows anyone to read and update an entity;
    // however, a version check is made upon commit and an exception is thrown if the version was updated in the database since the entity was read.
    //
    //
    // The version attribute is incremented with a successful commit.
    // If another concurrent transaction tries to update the entity and the version attribute has been updated since the entity was read,
    // then javax.persistence.OptimisticLockException is thrown.

    @Entity
    @Table(name = "MOVIE") //
    @NamedQueries({ //
	    @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM TransactionsLocking01None$Movie m"), //
    })
    public static class Movie implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@NotNull
	private Integer id;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "NAME")
	private String name;

	@NotNull
	@Size(min = 1, max = 200)
	@Column(name = "ACTORS")
	private String actors;

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("Movie [id=").append(id).append(", name=").append(name).append("]");
	    return builder.toString();
	}
    }

    // ==================================================================================================================================================================
    @Stateless
    public static class FirstService {

	@PersistenceContext(name = "myPU")
	EntityManager em;

	@TransactionAttribute(REQUIRED)
	public Movie findMovie(Integer id) {
	    System.out.println("Begin FirstService.findMovie");

	    Movie result = em.find(Movie.class, id);

	    try {

		readCountDownLatch.await(10, SECONDS);

	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	    System.out.println("Done FirstService.findMovie");
	    return result;
	}

	@TransactionAttribute(REQUIRED)
	public void deleteMovie(Integer id) {
	    System.out.println("Begin FirstService.deleteMovie");

	    em.remove(em.find(Movie.class, id));

	    em.flush();

	    try {

		deleteCountDownLatch.await(10, SECONDS);

	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	    System.out.println("Done FirstService.deleteMovie");
	}

	@TransactionAttribute(REQUIRED)
	public void updateMovie(Integer id, String name) {

	    System.out.println("Begin FirstService.updateMovie");

	    Movie movie = em.find(Movie.class, id);

	    movie.name = name;

	    em.merge(movie);
	    em.flush();

	    try {

		updateCountDownLatch.await(10, SECONDS); // wait the countdown

	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	    System.out.println("Done FirstService.updateMovie");
	}

    }

    @Stateless
    public static class SecondService {

	@PersistenceContext(name = "myPU")
	EntityManager em;

	public Movie findMovie(Integer id) {
	    System.out.println("Begin SecondService.findMovie");

	    Movie result = em.find(Movie.class, id);

	    System.out.println("Done SecondService.findMovie");
	    return result;
	}

	@TransactionAttribute(REQUIRED)
	public void updateMovie(Integer id, String name) {
	    System.out.println("Begin SecondService.updateMovie");

	    Movie movie = findMovie(id);

	    movie.name = name;

	    em.merge(movie);
	    em.flush();

	    System.out.println("Begin SecondService.updateMovie");
	}

	@TransactionAttribute(REQUIRED)
	public void updateMovie(Movie movie, String name) {
	    System.out.println("Begin SecondService.updateMovie");

	    movie.name = name;

	    em.merge(movie);
	    em.flush();

	    System.out.println("Done SecondService.updateMovie");
	}

	@SuppressWarnings("unchecked")
	public List<Movie> findAll() {
	    return em.createNamedQuery("Movie.findAll").getResultList();
	}

    }

    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	FirstService firstService;

	@EJB
	SecondService secondService;

	@Resource
	ManagedExecutorService executor;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    testUpdade();
	}

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
	    System.out.println("Movies After: " + secondService.findAll());
	}

	// --------------------------------------------------------------------------------------------------------------------------------
	void testUpdade() {
	    resetCountDownLatches();

	    // testLockingOptimisticUpdateAndRead

	    System.out.println("Movies before: " + secondService.findAll());

	    executor.execute(new Runnable() {
		@Override
		public void run() {
		    firstService.updateMovie(3, "INCEPTION UR");
		}
	    });

	    // This is necessary to guarantee that de firstService execute first.
	    // try {
	    // Thread.sleep(1000); //
	    // } catch (InterruptedException e) {
	    // e.printStackTrace();
	    // }

	    executor.execute(new Runnable() {
		@Override
		public void run() {
		    secondService.updateMovie(3, "New Movie"); // secondService end the operation first, the firstService update won't rollback
		    updateCountDownLatch.countDown();
		}
	    });
	}
    }

    // ==========================================================================================================================================================
    public static CountDownLatch updateCountDownLatch = new CountDownLatch(1);

    public static CountDownLatch readCountDownLatch = new CountDownLatch(1);

    public static CountDownLatch deleteCountDownLatch = new CountDownLatch(1);

    // ==========================================================================================================================================================
    public static void resetCountDownLatches() {
	updateCountDownLatch = new CountDownLatch(1);
	readCountDownLatch = new CountDownLatch(1);
	deleteCountDownLatch = new CountDownLatch(1);
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
	    war.addMetaInfFiles(EmbeddedResource.add("persistence.xml", "src/main/resources/chapter13_javaPersistence/part13_transactionLocking/persistence.xml"));
	    war.addMetaInfFiles(EmbeddedResource.add("load.sql", "src/main/resources/chapter13_javaPersistence/part13_transactionLocking/load.sql"));
	    war.addMetaInfFiles(EmbeddedResource.add("create.sql", "src/main/resources/chapter13_javaPersistence/part13_transactionLocking/create.sql"));

	    war.addClasses(ServletPrincipal.class, SecondService.class, FirstService.class, Movie.class);

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

	    final HttpResponse responsePost = httpClient.execute(new HttpPost("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));
	    System.out.println(responsePost);

	    Thread.sleep(15000);

	    final HttpResponse responseGet = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));
	    System.out.println(responseGet);

	    Thread.sleep(30000); // 5 seconds only for the server finishs its jobs...

	} catch (final Exception ex) {
	    System.out.println(ex);
	}

	downVariables();
    }
}
