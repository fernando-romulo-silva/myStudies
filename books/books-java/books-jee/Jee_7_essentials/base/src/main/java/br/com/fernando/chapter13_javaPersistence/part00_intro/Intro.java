package br.com.fernando.chapter13_javaPersistence.part00_intro;

public class Intro {

    // JPA defines an API for the management of persistence and object/relational mapping using a Java domain model.
    //
    // A database table, typically with multiple columns, stores the persistent state of an application.
    //
    // Multiple rows are stored in the database table to capture different states. 
    // A single column or combination of columns may define the uniqueness of each row using primary key constraint. 
    // Typically, an application accesses and stores data to multiple tables. 
    // These tables generally have relationships defined among them using foreign key constraint.
    //
    //
    // JPA defines a standard mapping between a database table and a POJO. 
    // It defines syntax to capture primary and foreign key constraints and how these rows can be created, read, updated, and deleted using these POJOs. 
    // Transactions, caching, validation, and other similar capabilities required by an application accessing a database are also defined by JPA.
}
