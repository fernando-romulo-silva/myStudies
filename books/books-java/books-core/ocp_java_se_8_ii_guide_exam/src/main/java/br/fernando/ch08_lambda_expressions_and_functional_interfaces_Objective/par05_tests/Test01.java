package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par05_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Test01 {

    // =========================================================================================================================================
    static void test01_01() {
        // Identify the correct statements about the following code:
        Map<String, Account> myAccts = new HashMap<>();
        myAccts.put("111", new Account("111"));
        myAccts.put("222", new BankAccount("111", 200.0));

        BiFunction<String, Account, Account> bif = (a1, a2) -> a2 instanceof BankAccount ? new BankAccount(a1, 300.0) : new Account(a1); // 1

        myAccts.computeIfPresent("222", bif);// 2

        BankAccount ba = (BankAccount) myAccts.get("222");

        // Since myAccts map does contain a key "222", computeIfPresent method will execute the function and replace the existing value
        // associated with the given key in the map with the new value returned by the function.
        // The given function returns a new BankAccount object with a balance of 300.0.

        System.out.println(ba.getBalance());

    }

    static class Account {

        private String id;

        public Account(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    static class BankAccount extends Account {

        private double balance;

        public BankAccount(String id, double balance) {
            super(id);
            this.balance = balance;
        }

        public double getBalance() {
            return balance;
        }
    }

    // =========================================================================================================================================
    static void test01_02() {
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
    static void test01_03() {
        // Given that Book is a valid class with appropriate constructor and getPrice and getTitle methods that returns a double and a String respectively,
        // consider the following code:

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

    static private class Book {

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

    // =========================================================================================================================================
    static void test01_04() {

        // What will the following code print?

        List<Integer> str = Arrays.asList(1, 2, 3, 4);

        str.stream().filter(x -> { //
            System.out.print(x + " ");
            return x > 2;
        });

        // nothing
        //
        // Remember that filter is an intermediate operation.
        // It will not be executed until you invoke a terminal operation such as count or forEach on the stream.

    }

    // =========================================================================================================================================
    static void test01_05() {
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
    static void test01_06() {
        // Which of the following statements are correct regarding a functional interface?

        // It must have exactly one abstract method and may have other default or static methods.

    }

    // =========================================================================================================================================
    static void test01_07() {
        // Assuming that book is a reference to a valid Book object, which of the following code fragments correctly prints the details of the Book?

        Book book = new Book("Tell me your dreams", 100.0);

        // Remember that Consumer doesn't return anything. Therefore, the body of the lambda expression used to capture Consumer must be an expression of type void.
        // Here, the type of the expression is String and so it will not compile
        Consumer<Book> c = b -> System.out.println(b.getId() + ":" + b.getTitle());
        c.accept(book);
    }

    // =========================================================================================================================================
    static void test01_08() {
        // Given :
        ArrayList<Data> al = new ArrayList<>();

        Data d = new Data(1);
        al.add(d);
        d = new Data(2);
        al.add(d);
        d = new Data(3);
        al.add(d);

        //  Which of the following options can be inserted above so that it will print [1, 3]?

        filterData(al, (Data x) -> x.value % 2 == 0);
        // When all your method does is return the value of an expression, you can omit the curly braces, the return keyword, and the semi-colon
        // from the method body part. Thus, instead of
        // { return x.value%2 == 0; }, you can just write
        // x.value%2 == 0

        // Error
        // Here, observe that the variable d is already defined so your argument list cannot use d as a variable name. 
        // It would be like defining the same variable twice in the same scope.
        // filterData(al, d -> d.value % 2 == 0);

        System.out.println(al);
    }

    // and the following code fragments:
    static void filterData(ArrayList<Data> dataList, Predicate<Data> p) {
        Iterator<Data> i = dataList.iterator();
        while (i.hasNext()) {
            if (p.test(i.next())) {
                i.remove();
            }
        }
    }

    // In Data.java
    private static class Data {

        int value;

        Data(int value) {
            this.value = value;
        }

        public String toString() {
            return "" + value;
        }
    }

    // =========================================================================================================================================
    static void test01_09() {
        // What can be inserted in the code below so that it will print true when run?

        boolean b01 = false;// WRITE CODE HERE

        // The test method of Predicate returns a boolean. So all you need for your  body part in your lambda expression is an expression that
        // returns a boolean. isEmpty() is a valid method of ArrayList, which returns true if there are no elements in the list.
        // Therefore, al.isEmpty() constitutes a valid body for the lambda expression in this case.
        b01 = checkList(new ArrayList(), al -> al.isEmpty());
        //
        //
        // The add method of ArrayList returns a boolean. Further, it returns true if the list is altered because of the call to add.
        // In this case, al.add("hello") indeed alters the list because a new element is added to the list.
        b01 = checkList(new ArrayList(), al -> al.add("hello"));
    }

    public static boolean checkList(List list, Predicate<List> p) {
        return p.test(list);
    }

    // =========================================================================================================================================
    static void test01_10() {
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
    public static void main(String[] args) {
        test01_02();
    }
}
