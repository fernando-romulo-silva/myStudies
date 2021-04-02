package br.com.fernando.enthuware.developWebApplicationsJSFs;

import javax.inject.Inject;

@SuppressWarnings("unused")
public class Question02 {
	// Given the code fragment:
	//
	//
	public class Account {
		private int accld = 12345;
		// getters and setters
	}

	public class Customer {
		private Account acc; // line 1

		public void setAcc(Account acc) { // line 2
			this.acc = acc;
		}

		public Account getAcc() { // line 3
			return acc;
		}
	}

	// And index. xhtml:
	/**
	 * <h:outputText value = "Order #(customer.order.orderId)"/>
	 */
	// Which steps, when performed independently, enable the index.xhtml page to print the following text: The Id is 12345 ?
	//
	// You had to select 1 option(s)
	//
	// A
	// Replace line 1 with: @Inject private Account acc;
	//
	// B
	// Replace line 2 with: @Inject public void setAcc(Account acc)
	//
	// C
	// Replace line 3 with: public @Inject Account getAcc()
	//
	// D
	// Replace line 3 with: @Inject public Account getAcc()
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
	// The correct answer is B
	//
	// The injection requires on setter and getter methods.
	//
	@Inject
	public void setAcc(Account acc) {

	}

	// A is wrong because since Employee class has setter/getter for acc field, it
	// seems to be expecting Initializer method parameter injection.
	//
	// C and D is wrong because you don't use @Inject annotations in methods without
	// parameters
	//
	//
	// Constructor injection
	public class Customer2 {

		private Account acc;

		@Inject
		public Customer2(Account acc) {
			super();
			this.acc = acc;
		}
	}

	//
	// Field injection
	public class Customer3 {

		@Inject
		private Account acc;
	}
}
