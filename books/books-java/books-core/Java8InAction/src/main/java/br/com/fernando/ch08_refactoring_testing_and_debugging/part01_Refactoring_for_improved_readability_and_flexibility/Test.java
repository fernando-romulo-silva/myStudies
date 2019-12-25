package br.com.fernando.ch08_refactoring_testing_and_debugging.part01_Refactoring_for_improved_readability_and_flexibility;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.fernando.Apple;
import br.com.fernando.Dish;
import br.com.fernando.ch06_collectiing_date_with_streams.part03_Grouping.Test.CaloricLevel;

// Refactoring for improved readability and flexibility
public class Test {

    // Improving code readability
    public static void test01() {

	// Java 8 features can also help improve code readability compared to previous versions:
	//
	//  You can reduce the verbosity of your code, making it easier to understand.
	//  You can improve the intent of your code by using method references and the Streams API.
	//
	// We describe three simple refactorings that use lambdas, method references, and streams, which
	// you can apply to your code to improve its readability:
	//
	//  Refactoring anonymous classes to lambda expressions
	//  Refactoring lambda expressions to method references
	//  Refactoring imperative-style data processing to streams
    }

    // From anonymous classes to lambda expressions
    public static void test02() {
	// The first simple refactoring you should consider is converting uses of anonymous classes
	// implementing one single abstract method to lambda expressions
	//
	// First, the meanings of this and super are different for anonymous classes and
	// lambda expressions. Inside an anonymous class, this refers to the anonymous class itself, but
	// inside a lambda it refers to the enclosing class. Second, anonymous classes are allowed to
	// shadow variables from the enclosing class.

	// Before, using an anonymous class
	final Runnable r1 = new Runnable() {

	    @Override
	    public void run() {
		System.out.println("Hello!");
	    }
	};

	// After, using a lambda expression
	final Runnable r2 = () -> System.out.println("Hello Again!");

	// more
	//
	final int a = 10;

	final Runnable r3 = () -> {

	    // int a = 2; // comile error!

	};

	final Runnable r4 = new Runnable() {

	    @Override
	    public void run() {
		final int a = 2;
		System.out.println(a); // Everything is fine!
	    }
	};

	// Finally, converting an anonymous class to a lambda expression can make the resulting code
	// ambiguous in the context of overloading. Indeed, the type of anonymous class is explicit at
	// instantiation, but the type of the lambda depends on its context. Here’s an example of how this
	// can be problematic. Let’s say you’ve declared a functional interface with the same signature as
	// Runnable, here called Task (this might occur when you need interface names that are more
	// meaningful in your domain model):
	//

	// You can now pass an anonymous class implementing Task without a problem:
	doSomething(new Task() {

	    @Override
	    public void execute() {
		System.out.println("Danger danger!!");
	    }
	});

	// Problem; both doSomething(Runnable) and doSomething(Task) match
	// doSomething(() -> System.out.println("Danger dangre!!"));

	// You can solve the ambiguity by providing an explicit cast (Task):
	doSomething((Task) () -> System.out.println("Danger danger!!"));

    }

    interface Task {

	public void execute();
    }

    public static void doSomething(Runnable r) {
	r.run();
    }

    public static void doSomething(Task r) {
	r.execute();
    }

