package br.com.fernando.chapter13_javaPersistence.part12_validatingEntities;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
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

public class ValidatingEntities {

    // Bean Validation allows you to specify validation metadata on JavaBeans.
    // For JPA, all managed classes (entities, managed superclasses, and embeddable classes) may be configured to include Bean Validation constraints.
    // These constraints are then enforced when the entity is persisted, updated, or removed in the database.
    //
    // By default, the validation of entity beans is automatically turned on.
    // You can change the default validation behavior by specifying the validation-mode element in persistence.xml.
    @Entity
    @Table(name = "STUDENT")
    public static class Student implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@NotNull
	@Id
	@Column(name = "ID")
	int id;

	@Size(max = 20, groups = { GroupName.class })
	@Column(name = "NAME")
	String name;

	@Size(min = 2, max = 5, groups = { GroupGrade.class }) //
	@Column(name = "GRADE")
	String grade;

	// Embeddable attributes are validated only if the Valid annotation has been specified on them.
	@Valid
	@ElementCollection
	@CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "ADDRESS_ID"))
	List<Address> addresses;

	// You can target a field in the Student entity to this validation group:
	@Transient
	@AssertTrue(groups = GroupGrade.class)
	boolean canBePersisted;

	@Transient
	@AssertTrue(groups = GroupName.class)
	boolean canBeUpdate;
    }

    @Embeddable
    public static class Address {

	@Size(max = 20)
	String street;

	@Size(max = 20)
	String city;

	@Size(max = 20)
	String zip;
    }

    // You can define a new validation group by declaring a new interface:
    public static interface GroupGrade {
    }

    public static interface GroupAddress {
    }

    public static interface GroupName {
    }

    // ==================================================================================================================================================================
    @Stateless
    public static class Service {

	@PersistenceContext(name = "defaultPU")
	EntityManager em;

	public void execute() {

	    final Student s01 = new Student();
	    s01.id = 1;
	    s01.name = "Mary Cortez";
	    s01.grade = "12";
	    s01.canBePersisted = true;
	    s01.canBeUpdate = false;

	    em.persist(s01); // no problems
	    em.flush();
	    
	    // -------------------------------------------------
	    s01.name = "This is a super big text to fail the validation bla bla blab bla bla"; // Update the name
	    try {

		em.merge(s01); // you can't !
		
		em.flush();

	    } catch (final ConstraintViolationException e) {
		System.out.println("Validation! " + e.getConstraintViolations());
	    }

	    s01.name = "Mary Cortez Silva"; // Update the name
	    s01.canBeUpdate = true;
	    em.merge(s01); // now you can !
	    em.flush();

	    final Student s03 = new Student();
	    s03.id = 1234;
	    s03.name = "Joe Smith";
	    s03.canBePersisted = true;
	    s03.canBeUpdate = false;
	    s03.grade = "1"; // fail here @Size(min = 2, max = 5, groups = { GroupGrade.class }) //

	    // With these constraints, attempting to add the following Student to the database will throw a ConstraintViolationException,
	    // as the grade field must be at least 2 characters long:
	    try {

		em.persist(s03);
		em.flush();

	    } catch (final ConstraintViolationException e) {
		System.out.println("Validation! " + e.getConstraintViolations());
	    }

	    // You can also achieve validation in the application by calling the Validator.validate method upon an instance of a managed class.
	    // The life-cycle event validation only occurs when a Bean Validation provider exists in the runtime.
	    // If a constraint is violated, the current transaction is marked for rollback.
	}

	@SuppressWarnings("unused")
	void temp() {

	    // You can also specify these values using the javax.persistence.validation.mode property
	    // if you create the entity manager factory using Persistence.createEntityManagerFactory:
	    Map<String, Object> props = new HashMap<>();
	    props.put("javax.persistence.validation.mode", "callback");
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySamplePU", props);
	}

    }

    // By default, all entities in a web application are in the Default validation group.
    // The Default group is targeted in pre-persist and pre-update events, and no groups are targeted in pre-remove events.
    // So the constraints are validated when an entity is persisted or updated, but not when it is deleted.

    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	Service service;

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
	    service.execute();
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
	    war.addMetaInfFiles(EmbeddedResource.add("persistence.xml", "src/main/resources/chapter13_javaPersistence/part12_validatingEntities/persistence.xml"));

	    war.addClasses(ServletPrincipal.class, Student.class, Address.class, GroupGrade.class, GroupAddress.class, GroupName.class, Service.class);

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
