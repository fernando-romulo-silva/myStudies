package br.com.fernando.ch08_refactoring_testing_and_debugging.part02_Refactoring_object_oriented_design_patterns_with_lambdas;

import java.util.function.Function;
import java.util.function.UnaryOperator;

// Refactoring object-oriented design patterns with lambdas
public class Test3 {

    // Chain of responsibility
    public static void test4() {
	// The chain of responsibility pattern is a common solution to create a chain of processing objects
	// (such as a chain of operations). One processing object may do some work and pass the result to
	// another object, which then also does some work and passes it on to yet another processing
	// object, and so on

	//
	// Normal
	// Create the responsibility
	final ProcessingObject<String> p1 = new HeaderTextProcessing();
	final ProcessingObject<String> p2 = new SpellCheckerProcessing();
	// Chaining two processing objects
	p1.setSuccessor(p2);

	final String result1 = p1.handle("Aren't labdas really sexy?!!");
	// Prints 'From Raoul, Mario and Alan: Arent't lambdas really sexy?!!'
	System.out.println(result1);

	//
	// Lambda
	//
	// You can represent the processing objects as an instance of Function<String, String> or more precisely a UnaryOperator<String>.
	// To chain them you just need to compose these functions by using the andThen method!
	//
	// The first processing object
	final UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
	//
	// The second processing object
	final UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");
	//
	// Compose the tow functions, resulting in a chain of operations
	final Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
	//
	final String result2 = pipeline.apply("Aren't labdas really sexy?!!");
	System.out.println(result2);
    }

    static abstract class ProcessingObject<T> {

	protected ProcessingObject<T> successor;

	public void setSuccessor(ProcessingObject<T> successor) {
	    this.successor = successor;
	}

	public T handle(T input) {
	    final T r = handleWork(input);

	    if (successor != null) {
		return successor.handle(r);
	    }

	    return r;
	}

	abstract protected T handleWork(T input);
    }

    static class HeaderTextProcessing extends ProcessingObject<String> {

	@Override
	public String handleWork(String text) {
	    return "From Raoul, Mario and Alan: " + text;
	}
    }

    static class SpellCheckerProcessing extends ProcessingObject<String> {

	@Override
	public String handleWork(String text) {
	    return text.replaceAll("labda", "lambda"); // Oops, we forgot the 'm' in 'Lambda'!
	}
    }

    // ==================================================================================================
    public static void main(String[] args) {
	test4();
    }

}
