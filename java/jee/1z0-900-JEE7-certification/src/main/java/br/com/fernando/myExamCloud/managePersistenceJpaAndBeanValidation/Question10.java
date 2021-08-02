package br.com.fernando.myExamCloud.managePersistenceJpaAndBeanValidation;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;

public class Question10 {

    // Code :

    @Entity
    public class Customer {

	@Id
	private int id;
	// other fields and methods
    }

    // Which of the following find an Order entity by its primary key?
    //
    // Choice A
    // EntityManager entityManager =//...
    // Customer customer = (Customer)entityManager.find("Customer", new Integer(99));
    //
    // Choice B
    // EntityManager entityManager =//...
    // Customer customer = (Customer)entityManager.findByPk("Customer", new Integer(99));
    //
    // Choice C
    // EntityManager entityManager =//...
    // Customer customer = (Customer)entityManager.findByPrimaryKey("Customer", new Integer(99));
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
    // Choice A is correct.
    //
    // The 'find' method EntityManager is used to find an entity by its primary key.
    // We can use EntityManager method find to retrieve the corresponding entity from the database without having to create a query by its primary key.
    public void test() {
	EntityManager entityManager = null;// ...
	Customer customer = (Customer) entityManager.find(Customer.class, new Integer(99));
    }

}
