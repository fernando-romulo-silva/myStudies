package com.crashcourse.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.crashcourse.domain.model.Guest;

public interface GuestRepository extends MongoRepository<Guest, String> {

    Guest findByName(final String name);
    
    @Query("?0")
    List<Guest> findAll(final Document document);
    
    @Query("?0")
    Page<Guest> findAll(final Document document, final Pageable pageable);
}
