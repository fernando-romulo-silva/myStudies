package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par05_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test05 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
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
    static void test01_03() throws Exception {
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
    static void test01_04() throws Exception {
        // Which of the following is/are valid functional interfaces?

        // The use of abstract keyword is redundant here, but it is legal.

        F f = () -> System.out.println();
    }

    private static interface F {

        default void m() {
        }

        abstract void n();
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
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
    static void test01_06() throws Exception {
        // Given :

        ArrayList<Data> al = new ArrayList<Data>();
        al.add(new Data(1));
        al.add(new Data(2));
        al.add(new Data(3));

        // Which of the following options can be inserted above so that it will print 3?

        printUsefulData(al, (Data d) -> {
            return d.value > 2;
        });

        printUsefulData(al, d -> d.value > 2);

        // 1. Compiler already knows the parameter types, so Data can be omitted from the parameter list.
        //
        // 2. When there is only one parameter in the method, you can omit the brackets because the compiler can associate the -> sign with
        // the parameter list without any ambiguity.
        //
        // 3. When all your method does is return the value of an expression, you can omit the curly braces, the return keyword, and the
        // semi-colon from the method body part. Thus, instead of { return d.value>2; }, you can just write d.value>2
    }

    private static void printUsefulData(ArrayList<Data> dataList, Predicate<Data> p) {
        for (Data d : dataList) {
            if (p.test(d))
                System.out.println(d.value);
        }
    }

    static public class Data {

        int value;

        Data(int value) {
            this.value = value;
        }
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
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
    static void test01_08() throws Exception {
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
    static void test01_09() throws Exception {
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
    static void test01_10() throws Exception {
        // Which of the following statements are correct regarding a functional interface?

        // It must have exactly one abstract method and may have other default or static methods.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
