package br.com.fernando.enthuware.secureJavaEE7Applications;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Question01 {

    // You want to allow one set of roles to POST to a resource and another set of roles to GET it.
    // Which configuration options should you use? You have to select two options.
    //
    // A
    // Two separate @HttpMethodConstraints annotations and sets of roles
    //
    // B
    // A single @HttpMethodContstraint annotation and a map of method to roles.
    //
    // C
    // Two <web-resource-collection> with different <http-method> in the deployment descriptor
    //
    // D
    // A single <web-resource-collection> with two <auth-constraint> with different <http-method> in the deployment descriptor
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
    // The correct answer is A and C
    //
    // A is correct because of:
    @ServletSecurity(httpMethodConstraints = { //
	    @HttpMethodConstraint(value = "GET", rolesAllowed = "R1"), //
	    @HttpMethodConstraint(value = "POST", rolesAllowed = "R2") //
    })
    @WebServlet(name = "MyFooServlet", urlPatterns = { "/thatFoo" })
    public class FooServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
    }
    // C is correct because of
    /**
     * <pre>
     *      <security-constraint>
     *        
     *        <web-resource-collection>    
     *          <web-resource-name>wholesale</web-resource-name>    
     *          <url-pattern>/acme/wholesale/*</url-pattern>    
     *          <http-method>POST</http-method>    
     *        </web-resource-collection>    
     *        
     *        <auth-constraint>    
     *          <role-name>SALES</role-name>    
     *        </auth-constraint>    
     *      </security-constraint>    
     *          
     *      <security-constraint>    
     *        
     *        <web-resource-collection>    
     *          <web-resource-name>wholesale</web-resource-name>    
     *          <url-pattern>/acme/wholesale/*</url-pattern>    
     *          <http-method>GET</http-method>    
     *        </web-resource-collection>    
     *        
     *        <auth-constraint>    
     *          <role-name>MANAGER</role-name>    
     *        </auth-constraint>    
     *      </security-constraint>
     * </pre>
     */

}
