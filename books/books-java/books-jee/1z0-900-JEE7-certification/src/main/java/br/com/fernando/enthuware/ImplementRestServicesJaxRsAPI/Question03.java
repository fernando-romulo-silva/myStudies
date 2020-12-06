package br.com.fernando.enthuware.ImplementRestServicesJaxRsAPI;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class Question03 {

    public class Employee {

    }

    @Named
    public class EmployeeManager {

	public List<Employee> findAllEmployees() {
	    // TODO Auto-generated method stub
	    return null;
	}

    }

    // Given:
    @Path("Employees")
    @Produces({ MediaType.APPLICATION_JSON })
    public class EmployeesResource {

	@Inject
	private EmployeeManager em;

	@GET
	public List<Employee> findAll() {
	    return em.findAllEmployees();
	}
    }

    // What will be the response to a HEAD request?
    // You had to select 1 option(s)
    //
    // A
    // The findAll() method will be called and the employee list will be returned.
    //
    // B
    // The findAll() method will be called and no employees will be returned.
    //
    // C
    // No method will be invoked and no error will be returned.
    //
    // D
    // No method will be invoked and a 404 error will be returned.
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
    // The correct answer is B
    // In JAX-RS, an incoming HTTP HEAD request is handled by a target resource method successfully if:
    // The resource method is annotated with @HEAD.
    // The resource method annotated with @GET: In this case, any returned entity is discarded for HTTP HEAD request.

}
