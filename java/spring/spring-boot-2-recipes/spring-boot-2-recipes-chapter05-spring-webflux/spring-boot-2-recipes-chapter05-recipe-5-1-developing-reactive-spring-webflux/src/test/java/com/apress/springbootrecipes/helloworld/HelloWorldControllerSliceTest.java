package com.apress.springbootrecipes.helloworld;

import static org.springframework.http.MediaType.TEXT_PLAIN;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(HelloWorldController.class)
public class HelloWorldControllerSliceTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void shouldSayHello() {

	webClient.get().uri("/") //
			.accept(TEXT_PLAIN) //
			.exchange() //
			.expectStatus().isOk() //
			.expectBody(String.class).isEqualTo("Hello World, from Reactive Spring Boot 2!");

    }
}
