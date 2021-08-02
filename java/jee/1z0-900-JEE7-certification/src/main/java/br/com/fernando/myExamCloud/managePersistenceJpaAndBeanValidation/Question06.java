package br.com.fernando.myExamCloud.managePersistenceJpaAndBeanValidation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class Question06 {

    // When should a JPA entity implement the Serializable interface?

    @Entity
    @Table(name = "TB_PERSON")
    public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column
	private String name;
    }
    // Choice A
    // when JPA entities are used in the EJB Full container
    //
    // Choice B
    // when JPA entities are used outside of the EJB Lite container
    //
    // Choice C
    // always, because JPA entities are required to implement the Serializable interface
    //
    // Choice D
    // when JPA entities are used as parameters or return values by the remote EJB operations
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
    // Choice D is correct.
    //
    // The JPA entities are plain Java objects with setter and getter methods for a business domain objects.
    //
    // There are some situations where it needs to be passed as parameters or return values of remote EJB
    // in that case it must implements Serializable interface.

}
