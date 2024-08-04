package com.example.elasticbeanstalk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to the course";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
