package br.com.fernando.chapter12_javaTransaction.part04_transactionIsolation;

public class TransactionIsolation02 {

    /**
     * <pre>
     * 
     *  Isolation Level   |  Dirty Read | Nonrepeatable Read  | Phantom
     *  ------------------|-------------|---------------------|--------
     *  Read Uncommitted  |     yes     |        yes          |   yes 
     *                    |             |                     | 
     *  Read Commited     |     no      |        yes          |   yes 
     *                    |             |                     | 
     *  Reapeatable Read  |     no      |        no           |   yes
     *                    |             |                     | 
     *  Serializable      |     no      |        no           |   no
     * 
     * </pre>
     */

    // * Read Uncommitted *
    //
    // When this level is set, the transaction can read uncommitted data resulting in the Dirty Read problem.
    // With this isolation level, we allow a transaction to read the data which is being updated by other transaction and not yet committed.
    //
    // Use this on a one-user system, on systems in which the likelihood of two transactions accessing the same resources is nil or almost nil, 
    // or when using the Rowversion data type to control concurrency.
    //
    // Read Uncommitted isolation level can be useful in certain situations, particularly if you’re trying to avoid heavy lock contention.
    // If you’re pulling historical data from a data warehouse or reporting database, your queries might benefit from the minimal locking, especially those complex 
    // joins that can easily escalate to table locks. 
    // In such a scenario, the primary DML operations might be inserts only and often not part of the queries.
    // 
    //
    //
    //
    // * Read Committed* 
    //
    // This prevents Dirty Read. When this level is set, the transaction can not read the data that is being modified by the current transaction.
    // This will force user to wait for the current transaction to finish up its job.
    // Suppose User A is trying to read a row which is being updated by User B. 
    // Here, we are asking User A to wait for the User B to finish its update task, and giving the updated/correct data to User A.
    // But the problem with this level is - it can't resolve Phantom Read or Inconsistency Analysis i.e it asks User A to wait for Read but not for update or insert.
    //
    //
    //
    //
    //
    // * Repeatable Read *
    //
    // This level does every work that Read Committed does. but it has one additional benefit. 
    // User A will wait for the transaction being executed by User B to execute it's Update query as well, like Read Query. 
    // But Insert query doesn't wait, this also creates Phantom Read problem.
    //
    //
    //
    //    
    //
    //
    //
    // * Serializable *
    //
    // This is the maximum level of Isolation level provided by SQL Server transaction. 
    // We can prevent Phantom Read problem by implementing this level of isolation. 
    // It asks User A to wait for the current transaction for any kind of operation he wants to perform.
    //
    // As you will see in the examples that follow, the higher the isolation level, the higher the level of protection (the more concurrency issues are prevented). 
    // Also, each isolation level includes the protections provided by the previous level so that each successively higher isolation level provides added protection in the form of 
    // more concurrency issues avoided. 
    // But, alas, nothing is free, and so the higher the isolation level, the less data availability there will be. 
    //
    // Choosing the appropriate isolation level is a balancing act between highly safe concurrency and high data availability.
}
