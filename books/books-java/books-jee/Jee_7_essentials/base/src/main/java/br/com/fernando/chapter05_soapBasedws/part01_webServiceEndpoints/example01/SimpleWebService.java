package br.com.fernando.chapter05_soapBasedws.part01_webServiceEndpoints.example01;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

// You can convert a POJO to a SOAP-based web service endpoint by adding the @WebService annotation.
//
// The @WebService annotation has several attributes to override the defaults, as defined:
@WebService( //
        // endpointInterface: Fully qualified class name of the service endpoint interface defining the service's abstract web service contract
        endpointInterface = "br.com.fernando.chapter05_soapBasedws.part01_webServiceEndpoints.example01.SimpleEndpointInterface", //
        // name: Name of the web service ( wsdl:portType )
        name = "SimpleWebService", //
        // portName: Port name of the web service ( wsdl:port )
        portName = "SimplePort", //
        // serviceName: Service name of the web service ( wsdl:service )
        serviceName = "SimpleWebService", //
        // targetNamespace: Namespace for the web service ( targetNamespace )
        targetNamespace = "http://simplewebservice.com.br"
// wsdlLocation: Location of a predefined WSDL describing the service
// wsdlLocation = "classpath:wsdl/myservice.wsdl" //

)
public class SimpleWebService implements SimpleEndpointInterface {

    // All public methods of the class are exposed as web service operations.
    @Override
    @WebMethod(operationName = "hello")
    // You can customize the mapping of an individual parameter of a method to WSDL using @WebParam , and the mapping of the return value using @WebResult
    @WebResult(name = "nameResult")
    public String sayHello(@WebParam(name = "name") final String name) {
        return "Hello " + name;
    }

    // If there are multiple methods in the POJO and a particular method needs to be excluded
    // from the web service description, the exclude attribute can be used:
    @Override
    @WebMethod(exclude = true)
    public String sayHelloAgain(final String name) {
        return "Hello " + name;
    }

    // This is called a Service Endpoint Interface (SEI)–based endpoint.
    // Even though the name contains the word interface, an interface is not required for building a JAX-WS endpoint.
    //
    // The web service implementation class implicitly defines an SEI.
    // This approach of starting with a POJO is also called the code-first approach.
    //
    // The other approach—in which you start with a WSDL and generate Java classes from it—is called the contract-first approach.
    //
    // You can customize the mapping of an individual parameter of a method to WSDL using @WebParam , and the mapping of the return value using @WebResult .
}
