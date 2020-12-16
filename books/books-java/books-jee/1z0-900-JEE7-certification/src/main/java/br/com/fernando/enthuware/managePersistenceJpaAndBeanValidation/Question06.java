package br.com.fernando.enthuware.managePersistenceJpaAndBeanValidation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
    //
    //
    //
    //
    //
    // The correct answer is C
    //
    // We can make an Embedded class, which contains compiste primary keys, and then we can have a reference to that class as EmbeddedId in our Entity.
    //
    // You would need the @EmbeddedId and @Embeddable annotations.
    @Entity
    public class MyEntity1 {

	@EmbeddedId
	private MyKey1 myKey;

	@Column(name = "ColumnA")
	private String columnA;

	/** Your getters and setters **/
    }

    @Embeddable
    public class MyKey1 implements Serializable {

	@Column(name = "Id", nullable = false)
	private int id;

	@Column(name = "Version", nullable = false)
	private int version;

	/** getters and setters **/
    }

    // Another way to achieve this goal is to use @IdClass annotation, and place both ids in that IdClass.
    // We can can use normal @Id annotation on both the attributes
    
    @Entity
    @IdClass(MyKey2.class)
    public class MyEntity2 {

      @Id
      private int id;

      @Id
      private int version;
    }
    
    public class MyKey2 implements Serializable {
	private int id;
	private int version;
    }
}
