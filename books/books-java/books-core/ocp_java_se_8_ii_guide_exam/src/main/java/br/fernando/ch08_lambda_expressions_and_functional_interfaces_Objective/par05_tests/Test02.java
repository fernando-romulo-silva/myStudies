package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par05_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Predicate;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test02 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following statements are correct regarding a functional interface?

        // It must have exactly one abstract method and may have other default or static methods.
        //
        // Explanation
        // A functional interface is an interface that contains exactly one abstract method. It may contain zero or more default methods and/or
        // static methods. Because a functional interface contains exactly one abstract method, you can omit the name of that method when you
        // implement it using a lambda expression.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // What will the following code print?

        List<Integer> str = Arrays.asList(1, 2, 3, 4);

        str.stream().filter(x -> {
            System.out.print(x + " ");
            return x > 2;
        });

        // It will not print anything.
        //
        // Remember that filter is an intermediate operation. It will not be executed until you invoke a terminal operation such as
        // count or forEach on the stream.
        //
        // Explanation
        // To answer this question, you need to know two things - distinction between "intermediate" and "terminal" operations and
        // which operations of Stream are "intermediate" operations.
        //
        // It is easy to identify which operations are intermediate and which are terminal. All intermediate operations return Stream
        // (that means, they can be chained), while terminal operations don't.
        //
        // filter, peek, and map are intermediate operations.
        //
        // count, forEach, sum, allMatch, noneMatch, anyMatch, findFirst, and findAny are terminal operations.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What can be inserted into the following code at //1 so that it will print "Oldboy"?

        List<Movie> movies = Arrays.asList( //
                new Movie("On the Waterfront", Movie.Genre.DRAMA), //
                new Movie("Psycho", Movie.Genre.THRILLER), //
                new Movie("Oldboy", Movie.Genre.THRILLER), //
                new Movie("Shining", Movie.Genre.HORROR)); //

        Predicate<Movie> horror = mov -> mov.getGenre() == Movie.Genre.THRILLER;

        Predicate<Movie> name = mov -> mov.getName().startsWith("O");

        // 1 INSERT CODE HERE .forEach(mov->System.out.println(mov.getName()));
        movies.stream().filter(horror).filter(name) //
                .forEach(mov -> System.out.println(mov.getName()));

        // Explanation
        // The given code illustrates how you can use functional interfaces, lambda expressions, and streams to filter a List of objects based
        // on multiple criteria. First, you need to convert the List into a Stream.
        //
        // List has stream() method that returns a Stream. Stream support filter(Predicate ) method, which filters the elements in the stream.
        // Only those elements for which Predicate's test() method returns true, are left remaining in the stream.
        //
        // The return type of filter method is Stream, so you can chain multiple filter methods one after another.
    }

    static class Movie {

        static enum Genre {
            DRAMA, //
            THRILLER, //
            HORROR, //
            ACTION //
        };

        private Genre genre;

        private String name;

        Movie(String name, Genre genre) {
            this.name = name;
            this.genre = genre;
        } // accessors not shown

        public String getName() {
            return name;
        }

        public Genre getGenre() {
            return genre;
        }
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // What can be inserted in the code below so that it will print true when run?

        boolean b01 = false;// WRITE CODE HERE

        // 1º
        // The test method of Predicate returns a boolean. So all you need for your  body part in your lambda expression is an expression that
        // returns a boolean. isEmpty() is a valid method of ArrayList, which returns true if there are no elements in the list.
        // Therefore, al.isEmpty() constitutes a valid body for the lambda expression in this case.
        b01 = checkList(new ArrayList(), al -> al.isEmpty());
        //
        //
        // 2º
        // The add method of ArrayList returns a boolean. Further, it returns true if the list is altered because of the call to add.
        // In this case, al.add("hello") indeed alters the list because a new element is added to the list.
        b01 = checkList(new ArrayList(), al -> al.add("hello"));

        System.out.println(b01);
    }

    public static boolean checkList(List list, Predicate<List> p) {
        return p.test(list);
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Which of the following interface definitions can use Lambda expressions?
        // AA
        // It has one abstract method.

        // C
        // It has one abstract method.
        //
        // Explanation
        // To take advantage of lambda expressions, an interface must be a "functional" interface, which basically means that the interface must
        // have exactly one abstract method. A lambda expression essentially provides the implementation for that abstract method.
        // It does not matter whether the abstract method is declared in this interface or a super interface.
        //
    }

    interface A {

        static void m1() {
        };
    }

    interface AA extends A {

        void m2();
    }

    interface AAA extends AA {

        void m3();
    }

    interface B {

        default void m1() {
        }
    }

    interface BB extends B {

        static void m2() {
        };
    }

    interface C extends BB {

        void m3();
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What will the following code print?
        List<Integer> names = Arrays.asList(1, 2, 3); // 1
        names.forEach(x -> x = x + 1); // 2
        names.forEach(System.out::println); // 3

        // 1 2 3
        //
        // Explanation
        // There is no problem with either of the lines //2 and //3.
        // List's forEach method takes java.util.function.Consumer instance and both of them have valid lambda expressions that capture this interface.
        // The expression x-> x = x+1; doesn't actually change the elements in the list.
        // You can think of x as a temporary local variable and changing the value of this x does not affect the actual element in the list.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // What will the following code print when compiled and run?

        List<StringBuilder> messages = Arrays.asList(new StringBuilder(), new StringBuilder());
        messages.stream().forEach(s -> s.append("helloworld"));
        messages.forEach(s -> {
            s.insert(5, ",");
            System.out.println(s);
        });

        // StringBuilder is mutable. It has several overloaded insert methods (one for each data type such as boolean, char, int, and String)
        // that insert the given object into a given position.

        // hello,world
        // hello,world
        //
        // Explanation
        // Java 8 has added a default method default void forEach(Consumer<? super T> action) in java.lang.Iterable interface (which is extended by java.util.List interface).
        //
        // java.util.Stream interface also contains the same void forEach(Consumer<? super T> action) method that applies the given action to each element of the stream.
        // This is a terminal operation
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Which of the following are correct about java.util.function.Predicate?
        //
        // It is an interface that has only one abstract method with the signature - public boolean test(T t);
        //
        // Explanation
        // java.util.function.Predicate is one of the several functional interfaces that have been added to Java 8.
        // This interface has exactly one abstract method named test, which takes any object as input and returns a boolean.

    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {

        // Which of the following statements is/are true about java.util.function.IntFunction?

        // It takes int primitive as an argument. It can be parameterized to return any thing. For example
        IntFunction<String> f = x -> "" + x;

        // It avoids additional cost associated with auto-boxing/unboxing
        // Remember that primitive and object versions of data types (i.e. int and Integer, double and Double, etc.) are not really compatible with each other in java.
        //
        // To eliminate this problem, the function package contains primitive specialized versions of streams as well as functional interfaces.
        // For example, instead of using Stream<Integer>, you should use IntStream. You can now process each element of the stream using IntFunction.
        // This will avoid auto-boxing/unboxing altogether.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Which of the following is/are valid functional interfaces?

        // The use of abstract keyword is redundant here, but it is legal.
        //
        // Explanation
        // A functional interface is an interface that contains exactly one abstract method. It may contain zero or more default methods and/or
        // static methods in addition to the abstract method.
    }

    interface F {

        default void m() {
        }

        abstract void n();
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
