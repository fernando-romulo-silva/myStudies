package br.com.fernando.chapter03_workingWithMicroservices.part02_monolithicVsMicroservices;

public class Part01 {

    // The monolithic architecture
    //
    // This kind of an application is built as a single unit.
    // As you can see, we have a couple of layers here.
    // Enterprise Applications are built in three parts usually:
    //
    // 1* A client-side user interface (consisting of HTML pages and JavaScript running in a browser),
    // a server-side part handling the HTTP requests (probably constructed using some spring-like controllers),
    //
    // 2* We have a service layer, which could probably be implemented using EJBs or Spring services.
    // The service layer executes the domain specific business logic, and retrieves/updates data in the database, eventually.
    //
    //
    /**
     * <pre>
     * ___________________________
     * |                         | 
     * |    UI1 ... UIn          |               
     * |        |                |         
     * |        V                |         
     * | Service1 ... ServiceN   |
     * |        |                |
     * |        V                |
     * |    DataAcess            |
     * |        |                |
     * |        V                |
     * |     DataBase            |
     * |                         |
     * ---------------------------
     * </pre>
     */

    // The whole application is a monolith, a single logical executable.
    //
    // To make any changes to the system, we must build and deploy an updated version of the whole server-side application;
    // this kind of application is packaged into single WAR or EAR archive, altogether with all the static content such as HTML and JavaScript files.
    //
    // When deployed, all the application code runs in the same machine.
    // Scaling this kind of application usually requires deploying multiple copies of the exact same application code to multiple machines in a cluster,
    // behind some load balancer perhaps.
    //
    // All monolithic applications have these characteristics:
    //
    // * They are rather large, often involving a lot of people working on them. This can be a problem when loading your project into the IDE,
    // despite having powerful machines and a great development environment
    //
    // * They have a long release cycle, we all know the process of release management, permissions, regression testing, and so on.
    //
    // * They are difficult to scale; it typically takes a considerable amount of work to put in a new application instance in the cluster by the operations team.
    //
    // * In case of deployment failure, the whole system is unavailable.

}
