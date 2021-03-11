package br.com.fernando.myExamCloud.ImplementRestServicesJaxRsAPI;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class Question10 {

    public class Order {

    }

    public class OrderManager {

	public Order findOrder(String id) {
	    return new Order();
	}

    }

    // Given the code fragment:

    @Path("Orders")
    public class OrderResource {

	@Inject
	private OrderManager orderManager;

	@GET
	@Path("{id:[A-Z][0-9]+}")
	public Order getOrder(@PathParam("id") String id) {
	    return orderManager.findOrder(id);
	}
    }

    // Which URL triggers the invocation of the getOrder() method?
    //
    // Choice A
    // <base url>/Orders/1234
    //
    // Choice B
    // <base url>/Orders/A1234
    //
    // Choice C
    // <base url>/Orders/id:a1234
    //
    // Choice D
    // <base url>/Orders/id/a1234
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
    // Choice B is correct.
    //
    // @Path support complex URI matching with regular expression:
    // id:[A-Z] [0-9]+ implies that id must match the regular expression [A-Z] [0-9]+.
    // Any string that starts with a capital letter from A to Z and then numbers, matches this regular expression.
    //
    // Choice A is incorrect, URI Pattern "<base url>/Orders/1234" , failed, don’t match.
    // Choice C is incorrect, URI Pattern "<base url>/Orders/id:a1234" , failed, don’t match.
    // Choice D is incorrect, URI Pattern "<base url>/Orders/id/a1234" , failed, don’t match.

}
