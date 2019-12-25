package br.com.fernando.ch03_lambda_expressions.part02_Where_and_how_to_use_lambdas;

// Where and how to use lambdas
public class Test {

    // Functional interface
    //
    // In a nutshell, a functional interface is an interface that specifies exactly one abstract method.
    // You already know several other functional interfaces in the Java API such as Comparator and Runnable
    //
    // An interface is still a functional interface if it has many default methods as long as it
    // specifies only one abstract method.
    //
    // Function descriptor
    //
    // This annotation is used to indicate that the interface is intended to be a functional interface.
    // The compiler will return a meaningful error if you define an interface using the @FunctionalInterface
    // annotation and it isn’t a functional interface
    @FunctionalInterface
    interface InterfaceTest {

        public boolean execute();

    }

    public static void test1() {

        InterfaceTest it = () -> true;

        System.out.println(it.execute());
    }

    //
    public static void main(String[] args) {

    }
}
