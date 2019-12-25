package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par05_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test03 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // What will the following code print?
        List<Integer> names = Arrays.asList(1, 2, 3); // 1
        names.forEach(x -> x = x + 1); // 2
        names.forEach(System.out::println); // 3

        // 1
        // 2
        // 3

        // It's valid you do names.foreach(Consumer) or names.stream().foreach(Consumer)
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
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
    static void test01_03() throws Exception {
        // Give

        class Book {

            private final int id;

            private final String title;

            public Book(int id, String title) {
                super();
                this.id = id;
                this.title = title;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }
        }

        // Assuming that book is a reference to a valid Book object, which of the following code fragments correctly prints the details of the Book?

        Book book = new Book(1, "Java head first!");

        Consumer<Book> c = b -> System.out.println(b.getId() + ":" + b.getTitle());
        c.accept(book);
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Given that java.lang.String has two overloaded toUpperCase methods - toUpperCase() and toUpperCase(Locale ), consider the following code:

        String name = "bob";
        String val = null;
        // Insert code here
        System.out.print(val);

        // Which of the following code fragments can be inserted in the above code so that it will print BOB?

        Supplier<String> s = name::toUpperCase;
        val = s.get();
        // The functional method of the interface Supplier<T> is T get(). name::toUpperCase correctly implements this interface.
        // It returns the value returned by calling name.toUpperCase.

        // and

        // Only one of the above is correct.
        //
        // If you want to make use of toUpperCase(Locale ) method, you should do:
        Function<Locale, String> f1 = name::toUpperCase;
        // You can then use it like this:
        val = f1.apply(Locale.UK);
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Which of the following interface definitions can use Lambda expressions?

        // AA
        // without parameters
        AA a1 = () -> System.out.println("");
        //
        // and
        //
        // C
        C c1 = () -> System.out.println("");

        // Explanation
        // To take advantage of lambda expressions, an interface must be a "functional" interface, which basically means that the interface must
        // have exactly one abstract method.
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
    static void test01_07() throws Exception {
        // Which of the following statements are correct regarding a functional interface?

        // It must have exactly one abstract method and may have other default or static methods.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
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

    static boolean checkList(List list, Predicate<List> p) {
        return p.test(list);
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
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
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
