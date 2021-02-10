package br.com.fernando.enthuware.implementBusinessLogicUsingEJBs;

import javax.ejb.EJB;
import javax.ejb.Stateless;

public class Question10 {

    // Given the code fragment:

    public class Product {
    }

    @Stateless // 1
    // 2
    public class Warehouse { // 3
	public void sendConfirmation(String msg) { // 4

	}

	public String reserve(Product pro) { // 5
	    return "Text";
	}
    }// 6

    @Stateless // 8
    // 9
    public class Shop { // 10
	@EJB
	private Warehouse war; // 11
	// 12

	public void purchase(Product pro) { // 13
	    String confirmationCode = war.reserve(pro); // 14
	    war.sendConfirmation(confirmationCode); // 15
	} // 16
    } // 17

    // The sendConfirmation() and reserve() methods should be executed in the same transactional context.
    // Which transaction attributes do you ensure this?
    //
    // A - Add annotations:
    // @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) at line 2,
    // @Transactional at line 9, and
    // @TransactionAttribute(TransactionAttributeType MANDATORY) at line 12.
    //
    // B - Add annotations:
    // @TransactionAttribute(TransactionAttributeType.REQUIRED) at line 2,
    // @Transactional at line 9, and
    // @ TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    //
    // C - Add annotations:
    // @TransactionAttribute(TransactionAttributeType.MANDATORY) at line 2,
    // @Transactional at line 9, and
    // @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    //
    // D - No additional annotations are required.
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
    // The correct answer is D
    //
    // By default, if a TransactionAttribute annotation is not specified for a method of an enterprise bean with container-managed transaction demarcation,
    // the value of the transaction attribute for the method is defined to be REQUIRED.
    //
    // The same transaction context is propagated automatically accross method calls.
}
