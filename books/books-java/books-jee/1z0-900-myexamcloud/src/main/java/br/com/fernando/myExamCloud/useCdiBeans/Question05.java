package br.com.fernando.myExamCloud.useCdiBeans;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Named;

public class Question05 {

    public static class OrderEvent {

    }

    @Named
    public static class ImportantEventSource {

	// The producer bean can specify a set of qualifiers when injecting the event:
	@Inject
	private Event<OrderEvent> importantMessageEvent;

	public void fireEvent() {
	    OrderEvent oe = new OrderEvent();

	    importantMessageEvent.fire(oe);
	}
    }

    // Given the code fragment:
    @RequestScoped // line 1
    public static class OrderActivityService {

	// line3
	public void notifPayment(OrderEvent oe) {
	}

	// line 6
	public void notifyRefund(OrderEvent oe) {
	}
    }

    // Which code can be added to register both of these methods to receive OrderEvent notifications
    // only if an instance of OrderActivityService is already instantiated in the current context?
    //
    // Choice A
    // @Observes(notifyObserver=IF_EXISTS) on line 3 and line 6
    //
    // Choice B
    // @Observes(during=IN_PROGRESS) on line 1
    //
    // Choice C
    // @Observes(during=AFTER_COMPLETION) on line 1
    //
    // Choice D
    // @Observes(notifyObserver=IF_EXISTS) on line 4 and line 7 before method parameter declaration
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // A conditional observer method is notified of an event only if an instance of the bean that defines the
    // observer method already exists in the current context.
    //
    // To declare a conditional observer method, specify notifyObserver=IF_EXISTS as an argument to @Observes:

    @RequestScoped // line 1
    public static class OrderActivityServiceResponse {

	// line3
	public void notifPayment(@Observes(notifyObserver = Reception.IF_EXISTS) OrderEvent oe) {
	}

	// line 6
	public void notifyRefund(@Observes(notifyObserver = Reception.IF_EXISTS) OrderEvent oe) {
	}
    }
}
