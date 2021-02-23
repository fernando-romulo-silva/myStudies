package com.apress.prospring5.ch12;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.apress.prospring5.ch12.config.AppConfig;

public class JmsHornetQDemo {
    public static void main(String... args) throws Exception {
	GenericApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

	MessageSender messageSender = ctx.getBean("messageSender", MessageSender.class);
	System.out.println(Arrays.toString(ctx.getBeanDefinitionNames()));

	for (int i = 0; i < 10; ++i) {
	    messageSender.sendMessage("Test message: " + i);
	}

	System.in.read();
	ctx.close();
    }
}
