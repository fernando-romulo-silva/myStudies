package br.com.fernando.chapter05_soapBasedws.part00_intro;

public class Intro {

    // SOAP is an XML-based messaging protocol used as a data format for exchanging information over web services. 
    // The SOAP specification defines an envelope that represents the contents of a SOAP message and encoding rules for data types.
    // 
    // Java API for XML-Based Web Services (JAX-WS) hides the complexity of the SOAP protocol and provides a simple API for development 
    // and deployment of web service endpoints and clients.
    //
    // The developer writes a web service endpoint as a Java class. 
    // The JAX-WS runtime publishes the web service and its capabilities using Web Services Description Language (WSDL). 
    // Tools provided by a JAX-WS implementation, such as wscompile by the JAX-WS Reference Implementation, are used to generate a proxy to
    // the service and invoke methods on it from the client code.
    // 
    // In addition to sending SOAP messages over HTTP, JAX-WS provides XML-over-HTTP protocol binding and is extensible to other protocols and transports. 
    // The XML-over-HTTP binding use case is better served by JAX-RS and will not be discussed here.
    //
    // JAX-WS also facilitates, using a nonstandard programming model, the publishing and invoking of a web service that uses WS-* specifications such 
    // as WS-Security, WS-Secure Conversation, and WS-Reliable Messaging. 
    //
    //  Some of these specifications are already  implemented in the JAX-WS implementation bundled as part of GlassFish. 
    // However, this particular usage of JAX-WS will not be discussed here. More details about it can be  found at http://metro.java.net.
}
