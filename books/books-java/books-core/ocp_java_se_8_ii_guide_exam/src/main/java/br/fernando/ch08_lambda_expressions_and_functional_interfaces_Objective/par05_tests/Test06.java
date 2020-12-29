package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par05_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test06 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {

        // What will the following code print when run?

        Employee e = new Employee(); // 2
        // System.out.println(validateEmployee(e, e -> e.age < 10000)); // 3

        // It will fail to compile at line marked //3

        // Remember that the parameter list part of a lambda expression declares new variables that are used in the body part of that
        // lambda expression. However, a lambda expression does not create a new scope for variables. Therefore, you cannot reuse the
        // local variable names that have already been used in the enclosing method to declare the variables in you lambda expression.
        // It would be like declaring the same variable twice.

        // Here, the main method has already declared a variable named e. Therefore, the parameter list part of the lambda expression
        // must not declare another variable with the same name. You need to use another name.
        // For example, if you change //3 to the following, it will work.

        System.out.println(validateEmployee(e, x -> x.age < 10000)); // it would print true
    }

    private static class Employee {
        int age; // 1
    }

    private static boolean validateEmployee(Employee e, Predicate<Employee> p) {
        return p.test(e);
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Which of the following is/are valid functional interfaces?
        //
        // F
        //
        // A functional interface is an interface that contains exactly one abstract method. It may contain zero or more default methods and/or static methods in
        // addition to the abstract method. Because a functional interface contains exactly one abstract method, you can omit the name of that method when you
        // implement it using a lambda expression.
    }

    interface F {

        default void m() {
        }

        // The use of abstract keyword is redundant here, but it is legal.
        abstract void n();
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
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
    static void test01_04() throws Exception {
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

    // In Data.java
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
        // Given:

        List<String> fnames = Arrays.asList("a", "b", "c");
        Tiger t = new Tiger();

        // Which of the following options can be inserted independent of each other in the code above without any compilation error?

        process(fnames, t);

        process(fnames, t::eat);

        process(fnames, t::calories);

        process(fnames, Test03::size);

        // Explanation
        // Don't be confused by twisted code. The method process(List<String> names, Carnivore c) expects a List<String> and a Carnivore instance as arguments.
        //
        // Carnivore has exactly one abstract method and therefore it is a functional interface. You can either pass a Carnivore instance explicitly or pass a
        // reference to a method that matches the parameter list of Carnivore's abstract method eat(List<String> foods);.
        //
        // t::eat, t::calories, and TestClass::size are all valid method references with the exact same parameter requirements and are therefore valid.
        //
        // Carnivore::calories is invalid because Carnivore is an interface. It does not refer to any object upon which calories method can be invoked.
        //
        // t::calories, on the other hand is valid because t does refer to an object upon which calories method can be invoked. t::eat is valid for the same reason.
        //
        // Tiger::eat is a valid method reference that can mean to refer either to a static method eat of Tiger class or to an instance method of any arbitraty
        // instance of Tiger class. Which meaning is implied depends on the context in which it is used.
        //
        // Here, the context does not supply any instance of Tiger class. Therefore, Tiger::eat will refer to a static method eat. But there is no such
        // static method in Tiger class. Therefore, it is invalid in this context.
        //
        // To use Tiger::eat, you need a reference to a Tiger instance, which is not available here. TestClass::size, on the other hand, is a static
        // method of TestClass, which means you don't need an object of TestClass to invoke this method and that is why it is valid.
    }

    public static int size(List<String> names) {
        return names.size() * 2;
    }

    public static void process(List<String> names, Carnivore c) {
        c.eat(names);
    }

    interface Carnivore {

        default int calories(List<String> food) {
            return food.size() * 100;
        }

        int eat(List<String> foods);
    }

    static class Tiger implements Carnivore {

        public int eat(List<String> foods) {
            System.out.println("Eating " + foods);
            return foods.size() * 200;
        }
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Given:
        List<Integer> ls = Arrays.asList(10, 47, 33, 23);

        int max = 0; // INSERT code HERE

        System.out.println(max); // 1

        // Which of the following options can be inserted above so that it will print the largest number in the input stream?

        max = ls.stream().max(Comparator.comparing(a -> a)).get();

        // Comparator.comparing method requires a Function that takes an input and returns a Comparable. This Comparable, in turn, is used by
        // the comparing method to create a Comparator. The max method uses the Comparator to compare the elements int he stream.
        //
        // The lambda expression a -> a creates a Function that takes an Integer and returns an Integer (which is a Comparable).
        // Here, the lambda expression does not do much but in situations where you have a class that doesn't implement Comparable and you
        // want to compare objects of that class using a property of that class that is Comparable, this is very useful.
        //
        // The call to get() is required because max(Comparator ) return an Optional object.
        //
        // Explanation
        // The Stream.reduce method needs a BinaryOperator. This interface is meant to consume two arguments and produce one output.
        // It is applied repeatedly on the elements in the stream until only one element is left.
        // The first argument is used to provide an initial value to start the process. (If you don't pass this argument, a different reduce
        // method will be invoked and that returns an Optional object. )

        // Errors
        //
        Optional<Integer> maxOptional = ls.stream().reduce((a, b) -> a > b ? a : b);
        //
        // This is actually a valid lambda expression that implements BinaryOperator but the return type of the reduce method used here is Optional.
        // Therefore, it will return Optional object containing 47 instead of just Integer 47 and you cannot assign an object of class Optional to
        // a variable of type int. You need to use the reduce method that takes identity value as the first element:

        max = ls.stream().reduce(Integer.MIN_VALUE, (a, b) -> a > b ? a : b);

        // This will return an Integer object, which can be assigned to max.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Which of the following standard functional interfaces is most suitable to process a large collection of int primitives and
        // return processed data for each of them?
        //
        // IntFunction
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
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
    static void test01_10() throws Exception {
        // What will the following code print when compiled and run?

        BiPredicate<String, Integer> bip = (s, i) -> s.length() > i; // 1

        BiFunction<String, Integer, String> bif = (s, i) -> { // 2
            if (bip.test(s, i)) { // 3
                return s.substring(0, i);
            } else return s;
        };

        String str = bif.apply("hello world", 5); // 4

        System.out.println(str);

        // hello
        // There is no problem with the code.
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // Identify correct statements about the following code:

        List<String> vals = Arrays.asList("a", "b");
        String join = vals.parallelStream().reduce("_", (a, b) -> a.concat(b));
        System.out.println(join);

        // It will print either _ab or _a_b
        //
        // Since we are creating a parallel stream, it is possible for both the elements of the stream to be processed by two different threads. 
        // In this case, the identity argument will be used to reduce both the elements.
        // Thus, it will print _a_b.
        //
        // It is also possible that the result of the first reduction ( _a ) is reduced further using the second element (b). 
        // In this case, it will print _ab.
        //
        // Even though the elements may be processed out of order individualy in different threads, the final output will be produced by 
        // joining the individual reduction results in the same order. Thus, the output can never have b before a.

    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
