package br.com.fernando.myExamCloud.implementBusinessLogicUsingEJBs;

import javax.annotation.Resource;
import javax.ejb.ApplicationException;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Question14 {

    // Given

    public class OrderEntity {

	public int orderAmount() {
	    // TODO Auto-generated method stub
	    return 0;
	}

    }

    public class MyApplicationException extends Exception {

	private static final long serialVersionUID = 1L;
    }

    @Stateless
    public class OrderManager {

	@PersistenceContext(name = "Order-PU")
	EntityManager entitymanager;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean addOrder(OrderEntity orderEntity) throws MyApplicationException {
	    // assume database insert operation succeeds
	    entitymanager.persist(orderEntity);

	    if (orderEntity.orderAmount() < 0) {
		throw new MyApplicationException();
	    }

	    return true;
	}
    }

    // If an exception is thrown inside the if block, what effect will it have on the transaction?
    //
    // Choice A
    // The transaction will be committed.
    //
    // Choice B
    // The transaction will be suspended.
    //
    // Choice C
    // The transaction will be rolled back.
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
    // The correct answer is A
    //
    // The container will not rollback if an application exception occurs.
    //
    // There are two ways to roll back a container-managed transaction:
    //
    // * First, if a system exception is thrown, the container will automatically roll back the transaction.
    // * Second, by invoking the setRollbackOnly method of the EJBContext interface, the bean method instructs the container to roll back the transaction.
    //
    // If the bean throws an application exception, the rollback is not automatic but can be initiated by a call to setRollbackOnly.
    //
    //
    // If you can throw it to the client side if you want to handle it there.
    // By default the EJB container won't rollback your transaction if you throw a BusinessException, but you can change this behavior
    // by annotating your Exception the following way:
    @ApplicationException(rollback = true)
    public class NotEnoughMoneyOnYourAccountException extends Exception {

	private static final long serialVersionUID = 1L;
    }
    // If your program throws a RuntimeException, it will be sent to the client wrapped as a RemoteException, and your transaction will be rolled back.
    // These are less excepted than business exceptions, therefore we usually don't catch them at EJB side.

    public class OrderManagerCaller {

	@EJB
	OrderManager orderManager;

	@Resource
	EJBContext context;

	public void callAddOrder1() { // this method commit transaction

	    OrderEntity orderEntity = new OrderEntity();

	    try {

		orderManager.addOrder(orderEntity);

	    } catch (MyApplicationException e) {
		// nothing, just logging
		e.printStackTrace();
		// context.setRollbackOnly(); // if you remove the comment, rollback the transaction
	    }
	}
    }

}
