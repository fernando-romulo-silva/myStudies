package com.crashcourse.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crashcourse.application.HotelService;
import com.crashcourse.domain.model.Hotel;
import com.turkraft.springfilter.boot.Filter;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    private final HotelService service;
    
    HotelController(final HotelService service) {
	this.service = service;
    }
    
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    Hotel getById(@PathVariable(name = "id", required = true) final String id) {
	return service.findById(id);
    }
    
    @GetMapping
    Page<Hotel> get(@Filter(entityClass = Hotel.class) final Document filter, final Pageable page) {
	return service.findBy(filter, page);  
    }
    
//    @GetMapping(produces = APPLICATION_JSON_VALUE)
//    List<Hotel> getAll() {
//	return service.findAll();  
//    }
}
