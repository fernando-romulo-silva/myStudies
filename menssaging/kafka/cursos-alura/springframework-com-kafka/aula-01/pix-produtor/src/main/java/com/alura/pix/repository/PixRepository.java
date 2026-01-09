package com.alura.pix.repository;

import com.alura.pix.model.Pix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PixRepository extends JpaRepository<Pix, Integer> {

}
