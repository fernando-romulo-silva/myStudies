package br.com.fernando.myExamCloud.ImplementRestServicesJaxRsAPI;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class Question09 {

    public class Employee {
	String name;

	Employee(String name) {
	    super();
	    this.name = name;
	}
    }

    public class EmployeeManager {

	List<Employee> findAllEmployees() {
	    return Arrays.asList(new Employee("A"), new Employee("B"));
	}
    }

    // Given:
    //
    @Path("Employees")
    @Produces({ MediaType.APPLICATION_JSON })
    public class EmployeesResource {

	@Inject
	private EmployeeManager em;

	@GET
	public List<Employee> findA11() {
	    return em.findAllEmployees();
	}
    }
    // What will be the response to a HEAD request?
    //
    // Choice A
    // The findAll() method will be called and the employee list will be returned.
    //
    // Choice B
    // The findAll() method will be called and no employees will be returned.
    //
    // Choice C
    // No method will be invoked and no error will be returned.
    //
    // Choice D
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
    // Choice B is correct
    //
    // In JAX-RS, an incoming HTTP HEAD request is handled by a target resource method successfully if:
    //
    // * The resource method is annotated with @HEAD.
    //
    // * The resource method annotated with @GET, any returned entity is discarded for HTTP HEAD request, but for HTTP GET request, it will function normally.
}
