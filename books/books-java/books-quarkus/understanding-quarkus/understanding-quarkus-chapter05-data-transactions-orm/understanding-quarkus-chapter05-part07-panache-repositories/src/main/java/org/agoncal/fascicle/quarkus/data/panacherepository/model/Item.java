package org.agoncal.fascicle.quarkus.data.panacherepository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Item extends PanacheEntity {

    @Column(length = 100)
    public String title;

    @Column(length = 3000)
    public String description;

    @Column(name = "unit_cost")
    public Float unitCost;
}
