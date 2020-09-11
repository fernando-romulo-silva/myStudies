package br.com.fernando.enthuware.implementBusinessLogicUsingEJBs;

import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Singleton;

public class Question02 {

    // Given:
    @Singleton
    public class MySessionBean {
	// line 1
	public void callMethod() {
	    // implementation logic
	}
	// Constructor and other business methods
    }

    // Which annotation do you use on line 1 to ensure that clients immediately time out when
    // attempting to concurrently invoke callMethod() while another client is already accessing the bean?
    //
    // You had to select 1 option(s)
    //
    // A
    // @AccessTimeout(value = 1, unit = TimeUnit.SECONDS)
    //
    // B
    // @AccessTimeout(null)
    //
    // C
    // @AccessTimeout(-1)
    //
    // D
    // @AccessTimeout(0)
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // The correct answer is D
    // The Bean Provider may optionally specify that concurrent client requests to a stateful session bean are prohibited.
    //
    // This is done using the AccessTimeout annotation or the access-timeout deployment descriptor element with a value of 0.
    //
    // In this case, if a client-invoked business method is in progress on an instance when another client-invoked call,
    // from the same or different client, arrives at the same stateful session bean istance, if the second client is a 
    // client of the bean’s business interface or no-interface view, the concurrent invocation must result in the second client 
    // receiving the javax.ejb.ConcurrentAccessException.
    // 
    // 
    // A is wrong because is not IMMEDIATELY, but compile
    @AccessTimeout(value = 1, unit = TimeUnit.SECONDS)
    public void callMethodA() {
	// implementation logic
    }
    //
    // B is wrong because not compile
    // @AccessTimeout(null)
    public void callMethodB() {
	// implementation logic
    }
    //
    // C is wrong because an AccessTimeout value of -1 indicates that a concurrent client request will block indefinitely until it can proceed
    @AccessTimeout(-1)
    public void callMethodC() {
	// implementation logic
    }
}
