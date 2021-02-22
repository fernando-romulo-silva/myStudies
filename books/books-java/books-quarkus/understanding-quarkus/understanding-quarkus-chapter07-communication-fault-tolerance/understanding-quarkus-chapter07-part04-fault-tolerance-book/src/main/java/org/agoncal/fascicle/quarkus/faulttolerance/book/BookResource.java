package org.agoncal.fascicle.quarkus.faulttolerance.book;

import com.github.javafaker.Faker;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Instant;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BookResource {

    private static final Logger LOGGER = Logger.getLogger(BookResource.class);

    private Faker faker = new Faker();

    @RestClient
    NumberProxy numberProxy;

    @GET
    @Path("/numbers")
    @Fallback(fallbackMethod = "fallbackGenerateBookNumbers")
    // Different class implementing FallbackHandler
    // @Fallback(CallNumberBackupService.class)    
    public JsonObject generateBookNumbers() {

	LOGGER.info("Generating book numbers");

	IsbnNumber isbnNumber = numberProxy.generateIsbn(true);
	JsonObject issnNumber = numberProxy.generateIssn();

	return Json.createObjectBuilder() //
		.add("isbn13", isbnNumber.isbn13) //
		.add("gs1", isbnNumber.gs1) //
		.add("isbn10", issnNumber.getJsonString("isbn10").getString()) //
		.build();
    }

    protected JsonObject fallbackGenerateBookNumbers() {
	LOGGER.warn("Falling back on generating book numbers");

	return Json.createObjectBuilder() //
		.add("isbn13", "dummy isbn") //
		.add("gs1", "dummy gs1") //
		.add("isbn10", "dummy issn") //
		.build();
    }

    @POST
    @Timeout(250)
    @Fallback(fallbackMethod = "fallbackCreateBook")
    public Book createBook() {
	LOGGER.info("Creating book");

	// Invoking microservice
	JsonObject issnNumber = numberProxy.generateIssn();

	Book book = new Book();
	book.title = faker.book().title();
	book.issn = issnNumber.getString("isbn10");
	book.generatedAt = Instant.now();

	return book;
    }

    protected Book fallbackCreateBook() {
	LOGGER.warn("Falling back on creating a book");

	Book book = new Book();
	book.title = "dummy title";
	book.issn = "dummy issn";
	book.generatedAt = Instant.now();
	return book;
    }

    @Path("/legacy")
    @Timeout(250)
    @POST
    @Fallback(fallbackMethod = "fallbackCreateLegacyBook")
    // if, within the last four invocations (requestVolumeThreshold) , 50% failed (failureRatio), 
    // then the circuit transits to an open state. The circuit will stay open for 2,000 ms (delay). 
    // After 2 consecutive successful invocations (successThreshold), the circuit will be back to close again.
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.5, delay = 2000, successThreshold = 2)
    public Book createLegacyBook() {
	LOGGER.info("Creating a legacy book");

	// Invoking microservice
	JsonObject issnNumber = numberProxy.generateIssn();

	Book book = new Book();
	book.title = faker.book().title();
	book.issn = issnNumber.getString("isbn10");
	book.generatedAt = Instant.now();

	return book;
    }

    protected Book fallbackCreateLegacyBook() {
	LOGGER.warn("Falling back on creating a legacy book");

	Book book = new Book();
	book.title = "dummy legacy title";
	book.issn = "dummy legacy issn";
	book.generatedAt = Instant.now();
	return book;
    }
}
