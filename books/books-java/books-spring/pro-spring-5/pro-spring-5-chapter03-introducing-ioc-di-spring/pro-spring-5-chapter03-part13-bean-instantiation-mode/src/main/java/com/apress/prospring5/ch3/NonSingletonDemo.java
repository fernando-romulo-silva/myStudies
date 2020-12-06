package com.apress.prospring5.ch3;

import com.apress.prospring5.ch3.annotated.Singer;
import org.springframework.context.support.GenericXmlApplicationContext;

public class NonSingletonDemo {
    // By default, all beans in Spring are singletons.
    // This means Spring maintains a single instance of the bean, all dependent objects use the same instance,
    // and all calls to ApplicationContext.getBean() return the same instance and Spring uses the same instances to fulfill all requests for that bean.
    public static void main(String... args) {
	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	// ctx.load("classpath:spring/app-context-xml.xml");
	ctx.load("classpath:spring/app-context-annotated.xml");
	ctx.refresh();

	Singer singer1 = ctx.getBean("nonSingleton", Singer.class);
	Singer singer2 = ctx.getBean("nonSingleton", Singer.class);

	System.out.println("Identity Equal?: " + (singer1 == singer2));
	System.out.println("Value Equal:? " + singer1.equals(singer2));
	System.out.println(singer1);
	System.out.println(singer2);

	ctx.close();
    }
}
