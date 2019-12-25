package br.fernando.ch05_IO_and_NIO_Objective.p05_serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Test03 {

    // =========================================================================================================================================
    // How Inheritance Affects Serialization
    //
    // Remember, when an object is constructed using new (as opposed to being deserialized),
    // the following things happen (in this order):
    //
    // 1. All instance variables are assigned default values.
    //
    // 2. The constructor is invoked, which immediately invokes the superclass
    // constructor (or another overloaded constructor, until one of the overloaded
    // constructors invokes the superclass constructor).
    //
    // 3. All superclass constructors complete.
    //
    // 4. Instance variables that are initialized as part of their declaration are assigned
    // their initial value (as opposed to the default values they’re given prior to the
    // superclass constructors completing).
    //
    // 5. The constructor completes.
    //
    // But these things do not happen when an object is deserialized.
    // We want only the values saved as part of the serialized state of the object to be reassigned.
    //
    // =========================================================================================================================================
    static void test01() {
        // Because Animal is not serializable, any state maintained in the Animal class, even
        // though the state variable is inherited by the Dog , isn’t going to be restored with the Dog
        // when it’s deserialized!
        //
        // This is because the non-serializable class constructor will run!
        //
        // In fact, every constructor above the first non-serializable class constructor will also
        // run, no matter what, because once the first super constructor is invoked (during deserialization),
        // it, of course, invokes its super constructor and so on, up the inheritance tree.

        Dog d = new Dog("Fido", 35);

        System.out.println("before: " + d.name + " " + d.weight);

        // write
        try {
            FileOutputStream fs = new FileOutputStream("testSer.ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(d);
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // read
        try {
            FileInputStream fis = new FileInputStream("testSer.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            d = (Dog) ois.readObject();
            ois.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("after: " + d.name + " " + d.weight);
        // which produces the output:
        //
        // before: Fido 35
        // after: Fido 42
        //
        // The key here is that because Animal is not serializable, when the Dog was
        // deserialized, the Animal constructor ran and reset the Dog ’s inherited weight variable.

    }

    static class Animal { // not serializable

        int weight = 42;
    }

    static class Dog extends Animal implements Serializable {

        String name;

        public Dog(final String name, final int weight) {
            super();
            this.name = name; // not inherited
            this.weight = weight; // inherited
        }
    }

    // =========================================================================================================================================
    static void summary() {
        // **************************************************************************************************
        // How Inheritance Affects Serialization
        // **************************************************************************************************
        //
        // Look at Animal and Dog class...
        //
        // Because Animal is not serializable, any state maintained in the Animal class, even
        // though the state variable is inherited by the Dog , isn’t going to be restored with the Dog
        // when it’s deserialized!
        //
        // This is because the non-serializable class constructor will run!
        //
        // In fact, every constructor above the first non-serializable class constructor will also
        // run, no matter what, because once the first super constructor is invoked (during deserialization),
        // it, of course, invokes its super constructor and so on, up the inheritance tree.
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
