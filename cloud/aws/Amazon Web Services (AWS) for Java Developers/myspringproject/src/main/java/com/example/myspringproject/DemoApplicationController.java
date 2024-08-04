package com.example.myspringproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoApplicationController {

    @GetMapping("/")
    public String index() {
        return "This is our index page";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the course";
    }
}
