package br.com.fernando.enthuware.createJavaWebApplicationsUsingServlets;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;

public class Question10 {

    // Given an HttpServletRequest req and an EJBSessionContext ctx.
    // Which is a valid way to retrieve the Principal invoking either behavior?
    //
    // A - req.getCallerPrincipal() and ctx.getCallerPrincipal()
    //
    // B - req.getUserPrincipal() and ctx.getUserPrincipal()
    //
    // C - req.getCallerPrincipal() and ctx.getUserPrincipal()
    //
    // D - req.getUserPrincipal() and ctx.getCallerPrincipal()
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
    // The Correct answers D
    //
    // HttpServletRequest has :
    public String getUserName(final HttpServletRequest httpServletRequest) {

	final Principal userPrincipal = httpServletRequest.getUserPrincipal();

	return userPrincipal != null ? userPrincipal.getName() : null;
    }
    // Returns: a java.security.Principal object containing the name of the current authenticated user.
    // If the user has not been authenticated, the method returns null.
    //
    //
    //
    //
    // EJBContext has:
    // Obtain the java.security.Principal that identifies the caller.
    // Returns: The Principal object that identifies the caller. This method never returns null.

    @Resource
    private SessionContext sctx;

    public String getUserPrincipal() {

	final Principal userPrincipal = sctx.getCallerPrincipal(); // (EJBContext)

	return userPrincipal != null ? userPrincipal.getName() : null;
    }
}
