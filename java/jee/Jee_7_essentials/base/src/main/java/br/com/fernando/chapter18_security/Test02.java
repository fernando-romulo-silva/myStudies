package br.com.fernando.chapter18_security;

import java.security.Principal;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

public class Test02 {

    // The following code snippet demonstrates the use of the @DeclareRoles annotation with the isCallerInRole method.
    // In this example, the @DeclareRoles annotation declares a role that the enterprise bean PayrollBean uses to make
    // the security check by using isCallerInRole("payroll") to verify that the caller is authorized to change salary data:
    @DeclareRoles("payroll")
    @Stateless
    public class PayrollBean {

	@Resource
	private SessionContext ctx;

	public void updateEmployeeInfo(EmplInfo info) {


            // The salary field can be changed only by callers
            // who have the security role "payroll"
            Principal callerPrincipal = ctx.getCallerPrincipal();
            // getCallerPrincipal allows the enterprise bean methods to obtain the current caller principal’s name. 
            // The methods might, for example, use the name as a key to information in a database. 
            
            if (!ctx.isCallerInRole("payroll")) { // isCallerInRole allows the developer to code the security checks that cannot be easily defined using method permissions. 
                throw new SecurityException("Error!");
            }
            
            // obtain the caller principal's name
            String callerKey = callerPrincipal.getName();
            
            // ...
        }
	// ...
    }
    
    public static class EmplInfo {
	
    }

}
