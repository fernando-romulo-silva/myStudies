package br.fernando.ch02_object_orientation.par03_overriding_overloading;

public class Test03 {

    // =========================================================================================================================================
    // Overloaded Methods
    static void test01() {
        // Overloaded methods let you reuse the same method name in a class, but with different
        // arguments (and, optionally, a different return type).

        // The rules aren’t too complex:
        //
        // * Overloaded methods MUST change the argument list.
        //
        // * Overloaded methods CAN change the return type.
        //
        // * Overloaded methods CAN change the access modifier.
        //
        // * Overloaded methods CAN declare new or broader checked exceptions.
        //
        // * A method can be overloaded in the same type or in a subtype.
        //
        //

        Adder adder = new Adder();

        int b = 27;

        int c = 3;

        int result = adder.addThem(b, c); // which addThem is Invoked? int version

        double doubleResult = adder.addThem(22.6, 9.3); // Which addThem double version

        // Invoking overloaded methods that take object references rather than primitives is a
        // little more interesting.

        UseAnimals ua = new UseAnimals();

        Animal animalObj = new Animal();

        Horse horseObj = new Horse();

        ua.doStuff(animalObj); // "In the Animal version"

        ua.doStuff(horseObj); // In the Horse version

        // But what if you use an Animal reference to a Horse object?
        Animal animalRefToHorse = new Horse();
        ua.doStuff(animalRefToHorse); // in the Animal version

        // Just remember—the reference type (not the object type) determines which
        // overloaded method is invoked!

        //
        // So it’s true that polymorphism doesn’t determine which overloaded version
        // is called; polymorphism does come into play when the decision is about which
        // overridden version of a method is called. But sometimes a method is both overloaded
        // and overridden.

        Animal a = new Animal();
        a.eat(); // Genereic Animal Eating Generically

        Horse h = new Horse();
        h.eat(); // Horse eating Hay

        // Plymorphism works - The actual object type (Horse), not the reference type (Animal), is used to determine which eat() is called.
        Animal ah = new Horse();
        ah.eat(); // Horse eating hay

        // The overloaded eat(String s) method is invoked
        Horse he = new Horse();
        he.eat("Apples"); // Horse eating Apples

        Animal a2 = new Animal();
        // a2.eat("treats"); // Compler error! Animal does't have an eat(String s) method.

        // Compile still looks only at the reference and sees that Animal does't have an eat();
        Animal ah2 = new Horse();
        // ah2.eat("Carrots"); // Compiler error! 
    }

    static class Adder {

        int addThem(int x, int y) {
            return x + y;
        }

        // overload the addThem method to add doubles instead of ints
        double addThem(double x, double y) {
            return x + y;
        }
    }

    static class Animal {

        void eat() {
            System.out.println("Generic Animal Eating Generically");
        }
    }

    // Notice that the Horse class has both overloaded and overridden the eat() method.
    static class Horse extends Animal {
        void eat() {
            System.out.println("Horse eating hay");
        }

        void eat(String s) {
            System.out.println("Horse eating " + s);
        }
    }

    static class UseAnimals {

        public void doStuff(Animal a) {
            System.out.println("In the Animal version");
        }

        public void doStuff(Horse h) {
            System.out.println("In the Horse version");
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
