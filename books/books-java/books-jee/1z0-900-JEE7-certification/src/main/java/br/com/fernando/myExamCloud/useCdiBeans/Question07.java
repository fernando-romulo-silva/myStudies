package br.com.fernando.myExamCloud.useCdiBeans;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Inject;
import javax.inject.Qualifier;

public class Question07 {

    @Qualifier
    @Target({ TYPE, METHOD, PARAMETER, FIELD })
    @Retention(RUNTIME)
    public @interface Synchronous {
    }

    @Qualifier
    @Target({ TYPE, METHOD, PARAMETER, FIELD })
    @Retention(RUNTIME)
    public @interface Asynchronous {
    }

    interface PaymentProcessor {

    }

    @Synchronous
    class SynchronousPaymentProcessor implements PaymentProcessor {
	// . . .
    }

    @Asynchronous
    class AsynchronousPaymentProcessor implements PaymentProcessor {
	// . . .
    }

    // Which of the folllowing client code injects an instance of SynchronousPaymentProcessor?

    PaymentProcessor paymentProcessor; // ?

    // Choice A - Inject(type=Synchronous) PaymentProcessor paymentProcessor;
    //
    // Choice B - @Inject @Synchronous PaymentProcessor paymentProcessor;
    //
    // Choice C - @Inject PaymentProcessor paymentProcessor;
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
    // Choice B is correct
    @Inject
    @Synchronous // qualifier
    PaymentProcessor paymentProcessorCorrect;

    // Qualifier types are applied to injection points to distinguish which implementation is required by the client.
}
