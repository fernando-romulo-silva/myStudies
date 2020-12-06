package br.com.fernando.enthuware.useCdiBeans;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

public class Question06 {

    // Given the code fragment:
    @RequestScoped // 1
    public static class BankActivityService { // 2
	// 3
	public void notifyDebit(BankEvent be) { // 4
	} // 5

	// 6
	public void notifyCredit(BankEvent be) { // 7
	} // 8
    } // 9

    public static class BankEvent {

	private final String eventMessage;

	public String getEventMessage() {
	    return eventMessage;
	}

	public BankEvent(String eventMessage) {
	    this.eventMessage = eventMessage;
	}
    }

    // Which code can be added to register both of these methods to receive BankEvent notifications
    // only if an instance of BankActivityService is already instantiated in the current context?
    //
    // A - Apply @Observes(notifyObserver=IF_EXISTS) to method parameters on line 4 and line 7
    //
    // B - Add @Observes(during=IN_PROGRESS) on line 1
    //
    // C - Add @Observes(during=AFTER COMPLETION) on line 1
    //
    // D - Add @Observes(notifyObserver=IF_EXISTS) on line 4 and line 7 before method parameter declaration
    //
    //
    //
    //
    // The correct answer is A
    //
    // Observer methods can also be conditional or transactional:
    //
    // A conditional observer method is notified of an event only if an instance of the bean that defines the observer method already exists in the current context.
    // To declare a conditional observer method, specify notifyObserver=IF_EXISTS as an argument to @Observes:

    public void notifyDebit(@Observes(notifyObserver = Reception.IF_EXISTS) BankEvent be) {
    }
}
