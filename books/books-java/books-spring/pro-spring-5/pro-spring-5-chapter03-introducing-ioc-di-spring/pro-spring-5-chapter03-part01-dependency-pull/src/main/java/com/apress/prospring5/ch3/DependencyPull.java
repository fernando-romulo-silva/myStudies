package com.apress.prospring5.ch3;

import com.apress.prospring5.ch2.decoupled.MessageRenderer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DependencyPull {
    public static void main(String... args) {

	// The following code sample shows a typical dependency pull lookup in a Spring-based application:

	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/app-context.xml");

	MessageRenderer mr = ctx.getBean("renderer", MessageRenderer.class);
	mr.render();
    }
}
