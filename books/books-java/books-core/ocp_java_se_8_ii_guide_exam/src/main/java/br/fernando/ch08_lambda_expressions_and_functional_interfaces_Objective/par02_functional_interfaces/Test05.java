package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par02_functional_interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class Test05 {

    // =========================================================================================================================================
    // Working with Predicates
    static void test01_01() {
        // Predicate is a functional interface, with a functional method, test() , which returns a boolean.
        // That means we can use the built-in Predicate interface in place of the DogQuerier interface. Let’s do that:

        Dog boi = new Dog("boi", 30, 6);
        Dog clover = new Dog("clover", 35, 12);
        Dog zooey = new Dog("zooey", 45, 8);

        List<Dog> dogs = Arrays.asList(boi, clover, zooey);

        Predicate<Dog> p = d -> d.age > 9;

        System.out.println("Is Boi older than 9? " + p.test(boi));

        System.out.println("Is Clover older than 9? " + p.test(clover));

        // Now let’s check out how Predicate s are used in the JDK. One example is the
        // removeIf() method of ArrayList , which takes a Predicate and removes an item
        // from the ArrayList if the predicate’s test() method returns true for that item

        Predicate<Dog> findCs = d -> d.name.startsWith("c");

        dogs.removeIf(findCs);
    }

    static class Dog {

        String name;

        int age;

        int weight;

        public Dog(String name, int age, int weight) {
            super();
            this.name = name;
            this.age = age;
            this.weight = weight;
        }

        public void bark() {
            System.out.println("Woof!");
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    // =========================================================================================================================================
    // Predicate’s Default and Static Methods
    static void test01_02() {
        // The default methods, and() , or() , and negate() , are there so you can chain predicates together,
        // much like we did with consumers and the andThen() method. This can save you time creating new predicates
        // that are logical combinations of predicates you already have

        Dog boi = new Dog("boi", 30, 6);
        Dog clover = new Dog("clover", 35, 12);
        Dog zooey = new Dog("zooey", 45, 8);

        List<Dog> dogs = Arrays.asList(boi, clover, zooey);

        final Predicate<Dog> name = d -> d.name.equals("boi");

        final Predicate<Dog> age = d -> d.age == 6;

        final Predicate<Dog> nameAndAge01 = d -> name.and(age).test(d);

        System.out.println("----- Test Name and Age of Boi ------");
        System.out.println("Is boi named 'boi' and age 6: " + nameAndAge01.test(boi));

        boi.age = 7;

        System.out.println("Is boi named 'boi' and age 6: " + nameAndAge01.test(boi));
        System.out.println("Is boi named 'boi' and age 6: " + nameAndAge01.test(boi));

        // We can simplify the nameAndAge Predicate even further by writing
        final Predicate<Dog> nameAndAge02 = name.and(age);

        // This works! Remember, what this does is create a new Predicate that is the
        // composition of two Predicates , name and age . So the result of calling the and()
        // method on the name predicate with the argument age is a new Predicate<Dog> that
        // and s the result of calling name.test() on a dog and age.test() on that same dog.

        // ---------------------------------------------------------------------------------------
        // The static method in Predicate , isEqual() , just gives you a way to test if one
        // object equals another, using the same test as equals() uses when comparing two
        // objects (that is, are they the same object?).
        Predicate<Dog> p01 = Predicate.isEqual(zooey);

        System.out.println("Is aiko the same object as zooey? " + p01.test(boi));
        System.out.println("Is aiko the same object as zooey? " + p01.test(zooey));
        //
        //
        // Along with Predicate , you’ll find BiPredicate , DoublePredicate , IntPredicate ,
        // and LongPredicate in java.util.function . You can probably guess what these do.
        // Yep, BiPredicate ’s test() method takes two arguments, whereas DoublePredicate ,
        // IntPredicate , and LongPredicate each take one argument of a primitive type (to avoid autoboxing).

        IntPredicate universeAnswer = i -> i == 42;
        System.out.println("Is the answer 42? " + universeAnswer.test(42));
    }

    // =========================================================================================================================================
    // BiPredicate // 677S
    static void test01_03() {
        // BiPredicate is just a variation on Predicate that allows you to pass in two objects for testing instead of one

        List<Book> books = Arrays.asList( //
                new Book("OCP8 Java Certification", 53.0), //
                new Book("Is Java Coffee or Programming?", 39.86), //
                new Book("Whle you were out Java happened", 12.99) //
        );

        BiPredicate<String, Double> javaBuy = (name, price) -> name.contains("Java");
        BiPredicate<String, Double> priceBuy = (name, price) -> price < 55.00;

        BiPredicate<String, Double> definetelyBuy = javaBuy.and(priceBuy);

        books.forEach(book -> {

            if (definetelyBuy.test(book.name, book.price)) {
                System.out.println("You should definitely buy " + book.name + " (" + book.price + ")");
            }
        });

        // 677
    }

    static class Book {

        String name;

        Double price;

        public Book(String name, Double price) {
            super();
            this.name = name;
            this.price = price;
        }
    }

    // =========================================================================================================================================
    // Caveat Time
    static void test01_04() {

        // The main reason to use lambda expressions is if you’ll end up using them in other
        // ways too, and if having the code packaged up into lambdas will help make your code
        // more concise and get you some code reuse

        //
        // A big caveat here is that although we’re showing lots of examples of building
        // lambda expressions using the built-in functional interfaces so you get practice for the
        // exam, once you’re back in the real world, think about whether you really need a lambda expression before you write on
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
