package br.com.fernando.myExamCloud.ImplementRestServicesJaxRsAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class Question04 {

    // A developer has written the following Resource classes.
    //
    public class License {

    }

    public class Order {

    }

    @Path("license")
    public class LicenseResource {

	@GET
	@Path("process")
	public License getLicense() {
	    // ...
	    return null;
	}

	@Path("{id}")
	public OrderResource findOrder(@PathParam("id") String id) {
	    return new OrderResource(id);
	}
    }

    public class OrderResource {
	public OrderResource(String id) {
	    // ...
	}

	@GET
	public Order getOrder() {
	    // ...
	    return null;
	}
    }

    // Which method will handle GET request for the license/process URI?
    //
    // Choice A
    // getLicense of LicenseResource
    //
    // Choice B
    // getOrder of OrderResource
    //
    // Choice C
    // findOrder of LicenseResource
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
    // Choice A is correct.
    //
    // Methods of a resource class that are annotated with @Path are either sub-resource methods or sub-resource locators. 
    // Sub-resource methods handle a HTTP request directly whilst sub-resource locators return an object that will handle a HTTP request. 
    // The presence or absence of a request method designator (e.g. @GET) differentiates between the two:
    //
    // Present - Such methods, known as sub-resource methods, are treated like a normal resource method except the method is only invoked 
    // for request URIs that match a URI template created concatenating the URI template of the resource class with the URI template of the method2.
    // In our case license/process for getLicense method.
    //
    // Absent - Such methods, known as sub-resource locators, are used to dynamically resolve the object that will handle the request. 
    // Any returned object is treated as a resource class instance and used to either handle the request or to further resolve the object that will handle the request. 
    // An implementation MUST dynamically determine the class of object returned rather than relying on the static sub-resource locator return type since the returned instance may be a subclass of the declared type with potentially different annotations. Sub-resource locators may have all the same parameters as a normal resource method except that they MUST NOT have an entity parameter.
    // In our case findOrder uses parameter. The URI will be license/xxxxx.

}
