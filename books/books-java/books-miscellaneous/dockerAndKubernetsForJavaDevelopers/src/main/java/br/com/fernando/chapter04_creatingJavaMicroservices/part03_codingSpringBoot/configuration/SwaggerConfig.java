package br.com.fernando.chapter04_creatingJavaMicroservices.part03_codingSpringBoot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// Your clients will need to know how to interact with your API. 
// Swagger is gaining a lot of ground with support from major vendors in the last couple of years. 
// Swagger's specification presents all the details of your service resources and operations in a JSON format. 
// The format of the specification is known as the OpenAPI specification (Swagger RESTful API documentation specification).
//
//  The SpringFox library, available on GitHub at http://springfox.github.io/springfox/ and in the Maven central, is a tool to automatically build JSON API documentation for APIs built with Spring

@Configuration
@EnableSwagger2
//  @Configuration means that the annotated class is defining a Spring configuration, @EnableSwagger2 turns off the Swagger support.
public class SwaggerConfig {

    @Bean
    public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2) //
		.select() // The select() method called on the Docket bean instance returns an ApiSelectorBuilder,
		.apis(RequestHandlerSelectors.any()) // , which provides the apis()
		.paths(PathSelectors.any()) // and paths() methods to filter the controllers and methods being documented using string predicates
		.build();

	// You could also use the regex parameter passed to paths() to provide an additional filter to generate documentation only for the path matching the regex expression.
	// That's it; it's the simplest form of generating a documentation for your API.
	// If you now run the service (we are going to do this in a short while), two endpoints will be available:
	//
	// * http://localhost:8080/v2/api-docs
	//
	// * http://localhost:8080/swagger-ui.html
	//
	// The Swagger UI is a collection of HTML, JavaScript, and CSS assets that dynamically generate beautiful documentation from a Swagger-compliant API.
	// It lists your service operations, and its request and response formats.
	//
	// Our documentation is not very descriptive. Of course, we have a listing of our exposed endpoints with their input and output description.
	// It would be nice if we could enhance the documentation with some more specific details.
	//
	// We CAN do it, there are Java annotations we can use in the service's code to enhance the generated documentation.
	// The annotations come from the Swagger-annotation package, which will be available if you use the springfox-swagger2 library in your project.
	// For example, consider the following code snippet on BookController.listBooks :

	/**
	 * <pre>
	 * 
	 * &#64;ApiOperation(value = "Retrieve a list of books.", responseContainer = "List")
	 * &#64;RequestMapping(value = "/books", method = RequestMethod.GET, produces = { "application/json" })
	 * public List<Book> listBooks() {
	 *     LOGGER.debug("Received request to list all books");
	 *     return bookService.getList();
	 * }
	 * </pre>
	 */
    }

}
