package br.com.fernando.myExamCloud.ImplementSoapServicesJaxWsAndJaxb;

public class Question03 {

    // Assume that you have defined Customer class with valid JAXB marshal annotations.
    //
    // Customer customer = new Customer();
    // customer.setName("Joe Pop");
    // customer.setAddress(“919 Smart Street Phase I, Gandhi Nagar, Chennai”);
    // customer.setMobileNumber(“9000000”);
    //
    // Which of the following code initiate JAXB marshaller?
    //
    // Choice A
    // JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
    // Marshaller jaxbMarshaller = jaxbContext.initMarshaller();
    //
    // Choice B
    // JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
    // Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    //
    // Choice C
    // JAXBContext jaxbContext = JAXBContext.createCustomer.class);
    // Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    //
    //
    // Choice B is correct.
    //
    // The class javax.xml.bind.JAXBContext provides a framework for validating, marshaling and un-marshaling XML into (and from) 
    // Java objects and  it is the entry point to the JAXB API.
}
