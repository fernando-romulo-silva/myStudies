package br.com.fernando.myExamCloud.managePersistenceJpaAndBeanValidation;

public class Question02 {

    // A developer working in EPractize Labs wants to fetch all customers whose order amount is greater than 500 USD.
    // Assume that Employee entity is super class of PersonalCustomer and CorporateCustomer.
    // Which query can achieve this?
    //
    //
    // Choice A
    // select c,p,d from Employee c, PersonalCustomer p, CorporateCustomer d where c.orderTotal > 500 AND p. orderTotal > 500 and d. orderTotal > 500
    //
    // Choice B
    // select c from Employee c where c.orderTotal > 500
    //
    // Choice C
    // select c,p,d from Employee c, PersonalCustomer p, CorporateCustomer d where c.orderTotal > 500
    //
    // Choice D
    // select c,p,d from Employee c, PersonalCustomer p, CorporateCustomer d where d.orderTotal > 500
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
    // Explanation
    //
    // Choice B is correct
    // By default, all queries are polymorphic.
    //
    // That is, the FROM clause of a query designates not only instances of the specific entity class(es) to which it explicitly refers,
    // but subclasses as well.
    //
    // The instances returned by a query include instances of the subclasses that satisfy the query conditions.
    // The following query returns all customer, including subtypes of Employee, such as PersonalCustomer and CorporateCustomer.
    //
    //  select c from Employee c where c.orderTotal > 500
}
