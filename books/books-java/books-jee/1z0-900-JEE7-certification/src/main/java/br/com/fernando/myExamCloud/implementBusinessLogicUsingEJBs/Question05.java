package br.com.fernando.myExamCloud.implementBusinessLogicUsingEJBs;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

public class Question05 {

    // A session bean TestManager uses container-managed transactions (it not guarantee that use transaction),
    // The container throws a javax.transaction.TransactionRolledBackException (Rollback) when the associateTest method runs.
    //
    // Which transaction attribute can the associateTest method have for this to occur?
    //
    // Choice A
    // NEVER
    //
    // Choice B
    // MANDATORY
    //
    // Choice C
    // REQUIRES_NEW
    //
    // Choice D
    // NOT_SUPPORTED
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
    // Choice B is correct.
    //
    //
    // public class TransactionRolledbackException extends RemoteException, Hmmm it's a REMOTE CLIENT
    //
    // This exception indicates that the transaction associated with processing of the request has been rolled back (rollback), or marked to roll back.
    // Thus the requested operation either could not be performed or was not performed because further computation on behalf of the transaction would be fruitless
    //
    // The transaction attribute Mandatory requires the container to invoke a bean's method in a client's transaction context.
    //
    // If the client is not associated with a transaction context when calling this method, the container throws javax.transaction.TransactionRequiredException
    //
    // if the client is a remote client or javax.ejb.TransactionRequiredLocalException if the client is a local client.
    //
    // If the calling client has a transaction context, the case is treated as Required by the container.
    //
    @Stateless
    @TransactionManagement(TransactionManagementType.CONTAINER) // DEFAULT
    public static class TestManager {

	// ?
	public void associateTest() {

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void method1() {
	    // Here the container will always start a new transaction
	    // and run the method inside its scope
	}

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void method2() {
	    // Here the container will allow only clients that already startd
	    // a transaction to call this method
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void method3() {
	    // Here the container will make sure that the method
	    // will run in no transaction scope and will suspend any
	    // transaction started by the client
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void method4() {
	    // Here the container will allow the method to be called
	    // by a client whether the client already has a transaction or not
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void method5() {
	    // Here the container will reject any invocation done by clients already
	    // participate in transactions
	}

    }

    @Stateless
    public static class TestManagerClient {

	@EJB
	private TestManager testManager;

	// ?
	@TransactionAttribute(TransactionAttributeType.SUPPORTS) // maybe it
	public void associateTestClient() {
	    testManager.method2();
	}
    }

}
