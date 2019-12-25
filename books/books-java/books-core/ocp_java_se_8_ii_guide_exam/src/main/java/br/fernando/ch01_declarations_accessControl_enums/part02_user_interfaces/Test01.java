package br.fernando.ch01_declarations_accessControl_enums.part02_user_interfaces;

public class Test01 {

    // =====================================================================================================================================
    // Declaring an Interface
    //
    // An interface is a contract. Think of an interface as a 100-percent abstract class. Like an abstract class, an
    // interface defines abstract methods that take the following form.
    // But although an abstract class can define both abstract and nonabstract methods, an interface generally has
    // only abstract methods. Another way interfaces differ from abstract classes is that interfaces have very little
    // flexibility in how the methods and variables defined in the interface are declared. These rules are strict:

    // * All interface methods are implicitly public. Unless declared as default or static, they are also implicitly abstract.
    // In other words, you do not need to actually type the public or abstract modifiers in the method declaration, but
    // the method is still always public and abstract.
    //
    // * All variables defined in an interface must be public, static, and final—in other words, interfaces can declare only
    // constants, not instance variables. Interface methods cannot be marked final, strictfp, or native. (More on
    // these modifiers later in the chapter.)
    //
    // * An interface can extend one or more other interfaces.
    //
    // * An interface cannot extend anything but another interface.
    //
    // * An interface cannot implement another interface or class.
    //
    // * An interface must be declared with the keyword interface.
    //
    // * Interface types can be used polymorphically (see Chapter 2 for more details).
    //
    // The following is a legal interface declaration:

    public abstract interface Rollable01 {
    }

    public interface Rollable02 {
    }

    public static void test01() {
    }

    public interface Bounceable {

        public abstract void bounce();

        // Typing in the public and abstract modifiers on the methods is redundant
        void setBounceFactor(int bf);
    }

    // =====================================================================================================================================
    //
    // Declaring Interface Constants
    // They must always be "public static final"
    // Just as interface methods are always public and abstract whether you say so in the code or
    // not, any variable defined in an interface must be—and implicitly is—a public constant.
    //
    // See if you can spot the problem with the following code (assume two separate files):
    //
    interface Foo {

        int BAR = 42;

        void go();
    }

    class Zap implements Foo {

        @Override
        public void go() {
            // BAR = 27; compile error
        }

    }

    public static void test02() {

    }

    // =====================================================================================================================================
    //
    // Declaring default Interface Methods
    // These concrete methods are called default methods:
    //
    // * default methods are declared by using the default keyword. The default keyword can be used only with
    // interface method signatures, not class method signatures.
    //
    // * default methods are public by definition, and the public modifier is optional.
    //
    // * default methods cannot be marked as private, protected, static, final, or abstract.
    //
    // * default methods must have a concrete method body.
    //
    // Here are some examples of legal and illegal default methods:

    interface TestDefault {

        default int m1() { // legal
            return 1;
        }

        public default void m2() { // legal

        }

        // static defaul void m3(){} // default cannot be marked static

        // dfault void m4(); // default must have a method body.
    }
    //
    // =====================================================================================================================================
    // Declaring static Interface Methods
    //
    // For now, we’ll focus on the basics of declaring and using static interface methods:
    // 
    // * static interface methods are declared by using the static keyword.
    // * static interface methods are public, by default, and the public modifier is optional.
    // * static interface methods cannot be marked as private, protected, final, or abstract.
    // * static interface methods must have a concrete method body.
    // * When invoking a static interface method, the method’s type (interface name) MUST be included in the invocation.

    interface StaticInterface {
        static int m1() {
            return 43;
        } // legal

        public static void m2() {
        } // legal

        // final static void m3() { } // Illegal: final not allowed

        // abstract static void m4(){} // Illegal: abstract not allowed

        // static void m5(); // Illegal: needs a method body
    }

    public class TestSIF implements StaticInterface {

        public void t1() {
            System.out.println(StaticInterface.m1()); // legal: m1()'s type must be included

            new TestSIF().go(); // Legal

            // System.out.println(m1()); // Illegal: reference to interface is required
        }

        void go() {
            System.out.println(StaticInterface.m1()); // also legal from an instance
        }

    }

}
