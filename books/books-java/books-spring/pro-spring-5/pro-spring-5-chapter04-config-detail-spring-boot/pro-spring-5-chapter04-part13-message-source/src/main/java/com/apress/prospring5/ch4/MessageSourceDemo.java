package com.apress.prospring5.ch4;

import java.util.Locale;

import org.springframework.context.support.GenericXmlApplicationContext;

public class MessageSourceDemo {
    public static void main(String... args) {
	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	ctx.load("classpath:spring/app-context-xml.xml");
	ctx.refresh();

	Locale english = Locale.ENGLISH;
	Locale german = new Locale("de", "DE");

	System.out.println(ctx.getMessage("msg", null, english));
	System.out.println(ctx.getMessage("msg", null, german));

	System.out.println(ctx.getMessage("nameMsg", new Object[] { "John", "Mayer" }, english));
	System.out.println(ctx.getMessage("nameMsg", new Object[] { "John", "Mayer" }, german));

	ctx.close();
	// Now this example just raises even more questions. What do those calls to getMessage() mean? 
	// Why did we use ApplicationContext.getMessage() rather than access the ResourceBundleMessageSource bean directly? 
	
	// The main reason to use ApplicationContext rather than a manually defined MessageSource bean is that Spring does, where possible, 
	// expose ApplicationContext, as a MessageSource, to the view tier.
	//
	// This means when you are using Spring’s JSP tag library, the <spring:message> tag automatically reads messages from ApplicationContext, 
	// and when you are using JSTL, the <fmt:message> tag does the same.
    }
}
