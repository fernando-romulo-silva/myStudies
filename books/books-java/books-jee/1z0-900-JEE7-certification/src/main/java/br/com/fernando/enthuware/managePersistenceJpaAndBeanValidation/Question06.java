package br.com.fernando.enthuware.managePersistenceJpaAndBeanValidation;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class Question06 {
    
    // Given the code fragments:

    // line 1
    public class ContactId implements Serializable {
	private String countryCode;
	private String phoneNumber;
	// remaining implementation
    }

    @Entity
    // line 2
    public class Contact implements Serializable {
	@NotNull
	@Id
	protected String countryCode;
	@NotNull
	@Id
	protected String phoneNumber;
	// remaining implementation
    }

    // Which action completes this composite primary key implementation?
    //
    // A - Add @IdClass annotation at line 1.
    //
    // B - Add @Embeddable annotation at line 1 and replace both @Id annotations with @EmbeddedId
    //
    // C - Add @IdClass(ContactId.class) annotation at line 2.
    //
    // D - Add @Embeddable annotation at line 1 and @EmbeddedId(Contactld.class) at line 2.
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
    // The correct answer is C
}
