package br.com.fernando.chapter05_soapBasedws.part05_dispatchBasedDynamicClient;

import javax.jws.WebService;

@WebService
public interface SimpleWebServiceInterface {

    public String sayHello(final String name);
}
