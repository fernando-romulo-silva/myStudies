package com.apress.springbootrecipes.library.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apress.springbootrecipes.library.Book;
import com.apress.springbootrecipes.library.BookService;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(final BookService bookService) {
	this.bookService = bookService;
    }

    @GetMapping("/books.html")
    public String all(final Model model) {
	model.addAttribute("books", bookService.findAll());
	return "books/list";
    }

    @GetMapping(value = "/books.html", params = "isbn")
    public String get(@RequestParam("isbn") final String isbn, final Model model) {

	bookService.find(isbn) //
		.ifPresent(book -> model.addAttribute("book", book));

	return "books/details";
    }

    @PostMapping("/books")
    public Book create(@ModelAttribute final Book book) {
	return bookService.create(book);
    }
}
