package com.crashcourse.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.crashcourse.domain.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {

    @Query("?0")
    List<Book> findAll(final Document document);
    
    @Query("?0")
    Page<Book> findAll(final Document document, final Pageable pageable);

}
