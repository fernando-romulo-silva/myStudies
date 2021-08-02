package com.apress.springbootrecipes.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void foo() {
	webTestClient.get().uri("/orders").exchange().expectStatus().isOk();
    }

}
