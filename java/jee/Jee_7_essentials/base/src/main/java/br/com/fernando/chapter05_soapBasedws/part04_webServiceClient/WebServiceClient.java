package br.com.fernando.chapter05_soapBasedws.part04_webServiceClient;

import javax.xml.ws.Endpoint;

import br.com.fernando.chapter05_soapBasedws.part04_webServiceClient.client.DifferentWebService;
import br.com.fernando.chapter05_soapBasedws.part04_webServiceClient.client.DifferentWebService_Service;

public class WebServiceClient {

    // The contract between the web service endpoint and a client is defined through WSDL.
    // As with an SEI-based web service endpoint, you can easily generate a high-level web service client by importing the WSDL.
    // Such tools follow the WSDL-to-Java mapping defined by the JAX-WS specification and generate the corresponding classes.

    // WSDL-to-Java mappings
    //
    // wsdl:service = Service class extending javax.xml.ws.Service; provides the client view of a web service
    //
    // wsdl:portType = Service endpoint interface
    //
    // wsdl:operation = Java method in the corresponding SEI
    //
    // wsdl:input = Wrapper- or nonwrapper-style Java method parameters
    //
    // wsdl:output = Wrapper- or nonwrapper-style Java method return value
    //
    // wsdl:fault = Service-specific exception
    //
    // XML schema elements in wsdl:types = As defined by XML-to-Java mapping in the JAXB specification
    //
    //
    // wsimport -keep -verbose http://localhost:8080/example/SimpleWebService?wsdl
    //
    // class in the package br.com.fernando.chapter05_soapBasedws.part04_webServiceClient.client
    public static void main(String[] args) {
        // ----------------------------------------------------------------------------------------
        // Server
        final Endpoint endpoint = Endpoint.publish("http://localhost:8080/example/SimpleWebService", new SimpleWebService());

        // ----------------------------------------------------------------------------------------
        // Client 01
        // A client will then invoke a business method on the web service:
        final DifferentWebService_Service service01 = new DifferentWebService_Service();
        final DifferentWebService port01 = service01.getDifferentPort();
        System.out.println(port01.sayHello("Luiz"));

        // ----------------------------------------------------------------------------------------
        // Client 02
        // A more generic getPort method may be used to obtain the endpoint:
        final DifferentWebService port02 = service01.getPort(DifferentWebService.class);
        System.out.println(port02.sayHello("Zig"));

        // Each generated proxy implements the BindingProvider interface.
        // Typically, a generated client has an endpoint address preconfigured based upon the value of the soap:address element in the WSDL. 
        // BindingProvider propertie
        // 
        // ----------------------------------------------------------------------------------------
        // Stop server
        endpoint.stop();
    }
}
