package com.apress.prospring5.ch3.xml;

import java.util.Arrays;

import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Created by iuliana.cosmina on 2/19/17.
 */
public class BeanCrazyNaming {

    public static void main(String... args) {
	
	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	ctx.load("classpath:spring/app-context-03.xml");
	ctx.refresh();
	
	final var beans = ctx.getBeansOfType(String.class);
	
	beans.entrySet().stream().forEach(b -> System.out.println("id: " + b.getKey() + "\n aliases: " + Arrays.toString(ctx.getAliases(b.getKey())) + "\n"));
	ctx.close();
    }
}
