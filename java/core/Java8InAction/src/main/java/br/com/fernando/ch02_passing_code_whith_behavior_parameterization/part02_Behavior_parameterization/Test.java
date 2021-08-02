package br.com.fernando.ch02_passing_code_whith_behavior_parameterization.part02_Behavior_parameterization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.fernando.Apple;

// Behavior parameterization
public class Test {

    public static void main(String... args) {

	final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

	// [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
	final List<Apple> greenApples = filter(inventory, new AppleColorPredicate());
	System.out.println(greenApples);

	// [Apple{color='red', weight=120}]
	final List<Apple> redApples = filter(inventory, new AppleRedAndHeavyPredicate());
	System.out.println(redApples);

    }

    // You can see these criteria as different behaviors for the filter method. What you just did is
    // related to the strategy design pattern,[1] which lets you define a family of algorithms, encapsulate
    // each algorithm (called a strategy), and select an algorithm at run-time.

    public interface ApplePredicate {

	boolean test(Apple apple);
    }

    static class AppleWeightPredicate implements ApplePredicate {

	@Override
	public boolean test(Apple apple) {
	    return apple.getWeight() > 150;
	}
    }

    static class AppleColorPredicate implements ApplePredicate {

	@Override
	public boolean test(Apple apple) {
	    return "green".equals(apple.getColor());
	}
    }

    static class AppleRedAndHeavyPredicate implements ApplePredicate {

	@Override
	public boolean test(Apple apple) {
	    return "red".equals(apple.getColor()) && apple.getWeight() > 150;
	}
    }

    // =================================================================================

    public static List<Apple> filter(List<Apple> inventory, ApplePredicate p) {
	final List<Apple> result = new ArrayList<>();
	for (final Apple apple : inventory) {
	    if (p.test(apple)) {
		result.add(apple);
	    }
	}
	return result;
    }
    // =================================================================================

    // First, you need a way to represent a behavior that takes an Apple and returns a formatted String
    // result. You did something similar when you created an ApplePredicate interface:
    public interface AppleFormatter {

	String accept(Apple a);
    }

    // You can now represent multiple formatting behaviors by implementing the Apple-Formatter interface:
    public class AppleFancyFormatter implements AppleFormatter {

	@Override
	public String accept(Apple apple) {
	    final String characteristic = apple.getWeight() > 150 ? "heavy" : "light";
	    return "A " + characteristic + " " + apple.getColor() + " apple";
	}
    }

    public class AppleSimpleFormatter implements AppleFormatter {

	@Override
	public String accept(Apple apple) {
	    return "An apple of " + apple.getWeight() + "g";
	}
    }
    
    // You’ve seen that you can abstract over behavior and make your code adapt to requirement
    // changes, but the process is verbose because you need to declare multiple classes that you
    // instantiate only once.    
    public static void prettyPrintApple(List<Apple> inventory, AppleFormatter formatter) {
	for (final Apple apple : inventory) {
	    final String output = formatter.accept(apple);
	    System.out.println(output);
	}
    }

}
