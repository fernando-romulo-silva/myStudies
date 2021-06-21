package com.apress.springbootrecipes.demo;

import java.time.LocalDateTime;
import java.util.Collections;

import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

/**
 * This test will setup a JNID context before starting the Spring Boot application, with this you can test the JNDI setup quite easily. However you might want to use an embedded broker instead of
 * pre-configuring JNDI for your test.
 *
 * If you need this configuration in multiple tests you might want to place this in a {@code TestExecutionListener} or as part of your configuration.
 */
@SpringBootTest
public class JmsJndiApplicationTest {

    private static SimpleNamingContextBuilder builder;

    @Autowired
    private JmsTemplate jmsTemplate;

    @BeforeAll
    public static void setupJndi() throws NamingException {
	// Initialize a Mock JNDI context for testing
	builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();

	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");

	// When sending Java Objects we need to set the packages that are allowed so be serialized
	// Generally using JSON or XML to transferred objects should be preferred.
	connectionFactory.setTrustedPackages(Collections.singletonList("java.time"));

	builder.bind("java:/jms/ConnectionFactory", connectionFactory);
	builder.bind("current-time", new ActiveMQQueue("current-time"));
    }

    @AfterAll
    public static void cleanUp() {
	builder.clear();
	builder.deactivate();
	builder = null;
    }

    @Test
    public void start() {

	jmsTemplate.convertAndSend("current-time", LocalDateTime.now());

    }

}
