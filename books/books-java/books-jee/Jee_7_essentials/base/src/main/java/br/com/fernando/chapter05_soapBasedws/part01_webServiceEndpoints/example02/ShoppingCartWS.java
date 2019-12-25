package br.com.fernando.chapter05_soapBasedws.part01_webServiceEndpoints.example02;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

@WebService
public class ShoppingCartWS implements ShoppingCartEndpointInterface {

    // A WebServiceContext may be injected in an endpoint implementation class.
    // This provides information about message context (via the getMessageContext method)  and security information (via the getUserPrincipal and isUserInRole methods) 
    // relative to a request being served.
    @Resource
    private WebServiceContext context;

    // The mapping of Java programming language types to and from XML definitions is delegated to JAXB.
    // It follows the default Java-to-XML and XML-to-Java mapping for each method parameter and return type.
    // The usual JAXB annotations can be used to customize the mapping to the generated schema
    @Override
    public void purchase(final List<Item> items) {

        for (final Item item : items) {
            System.out.println("Product: " + item.getName());
        }
    }

    //
    // The business methods can throw a service-specific exception
    @Override
    public Item createItem(String name) throws InvalidNameException {
        // If this exception (checked exception) is thrown in the business method on the server side, it is propagated to the client side.
        //
        // If the exception is declared as an unchecked exception, it is mapped to SOAPFaultException on the client side.
        // The @WebFault annotation may be used to customize the mapping of wsdl:fault in the generated WSDL.
        return new Item(name);
    }

    // By default, a message follows the request/response design pattern where a response is received for each request.
    //
    // A method may follow the fire-and-forget design pattern by specifying the @Oneway annotation on it so that a request can be sent
    // from the message but no response is received.
    //
    // Such a method must have a void return type and must not throw any checked exceptions:
    @Override
    public void doSomething() {
        System.out.println("I'll do");
    }
}
