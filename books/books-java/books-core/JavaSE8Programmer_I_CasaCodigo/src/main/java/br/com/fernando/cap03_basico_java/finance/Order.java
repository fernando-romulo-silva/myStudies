package br.com.fernando.cap03_basico_java.finance;

import java.util.Date;

import br.com.fernando.cap03_basico_java.order.Product;

public class Order {

    br.com.fernando.cap03_basico_java.model.Person client; // Referencing the type Person from another package (full reference name)

    Product product; // imported reference

    Date creationDate; // imported reference
}
