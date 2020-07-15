package br.com.fernando.myExamCloud.implementBusinessLogicUsingEJBs;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.SessionContext;

public class Question01 {

    // An EJB developer writes a CMT stateless session bean with one local business interface.
    // All business methods are declared as REQUIRED.
    // The bean has an injected field sessionCtx of the type SessionContext.
    //
    @Resource
    private SessionContext sessionCtx;

    // Which two operations are allowed in a business method of the bean?
    //
    public void method() {
	// Choice A
	// sessionCtx.getEJBObject
	//
	// Choice B
	// sessionCtx.setRollbackOnly
	//
	// Choice C
	// sessionCtx.getMessageContext
	//
	// Choice D
	// sessionCtx.getBusinessObject
	//
	// Choice E
	// sessionCtx.getEJBLocalObject
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
	// Choice B and D are correct answers.
	//
	// Call to getEJBObject is not valid, since the bean is local.
	// IllegalStateException - Thrown if the instance invokes this method while the instance is in a state that does not allow the instance
	// to invoke this method, or if the instance does not have a remote interface.
	//
	// Call to setRollbackOnly is valid, since all methods are running in a transaction (REQUIRED)
	sessionCtx.setRollbackOnly();
	//
	// Call to getMessageContext is not valid, since the bean is not a web service endpoint interface.
	//
	// Call to getBusinessObject is valid.
	// The method obtain an object that can be used to invoke the current bean through a particular business interface view or its no-interface view.
	sessionCtx.getBusinessObject(Serializable.class);
    }

}
