package br.com.fernando.myExamCloud.ImplementRestServicesJaxRsAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class Question05 {

    // Given:
    @Path("/customers")
    public class CustomerService {

	@GET
	@Path("{id : \\d+}")
	public Response getCustomerById(@PathParam("id") String id) {
	    return Response.status(200).entity("getUserById is called, id : " + id).build();
	}
    }

    // Which URL triggers the invocation of the getCustomerById () method?
    //
    // Choice A
    // <base url>/customers/1234
    //
    // Choice B
    // <base url>/customers/a1234
    //
    // Choice C
    // <base url>/customers/id:a1234
    //
    // Choice D
    // <base url>/customers/id/a1234
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
    // Choice A is correct.
    //
    // @Path support complex URI matching with regular expression, via following expression : {" variable-name [ ":" regular-expression ] "} .
    //
    // Choice B is incorrect, URI Pattern "<base url>/Orders/a1234" , failed, don’t match.
    // Choice C is incorrect, URI Pattern "<base url>/Orders/id:a1234" , failed, don’t match.
    // Choice D is incorrect, URI Pattern "<base url>/Orders/id/a1234" , failed, don’t match.

}
