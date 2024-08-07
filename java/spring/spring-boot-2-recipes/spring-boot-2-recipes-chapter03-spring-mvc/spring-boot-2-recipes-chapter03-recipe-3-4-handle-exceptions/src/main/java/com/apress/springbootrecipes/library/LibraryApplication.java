package com.apress.springbootrecipes.library;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LibraryApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
	SpringApplication.run(LibraryApplication.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
	registry.addViewController("/").setViewName("index");
    }

    @Bean
    public ApplicationRunner booksInitializer(BookService bookService) {
	return args -> {
	    bookService.create(new Book("9780061120084", "To Kill a Mockingbird", "Harper Lee"));
	    bookService.create(new Book("9780451524935", "1984", "George Orwell"));
	    bookService.create(new Book("9780618260300", "The Hobbit", "J.R.R. Tolkien"));
	};
    }

    @Bean
    public CustomizedErrorAttributes errorAttributes() {
	return new CustomizedErrorAttributes();
    }
}
