package com.apress.prospring5.ch3;

import com.apress.prospring5.ch2.decoupled.MessageProvider;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {

    // Constructor dependency injection occurs when a component’s dependencies are provided to it in its constructor (or constructors).
    // The component declares a constructor or a set of constructors, taking as arguments its dependencies, and the IoC container
    // passes the dependencies to the component when instantiation occurs
    //
    // Constructor injection is particularly useful when you absolutely must have an instance of the dependency class before your component is used. 
    public static void main(String... args) {
	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	ctx.load("classpath:spring/app-context-annotation.xml");
	ctx.refresh();

	MessageProvider messageProvider = ctx.getBean("provider", MessageProvider.class);

	System.out.println(messageProvider.getMessage());
	ctx.close();

    }
}
