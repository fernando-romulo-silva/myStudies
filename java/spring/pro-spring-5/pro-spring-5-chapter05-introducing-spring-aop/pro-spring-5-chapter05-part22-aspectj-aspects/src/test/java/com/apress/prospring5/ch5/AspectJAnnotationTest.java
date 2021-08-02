package com.apress.prospring5.ch5;

import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Created by iuliana.cosmina on 4/9/17.
 */
public class AspectJAnnotationTest {

    @Test
    public void xmlTest() {
	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	ctx.load("classpath:spring/app-context-xml.xml");
	ctx.refresh();

	MessageWriter writer = new MessageWriter();
	writer.writeMessage();
	writer.foo();
    }

}
