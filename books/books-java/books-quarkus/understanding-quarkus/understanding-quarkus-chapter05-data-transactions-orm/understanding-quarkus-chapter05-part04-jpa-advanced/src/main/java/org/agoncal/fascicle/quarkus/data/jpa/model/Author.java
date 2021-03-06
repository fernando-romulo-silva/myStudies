package org.agoncal.fascicle.quarkus.data.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */
@Entity
public class Author extends Artist {

    // ======================================
    // = Attributes =
    // ======================================

    @Column(name = "preferred_Language")
    @Enumerated
    private Language preferredLanguage;

    public Language getPreferredLanguage() {
	return preferredLanguage;
    }

    public void setPreferredLanguage(Language preferredLanguage) {
	this.preferredLanguage = preferredLanguage;
    }
}
