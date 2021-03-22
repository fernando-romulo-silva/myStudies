package com.apress.prospring5.ch18;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.apress.prospring5.ch18.config.BatchConfig;
import com.apress.prospring5.ch18.config.IntegrationConfig;

public class FileWatcherDemo {

    private static Logger logger = LoggerFactory.getLogger(FileWatcherDemo.class);

    public static void main(String... args) throws Exception {
//	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:spring/integration-config.xml");
	GenericApplicationContext ctx = new AnnotationConfigApplicationContext(IntegrationConfig.class);
	
	assert (ctx != null);
	logger.info("Application started...");
	System.in.read();
	ctx.close();
    }
}
