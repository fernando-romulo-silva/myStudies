package br.fernando.ch02_object_orientation.par10_inicialization_blocks;

class Test01 {

    // =========================================================================================================================================
    // Inicialization blocks
    static void test01() {
        // Static initialization blocks run when the class is first loaded, and instance initialization blocks run whenever an instance
        // is created (a bit similar to a constructor), but after the constructors runs.
        //
        // A static initialization block runs once when the class is first loaded. An instance initialization block runs once every
        // time a new instance is created.
        // The order in which initialization blocks appear in a class matters.

        // Based on the rules we just discussed, can you determine the output of the following program?

        new Init();
        new Init(7);

        // To figure this out, remember these rules:
        // * init blocks execute in the order in which they appear.
        // * Static init blocks run once, when the class is first loaded.
        // * Instance init blocks run every time a class instance is created.
        // * Instance init blocks run after the constructor’s call to super().
        //
        // AS you can see, the instance ini blocks each ran twice. Inistance init blocks are 
        // often used as a place to put code that all the constructors in a class should share:

        // 1st static init
        // 2nd static init
        // 1st instance init
        // 2nd instance init
        // no-arg const
        // 1st instance init
        // 2nd instance init
        // 1-arg const
    }

    static class SmallInt {

        static int x;

        int y;

        static { // static init block
            x = 7;
        }

        { // instance init block
            y = 8;
        }
    }

    static class Init {

        Init(int x) {
            System.out.println("1-arg const");
        }

        Init() {
            System.out.println("no-arg const");
        }

        static {
            System.out.println("1st static init");
        }

        {
            System.out.println("1st instance init");
        }
        {
            System.out.println("2nd instance init");
        }

        static {
            System.out.println("2nd static init");
        }

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }

}
