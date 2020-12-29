package br.fernando.ch02_object_orientation.par01_encapsulation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test01 {

    // ===============================================================================================================================================
    // Encapsulation
    // By hiding implementation details, you can rework your method code (perhaps also altering the way variables are used by your class)
    // without forcing a change in the code that calls your changed method.
    // If you want maintainability, flexibility, and extensibility (and, of course, you do), your design must include encapsulation. How do you do that?
    //
    // 1º Keep instance variables hidden (with an access modifier, often private ).
    //
    // 2º Make public accessor methods, and force calling code to use those methods rather than directly accessing the instance variable.
    // These so-called accessor methods allow users of your class to set a variable’s value or get a variable’s value.
    //
    // 3º For these accessor methods, use the most common naming convention of set<SomeProperty> and get<SomeProperty> .
    static void test01() {
        // --------------------------------------
        BadOO badOO = new BadOO();

        badOO.size = -5; // legal but bad!

        System.out.println("======================");
        // --------------------------------------
        Better01OO better01OO = new Better01OO();

        StringBuilder localName1 = better01OO.getName();

        System.out.println("N: " + localName1);

        localName1.setLength(0);

        System.out.println("N: " + localName1);
        System.out.println("Name: " + better01OO.getName());

        System.out.println("======================");
        // --------------------------------------

        Better02OO better02OO = new Better02OO();

        better02OO.setName(new StringBuilder("Foo2"));

        better02OO.addSize(new BigDecimal(1));

        List<BigDecimal> sizeList1 = better02OO.getSizes();

        // java.lang.UnsupportedOperationException
        // sizeList1.remove(0);

        BigDecimal size01Local = sizeList1.get(0);

        size01Local = size01Local.add(new BigDecimal(5));

        System.out.println(size01Local);
        System.out.println(better02OO.getSizes());
    }

    static class BadOO {

        int size;

        int weight;
    }

    static class Better01OO {

        private StringBuilder name = new StringBuilder("Foo");

        public void setName(final StringBuilder name) {

            if (name == null || "".equals(name.toString())) {
                throw new IllegalArgumentException("You can't do it!");
            }

            this.name = name;
        }

        public StringBuilder getName() {
            return name;
        }
    }

    static class Better02OO {

        private StringBuilder name = new StringBuilder("Foo");

        private final List<BigDecimal> sizes = new ArrayList<>();

        public void setName(final StringBuilder name) {

            checkName(name);

            this.name = name;
        }

        public StringBuilder getName() {
            // it's a copy!
            return new StringBuilder(name.toString());
        }

        public List<BigDecimal> getSizes() {
            return Collections.unmodifiableList(sizes);
        }

        public void addSize(final BigDecimal newSize) {

            checkSize(newSize);

            sizes.add(newSize);
        }

        private void checkSize(BigDecimal newSize) {
            // TODO Auto-generated method stub
        }

        private void checkName(final StringBuilder name) {
            if (name == null || "".equals(name.toString())) {
                throw new IllegalArgumentException("You can't do it!");
            }
        }
    }

    static class Better03OO {

        // name guarantee your integrity
        private Name name;

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }
    }

    // value object Name
    static class Name {

        final StringBuilder value;

        public Name(StringBuilder value) {
            super();
            check(value);
            this.value = value;
        }

        private void check(final StringBuilder value) {
            if (value == null || "".equals(value.toString())) {
                throw new IllegalArgumentException("You can't do it!");
            }
        }
    }

    // ===============================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
