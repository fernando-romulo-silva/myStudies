package br.com.fernando.chapter04_creatingJavaMicroservices.part03_codingSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fernando.chapter04_creatingJavaMicroservices.part03_codingSpringBoot.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
