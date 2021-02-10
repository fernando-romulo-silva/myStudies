package br.com.fernando.myExamCloud.secureJavaEE7Applications;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

public class Question07 {

    // Which two statements about the EJBContext.isCallerInRole method are correct?
    // [ Choose two ]
    //
    //
    // Choice A
    // Message-driven beans must NOT call the isCallerInRole method.
    //
    // Choice B
    // The isCallerInRole method may be called in a session bean constructor.
    //
    // Choice C
    // The isCallerInRole method can be called in any business method of a stateless or a stateful session bean.
    //
    // Choice D
    // The isCallerInRole method can be called in the PostConstruct and PreDestroy lifecycle callback methods of a stateless session bean.
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
    // Choice A and C are correct answers.
    //
    // When an MDB services a JMS message there is no "caller," so there is no security context to be obtained from the caller.
    // JMS is asynchronous and doesn't propagate the sender's security context to the receiver--that wouldn't make sense, since senders and receivers
    // tend to operate in different environments.
    //
    // The security methods--getCallerPrincipal() and isCallerInRole()-- throw a RuntimeException if invoked on a MessageDrivenContext.
    // Session beans can use this method.

    @Stateless
    public class LicenseManagerBean {
	// Injects EJB context

	@Resource
	private SessionContext context;

	public void placeLicenseRequest() {
	    final boolean v = context.isCallerInRole("user1");
	    System.out.println(v);
	}
    }
}
