package br.com.fernando.enthuware.implementBusinessLogicUsingEJBs;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

public class Question04 {

    class Customer {

    }

    public class AppException extends Exception {
    }

    // Given:
    //
    @Stateless
    public class CustomerService {

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean addCustomer(int id, String name) throws AppException {

	    // assume database insert operation succeeds
	    Customer c = insertIntoDB(id, name);

	    if (id < 20) {
		throw new AppException();
	    }

	    return true;
	}

	private Customer insertIntoDB(int id, String name) {
	    // TODO Auto-generated method stub
	    return null;
	}
    }
    // If an exception is thrown inside the if block, what effect will it have on the transaction?
    // You have to select one option
    // 
    // A
    // The transaction will be committed.
    //
    // B
    // The transaction will be suspended.
    //
    // C
    // The transaction will be rolled back.
    //
    // D
    // None of the above.
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
    // This question has a problem. Assuming that AppException is an application exception, it is not given whether this exception is specified as causing rollback. 
    // So, we do not know whether it will cause the transaction to rollback automatically. 
    // However, since no other option is given, this seems to be the best option.
    // Use your judgement if you get such a question in the exam.
    //
    // C is wrong because An application exception does not affect the transaction unless it has been specified as causing rollback.
}
