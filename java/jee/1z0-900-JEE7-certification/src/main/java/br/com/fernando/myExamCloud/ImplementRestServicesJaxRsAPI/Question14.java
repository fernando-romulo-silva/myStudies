package br.com.fernando.myExamCloud.ImplementRestServicesJaxRsAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

public class Question14 {

    public class Feed {

    }

    // @Path("feed")
    public interface FeedService {

	@GET
	@Produces("application/atom+xml")
	Feed getFeed();
    }

    @Path("feed")
    public class MyFeedService implements FeedService {

	@Produces("application/atom+xml")
	public Feed getFeed() {
	    return null;
	}
    }

    // What are the annotations MyFeedService inherits from FeedService?
    //
    // Choice A
    // Both @GET and @Produces
    //
    // Choice B
    // Only @GET
    //
    // Choice C
    // Only @Produces
    //
    // Choice D
    // None of the above
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
    // Choice D is correct.
    //
    // JAX-RS annotations MAY be used on the methods of a super-class or an implemented interface.
    //
    // Such annotations are inherited by a corresponding sub-class or implementation class method provided that method does not have any of its own JAX-RS annotations.
    //
    // Annotations on a super-class take precedence over those on an implemented interface.
    //
    // If a subclass or implementation method has any JAX-RS annotations then all of the annotations on the super class or interface method are ignored.
    //
    // The rule is "..subclass or implementation method has any JAX-RS annotations then all of the annotations on the super class or interface method are ignored.." 
    
    public class MyFeedGenericService implements FeedService {
        
	// in this case, inherits from FeedService
	public Feed getFeed() {
	    return null;
	}
    }
    
}
