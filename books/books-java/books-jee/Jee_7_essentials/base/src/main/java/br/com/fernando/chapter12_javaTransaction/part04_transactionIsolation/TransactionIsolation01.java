package br.com.fernando.chapter12_javaTransaction.part04_transactionIsolation;

public class TransactionIsolation01 {

    // JDBC Transaction Isolation Levels
    //
    // Problems:
    //
    // Dirty reads: A dirty read occurs when a transaction is allowed to read data from a row that has been modified by another running transaction and not yet committed.
    // Occurs when a transaction reads uncommitted data. In other words, the transaction is allowed to read the data that has been changed by other transactions and is not yet committed.
    //
    // Non-repeatable reads: A non-repeatable read occurs, when during the course of a transaction, a row is retrieved twice and the values within the row differ between reads.  
    //  It occurs because after the first transaction reads the data in the row, other transactions are allowed to change this data.
    //
    // Phantom reads: A phantom read occurs when, in the course of a transaction, new rows are added or removed by another transaction to the records being read.
    //
    // Not all database vendors support all transaction isolation levels available in the JDBC API.
    // The Enterprise Server permits specifying any isolation level your database supports.
    // The following table defines transaction isolation levels.
    //
    //
    // Database and isolation levels
    //
    // Apart from MySQL (which uses REPEATABLE_READ), the default isolation level of most relational database systems is READ_COMMITTED. 
    // All databases allow you to set the default transaction isolation level.
    //
    // Typically, the database is shared among multiple applications and each one has its own specific transaction requirements. 
    // For most transactions, the READ_COMMITTED isolation level is the best choice and we should only override it for specific business cases.
    //
    // * Choosing a transaction isolation level doesn't affect the locks that are acquired to protect data modifications. *
    //
    // A transaction always gets an exclusive lock on any data it modifies and holds that lock until the transaction completes, regardless of the isolation level set for that transaction. 
    // For read operations, transaction isolation levels primarily define the level of protection from the effects of modifications made by other transactions.
    //
    // Conversely, a higher isolation level reduces the types of concurrency effects that users might encounter, but requires more system resources and increases the chances that one transaction will block another. 
    //
    // Choosing the appropriate isolation level depends on balancing the data integrity requirements of the application against the overhead of each isolation level. 
    //
    // The highest isolation level, serializable, guarantees that a transaction will retrieve exactly the same data every time it repeats a read operation, but it does this by performing a level of locking that is likely to impact other users in multi-user systems.
    //
    // The lowest isolation level, read uncommitted, can retrieve data that has been modified but not committed by other transactions. 
    //
    // All concurrency side effects can happen in read uncommitted, but there's no read locking or versioning, so overhead is minimized.
}
