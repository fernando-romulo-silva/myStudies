package br.fernando.ch14_tests.part06_final.test02;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.OptionalInt;
import java.util.ResourceBundle;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test02_02 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following options will print 10?

        // Answer 01

        IntStream is01 = IntStream.rangeClosed(1, 4);
        int sum01 = is01.reduce(0, (a, b) -> a + b);
        System.out.println(sum01);

        // 1. IntStream.rangeClosed(1, 4) returns 1, 2, 3, and 4 elements in the stream.
        //
        // 2. ((a, b)-> a + b) correctly captures IntBinaryOperator function that sums all the two given values.
        //
        // 3. The reduce method uses a function that combines two values and produces one value repeatedly to reduce all the elements of the
        // stream into one final value.  Notice that the first argument to reduce method is called the "identity" value. This value is the
        // basically the starting value of the result. The first element of the stream is combined with this value.
        // The result is then combined with the second value and so on. This ensures that a final result is always returned even if
        // the stream contains no element.

        // Answer 02

        IntStream is02 = IntStream.range(1, 5);
        OptionalInt sum02 = is02.reduce((a, b) -> a + b);
        System.out.println(sum02.orElse(0));

        // This is same as option 3 except that we are using range instead of rangeClosed.
        // Also notice that here we are not passing an identity value as the first argument to reduce method.
        //
        // Thus, if the stream contains no element, there will not be any result value.
        // For this reason, the return type of this flavor of the reduce method is an OptionInt instead of int.
        // The orElse(0) of OptionalInt will return 0 if the OptionalInt contains null

        // Only for study

        IntStream is03 = IntStream.range(1, 5);
        int sum03 = is03.sum();
        System.out.println(sum03);
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Which of the following File Attribute views support manipulating the owner of a file?

        // PosixFileAttributeView

        // Unix based file systems provide this view. This view supports the following attributes in addition to BasicFileAttributeView:
        // "permissions" : Set<PosixFilePermission>
        // "group" : GroupPrincipal

        // The permissions attribute is used to update the permissions, owner, or group-owner as by invoking the setPermissions,
        // setOwner, and setGroup methods respectively.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        class Book {

            String title;

            Double value;

            public Book(String title, Double value) {
                super();
                this.title = title;
                this.value = value;
            }

            public String getTitle() {
                return title;
            }

            public Double getValue() {
                return value;
            }
        }

        // Given that Book is a valid class with appropriate constructor and getTitle and getPrice methods that return a String and a Double respectively,
        // what can be inserted at //1 and //2 so that it will print the price of all the books having a title that starts with "A"?

        List<Book> books = Arrays.asList( //
                new Book("Atlas Shrugged", 10.0), //
                new Book("Freedom at Midnight", 5.0), //
                new Book("Gone with the wind", 5.0) //
        );

        // 1 INSERT CODE HERE

        // The first line generates a Map<String, Double> from the List using Stream's collect method.
        // The Collectors.toMap method uses two functions to get two values from each element of the stream.  
        // The value returned by the first function is used as a key and the value returned by the second function is used as a value to build the resulting Map.
        Map<String, Double> bookMap = books.stream().collect(Collectors.toMap(b -> b.getTitle(), b -> b.getValue()));

        // The forEach method of a Map requires a BiConsumer. This function is invoked for each entry, that is each key-value pair, in the map.
        // The first argument of this function is the key and the second is the value.
        BiConsumer<String, Double> func = (a, b) -> {
            if (a.startsWith("A")) {
                System.out.println(b);
            }
        };

        // 2 INSERT CODE HERE
        bookMap.forEach(func);
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Given ComplicatedTask class

        // Identify the correct statements about the above code.
        //
        // * THRESHOLD should be increased if the cost of forking a new task dominates the cost of direct computation.
        //
        // There is a cost associated with forking a new task. If the cost of actually finshing the task without forking new subtasks is less,
        // then there is not much benefit in breaking a task into smaller units.
        //
        // Therefore, a balance needs to be reached where the cost of forking is less than direct computation. THRESHOLD determines that level.
        // THRESHOLD value can be different for different tasks.
        //
        // For example, for a simple sum of the numbers, you may keep THRESHOLD high, but for say computing the sum of factorial of each numbers,
        // THRESHOLD may be low.
        //
        // * The logic for computing mid should be such that it divides the task into two equal parts in terms of cost of computation.
        //
        // The whole objective is to divide the task such that multiple threads can compute it in parallel. Therefore, it is better if two sub tasks
        // are equal in terms of cost of computation, otherwise, one thread will finish earlier than the other thereby reducing performance.

        // Example

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

        int ia[] = new int[]{ 1, 2, 3, 4, 5, 6, 7 };

        ForkJoinPool fjp = new ForkJoinPool();
        ComplicatedTask st = new ComplicatedTask(ia, 0, 6);

        int sum = fjp.invoke(st);
        System.out.println("sum = " + sum);
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {

    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
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
    static void test01_07() throws Exception {
        // Given:
        List<Book> books = Arrays.asList( //
                new Book("Gone with the wind", "Fiction"), //
                new Book("Bourne Ultimatum", "Thriller"), //
                new Book("The Client", "Thriller"));

        Reader01 r01 = b -> {
            System.out.println("Reading book " + b.getTitle());
        };

        Reader02 r02 = b -> {
            System.out.println("Reading book " + b.getTitle());
        };

        books.forEach(x -> r01.read(x));

        books.forEach(x -> r02.read(x));

        // What would be a valid definition of Reader for the above code to compile and run without any error or exception?
    }

    // Since the given code uses lambda expression, Reader must be a functional interface (i.e. an interface with exactly one abstract method).
    // The interface can have other default and/or static method.
    private static interface Reader01 {

        void read(Book b);

        default void unread(Book b) {
        }
    }

    // This is a valid functional interface and so the code will work fine. However, observe that the lambda expression in the code will
    // capture the unread method (not the read method, because read method is not abstract). Therefore, r.read() will cause the read method
    // defined in this interface to be invoked instead of the code implemented by the lambda expression.
    private static interface Reader02 {

        default void read(Book b) {
        }

        void unread(Book b);
    }

    private static class Book {

        private final String title;

        private final String genre;

        public Book(String title, String genre) {
            super();
            this.title = title;
            this.genre = genre;
        }

        public String getGenre() {
            return genre;
        }

        public String getTitle() {
            return title;
        }
    }

    // =========================================================================================================================================
    static void test01_08(String records1, String records2) throws Exception {
        // Consider the following method code:

        try (InputStream is = new FileInputStream(records1); OutputStream os = new FileOutputStream(records2);) {
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
                System.out.println("Read and written bytes " + bytesRead);
            }
        } catch (IOException | RuntimeException e) { // LINE 100
        }

        // What can be inserted at //LINE 100 to make the method compile?

        // IOException | RuntimeException
        // Remember, You cannot include classes that are related by inheritance in the same multi-catch block.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Which of the following code fragments allow you to hide the file test.txt?

        Path p01 = Paths.get("c:\\temp\\test.txt");
        Files.setAttribute(p01, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);

        Path p02 = Paths.get("c:\\temp\\test.txt");
        Files.setAttribute(p02, "dos:hidden", true);
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Consider the following piece of code, which is run in an environment where the default locale is English - US:

        Locale.setDefault(new Locale("fr", "CA")); // Set default to French Canada
        Locale l = new Locale("jp", "JP");
        ResourceBundle rb = ResourceBundle.getBundle("appmessages", l);
        String msg = rb.getString("greetings");
        System.out.println(msg);

        // You have created two resource bundles for appmessages, with the following contents:
        //
        // #In English US resource bundle file
        // greetings=Hello
        //
        // #In French CA resource bundle file
        // greetings=bonjour

        // What will be the output?
        //
        // bonjour
        //
        // Explanation
        // Observe that when a resource bundle is not found for a given locale, the default locale is used to load the resource bundle.  
        // Every effort is made to load a resource bundle if one is not found and there are several fall back options.
        // As a last resort, it will try to load a resource bundle with no locale information i.e. appmessages.properties in this case.
        // An exception is thrown when even this resource
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // Given Course Class
        class Course {

            private String id;

            private String name;

            public Course(String id, String name) {
                this.id = id;
                this.name = name;
            }

            public String toString() {
                return id + " " + name;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }

        // What will the following code print?
        List<Course> cList = Arrays.asList( //
                new Course("803", "OCAJP 7"), //
                new Course("808", "OCAJP 8"), //
                new Course("809", "OCPJP 8") //
        );

        cList.stream().filter(c -> c.getName().indexOf("8") > -1).map(c -> c.getId()).collect(Collectors.joining("1Z0-"));

        cList.stream().forEach(c -> System.out.println(c.getId()));

        // 803
        // 808
        // 809

        // Explanation
        // There are multiple flavors of Collectors.joining method and all of them are meant to join CharSequences and return the combined String.
        // or example, if you have a List of Strings, you could join all the elements into one long String using the Collectors
        // returned by these methods. You should check their JavaDoc API description for details
        //
        // The given code ostensibly tries to apply "1Z0-" as prefix to the id value of each course that has a name containing "8", but that is
        // not what the code actually does.
        //
        // It first filters the stream (removing the elements that don't satisfy the condition, which means the stream now contains only
        // two Course objects), then replaces each Course element with a String element (containing just the id, which means the stream
        // now has two Strings "808" and "809"), and then joins each of the elements with "1Z0-" as delimiter to return "8081Z0-809".
        //
        // However, this return value is lost because it is not assigned to any thing.
        //
        // The next line creates a new stream using the original List of Course objects and prints the id for each Course.
        // This prints the three ids values 803, 808, and 809.
    }

    // =========================================================================================================================================
    static private class PathTest {

        static Path p1 = Paths.get("c:\\a\\b\\c");

        public static String getValue() {
            String x = p1.getName(1).toString();
            String y = p1.subpath(1, 2).toString();
            return x + " : " + y;
        }
    }

    static void test01_12() throws Exception {
        // What will the following code print when run?

        System.out.println(PathTest.getValue());

        // b : b
        //
        // Explanation
        // Remember the following points about Path.subpath(int beginIndex, int endIndex)
        //
        // 1. Indexing starts from 0.
        //
        // 2. Root (i.e. c:\) is not considered as the beginning.
        //
        // 3. name at beginIndex is included but name at endIndex is not.
        //
        // 4. paths do not start or end with \.
        //
        // Thus, if your path is "c:\\a\\b\\c",
        //
        // subpath(1,1) will cause IllegalArgumentException to be thrown.
        //
        // subpath(1,2) will correspond to b.
        //
        // subpath(1,3) will correspond to b/c.
        //
        // Remember the following 4 points about Path.getName() method :
        //
        // 1. Indices for path names start from 0.
        //
        // 2. Root (i.e. c:\) is not included in path names.
        //
        // 3. \ is NOT a part of a path name.
        //
        // 4. If you pass a negative index or a value greater than or equal to the number of elements, or this path has zero name elements,
        // java.lang.IllegalArgumentException is thrown. It DOES NOT return null.
        //
        // Thus, for example, If your Path is "c:\\code\\java\\PathTest.java", p1.getRoot() is c:\  ((For Unix based environments, the root is usually / ):
        // p1.getName(0) is code
        // p1.getName(1) is java
        // p1.getName(2) is PathTest.java
        // p1.getName(3) will cause IllegalArgumentException to be thrown.
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        // Which of the following statements will print /test.txt when executed on a *nix system?

        System.out.println(Paths.get("/", "test.txt"));

        // Note that this will throw an exception on Windows. It is not clear if the exam explicitly mentions the platform on which the code
        // is run in the problem statement of questions that depend on platform specifics.
        //
        // In case it does not, our recommendation is to assume that the code is run on a *nix system unless the paths used in the code are
        // clearly for a windows system (such as paths starting with c:\> ).
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {
        // Identify correct statements about the fork/join framework?

        // 1. It increases application throughput for CPU intensive tasks.
        // Since the ForkJoinPool uses work-stealing mechanism, which permits any worker thread to execute any task created by any other
        // worker thread, it does not let the CPU remain idle. This increases application throughput.
        //
        // 2. An advantage of this framework is that it offers a portable means of expressing a parallelizable algorithm without knowing
        // in advance how much parallelism the target system will offer.
        // a. The number of threads is determined (by default) by number of CPU cores available at run time.
        // b. Since any task can be executed by any worker thread, the number of worker threads need not have to be predetermined.
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
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
    static void test01_16() throws Exception {
        boolean result = false;

        // Given:
        List<Integer> ls = Arrays.asList(11, 11, 22, 33, 33, 55, 66);

        // Which of the following expressions will return true?
        //
        result = ls.stream().noneMatch(x -> x % 11 > 0);
        // noneMatch returns true only if none of the elements in the stream satisfy the given Predicate.
        // Here, all the elements are divisible by 11 and x%11 will be 0 for each element.
        // Therefore, the given Predicate will return false for every element, causing noneMatch to return true.
        System.out.println(result);
        //
        // and
        //
        result = ls.stream().distinct().anyMatch(x -> x == 11);
        // anyMatch(Predicate<? super T> predicate) returns whether any elements of this stream match the provided predicate.
        // May not evaluate the predicate on all elements if not necessary for determining the result.
        // If the stream is empty then false is returned and the predicate is not evaluated. This is a short-circuiting terminal operation.
        System.out.println(result);
        //
        // Errors
        //
        // ls.stream().anyMatch(44);
        // This will not compile because anyMatch requires a Predicate object as an argument, not an int.
        //
        // ls.stream().distinct().anyMatch(11);
        // This will not compile because anyMatch requires a Predicate object as an argument, not an int.
        //
        // ls.stream().distinct().allMatch(11);
        // This will not compile because anyMatch requires a Predicate object as an argument, not an int.
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // Given:
        ArrayList<Integer> source = new ArrayList<Integer>();
        source.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));

        List<Integer> destination = Collections.synchronizedList(new ArrayList<Integer>());

        source //
                .parallelStream() // 1
                .peek(item -> {
                    destination.add(item);
                }) // 2
                .forEachOrdered(System.out::print);

        System.out.println("");

        destination //
                .stream() // 3
                .forEach(System.out::print); // 4

        // What changes must be made to the above code so that it will consistently print
        // 123456
        // 123456

        // Replace code at //1 with .stream()
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // Assume that the local time on the system at the time this code is run is 2nd Sep 2015 1:00 AM.
        // What will the following code print when run?

        Period p = Period.between(LocalDate.now(), LocalDate.of(2015, Month.SEPTEMBER, 1));
        System.out.println(p);
        // P-1D

        Duration d = Duration.between(LocalDateTime.now(), LocalDateTime.of(2015, Month.SEPTEMBER, 2, 10, 10));
        System.out.println(d);
        // PT9H10M

        // Note that if the second date is before the first date, a minus sign is included in the output.
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {
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
    static void test01_20() throws Exception {
        // Given
        class Person {

            private String name;

            private int age;

            public Person(String name, int age) {
                this.name = name;
                this.age = age;
            }

            public String getName() {
                return name;
            }

            public int getAge() {
                return age;
            }
        }

        // What will the following code print?
        List<Person> friends = Arrays.asList(new Person("Bob", 31), new Person("Paul", 32), new Person("John", 33));

        double averageAge = friends.stream() //
                .filter(f -> f.getAge() < 30) //
                .mapToInt(f -> f.getAge()) //
                .average() // get a OptionalDouble
                .getAsDouble(); // throw exception here

        System.out.println(averageAge);

        // It will throw an exception at runtime.

        // Explanation
        // The given code chains three operations to a stream. First, it filters out all the element that do not satisfy the condition f.getAge()<30,
        // which means there will no element in the stream, second, it maps each Person element to an int using the mapping function f.getAge().
        // Since there is no element in the stream anyway, the stream remains empty.
        // Finally, the average() method computes the average of all the elements, which is 0.0.
        //
        // However, there is a problem with this code. The average() method actually returns an OptionalDouble (and not Double).
        // Since the stream contains no element and the average() method returns an OptionalDouble containing OptionalDouble.empty.
        //
        // Therefore, getAsDouble method throws a java.util.NoSuchElementException.  
        //
        // To avoid this problem, instead of getAsDouble, you should use orElse(0.0). That way, if the OptionalDouble is empty, it will return 0.0.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
