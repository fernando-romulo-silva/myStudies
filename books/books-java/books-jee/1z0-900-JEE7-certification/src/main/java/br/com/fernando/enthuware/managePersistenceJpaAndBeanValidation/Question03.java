package br.com.fernando.enthuware.managePersistenceJpaAndBeanValidation;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

public class Question03 {

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
    // 'A' is wrong because The lock mode type values READ and WRITE are synonyms of OPTIMISTIC and OPTIMISTIC_FORCE_INCREMENT respectively.
    // The latter are to be preferred for new applications.
    //
    // 'B' is wrong because A lock with LockModeType.PESSIMISTIC_READ can be used to query data using repeatable-read semantics without
    // the need to reread the data at the end of the transaction to obtain a lock, and without blocking other transactions reading the data.
    //
    // A lock with LockModeType.PESSIMISTIC_WRITE can be obtained on an entity instance to force serialization among transactions attempting to update the entity data.
    // A lock with LockModeType.PESSIMISTIC_WRITE can be used when querying data and there is a high likelihood of deadlock or update failure among concurrent updating transactions.
    //
    // C is wrong because when the lock cannot be obtained, and the database locking failure results in transaction-level rollback,
    // the provider must throw the PessimisticLockException and ensure that the JTA transaction or EntityTransaction has been marked for rollback.
    //
    // When the lock cannot be obtained, and the database locking failure results in only statement-level rollback, the provider must throw
    // the LockTimeoutException (and must not mark the transaction for rollback).

}
