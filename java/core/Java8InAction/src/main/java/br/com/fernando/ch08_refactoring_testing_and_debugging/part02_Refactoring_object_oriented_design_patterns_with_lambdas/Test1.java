package br.com.fernando.ch08_refactoring_testing_and_debugging.part02_Refactoring_object_oriented_design_patterns_with_lambdas;

import java.util.function.Consumer;

// Refactoring object-oriented design patterns with lambdas
//
// Lambda expressions provide yet another new tool in the programmer’s toolbox. They can
// provide alternative solutions to the problems the design patterns are tackling but often with less
// work and in a simpler way. Many existing object-oriented design patterns can be made
// redundant or written in a more concise way using lambda expressions.
public class Test1 {

    // Strategy
    public static void test01() {
        // The strategy design pattern
        //
        //  An interface to represent some algorithm (the interface Strategy)
        //  One or more concrete implementations of that interface to represent multiple algorithms (the concrete classes ConcreteStrategyA, ConcreteStrategyB)
        //  One or more clients that use the strategy objects
        //
        //
        // old school
        final Validator v1 = new Validator(new IsNumeric());
        System.out.println(v1.validate("aaaa"));
        final Validator v2 = new Validator(new IsAllLowerCase());
        System.out.println(v2.validate("bbbb"));

        // By now you should recognize that ValidationStrategy is a functional interface (in addition, it has
        // the same function descriptor as Predicate<String>). This means that instead of declaring new
        // classes to implement different strategies, you can pass lambda expressions directly, which are
        // more concise:

        // with lambdas
        final Validator v3 = new Validator((String s) -> s.matches("\\d+"));
        System.out.println(v3.validate("aaaa"));
        //
        final Validator v4 = new Validator((String s) -> s.matches("[a-z]+"));
        System.out.println(v4.validate("bbbb"));
    }

    // Interface Strategy
    public static interface ValidationStrategy {

        boolean execute(String s);
    }

    // Implemention(s) of that interface
    public static class IsAllLowerCase implements ValidationStrategy {

        @Override
	public boolean execute(String s) {
            return s.matches("[a-z]+");
        }
    }

    public static class IsNumeric implements ValidationStrategy {

        @Override
	public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }

    public static class Validator {

        private final ValidationStrategy strategy;

        public Validator(ValidationStrategy v) {
            this.strategy = v;
        }

        public boolean validate(String s) {
            return strategy.execute(s);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Template method
    public static void test02() {
        // The template method design pattern is a common solution when you need to represent the
        // outline of an algorithm and have the additional flexibility to change certain parts of it. 
	// Okay, it  sounds a bit abstract. In other words, the template method pattern is useful when you find
        // yourself in a situation such as “I’d love to use this algorithm but I need to change a few lines so it
        // does what I want".
        //
        final OnlineBanking normalOnLine = new OnlineBanking() {

            @Override
            void makeCustomerHappy(Customer c) {
                System.out.println("Customer happy!");
            }
        };
        //
        // Using lambda expressions
        //
        final OnlineBankingLambda onlineBankingLambda = new OnlineBankingLambda();
        //
        // You can tackle the same problem (creating an outline of an algorithm and letting implementers
        // plug in some parts) using your favorite lambdas!
	onlineBankingLambda.processCustomer(1337, (Customer c) -> System.out.println("Hello!"));
	//
	//
	onlineBankingLambda.processCustomer(1337, (Customer c) -> System.out.println("Hello Sr!"));
    }

    // Normal Template Method
    public static abstract class OnlineBanking {

        public void processCustomer(int id) {
            final Customer c = Database.getCustomerWithId(id);
            makeCustomerHappy(c);
        }

        abstract void makeCustomerHappy(Customer c);
    }

    // Lambda Template Method
    public static class OnlineBankingLambda {

        public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
            final Customer c = Database.getCustomerWithId(id);
            makeCustomerHappy.accept(c);
        }
    }

    // dummy Customer class
    public static class Customer {
    }

    // dummy Database class
    public static class Database {

        static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }

}
