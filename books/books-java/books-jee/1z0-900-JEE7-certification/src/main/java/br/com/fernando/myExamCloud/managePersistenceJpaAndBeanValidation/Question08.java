package br.com.fernando.myExamCloud.managePersistenceJpaAndBeanValidation;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

public class Question08 {

    EntityManager entityManager;

    // A Persistence application locks entity x with a LockModeType.PESSIMISTIC_READ lock type.
    // Which statement is true?
    public void method01() {

	Query query = entityManager.createQuery("from Student where studentId = :studentId");
	query.setParameter("studentId", 2);
	query.setLockMode(LockModeType.PESSIMISTIC_READ);
	query.getResultList();
    }

    // You had to select 1 option(s)
    //
    // A
    // LockModeType.PESSIMISTIC_READ is the synonym of LockModeType.READ
    //
    // B
    // This operation will force serialization among transactions attempting to read the entity data.
    //
    // C
    // This operation will result in a TransactionRolledbackException if the lock cannot be obtained.
    //
    // D
    // If the application updates the entity later, and the changes are flushed to the database, the lock will be converted to an exclusive lock.
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
    // If transaction T1 calls for a lock of type LockModeType.PESSIMISTIC_READ or LockModeType.PESSIMISTIC_WRITE on an object, 
    // the entity manager must ensure that neither of the following phenomena can occur:
    // 
    // P1 (Dirty read): Transaction T1 modifies a row. 
    // Another transaction T2 then reads that row and obtains the modified value, before T1 has committed or rolled back.
    //
    // P2 (Non-repeatable read): Transaction T1 reads a row. Another transaction T2 then modifies or deletes that row, before T1 has committed or rolled back.


}
