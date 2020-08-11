package br.com.fernando.myExamCloud.managePersistenceJpaAndBeanValidation;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class Question04 {

    // Given:
    //
    // Entity A is mapped to a table named A_T.
    // Entity B is mapped to a table named B_T.
    // Table A_T contains a foreign key to table B_T.
    //
    // Select the correct Entity declaration for entity A.
    //
    //
    class B {

    }

    // Choice A
    @Entity
    public class A1 {
	private B b;

	@OneToMany
	public B getB() {
	    return b;
	}

	public void setB(B b) {
	    this.b = b;
	}
	// ...
    }

    // Choice B
    @Entity
    public class A2 {
	private B b;

	@ManyToOne
	public B getB() {
	    return b;
	}

	public void setB(B b) {
	    this.b = b;
	}
	// ...
    }

    // Choice C
    @Entity
    public class A3 {
	private B b;

	@ManyToOne(/* #B.A */)
	public B getB() {
	    return b;
	}

	public void setB(B b) {
	    this.b = b;
	}
	// ...
    }
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
    // Choice B is correct.
    //
    // This an example of bidirectional ManyToOne / OneToMany Relationships.
    // The annotation @ManyToOne must be defined on owner side.
    // Here, entity A is the owner of the relationship (Table A_T contains a foreign key to table B_T).
    // Hence Choice B is correct.
}
