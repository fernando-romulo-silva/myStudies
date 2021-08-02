package br.fernando.ch02_object_orientation.par11_statics;

class Test01 {

    // =========================================================================================================================================
    // Static Variables and Methods
    static void test01() {
        // Variables and methods marked static belong to the type, rather than to any
        // particular instance. In fact, for classes, you can use a static method or variable
        // without having any instances of that class at all.

        new Frog01();
        new Frog01();
        new Frog01();

        System.out.println("Frog count is now " + Frog01.frogCount);
        // if frogCount wasn't static, you would get 'Cannot make a static reference to the non-static field Frog.frogCount'
    }

    static class Frog01 {

        static int frogCount = 0; // Declare an initialize static variable.

        public Frog01() {
            frogCount += 1; // Modify the value in the constructor
        }
    }

    // =========================================================================================================================================
    // Accessing Static Methods and Variables
    static void test02() {
        Frog02 f01 = new Frog02(25);
        System.out.println(f01.getFrogSize()); // access instance method using f

        // But this approach (using a reference to an object) isn’t appropriate for accessing a
        // static method because there might not be any instances of the class at all! So the way
        // we access a static method (or static variable) is to use the dot operator on the type
        // name, as opposed to using it on a reference to an instance, as follows:

        new Frog02(45);
        new Frog02(12);

        System.out.println("from static " + Frog02.getCount()); // static context

        Frog02 f02 = new Frog02(21);

        System.out.println("use ref static " + f02.getCount()); // var context, // Access a static using an instance variable

        // This is merely a syntax trick to let you use an object reference
        // variable (but not the object it refers to) to get to a static method or variable, but the
        // static member is still unaware of the particular instance used to invoke the static member

        new Frog02(10);

        new Test01().go();
    }

    void go() {
        System.out.println("from instance " + Frog02.getCount()); // instance context
    }

    static class Frog02 {

        static int frogCount = 0; // Declare an initialize static variable.

        static int getCount() { // static getter method
            return frogCount;
        }

        int frogSize = 0;

        Frog02(int s) {
            this.frogSize = s;
            frogCount += 1; // Modify the value in the constructor
        }

        public int getFrogSize() {
            return frogSize;
        }
    }

    static interface FrogBoilable {

        // interface static method
        static int getCtoF(int cTemp) {
            return (cTemp * 9 / 5) + 32;
        }

        // interface default method
        default String hop() {
            return "hopping";
        }
    }

    static class DontBoilFrogs implements FrogBoilable {

        void go() {
            // A legal invocation of an interface’s default method.
            System.out.println(hop()); // 1 - ok for default method

            // An illegal attempt to invoke an interface’s static method.
            // System.out.println(getCtoF(100)); // 2 - cannot find symbol

            // THE legal way to invoke an interface’s static method
            System.out.println(FrogBoilable.getCtoF(100)); // 3 - ok for static method

            DontBoilFrogs dbf = new DontBoilFrogs();
            // Another illegal attempt to invoke an interface’s static method.
            // System.out.println(dbf.getCtoF(100)); // 4 - cannot find symbol

        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test02();
    }

}
