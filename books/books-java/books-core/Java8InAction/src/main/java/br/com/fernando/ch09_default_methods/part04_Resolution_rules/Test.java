package br.com.fernando.ch09_default_methods.part04_Resolution_rules;

// Resolution rules
public class Test {

    static interface A {

	public default void hello() {
	    System.out.println("Hello from A");
	}

	public default void goodbye() {
	    System.out.println("Goodbye from A");
	}
    }

    static interface B {

	public default void hello() {
	    System.out.println("Hello from B");
	}
    }

    static interface D extends A {

	@Override
	public default void goodbye() {
	    System.out.println("Goodbye from D");
	}

    }

    // -------- classes -------------

    static class E implements A {

	@Override
	public void goodbye() {
	    System.out.println("Goodbye from A");
	}
    }

    static class F implements D {

	@Override
	public void hello() {
	    System.out.println("Hello from F");
	}
    }

    static class C extends E implements B, A {

	@Override
	public void hello() {
	    A.super.hello(); // Explicitly choosing to call the method from interface 'A'
	}
    }

    // --------------------------------------------------------

    // Three resolution rules to know
    public static void test01() {
	new C().hello(); // What gets printed?

	// 1. Classes always win. A method declaration in the class or a superclass takes priority over any
	// default method declaration.
	//
	// 2. Otherwise, sub-interfaces win: the method with the same signature in the most specific
	// default-providing interface is selected. (If B extends A, B is more specific than A).
	//
	// 3. Finally, if the choice is still ambiguous, the class inheriting from multiple interfaces has to
	// explicitly select which default method implementation to use by overriding it and calling the
	// desired method explicitly.
    }

    // Most specific default-providing interface wins
    public static void test02() {

	// Rule 1 says that a method declaration in the class takes priority.
	// But 'E' doesn’t override 'goodbye'; it implements interface A
	//
	// what gets printed?
	new F().hello(); // 'Hello from F'

	// Rule 2 says that if there are no implementation of method 'goodbye' in the class or superclass,
	// then the method with the most specific default-providing interface is selected.
	// The compiler therefore has the choice between the 'goodbye' method from interface A.
	// Because the interface 'D' is more specific.
	//
	// what gets printed?
	new F().goodbye(); // 'Goodbye from D'

    }

    // Conflicts and explicit disambiguation
    public static void test03() {

	// Rule 3 if you don't explicitly choosing to call, it won't compile
	//
	// what gets printed?
	new C().goodbye(); // 'Goodbye from A'
    }

    // Diamond problem
    public static void test04() {
	// What gets printed?
	new H().hello(); // t “Hello from I”

	// you may be wondering what happens if you add an abstract hello method
	// (one that’s not default) in interface 'K' as follows (still no methods in 'I' and 'J')
	//
	// The new abstract hello method in C takes priority over the default hello method from interface 'I'
	// because 'K' is more specific. Therefore, class 'H' now needs to provide an explicit implementation
	// for hello; otherwise the program won’t compile.
    }

    static interface I {

	default void hello() {
	    System.out.println("Hello from I");
	}
    }

    static interface J extends I {

    }

    static interface K extends I {

	// it cancel default method
	@Override
	void hello();
    }

    static class H implements J, K {

	// You MUST implement the method
	@Override
	public void hello() {
	    //
	}

    }

    // =====================================================================================
    public static void main(String[] args) {
	test02();
    }
}
