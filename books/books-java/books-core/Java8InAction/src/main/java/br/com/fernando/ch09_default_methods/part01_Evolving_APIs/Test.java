package br.com.fernando.ch09_default_methods.part01_Evolving_APIs;

import java.util.Arrays;
import java.util.List;

// Default methods
public class Test {

    // Java 8 introduces a new mechanism to tackle this problem. It might sound
    // surprising, but interfaces in Java 8 can now declare methods with implementation code; this
    // can happen in two ways. First, Java 8 allows static methods inside interfaces. Second, Java 8
    // introduces a new feature called default methods that allows you to provide a default
    // implementation for methods in an interface. In other words, interfaces can provide concrete
    // implementation for methods. As a result, existing classes implementing an interface will
    // automatically inherit the default implementations if they don’t provide one explicitly. This
    // allows you to evolve interfaces nonintrusively

    public static void test01() {

        // Game Main
        // A list of shapes that are resizable
        final List<Resizable> resizableShapes = Arrays.asList(new Square(), new Triangle(), new Ellipse());
        // Calling the setAbsoluteSize method on each shape
        Utils.paint(resizableShapes);
        //
        // 
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

        // Adding a new mehtod for API version 2
        // void setRelativeSize(int wFactor, int hFactor);
        //
        // Problems for your users
        //
        // But the Ellipse implementation your user created doesn’t implement the method setRelativeSize. 
        // Adding a new method to an interface is binary compatible; this means existing class file implementations 
        // will still run without the implementation of the new method, if there’s no attempt to recompile them.
    }
}
