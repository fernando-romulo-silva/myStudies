package com.apress.prospring5.ch4;

import com.apress.prospring5.ch2.decoupled.MessageProvider;
import com.apress.prospring5.ch2.decoupled.MessageRenderer;
import com.apress.prospring5.ch2.decoupled.StandardOutMessageRenderer;
import com.apress.prospring5.ch3.xml.ConfigurableMessageProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class JavaConfigSimpleDemo {

    @Configuration
    static class AppConfig {
	@Bean
	public MessageProvider messageProvider() {
	    return new ConfigurableMessageProvider();
	}

	// The @Bean annotation is equivalent to the <bean> tag, the method name is equivalent to the id attribute within the <bean> tag, and when instantiating
	// the MessageRender bean, setter injection is achieved by calling the corresponding method to get the message provider, which is the same as using
	// the <ref> attribute in the XML configuration.

	@Bean
	public MessageRenderer messageRenderer() {
	    MessageRenderer renderer = new StandardOutMessageRenderer();
	    renderer.setMessageProvider(messageProvider());

	    return renderer;
	}
    }

    public static void main(String... args) {
	ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

	MessageRenderer renderer = ctx.getBean("messageRenderer", MessageRenderer.class);

	renderer.render();
    }
}
