package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par02_functional_interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Test03 {

    // =========================================================================================================================================
    // Working with Consumer
    // You can think of consumers as the opposite of suppliers. Consumers accept one or more arguments and don’t return anything.
    // So this lambda expression
    static void test01() {
        final Consumer<String> redOrBlue = pill -> {

            if (pill.equals("red")) {
                System.out.println("Down the rabbit hole.");
            } else {
                System.out.println("Stay in lala land.");
            }
        };

        redOrBlue.accept("red");

        // As with suppliers, there are variations on consumers in the java.util.function package. IntConsumer, DoubleConsumer, and LongConsumer
        // do what you’d expect: their accept() methods take one primitive argument and avoid the autoboxing you get with Consumer .
        // In addition to these, there’s ObjIntConsumer, ObjDoubleConsumer, and ObjLongConsumer, whose accept() methods take
        // an object (type T ) and an int , a double , or a long
    }

    // =========================================================================================================================================
    // BiConsumer
    static void test01_01() {
        // And finally we have BiConsumer , which is similar, except that its accept() method takes two objects

        Map<String, String> env = System.getenv();

        BiConsumer<String, String> printEvn = (key, value) -> {
            System.out.println(key + " " + value);
        };

        // The printEnv BiConsumer is a lambda expression with two arguments that we are
        // using to display a key and value from a Map . To use the printEnv consumer, we call its
        // accept() method, passing in two strings, and see the result displayed in the console.

        printEvn.accept("USER", env.get("USER"));
    }

    // =========================================================================================================================================
    // ForEach
    public static void test01_02() {
        // forEach() expects a consumer. You’ll end up using forEach() a lot, as it’s a handy way to iterate through collections.

        final List<String> dogNames = Arrays.asList("boi", "clover", "zooey");

        Consumer<String> printName = name -> System.err.println(name);

        dogNames.forEach(printName); // pass the printName consumer to forEach()

        // We could, of course, combine the last two of these lines, like this:
        dogNames.forEach(name -> System.out.println(name));

        // for Map , the consumer you’ll use is a BiConsumer (that is, a consumer whose accept() method expects two arguments, both objects).

        Map<String, String> env = System.getenv();

        BiConsumer<String, String> printEvn = (key, value) -> {
            System.out.println(key + " " + value);
        };

        env.forEach(printEvn); // the forEach() method of Map expects a BiConsumer
    }

    // =========================================================================================================================================
    // Side Effects from Within Lambdas
    public static void test01_03() {

        Map<String, String> env = System.getenv();

        // if you define a variable in the enclosing scope of a lambda expression, you can’t modify that variable from within the lambda:

        String name01; // local variable not intialized

        final BiConsumer<String, String> findUserName01 = (key, value) -> {

            if (key.equals("USER")) {
                // name01 = value; // compile error! username must be effectively final
            }
        };

        // Remember that variables declared outside a lambda expression must be final or effectively final to be
        // used within a lambda expression.

        User user = new User();

        final BiConsumer<String, String> findUserName = (key, value) -> {
            if (key.equals("USER")) {
                // Although we can’t modify a variable from within a lambda
                // expression, we can modify a field of an object.
                user.setUserName(value);
            }
        };

        env.forEach(findUserName01);

        System.out.println("Username from env: " + user.getUserName());
    }

    static class User {

        String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_02();
    }
}
