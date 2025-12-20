package com.crashcourse.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.crashcourse.domain.model.Room;

public interface RoomRepository extends MongoRepository<Room, String> {

    @Query("?0")
    List<Room> findAll(final Document document);
    
    @Query("?0")
    Page<Room> findAll(final Document document, final Pageable pageable);

}
