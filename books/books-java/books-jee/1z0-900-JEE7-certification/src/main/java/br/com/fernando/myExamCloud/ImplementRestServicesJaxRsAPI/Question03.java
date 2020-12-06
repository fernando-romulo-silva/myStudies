package br.com.fernando.myExamCloud.ImplementRestServicesJaxRsAPI;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class Question03 {

    // Which of the following EJB's can use JAX-RS @Path annotation?
    // [Choose Two ]
    //
    //
    // Choice A
    // Stateless session bean
    //
    // Choice B
    // Stateful session bean
    //
    // Choice C
    // Singleton session bean
    //
    // Choice D
    // Message-driven Bean
    //
    //
    //
    //
    //
    //
    //
    //
    // Choice A and C are correct answers.
    //
    // JAX-RS works with Enterprise JavaBeans technology (enterprise beans) and Contexts and Dependency Injection for the Java EE Platform (CDI).
    // The @Path annotation can be used with stateless session and singleton session beans.
    
    @Path("helloworld")
    @Stateless
    public class HelloWorld01 {

	@Context
	private UriInfo context;

	public HelloWorld01() {
	}

	@GET
	@Produces("text/html")
	public String getHtml() {
	    return "<html lang=\"en\"><body><h1>Hello, World!!</h1></body></html>";
	}
    }
    
    @Path("helloworld")
    @Singleton
    public class HelloWorld02 {

	@Context
	private UriInfo context;

	public HelloWorld02() {
	}

	@GET
	@Produces("text/html")
	public String getHtml() {
	    return "<html lang=\"en\"><body><h1>Hello, World!!</h1></body></html>";
	}
    }
}
