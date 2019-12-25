package br.fernando.ch06_generics_and_collections_Objective.part04_Generic_Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

public class Test02 {

    static class Parent {
    }

    static class Child extends Parent {
    }

    // =========================================================================================================================================
    // Polymorphism and Generics
    //
    public static void test01() {
        // You’ve already seen that polymorphism applies to the “base” type of the collection:
        final List<Integer> myList01 = new ArrayList<>();

        // No, it doesn’t work. There’s a very simple rule here—the type of the variable declaration must match the
        // type you pass to the actual object type.
        // List<Parent> myList02 = new ArrayList<Child>();

        // These are wrong:
        // List<Object> myList03 = new ArrayList<JButton>();
        // List<Number> myList04 = new ArrayList<Integer>();

        // But these are fine:
        List<JButton> myList03 = new ArrayList<JButton>();

        List<Integer> myList04 = new ArrayList<Integer>();

        // List and ArrayList are the base type and JButton is the generic type. So an
        // ArrayList can be assigned to a List , but a collection of <JButton> cannot be assigned
        // to a reference of <Object> , even though JButton is a subtype of Object

        //
        // The part that feels wrong for most developers is that this is NOT how it works with arrays,
        // where you are allowed to do this:
        Parent[] myArray01 = new Child[3];
    }
    // =========================================================================================================================================
    // Generic Methods
    //
    // One of the biggest benefits of polymorphism is that you can declare, say, a method argument of a particular type and
    // at runtime be able to have that argument refer to any subtype—including those you’d never known about at the time you
    // wrote the method with the supertype argument.

    static abstract class Animal {

        public abstract void checkup();
    }

    static class Dog extends Animal {

        // implement Dog-specific code
        @Override
        public void checkup() {
            System.out.println("Dog checkup");
        }
    }

    static class Cat extends Animal {

        // implement Cat-specific code
        @Override
        public void checkup() {
            System.out.println("Cat checkup");
        }
    }

    static class Bird extends Animal {

        // implement Bir-specific code
        @Override
        public void checkup() {
            System.out.println("Bird checkup");
        }
    }

    // So in the AnimalDoctor class, you’d probably have a polymorphic method:
    public static void checkAnimal(final Animal animal) {
        // does not matter which animal subtype each
        // Animal's overridden checkup() method runs
        animal.checkup();
    }

    // Again, we don’t want overloaded methods with arrays for each potential Animal subtype, so
    // we use polymorphism in the AnimalDoctor class:
    public static void checkAnimals(final Animal[] animals) {
        for (final Animal animal : animals) {
            animal.checkup();
        }
    }

    public static void test02() {
        // test it
        Dog[] dogs = { new Dog(), new Dog() };
        Cat[] cats = { new Cat(), new Cat() };
        Bird[] birds = { new Bird() };

        checkAnimals(birds);
        checkAnimals(dogs);

        final List<Dog> dogsList = Arrays.asList(new Dog(), new Dog());

        // You simply CANNOT assign the individual ArrayList s of Animal subtypes (<Dog> , <Cat> , or <Bird> ) to an ArrayList
        // of the supertype <Animal> , which is the declared type of the argument.
        // checkAnimals(dogList);

        // We’ll get there, but first, let’s step way back for a minute and consider this perfectly legal scenario:
        final Animal[] animalsArray = new Animal[3];
        animalsArray[0] = new Cat();
        animalsArray[2] = new Dog();

        // So here, we’re using polymorphism, not for the object that the array reference
        // points to, but rather what the array can actually HOLD—in this case, any subtype of
        // Animal . You can do the same thing with generics:
        final List<Animal> animalsList = new ArrayList<>();
        animalsList.add(new Cat()); // Ok
        animalsList.add(new Dog()); // ok

        // no problem, send the Dog[] to the method
        addAnimal(dogs);

        // no problem, sent the Cat[] to the method
        addAnimal(cats); // Eeek! we jus put a Dog in a Cat array! you’ll get the exception.

        // The problem is that if you passed in an array of an Animal subtype ( Cat , Dog , or Bird ),
        // the compiler does not know. The compiler does not realize that out on the heap somewhere is
        // an array of type Cat[] , not Animal[] , and you’re about to try to add a Dog to it.
        // To the compiler, you have passed in an array of type Animal , so it has no way to recognize the problem.

        // The reason the compiler won’t let you pass an ArrayList<Dog> into a method that
        // takes an ArrayList<Animal> is because within the method, that parameter is of type
        // ArrayList<Animal> , and that means you could put any kind of Animal into it

        List<Animal> animalsList02 = new ArrayList<>();
        animalsList02.add(new Dog());
        animalsList02.add(new Dog());

        addAnimal(animalsList02);

        List<Dog> animalsList03 = new ArrayList<>();
        animalsList03.add(new Dog());
        animalsList03.add(new Dog());

        // addAnimal(animalsList03); // Don't compile!

    }

