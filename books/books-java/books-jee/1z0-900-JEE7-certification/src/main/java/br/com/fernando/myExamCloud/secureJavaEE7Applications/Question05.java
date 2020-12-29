package br.com.fernando.myExamCloud.secureJavaEE7Applications;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Question05 {

    // Given an HttpServletRequest request and an EJB SessionContext ctx.
    // Which is a valid way to retrieve the Principal invoking either behavior?
    //
    //
    // Choice A
    // request.getCallerPrincipal() and ctx.getCallerPrincipal()
    //
    // Choice B
    // request.getUserPrincipal() and ctx.getUserPrincipal()
    //
    // Choice C
    // request.getCallerPrincipal() and ctx.getUserPrincipal()
    //
    // Choice D
    // request.getUserPrincipal() and ctx.getCallerPrincipal()
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
    // Explanation :
    // Choice D is correct.
    //
    // The HttpServletRequest method java.security.Principal getUserPrincipal() Returns a java.security.Principal object containing the name of the current authenticated
    // user. If the user has not been authenticated, the method returns null.
    //
    // The SessionContext java.security.Principal getCallerPrincipal() method obtain the java.security.Principal that identifies the caller.
    // Note that in web tier user directly interact with web page, so remember getUserPrincipal method for HttpServletRequest.
    // The EJB tiers are called by web components, so remember getCallerPrincipal method.
    @WebServlet("/servletOne")
    public static class ServletOne extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    Principal userPrincipal = request.getUserPrincipal();
	    System.out.println(userPrincipal);
	}
    }
    
    @Stateless
    public static class TestManagerClient {
	
	@Resource
	private SessionContext ctx;

	public void associateTestClient() {
	    Principal userPrincipal = ctx.getCallerPrincipal();
	    System.out.println(userPrincipal);
	}
    }
}
