package br.fernando.ch01_declarations_accessControl_enums.part01_Define_Classes_Interfaces;

// Class Declarations and Modifiers
public class Test01 {

    //====================================================================================================

    // The following code is a bare-bones class declaration:
    class MyClass {
    }

    // This code compiles just fine, but you can also add modifiers before the class
    // declaration. In general, modifiers fall into two categories:
    //
    // 1º Access modifiers ( public , protected , private ) The fourth access control level (called default or package access)
    //
    // 2º Nonaccess modifiers (including strictfp , final , and abstract )
    //
    // Class Access
    // When we say code from one class (class A) has access to another class (class B), it means class A can do one of three things:
    //
    // * Create an instance of class B.  
    // * Extend class B (in other words, become a subclass of class B).
    // * Access certain methods and variables within class B, depending on the access control of those methods and variables.
    // 
    // Default Access : Think of default access as package-level access, because a class with default access can be seen only 
    // by classes within the same package
    // 
    // Public Access : A class declaration with the public keyword gives all classes from all packages access to the public class.
    // Don’t forget, though, that if a public class you’re trying to use is in a different package from the class you’re writing, 
    // you’ll still need to import the public class or use the fully qualified name.
    //
    // Other (Nonaccess) Class Modifiers
    // You can modify a class declaration using the keyword final, abstract, or strictfp.

    // Strictfp : strictfp means that any method code in the class will conform strictly to the IEEE 754 standard rules for floating points,
    // and is a keyword and can be used to modify a class or a method, but never a variable.
    //
    // Final Classes : When used in a class declaration, the final keyword means the class can’t be subclassed.
    // 
    // Abstract Classes : An abstract class can never be instantiated. Its sole purpose, mission in life, raison d’être, is to be extended (subclassed).
    // You can, however, put nonabstract methods in an abstract class. You can, however, put nonabstract methods in an abstract class.

    public static void test01() {

    }

    //====================================================================================================

}
