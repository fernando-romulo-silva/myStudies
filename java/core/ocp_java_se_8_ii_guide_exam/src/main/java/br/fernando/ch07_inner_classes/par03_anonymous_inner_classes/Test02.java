package br.fernando.ch07_inner_classes.par03_anonymous_inner_classes;

public class Test02 {

    // =========================================================================================================================================
    // Argument-Defined Anonymous Inner Classes
    static void test01() {
        Bar b = new Bar();

        b.doStuff(new Foo() {
            @Override
            public void foof() {
                System.out.println("foofy");
            } // end foof() method
        }, // end inner class def
                10); // end arg, and b.doStuff stmt.

    }// end test01

    static interface Foo {

        void foof();
    }

    static class Bar {

        void doStuff(Foo f, int x) {
            f.foof();
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
