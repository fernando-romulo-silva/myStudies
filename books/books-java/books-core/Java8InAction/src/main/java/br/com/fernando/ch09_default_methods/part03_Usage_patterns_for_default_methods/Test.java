package br.com.fernando.ch09_default_methods.part03_Usage_patterns_for_default_methods;

// Usage patterns for default methods
public class Test {

    // Optional methods
    public static void test01() {

    }

    // With default methods, you can provide a default implementation for such methods, so concrete
    // classes don’t need to explicitly provide an empty implementation. For example, the Iterator
    // interface in Java 8 provides a default implementation for remove as follows:
    interface Iterator<T> {

	boolean hasNext();

	T next();

	// Consequently, you can reduce boilerplate code. Any class implementing the Iterator interface
	// doesn’t need to declare an empty remove method anymore to ignore it, because it now has a
	// default implementation.
	default void remove() {
	    throw new UnsupportedOperationException();
	}
    }

    // ==================================================================================

    // Multiple inheritance of behavior
    public static void test02() {
	// Remember that classes in Java can inherit from only one other class, but classes have always
	// been allowed to implement multiple interfaces.
	//
	// Composing interfaces
	//
	// You can now create different concrete classes for your game by composing these interfaces.
	//
	// The Monster class will automatically inherit the default methods from the Rotatable, Moveable, and Resizable interfaces.
	// In this case, Monster inherits the implementations of rotateBy, moveHorizontally, moveVertically, and setRelativeSize.
	// You can now call the different methods directly:

	final Monster m1 = new Monster(); // Constructor internally sets the coordnates, height, width, and default angle.
	m1.rotateBy(180); // Calling rotateBy from Rotatable
	m1.moveVertically(10); // Calling moveVertically from Moveable
    }

    // Rotatable interface with two abstract methods,
    // setRotationAngle and getRotationAngle. The interface also declares a default rotateBy method
    // that can be implemented using the setRotationAngle and get-RotationAngle methods as follows:
    static interface Rotatable {

	void setRotationAngle(int angleInDegrees);

	int getRotationAngle();

	// A default implementation for the method
	default void rotateBy(int angleInDegrees) {
	    setRotationAngle((getRotationAngle() + angleInDegrees) % 360);
	}
    }

    // Similarly, you can define two interfaces, Moveable and Resizable, that you saw earlier. They
    // both contain default implementations. Here’s the code for Moveable:
    static interface Moveable {

	int getX();

	int getY();

	void setX(int x);

	void setY(int y);

	default void moveHorizontally(int distance) {
	    setX(getX() + distance);
	}

	default void moveVertically(int distance) {
	    setY(getY() + distance);
	}
    }

    // And here’s the code for Resizable:
    static interface Resizable {

	int getWidth();

	int getHeight();

	void setWidth(int width);

	void setHeight(int height);

	void setAbsoluteSize(int width, int height);

	default void setRelativeSize(int wFactor, int hFactor) {
	    setAbsoluteSize(getWidth() / wFactor, getHeight() / hFactor);
	}
    }

    // The class Monster needs to provide implementations for all abstract methods but not the default methods
    static class Monster implements Rotatable, Moveable, Resizable {

	@Override
	public int getWidth() {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public int getHeight() {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public void setWidth(int width) {
	    // TODO Auto-generated method stub
	}

	@Override
	public void setHeight(int height) {
	    // TODO Auto-generated method stub
	}

	@Override
	public void setAbsoluteSize(int width, int height) {
	    // TODO Auto-generated method stub
	}

	@Override
	public int getX() {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public int getY() {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public void setX(int x) {
	    // TODO Auto-generated method stub
	}

	@Override
	public void setY(int y) {
	    // TODO Auto-generated method stub
	}

	@Override
	public void setRotationAngle(int angleInDegrees) {
	    // TODO Auto-generated method stub
	}

	@Override
	public int getRotationAngle() {
	    // TODO Auto-generated method stub
	    return 0;
	}
    }

    //
    static class Sun implements Moveable, Rotatable {

	@Override
	public void setRotationAngle(int angleInDegrees) {
	    // TODO Auto-generated method stub
	}

	@Override
	public int getRotationAngle() {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public int getX() {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public int getY() {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public void setX(int x) {
	    // TODO Auto-generated method stub
	}

	@Override
	public void setY(int y) {
	    // TODO Auto-generated method stub
	}
    }

}
