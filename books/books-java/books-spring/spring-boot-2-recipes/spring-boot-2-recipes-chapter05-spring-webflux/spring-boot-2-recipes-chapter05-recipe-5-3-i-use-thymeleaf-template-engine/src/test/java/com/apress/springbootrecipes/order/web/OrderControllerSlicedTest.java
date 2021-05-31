package com.apress.springbootrecipes.order.web;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.apress.springbootrecipes.order.OrderGenerator;
import com.apress.springbootrecipes.order.OrderService;

import reactor.core.publisher.Flux;

@WebFluxTest(OrderController.class)
public class OrderControllerSlicedTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private OrderService orderService;

    private OrderGenerator generator = new OrderGenerator();

    @Test
    public void showOrdersIndexPage() {

	when(orderService.orders()).thenReturn(Flux.just(generator.generate(), generator.generate()));

	webTestClient.get().uri("/orders") //
			.exchange() //
			.expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML) //
			.expectStatus().isOk(); //
    }
}
