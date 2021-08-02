package com.apress.springbootrecipes.order.web;

import com.apress.springbootrecipes.order.Order;
import com.apress.springbootrecipes.order.OrderService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

@WebFluxTest(OrderController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderControllerSliceWithSpyTest {

  @Autowired
  private WebTestClient webTestClient;

  @SpyBean
  private OrderService orderService;

  @Test
  public void listOrders() {

      webTestClient.get().uri("/orders") //
		      .exchange() //
		      .expectStatus().isOk() //
		      .expectBodyList(Order.class).hasSize(25); //
  }

  @Test
  public void addAndGetOrder() {
    var order = new Order("test1", BigDecimal.valueOf(1234.56));

    webTestClient.post().uri("/orders").bodyValue(order) // 
            .exchange()                                  // 
              .expectStatus().isOk()                     // 
              .expectBody(Order.class).isEqualTo(order); // 

    webTestClient.get().uri("/orders/{id}", order.getId()) // 
            .exchange()                                    // 
            .expectStatus().isOk()                         // 
            .expectBody(Order.class).isEqualTo(order);     // 
  }
}
