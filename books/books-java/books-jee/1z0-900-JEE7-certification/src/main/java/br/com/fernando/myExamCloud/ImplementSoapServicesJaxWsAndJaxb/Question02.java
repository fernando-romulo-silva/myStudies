package br.com.fernando.myExamCloud.ImplementSoapServicesJaxWsAndJaxb;

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

public class Question02 {

    @XmlRootElement(name = "employee")
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "name", "address", "mobileNumber" })
    public static class Employee implements Serializable {
	
	private static final long serialVersionUID = 1L;

	String name;

	String mobileNumber;

	String address;

	public Employee(String name, String address, String mobileNumber) {
	    super();
	    this.name = name;
	    this.address = address;
	    this.mobileNumber = mobileNumber;
	}
    }

    // Assume that you have defined Employee class with valid JAXB marshal annotations.
    //
    // Employee customer = new Employee();
    // customer.setName("Joe Pop");
    // customer.setAddress(“919 Smart Street Phase I, Gandhi Nagar, Chennai”);
    // customer.setMobileNumber(“9000000”);
    //
    // Which of the following code initiate JAXB marshaller?
    //
    // Choice A
    // JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
    // Marshaller jaxbMarshaller = jaxbContext.initMarshaller();
    //
    // Choice B
    // JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
    // Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    //
    // Choice C
    // JAXBContext jaxbContext = JAXBContext.create(Customer.class);
    // Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // Choice B is correct.
    //
    // The class javax.xml.bind.JAXBContext provides a framework for validating, marshaling and un-marshaling XML into (and from)
    // Java objects and it is the entry point to the JAXB API.

    public static void test01() throws JAXBException {

	Employee customer = new Employee("Joe Pop", "919 Smart Street Phase I, Gandhi Nagar, Chennai", "9000000");
	// Choice B
	JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
	Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

	jaxbMarshaller.marshal(jaxbContext, new File(""));

    }
}
