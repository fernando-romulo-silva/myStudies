package br.com.fernando.enthuware.implementBusinessLogicUsingEJBs;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

public class Question11 {

    // Given the code fragment:

    @Stateless
    @TransactionManagement(TransactionManagementType.BEAN)
    public class ImageService {
	
	@Resource
	private UserTransaction ut;

	public void addRecord(int data) {
	    try {
		ut.begin();
		// access database
		ut.commit();
	    } catch (Exception e) {

		// LINE 13
	    }
	}
    }

    // What should you do on Line 13 to abort the transaction?
    //
    // A - Rethrow the exception.
    //
    // B - Nothing; the container will roll back the transaction.
    //
    // D - Call ut.rollback();
    //
    // C - Call ut.setRollbackOnly();
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
    // The correct answer is C
    // You must complete any transactions associated with that thread before beginning a new transaction.
    //
    // B is wrong because the container will not roll back the transaction automatically.
    // The transaction will keep be remain alive and uncompleted.
    // If you call ut.begin() in the same thread, an IllegalStateException will be thrown.

}
