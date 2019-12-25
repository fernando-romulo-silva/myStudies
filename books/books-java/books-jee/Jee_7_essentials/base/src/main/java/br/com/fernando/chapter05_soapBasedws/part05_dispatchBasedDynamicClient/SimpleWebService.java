package br.com.fernando.chapter05_soapBasedws.part05_dispatchBasedDynamicClient;

import javax.jws.WebService;

@WebService( //
        name = "NewDifferentWebService", //
        portName = "NewDifferentPort", //
        serviceName = "NewDifferentWebService", //
        targetNamespace = "http://newdifferentwebservice.com.br")
public class SimpleWebService implements SimpleWebServiceInterface {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
