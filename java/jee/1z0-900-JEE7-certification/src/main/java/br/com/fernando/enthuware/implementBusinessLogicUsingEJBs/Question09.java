package br.com.fernando.enthuware.implementBusinessLogicUsingEJBs;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceRef;

public class Question09 {

    // How can you inject a target web service into an EJB?
    //
    // A - Define service as an injectable resource by using the <resource-ref> declaration.
    //
    // B - Use a HandlerChain.
    //
    // C - Use a java.xml.ws.WebServiceRef annotation.
    //
    // D - Use a java.xml.ws.WebServiceContext annotation.
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
    // The correct answer is C
    //
    // The following example illustrates how a JAX-WS client obtains a reference to a web service endpoint,
    // obtains a port object for the web service endpoint, and invokes a method on that endpoint.

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/AnyService/Any.wsdl")
    public StockQuoteService stockQuoteService;

    // ...
    // StockQuoteProvider sqp = stockQuoteService.getStockQuoteProviderPort();
    // float quotePrice = sqp.getLastTradePrice("ACME");

    @WebService
    @HandlerChain(file = "handler-chain.xml")
    interface StockQuoteService {

    }

}
