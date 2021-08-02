package com.apress.springbootrecipes.library.rest;

import static org.springframework.http.ResponseEntity.notFound;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.apress.springbootrecipes.library.Book;
import com.apress.springbootrecipes.library.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
	this.bookService = bookService;
    }

    @GetMapping
    public Iterable<Book> all() {
	return bookService.findAll();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> get(@PathVariable("isbn") String isbn) {
	return bookService.find(isbn) //
			.map(ResponseEntity::ok) //
			.orElse(notFound().build()); 
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book, UriComponentsBuilder uriBuilder) {
	final var created = bookService.create(book);
	
	final var newBookUri = uriBuilder.path("/books/{isbn}").build(created.getIsbn());
	
	return ResponseEntity.created(newBookUri).body(created);
    }
}
