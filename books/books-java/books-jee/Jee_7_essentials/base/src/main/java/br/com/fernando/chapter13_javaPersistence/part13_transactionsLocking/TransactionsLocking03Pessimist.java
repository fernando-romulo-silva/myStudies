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
import static javax.persistence.LockModeType.PESSIMISTIC_FORCE_INCREMENT;
import static javax.persistence.LockModeType.PESSIMISTIC_READ;
import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

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
import javax.persistence.Version;
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

public class TransactionsLocking03Pessimist {

    // Pessimistic locking gives an exclusive lock on the entity until the application is finished processing it.
    //
    // Pessimistic locking ensures that multiple transactions cannot update the same entity at the same time, which can simplify application code,
    // but it limits concurrent access to the data.
    //
    // When an entity instance is locked via pessimistic locking, the persistence provider must lock the database row(s) that correspond
    // to the non-collection-valued persistent state of that instance.
    //
    // Transactional isolation is usually implemented by locking whatever is accessed in a transaction.
    // There are two different approaches to transactional locking: Pessimistic locking and optimistic locking.
    //
    // The disadvantage of pessimistic locking is that a resource is locked from the time it is first accessed in a transaction until the transaction is finished,
    // making it inaccessible to other transactions during that time.
    //
    // If most transactions simply look at the resource and never change it, an exclusive lock may be overkill as it may cause lock contention, and optimistic
    // locking may be a better approach. With pessimistic locking, locks are applied in a fail-safe way.
    //
    // The main supported pessimistic lock modes are:
    //
    // PESSIMISTIC_READ - which represents a shared lock. This lock request fails if another user (which is represented by another EntityManager instance)
    // currently holds a PESSIMISTIC_WRITE lock on that database object.
    //
    // PESSIMISTIC_WRITE - which represents an exclusive lock. This lock request fails if another user currently holds either a PESSIMISTIC_WRITE lock or a
    // PESSIMISTIC_READ lock on that database object.
    //
    //
    @Entity
    @Table(name = "MOVIE") //
    @NamedQueries({ //
	    @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM TransactionsLocking03Pessimist$Movie m"), //
	    @NamedQuery(name = "Movie.findAllOptimistic", query = "SELECT m FROM TransactionsLocking03Pessimist$Movie m ", lockMode = PESSIMISTIC_READ), //
	    @NamedQuery(name = "Movie.findAllPessimistic", query = "SELECT m FROM TransactionsLocking03Pessimist$Movie m ", lockMode = PESSIMISTIC_FORCE_INCREMENT), //
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

	// The @Version attribute can be specified on an entity’s field of type int, Integer, short, Short, long, Long, or java.sql.Timestamp.
	// An entity is automatically enabled for optimistic locking if it has a property or field mapped with a Version mapping.
	@Version
	private Integer version;

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("Movie [id=").append(id) //
		    .append(", name=").append(name) //
		    .append(", version=").append(version) //
		    .append("]");
	    return builder.toString();
	}
    }

    // ==================================================================================================================================================================
    @Stateless
    public static class FirstService {

	@PersistenceContext(name = "myPU")
	EntityManager em;

	public Movie findMovie(Integer id) {
	    System.out.println("# Begin FirstService.findMovie");

	    Movie result = em.find(Movie.class, id);

	    System.out.println("# Done FirstService.findMovie");
	    return result;
	}

	@TransactionAttribute(REQUIRED)
	public void deleteMovie(Integer id) {
	    System.out.println("@ Begin FirstService.deleteMovie");

	    em.remove(findMovie(id));

	    em.flush();

	    System.out.println("@ Done FirstService.deleteMovie");
	}

