package com.crashcourse.domain.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.crashcourse.domain.model.Hotel;

public interface HotelRepository extends MongoRepository<Hotel, String> {

    Hotel findByName(final String name);

    List<Hotel> findAll();

    @Query("?0")
    List<Hotel> findAll(final Document document);

    @Query("?0")
    Page<Hotel> findAll(final Document document, final Pageable pageable);

}
