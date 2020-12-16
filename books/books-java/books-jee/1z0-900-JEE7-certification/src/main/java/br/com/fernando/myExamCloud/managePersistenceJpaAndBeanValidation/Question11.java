package br.com.fernando.myExamCloud.managePersistenceJpaAndBeanValidation;

public class Question11 {

    // The Java Persistence API defines a notion of propagation of a persistence context Which statement is correct?
    //
    // Choice A
    // Persistence context propagation is NOT supported for message-driven beans.
    //
    // Choice B
    // Persistence context propagation is supported for any type of an entity manager.
    //
    // Choice C
    // Persistence context propagation avoids the need for the application to pass references of entity manager instances.
    //
    // Choice D
    // Persistence context propagation results in cloning of all managed instances for use by another instance of an entity manager.
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
    // Choice C is correct.
    //
    // A persistence context(PC) is a set of managed entity instances in which for any persistent entity identity there is a unique entity instance.
    // An Entity Manager(EM) provides the necesarry APIs to interact with a persistence context.
    //
    // There is often a need to access the entities in the same PC in different components (say multiple EJBs or web component and EJB etc.) involved in processing of a request.
    // One way of doing this is to pass an EM instance around in the call stack from one component to another, but that's not so elegant as it pollutes the interfaces.
    //
    // A better and recommended approach is to use a feature called Persistence Context Propagation that is offered by the Java EE container.
    // PC propagation allows multiple components of a Java EE application to share the same persistence context.
    // Each component use their own EM which they obtain using dependency injection or JNDI lookup and when they use the EM in the contex of the same JTA transaction, container automaticaly binds the EM to the same PC.
    //
    // Thus application does not have to explicitly pass an EM from one component to another.
    // This technique is portable across any Java EE 5 compatible application server.

}
