package br.fernando.ch06_generics_and_collections_Objective.part04_Generic_Types;

import java.util.ArrayList;
import java.util.List;

import br.fernando.ch06_generics_and_collections_Objective.part04_Generic_Types.Test02.Dog;

class Test03 {

    static class Car {

    }

    // =========================================================================================================================================
    // Generic Declarations
    // When you look at an API for a generics class or interface, pick a type parameter
    // ( Dog , JButton , even Object ) and do a mental find and replace on each instance of E (or
    // whatever identifier is used as the placeholder for the type parameter).
    static void test01() {

    }
    // =========================================================================================================================================
    // Making Your Own Generic Class

    static class Rental01 {

        protected List rentalPool;

        protected int maxNum;

        public Rental01(final int maxNum, List rentalPool) {
            this.maxNum = maxNum;
            this.rentalPool = rentalPool;
        }

        public Object getRental() {
            // blocks until there's somethng available
            return rentalPool.get(0);
        }

        public void returnRental(final Object object) {
            rentalPool.add(object);
        }
    }

    static class CarRental01 extends Rental01 {

        public CarRental01(int maxNum, List<Car> rentalPool) {
            super(maxNum, rentalPool);
        }

        public Car getRental() {
            return (Car) rentalPool.get(0);
        }

        public void returnRental(final Object object) {

            if (object instanceof Car) {
                super.returnRental(object);
            } else {
                System.out.println("Cannot add a non-car");
                // probably throw an exception
            }
        }
    }

    // But then, the more you look at it, the more you realize:
    // 1. You are doing your own type checking in the returnRental() method. You
    // can’t change the argument type of returnRental() to take a Car , since it’s an
    // override (not an overload) of the method from class Rental . (Overloading
    // would take away your polymorphic flexibility with Rental .)
    //
    // 2. You really don’t want to make separate subclasses for every possible kind of
    // rentable thing (cars, computers, bowling shoes, children, and so on).

    // So here’s your new and improved generic Rental class:
    static class Rental02<T> { // T is for the type parameter

        protected List<T> rentalPool; // Use the class type for the List type

        protected int maxNum;

        public Rental02(final int maxNum, List<T> rentalPool) { // constructor takes a List of the class type
            this.maxNum = maxNum;
            this.rentalPool = rentalPool;
        }

        public T getRental() { // we rent out a T
            return rentalPool.remove(0);
        }

        public void returnRental(final T object) { // and the renter returns a T
            rentalPool.add(object);
        }
    }

    static void test02() {
        Car c1 = new Car();
        Car c2 = new Car();

        List<Car> carList = new ArrayList<>();

        Rental02<Car> carRental = new Rental02<>(2, carList);

        // now get a car out, and it won't need a cast
        Car carToRent = carRental.getRental();
        carRental.returnRental(carToRent);

        // can we stick something else the original carList?
        // carList.add("fluffy");
        // Now we have a Rental class that can be typed to whatever the programmer
        // chooses, and the compiler will enforce it.

        // And you can use a form of wildcard notation in a class definition to specify a range
        // (called “bounds“) for the type that can be used for the type parameter:
    }

    // =========================================================================================================================================
    // Creating Generic Methods 573
    // Using a generic method, we can declare the method without a specific type and then get the type information based on the type of the
    // object passed to the method
    static void test03() {
        makeArrayList(1L);
        makeArrayList("Teste");
    }

    // The strangest thing about generic methods is that you must declare the type variable
    // BEFORE the return type of the method:
    public static <T> void makeArrayList(T t) { // take an object of an unkown type and use a "T" to represent the type
        List<T> list = new ArrayList<>(); // now we can create the list using "T"
        list.add(t);
    }

    // You’re also free to put boundaries on the type you declare. For example, if you want to restrict the makeArrayList()
    // method to only Number or its subtypes ( Integer , Float , and so on), you would say
    public static <T extends Number> void makeArrayList(T t) {

    }

    // In the preceding code, if you invoke the makeArrayList() method with a Dog
    // instance, the method will behave as though it looked like this all along:
    public static void makeArrayList(Dog d) {
        List<Dog> list = new ArrayList<>();
        list.add(d);
    }

    // =========================================================================================================================================
    public static void main(String[] args) {

        test01();
    }

}
