package br.fernando.ch14_tests.part06_final.test03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test03_03 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Given:
        List<Double> dList = Arrays.asList(10.0, 12.0);

        dList.stream().forEach(x -> {
            x = x + 10;
        });

        dList.stream().forEach(d -> System.out.println(d));

        // What will it print when compiled and run?
        //
        // 10.0
        // 12.0

        // Remember that Double objects are immutable. Therefore, the first call to forEach does not change the elements in the original list
        // on which the stream is based. It only replaces the elements of that stream with new ones.
        //
        // Therefore, when you call dlist.stream() the second time, a new stream is created and it has the same elements as the list i.e.
        // 10.0 and 12.0.
        //
        // If it were a Stream of mutable objects such as StringBuilders and if you append something to the elements in the first forEach,
        // that would change the original StringBuilder objects contained in the list. In that case, the second forEach will actually print
        // the updated values. For example, the following code will indeed print ab and aab.
        //

        List<StringBuilder> dList02 = Arrays.asList(new StringBuilder("a"), new StringBuilder("aa"));
        dList02.stream().forEach(x -> x.append("b"));
        dList02.stream().forEach(x -> System.out.println(x));
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // What will the following code print?

        Path p1 = Paths.get("c:\\temp\\test.txt");
        Path p2 = Paths.get("c:\\temp\\report.pdf");

        System.out.println(p1.resolve(p2));

        // c:\temp\report.pdf

        // When the argument to resolve starts with the root (such as c: or, on *nix, a /), the result is same as the argument.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What will the following code print?

        List<String> ls = Arrays.asList("Tom Cruise", "Tom Hart", "Tom Hanks", "Tom Brady");

        Predicate<String> p = str -> {
            System.out.println("Looking...");
            return str.indexOf("Tom") > -1;
        };

        boolean flag = ls.stream().filter(str -> str.length() > 8).allMatch(p);

        // Looking...
        // Looking...
        // Looking...

        // Tom Hart is removed, flag is false;

        // Explanation
        // Remember that filter is an intermediate operation, which means it will not execute until a terminal operation is invoked on the stream.
        // allMatch is a short circuiting terminal operation. Thus, when allMatch is invoked, the filter method will be invoked and it will keep
        // only those elements in the stream that satisfy the condition given in the filter i.e. the string must be longer than 8 characters.  
        // After this method is done, only three elements will be left in the stream. When allMatch is invoked, the code in predicate will
        // be executed for each element until it finds a mismatch. Thus, Looking... will be printed three times.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // You have a file named customers.dat in c:\company\records directory. You want to copy all the lines in this file to another
        // file named clients.dat in the same directory and you have the following code to do it:

        Path p1 = Paths.get("c:\\company\\records\\customers.dat");

        // LINE 20 - INSERT CODE HERE

        Path p2 = null;

        p2 = p1.resolveSibling("clients.dat");

        // or

        p2 = Paths.get("c:", p1.subpath(0, 2).toString(), "clients.dat");

        try (BufferedReader br = new BufferedReader(new FileReader(p1.toFile())); BufferedWriter bw = new BufferedWriter(new FileWriter(p2.toFile()))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Which of the following options can be inserted independent of each other at //LINE 20 to make it work?
        // Assume that the current directory for the program when it runs is c:\code.

        // Path p2 = p1.resolveSibling("clients.dat");

        // or

        // Path p2 = Paths.get("c:", p1.subpath(0, 2).toString(), "clients.dat");
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What will the following code print when executed?
        Set<String> names = new TreeSet<String>();
        names.add("111");
        names.add("222");
        names.add("111");
        names.add("333");
        for (String name : names) {
            switch (name) {
            default:
                System.out.print("333 ");
                break;
            case "111":
                System.out.print("111 ");
            case "222":
                System.out.print("222 ");
            }
        }

        // 111 222 222 333

        // Explanation
        // There are a few things this question tries to test you on:
        //
        // 1. A Set contains unique elements. If you add an element that already exists, it is ignored.
        // Thus, in this case, the set will only contain 3 elements (and not 4) because 111 is being added twice.
        //
        // 2. A TreeSet keeps its elements sorted. So when you iterate through a TreeSet, you get elements in sorted order.
        //
        // 3. The order of case labels in a switch block doesn't matter. So even though default is the first statement in this switch block,
        // it will not be executed when the name is 111 or 222.
        //
        // 4. A break statement causes the execution control to come out of the switch block. If you do not have a break after a case block,
        // the control will fall through to the next case block. Thus, when name is 111, the control will go to case 111 block and, after
        // executing the case block, fall through to the next case block. Thus, it will print 111 222.
        //
        // Therefore, the given code will print 111 222 (when name is 111), 222 (when name is 222) and 333 (when name is 333).
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Given:
        List<String> l1 = Arrays.asList("a", "b");
        List<String> l2 = Arrays.asList("1", "2");

        // Which of the following lines of code will print the following values?
        // a
        // b
        // 1
        // 2

        Stream.of(l1, l2).flatMap((x) -> x.stream()).forEach((x) -> System.out.println(x));

        // The objective of flatMap is to take each element of the current stream and replace that element with elements contained in
        // the stream returned by the Function that is passed as an argument to flatMap.
        //
        // It is perfect for the requirement of this question. You have a stream that contains Lists.
        //
        // So you need a Function object that converts a List into a Stream of elements. Now, List does have a method named stream()
        // that does just that. It generates a stream of its elements.
        //
        // Therefore, the lambda expression x -> x.stream() can be used here to create the Function object.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
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
    static void test01_08() throws Exception {
        // Which of the following classes will you use to generate random int values from multiple threads without degrading performance?

        // java.util.concurrent.ThreadLocalRandom

        // int nextInt(int least, int bound) Returns a pseudorandom, uniformly distributed value between the given least value (inclusive) and bound (exclusive).
        // Example usage:

        int r = ThreadLocalRandom.current().nextInt(1, 11); // This will return a random int from 1 to 10

        // Explanation
        // Consider instead using ThreadLocalRandom in multithreaded designs.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // What will be printed when the following code is compiled and run?
        class Device implements AutoCloseable {

            String header = null;

            public void open() {
                header = "OPENED";
                System.out.println("Device Opened");
            }

            public String read() throws IOException {
                throw new IOException("Unknown");
            }

            public void writeHeader(String str) throws IOException {
                System.out.println("Writing : " + str);
                header = str;
            }

            public void close() {
                header = null;
                System.out.println("Device closed");
            }
        }

        try (Device d = new Device()) {
            d.open();
            d.read();
            d.writeHeader("TEST");
            d.close();
        } catch (IOException e) {
            System.out.println("Got Exception");
        }

        // Device Opened
        // Device closed
        // Got Exception
        //
        // Catch and finally blocks are executed after the resource opened in try-with-resources is closed.
        // Therefore, Device Closed will be printed before Got Exception.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // What will the following code fragment print?

        final String p1Win = "c:\\personal\\.\\photos\\..\\readme.txt";

        final String p2Win = "c:\\personal\\index.html";

        Path p1 = Paths.get(p1Win);
        Path p2 = Paths.get(p2Win);
        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        // ..\..\..\..\index.html -> win

        // ../../../../index.html -> linux
        //
        // Observe that if you append this path to p1, you will get p2. Therefore, this is the right answer. p1 + ..\..\..\..\index.html
        // =>c:\\personal\\.\\photos\\..\\readme.txt + ..\..\..\..\index.html
        // =>c:\\personal\\.\\photos\\.. + ..\..\..\index.html
        // =>c:\\personal\\.\\photos + ..\..\index.html
        // =>c:\\personal\\. + ..\index.html
        // =>c:\\personal + index.html
        // =>c:\\personal\index.html

        // A ".." implies parent folder, therefore imagine that you are taking off one ".." from the right side of the plus sign and removing
        // the last name of the path on the left side of the plus sign.

        // Explanation
        // You need to understand how relativize works for the purpose of the exam. The basic idea of relativize is to determine a path,
        // which, when applied to the original path will give you the path that was passed.
        //
        // For example, "a/c" relativize "a/b" is "../b" because "/a/c/../b" is "/a/b" Notice that "c/.." cancel out.
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // A WatchKey can be in which of the following states?

        // Ready
        // Invalid
        // Signaled

        // Explanation
        // When initially created the key is said to be ready. When an event is detected then the key is signaled and queued so that it can be
        // retrieved by invoking the watch service's poll or take methods. Once signalled, a key remains in this state until its reset method
        // is invoked to return the key to the ready state.
        // Cancels the registration with the watch service. Upon return the watch key will be invalid.
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // Which of the following statements are true regarding the Fork/Join framework?
        //
        // The worker threads in the ForkJoinPool extend java.lang.Thread and are created by a factory.
        // By default, they are created by the default thread factory but another factory may be passed in the constructor. They do extend Thread.
        //
        // One worker thread may steal work from another worker thread.
        // A ForkJoinPool differs from other kinds of ExecutorService mainly by virtue of employing work-stealing: all threads in the pool
        // attempt to find and execute subtasks created by other active tasks (eventually blocking waiting for work if none exist).
        // This enables efficient processing when most tasks spawn other subtasks (as do most ForkJoinTasks).
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        // What will the following code print?

        Object v1 = IntStream.rangeClosed(10, 15) //
                .boxed() //
                .filter(x -> x > 12) //
                .parallel() //
                .findAny(); //

        Object v2 = IntStream.rangeClosed(10, 15) //
                .boxed() //
                .filter(x -> x > 12) //
                .sequential()//
                .findAny();

        // <An Optional containing 13, 14, or 15> : <An Optional conataining 13, 14, or 15>
        System.out.println(v1 + ":" + v2);

        // (Note: < and > in the options below denote the possible output and not the sign themselves.)
        //
        // Explanation
        // Since the first stream is made parallel, it may be partitioned into multiple pieces and each piece may be processed by
        // a different thread. findAny may, therefore, return a value from any of those partitions.
        // Hence, any number from 13 to 15 may be printed.
        //
        // The second stream is sequential and therefore, ideally, findAny should return the first element. However, findAny is
        // deliberately designed to be non-deterministic. Its API specifically says that it may return any element from the stream.
        // If you want to select the first element, you should use findFirst.
        //
        // Further findAny returns Optional object. Therefore, the output will be Optional[15] instead of just 15 (or any other number).
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {
        // Which of the following attributes are supported by BasicFileAttributeView?

        // lastModifiedTime
        // isSymbolicLink

        // Explanation
        // The following attributes are supported by BasicFileAttributeView:
        //
        // Name ----------------------Type
        // "lastModifiedTime"---------FileTime
        // "lastAccessTime"-----------FileTime
        // "creationTime"-------------FileTime
        // "size"---------------------Long
        // "isRegularFile"------------Boolean
        // "isDirectory---------------Boolean
        // "isSymbolicLink"-----------Boolean
        // "isOther"------------------Boolean
        // "fileKey"------------------Object
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // A WatchKey can be in which of the following states?

        // Ready
        // Invalid
        // Signaled

        // Explanation
        // When initially created the key is said to be ready. When an event is detected then the key is signaled and queued so that it can be
        // retrieved by invoking the watch service's poll or take methods. Once signalled, a key remains in this state until its reset method
        // is invoked to return the key to the ready state.
        // Cancels the registration with the watch service. Upon return the watch key will be invalid.
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // What will be the result of compilation and execution of the following code ?

        DoubleStream is = DoubleStream.of(0, 2, 4); // 1
        double sum = is.filter(i -> i % 2 != 0).sum(); // 2
        System.out.println(sum); // 3

        // It will print 0.0
        //
        // Explanation
        // The sum method of all numeric streams (i.e. IntStream, LongStream, and DoubleStream) returns a primitive value of the same type as 
        // the type of the stream (i.e. int, long, or double, respectively). 
        // If the stream has no elements, it returns 0, 0, or 0.0. Thus, there is no compilation error in the given code.
        //
        // Note that this is unlike the average method which always returns an OptionalDouble for all numeric streams. If the stream has no elements, 
        // average returns an empty OptionalDouble and not a null
        //
        // In this case, the original stream has three elements, but the filter method returns a new stream with no elements (because none of 
        // the elements match the filter condition). Thus, the sum is 0.0, which is what is printed
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // What will the following code print when compiled and run?

        Instant start = Instant.parse("2015-06-25T16:13:30.00z");
        start.plus(10, ChronoUnit.HOURS);
        System.out.println(start);

        Duration timeToCook = Duration.ofHours(1);
        Instant readyTime = start.plus(timeToCook);
        System.out.println(readyTime);

        LocalDateTime ltd = LocalDateTime.ofInstant(readyTime, ZoneId.of("GMT+2"));
        System.out.println(ltd);

        // 2015-06-25T16:13:30Z
        // 2015-06-25T17:13:30Z
        // 2015-06-25T19:13:30

        // 1. The first println prints the same Instant because calling plus on an Instance doesn't change that Instance. It returns a new instance.
        //
        // 2. Adding 1 hour to 16:13, will change it to 17:13, which is what the second println prints.
        //
        // 3. A Timezone of GMT+2 means that in that time zone, the time is 2 hours ahead of GMT. Thus, when it is 17:13 in GMT, it is 19:13 in GMT+2.
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // Which of the following statements are true regarding the try-with-resources statement?

        // catch and finally blocks are executed after the resources opened in the try blocks are closed.

        // Explanation
        // You need to know the following points regarding try-with-resources statement for the exam:
        //
        // 1. The resource class must implement java.lang.AutoCloseable interface. Many standard JDK classes such as BufferedReader, BufferedWriter)
        // implement java.io.Closeable interface, which extends java.lang.AutoCloseable.
        //
        // 2. Resources are closed at the end of the try block and before any catch or finally block.
        //
        // 3. Resources are not even accessible in the catch or finally block. For example:
        /**
         * <pre>
        try(Device d = new Device()){ 
            d.read();         
        }finally{
            d.close(); //This will not compile because d is not accessible here.         
        }
         * </pre>
         */
        // 4. Resources are closed in the reverse order of their creation.
        //
        // 5. Resources are closed even if the code in the try block throws an exception.
        //
        // 6. java.lang.AutoCloseable's close() throws Exception but java.io.Closeable's close() throws IOException.
        //
        // 7. If code in try block throws exception and an exception also thrown while closing is resource, the exception thrown while
        // closing the resource is suppressed. The caller gets the exception thrown in the try block.
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {
        // You need to traverse through a given directory. After visiting the directory, you want to print the number of files visited in that directory.
        // Which three methods of FileVisitor will be helpful in implementing this requirement?

        // postVisitDirectory
        // visitFile
        // preVisitDirectory
    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
        // Which of these statements about interfaces are true?

        // 1. Interfaces are always abstract.

        // 2. An interface can have static methods.

        // 3. Interfaces cannot be final.

        // 4. In Java 8, interfaces allow multiple implementation inheritance through default methods.
    }

    static interface I1 {

        default void m1() {
            System.out.println("In I1.m1");
        }
    }

    static interface I2 {

        public default void m1() {
            System.out.println("In I2.m1");
        }
    }

    // This class will not compile.
    // static class C1 implements I1, I2{ }

    // This class will compile because it provides its own implementation of m1
    static class C2 implements I1, I2 {

        public void m1() {
            System.out.println("in C2.m1");
        }
    }

    // =========================================================================================================================================
    static void test01_21() throws Exception {
        // Which of the following code fragments is/are appropriate usage(s) of generics?

        List<String> list01 = new ArrayList<>();
        list01.add("A");
        list01.addAll(new ArrayList<>());

        // and

        List<String> list02 = new ArrayList<>();
        list02.add("A");
        List<? extends String> list2 = new ArrayList<>();
        list02.addAll(list2);

        // list2.add("C"); // you can't do that
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
