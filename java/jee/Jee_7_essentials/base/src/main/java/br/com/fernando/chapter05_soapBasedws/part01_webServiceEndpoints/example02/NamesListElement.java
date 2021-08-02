package br.com.fernando.chapter05_soapBasedws.part01_webServiceEndpoints.example02;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NamesListElement {

    @XmlElement
    private List<String> elements;

    NamesListElement() {
        super();
    }

    public NamesListElement(final List<String> elements) {
        super();
        this.elements = elements;
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
