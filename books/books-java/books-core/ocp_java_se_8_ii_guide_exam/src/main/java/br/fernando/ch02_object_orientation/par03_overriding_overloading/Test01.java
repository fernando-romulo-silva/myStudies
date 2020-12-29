package br.fernando.ch02_object_orientation.par03_overriding_overloading;

public class Test01 {

    // =========================================================================================================================================
    // Overridden Methods
    static void test01() {
        // The key benefit of overriding is the ability to define behavior that’s specific to a particular ubtype

        Animal a = new Animal();

        Animal b = new Horse(); // Animal ref, but a Horse object

        a.eat(); // runs the Animal version of eat
        b.eat(); // runs the Horse version of eat

        // In the preceding code, the test class uses an Animal reference to invoke a method on
        // a Horse object. Remember, the compiler will allow only methods in class Animal to be
        // invoked when using a reference to an Animal

        Animal c = new Horse();
        // c.buck(); // Can't invoke buck(); anmal class doesn't have that method

        // The rules for overriding a method are as follows:
        //
        // 1 - The argument list must exactly match that of the overridden method.
        // If they don’t match, you can end up with an overloaded method you didn’t intend.
        //
        // 2 - The return type must be the same as, or a subtype of, the return type declared in
        // the original overridden method in the superclass.
        //
        // 3 - The access level can’t be more restrictive than that of the overridden method.
        //
        // 4 - The access level CAN be less restrictive than that of the overridden method.
        //
        // 5 - Instance methods can be overridden only if they are inherited by the subtype.
        // A subtype within the same package as the instance’s supertype can override any supertype
        // method that is not marked private or final. A subtype in a different package can override
        // only those non final methods marked public or protected (since protected methods are
        // inherited by the subtype)
        //
        // 6 - The overriding method CAN throw any unchecked (runtime) exception, regardless of whether
        // the overridden method declares the exception.
        //
        // 7 - The overriding method must NOT throw checked exceptions that are new or
        // broader than those declared by the overridden method.
        //
        // 8 - The overriding method can throw narrower or fewer exceptions. Just because
        // an overridden method “takes risks” doesn’t mean that the overriding subtype’s
        // exception takes the same risks. Bottom line: an overriding method doesn’t have
        // to declare any exceptions that it will never throw, regardless of what the
        // overridden method declares.
        //
        // 9 - You cannot override a method marked final 
        //
        // 10 - You cannot override a method marked static . 
        //
        // 11 - If a method can’t be inherited, you cannot override it. 
        // Remember that overriding implies that you’re reimplementing a method you inherited! 
    }

    static class Animal {

        void eat() {
            System.out.println("Generic Animal Eating Genercially");
        }
    }

    static class Horse extends Animal {

        void eat() {
            System.out.println("Horse eating hay, oats, and horse treats");
        }

        void buck() {

        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
