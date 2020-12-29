package br.com.fernando.chapter18_security;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

public class Test01 {

    // Specifying Authorized Users by Declaring Security Roles
    //
    //
    // Method permissions can be specified on the class, the business methods of the class, or both.
    // Method permissions can be specified on a method of the bean class to override the method permissions value specified on the entire bean class.
    // The following annotations are used to specify method permissions.
    //
    // @DeclareRoles: Specifies all the roles that the application will use, including roles not specifically named in a @RolesAllowed annotation.
    @DeclareRoles("BusinessAdmin")
    public class Calculator1 {
	// ...
    }

    // The set of security roles the application uses is the total of the security roles defined in the @DeclareRoles and @RolesAllowed annotations.

    @DeclareRoles({ "Administrator", "Manager", "Employee" })
    public class Calculator2 {

	@RolesAllowed("Administrator")
	public void setNewRate(int rate) {
	    // ...
	}

	// @PermitAll Specifies that all security roles are permitted to execute the specified method or methods.
	// The user is not checked against a database to ensure that he or she is authorized to access this application.
	@PermitAll
	public long convertCurrency(long amount) {
	    // ...

	    return 0l;
	}

	// @DenyAll Specifies that no security roles are permitted to execute the specified method or methods.
	// This means that these methods are excluded from execution in the Java EE container.
	@DenyAll
	public long convertCurrencyNew(long amount) {
	    // ...
	    return 0l;
	}
    }
}
