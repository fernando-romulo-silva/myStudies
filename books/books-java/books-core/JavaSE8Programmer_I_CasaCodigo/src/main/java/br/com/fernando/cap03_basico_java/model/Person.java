package br.com.fernando.cap03_basico_java.model;

import java.util.Date;
// import java.sql.Date; // compilation error

public class Person { // anyone can reference me!

    Address address; // Person using an address

    // Person referencing the type
    // in the same package
    
    private Date d; // no error
}
