package br.com.fernando.ch09_default_methods.part02_Default_methods_in_a_nutshell;

// Default methods in a nutshell
public class Test {

    // So how do you recognize a default method? It’s very simple. It starts with a default modifier and
    // contains a body just like a method declared in a class. For example, in the context of a collection
    // library, you could define an interface Sized with one abstract method size and a default method
    // isEmpty as follows:

    public static interface Sized {

	int size();

	// A default method
	default boolean isEmpty() {
	    return size() == 0;
	}

	// Now any class that implements the Sized interface will automatically inherit the implementation
	// of isEmpty. Consequently, adding a method to an interface with a default implementation isn’t a
	// source incompatibility.
    }

    // ===========================================================================================

    public static void test01() {
	// So what’s the difference between an abstract class and an interface? They both can contain
	// abstract methods and methods with a body.
    }

    static interface Drawable {

	public void draw();

    }

    static interface Resizable extends Drawable {

	int getWidth();

	int getHeight();

	void setWidth(int width);

	void setHeight(int height);

	void setAbsoluteSize(int width, int height);

	// Now you can add a setRelativeSize method without break the code.
	default void setRelativeSize(int wFactor, int hFactor) {
	    setAbsoluteSize(getWidth() / wFactor, getHeight() / hFactor);
	}
    }

}
