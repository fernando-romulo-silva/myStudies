package com.alura.pix.repository;

import com.alura.pix.model.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<Key, Integer> {
    Key findByChave(String key);
}
