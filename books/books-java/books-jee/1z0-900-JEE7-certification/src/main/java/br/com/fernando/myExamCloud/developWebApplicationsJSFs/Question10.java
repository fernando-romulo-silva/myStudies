package br.com.fernando.myExamCloud.developWebApplicationsJSFs;

import javax.inject.Inject;

public class Question10 {

    // Given the code fragment:
    // Order.java:
    public class Order {
	private int orderId = 12345;
	// getters and setters
    }

    // Customer.java:
    public class Customer {
	private Order order; // line 1

	public void setOrder(Order order) { // line 2
	    this.order = order;
	}

	public Order getOrder() { // line 3
	    return order;
	}
    }

    // index.xhtml:
    /**
     * <h:outputText value = "Order #(customer.order.orderId)"/>
     */
    //
    // Which two steps, when performed independently, enable the index.xhtml page to print the following text: Order 12345?
    //
    //
    // Choice A
    // Replace line 2 with: @Inject public void setOrder(Order order)
    //
    // Choice B
    // Replace line 3 with: @Inject public Order getOrder()
    //
    // Choice C
    // Replace line 1 with: @Inject private Order order;
    //
    // Choice D
    // Replace line 3 with: public @Inject Order getOrder()
    //
    // Choice E
    // Replace line 1 with: private @Inject Order order;
    //
    // Choice F
    // Replace line 2 with: public void setOrder(@Inject Order order)
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
    // Choice A and D are correct answers.
    //
    public @Inject Order getOrder() { // yeah, it works
	return null;
    }

    @Inject // The injection requires on setter and getter methods.
    public void setOrder(Order order) {

    }

    //
    // The injection requires on setter and getter methods for jsf
    //
    // C is wrong because since Order class has setter/getter for acc field, it seems to be expecting Initializer method parameter injection.

}