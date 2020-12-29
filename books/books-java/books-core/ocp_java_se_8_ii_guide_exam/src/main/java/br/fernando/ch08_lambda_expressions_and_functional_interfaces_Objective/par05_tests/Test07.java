package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par05_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test07 {

    static class Data {

        int value;

        Data(int value) {
            this.value = value;
        }

        public String toString() {
            return "" + value;
        }
    }

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following are correct about java.util.function.Predicate?
        //
        // It is an interface that has only one abstract method with the signature - public boolean test(T t);
        //
        // Explanation
        // java.util.function.Predicate is one of the several functional interfaces that have been added to Java 8.
        // This interface has exactly one abstract method named test, which takes any object as input and returns a boolean.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // What will the following code print?
        List<Integer> names = Arrays.asList(1, 2, 3); // 1
        names.forEach(x -> x = x + 1); // 2
        names.forEach(System.out::println); // 3

        // 1
        // 2
        // 3

        // There is no problem with either of the lines //2 and //3. List's forEach method takes java.util.function.Consumer instance and both
        // of them have valid lambda expressions that capture this interface. The expression x->x=x+1; doesn't actually change the elements in the list.
        // You can think of x as a temporary local variable and changing the value of this x does not affect the actual element in the list.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Given:

        ArrayList<Data> al = new ArrayList<Data>();

        Data d = new Data(1);
        al.add(d);
        d = new Data(2);
        al.add(d);
        d = new Data(0);
        al.add(d);

        filterData01(al, new MyFilter()); // 1

        // How can you use a lambda expression to achieve the same result?

        System.out.println(al);

        // Remove MyFilter class altogether.
        // Change type of f from MyFilter to java.util.function.Predicate<Data> in filterData method and replace the line at 1 with: 
        filterData02(al, x -> x.value == 0);
    }

    static class MyFilter {

        public boolean test(Data d) {
            return d.value == 0;
        }
    }

    public static void filterData01(ArrayList<Data> dataList, MyFilter f) {
        Iterator<Data> i = dataList.iterator();
        while (i.hasNext()) {
            if (f.test(i.next())) {
                i.remove();
            }
        }
    }

    public static void filterData02(ArrayList<Data> dataList, Predicate<Data> f) {
        Iterator<Data> i = dataList.iterator();
        while (i.hasNext()) {
            if (f.test(i.next())) {
                i.remove();
            }
        }
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // What can be inserted into the following code at //1 so that it will print "Oldboy"?

        List<Movie> movies = Arrays.asList( //
                new Movie("On the Waterfront", Movie.Genre.DRAMA), //
                new Movie("Psycho", Movie.Genre.THRILLER), //
                new Movie("Oldboy", Movie.Genre.THRILLER), //
                new Movie("Shining", Movie.Genre.HORROR)); //

        Predicate<Movie> horror = mov -> mov.getGenre() == Movie.Genre.THRILLER;

        Predicate<Movie> name = mov -> mov.getName().startsWith("O");

        // 1 INSERT CODE HERE .forEach(mov->System.out.println(mov.getName()));
        movies.stream() //
                .filter(horror) //
                .filter(name) //
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
            DRAMA,
            THRILLER,
            HORROR,
            ACTION
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
    static void test01_05() throws Exception {
        // Identify the correct statements about the following code:
        class Account {

            private String id;

            public Account(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            };

        }

        class BankAccount extends Account {

            private double balance;

            public BankAccount(String id, double balance) {
                super(id);
                this.balance = balance;
            }

            public double getBalance() {
                return balance;
            }
        }

        Map<String, Account> myAccts = new HashMap<>();
        myAccts.put("111", new Account("111"));
        myAccts.put("222", new BankAccount("111", 200.0));

        BiFunction<String, Account, Account> bif = (a1, a2) -> a2 instanceof BankAccount ? new BankAccount(a1, 300.0) : new Account(a1); // 1
        myAccts.computeIfPresent("222", bif); // 2
        BankAccount ba = (BankAccount) myAccts.get("222");
        System.out.println(ba.getBalance());

        // It will print 300.0
        // Since myAccts map does contain a key "222", computeIfPresent method will execute the function and replace the existing value
        // associated with the given key in the map with the new value returned by the function.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What will the following code print when compiled and run?

        String val1 = "hello";
        StringBuilder val2 = new StringBuilder("world");
        UnaryOperator<String> uo1 = s1 -> s1.concat(val1); // 1
        UnaryOperator<String> uo2 = s -> s.toUpperCase(); // 2

        // System.out.println(uo1.apply(uo2.apply(val2))); // 3

        // Compilation error at //3

        // Remember that StringBuilder and StringBuffer do not extend String. The UnaryOperaty uo2 has been typed to String and therefore,
        // you cannot apply it to a StringBuilder object.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Given:

        class MyProcessor {

            int value;

            public MyProcessor() {
                value = 10;
            }

            public MyProcessor(int value) {
                this.value = value;
            }

            public void process() {
                System.out.println("Processing " + value);
            }
        }

        // Which of the following code snippets will print Processing 10?

        Supplier<MyProcessor> supp = MyProcessor::new;
        MyProcessor mp01 = supp.get();
        mp01.process();

        //
        Function<Integer, MyProcessor> f = MyProcessor::new;
        MyProcessor mp02 = f.apply(10);
        mp02.process();

        // Here, you are using a constructor reference of the constructor that takes an argument. The argument is actually passed during
        // the call to f.apply method, which is also when the constructor is invoked.

        // Explanation
        // An important point to understand with method or constructor references is that you can never pass arguments while referring
        // to a constructor or a method.
        // On the other hand, when you do Function<Integer, MyProcessor> f =  MyProcessor::new; you are telling the compiler to get
        // you the constructor reference of the constructor that takes one Integer argument.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Given:
        List<String> strList = Arrays.asList("a", "aa", "aaa");
        Function<String, Integer> f = x -> x.length();
        Consumer<Integer> c = x -> System.out.print("Len:" + x + " ");
        strList.stream().map(f).forEach(c);

        // What will it print when compiled and run?

        // Len:1 Len:2 Len:3 

        // The function f accepts a String and returns its length. The call to map(f), uses this function to replace each element of
        // the stream with an Integer. The call to forEach(c) uses function c to print each element.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Given:
        List<Integer> ls = Arrays.asList(3, 4, 6, 9, 2, 5, 7);

        System.out.println(ls.stream().reduce(Integer.MIN_VALUE, (a, b) -> a > b ? a : b)); // 1
        System.out.println(ls.stream().max(Integer::max).get()); // 2
        System.out.println(ls.stream().max(Integer::compare).get()); // 3
        System.out.println(ls.stream().max((a, b) -> a > b ? a : b)); // 4

        // Which of the above statements will print 9?

        // 1 and 3

        // The code will print:
        //
        // 9
        // 3
        // 9
        // Optional[3]
        //
        // You need to understand the following points to answer this question:
        //
        // 1. The reduce method needs a BinaryOperator. This interface is meant to consume two arguments and produce one output.
        // It is applied repeatedly on the elements in the stream until only one element is left.
        // The first argument is used to provide an initial value to start the process. (If you don't pass this argument, a different reduce
        // method will be invoked and that returns an Optional object. )
        //
        // 2. The Stream.max method requires a Comparator. All you need to implement this interface using a lambda expression is a reference
        // to any method that takes two arguments and returns an int. The name of the method doesn't matter.
        // That is why it is possible to pass the reference of Integer's max method as an argument to Stream's max method.
        //
        // However, Integer.max works very differently from Integer.compare.
        // The max method returns the maximum of two numbers while the compare method returns a difference between two numbers.
        // Therefore, when you pass Integer::max to Stream's max, you will not get the correct maximum element from the stream.
        // That is why //2 will compile but will not work correctly.
        //
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Given:
        double principle = 100;
        int interestrate = 5;

        compute01(principle, x -> x * interestrate);
        compute02(principle, x -> x * interestrate);

        // Which of the following methods can be inserted in the above code so that it will compile and run without any error/exception?

        // compute01 and compute02

        // Explanation
        // The landba expression x->x*interestrate basically takes an input value, modifies it, and return the new value.
        // It is, therefore, acting as a Function. Therefore, you need a method named compute that takes two arguments - an double value and
        // a Function instance. Option 1 and 4 provide just such a method.

    }

    public static double compute01(double base, Function<Integer, Integer> func) {
        return func.apply((int) base);
    }

    public static double compute02(double base, Function<Double, Double> func) {
        return func.apply(base);
    }

    // ---- Errors
    // Function<Double, Integer> implies that your argument type is Double and return type is Integer. However, the return type of the
    // lambda expression x -> x * interestrate is Double because you are passing base, which is a double, as the argument to this function..
    public static double compute03(double base, Function<Double, Integer> func) {
        return func.apply(base);
    }

    // The method definition is fine. But the usage of this method i.e. compute(principle, x-> x * interestrate); will not compile because
    // the type of the expression that makes up the body of the function i.e. x*interestrate is int, while the expected return type of
    // the Function is Double. int cannot be boxed to a Double. You could do this though:
    // double amount = compute(principle, x -> new Double( x * interestrate));
    public static double compute04(double base, Function<Integer, Double> func) {
        return func.apply((int) base);
    }

    // Function<Integer, Double> implies that your argument type is Integer and return type is Double. However, you are passing base,
    // which is double, to this function. You cannot pass a double where an int or Integer is required without explicit
    // cast due to potential loss of precision.
    /**
     * <pre>
     * 
     * public static double compute05(double base, Function<Integer, Double> func) {
     *     return func.apply(base);
     * }
     * 
     * </pre>
     */

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
