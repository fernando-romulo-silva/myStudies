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
    // The relationship is mapped using a join table CUSTOMER_ADDRESS.
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
    // The annotation @OneToOne defines a single-valued association to another entity that has one-to-one multiplicity. 
    //
    // It is not normally necessary to specify the associated target entity explicitly since it can usually be inferred from the type of the object being referenced.
    //
    // mappedBy - The field that owns the relationship. So, in this case Customer.address. The foreign key is on CUSTOMER table.
    //
    // This element is only specified on the inverse (non-owning) side of the association. Hence Choice C is correct.
    //
    //
    // 00000000000000000000000000000000000000000000000
    // 000||=========||0000000000||==============||000
    // 000||0ADDRESS0||0000000000||000CUSTOMER000||000
    // 000||=========||I--------I||==============||000
    // 000||ID0000000||0000000000||ID000000000000||000
    // 000||=========||0000000000||ADDRESS_ID0000||000
    // 00000000000000000000000000||==============||000
    // 00000000000000000000000000000000000000000000000
    //    
    // Remember: MappedBy is a property of the relationship annotations whose purpose is to generate a mechanism to relate two entities which by 
    // default they do by creating a join table. MappedBy halts that process in one direction.
    //
    // To start considering a bi-directional OneToOne relationship, you do not require two foreign keys, one in each table, 
    // so a single foreign key in the OWNING side of the relationship is sufficient.
    //
    // In JPA the inverse OneToOne must use the mappedBy attribute (with some exceptions), this makes the JPA provider use the foreign key 
    // and mapping information in the source mapping to define the target mapping.
}
