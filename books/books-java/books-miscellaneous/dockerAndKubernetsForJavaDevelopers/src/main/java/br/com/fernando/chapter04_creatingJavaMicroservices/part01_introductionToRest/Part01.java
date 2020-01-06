package br.com.fernando.chapter04_creatingJavaMicroservices.part01_introductionToRest;

public class Part01 {

    // Introduction to Rest
    //
    // The REST acronym stands for Representational State Transfer.
    // It's an architectural style and a design for network-based software.
    // It describes how one system can communicate a state with another.
    //
    // There are some concepts in REST that we need to understand, before we go further:
    //
    // * Resource: This is the main concept in the REST architecture.
    // Any information can be a resource. A bank account, a person, an image, a book.
    // A representation of a resource must be stateless
    //
    // * Representation: A specific way a resource can be represented.
    // For example, a bank account resource can be represented using JSON, XML, or HTML.
    //
    // * Server: A service provider. It exposes services which can be consumed by clients.
    //
    // * lient: A service consumer. This could be another microservice, application, or just a user's
    // web browser running an Angular application, for example
    //
    // HTTP Methods
    //
    // * GET: Get gives a read access to the resource. Calling GET should not create any side-effects.
    // It means that the GET operation is idempotent. The resource is never changed via a GET request;
    //
    // * PUT: Put creates a new resource. Similar to GET, it should also be idempotent.
    // If the Request-URI refers to an already existing resource – an update operation will happen,
    // otherwise create operation should happen
    //
    // * DELETE: Removes the resource or resources.
    // The DELETE operation should not give different results when called repeatedly.
    //
    // * POST: Post create a new resource.
    //
    // It is useful to note that a REST service is a web service, but a web service is not necessarily a REST service.
    // The REST microservice should represent the state of an entity.
    // For example, having our book resource, we could imagine having the following operations defined in the service:
    //
    //
    // * /books would allow access of all the books
    //
    // * /books/:id would be an operation for viewing an individual book, retrieved based on its unique ID
    //
    // * sending a POST request to /books would be how you would actually create a new book and store it in a database
    //
    // * sending a PUT request to /books/:id would be how you would update the attributes of a given book, again identified by its unique ID
    //
    // * sending a DELETE request to /books/:id would be how you would delete a specific book, again identified by its unique ID

    //
}
