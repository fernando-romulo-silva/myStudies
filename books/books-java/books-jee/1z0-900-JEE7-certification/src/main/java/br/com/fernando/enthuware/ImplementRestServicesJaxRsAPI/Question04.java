package br.com.fernando.enthuware.ImplementRestServicesJaxRsAPI;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.com.fernando.enthuware.ImplementRestServicesJaxRsAPI.Question03.EmployeeManager;

public class Question04 {

    // Given:
    @Path("Employees")
    public class CustomerService {
	
	@Inject 
	private EmployeeManager em;

	@GET
	@Path("{id:[A-Z] [0-9]+}")
	public Response getCustomerById(@PathParam("id") String id) {
	    return Response.status(200).entity("getUserById is called, id : " + id).build();
	}
    }

    // Which URL triggers the invocation of the getEmployee () method?
    //
    // Choice A
    //  <base url>/Employees/997122
    //
    // Choice B
    //  <base url>/Employees/G6666
    //
    // Choice C
    // <base url>/Employees/id:b777
    //
    // Choice D
    // <base url>/Employees/id/H6555
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
    // Choice B is correct.
    //
    // @Path support complex URI matching with regular expression:
    // id:[A-Z] [0-9]+ implies that id must match the regular expression [A-Z] [0-9]+.
    // Any string that starts with a capital letter from A to Z and then numbers, matches this regular expression.
    //
    //
    // Choice A is incorrect, URI Pattern "<base url>/Employees/997122" , failed, don’t match.
    // Choice C is incorrect, URI Pattern "<base url>/Employees/id:b777" , failed, don’t match.
    // Choice D is incorrect, URI Pattern "<base url>/Employees/id/H6555" , failed, don’t match.

}
