package br.com.fernando.chapter12_javaTransaction.part02_containerManagedTransactions;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

public class ContainerManagedTransactions02 {

    // Just to Summarize Transaction Attributes
    //
    // https://www.wideskills.com/ejb/container-managed-transactions
    //
    // Transaction attributes control the scope and transaction behavior in the marked method.
    // The following attributes are provided by the JEE:
    //
    // Required
    //
    // If the called/client (i.e. another bean) already started a transaction, the method will participate in the same transaction scope,
    // if the called didn’t start a transaction before invoking the method, the container will start a new transaction and the method will be added
    // to the transaction scope.
    // Required attribute is the default attribute that is used if no other attribute was specified for the beans methods.
    //
    //
    // RequiresNew
    //
    // With this attribute the container always invokes the method in a new transaction, whether the client has already started a transaction or not.
    // In case the called already started a transaction, the container suspends the transaction, starts another one and adds the method to the new transaction scope.
    // Once the transaction does commit, the container resumes the suspended transaction again.
    //
    //
    // Mandatory
    //
    // With this attribute, the client is forced to start a transaction to be able to invoke the method.
    // In case the caller doesn’t participate in any transaction scope, the container prevents the method invocation and
    // throws a TransactionRequiredException Exception.
    //
    //
    // NotSupported
    //
    // This attribute can be used to improve the methods invocation performance as it removes the transaction management overhead from the method invocation.
    // With this attribute, the container suspends the transactions started by the client, if any, and invokes the method in no transaction scope.
    // Once the method invocation is done, the container resumes the client suspended transaction, if any.
    //
    //
    // Supports
    //
    // If the enterprise bean’s method is invoked while the client is running within transaction, then he method executes within the client’s transaction.
    // The container does not start a new transaction before running the method, if the client is not associated with a transaction,
    // This should be used with caution.
    //
    //
    // Never
    //
    // This attribute means that the method should never run inside a transaction and if the client already started one, the container prevents
    // the method invocation and throws RemoteException exception to the caller thread.

    @Stateless
    @LocalBean
    @TransactionManagement(TransactionManagementType.CONTAINER)
    public class ContainerManagedTransactionExample {

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
	
	@Resource
	private EJBContext eJBContext;
    }
    //
    //
    // Rolling Back a Transaction
    // Rolling back is the process of cleaning all the changes that are done inside the transaction on the application database.
    // In container managed transaction, there are 2 ways to rollback a transaction - by throwing a system exception or
    // by calling the method setRollbackOnly of the interface EJBContext.
    // The interface can be set using injection.
    //
    // In case of throwing application exception, the transaction won’t rollback by the container.
    // For changing this, you can mark the exception with the annotation @ApplicationException(rollback=true).
    // That way the transaction will rollback in case the exception is thrown inside.

}
