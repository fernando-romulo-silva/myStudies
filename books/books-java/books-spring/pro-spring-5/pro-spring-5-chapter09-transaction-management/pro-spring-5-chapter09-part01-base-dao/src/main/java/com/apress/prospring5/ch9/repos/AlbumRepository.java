package com.apress.prospring5.ch9.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apress.prospring5.ch9.entities.Album;
import com.apress.prospring5.ch9.entities.Singer;

/**
 * Created by iuliana.cosmina on 5/7/17.
 */
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findBySinger(Singer singer);
}
