package br.com.fernando.enthuware.useCdiBeans;

import static javax.enterprise.event.Reception.IF_EXISTS;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fernando.myExamCloud.useCdiBeans.Question05.OrderEvent;

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
    
    @Named
    public static class ImportantEventSource {

	// The producer bean can specify a set of qualifiers when injecting the event:
	@Inject
	private Event<BankEvent> importantMessageEvent;

	public void fireEvent() {
	    BankEvent oe = new BankEvent("");

	    importantMessageEvent.fire(oe);
	}
    }

    // Event
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
    // A - Apply @Observes(notifyObserver=IF_EXISTS) to method parameters on line 3 and line 6
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
    //
    // Observer methods can also be conditional or transactional:
    //
    // A conditional observer method is notified of an event only if an instance of the bean that defines the observer method already exists in the current context.
    //
    // To declare a conditional observer method, specify notifyObserver=IF_EXISTS as an argument to @Observes:

    @RequestScoped // line 1
    public static class OrderActivityServiceResponse {

	// line3
	public void notifPayment(@Observes(notifyObserver = IF_EXISTS) OrderEvent oe) { // 4
	}

	// line 6
	public void notifyRefund(@Observes(notifyObserver = IF_EXISTS) OrderEvent oe) { // 7
	}

	// line3
	// @Observes(notifyObserver = IF_EXISTS) // error: The annotation @Observes is disallowed for this location
	public void notifPayment2(OrderEvent oe) {
	}
    }
}
