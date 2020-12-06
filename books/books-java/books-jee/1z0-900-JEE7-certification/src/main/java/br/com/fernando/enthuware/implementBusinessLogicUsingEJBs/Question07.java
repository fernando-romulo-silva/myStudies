package br.com.fernando.enthuware.implementBusinessLogicUsingEJBs;

import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;

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
    // The correct answer is D
}
