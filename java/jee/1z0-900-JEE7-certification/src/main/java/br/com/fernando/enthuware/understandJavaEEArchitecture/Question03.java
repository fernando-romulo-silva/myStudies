package br.com.fernando.enthuware.understandJavaEEArchitecture;

public class Question03 {

    // Understand Java EE Architecture
    //
    // A Java EE application that uses an EJB container must communicate with a legacy system developed using non-Java technologies.
    // Which two Java EE technologies can be used in the integration tier of the application? (select two)
    //
    //  Choice A 	
    //  JMS
    //
    //  Choice B 	
    //  JSF
    //
    //  Choice C 	
    //  JPA
    //
    //  Choice D 	
    //  JCA
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
    //
    //
    //
    //
    //    
    //
    //
    //
    // Choice A and D are correct answers.
    //
    // The business solution requires integration with a non-Java based legacy system, so JCA(Java Connector Architecture) and JMS (Java Message Service) are best choices for such integrations.
    //
    // The 'A' is correct because JMS (Java Message Service) is an API that provides the facility to create, send and read messages.
    // It provides loosely coupled, reliable and asynchronous communication. JMS is also known as a messaging service.
    // It is possible for an MDB to interface with a legacy application. 
    // The new Java application can remain decoupled with the legacy application by sending messages to the MDB.
    //
    // The 'D' is correct because Java EE Connector Architecture (JCA) is a Java-based technology solution for connecting application servers and enterprise information systems (EIS) as part of
    // enterprise application integration (EAI) solutions.
    // While JDBC is specifically used to connect Java EE applications to databases, JCA is a more generic architecture for connection to legacy systems.
    //
    // Choice B is incorrect, JSF (Java Server Faces) is a web tier framework and it cannot be used at integration tier.
    //
    // Choice C is incorrect, JPA (Java Persistence API) is a collection of classes and methods to persistently store the enterprise data into a database and it cannot be used to interact with non-Java legacy systems.

}
