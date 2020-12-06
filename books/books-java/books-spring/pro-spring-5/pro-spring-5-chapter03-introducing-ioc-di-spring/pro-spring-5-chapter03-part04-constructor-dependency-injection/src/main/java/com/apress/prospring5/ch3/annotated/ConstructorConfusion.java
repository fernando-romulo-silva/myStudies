package com.apress.prospring5.ch3.annotated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service("constructorConfusion")
public class ConstructorConfusion {

    private String someValue;

    // hard-coding the value in the code is not a good idea; to change it, we would need to recompile the program.
    // Even if you choose annotation-style DI, a good practice is to externalize those values for injection.
    // To externalize the message, let’s define the message as a Spring bean in the annotation configuration file
    public ConstructorConfusion(@Value("Configurable message") String someValue) { // don't do that
	System.out.println("ConstructorConfusion(String) called");
	this.someValue = someValue;
    }

    // By applying the @Autowired annotation to the desired constructor method, Spring will use that method to instantiate the bean and inject the value as specified.
    // As before, we should externalize the value from the configuration.

    @Autowired
    public ConstructorConfusion(@Value("90") int someValue) {
	System.out.println("ConstructorConfusion(int) called");
	this.someValue = "Number: " + Integer.toString(someValue);
    }

    public String toString() {
	return someValue;
    }

    public static void main(String... args) {
	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	ctx.load("classpath:spring/app-context-annotation.xml");
	ctx.refresh();

	ConstructorConfusion cc = (ConstructorConfusion) ctx.getBean("constructorConfusion");
	System.out.println(cc);
	ctx.close();
    }
}