    // We know it won’t work correctly, but let’s try changing the AnimalDoctor code to
    // use generics instead of arrays:
    public static void checkAnimals(final ArrayList<Animal> animals) {
        for (final Animal animal : animals) {
            animal.checkup();
        }
    }

    // no problem, any Animal works in Animal[]
    public static void addAnimal(Animal[] animals) {
        animals[0] = new Dog();
    }

    // sometimes allowed ...
    // Actually, you CAN do this under certain conditions. The previous code WILL
    // compile just fine IF what you pass into the method is also an ArrayList<Animal>.
    // This is the part where it differs from arrays, because in the array version, you COULD
    // pass a Dog[] into the method that takes an Animal[]
    public static void addAnimal(ArrayList<Animal> animals) {
        animals.add(new Dog());
    }

    public static void addAnimal(List<Animal> animals) {
        animals.add(new Dog()); // this is always legal, since Dog can be assigned to an Animal reference
    }

    // And there IS a mechanism to tell the compiler that you can take any generic subtype of
    // the declared argument type because you won’t be putting anything in the collection.
    // And that mechanism is the wildcard <?>
    public static void addAnimalNew01(List<? extends Animal> animals) {
        // animals.add(new Dog()); // not compile! Can't add if we use <? extends Animal>
        for (final Animal animal : animals) {

        }
    }

    // “Hey, compiler, please accept any List with a generic type that is of type Dog or a supertype of Dog.
    // Nothing lower in the inheritance tree can come in, but anything higher than Dog is okay.“
    public static void addAnimalNew02(List<? super Dog> animals) {
        animals.add(new Dog()); //
        // animals.add(new Cat()); don't work

        // You are telling the compiler that you can accept the type on the right side of super or any of its
        // supertypes, since—and this is the key part that makes it work—a collection declared as
        // any supertype of Dog will be able to accept a Dog as an element. List<Object> can
        // take a Dog . List<Animal> can take a Dog . And List<Dog> can take a Dog .
    }

    // which is the wildcard <?> without the keywords extends or super , simply means “any type.”
    // So that means any type of List can be assigned to the argument. That could be a List of <Dog> , <Integer> ,
    // <JButton> , <Socket> , whatever. And using the wildcard alone, without the keyword
    // super (followed by a type), means that you cannot ADD anything to the list referred to as List<?> .
    public static void foo01(final List<?> list) {

        // list.add(""); // don't compile
    }

    // ist<Object> is completely different from List<?> . List<Object> means that the
    // method can take ONLY a List<Object> .
    public static void foo02(final List<Object> list) {

    }
    // By the way, List<? extends Object> and List<?> are absolutely identical! They
    // both say, “I can refer to any type of object.” But as you can see, neither of them is the
    // same as List<Object> .

    public static void test03() {
        // As a little review before we move on with generics, look at the following
        // statements and figure out which will compile:

        final List<?> list01 = new ArrayList<Dog>();

        final List<? extends Animal> list02 = new ArrayList<Dog>();

        // final List<?> list03 = new ArrayList<? extends Animal>();
        // You cannot use wildcard notation in the object creation.

        // final List<? extends Dog> list04 = new ArrayList<Integer>();
        // You cannot assign an Integer list to a reference that takes only a Dog (including any subtypes of Dog, of course).

        final List<? super Dog> list04 = new ArrayList<Animal>();

        //final List<? super Animal> list05 = new ArrayList<Dog>();
        // You cannot assign a Dog to <? super Animal>. The Dog is too "low" in the class hierarchy.
        // Only <Animal> or <Object> would have been legal.

    }

    // =========================================================================================================================================
    public static void main(String[] args) {

        test02();
    }
}
