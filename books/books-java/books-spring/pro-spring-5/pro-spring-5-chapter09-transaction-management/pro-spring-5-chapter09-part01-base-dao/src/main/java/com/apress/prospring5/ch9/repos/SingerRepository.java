package com.apress.prospring5.ch9.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.apress.prospring5.ch9.entities.Singer;

public interface SingerRepository extends CrudRepository<Singer, Long> {

    @Query("select count(s) from Singer s")
    Long countAllSingers();

}
