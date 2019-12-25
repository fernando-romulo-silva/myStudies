package br.fernando.ch01_declarations_accessControl_enums.part04_Declare_Enums;

import static br.fernando.ch01_declarations_accessControl_enums.part04_Declare_Enums.Test01.CoffeeSize01.BIG;

// Declaring enums
public class Test01 {

    // =========================================================================================================================================
    //
    // Java lets you restrict a variable to having one of only a few predefined values—in other words,
    // one value from an enumerated list. (The items in the enumerated list are called, surprisingly, enums.)

    // Each of the enumerated CoffeeSize values is actually an instance of CoffeeSize. In other words,
    // BIG is of type CoffeeSize.
    enum CoffeeSize01 {
        // this cannot be private or protected
        BIG, //

        HUGE, //

        OVERWHELMING
    }; // <- semicolon is optional here

    // From then on, the only way to get a CoffeeSize will be with a statement something like this:
    CoffeeSize01 cs01 = CoffeeSize01.BIG; // enum outise class, enclosing class name required

    public static void test01() {
        CoffeeSize01 cs02 = BIG; // import static
    }

    // =========================================================================================================================================
    //
    // Declaring Constructors, Methods, and Variables in an enum
    //
    // You can add constructors, instance variables, methods, and something really strange known as a constant specific class body
    enum CoffeeSize02 {

        BIG(8),

        HUGE(10),

        OVERWHELMING(16);

        private int ounces;

        CoffeeSize02(int ounces) { // constructor
            this.ounces = ounces;
        }

        public final int getOunces() {
            return ounces;
        }

    }

    // Note: Every enum has a static method, values(), that returns an array of the enum’s values in the order they’re declared.
    public static void test02() {
        final CoffeeSize02 drinkSize = CoffeeSize02.BIG;
        drinkSize.getOunces();

        for (CoffeeSize02 cs : CoffeeSize02.values()) {
            System.out.println(cs + " " + cs.getOunces());
        }

        // The key points to remember about enum constructors are :
        //
        // * You can NEVER invoke an enum constructor directly. The enum constructor is
        // invoked automatically with the arguments you define after the constant value.
        //
        // * You can define more than one argument to the constructor, and you can and you can
        // overload the enum constructors, just as you can overload a normal class constructor.
        //
        // * Yu can define something really strange in an enum that looks like an anonymous inner class.
        // It’s known as a constant specific class body, and you use it when you need a particular constant
        // to override a method defined in the enum.
    }

    enum CoffeeSize03 {

        BIG(8), //
        HUGE(10), //
        OVERWHELMING(16) { // start a code block that defines

            // override the method defined in CoffeeSize03
            @Override
            public String getLidCode() {
                return "A";
            }

        };

        private int ounces;

        CoffeeSize03(int ounces) { // constructor
            this.ounces = ounces;
        }

        public final int getOunces() {
            return ounces;
        }

        public String getLidCode() { // this method is overridden by the OVERWHELMING constante
            return "B"; // the default value we want to return for CoffeeSize
        }
    }

    // TWO-MINUTE DRILL -> 114
}
