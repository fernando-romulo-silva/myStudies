package com.apress.springbootrecipes.demo;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMQConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Bean
    public ConnectionFactory connectionFactory() {
	final var activeMQConnectionFactory = new ActiveMQConnectionFactory();
	activeMQConnectionFactory.setBrokerURL(brokerUrl);
	return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
	final var jmsTemplate = new JmsTemplate();
	jmsTemplate.setConnectionFactory(connectionFactory());
	jmsTemplate.setPubSubDomain(true); // enable for Pub Sub to topic. Not Required for Queue.
	return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
	final var factory = new DefaultJmsListenerContainerFactory();
	factory.setConnectionFactory(connectionFactory());
	factory.setPubSubDomain(true);
	return factory;
    }
}
