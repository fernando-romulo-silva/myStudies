package br.com.fernando.chapter03_workingWithMicroservices.part03_maintainingDataConsistency;

public class Part01 {

    // Microservices need to communicate, but they are independent of each other.
    // They, have well defined interfaces and encapsulate implementation details.
    //
    // But what about data?
    // So, when building microservices and thus splitting up our database into multiple smaller databases,
    // how do we manage these challenges?
    //
    // We have also said that services should own their data. That is, every microservice should depend only on its own database.
    //
    // Microservices should have clearly defined responsibilities and boundaries.
    //
    // Microservices need to be grouped according to their business domain.
    //
    // Also, in practice, you will need to design your microservices in such a way that they cannot directly connect to a
    // database owned by another service.
    //
    // The loose coupling means microservices should expose clear API interfaces that model the data and access patterns related to this data.
    // They must stick to those interfaces, when changes are necessary, you will probably introduce a versioning mechanism and create another version of the microservice.
    //
    // You could use a publish/subscribe pattern to dispatch events from one microservice to be processed by others.
    //
    // The publish/subscribe mechanism you would want to use should provide retry and rollback features for the event processing.
    // In a publish/subscribe scenario, the service that modifies or generates the data allows other services to subscribe to events.
    // The subscribed services receive the event saying that the data has been modified.
    // It's often the case that the event contains the data that has been modified.

}
