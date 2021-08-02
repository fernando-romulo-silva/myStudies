package org.agoncal.fascicle.quarkus.data.panacherepository.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.agoncal.fascicle.quarkus.data.panacherepository.model.CD;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CDRepository implements PanacheRepository<CD> {

    public CD update(CD cd) {
	return Panache.getEntityManager().merge(cd);
    }

    public List<CD> findLikeGenre(String genre) {
	return list("genre like ?1", "%" + genre + "%");
    }
}
