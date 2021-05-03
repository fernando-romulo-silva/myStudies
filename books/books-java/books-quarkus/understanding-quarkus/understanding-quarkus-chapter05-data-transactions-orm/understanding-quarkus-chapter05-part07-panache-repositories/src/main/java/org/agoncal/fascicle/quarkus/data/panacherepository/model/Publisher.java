package org.agoncal.fascicle.quarkus.data.panacherepository.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */

@Entity
public class Publisher extends PanacheEntity {

    @Column(length = 30)
    public String name;
}
