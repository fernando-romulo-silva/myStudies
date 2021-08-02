package br.com.fernando.chapter05_soapBasedws.part03_endpoint_based;

import javax.jws.WebService;

@WebService
public interface SimpleWebServiceInterface {

    public String sayHello(final String name);
}
