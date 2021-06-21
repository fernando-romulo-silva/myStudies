package com.apress.springbootrecipes.demo;

import static org.assertj.core.api.Assertions.assertThat;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;

@SpringBootTest
public class JmsSenderApplicationTest {

    @Autowired
    private JmsTemplate jms;

    @Test
    public void shouldSendMessage() throws JMSException {

	final var message = jms.receive("time-queue");

	assertThat(message).isInstanceOf(TextMessage.class);
	assertThat(((TextMessage) message).getText()).startsWith("Current Date & Time is: ");

    }
}
