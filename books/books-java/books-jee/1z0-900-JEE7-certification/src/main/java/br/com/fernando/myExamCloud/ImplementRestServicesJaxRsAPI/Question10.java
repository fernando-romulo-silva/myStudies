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
	    // TODO Auto-generated method stub
	    return null;
	}

    }

    // Given the code fragment:

    @Path("Orders")
    public class OrderResource {

	@Inject
	private OrderManager orderManager;

	@GET
	@Path("{id:[A-Z] [0-9]+}")
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
    // <base url>/ Orders /a1234
    //
    // Choice C
    // <base url>/ Orders /id:a1234
    //
    // Choice D
    // <base url>/ Orders /id/a1234
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
    // @Path support complex URI matching with regular expression, via following expression : {" variable-name [ ":" regular-expression ] "} .
    //
    // Choice B is incorrect, URI Pattern "<base url>/Orders/a1234" , failed, don’t match.
    // Choice C is incorrect, URI Pattern "<base url>/Orders/id:a1234" , failed, don’t match.
    // Choice D is incorrect, URI Pattern "<base url>/Orders/id/a1234" , failed, don’t match.

}
