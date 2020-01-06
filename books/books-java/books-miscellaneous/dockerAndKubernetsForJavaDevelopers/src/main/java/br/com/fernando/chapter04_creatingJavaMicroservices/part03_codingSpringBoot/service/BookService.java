package br.com.fernando.chapter04_creatingJavaMicroservices.part03_codingSpringBoot.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.fernando.chapter04_creatingJavaMicroservices.part03_codingSpringBoot.domain.Book;

public interface BookService {

    Book saveBook(@NotNull @Valid final Book book);

    List<Book> getList();

    Book getBook(Long bookId);

    void deleteBook(final Long bookId);
}
