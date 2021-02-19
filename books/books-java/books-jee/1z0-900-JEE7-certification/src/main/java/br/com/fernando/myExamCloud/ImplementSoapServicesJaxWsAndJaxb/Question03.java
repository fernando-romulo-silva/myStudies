package br.com.fernando.myExamCloud.ImplementSoapServicesJaxWsAndJaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class Question03 {

    // Given in the below code snippet
    //
    @XmlType(propOrder = { "name", "address", "mobileNumber" })
    // Code here
    public class Customer {

	String name;
	String address;
	String mobileNumber;

	@XmlElement(name = "Customer_Name", required = true)
	public void setName(String name) {
	    this.name = name;
	}

	@XmlElement(name = "Customer_Address")
	public void setAddress(String address) {
	    this.address = address;
	}

	@XmlAttribute(name = "mobileNumber", required = true)
	public void setMobileNumber(String mobileNumber) {
	    this.mobileNumber = mobileNumber;
	}
    }

    // Which of the following JAXB marshal annotation convert Employee object as valid XML?
    //
    // Choice A - @XmlElement(name = "Employee")
    //
    // Choice B - @XmlRootElement(name = "Employee")
    //
    // Choice C - @XmlMainElement(name = "Employee")
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
    // The process of converting Java Objects into XML files are marshalling.
    // The JAXB @XmlRootElement annotation denotes as root element and it is defined just before class starts.
    //
    // @XmlElement in combination with setter methods.
    //
    // @XmlAttribute to pass attributes to the XML nodes. These attributes can have properties like to be required or not.
    //
    // @XmlType to indicate special options like to order of appearance in the XML.
}
