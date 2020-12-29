package br.fernando.ch01_declarations_accessControl_enums.part03_Declare_Class_Members;

// Array Declarations
//
// An array itself will always be an object on the heap, even if the array is
// declared to hold primitive elements.
public class Test03 {

    // ========================================================================================================
    // Declaring an Array of Primitives:

    int[] key01; // Square brackets before name (recommended)

    int key02[]; // Square brackets after name (legal but less readable)

    // Declarring an Array of Object References:

    Thread[] threads;

    // We can also declare multidimensional arrays, which are, in fact, arrays of arrays. This can be done in the following manner:

    String[][][] occupantName;
    String[] managerName[];

    // ========================================================================================================
    // Final Variables
    //
    // For primitives, this means that once the variable is assigned a value, the value can’t be altered
    //
    // A reference variable marked final can never be reassigned to refer to a different object. The data within the 
    // object can be modified, but the reference variable cannot be changed.
    //
    // Effect of final on variables, methods, and classes: You can't override anything.
    //
    // ========================================================================================================
    // Transient Variables
    //
    // If you mark an instance variable as transient, you’re telling the JVM to skip (ignore) this variable when 
    // you attempt to serialize the object containing it.
    //
    // ========================================================================================================
    // Static Variables and Methods
    //
    // The static modifier is used to create variables and methods that will exist independently of any instances created 
    // for the class. All static members exist before you ever make a new instance of a class, and there will be only one 
    // copy of a static member regardless of the number of instances of that class.
    //
    // Things you can mark as static: 
    // * Methods
    // * Variables
    // * A class nested within another class, but not within a method
    // * Initialization blocks
    //
    // Things you can’t mark as static:
    // * Constructors (makes no sense; a constructor is used only to create instances)
    // * Classes (unless they are nested)
    // * Interfaces (unless they are nested)
    // * Method local inner classes (not on the OCA 8 exam)
    // * Inner class methods and instance variables (not on the OCA 8 exam)
    // * Local variables

    // 2.4 107

}
