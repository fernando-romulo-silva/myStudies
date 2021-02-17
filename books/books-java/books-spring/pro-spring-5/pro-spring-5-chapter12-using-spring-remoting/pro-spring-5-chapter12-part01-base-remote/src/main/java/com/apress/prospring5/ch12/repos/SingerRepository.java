package com.apress.prospring5.ch12.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.apress.prospring5.ch12.entities.Singer;

public interface SingerRepository extends CrudRepository<Singer, Long> {

    List<Singer> findByFirstName(String firstName);
}
