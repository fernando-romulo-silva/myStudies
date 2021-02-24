package com.apress.prospring5.ch12.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apress.prospring5.ch12.entities.Singer;
import com.apress.prospring5.ch12.repos.SingerRepository;
import com.google.common.collect.Lists;

@Service("singerService")
@Transactional
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerRepository singerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Singer> findAll() {
	return Lists.newArrayList(singerRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Singer> findByFirstName(String firstName) {
	return singerRepository.findByFirstName(firstName);
    }

    @Override
    @Transactional(readOnly = true)
    public Singer findById(Long id) {
	return singerRepository.findById(id).get();
    }

    @Override
    public Singer save(Singer singer) {
	return singerRepository.save(singer);
    }

    @Override
    public void delete(Singer singer) {
	singerRepository.delete(singer);
    }
}
