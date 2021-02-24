package com.apress.prospring5.ch12.services;

import java.util.List;

import com.apress.prospring5.ch12.entities.Singer;

public interface SingerService {

    List<Singer> findAll();

    List<Singer> findByFirstName(String firstName);

    Singer findById(Long id);

    Singer save(Singer singer);

    void delete(Singer singer);
}
