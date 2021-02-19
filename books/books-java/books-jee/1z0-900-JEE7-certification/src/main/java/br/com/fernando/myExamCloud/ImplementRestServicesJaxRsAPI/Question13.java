package br.com.fernando.myExamCloud.ImplementRestServicesJaxRsAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class Question13 {

    // Which statement is true about JAX-RS resource implementation?
    // You had to select 1 option(s)
    //
    // A - The REST resource implementation class must extend the javax.ws.rs.core.Application class
    //
    // B - The REST resource class can be implemented as a stateful Enterprise JavaBean (EJB).
    //
    // C - The REST resource class can be implemented as a Plain Old Java Object (POJO).
    //
    // D - The REST resource implementation class must not be final.
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //    
    // Answer C is correct
    //
    // JAX-RS is a Java programming language API designed to make it easy to develop applications that use the REST architecture. 
    // Root resource classes are "plain old Java objects" (POJOs) that are either annotated with @Path or have at least one method annotated 
    // with @Path or a request method designator, such as @GET, @PUT, @POST, or @DELETE.
    //
    // Choice A is incorrect. The javax.ws.rs.core.Application class can be used to define the URL mapping for the application and it is 
    // not mandatory for JAX-RS root class. 
    // 
    // Choice B is incorrect. According to JAX-RS specifications, a resource can be a @Singleton or @Stateless EJB.
    //
    // Choice D is incorrect. The JAX-RS resource class can be final.
    //
    // Resource classes are POJOs that have at least one method annotated with @Path or a request method designator.

    /**
     * Root resource (exposed at "helloworld" path)
     */
    @Path("helloworld")
    public final class HelloWorld { // it's can be final
	
	@Context
	private UriInfo context;

	/** Creates a new instance of HelloWorld */
	public HelloWorld() {
	}

	/**
	 * Retrieves representation of an instance of helloWorld.HelloWorld
	 * 
	 * @return an instance of java.lang.String
	 */
	@GET
	@Produces("text/html")
	public String getHtml() {
	    return "<html lang=\"en\"><body><h1>Hello, World!!</h1></body></html>";
	}
    }
}
