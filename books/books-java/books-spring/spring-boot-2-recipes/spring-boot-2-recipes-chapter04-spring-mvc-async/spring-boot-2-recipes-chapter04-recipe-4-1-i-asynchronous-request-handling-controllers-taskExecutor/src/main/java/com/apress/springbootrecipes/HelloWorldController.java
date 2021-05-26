package com.apress.springbootrecipes;

import java.util.concurrent.Callable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping
    public Callable<String> hello() {
	return () -> {
	    Thread.sleep(5000);
	    return "Hello World, from Spring Boot 2!";
	};
    }
}
