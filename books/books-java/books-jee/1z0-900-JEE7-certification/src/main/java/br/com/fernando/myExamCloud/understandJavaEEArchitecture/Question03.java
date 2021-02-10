package br.com.fernando.myExamCloud.understandJavaEEArchitecture;

import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceRef;

public class Question03 {

    // How can you inject a target web service into an EJB?
    //
    // Choice A
    // Define service as an injectable resource by using the <resource-ref> declaration.
    //
    // Choice B
    // Use a HandlerChain.
    //
    // Choice C
    // Use a java.xml.ws.WebServiceRef annotation.
    //
    // Choice D
    // Use a java.xml.ws.WebServiceContext annotation.
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
    // Choice C is correct.
    //
    // The @WebServiceRef annotation is used to declare a reference to a web service.
    // Use the @WebServiceRef annotation to inject a reference to the service you want to invoke.

    @Stateless
    public class EbjService {

	@WebServiceRef(wsdlLocation = "META-INF/wsdl/AnyService/Any.wsdl")
	private HelloMessengerService service;

    }

    @WebService
    @HandlerChain(file = "handler-chain.xml")
    public interface HelloMessengerService {

    }

}
