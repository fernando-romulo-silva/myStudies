package br.fernando.ch01_declarations_accessControl_enums.part03_Declare_Class_Members;

public class Test01 {

    // ====================================================================================================
    // Access Modifiers : public, protected, default, private
    //
    // You need to understand two different access issues:
    //
    // 1º Whether method code in one class can access a member of another class
    // The access occurs when a method in one class tries to access a method or a variable of another class,
    // using the dot operator (.) to invoke a method or retrieve a variable. For example:

    class Zoo {

        public String coolMethod() {
            return "Wow baby";
        }
    }

    class Moo01 {

        public void useAZoo() {
            Zoo z = new Zoo();
            // if the preceding line compiles Moo has acces to the Zoo class
            // But... does it have acces to the coolMethod()?
            System.out.println("A Zoo says :" + z.coolMethod());
            // The preceding line works because Moo can access the public method
        }
    }

    // 2º Whether a subclass can inherit a member of its superclass
    // The second type of access revolves around which, if any, members of a superclass a subclass
    // can access through inheritance.
    class Moo02 extends Zoo {

        public void useMyCoolMethod() {
            // Does an instance of Moo inherit the coolMethod()
            System.out.println("Moo says, " + this.coolMethod());
            // The preceding line works because Moo can inherit the plublic method

            // Can an instance of Moo invoke coolMethod() on an instance of Zoo?
            Zoo z = new Zoo();
            System.out.println("Zoo says," + z.coolMethod());
            // coolMethod() is public, so Moo can invoke it on a Zoo reference
        }
    }

    public static void test01() {

    }

    // ====================================================================================================
    // Public Members
    // When a method or variable member is declared public, it means all other classes, regardless of the package
    // they belong to, can access the member (assuming the class itself is visible).

    static class Sludge {

        public void testIt() {
            System.out.println("sludge");
        }
    }

    static class Roo {

        public String doRootThings01() {
            // imagine the fun code that goes here
            return "fun";
        }

        private String doRootThings02() {
            // imagine the fun code that goes here, but only Roo class knows
            return "fun";
        }
    }

    // Remember, if you see a method invoked (or a variable accessed) without the dot perator (.), it means the method
    // or variable belongs to the class where you see that code.
    // It also means that the method or variable is implicitly being accessed using the this reference. So in the preceding
    // code, the call to doRooThings() in the Cloo class could also have been written as this.doRooThings().
    // The object running the code where you see the this reference.

    static class Cloo extends Roo {

        public void testCloo() {
            System.out.println(doRootThings01());
        }

        private String doRootThings02() {
            // imagine the fun code that goes here, but only Roo class knows
            return "fun";
        }
    }

    public static void test02() {
        Sludge o = new Sludge();
        o.testIt(); // no problem, method is public
    }

    // ====================================================================================================
    // Private Members : Members marked private can’t be accessed by code in any class other than the class
    // in which the private member was declared.
    //
    // When a member is declared private, a subclass can’t inherit it.
    // You can, however, declare a matching method in the subclass. But regardless of how it looks, it is not
    // an overriding method! It is simply a method that happens to have the same name as a private method
    // (which you’re not supposed to know about) in the superclass.
    public static void test03() {
        Roo r = new Roo();

        // r.doRootThings02(); // compile error! not here because its nested class, but in different package it won't work

        // You can, however, declare a matching method in the subclass. But regardless of how it looks, it is
        // not an overriding method! It is simply a method that happens to have the same name as a private method (which you’re not
        // supposed to know about) in the superclass.
    }

    // ====================================================================================================
    //
    // Protected and Default Members
    //
    // Just a reminder, in the next several sections, when we use the word “default,” we’re talking about access control.
    // We’re NOT talking about the new kind of Java 8 interface method that can be declared default.
    //
    // The protected and default access control levels are almost identical, but with one critical difference: a default
    // member may be accessed only if the class accessing the member belongs to the same package, whereas a protected member
    // can be accessed (through inheritance) by a subclass even if the subclass is in a different package.

    static class OtherClass {

        void testIt() { // No modifier means method has default access
            System.out.println("OtherClass");

        }
    }

    static class Parent01 { // Pretend it other package

        protected int x = 9;
    }

    static class Child extends Parent01 {

        public void testIt() {

            System.out.println("x is " + x); // No problem; Child inherits x

            Parent01 p = new Parent01(); // Ca we access x using the p refernce?

            // System.out.println("x in parent is "+p.x); // Compiler Error!
        }
    }

    // Once the subclass-outside-the-package inherits the protected member, that member (as inherited by the subclass)
    // becomes private to any code outside the subclass, with the exception of subclasses of the subclass.
    // So if class Neighborinstantiates a Child object, then even if class Neighbor is in the same package as class
    // Child , class Neighbor won’t have access to the Child ’s inherited (but protected ) variable x

    public static void test04() {
        final OtherClass o = new OtherClass();
        o.testIt();
    }
    //
    // ====================================================================================================
    //
    // Default Details
    // Notice we didn’t place an access modifier in front of the variable x . Remember, if you don’t type an access
    // modifier before a class or member declaration, the access control is default, which means package level.
    // Just remember that default members are visible to subclasses only if those subclasses are in the same package
    // as the superclass.
    //
    // ====================================================================================================
    //
    // Local Variables and Access Modifiers
    //
    // Can access modifiers be applied to local variables? NO!
    // In fact, there is only one modifier that can ever be applied to local variables — final.
}
