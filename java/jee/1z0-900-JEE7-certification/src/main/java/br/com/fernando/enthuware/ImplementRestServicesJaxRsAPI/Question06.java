package br.com.fernando.enthuware.ImplementRestServicesJaxRsAPI;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

public class Question06 {

    // Given the code fragment:
    public static void test01() throws Exception {

	/* 1. */ JAXBContext jo = JAXBContext.newInstance("com.xyz.foo");
	/* 2. */ Unmarshaller u = jo.createUnmarshaller();
	/*  */
	/* 3. */ // INSERT CODE HERE
	/*  */
	/* 4. */ InputStream in = new FileInputStream("fooStuft.xml");
	/* 5. */ Object o = u.unmarshal(in);
    }

    // Which method should be used on line 3 to enable default validation mechanism?
    //
    // A - u.setProperty(String, Object)
    //
    // B - u.setProperty(Schema)
    //
    // C - u.setEventHandler(validationEventHandler)
    //
    // D - u.setAdapter(xmlAdapter)
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
    //
    //
    //
    //
    // The correct answer is C
    //
    // unmarshaller.setEventHandler(new DefaultValidationEventHandler());
    //
    // The ValidationEventHandler will be called by the JAXB Provider if any validation errors are encountered during calls to any of the unmarshal methods.
    // If the client application does not register a ValidationEventHandler before invoking the unmarshal methods, then ValidationEvents will be
    // handled by the default event handler which will terminate the unmarshal operation after the first error or fatal error is encountered.
    public static void test02() throws Exception {

	JAXBContext jo = JAXBContext.newInstance("com.xyz.foo");
	Unmarshaller u = jo.createUnmarshaller();

	u.setEventHandler(new DefaultValidationEventHandler());

	InputStream in = new FileInputStream("fooStuft.xml");
	Object o = u.unmarshal(in);
    }
}
