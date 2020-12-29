package br.com.fernando.myExamCloud.understandJavaEEArchitecture;

public class Question07 {

    // Scenario : You are developing a web based shopping cart application with the following components.
    //
    // * Shopping Cart - needs to maintain state
    // * Customer - needs persistence
    // * Payment processing sub system - needs to process transactions
    //
    // Select the JEE technologies suited for the above components. [ Choose four ]
    //
    // Choice A - Servlet and JSP for presentation.
    //
    // Choice B - Shopping Cart will be represented as stateful session bean.
    //
    // Choice C - Customer will be represented as JPA entity.
    //
    // Choice D - Payment processing sub system will be implemented as stateless session beans.
    //
    // Choice E - Payment processing sub system will be implemented as stateful session bean components.
    //
    // Choice F - AWT/JFC for presentation
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
    // Choice A, B, C and D are correct answers.
    //
    // Note that it is a web based application; so JSP/Servlet will be the best choice for handling dynamic requests at presentation tier.
    // Shopping Cart component needs to maintain the state; hence stateful session beans will be used.
    // Customer details needs to be persisted and JPA entities are used to handle the persistence.
    // Payment processing sub system requires transaction; therefore, stateless session are used to handle the transaction.

}
