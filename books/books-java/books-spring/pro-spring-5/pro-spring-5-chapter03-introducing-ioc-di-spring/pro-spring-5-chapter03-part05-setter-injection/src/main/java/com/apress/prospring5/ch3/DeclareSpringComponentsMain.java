package com.apress.prospring5.ch3;

import com.apress.prospring5.ch2.decoupled.MessageRenderer;
import org.springframework.context.support.GenericXmlApplicationContext;

public class DeclareSpringComponentsMain {

    // In setter dependency injection, the IoC container injects a component’s dependencies via JavaBean-style setter methods.
    // A component’s setters expose the dependencies the IoC container can manage.
    //
    // An obvious consequence of using setter injection is that an object can be created without its dependencies, and they can be provided later by calling the setter.

    public static void main(String... args) {
	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	ctx.load("classpath:spring/app-context-xml.xml");
	ctx.refresh();
	MessageRenderer messageRenderer = ctx.getBean("renderer", MessageRenderer.class);
	messageRenderer.render();
	ctx.close();
    }
}
