package br.com.fernando.enthuware.implementBusinessLogicUsingEJBs;

import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;

public class Question07 {

    // Given
    // line 1
    public class MyMapper {
	@PrePassivate
	public void setPinDrop() {
	}

	@PostActivate
	public void checkLocation() {
	}
    }

    // What code needs to be added to line 1 for MyMapper bean instances to be correctly passivated?
    //
    // A - @Stateless @ PassivationCapable
    //
    // B - @Stateless
    //
    // C - @Stateful @PassivationCapable
    //
    // D - @Stateful
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
    // Choice D is correct.
    //
    // Passivation and Activation only apply to EJBs that have state, namely stateful session beans and entity beans.
    //
    // Passivation is the process by which any state a given bean has is moved into storage.
    //
    // Activation is the process by which any state that a given bean previously had is loaded from storage.
    //
    // passivationCapable Specifies whether this stateful session bean is passivation capable.
    //
    @Stateful(passivationCapable = false)
    public class HelloBean {
	// . . .
    }
}
