package br.com.fernando.ch01_why_should_you_care.part04_Default_methods;

// Default methods
public class Test {
    // Default methods are added to Java 8 largely to support library designers by enabling them to
    // write more evolvable interfaces.

    public static interface TestInterface {

	default void execute() {
	    System.out.println("Your test!");
	}
    }

    public static void test1() {

    }

    public static void main(String[] args) {

    }

}
