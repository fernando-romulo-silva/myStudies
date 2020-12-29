package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par01_lambda_symtax;

public class Test01 {

    // =========================================================================================================================================
    // Lambda Expression Syntax
    // Let’s take another look at the syntax and talk about the varieties of lambda expression
    // syntax you might expect to see in the real world and on the exam.
    static void test01() {

        DogQuerier dq01 = new DogQuerier() {

            public boolean test(Dog d) {
                return d.getAge() > 9;
            }
        };

        // That’s the same as the type of the interface and the same as the type of the instance
        // being created by the inner class.
        DogQuerier dq02 = (d) -> d.getAge() > 9;

        // It’s perfectly legal to leave off the parentheses around the parameter and write the lambda like this instead
        DogQuerier dq03 = d -> d.getAge() > 9;

        // in this example, you can supply the type if you want to, but if you do, you’ll have to use
        // the parentheses around the parameter
        DogQuerier dp04 = (Dog d) -> d.getAge() > 9;

        // The rule is, if there is only one expression in the lambda, then the value of that
        // expression gets returned by default, and you don’t need a return. In fact, if you try to write
        //
        // What about the return value’s type? That, too, can be inferred from the DogQuerier
        // interface definition. And wait a sec, where’d that return go to anyway?

        // DogQuerier dq05 = return d.getAge() > 9; // does not compile
        // If you want to write return, then you’ll have to write the lambda like this:
        DogQuerier dq05 = d -> {
            return d.getAge() > 9;
        };

        // In other words, if the body of your lambda is anything more than an expression—
        // that is, a statement or multiple statements—you’ll need to use the curly braces.
        DogQuerier dq06 = d -> {
            System.out.println("Testing " + d.getName());
            return d.getAge() > 9;
        };
    }

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
    // Passing Lambda Expressions to Methods
    // Lambda expressions are easy to pass to methods. It’s a bit like passing a block of code to a method.
    static void test02() {

        // We can pass a lambda expression directly:
        new DogsPlay(d -> d.getAge() > 9);
    }

    static class DogsPlay {

        DogQuerier dogQuerier;

        public DogsPlay(DogQuerier dogQuerier) {
            this.dogQuerier = dogQuerier;
        }

        public boolean doQuery(Dog d) {
            return dogQuerier.test(d);
        }
    }

    // =========================================================================================================================================
    // Accessing Variables from Lambda Expressions
    static void test03() {

        // Just like you would in the body of a method, the lambda is essentially just creating a nested block, so you can’t
        // use the same variable name as you’ve used in the enclosing scope.

        {
            // nested block
        }

        int numCats = 3;

        int bone = 2;

        // A lambda expression “captures” variables from the enclosing scope, so you can
        // access those variables in the body of the lambda, but those variables must be final or
        // effectively final. An effectively final variable is a variable or parameter whose value
        // isn’t changed after it is initialized. 

        // numCats = 5; // if you remove this comment, won't compile, because 'numCats' won't be a 'effectively final'

        DogQuerier dqWithCats = d -> {
            int numBalls = 1; // completely new varable local to lambda

            numBalls++; // can modify numBalls

            System.out.println("Number of balls: " + numBalls); // can acess numBalls

            System.out.println("Number of cats: " + numCats); // can acces numCats

            System.out.print("Bone :" + bone);

            // numCats++; // liked block, or anonymous class, you can't change

            // int bone = 5; // won't compile! trying to redeclare bone

            return d.getAge() > 9;
        };

        // numCats = 10; // even before the lambda
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
