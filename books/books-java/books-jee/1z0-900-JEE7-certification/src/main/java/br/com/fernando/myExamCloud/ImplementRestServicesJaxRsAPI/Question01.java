package br.com.fernando.myExamCloud.ImplementRestServicesJaxRsAPI;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class Question01 {

    // Given
    @Path("/customer/{id}")
    public class Customer {
	public Customer(@PathParam("id") String id) {
	    // ...
	}
    }

    @Path("{lastname}")
    public final class CustomerDetails {
	// ...
    }

    // A developer wants to convert this resource class scope as application.
    // Which CDI managed bean code can achieve this?
    //
    // Choice A
    @ApplicationScoped
    @Path("/customer/{id}")
    public class CustomerA {

	public CustomerA(@PathParam("id") String id) {
	    // ...

	}
    }

    @ApplicationScoped
    @Path("{lastname}")
    public final class CustomerDetailsA { // final class, wrong
	// ...
    }

    //
    // -----------------------------------------------------------------------------------
    // Choice B
    @ApplicationScoped
    @Path("/customer/{id}")
    public class CustomerB {

	public CustomerB(@PathParam("id") String id) {
	    // ...
	}
    }

    @Path("{lastname}")
    public final class CustomerDetailsB {
	// ...
    }

    //
    // -----------------------------------------------------------------------------------
    // Choice C
    @Path("/customer/{id}")
    @ApplicationScoped
    public class CustomerC {

	public CustomerC() {
	    // ...
	}

	@Inject
	public CustomerC(@PathParam("id") String id) {
	    // ...
	}
    }

    @Path("{lastname}")
    @ApplicationScoped
    public class CustomerDetailsC {
	// ...
    }
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
    // Choice C is correct
    //
    // JAX-RS and CDI have slightly different component models.
    // By default, JAX-RS root resource classes are managed in the request scope, and no annotations are required for specifying the scope.
    //
    // CDI can't be final class
    //
    // CDI managed beans annotated with @RequestScoped or @ApplicationScoped can be converted to JAX-RS resource classes.

}
