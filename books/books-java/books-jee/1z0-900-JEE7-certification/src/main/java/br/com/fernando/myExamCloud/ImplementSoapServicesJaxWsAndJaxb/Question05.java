package br.com.fernando.myExamCloud.ImplementSoapServicesJaxWsAndJaxb;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

public class Question05 {

    // Which statement is true about Java methods that are exposed as Web Service operations by using JAX-WS API?
    //
    //
    // Choice A
    // The @WebResult annotation must exist.
    //
    // Choice B
    // Method parameters and return types must be JAXB compatible.
    //
    // Choice C
    // Method parameters must be declared by using @WebParam.
    //
    // Choice D
    // The @WebMethod annotation must exist.
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
    //
    // Choice B is correct

    @WebService(targetNamespace = "http://simplewebservice.com.br")
    public interface SimpleEndpointInterface {
	String getHello();
    }

    @WebService( //
	        // endpointInterface: Fully qualified class name of the service endpoint interface defining the service's abstract web service contract
	        endpointInterface = "br.com.fernando.myExamCloud.ImplementSoapServicesJaxWsAndJaxb.Question06#SimpleEndpointInterface", //
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
	
	@WebMethod
	@WebResult(name = "hellomessage")
	public String getHello() {
	    return "bla bla bla";
	}
	
	// If I dont have @WebResult I see the following xml:
	// <ns2:getHelloResponse>
	//     <return>hello fff</return>
	// </ns2:getHelloResponse>
	//
	//
	// with @WebResult:
	// <ns2:getHelloResponse>
	//    <hellomessage>hello fff</hellomessage>
	// </ns2:getHelloResponse>	
    }

    //
    // There are two APIs available in Java EE 7 to handle SOAP based and REST based web services.
    //
    // * JAX-RS for REST based web services.
    // * JAX-WS for SOAP based webservices.
    //
    // Requirements of a JAX-WS Endpoint
    //
    // JAX-WS endpoints must follow these requirements:
    //
    // * The implementing class must be annotated with either the javax.jws.WebService or the javax.jws.WebServiceProvider annotation.
    //
    // * The implementing class may explicitly reference an SEI through the endpointInterface element of the @WebService annotation but is not required to do so.
    // If no endpointInterface is specified in @WebService, an SEI is implicitly defined for the implementing class.
    //
    // * The business methods of the implementing class must be public and must not be declared static or final.
    //
    //
    // A valid endpoint implementation class must meet the following requirements:
    //
    // 1. It MUST carry a javax.jws.WebService annotation (see JSR 181).
    // 2. Any of its methods MAY carry a javax.jws.WebMethod annotation.
    // 3. All of its methods MAY throw java.rmi.RemoteException in addition to any service-specific exceptions.
    // 4. All method parameters and return types MUST be compatible with the JAXB 2.0 Java to XML Schema mapping definition.
    // 5. A method parameter or return value type MUST not implement the java.rmi.Remote interface either directly or indirectly.
    // 
    //
    //
    // Business methods that are exposed to web service clients MAY be annotated with javax.jws.WebMethod.
    //
    // Business methods that are exposed to web service clients MUST have JAXB-compatible parameters and return types. 
    // See the two tables of JAXB default data type bindings in Types Supported by JAX-WS.
    //
    // * The implementing class must not be declared final and must not be abstract.
    // * The implementing class must have a default public constructor.
    // * The implementing class must not define the finalize method.
    // * The implementing class may use the javax.annotation.PostConstruct or the javax.annotation.PreDestroy annotations on its methods for lifecycle event callbacks.
    // * The @PostConstruct method is called by the container before the implementing class begins responding to web service clients.
    // * The @PreDestroy method is called by the container before the endpoint is removed from operation.
}
