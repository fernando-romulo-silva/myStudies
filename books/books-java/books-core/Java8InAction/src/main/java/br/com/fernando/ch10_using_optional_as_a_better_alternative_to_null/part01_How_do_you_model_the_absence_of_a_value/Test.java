package br.com.fernando.ch10_using_optional_as_a_better_alternative_to_null.part01_How_do_you_model_the_absence_of_a_value;

// How do you model the absence of a value?
public class Test {

    //
    public static void test00() {

	// Raise your hand if you ever got a NullPointerException during your life as a Java developer.
	// Keep it up if this is the Exception you encounter most frequently. Unfortunately, we can’t see
	// you at this moment, but we believe there’s a very high probability that your hand is raised now.
	// We also guess you may possibly be thinking something like “Yes, I agree, NullPointerExceptions
	// are a pain for any Java developer, novice, or expert, but there’s not much we can do about them,
	// because this is the price we pay to use such a convenient, and maybe unavoidable, construct as
	// null references.”

	// Then, what’s possibly problematic with the following code?
	getCarInsuranceName1(null);
    }

    public static String getCarInsuranceName1(final Person person) {
	return person.getCar().getInsurance().getName();
    }

    // --------------------------------------------------------------------------------
    // Reducing NullPointerExceptions with defensive checking
    public static void test01() {

	// This is “deep doubts” because it shows a recurring pattern: every
	// time you have a doubt that a variable could be null, you’re obliged to add a further nested if
	// block, increasing the indentation level of the code. This clearly scales poorly and compromises
	// the readability, so maybe you’d like to attempt another solution
	getCarInsuranceName2(null);
	//
	// The every time you meet a null variable, you return the string “Unknown.” Nevertheless, this
	// solution is also far from ideal; now the method has four distinct exit points, making it hardly
	// maintainable. Even worse, the default value to be returned in case of a null, the string
	// “Unknown,” is repeated in three places—and hopefully not misspelled!
	getCarInsuranceName3(null);
    }

    public static String getCarInsuranceName2(final Person person) {
	// A first attempt to write a method preventing a NullPointerException is shown in the following listing.
	//
	// Each 'null' check increases the nesting level of the remaining part of the invocation chain
	if (person != null) {
	    final Car car = person.getCar();
	    if (car != null) {
		final Insurance insurance = car.getInsurance();
		if (insurance != null) {
		    return insurance.getName();
		}
	    }
	}

	return "Unknown";
    }

    public static String getCarInsuranceName3(final Person person) {
	if (person == null) { // each null check adds a further exit point.
	    return "Unknown";
	}

	final Car car = person.getCar();
	if (car == null) { // each null check adds a further exit point.
	    return "Unknown";
	}

	final Insurance insurance = car.getInsurance();
	if (insurance == null) { // each null check adds a further exit point.
	    return "Unknown";
	}

	return insurance.getName();
    }

    // --------------------------------------------------------------------------------
    // Problems with null
    public static void test02() {
	//  It’s a source of error. NullPointerException is by far the most common exception in Java.
	//
	//  It bloats your code. It worsens readability by making it necessary to fill your code with often deeply
	// nested null checks.
	//
	//  It’s meaningless. It doesn’t have any semantic meaning, and in particular it represents the wrong way
	// to model the absence of a value in a statically typed language.
	//
	//  It breaks Java philosophy. Java always hides pointers from developers except in one case: the null
	// pointer.
	//
	//  It creates a hole in the type system.
    }

    // ===========================================================

    static class Person {

	private Car car;

	public Car getCar() {
	    return car;
	}
    }

    static class Car {

	private Insurance insurance;

	public Insurance getInsurance() {
	    return insurance;
	}
    }

    static class Insurance {

	private String name;

	public String getName() {
	    return name;
	}
    }
}
