package br.com.fernando.chapter05_soapBasedws.part01_webServiceEndpoints.example02;

import java.util.List;

import javax.jws.Oneway;
import javax.jws.WebService;

@WebService
public interface ShoppingCartEndpointInterface {

    void purchase(final List<Item> items);

    Item createItem(String name) throws InvalidNameException;

    @Oneway
    void doSomething();

}
