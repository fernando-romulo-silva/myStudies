package org.agoncal.fascicle.quarkus.data.panacherepository.repository;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.agoncal.fascicle.quarkus.data.panacherepository.model.Publisher;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PublisherRepository implements PanacheRepository<Publisher> {

    // tag::adocSkip[]
    public Publisher update(Publisher publisher) {
	return Panache.getEntityManager().merge(publisher);
    }

    // end::adocSkip[]
    public Optional<Publisher> findByName(String name) {
	return find("name", name).firstResultOptional();
    }

    public long deleteByName(String name) {
	return delete("name", name);
    }
}
