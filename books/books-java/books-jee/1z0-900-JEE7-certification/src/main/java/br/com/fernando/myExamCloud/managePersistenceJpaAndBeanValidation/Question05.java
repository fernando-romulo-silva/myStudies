package br.com.fernando.myExamCloud.managePersistenceJpaAndBeanValidation;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

public class Question05 {

    // Which of these can be a JPA entity?
    //
    // Choice A
    // Enum type
    //
    // Choice B
    // Abstract class
    //
    // Choice C
    // Interface type
    //
    // Choice D
    // Final class
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //    
    //
    //
    //
    //
    //
    //
    //    
    //
    //
    //
    //
    //
    //
    //
    // Choice A is correct.
    //
    // Requirements for Entity Classes
    //
    // An entity class must follow these requirements.
    //
    // * The class must be annotated with the javax.persistence.Entity annotation.
    //
    // * The class must have a public or protected, no-argument constructor.
    // The class may have other constructors.
    //
    // * The class must not be declared final.
    // No methods or persistent instance variables must be declared final.
    //
    // * If an entity instance is passed by value as a detached object, such as through a session bean’s remote business interface,
    // the class must implement the Serializable interface.
    //
    // * Entities may extend both entity and non-entity classes, and non-entity classes may extend entity classes.
    //
    // * Persistent instance variables must be declared private, protected, or package-private and can be accessed directly
    // only by the entity class’s methods. Clients must access the entity’s state through accessor or business methods.

    public enum Status {
	OPEN, REVIEW, APPROVED, REJECTED;
    }

    @Entity
    public class Article {

	Article() {

	}

	@Id
	private int id;

	private String title;

	@Enumerated(EnumType.ORDINAL)
	private Status status;
    }
}
