package br.com.fernando.chapter03_workingWithMicroservices.part02_monolithicVsMicroservices;

public class Part02 {

    // The microservices architecture
    //
    // The microservices architecture is designed to address the issues we've mentioned with monolithic applications.
    //
    // The main difference is that the services defined in the monolithic application are decomposed into individual services. Best of all, they are deployed separately from one another on separate hosts.
    //
    /**
     * <pre>
     * 
     * -------------------     ------------------- 
     * |     Host 1      |     |     Host 2      | 
     * |  -------------  |     |  -------------  | 
     * |  | Service A |  |     |  | Service B |  | 
     * |  -------------  |     |  -------------  | 
     * |  -------------  |     |  -------------  | 
     * |  | Service B |  |     |  | Service C |  | 
     * |  -------------  |     |  -------------  | 
     * -------------------     -------------------
     * 
     * </pre>
     */
    //
    // When creating an application using the microservices architecture, each microservice is responsible for a single, specific business
    // function and contains only the implementation that is required to perform exactly that specific business logic.
    //
    // Let's summarize the benefits of using the microservices architecture from the development process perspective:
    //
    // * Services can be written using a variety of languages, frameworks, and their versions
    //
    // * Each microservice is relatively small, easier to understand by the developer (which results in less bugs), easy to develop, and testable
    //
    // * The deployment and start up time is fast, which makes developers more productive
    //
    // * Each service can consist of multiple service instances for increased throughput and availability
    //
    // * Each service can be deployed independently of other services, easier to deploy new versions of services frequently
    //
    // * It is easier to organize the development process; each team owns and is responsible for one or more service and can develop, release,
    // or scale their service independently of all of the other teams
}
