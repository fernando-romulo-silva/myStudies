package br.com.fernando.chapter05_soapBasedws.part04_webServiceClient;

import javax.jws.WebService;

@WebService( //
        name = "DifferentWebService", //
        portName = "DifferentPort", //
        serviceName = "DifferentWebService", //
        targetNamespace = "http://differentwebservice.com.br")
public class SimpleWebService implements SimpleWebServiceInterface {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
