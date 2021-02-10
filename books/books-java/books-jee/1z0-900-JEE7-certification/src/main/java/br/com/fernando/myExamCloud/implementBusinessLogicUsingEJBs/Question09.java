package br.com.fernando.myExamCloud.implementBusinessLogicUsingEJBs;

import javax.annotation.Resource;
import javax.ejb.ApplicationException;
import javax.ejb.EJBContext;

public class Question09 {

    // While executing a business method in a stateless session bean the container rolls back the method's transaction.
    // Which of the following ensure that container must rollback the transaction?
    // [ Choose two ]
    //
    // Choice A
    // Call to EJBContext.setRollbackOnly.
    //
    // Choice B
    // Call to EJBContext.getRollbackOnly.
    //
    // Choice C
    // The business method must throw an unchecked exception of a class type that is marked with the @ApplicationException annotation with the rollback element value true
    //
    // Choice D
    // The business method must throw a checked exception of a class type that is marked with the @ApplicationException annotation with the rollback element value false.
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
    // Choice A and C are correct answers.
    //
    // The EJBContext interface provides an instance with access to the container-provided runtime context of an enterprise Bean instance.
    // The setRollbackOnly method mark the current transaction for rollback. The transaction will become permanently marked for rollback.
    // A transaction marked for rollback can never commit. Only enterprise beans with container-managed transactions are allowed to use this method.
    //
    // @ApplicationException - specifies that an exception is an application exception and that it should be reported to the client application directly, or unwrapped.
    // This annotation can be applied to both checked and unchecked exceptions.
    //
    // attribute rollback - Specifies whether the EJB container should rollback the transaction, if the bean is currently being invoked inside of one,
    // if the exception is thrown.Valid values for this attribute are true and false.
    // Default value is false, or the transaction should not be rolled back.

    @ApplicationException(rollback = true)
    public class NotEnoughMoneyOnYourAccountException extends RuntimeException {

    }
    // If your program throws a RuntimeException, it will be sent to the client wrapped as a RemoteException, and your transaction will be rolled back.
    // These are less excepted than business exceptions, therefore we usually don't catch them at EJB side.

    public class OrderManagerCaller {

	@Resource
	EJBContext context;

	public void callAddOrder1() { // this method commit transaction

	    try {

		throw new NotEnoughMoneyOnYourAccountException();

	    } catch (NotEnoughMoneyOnYourAccountException e) {
		context.setRollbackOnly();
	    }
	}
    }
}
