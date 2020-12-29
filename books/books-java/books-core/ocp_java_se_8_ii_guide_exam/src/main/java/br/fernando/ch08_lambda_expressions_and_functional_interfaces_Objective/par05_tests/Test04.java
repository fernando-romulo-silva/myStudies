package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par05_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test04 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following interface definitions can use Lambda expressions?

        // AA
        // C
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
    //
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
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Identify the correct statements about the following code:
        class Account {

            private String id;

            public Account(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }
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

        myAccts.computeIfPresent("222", bif);// 2
        BankAccount ba = (BankAccount) myAccts.get("222");
        System.out.println(ba.getBalance());

        // It will print 300.0

        // Since myAccts map does contain a key "222", computeIfPresent method will execute the function and replace the existing value
        // associated with the given key in the map with the new value returned by the function.
        //
        // The given function returns a new BankAccount object with a balance of 300.0.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
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
    static void test01_05() throws Exception {
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
        // Identify the correct statements about the following code:
        List<Integer> values = Arrays.asList(2, 4, 6, 9); // 1

        Predicate<Integer> check = (Integer i) -> {
            System.out.println("Checking");
            return i == 4; // 2
        };

        // Predicate even = (Integer i) -> i % 2 == 0; // 3
        //
        // It will not compile because of code at //3.
        //
        // Observe the lambda expression used to instantiate the Predicate is using Integer as the type of the variable.
        // To make this work, the declaration part must be typed to Integer.

        Predicate<Integer> even02 = (Integer i) -> i % 2 == 0;
        // or
        Predicate even03 = i -> ((Integer) i) % 2 == 0;

        values.stream().filter(check).filter(even02).count(); // 4

        // After you fix the line at //3, it will print:

        // Checking
        // Checking
        // Checking
        // Checking
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        class Book {

            private int id;

            private String title;

            private double price;

            public Book(String title, double price) {
                super();
                this.title = title;
                this.price = price;
            }

            public double getPrice() {
                return price;
            }

            public String getTitle() {
                return title;
            }

            public int getId() {
                return id;
            }
        }

        // Given that Book is a valid class with appropriate constructor and getPrice and getTitle methods that returns a double and a String
        // respectively, consider the following code:
        List<List<Book>> books = Arrays.asList( //
                Arrays.asList( //
                        new Book("Windmills of the Gods", 7.0), //
                        new Book("Tell me your dreams", 9.0)), //
                Arrays.asList( //
                        new Book("There is a hippy on the highway", 5.0), //
                        new Book("Easy come easy go", 5.0))); //

        // What can be inserted in the above code so that it will print 26.0?
        double d = books.stream() //
                .flatMap(bs -> bs.stream()) // 1
                .mapToDouble(book -> book.getPrice())// 2
                .sum();

        System.out.println(d);

        // .flatMap(bs->bs.stream())
        // and
        // .mapToDouble(book->book.getPrice())
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Given :
        ArrayList<Data> al = new ArrayList<Data>();
        al.add(new Data(1));
        al.add(new Data(2));
        al.add(new Data(3));

        // Which of the following options can be inserted above so that it will print 1 4 9?
        //

        processList(al, (int a, int b) -> System.out.println(a * b));

        processList(al, (a, b) -> System.out.println(a * b));
        // It is ok to omit the parameter types in case of a functional interface because the compiler can determine the type of the parameters
        // by looking at the interface method.

        processList(al, (a, b) -> {
            System.out.println(a * b);
        }); //
        // If you enclose your method body within curly braces, you must write complete lines of code including the semi-colon.
    }

    public static void processList(ArrayList<Data> dataList, Process p) {
        for (Data d : dataList) {
            p.process(d.value, d.value);
        }
    }

    static interface Process {

        public void process(int a, int b);
    }

    static public class Data {

        int value;

        Data(int value) {
            this.value = value;
        }
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Which of the following is/are valid functional interfaces?

        // The use of abstract keyword is redundant here, but it is legal.
    }

    interface F {

        default void m() {
        }

        abstract void n();
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // What will the following code print when compiled and run?

        class Book {

            private int id;

            private String title;

            private Double price;

            public Book(String title, Double price) {
                super();
                this.title = title;
                this.price = price;
            }

            public Double getPrice() {
                return price;
            }

            public String getTitle() {
                return title;
            }

            public int getId() {
                return id;
            }
        }

        Book b1 = new Book("Java in 24 hrs", null);
        DoubleSupplier ds1 = b1::getPrice;
        System.out.println(b1.getTitle() + " " + ds1.getAsDouble());

        //
        // It will throw a NullPointerException.
        //
        //
        // Explanation
        //
        // java.util.function.DoubleSupplier (and other similar Suppliers such as IntSupplier and LongSupplier)
        // is a functional interface with the functional method named getAsDouble.
        //
        // The return type of this method is a primitive double (not Double). Therefore, if your lambda expression for this function returns 
        // a Double, it will automatically be converted into a double because of auto-unboxing.
        //
        // However, if your expression returns a null, a NullPointerException will be thrown.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
