package br.com.fernando.chapter05_soapBasedws.part01_webServiceEndpoints.example02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

// In this code, @XmlRootElement allows the Item class to be converted to XML and vice versa.
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    private String name;

    Item() {
        super();
    }

    public Item(final String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
