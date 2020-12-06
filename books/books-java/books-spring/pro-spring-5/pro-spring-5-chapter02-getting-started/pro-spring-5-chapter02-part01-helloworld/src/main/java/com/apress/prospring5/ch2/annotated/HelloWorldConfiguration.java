package com.apress.prospring5.ch2.annotated;

import com.apress.prospring5.ch2.decoupled.HelloWorldMessageProvider;
import com.apress.prospring5.ch2.decoupled.MessageProvider;
import com.apress.prospring5.ch2.decoupled.MessageRenderer;
import com.apress.prospring5.ch2.decoupled.StandardOutMessageRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by iuliana.cosmina on 1/28/17.
 */
@Configuration
public class HelloWorldConfiguration {

    // A configuration class can be used to read the annotated beans definitions as well. 
    // In this case, because the bean’s definition configuration is part of the bean class, the class will no longer need any @Bean annotated methods. 
    // But, to be able to look for bean definitions inside Java classes,
    
    @Bean
    public MessageProvider provider() {
	return new HelloWorldMessageProvider();
    }

    @Bean
    public MessageRenderer renderer() {
	MessageRenderer renderer = new StandardOutMessageRenderer();
	renderer.setMessageProvider(provider());
	return renderer;
    }
}
