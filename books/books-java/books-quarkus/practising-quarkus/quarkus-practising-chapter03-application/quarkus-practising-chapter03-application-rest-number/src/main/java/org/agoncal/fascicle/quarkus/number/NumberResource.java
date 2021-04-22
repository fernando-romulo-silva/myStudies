package org.agoncal.fascicle.quarkus.number;

import com.github.javafaker.Faker;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

// tag::adocSnippet[]
@Path("/api/numbers/book")
// tag::adocOpenAPI[]
@Tag(name = "Number Endpoint")
// end::adocOpenAPI[]
public class NumberResource {

  // tag::adocLogger[]
  private static final Logger LOGGER = Logger.getLogger(NumberResource.class);

  // end::adocLogger[]
  // tag::adocConfigProperty[]
  @ConfigProperty(name = "number.separator", defaultValue = "false")
  boolean separator;

  // end::adocConfigProperty[]
  // tag::adocFault[]
  @ConfigProperty(name = "seconds.sleep", defaultValue = "0")
  int secondsToSleep = 0;

  // end::adocFault[]
  // tag::adocOpenAPI[]
  @Operation(summary = "Generates book numbers", description = "These book numbers have several formats: ISBN, ASIN and EAN")
  @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BookNumbers.class)))
  // end::adocOpenAPI[]
  // tag::adocMetrics[]
  @Counted(name = "countGenerateBookNumber", description = "Counts how many times the generateBookNumbers method has been invoked")
  @Timed(name = "timeGenerateBookNumber", description = "Times how long it takes to invoke the generateBookNumbers method", unit = MetricUnits.MILLISECONDS)
  // end::adocMetrics[]
  // tag::adocGenerateBookNumbers[]
  // tag::adocTimeout[]
  @Timeout(250)
  // end::adocTimeout[]
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response generateBookNumbers() throws InterruptedException {
    // tag::adocFault[]
    LOGGER.info("Waiting for " + secondsToSleep + " seconds");
    TimeUnit.SECONDS.sleep(secondsToSleep);
    // end::adocFault[]
    LOGGER.info("Generating book numbers");
    Faker faker = new Faker();
    BookNumbers bookNumbers = new BookNumbers();
    bookNumbers.setIsbn10(faker.code().isbn10(separator));
    bookNumbers.setIsbn13(faker.code().isbn13(separator));
    bookNumbers.setAsin(faker.code().asin());
    bookNumbers.setEan8(faker.code().ean8());
    bookNumbers.setEan13(faker.code().ean13());
    bookNumbers.setGenerationDate(Instant.now());
    return Response.ok(bookNumbers).build();
  }

  // end::adocGenerateBookNumbers[]
  // tag::adocPing[]
  @GET
  @Path("/ping")
  @Produces(MediaType.TEXT_PLAIN)
  public String ping() {
    return "ping";
  }
  // end::adocPing[]
}
// end::adocSnippet[]
