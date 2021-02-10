package br.com.fernando.myExamCloud.useCdiBeans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

public class Question09 {

    // Given the code fragment:

    public class CustomerBean {
    }

    @SessionScoped
    public class CustomerController {

	@Inject
	private CustomerBean customerBean;

    }

    // Assuming this bean is used only in the code fragment above, how long will the injected CustomerBean instance be available?
    //
    // Choice A
    // For the lifetime of the enterprise application
    //
    // Choice B
    // For the lifetime of the request
    //
    // Choice C
    // For the lifetime of the session
    //
    // Choice D
    // For the lifetime of the CustomerController object
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
    // Choice D is correct.
    //
    // Different approaches exist to describe the dependencies of a class.
    //
    // The most common approach is to use Java annotations to describe the dependencies directly in the class.
    // The standard Java annotations for describing the dependencies of a class are defined in the Java Specification Request 330 (JSR330).
    //
    // This specification describes the @Inject and @Named annotations.
    //
    // Dependency injection can be performed on:
    //
    // * the constructor of the class (construction injection)
    // * a field (field injection)
    // * the parameters of a method (method injection)
    //
    // The injected CustomerBean instance lifetime will be lifetime of the CustomerController.
    //

}
