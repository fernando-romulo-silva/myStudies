package com.apress.springbootrecipes.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HelloWorld {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

//    @Scheduled(fixedRate = 4000L)
    public void printMessage() {
	logger.info("Hello World, from Spring Boot 2!");
    }
}