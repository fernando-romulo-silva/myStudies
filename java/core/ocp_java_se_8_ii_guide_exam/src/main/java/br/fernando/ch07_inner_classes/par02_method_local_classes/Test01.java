package br.fernando.ch07_inner_classes.par02_method_local_classes;

import java.io.IOException;

public class Test01 {

    // =========================================================================================================================================
    // Method-Local Inner Classes
    static void test01() {
        // So to use the inner class, you must make an instance of it
        // somewhere within the method but below the inner class definition
    }

    static class MyOuter02 {

        private String x = "Outer02";

        void doStuff() {

            int localVar = 14;

            // class inside doStuff
            class MyInner {

                void seeOuter() {
                    System.out.println("Outer x is " + x);

                    System.out.println(localVar); // can access
                }
            }

            // this line must after the class
            MyInner mi = new MyInner();
            mi.seeOuter();
        }
    }

    // =========================================================================================================================================
    // What a Method-Local Inner Object Can and Can’t Do
    static void test02() {
        // A method-local inner class can be instantiated only within the method where the inner class is defined.
        // the inner class object cannot change the local variables of the method the inner class is in

        MyOuter03 mo3 = new MyOuter03();
        mo3.doStuff();

        // Just a reminder about modifiers within a method: The same rules apply to methodlocal
        // inner classes as to local variable declarations. 
        // You can’t, for example, mark a method-local inner class public, private, protected, static, transient, 
        // and the like. For the purpose of the exam, the only modifiers you can apply to a method-local
        // inner class are abstract and final, but, as always, never both at the same time.
    }

    static class MyOuter03 {

        private String x = "Outer03";

        void doStuff() {

            // marking the local variable "z" as final, although optional,
            // is a good reminder that we can’t change it if we want to
            // be able to use z in seeOuter

            final String z = "local variable";

            abstract class AbstractMyInner {

            }

            final class MyInner extends AbstractMyInner {

                void seeOuter() {
                    System.out.println("Outer x is " + x);

                    System.out.println("Local var z is " + z);
                    // z = "changing the local variable"; // won't compile!
                    x = "Can changing the instance variable!";
                }
            }

            MyInner mi = new MyInner();
            mi.seeOuter();
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws IOException {
        test02();
    }
}
