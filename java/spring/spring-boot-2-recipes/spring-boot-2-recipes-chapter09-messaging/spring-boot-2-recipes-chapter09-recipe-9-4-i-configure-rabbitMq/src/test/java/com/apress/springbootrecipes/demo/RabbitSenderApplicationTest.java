package com.apress.springbootrecipes.demo;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class RabbitSenderApplicationTest {

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    public void shouldSendAtLeastASingleMessage() {

	verify(rabbitTemplate, Mockito.atLeastOnce()).convertAndSend("hello", "Hello World, from Spring Boot 2, over RabbitMQ!");

    }

}
