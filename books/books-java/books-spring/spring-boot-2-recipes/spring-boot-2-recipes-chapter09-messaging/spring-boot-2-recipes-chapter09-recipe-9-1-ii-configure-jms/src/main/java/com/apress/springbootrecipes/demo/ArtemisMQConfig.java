package com.apress.springbootrecipes.demo;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

//@Configuration
public class ArtemisMQConfig {

//    @Value("${spring.artemis.host}")
    private String brokerUrl;

//    @Bean
    public ConnectionFactory connectionFactory() throws JMSException {
	final var connectionFactory = new ActiveMQConnectionFactory();
	connectionFactory.setBrokerURL(brokerUrl);
	return connectionFactory;
    }

//    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
	final var jmsTemplate = new JmsTemplate();
	jmsTemplate.setConnectionFactory(connectionFactory());
	jmsTemplate.setPubSubDomain(true); // enable for Pub Sub to topic. Not Required for Queue.
	return jmsTemplate;
    }

//    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws JMSException {
	final var factory = new DefaultJmsListenerContainerFactory();
	factory.setConnectionFactory(connectionFactory());
	factory.setPubSubDomain(true);
	return factory;
    }
}
