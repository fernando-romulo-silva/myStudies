package br.com.fernando.chapter05_soapBasedws.part03_endpoint_based;

import javax.jws.WebService;

@WebService
public class SimpleWebService implements SimpleWebServiceInterface {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
