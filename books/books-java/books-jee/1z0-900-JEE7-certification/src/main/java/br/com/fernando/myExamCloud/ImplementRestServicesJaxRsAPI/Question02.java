package br.com.fernando.myExamCloud.ImplementRestServicesJaxRsAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

public class Question02 {

    // Given.
    //
    @Path("/customers/{firstname}.{lastname}@{domain}.com")
    public class CustomerResource {

	@GET
	@Produces("text/xml")
	public String getLastName(/* ___________ */ String lastName) {
	    // ...
	    return "";
	}
    }

    // What code inserted on ___________ will extract domain?
    //
    // Choice A
    // @PathParam("domain")
    //
    // Choice B
    // @Param("domain")
    //
    // Choice C
    // @ResourceParam("domain")
    //
    // Choice D
    // @MethodParam("domain")
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
    // Explanation :
    // Choice A is correct.
    //
    // The @PathParam annotation lets you use variable URI path fragments when you call a method.
    // In this example, the @Path annotation defines the URI variables (or path parameters) {firstname}, {lastname}, and {domain}.
    // The @PathParam in the method parameter of the request method extracts the domain from the email address.
    // If your HTTP request is GET /customers/brian.christopher@epractizelabs.com, the value, “epractizelabs” is injected into {domain}.
    //

    @Path("/customers/{firstname}.{lastname}@{domain}.com")
    public class CustomerResource2 {

	@GET
	@Produces("text/xml")
	public String getLastName(@PathParam("domain") String lastName) {
	    // ...
	    return "";
	}
    }

}
