package br.fernando.ch05_IO_and_NIO_Objective.p05_serialization;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Test02 {

    // =========================================================================================================================================
    // Using writeObject and readObject
    // Java serialization has a special mechanism just for this—a set of private methods
    // you can implement in your class that, if present, will be invoked automatically during
    // serialization and deserialization.
    static void test01() {
        // In our scenario we’ve agreed that, for whatever real-world reason, we can’t serialize
        // a Collar object, but we want to serialize a Dog . To do this we’re going to implement
        // writeObject() and readObject(). By implementing these two methods you’re saying
        // to the compiler: “If anyone invokes writeObject() or readObject() concerning a Dog
        // object, use this code as part of the read and write.“
    }

    static class Dog implements Serializable {

        private static final long serialVersionUID = 1L;

        private transient Collar collar; // we can't serialize this

        private int dogSize;

        // your code for saving the Collar variables
        private void writeObject(ObjectOutputStream os) {
            // Like most I/O-related methods writeObject() can throw exceptions.
            // You can declare them or handle them, but we recommend handling them.
            try {

                // When you invoke defaultWriteObject() from within writeObject() , you’re
                // telling the JVM to do the normal serialization process for this object.
                os.defaultWriteObject();

                // In this case, we decided to write an extra int (the collar size) to the stream
                // that’s creating the serialized Dog . You can write extra stuff before and/or after
                // you invoke defaultWriteObject() . But…when you read it back in, you have
                // to read the extra stuff in the same order you wrote it.
                os.writeInt(collar.getCollarSize());

            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        }

        // Your code to read the Collar state, create a new Collar and assign it to the Dog
        private void readObject(ObjectInputStream is) {

            // Again, we chose to handle rather than declare the exceptions
            try {
                // When it’s time to deserialize, defaultReadObject() handles the normal
                // deserialization you’d get if you didn’t implement a readObject() method.
                is.defaultReadObject();

                // Finally, we build a new Collar object for the Dog using the collar size that we
                // manually serialized. (We had to invoke readInt() after we invoked defaultReadObject()
                // or the streamed data would be out of sync!)
                collar = new Collar(is.readInt());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // ------------------------
        public Dog(final Collar collar, final int dogSize) {
            super();
            this.collar = collar;
            this.dogSize = dogSize;
        }

        public Collar getCollar() {
            return collar;
        }

        public int getDogSize() {
            return dogSize;
        }
    }

    static class Collar {

        private int collarSize;

        public Collar(int collarSize) {
            super();
            this.collarSize = collarSize;
        }

        public int getCollarSize() {
            return collarSize;
        }
    }

    // =========================================================================================================================================
    static void summary() {
        // *********************************************************************************************
        // Using writeObject and readObject
        // *********************************************************************************************
        // Java serialization has a special mechanism just for this—a set of private methods you can implement in your class that, if present, 
        // will be invoked automatically during serialization and deserialization.
        //
        // Look at Dog class

    }

    // =========================================================================================================================================
    public static void main(String[] args) {

    }
}
