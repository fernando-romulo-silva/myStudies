package org.agoncal.fascicle.quarkus.data.panacherepository.service;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.agoncal.fascicle.quarkus.data.panacherepository.model.Publisher;
import org.agoncal.fascicle.quarkus.data.panacherepository.repository.PublisherRepository;

import io.quarkus.hibernate.orm.panache.Panache;

@ApplicationScoped
@Transactional(SUPPORTS)
public class PublisherService {

    @Inject
    PublisherRepository repository;

    @Transactional(REQUIRED)
    public Publisher persist(Publisher publisher) {
	repository.persist(publisher);
	return publisher;
    }

    public List<Publisher> findAll() {
	return repository.listAll();
    }

    public Optional<Publisher> findByIdOptional(Long id) {
	return repository.findByIdOptional(id);
    }

    @Transactional(REQUIRED)
    public void deleteById(Long id) {
	repository.deleteById(id);
    }
    // tag::adocSkip[]

    @Transactional(REQUIRED)
    public Publisher update(Publisher publisher) {
	return Panache.getEntityManager().merge(publisher);
    }

    public Optional<Publisher> findByName(String name) {
	return repository.findByName(name);
    }

    @Transactional(REQUIRED)
    public long deleteByName(String name) {
	return repository.deleteByName(name);
    }
    // end::adocSkip[]
}