    // From lambda expressions to method references
    public static void test03() {
	// Lambda expressions are great for short code that needs to be passed around. But consider using
	// method references when possible to improve code readability.

	final Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = Dish.menu.stream() //
	    .collect( //
	              groupingBy(dish -> { //
	                  if (dish.getCalories() <= 400) {
		              return CaloricLevel.DIET;
	                  } else if (dish.getCalories() <= 700) {
		              return CaloricLevel.NORMAL;
	                  } else {
		              return CaloricLevel.FAT;
	                  }
	              }));

	final Map<CaloricLevel, List<Dish>> dishesByCaloricLevel2 = Dish.menu.stream() //
	    .collect(groupingBy(Dish::getCaloricLevel));

	// In addition, consider making use of helper static methods such as comparing and maxBy when
	// possible. These methods were designed for use with method references! Indeed, this code states
	// much more clearly its intent than its counterpart using a lambda expression,

	final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

	// You need to think about the implementation of comparison
	inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));

	// Reads like the problem statement
	inventory.sort(comparing(Apple::getWeight));

	//
	// Moreover, for many common reduction operations such as sum, maximum there are built-in
	// helper methods that can be combined with method references. For example, we showed that
	// using the Collectors API you can find the maximum or sum in a clearer way than using a
	// combination of a lambda expression and a lower-level reduce operation. Instead of writing

	final int totalCalories = Dish.menu.stream() //
	    .map(Dish::getCalories) //
	    .reduce(0, (c1, c2) -> c1 + c2);
	//
	// try using alternative built-in collectors, which state more clearly what the problem statement is.
	// Here we use the collector summingInt (names go a long way in documenting your code):
	//
	final int totalCalories2 = Dish.menu.stream() //
	    .collect(summingInt(Dish::getCalories));

    }

    // From imperative data processing to Streams
    public static void test04() {

	// Ideally, you should try to convert all code that processes a collection with typical data processing
	// patterns with an iterator to use the Streams API instead. Why? The Streams API expresses more
	// clearly the intent of a data processing pipeline. I

	final List<String> dishNames = new ArrayList<>();

	for (final Dish dish : Dish.menu) {
	    if (dish.getCalories() > 300) {
		dishNames.add(dish.getName());
	    }
	}

	// The alternative using the Streams API reads more like the problem statement, and it can be
	// easily parallelized:
	Dish.menu.parallelStream() //
	    .filter(d -> d.getCalories() > 300) //
	    .map(Dish::getName) //
	    .collect(toList());

	// Unfortunately, converting imperative code to the Streams API can be a difficult task, because
	// you need to think about control-flow statements such as break, continue, and return and infer
	// the right stream operations to use. The good news is that some tools can help you with this task
	// as well.[2] 2 See http://refactoring.info/tools/LambdaFicator/
    }

    // Improving code flexibility
    public static void test05() {

	// We argued in chapters 2 and 3 that lambda expressions encourage the style of behavior
	// parameterization. You can represent multiple different behaviors with different lambdas that
	// you can then pass around to execute. This style lets you cope with requirement changes (for
	// example, creating multiple different ways of filtering with a Predicate or comparing with a
	// Comparator).
    }

    // Adopting functional interfaces - Conditional deferred execution
    final static Logger logger = Logger.getLogger(Test.class.getName());

    public static void test051() {

	// It’s common to see control-flow statements mangled inside business logic code. Typical
	// scenarios include security checks and logging. For example, consider the following code that
	// uses the built-in Java Logger class:

	if (logger.isLoggable(Level.FINER)) {
	    logger.finer("Problem: " + generateDiagnostic());
	}

	// What’s wrong with it? A couple of things:
	//
	//  The state of the logger (what level it supports) is exposed in the client code through the method isLoggable.
	//
	//  Why should you have to query the state of the logger object every time before you can log a message? It just clutters your code.
	//
	// A better alternative is to make use of the log method, which internally checks to see if the logger
	// object is set to the right level before logging the message:

	logger.log(Level.FINER, "Problem: " + generateDiagnostic());

	// This is a better approach because your code isn’t cluttered with if checks, and the state of the
	// logger is no longer exposed.
	// Unfortunately, there’s still an issue with this code. The logging message is always evaluated,
	// even if the logger isn’t enabled for the message level passed as argument.
	//
	// public void log(Level level, Supplier<String> msgSupplier) {
	//// if (logger.isLoggable(Level.FINER)) {
	////// logger.finer("Problem: " + generateDiagnostic());
	//// }
	// }

	logger.log(Level.FINER, () -> "Problem: " + generateDiagnostic());
    }

    private static String generateDiagnostic() {
	return "Some errors!";
    }

    // Execute around
    public static String processFile(BufferedReaderProcessor p) throws IOException {

	// In chapter 3 we discussed another pattern that you can adopt: execute around. If you find
	// yourself surrounding different code with the same preparation and cleanup phases, you can
	// often pull that code into a lambda. The benefit is that you reuse the logic dealing with the
	// preparation and cleanup phases, thus reducing code duplication.

	try (final BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {

	    return p.process(br); // Execute the BufferedReaderProcessor
	}

    }

    // A functional interface for a lambda which can throw an IOException
    @FunctionalInterface
    public interface BufferedReaderProcessor {
	public String process(BufferedReader b) throws IOException;
    }

    public static void test052() throws IOException {
	final String result1 = processFile((BufferedReader br) -> br.readLine()); // pass a lambda

	final String result2 = processFile((BufferedReader br) -> br.readLine() + " " + br.readLine()); // Pass a different lambda
    }

}
