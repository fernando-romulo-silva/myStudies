package com.apress.springbootrecipes.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class RabbitSenderApplicationTest {

  @MockBean
  private RabbitTemplate rabbitTemplate;

  @Test
  public void shouldSendAtLeastASingleMessage() {

    verify(rabbitTemplate, atLeastOnce())
            .convertAndSend(
                    eq("orders"),
                    eq("new-order"),
                    any(Order.class));
  }
}
