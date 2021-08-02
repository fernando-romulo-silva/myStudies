package org.javaee7.movieplex7;

public class Description {

    // This chapter provides self-paced instructions for building a typical three-tier end-to-end
    // application using the following Java EE 7 technologies:
    //
    // Java Persistence API 2.1
    // Java API for RESTful Web Services 2.0
    // Java Message Service 2.0
    // JavaServer Faces 2.2
    // Contexts and Dependency Injection 1.1
    // Bean Validation 1.1
    // Batch Applications for the Java Platform 1.0
    // Java API for JSON Processing 1.0
    // Java API for WebSocket 1.0
    // Java Transaction API 1.2
    //
    // Problem Statement
    //
    // This hands-on lab builds a typical three-tier Java EE 7 web application that allows customers to view the show times for a movie in a seven-theater cineplex and make reservations.
    // Users can add new movies and delete existing movies. Customers can discuss the movie in a chat room.
    // Total sales from each showing are calculated at the end of the day. Customers also accrue points for watching movies.
    //
    /**
     * <pre>
     * +----------------+     +------------------+       +-----------+
     * |                |<--->| Show Booking     | <---> |           |
     * |                +     +------------------+       |           |
     * |                |<--->| Add/Delete Movie | <---> |           |
     * |                +     +------------------+       |           |
     * | User Interface |<--->| Ticket Sales     | <---> | Data Base |
     * |                +     +------------------+       |           |
     * |                |<--->| Movie Points     | <---> |           |
     * |                +     +------------------+       |           |
     * |                |<--->| Chat Room        |       |           |
     * +----------------+     +------------------+       +-----------+
     * 
     * </pre>
     */
    
    // The different functions of the application, as previously detailed, utilize various Java technologies and web standards in their 
    // implementation. 
    /**
     * +-----------+          +----------------------+         +-----------+
     * |           |<-------->| Enterprise JavaBeans |<--JPA-->|           |
     * |           +          +----------------------+         +           |
     * |           |<--JSON-->| RESTful Web Services |<--JPA-->|           |
     * |           +          +----------------------+         +           |
     * | JSF Pages |<-------->| Batch Artifacts      |<--JPA-->| Data Base |
     * |           +          +----------------------+         +           |
     * |           |<-------->| Java Message Service |<------->|           |
     * |           +          +----------------------+         +           |
     * |           |<-------->| WebSocket Endpoint   |         |           |
     * +-----------+          +----------------------+         +-----------+
     */
    
    // Technologies used in the application
    //
    // User Interface - Written entirely in JavaServer Faces (JSF).
    //
    // Show Booking - Uses lightweight Enterprise JavaBeans to communicate with the database using the Java Persistence API.
    //
    // Add/Delete Movie - Implemented using RESTful Web Services. JSON is used as on-the-wire data format.
    //
    // Ticket Sales - Uses Batch Applications for the Java Platform to calculate the total sales and persist to the database.
    //
    // Movie Points - Uses Java Message Service (JMS) to update and obtain loyalty reward points; an optional implementation using database technology may be performed.
    //
    // Chat Room - Uses client-side JavaScript and JSON to communicate with a WebSocket endpoint.
    
}