	@TransactionAttribute(REQUIRED)
	public void deleteMovieLock(Integer id) {
	    System.out.println("@ Begin FirstService.deleteMovieLock");

	    Movie movie = findMovie(id);

	    em.lock(movie, PESSIMISTIC_WRITE);

	    em.remove(movie);

	    em.flush();

	    try {

		deleteCountDownLatch.await(10, SECONDS);

	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	    System.out.println("@ Done FirstService.deleteMovieLock");
	}

	@TransactionAttribute(REQUIRED)
	public void updateMovieLock(Integer id, String name) {

	    System.out.println("& Begin FirstService.updateMovieLock");

	    Movie movie = findMovie(id);
	    em.lock(movie, PESSIMISTIC_WRITE);

	    movie.name = name;

	    em.merge(movie);
	    em.flush();

	    try {

		updateCountDownLatch.await(10, SECONDS); // wait the countdown

	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	    System.out.println("& Done FirstService.updateMovieLock");
	}

    }

    @Stateless
    public static class SecondService {

	@PersistenceContext(name = "myPU")
	EntityManager em;

	public Movie findMovie(Integer id) {
	    System.out.println("# Begin SecondService.findMovie");

	    Movie result = em.find(Movie.class, id);

	    System.out.println("# Done SecondService.findMovie");
	    return result;
	}

	@TransactionAttribute(REQUIRED)
	public Movie findMovieLock(Integer id) {
	    System.out.println("# Begin SecondService.findMovieLock");

	    Movie result = em.find(Movie.class, id, PESSIMISTIC_WRITE);

	    try {
		Thread.sleep(2000); //
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	    System.out.println("# Done SecondService.findMovieLock");
	    return result;
	}

	@TransactionAttribute(REQUIRED)
	public void updateMovie(Integer id, String name) {
	    System.out.println("& Begin SecondService.updateMovie");

	    Movie movie = findMovie(id);

	    movie.name = name;

	    em.merge(movie);
	    em.flush();

	    System.out.println("& Done SecondService.updateMovie");
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
	private void testUpdade() {
	    resetCountDownLatches();

	    System.out.println("Test Update: Movies before: " + secondService.findAll());

	    // if firstService begins the operation first, the secondService update will rollback
	    executor.execute(new Runnable() {
		@Override
		public void run() {
		    try {
			firstService.updateMovieLock(3, "INCEPTION UR");
		    } catch (Exception e) { // Should throw an javax.persistence.OptimisticLockException? The Exception is wrapped around an javax.ejb.EJBException
			System.out.println("OptimisticLockException in firstService.update " + e.getClass());
		    }
		}
	    });

	    // This is necessary to guarantee that de firstService execute first.
	    try {
		Thread.sleep(1000); //
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	    executor.execute(new Runnable() {
		@Override
		public void run() {

		    try {
			System.out.println(secondService.findMovieLock(3));
		    } catch (Exception e) { // Should throw an javax.persistence.PessimisticLockException? The Exception is wrapped around an javax.ejb.EJBException
			System.out.println("PessimisticLockException in secondService.findMovieLock " + e.getClass());
		    }
		}
	    });

	    // if secondService begins the operation first, the firstService update will wait for end the
	    executor.execute(new Runnable() {
		@Override
		public void run() {

		    try {
			secondService.updateMovie(3, "New Movie");
		    } catch (Exception e) { // Should throw an javax.persistence.PessimisticLockException? The Exception is wrapped around an javax.ejb.EJBException
			System.out.println("PessimisticLockException in secondService.update " + e.getClass());
		    }

		    updateCountDownLatch.countDown(); // send a message to firstService finalize your job
		}
	    });
	}
    }

    // Test Update: Movies before: [Movie [id=1, name=The Matrix, version=0], Movie [id=2, name=The Lord of The Rings, version=0], Movie [id=3, name=Inception, version=0], Movie [id=4, name=The Shining, version=0]]
    // & Begin FirstService.updateMovieLock
    //
    // # Begin FirstService.findMovie
    // # Done FirstService.findMovie
    //
    // # Begin SecondService.findMovieLock
    // & Begin SecondService.updateMovie
    //
    // # Begin SecondService.findMovie
    // # Done SecondService.findMovie
    //
    // & Done FirstService.updateMovieLock (it's blocked until here!)
    // # Done SecondService.findMovieLock
    //
    // Movie [id=3, name=INCEPTION UR, version=1]
    //
    // PessimisticLockException int secondService.update class javax.ejb.EJBException
    // Movies After: [Movie [id=1, name=The Matrix, version=0], Movie [id=2, name=The Lord of The Rings, version=0], Movie [id=3, name=INCEPTION UR, version=1], Movie [id=4, name=The Shining, version=0]]

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
