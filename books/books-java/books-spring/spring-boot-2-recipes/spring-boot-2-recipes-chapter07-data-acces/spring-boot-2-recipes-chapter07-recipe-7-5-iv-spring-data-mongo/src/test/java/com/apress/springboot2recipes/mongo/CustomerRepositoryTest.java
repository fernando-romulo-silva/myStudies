package com.apress.springboot2recipes.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import reactor.test.StepVerifier;

@DataMongoTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @AfterEach
    public void cleanUp() {
	repository.deleteAll();
    }

    @Test
    public void insertNewCustomer() {

	StepVerifier.create(repository.findAll())//
			.expectNextCount(0) //
			.expectComplete() //
			.verify();

	final var mono = repository.save(new Customer("-1", "T. Testing", "t.testing@test123.tst")); // Mono<Customer>

	final var customerCreated = mono.block();

	assertThat(customerCreated.getId()).isNotEmpty();
	assertThat(customerCreated.getName()).isEqualTo("T. Testing");
	assertThat(customerCreated.getEmail()).isEqualTo("t.testing@test123.tst");

	final var customerResult = repository.findById(customerCreated.getId()).block(); // Mono<Customer>

	assertThat(customerResult.getId()).isEqualTo(customerCreated.getId());
    }
}
