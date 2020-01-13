package br.com.fernando.chapter04_creatingJavaMicroservices.part04_makingCalls;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.fernando.chapter04_creatingJavaMicroservices.part03_codingSpringBoot.domain.Book;

public class Part01_RestTemplate {

    // If you need to call a service from another service, you will need a HTTP client.
    // Spring provides the very useful RestTemplate class.
    // It gives you a synchronous client-side HTTP access, simplifies communication with HTTP servers, and enforces RESTful principles

    public static void main(String[] args) {
	try {
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Book> response = restTemplate.getForEntity("http://localhost:8080/books/1", Book.class);
	    System.out.println(response.getBody());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
