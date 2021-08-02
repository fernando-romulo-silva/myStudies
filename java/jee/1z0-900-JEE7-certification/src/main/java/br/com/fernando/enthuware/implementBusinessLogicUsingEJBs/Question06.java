package br.com.fernando.enthuware.implementBusinessLogicUsingEJBs;

public class Question06 {

    // Which of the following are true regarding setRollbackOnly() method of EJBContext?
    //
    // A - Only a session bean with bean managed transaction demarcation can call it.
    //
    // B - Once called, the container ensures that the transaction will never commit.
    //
    // C - The transaction timeout is disabled.
    //
    // D - A bean can call this method only if it has started the transaction.
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
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // The correct answer is B
    //
    // 'A' is wrong because On the contrary, only a session bean with container managed transaction demarcation can call it
    // because a BMT bean can use UserTransaction interface to rollback the transaction.
    //
    // 'D' is wrong because: to be able to call these methods, the bean must be in a transaction. 
    // Whether the bean started the transaction is immaterial.
}
