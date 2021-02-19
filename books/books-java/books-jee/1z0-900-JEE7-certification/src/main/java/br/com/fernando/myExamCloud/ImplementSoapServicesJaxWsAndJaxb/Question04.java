package br.com.fernando.myExamCloud.ImplementSoapServicesJaxWsAndJaxb;

import javax.jws.WebService;

public class Question04 {

    // Given:
    // 2
    public class ProcessOrder {

	// line 5
	public boolean saveOrder(Order order) {
	    boolean isSaved = false;
	    // Save order

	    return isSaved;
	}

	// line 11
	public boolean isValidOrder(String orderNumber) {
	    boolean isValidOrder = false;
	    // Verify order
	    return isValidOrder;
	}
    }

    public class Order {
    }

    // A developer wants to convert all ProcessOrder methods into Web services.
    // What changes can achieve this?
    //
    //
    // Choice A
    // Import javax.jws.WebService and extend WebService
    //
    //
    // Choice B
    // Import javax.jws.WebService and use @WebService at line 2.
    //
    //
    // Choice C
    // Import javax.jws.WebService and use @WebService at line 5 and line 11.
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
    // Choice B is correct.
    //
    // To export these methods, we must add two things: an import statement for the javax.jws.WebService package
    // and a @WebService annotation at the beginning that tells the Java interpreter that we intend to publish
    // the methods of this class as a web service.
    //
    // import javax.jws.WebService;
    //
    @WebService
    public class ProcessOrderResponse {

	public boolean saveOrder(Order order) {
	    boolean isSaved = false;
	    // Save order

	    return isSaved;
	}

	public boolean isValidOrder(String orderNumber) {
	    boolean isValidOrder = false;
	    // Verify order
	    return isValidOrder;
	}
    }    

}
