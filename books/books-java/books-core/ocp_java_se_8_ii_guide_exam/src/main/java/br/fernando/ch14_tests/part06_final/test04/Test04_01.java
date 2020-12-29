package br.fernando.ch14_tests.part06_final.test04;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test04_01 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // What will the following code print?

        AtomicInteger ai = new AtomicInteger();

        Stream<String> stream = Stream.of("old", "king", "cole", "was", "a", "merry", "old", "soul").parallel();

        stream.filter(e -> {
            ai.incrementAndGet();
            return e.contains("o");

        }).allMatch(x -> x.indexOf("o") > 0);

        System.out.println("AI = " + ai);

        // Any number between 1 to 8
        //
        // Explanation
        // 1. In the given code, stream refers to a parallel stream. This implies that the JVM is free to break up the original stream into
        // multiple smaller streams, perform the operations on these pieces in parallel, and finally combine the results.
        //
        // 2. Here, the stream consists of 8 elements. It is, therefore, possible for a JVM running on an eight core machine to split this
        // stream into 8 streams (with one element each) and invoke the filter operation on each of them. If this happens, ai will
        // be incremented 8 times.
        //
        // 3. It is also possible that the JVM decides not to split the stream at all. In this case, it will invoke the filter predicate on
        // the first element (which will return true) and then invoke the allMatch predicate (which will return false because "old".indexOf("o") is 0).
        // Since allMatch is a short circuiting terminal operation, it knows that there is no point in checking other elements because the result
        // will be false anyway. Hence, in this scenario, ai will be incremented only once.
        //
        // 4. The number of pieces that the original stream will be split into could be anything between 1 and 8 and by applying the same logic
        // as above, we can say that ai will be incremented any number of times between 1 and 8.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Given
        class Book {

            String name;

            public Book(String name) {
                super();
                this.name = name;
            }
        }

        // What changes should be applied to the following class to update it to use generics without changing any functionality?
        // Choose minimal changes that are necessary to take advantage of generics.

        class BookStore {

            Map map = new HashMap(); // 1

            public BookStore() {
                map.put(new Book("A111"), new Integer(10)); // 2
            }

            public int getNumberOfCopies(Book b) { // 3
                Integer i = (Integer) map.get(b); // 4
                return i == null ? 0 : i.intValue(); // 5
            }
        }

        // Replace line //1 with Map<Book, Integer> map = new HashMap<Book, Integer>();
        // There is no need to change //4 and //5.
        //
        // Explanation
        //
        // Generics allow you to write type safe code by letting you specify what kinds of object you are going to store in a collection.
        // Here, by replacing Map with Map<Book, Integer>, we are making sure that only Book-Integer pairs are stored in the map.

        class BookStoreAnswer {

            Map<Book, Integer> map = new HashMap<Book, Integer>(); // 1

            public BookStoreAnswer() {
                map.put(new Book("A111"), new Integer(10)); // 2
            }

            public int getNumberOfCopies(Book b) { // 3
                Integer i = (Integer) map.get(b); // 4
                return i == null ? 0 : i.intValue(); // 5
            }
        }
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What will the following code fragment print?
        Path p1 = Paths.get("photos\\goa");
        Path p2 = Paths.get("\\index.html");
        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        // java.lang.IllegalArgumentException will be thrown

        // Note that if one path has a root (for example, if a path starts with a // or c:) and the other does not, relativize cannot work
        // and it will throw an IllegalArgumentException.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // What will the following code print when run?

        try {
            m2();
        } catch (Exception e) {
            Throwable[] ta = e.getSuppressed(); // Returns an array containing all of the exceptions that were suppressed, typically by the try-with-resources statement
            for (Throwable t : ta) {
                System.out.println(t.getMessage());
            }

            // the Exception e is java.lang.RuntimeException: Exception from finally
        }

        // It will not print any thing.

        // Explanation
        // In the given code, method m2() throws an exception explicitly from the catch block as well as from the finally block. Since this is an explicit finally
        // block (and not an implicit finally block that is created when you use a try-with-resources statement), the exception thrown by the finally block
        // is the one that is thrown from the method. The exception thrown from the catch block is lost. It is not added to the suppressed exceptions list of
        // the exception thrown from the finally block.
        //
        // Therefore, e.getSuppressed() returns an array with 0 elements and nothing is printed.
        //
        // Had the code for m3() been something like this:
        // Now, if m1() throws an exception and r.close() also throws an exception, the exception thrown by m1 would have been the one thrown by the method m3
        // and the exception thrown by r.close() would have been added to the list of suppressed exception of the exception thrown from the try block.
    }

    public static void m1() throws Exception {
        throw new Exception("Exception from m1");
    }

    @SuppressWarnings("finally")
    public static void m2() throws Exception {
        try {
            m1();
        } catch (Exception e) { // Can't do much about this exception so rethrow it
            throw e;
        } finally {
            throw new RuntimeException("Exception from finally");
        }
    }

    public static void m3() throws Exception {
        try (Connection c = DriverManager.getConnection("url", "user", "password");) {
            m1();
        }
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What will the following code print when compiled and run?

        List<String> names = Arrays.asList("charles", "chuk", "cynthia", "cho", "cici");

        // int x = names.stream().filter(name->name.length()>4).collect(Collectors.counting());
        // System.out.println(x);

        // Compilation failure

        // Collector.counting returns a Collector that returns a object Long.
        // You cannot assign it to an int without a cast. If you make it long, it will print 2 because there are two elements with
        // length greater than 4.

        long x = names.stream().filter(name -> name.length() > 4).collect(Collectors.counting()); // change to long type

        System.out.println(x);

        // Explanation
        // public static <T> Collector<T,?,Long> counting()
        // Returns a Collector accepting elements of type T that counts the number of input elements.
        // If no elements are present, the result is 0.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Identify correct statements about the fork/join framework?

        // 1º It is well suited for computation intensive tasks that can be broken into smaller pieces recursively
        //
        // 2º A ForkJoinPool differs from other kinds of ExecutorService mainly by virtue of employing work-stealing.
        // What this really means is that if worker thread is done with a task, it will pick up a new task irrespective of which thread created
        // that task. In a fork/join framework, any worker thread may spawn new tasks and it is not necessary that the tasks spawned by a threads
        // will be executed by that particular thread. They can be executed by any available thread.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Which of the following texts can occur in a valid resource bundle file?

        // greetings=bonjour
        //
        // and
        //
        // greetings1=bonjour
        // greetings2=no bonjour
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // What will the following code fragment print?
        Path p1 = Paths.get("photos\\..\\beaches\\.\\calangute\\a.txt");

        Path p2 = p1.normalize();
        System.out.println(p2);

        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        Path p4 = p2.relativize(p1);
        System.out.println(p4);

        System.out.println(p1.getNameCount() + " " + p2.getNameCount() + " " + p3.getNameCount() + " " + p4.getNameCount());

        // 6 3 9 9

        // 1. p1 has 6 components and so p1.getNameCount() will return 6.
        //
        // 2. normalize applies all the .. and . contained in the path to the path. Therefore, p2 contains beaches\calangute\a.txt,
        // that is 3 components.
        //
        // 3. p3 contains ..\..\..\..\..\..\beaches\calangute\a.txt, that is 9 components.
        //
        // 4. p4 contains ..\..\..\photos\..\beaches\.\calangute\a.txt, that is 9 components.
        //
        // You need to understand how relativize works for the purpose of the exam. The basic idea of relativize is to determine a path, which,
        // when applied to the original path will give you the path that was passed.
        //
        // For example, "a/b" relativize "c/d" is "../../c/d" because if you are in directory b, you have to go two steps back and then one step
        // forward to c and another step forward to d to be in d. However, "a/c" relativize "a/b" is "../b" because you have to go only one step
        // back to a and then one step forward to b.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Which of the following statements is true about DSYNC constant defined in StandardOpenOption enum?

        // DSYNC keeps only the file content and not the file meta data synchronized with the underlying storage device.

        // D in DSYNC is for data. When you open a file with this attribute, it means that every update to the file's content will be written
        // synchronously to the underlying storage device. The meta data may still remain unsynchronized.
        // In other words, any change in the data of the file will be written to the storage device synchronously, but any change in the meta
        // data may be batched and written to the storage device later. Thus, it makes file operations a slower as compared to when you open
        // the file without any option.
        //
        // SYNC makes sure that both - the data and meta data are synchronized with the storage device.
        // Thus, it makes files operations even slower than DSYNC option.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // What will the following code print when run?

        double amount = 123456.789;
        Locale fr = new Locale("fr", "FR");
        NumberFormat formatter = NumberFormat.getInstance(fr);
        String s = formatter.format(amount);
        formatter = NumberFormat.getInstance();

        // Number amount2 = formatter.parse(s); // parse method throw a exception!

        // System.out.println(amount + " " + amount2);

        // It will not compile.

        // Remember that parse() method of DateFormat and NumberFormat throws java.text.ParseException. So it must either be declared in
        // the throws clause of the main() method or the call to parse() must be wrapped in a try/catch block.
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // Given:
        Path p1 = Paths.get("c:\\a\\b\\c.java");

        // What will the code below return?

        System.out.println(p1.getName(2).toString());

        // c.java
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // Given Student class
        // What can be inserted in the code below so that it will print:
        // {C=[S3], A=[S1, S2]}

        List<Student> ls = Arrays.asList(new Student("S1", Student.Grade.A), new Student("S2", Student.Grade.A), new Student("S3", Student.Grade.C));

        Map<Student.Grade, List<String>> grouping = ls.stream().collect( //
                Collectors.groupingBy(Student::getGrade, //
                        Collectors.mapping(Student::getName, Collectors.toList())) //
        );

        // This code illustrates how to cascade Collectors.
        // Here, you are first grouping the elements by Grade and then collecting each element of a particular grade into a list after mapping
        // it to a String. This will produce the required output.
    }

    static class Student {

        static enum Grade {
            A,
            B,
            C,
            D,
            F
        }

        private final String name;

        private final Grade grade;

        public Student(String name, Grade grade) {
            this.name = name;
            this.grade = grade;
        }

        public Grade getGrade() {
            return grade;
        }

        public String toString() {
            return name + ":" + grade;
        }

        public String getName() {
            return name;
        }
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();

        // You want to retrieve a WatchKey from a WatchService such that if no key is available, you want to wait for at most 1 minute.
        // Which of the following statements will you use?

        WatchKey key = watchService.poll(1, TimeUnit.MINUTES);

        // Explanation
        // WatchService has two types of methods for retrieving the WatchKeys - poll and take. The two poll methods return null if no key is
        // present and do not wait for ever. The take method doesn't return null but keeps waiting until a key is available.

        // WatchKey take() throws InterruptedException
        // Retrieves and removes next watch key, waiting if none are yet present. Notice that this method does not take any parameter.
        // So it waits until a key becomes available (or until the WatchService is closed, in which case the method throws ClosedWatchServiceException).
        // If you want to wait for a specified time, you should use the poll method.
        //
        // WatchKey poll(long timeout, TimeUnit unit) throws InterruptedException
        // Retrieves and removes the next watch key, waiting if necessary up to the specified wait time if none are yet present.
        //
        // WatchKey poll()
        // Retrieves and removes the next watch key, or null if none are present.
    }

    // =========================================================================================================================================
    static void test01_14(String records1, String records2) throws Exception {
        // Consider the following code:
        try (InputStream is = new FileInputStream(records1); OutputStream os = new FileOutputStream(records2);) {
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
                System.out.println("Read and written bytes " + bytesRead);
            }
        } catch (IOException | IndexOutOfBoundsException e) {
            // e = new FileNotFoundException();
            e.printStackTrace();
        }

        // Assuming appropriate import statements and the existence of both the files, what will happen when the program is compiled and run?

        // The program will not compile because the line e = new FileNotFoundException(); in catch block is invalid.

        // The exception parameter in a multi-catch clause is implicitly final. Thus, it cannot be reassigned. Had there been only one exception
        // in the catch clause (of type that is compatible with FileNotFoundException such as IOException or Exception, but not RuntimeException),
        // it would have been valid.
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // Given:
        Locale locale = new Locale("en", "US");
        ResourceBundle rb = ResourceBundle.getBundle("test.MyBundle", locale);

        // Which of the following are valid lines of code? (Assume that the ResourceBundle has the values for the given keys.)

        Object obj = rb.getObject("key1");

        String[] vals = rb.getStringArray("key2");

        // Keys are always Strings. So you cannot use an int to get value for a key.
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // What will the following code print?

        List<String> names = Arrays.asList("Peter", "Paul", "Pascal");

        Optional<String> ops = names.stream() //
                .parallel() //
                // .allMatch(name->name!=null) //
                .filter(name -> name.length() > 6).findAny();

        System.out.println(ops);

        // It will not compile.
        //
        // Remember that allMatch is a terminal operation that returns a boolean. You cannot chain any operation after that.
        // If you had just this :
        Optional<String> ops2 = names.stream().parallel().findAny();
        // It could potentially print any name from the list because of .parallel().
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // What will the following code fragment print when compiled and run?
        Locale myloc = new Locale.Builder().setLanguage("hinglish").setRegion("IN").build(); // L1

        ResourceBundle msgs = ResourceBundle.getBundle("mymsgs", myloc);

        Enumeration<String> en = msgs.getKeys();
        while (en.hasMoreElements()) {
            String key = en.nextElement();
            String val = msgs.getString(key);
            System.out.println(key + "=" + val);
        }

        // Assume that only the following two properties files (contents of the file is shown below the name of the file) are accessible
        // to the code.

        // 1. mymsgs_hinglish_US.properties
        // okLabel=OK
        // cancelLabel=Cancel

        // 2. mymsgs_hinglish_UK.properties
        // okLabel=YES
        // noLabel=NO
        //
        // It will throw an exception at run time.
        //
        // The code is trying to create a resource bundle for a specific locale i.e. hinglish_IN. To create this bundle, at least one of
        // mymsgs_hinglish_IN.properties,  mymsgs_hinglish.properties, and mymsgs.properties must be present in the classpath. Since none of these
        // files is not available, the resource bundle cannot be created. An exception will therefore be thrown when you call getBundle().
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // Consider the following program that computes the sum of all integers in a given array of integers:

        class ComplicatedTask extends RecursiveTask<Integer> {

            private static final long serialVersionUID = 1L;

            int[] ia;

            int from;

            int to;

            static final int THRESHOLD = 3;

            public ComplicatedTask(int[] ia, int from, int to) {
                this.ia = ia;
                this.from = from;
                this.to = to;
            }

            public int transform(int t) {
                // this is a CPU intensive operation that
                // transforms t and returns the value
                return 0;
            }

            protected Integer compute() {
                if (from + THRESHOLD > to) {
                    int sum = 0;
                    for (int i = from; i <= to; i++) {
                        sum = sum + transform(ia[i]);
                    }
                    return sum;
                } else {
                    int mid = (from + to) / 2;

                    ComplicatedTask newtask1 = new ComplicatedTask(ia, from, mid);
                    ComplicatedTask newtask2 = new ComplicatedTask(ia, mid + 1, to);

                    newtask2.fork();

                    int x = newtask1.compute();
                    int y = newtask2.join();

                    return x + y;
                }
            }
        }

        // What changes must be done together so that it will work as expected?
        //
        // The compute method must be changed to return a value.
        //
        // The values returned by the newtask1 and newtask2 should be added and returned.

        int ia[] = new int[]{ 1, 2, 3, 4, 5, 6, 7 };

        ForkJoinPool fjp = new ForkJoinPool();
        ComplicatedTask st = new ComplicatedTask(ia, 0, 6);

        int sum = fjp.invoke(st);
        System.out.println("sum = " + sum);
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {
        // What can be inserted in the following code so that it will print [21, 32, 43] ?
        List<Integer> ls = Arrays.asList(11, 22, 33);

        UnaryOperator<Integer> func = x -> x + 10; // INSERT CODE HERE

        ls.replaceAll(func);

        System.out.println(ls);

        // UnaryOperator<Integer> func = x->x+10;
        // List's replaceAll method takes a UnaryOperator as argument. This option implements a UnaryOperator correctly
        //
        // he difference between a UnaryOperator and a Function is that the type of the return value of a Function can be different from the 
        // type of its argument while return type of a UnaryOperator is always the same.
    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
        // What will the following code print?
        Duration d = Duration.ofMillis(1100);
        System.out.println(d);
        d = Duration.ofSeconds(61);
        System.out.println(d);

        // PT1.1S
        // PT1M1S

        // Explanation
        // The format of the returned string will be PTnHnMnS, where n is the relevant hours, minutes or seconds part of the duration.
        // Any fractional seconds are placed after a decimal point i the seconds section. If a section has a zero value, it is omitted.
        // The hours, minutes and seconds will all have the same sign.
        // Examples:     
        // "20.345 seconds"                 -- "PT20.345S     
        // "15 minutes" (15 * 60 seconds)   -- "PT15M"     
        // "10 hours" (10 * 3600 seconds)   -- "PT10H"     
        // "2 days" (2 * 86400 seconds)     -- "PT48H"
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
