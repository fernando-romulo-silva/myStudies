package br.com.fernando.chapter07_webSocket.part00_intro;

public class Intro {

    // WebSocket provides a full-duplex and bidirectional communication protocol over a single TCP connection. 
    // Full-duplex means a client and server can send messages independent of each other. 
    // Bidirectional means a client can send a message to the server and vice versa. 
    // WebSocket is a combination of the IETF RFC 6455 Protocol and the W3C JavaScript API.
    //
    // The protocol defines an opening handshake and basic message framing, layered over TCP. 
    // The API enables web pages to use the WebSocket protocol for two-way communication with the remote host.
    //
    // Unlike HTTP, there is no need to create a new TCP connection and send a message chock-full of headers for every exchange between client and server.
    //
    // Once the initial handshake happens via HTTP Upgrade (defined in RFC 2616, section 14.42), the client and server can send messages to each other, 
    // independent of the other. 
    //
    // There are no predefined message exchange patterns of request/response or one-way between client and server. 
    // These need to be explicitly defined over the basic protocol.
    //
    // The communication between client and server is pretty symmetric, but there are two differences:
    //
    // * A client initiates a connection to a server that is listening for a WebSocket request.
    //
    // * A client connects to one server using a URI. A server may listen to requests from multiple clients on the same URI.
    //
    // After a successful handshake, clients and servers transfer data back and forth in conceptual units referred to as messages. 
    // On the wire, a message is composed of one or more frames. 
    //
    // Java API for WebSocket defines a standard API for building WebSocket applications and will provide support for:
    //
    // * Creating a WebSocket client and server endpoint using annotations and an interface
    // * Creating and consuming WebSocket text, binary, and control messages
    // * Initiating and intercepting WebSocket life-cycle events
    // * Configuring and managing WebSocket sessions, like timeouts, retries, cookies, and connection pooling
    // * Specifying how the WebSocket application will work within the Java EE security model

}
