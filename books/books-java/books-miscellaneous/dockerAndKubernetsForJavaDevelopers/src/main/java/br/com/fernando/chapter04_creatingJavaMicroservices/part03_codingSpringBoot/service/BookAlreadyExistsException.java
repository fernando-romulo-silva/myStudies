package br.com.fernando.chapter04_creatingJavaMicroservices.part03_codingSpringBoot.service;

public class BookAlreadyExistsException extends RuntimeException {

    public BookAlreadyExistsException(final String message) {
        super(message);
    }
}
