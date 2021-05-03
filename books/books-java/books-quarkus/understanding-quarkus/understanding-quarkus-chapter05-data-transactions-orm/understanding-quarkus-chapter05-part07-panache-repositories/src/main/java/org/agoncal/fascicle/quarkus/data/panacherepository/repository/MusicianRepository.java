package org.agoncal.fascicle.quarkus.data.panacherepository.repository;

import javax.enterprise.context.ApplicationScoped;

import org.agoncal.fascicle.quarkus.data.panacherepository.model.Musician;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class MusicianRepository implements PanacheRepository<Musician> {

    public Musician update(Musician musician) {
	return Panache.getEntityManager().merge(musician);
    }
}
