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
    //
    //
    //
    // There are two types of locks we can retain: an exclusive lock and a shared lock.
    //
    // We could read but not write in data when someone else holds a SHARED LOCK.
    // In order to modify or delete the reserved data, we need to have an EXCLUSIVE LOCK.
    //
    //
    // JPA specification defines three pessimistic lock modes which we're going to discuss:
    //
    // * PESSIMISTIC_READ – allows us to obtain a SHARED LOCK and prevent the data from being UPDATED or DELETED
    // $$ Other transactions won't be able to make any updates or deletes though $$
    //
    // * PESSIMISTIC_WRITE – allows us to obtain an EXCLUSIVE LOCK and prevent the data from being READ, UPDATED or DELETED
    // $$ It will prevent other transactions from reading, updating or deleting the data $$
    //
    // * PESSIMISTIC_FORCE_INCREMENT – works like PESSIMISTIC_WRITE and it additionally increments a version attribute of a versioned entity
    // $$ Acquiring that lock results in updating the version column $$
    //
    // All of them are static members of the LockModeType class and allow transactions to obtain a database lock.
    // They all are retained until the transaction commits or rolls back.
    //
    //
    // Exceptions
    //
    // It's good to know which exception may occur while working with pessimistic locking. JPA specification provides different types of exceptions:
    //
    // * PessimisticLockException – indicates that obtaining a lock or converting a shared to exclusive lock fails and results in a transaction-level rollback
    //
    // * LockTimeoutException – indicates that obtaining a lock or converting a shared lock to exclusive times out and results in a statement-level rollback
    //
    // * PersistanceException – indicates that a persistence problem occurred. PersistanceException and its subtypes, 
    // except NoResultException, NonUniqueResultException, LockTimeoutException, and QueryTimeoutException, marks the active transaction to be rolled back.
}
