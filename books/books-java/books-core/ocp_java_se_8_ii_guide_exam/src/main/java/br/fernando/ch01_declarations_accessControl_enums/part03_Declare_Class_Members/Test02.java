package br.fernando.ch01_declarations_accessControl_enums.part03_Declare_Class_Members;

// Nonaccess Member Modifiers
public class Test02 {

    // ====================================================================================================
    // Final Methods
    // The final keyword prevents a method from being overridden in a subclass and is often used to enforce the API
    // functionality of a method. This can’t-be-overridden restriction provides for safety and security,
    // but you should use it with great caution.

    // ====================================================================================================
    // Final Arguments
    class Record {

    }

    // A typical method declaration with multiple arguments looks like this:
    public Record getRecord01(int fileNumber, int recNumber) {
        return null;
    }

    // Method arguments are essentially the same as local variables. In the preceding example, the variables fileNumber and
    // 'recNumber' will both follow all the rulesapplied to local variables. This means they can also have the
    // modifier final :
    public Record getRecord02(int fileNumber, final int recNumber) {
        return null;
    }

    // ====================================================================================================
    // Abstract Methods
    //
    // An abstract method is a method that’s been declared (as abstract ) but not implemented.
    // In other words, the method contains no functional code.
    // Only for abstract class!!
    //
    // Any class that extends an abstract class must implement all abstract methods of the superclass, unless
    // the subclass is also abstract . The rule is this:
    // The first concrete subclass of an abstract class must implement all abstract methods of the superclass.
    //
    // A method can never, ever, ever be marked as both abstract and final , or both abstract and private .
    //
    // Finally, you need to know that—for top-level classes—the abstract modifier can never be combined with the
    // static modifier.
    abstract class LegalClass {

        abstract void doIt();
    }

    // ====================================================================================================
    // Synchronized Methods
    // The synchronized keyword indicates that a method can be accessed by only one thread at a time.
    // we’re concerned with is knowing that the synchronized modifier can be applied only to methods—not variables, not classes, just methods
    //
    public synchronized Record retrieveUserInfo() {
        return null;
    }

    //
    // =====================================================================================================
    //
    // Methods with Variable Argument Lists (var-args)
    // Java allows you to create methods that can take a variable number of arguments.
    //
    // As a bit of background, we’d like to clarify how we’re going to use the terms “argument” and “parameter” throughout this book.
    // * arguments : The things you specify between the parentheses when you’re invoking a method:
    public synchronized Record retrieveUserInfo(int id) {
        return null;
    }

    //
    // *parameters : The things in the method’s signature that indicate what the method must receive when it’s invoked:
    // doStuff("a", 2); // invoking doStuff, so "a" & 2 arguments
    //
    // Let’s review the declaration rules for var-args:
    //
    // Var-arg type: When you declare a var-arg parameter, you must specify the type of the argument(s) this parameter of your
    // method can receive. (This can be a primitive type or an object type.)
    //
    // Basic syntax: To declare a method using a var-arg parameter, you follow the type with an ellipsis (...), a space
    // (preferred but optional), and then the name of the array that will hold the parameters received.
    //
    // Other parameters: It’s legal to have other parameters in a method that uses a var-arg.
    //
    // Var-arg limits: The var-arg must be the last parameter in the method’s signature, and you can have only one var-arg in a method.
    //
    // Let’s look at some legal and illegal var-arg declarations:
    //
    // Legal:
    void doStuff(int... x) {
    } // expects from 0 to many ints as parameters

    void doStuff2(char c, int... x) {
    } // expects first a char, then 0 to many ints.

    void doStuff3(Record... records) {
    } // 0 to many Record objects (space before the argument is legal)
      //
      // Illegal:
      // void doStuff4(int x...) {} // bad syntax
      //
      // void doStuff5(int... x, char... y){} // to many var-args
      //
      // void doStuff6(String... s, byte b) {} // var-arg must be last
      // =====================================================================================================
      //
      // Constructor Declarations
      //
      // In Java, objects are constructed. Every time you make a new object, at least one constructor is invoked.
      // Every class has a constructor, although if you don’t create one explicitly, the compiler will build one for you.
      // Constructors can’t be marked static (they are, after all, associated with object instantiation), and they can’t
      // be marked final or abstract (because they can’t be overridden).

    class Foo {

        // this Foo's constructor
        protected Foo() {
        }

        private Foo(byte b) {
        }

        Foo(int x, int... y) {
        }

        // Illegal constructors
        // this a badly named, but legal, method
        protected void Foo() {
        }
        // Foo2() {} // not a mehod or a constructor
        // Foo(short s); // looks like an abstract method
        // static Foo(float f){} // can't be static
        // final Foo(){} // can't be final
        // abstract Foo(){} // can't be abstract
    }

    // =======================================================================================================
    //
    // Variable Declarations
    //
    // There are two types of variables in Java:
    //
    // * Primitives: A primitive can be one of eight types: char, boolean, byte, short, int, long, double, or float.
    // Once a primitive has been declared, its primitive type can never change, although in most cases its value can change.
    //
    // * Reference variables: A reference variable is used to refer to (or access) an object. A reference variable is declared
    // to be of a specific type, and that type can never be changed. A reference variable can be used to refer to any object of
    // the declared type or of a subtype of the declared type (a compatible type).
    // We’ll talk a lot more about using a reference variable to refer to a subtype
    //
    // Declaring Primitives and Primitive Ranges
    // Primitive variables can be declared as class variables (statics), instance variables, method parameters, or local variables.
    public static void test03() {
        byte b;
        boolean myBooleanPrimitive;
        int x, y, z; // declare three int primitives
    }

    //
    // All six number types in Java are made up of a certain number of 8-bit bytes and are signed, meaning they can be negative or
    // positive. The leftmost bit (the most significant digit) is used to represent the sign, where a 1 means negative and 0 means
    // positive
    //
    // Declaring Reference Variables
    // Reference variables can be declared as static variables, instance variables, method parameters, or local variables.
    // You can declare one or more reference variables, of the same type, in a single line:
    Object o;

    String s1, s2, s3;

    //
    // Instance Variables
    // Instance variables are defined inside the class, but outside of any method, and are initialized only when the class is instantiated.
    class Employee {

        // deine fields (instance variables) for employee instances
        private String name;

        private String title;

        private String manager;
        // other code goes here including access methods for private fields
    }

    //
    // Local (Automatic/Stack/Method) Variables
    // A local variable is a variable declared within a method. That means the variable is not just initialized within 
    // the method, but also declared within the method.
    // Just as the local variable starts its life inside the method, it’s also destroyed when the method has
    // completed. Local variables are always on the stack, not the heap like instance variable.
    // Remember, before a local variable can be used, it must be initialized with a value.
    //
    // It is possible to declare a local variable with the same name as an instance variable.
    // It’s known as shadowing, as the following code demonstrates:
    //
    class TestServer {
        int count = 9; // Declare an instance variable named count

        public void logIn() {
            int count = 10; // Declare a local variable named count too
            System.out.println("Local variable count is " + count);

            System.out.println("Local variable count is " + this.count); // instance variable
        }

        public void count() {
            System.out.println("Instance variable count is " + count); // instance variable
        }

        public void doSomething(int i) {
            // count = i; won't compile! Can't access count outside method logIn()
        }
    }
}
