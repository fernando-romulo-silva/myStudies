package br.com.fernando.myExamCloud.understandJavaEEArchitecture;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@SuppressWarnings("unused")
public class Question09 {

    // Given the code fragment:

    public class Bean {
    }

    @SessionScoped
    public class Service {

	@Inject
	private Bean bean;
    }

    // Assuming this bean is used only in the code fragment above, how long will the injected Bean instance be available?
    // You have to select 1 option
    //
    // A
    // for the lifetime of the enterprise application
    //
    // B
    // for the lifetime of the request
    //
    // C
    // for the lifetime of the session
    //
    // D
    // for the lifetime of the Service object
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
    // The "bean" is a Non contextual instance and it exists untill the containing bean is destroyed.
    // In this case, the Service object itself is a contextual object and will last for the lifetime of the session.
}
