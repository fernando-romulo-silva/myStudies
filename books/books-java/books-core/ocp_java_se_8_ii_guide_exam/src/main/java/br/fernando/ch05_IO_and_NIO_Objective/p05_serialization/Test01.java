package br.fernando.ch05_IO_and_NIO_Objective.p05_serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Test01 {

    // =========================================================================================================================================
    // Working with ObjectOutputStream and ObjectInputStream
    protected static void test01() throws IOException {
        // The magic of basic serialization happens with just two methods: one to serialize
        // objects and write them to a stream, and a second to read the stream and deserialize objects
        //
        // ObjectOutputStream.writeObject(); // serialize and write
        //
        // ObjectInputStream.readObject(); // read and deserialize

        Cat c = new Cat(); // We make a new Cat object, which as we know is serializable

        // First, we had to put all of our I/O-related code in a try/catch block.
        try {

            // Next, we had to create a FileOutputStream to write the object to.
            FileOutputStream fos = new FileOutputStream("testSer.ser");
            //
            // Then, we wrapped the FileOutputStream in an ObjectOutputStream , which is the class that has the
            // magic serialization method that we need.
            ObjectOutputStream os = new ObjectOutputStream(fos);
            //
            // Remember that the invocation of writeObject() performs two tasks:
            // it serializes the object, and then it writes the serialized object to a file
            os.writeObject(c);

            os.close();
        } catch (final Exception ex) {
            System.out.println(ex);
        }

        try {

            FileInputStream fis = new FileInputStream("testSer.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            // The readObject() method returns an Object , so we have to cast the deserialized object back to a Cat.
            // Again, we had to go through the typical I/O hoops to set this up.
            c = (Cat) ois.readObject();

            ois.close();
        } catch (final Exception ex) {

        }
    }

    // We declare that the Cat class implements the Serializable interface.
    // Serializable is a marker interface; it has no methods to implement.
    // (In the next several sections, we’ll cover various rules about when
    // you need to declare classes Serializable .)
    static class Cat implements Serializable {

        private static final long serialVersionUID = 1L;
    }

    // =========================================================================================================================================
    // Object Graphs
    static void test02() throws IOException {

        final Collar c = new Collar(3);

        final CollarNew c2 = new CollarNew(5);

        Dog d = new Dog(c, c2, 8);

        // When you serialize an object, Java serialization takes care of saving that object’s entire “object graph.”
        // That means a deep copy of everything the saved object needs to be restored.

        System.out.println("before: collar size is " + d.getCollar().getCollarSize() + " and collarNew is " + d.getCollarNew());

        try {
            FileOutputStream fos = new FileOutputStream("testSer.ser");

            ObjectOutputStream os = new ObjectOutputStream(fos);

            os.writeObject(d);
            os.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }

        try {

            FileInputStream fis = new FileInputStream("testSer.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            d = (Dog) ois.readObject();

            ois.close();
        } catch (final Exception ex) {
            System.out.println(ex);
        }

        System.out.println("after: collar size is " + d.getCollar().getCollarSize() + " and collarNew is " + d.getCollarNew());

        // But what would happen if we didn’t have access to the Collar class source code?
        // In other words, what if making the Collar class serializable was not an option? Are
        // we stuck with a non-serializable Dog ?
        //
        // Obviously, we could subclass the Collar class, mark the subclass as Serializable ,
        // and then use the Collar subclass instead of the Collar class. But that’s not always an
        // option either for several potential reasons:
        //
        // 1. The Collar class might be final, preventing subclassing.
        // OR
        // 2. The Collar class might itself refer to other non-serializable objects, and
        // without knowing the internal structure of Collar , you aren’t able to make all
        // these fixes (assuming you even wanted to try to go down that road).
        // OR
        // 3. Subclassing is not an option for other reasons related to your design.

    }

    static class Dog implements Serializable {

        private static final long serialVersionUID = 1L;

        private Collar collar;

        private transient CollarNew collarNew;

        private int dogSize;

        public Dog(final Collar collar, final CollarNew collarNew, final int dogSize) {
            super();
            this.collar = collar;
            this.dogSize = dogSize;
            this.collarNew = collarNew;
        }

        public Collar getCollar() {
            return collar;
        }

        public CollarNew getCollarNew() {
            return collarNew;
        }

        public int getDogSize() {
            return dogSize;
        }
    }

    // What did we forget? The Collar class must also be Serializable . If we modify the
    // Collar class and make it serializable, then there’s no problem
    static class Collar implements Serializable {

        private static final long serialVersionUID = 1L;

        private int collarSize;

        public Collar(int collarSize) {
            super();
            this.collarSize = collarSize;
        }

        public int getCollarSize() {
            return collarSize;
        }
    }

    static class CollarNew {

        private int collarSize;

        public CollarNew(int collarSize) {
            super();
            this.collarSize = collarSize;
        }

        public int getCollarSize() {
            return collarSize;
        }
    }

    // =========================================================================================================================================
    static void summary() {

        // **************************************************************************************************************************
        // Object Constructor Order
        // **************************************************************************************************************************
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

        // **************************************************************************************************************************
        // ObjectOutputStream
        // **************************************************************************************************************************
        Cat c = new Cat(); // We make a new Cat object, which as we know is serializable

        // First, we had to put all of our I/O-related code in a try/catch block.
        try {

            // Next, we had to create a FileOutputStream to write the object to.
            FileOutputStream fos = new FileOutputStream("testSer.ser");
            //
            // Then, we wrapped the FileOutputStream in an ObjectOutputStream , which is the class that has the
            // magic serialization method that we need.
            ObjectOutputStream os = new ObjectOutputStream(fos);
            //
            // Remember that the invocation of writeObject() performs two tasks:
            // it serializes the object, and then it writes the serialized object to a file
            os.writeObject(c);

            os.close();
        } catch (final Exception ex) {
            System.out.println(ex);
        }

        // **************************************************************************************************************************
        // ObjectInputStream
        // **************************************************************************************************************************
        try {

            FileInputStream fis = new FileInputStream("testSer.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            // The readObject() method returns an Object , so we have to cast the deserialized object back to a Cat.
            // Again, we had to go through the typical I/O hoops to set this up.
            c = (Cat) ois.readObject();

            ois.close();
        } catch (final Exception ex) {
            System.out.println(ex);
        }

        // **************************************************************************************************************************
        // Object Graphs
        // **************************************************************************************************************************

        final Collar c1 = new Collar(3);

        final CollarNew c2 = new CollarNew(5);

        Dog d = new Dog(c1, c2, 8);

        // But what would happen if we didn’t have access to the Collar class source code?
        // In other words, what if making the Collar class serializable was not an option? Are
        // we stuck with a non-serializable Dog ?
        //
        // Obviously, we could subclass the Collar class, mark the subclass as Serializable ,
        // and then use the Collar subclass instead of the Collar class. But that’s not always an
        // option either for several potential reasons:
        //
        // 1. The Collar class might be final, preventing subclassing.
        // OR
        // 2. The Collar class might itself refer to other non-serializable objects, and
        // without knowing the internal structure of Collar , you aren’t able to make all
        // these fixes (assuming you even wanted to try to go down that road).
        // OR
        // 3. Subclassing is not an option for other reasons related to your design.

    }

    // =========================================================================================================================================
    public static void main(String[] args) throws IOException {
        test02();
    }
}
