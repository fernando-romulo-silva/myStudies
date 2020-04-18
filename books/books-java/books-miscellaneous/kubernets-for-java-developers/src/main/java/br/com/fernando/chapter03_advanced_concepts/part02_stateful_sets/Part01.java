package br.com.fernando.chapter03_advanced_concepts.part02_stateful_sets;

public class Part01 {

    // What are Stateful and Stateless applications?
    //
    // Stateful and Stateless applications store state from client requests on the server itself and use that state to process further requests.
    //
    // It uses DB for storing data as a backend, but session information stored on the server itself.
    // When a user sends a login request, it enables login to be true and user authenticated now, and on the second request, the user sees the dashboard.
    // Stateful applications don’t need to make a call to DB second time as session info stored on the server itself, hence it is faster.
    //
    // But it does have drawbacks. Let’s say there is a load balancer and there are two servers behind, running the same Stateful application.
    // The now first request to log in go to server 1 and second request might go to server 2, now since only server one has enabled login to true,
    // the user won’t be able to logic when LB sends him to 2nd server.
    //
    // Stateless don’t store any state on the server.
    // They use DB to store all the Info. Obviously, DB is stateful, i.e. it has persistent storage attached to it.
    //
    // Typically, a user requests for login with credentials, any of the servers behind LB process the request and generates an auth token and stores it
    // in DB and returns token to the client on the frontend.
    //
    // Next request is sent along with the token.
    // Now, no matter whichever server process request, it will match the token with info in DB, and grant login to the user.
    //
    // Every request is independent, doesn’t have any link with previous or next request, just like REST.
    //
    // Although Stateless apps have one extra overhead of the call to DB, these apps are amazing at horizontally scaling, crucial for modern apps,
    // which might have millions of users.
    //
    //
    //
    // Stateful Sets
    //
    // A stateful set ensures that a specified number of “pets” with unique identities are running at any given time.
    // Each pet is a stateful pod. The identity of a pod is composed of:
    //
    // * A stable hostname, available in DNS
    // * An ordinal index
    // * Stable storage, linked to the ordinal and hostname
    //
    // Stateful applications typically have the following requirements:
    // * Discovery of peers for quorum
    // * Stable persistent storage
    // * Startup/teardown ordering
    //
    // Look at the couchbase-statefulset.yml file.
    //
    //
    // * Kubernetes Stateless Candidates
    //
    // Web Servers: Apache, Nginx, or Tomcat
    //
    //
    // * kubernetes statefulset Candidates
    //
    // Databases Clusters: MySQL, PostgreSQL, CouchDB, etc
    //
    // Messages-broker Clusters: Kafka, RabbitMQ, etc.
    //
}
