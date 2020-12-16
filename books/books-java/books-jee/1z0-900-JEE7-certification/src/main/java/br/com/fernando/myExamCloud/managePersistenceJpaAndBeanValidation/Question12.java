package br.com.fernando.myExamCloud.managePersistenceJpaAndBeanValidation;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class Question12 {

    // Consider the following classes:
    @Entity
    class Customer {
	@Id
	int id;
	@OneToOne
	Address address;
    }

    @Entity
    class Address {
	@Id
	int id;
	@OneToOne(mappedBy = "address")
	Customer customer;
    }

    // Given that the Customer entity maps to an CUSTOMER database table and Address entity maps to an ADDRESS database table,
    // which statement is correct assuming there is NO mapping descriptor?
    //
    // Choice A
    // The relationship is mapped to a foreign key in the CUSTOMER table.
    //
    // Choice B
    // The relationship is mapped using a join table CUSTOMER_ ADDRESS.
    //
    // Choice C
    // The relationship is mapped to a foreign key in the ADDRESS table.
    //
    // Choice D
    // The relationship is mapped to foreign keys in both CUSTOMER and ADDRESS tables.
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
    // Choice C is correct.
    //
    // The annotation @OneToOne defines a single-valued association to another entity that has one-to-one multiplicity. 
    // It is not normally necessary to specify the associated target entity explicitly since it can usually be inferred from the type of the object being referenced.
    //
    // mappedBy - The field that owns the relationship. 
    //
    // This element is only specified on the inverse (non-owning) side of the association. Hence Choice C is correct.
}
