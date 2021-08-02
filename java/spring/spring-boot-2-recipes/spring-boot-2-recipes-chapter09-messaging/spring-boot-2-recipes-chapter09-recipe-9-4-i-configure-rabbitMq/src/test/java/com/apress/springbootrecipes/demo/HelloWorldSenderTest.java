package com.apress.springbootrecipes.demo;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class HelloWorldSenderTest {

    @Test
    public void shouldSendMessageWhenMethodCalled() {
	RabbitTemplate rabbitTemplate = Mockito.mock(RabbitTemplate.class);
	HelloWorldSender sender = new HelloWorldSender(rabbitTemplate);

	sender.sendTime();

	verify(rabbitTemplate, Mockito.atLeastOnce()).convertAndSend("hello", "Hello World, from Spring Boot 2, over RabbitMQ!");
    }
}
