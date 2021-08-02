package br.com.fernando.myExamCloud.implementBusinessLogicUsingEJBs;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

public class Question15 {

    class OrderVO {
    }

    interface OrderManager {
	void processOrder(OrderVO orderVO);
    }
    // Given the following stateful session bean:

    @Stateful
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public class OrderManagerBean implements OrderManager {

	// insert code here
	public void processOrder(OrderVO orderVO) {
	}
    }

    // Assuming no other transaction-related metadata, which code can be added at Line 14 to guarantee that business
    // method processOrder will execute only if invoked with an active transaction?
    //
    // Choice A
    // @TransactionAttribute()
    //
    // Choice B
    // @TransactionManagement(TransactionAttributeType.CONTAlNER)
    //
    // Choice C
    // @TransactionAttribute(TransactionAttributeType.MANDATORY)
    //
    // Choice D
    // @TransactionAttribute(TransactionAttributeType.REQUlRES_NEW)
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
    // Choice C is correct.
    @Stateful
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public class OrderManagerBean2 implements OrderManager {

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void processOrder(OrderVO orderVO) {
	}
    }
    // 
    // The transaction attribute Mandatory requires the container to invoke a bean's method in a client's transaction context.
    //
    // If the client is not associated with a transaction context when calling this method, the container throws javax.transaction.TransactionRequiredException 
    // if the client is a remote client or javax.ejb.TransactionRequiredLocalException if the client is a local client.
    //
    // If the calling client has a transaction context, the case is treated as Required by the container.
    //
    // Hence marking TransactionAttributeType.MANDATORY processOrder ensures that it will run only with active transaction.

}
