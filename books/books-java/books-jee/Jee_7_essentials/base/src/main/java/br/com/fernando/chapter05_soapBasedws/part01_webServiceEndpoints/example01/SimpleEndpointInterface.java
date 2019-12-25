package br.com.fernando.chapter05_soapBasedws.part01_webServiceEndpoints.example01;

import javax.jws.WebService;

@WebService(targetNamespace = "http://simplewebservice.com.br")
public interface SimpleEndpointInterface {

    public String sayHello(final String name);

    public String sayHelloAgain(final String name);
}
