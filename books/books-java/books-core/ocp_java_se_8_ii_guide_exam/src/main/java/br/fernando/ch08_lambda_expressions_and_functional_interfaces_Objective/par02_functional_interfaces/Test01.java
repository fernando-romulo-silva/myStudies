package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par02_functional_interfaces;

public class Test01 {

    // =========================================================================================================================================
    // Functional Interfaces
    static void test01() {
        // Lambda expressions work by standing in for instances of classes that implement interfaces with one abstract method
        // This annotation is not required but can be helpful when you want to ensure you don’t inadvertently add methods to a
        // functional interface that will then break other code.
    }

    @FunctionalInterface
    static interface DogQuerier {

        boolean test(Dog d);
    }

    static class Dog {

        String name;

        int age;

        int getAge() {
            return age;
        }

        String getName() {
            return name;
        }
    }

    // =========================================================================================================================================
    // What Makes an Interface Functional?
    static void test02() {
        // We already said that a functional interface is an interface with one and only one abstract method
        // To sum up, here is the rule for functional interfaces: A functional interface is an
        // interface that has one abstract method. Default methods don’t count; static methods
        // don’t count; and methods inherited from Object don’t count.
    }

    // =========================================================================================================================================
    // Categories of Functional Interfaces
    static void test03() {
        // Suppliers : Suppliers supply results. java.util.function.Supplier ’s functional
        // method, get() , never takes an argument, and it always returns something.
        //
        // Consumers : Consumers consume values. java.util.function.Consumer ’s functional
        // method, accept() , always takes an argument and never returns anything.
        //
        // Predicates : Predicates test things (and do logical operations). java.util.function.Predicate’s 
        // functional method, test() , takes a value, does a logical test, and returns true or false.
        // 
        // Functions : You can think of functions as the most generic of the functional interfaces.
        // java.util.function.Function ’s functional method, apply() , takes an argument and returns a value

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
