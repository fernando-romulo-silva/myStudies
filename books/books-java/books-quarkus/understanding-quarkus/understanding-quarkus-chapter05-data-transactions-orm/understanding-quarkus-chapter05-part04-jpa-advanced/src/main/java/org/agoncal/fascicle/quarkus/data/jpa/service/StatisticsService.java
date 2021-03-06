package org.agoncal.fascicle.quarkus.data.jpa.service;

import org.agoncal.fascicle.quarkus.data.jpa.model.Book;
import org.agoncal.fascicle.quarkus.data.jpa.model.Publisher;
import org.agoncal.fascicle.quarkus.data.jpa.repository.StatisticsRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;


@ApplicationScoped
public class StatisticsService {

    @Inject
    StatisticsRepository repository;

    @Transactional
    public void addNew(Publisher publisher) throws Exception {
	repository.add(publisher);
	repository.computeNewStatistics();
    }

    // tag::adocSkip[]
    @Transactional
    public void addNew(Book book) {
    }
    // end::adocSkip[]
}

