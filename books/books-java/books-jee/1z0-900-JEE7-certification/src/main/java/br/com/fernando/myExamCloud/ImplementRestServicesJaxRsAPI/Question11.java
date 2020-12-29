package br.com.fernando.myExamCloud.ImplementRestServicesJaxRsAPI;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

public class Question11 {

    // A resource method
    @Path("/customers/")
    @GET
    public Response getCustomers(@DefaultValue("2004") @QueryParam("minyear") int minyear, //
	    			 @DefaultValue("2011") @QueryParam("maxyear") int maxyear) {

	return null;
    }

    // Which URI can query for all customers who have registered between 1998 and 2004?
    //
    // Choice A
    // /customers?2004&1998
    //
    // Choice B
    // /customers?maxyear=2004&minyear=1998
    //
    // Choice C
    // /customers/?maxyear=2004&minyear=1998
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
    // The @QueryParam annotation is used to extract query parameters from the query component of the request URI.
    // The URI pattern get parameters are appended like any other http request.
    // First parameter must be appended with ? Symbol and & for subsequent parameters.

}
