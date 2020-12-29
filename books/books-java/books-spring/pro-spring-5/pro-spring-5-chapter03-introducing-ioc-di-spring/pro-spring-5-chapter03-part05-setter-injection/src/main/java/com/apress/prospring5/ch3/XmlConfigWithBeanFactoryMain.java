package com.apress.prospring5.ch3;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

public class XmlConfigWithBeanFactoryMain {

    // In the previous code sample, you can see that we are using DefaultListableBeanFactory, which is one of the two main BeanFactory implementations 
    // supplied with Spring, and that we are reading in the BeanDefinition information from an XML file by using XmlBeanDefinitionReader. 
    //
    // Once the BeanFactory implementation is created and configured, we retrieve the oracle bean by using its name, oracle, 
    // which is configured in the XML configuration file.
    
    public static void main(String... args) {
	DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

	// Spring also provides PropertiesBeanDefinitionReader, which allows you to manage your bean configuration 
	// by using properties rather than XML.  
	XmlBeanDefinitionReader rdr = new XmlBeanDefinitionReader(factory);
	
	rdr.loadBeanDefinitions(new ClassPathResource("spring/xml-bean-factory-config.xml"));
	Oracle oracle = (Oracle) factory.getBean("oracle");
	System.out.println(oracle.defineMeaningOfLife());
    }
}
