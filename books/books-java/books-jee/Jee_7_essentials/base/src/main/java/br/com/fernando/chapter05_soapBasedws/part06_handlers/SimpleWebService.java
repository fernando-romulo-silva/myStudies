package br.com.fernando.chapter05_soapBasedws.part06_handlers;

import javax.jws.HandlerChain;
import javax.jws.WebService;

@WebService
@HandlerChain(file = "handler-chain.xml")
// Make sure that the "handler-chain.xml" file is placed just beside the compiled webservice class
// Example: "DemoHandlerService.war/WEB-INF/classes/ws" directory.
public class SimpleWebService implements SimpleWebServiceInterface {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
