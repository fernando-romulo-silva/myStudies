package br.fernando.ch14_tests.part01_most_missed_tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test03 {

    // =========================================================================================================================================
    //
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
        // What will the following code print?

        /**
         * <pre>
         *  
        int value = 1,000,000; //1         
        
        switch(value){             
            case 1_000_000 : System.out.println("A million 1"); //2                 
            break;             
            case 1000000 : System.out.println("A million 2"); //3                 
            break;         
        }
         * </pre>
         **/

        // Compilation error because of //1 and //3

        // Explanation
        //
        // 1. You may use underscores (but not commas) to format a number for better code readability. So //1 is invalid.
        //
        // 2. Adding underscores doesn't actually change the number. The compiler ignores the underscores.
        // So 1_000_000 and 1000000 are actually same and you cannot have two case blocks with the same value. Therefore, the second case at //3 is invalid.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // You want to create a new file. If the file already exists, you want the new file to overwrite the existing one (the content of
        // the existing file, if any, should go away). Which of the following code fragments will accomplish this?

        Path myfile = Paths.get("c:\\temp\\test.txt");

        BufferedWriter br = Files.newBufferedWriter(myfile, Charset.forName("UTF-8"), //
                new OpenOption[]{ StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE });

        // If the file already exists, TRUNCATE_EXISTING will take care of the existing content.
        // If the file does not exist, CREATE will ensure that it is created.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
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
    static void test01_05() throws Exception {
        // Which of the following code fragments correctly prints all the roots of the default file system?
        // You had to select 2 options:
        //
        // 1º Option
        File[] roots01 = File.listRoots();
        for (File f : roots01) {
            System.out.println(f);
        }

        // 2º Option
        Iterable<Path> roots02 = FileSystems.getDefault().getRootDirectories();
        for (Path p : roots02) {
            System.out.println(p);
        }

        // Explanation
        // To find all the roots of the default file system, you first need to get the default FileSystem. This is accomplished using:

        FileSystem fs = FileSystems.getDefault();

        Iterable<Path> rootDirectories = fs.getRootDirectories();

        // Next, use the FileSystem.getRootDirectories() method to get all the roots. Note that getRootDirectories() returns an Iterable<Path> object
        // and not an Iterator<Path> object.
        // You can get an Iterator from an Iterable by calling Iterable.iterator() method and then iterate using the Iterator.

        Iterator<Path> it = rootDirectories.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
        // However, if you want to use the enhanced for loop (also called as foreach loop) to go over each element, you don't need an Iterator.
        // Iterable is all you need.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Assuming that a file named "a.java" exists in c:\pathtest\ as well as in c:\pathtest\dir2, what will happen when the following
        // code is compiled and run?

        Path p1 = Paths.get("c:\\pathtest\\a.java");
        Path p2 = Paths.get("c:\\pathtest\\dir2\\a.java");
        Files.move(p1, p2, StandardCopyOption.ATOMIC_MOVE);
        Files.delete(p1);
        System.out.println(p1.toFile().exists() + " " + p2.toFile().exists());

        // It will throw an exception at run time.

        // There are two contradictory factors at play here.
        //
        // 1. By default, Files.move method attempts to move the file to the target file, failing if the target file exists except
        // if the source and target are the same file, in which case this method has no effect. Therefore, this code should throw an exception
        // because a.java exists in the target directory.
        //
        // 2. However, when the CopyOption argument of the move method is StandardCopyOption.ATOMIC_MOVE, the operation is implementation
        // dependent if the target file exists. The existing file could be replaced or an IOException could be thrown.
        // If the exiting file at p2 is replaced, Files.delete(p1) will throw java.nio.file.NoSuchFileException
        //
        // Therefore, in this case, the given code on the whole will end up with an exception.
        //
        // Some candidates have reported being tested on StandardCopyOption.ATOMIC_MOVE. Unfortunately, it is not clear whether the
        // exam tests on the implementation dependent aspect of this option. If you get a question on it and the options do not contain any
        // option referring to its implementation dependent nature, we suggest the following: Assume that the move operation will succeed and
        // then the delete operation will throw a java.nio.file.NoSuchFileException.

    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // What will be printed when the following code is compiled and run?
        class Device {

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

        /**
         * <pre>
         * try (Device d = new Device()) {
         *     d.open();
         *     d.read();
         *     d.writeHeader("TEST");
         *     d.close();
         * } catch (IOException e) {
         *     System.out.println("Got Exception");
         * }
         * </pre>
         */

        // The code will not compile.
        // Remember that when you use a resource in try-with-resources, the resource must implement java.lang.AutoCloseable interface.
        // In this case, although Device class contains the close() method required by this interface but it does not say that it implements
        // AutoCloseable in its declaration. Therefore, compilation will fail.

    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Given Account and PremiumAccount, Which of the following options can be inserted in PremiumAccount independent of each other?

        // Explanation
        // In case of classes, you cannot override a static method with a non-static method and vice-versa.
        //
        // However, in case of interfaces, it is possible for a sub interface to have a non-static (i.e. default) method with the same signature
        // as that of a static method of a super interface.
        //
        // This is because static methods are not inherited in any sense in the sub-interface
        // and that is why it is allowed to declare a default method with the same signature. The reverse is not true though.
        // That is, you cannot have a static method in a sub-interface with the same signature as that of a default method in a super interface.

    }

    interface Account {

        public default String getId() {
            return "0000";
        }
    }

    interface PremiumAccount extends Account { // INSERT CODE HERE

        // An interface can redeclare a default method and also make it abstract.
        // String getId();

        // or (not the both)

        // An interface can redeclare a default method and provide a different implementation.
        default String getId() {
            return "1111";
        };
    }

    // =========================================================================================================================================

    static int count;

    static void test01_09() throws Exception {
        // Consider the following code and the directory structure of c:\works\pathtest shown in the image.

        // pathtest(dir1(c.java, dir12(d.java)), dir2(b.java), a.java)

        class MyFileChecker extends SimpleFileVisitor<Path> {

            private final PathMatcher matcher;

            public MyFileChecker() { // ** ATENTION **
                matcher = FileSystems.getDefault().getPathMatcher("glob:*.java");
            }

            void check(Path p) {
                // ** ATENTION **
                Path name = p.getFileName();
                if (p != null && matcher.matches(name)) {
                    count++;
                }
            }

            public int getCount() {
                return count;
            }

            public FileVisitResult visitFile(Path p, BasicFileAttributes attr) {
                check(p);
                return FileVisitResult.CONTINUE;
            }
        }

        MyFileChecker mfc = new MyFileChecker();
        Files.walkFileTree(Paths.get("c:\\works\\pathtest"), mfc);
        System.out.println(mfc.getCount());

        // Assuming appropriate imports, what will be the output when this program is run?

        // 4
        //
        // Note that Files.walkFileTree method will cause each subdirectory under the given directory to be travelled.
        // For each file in each directory, the FileVisitor's visitFile will be invoked.
        // This particular visitor simply tries to match the full file name with the given glob pattern.
        // The glob pattern will match only the files ending with .java.
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
    static void test01_11() throws Exception {
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
    static void test01_12() throws Exception {
        // Given the following code for monitoring a directory:

        Path path = Paths.get("");
        WatchService watchService = FileSystems.getDefault().newWatchService();

        path.register(watchService, //
                StandardWatchEventKinds.ENTRY_CREATE, //
                StandardWatchEventKinds.ENTRY_MODIFY, //
                StandardWatchEventKinds.ENTRY_DELETE);

        while (true) {
            WatchKey key = watchService.take(); // waits until a key is available

            System.out.println(key.isValid());

            for (WatchEvent<?> watchEvent : key.pollEvents()) {
                Kind<?> kind = watchEvent.kind();
                System.out.println(watchEvent);
            }

            // key.reset(); if you don't reset, you will lost the other events. That's because you catch one event
        }

        // A file is created and then deleted from the monitored directory (with substantial time elapsed between the two actions).
        // How many events will be printed by the above code?

        // 1 (one event)

        // Explanation
        // Here is how watch service works -
        //
        // 1. After getting a WatchKey from WatchService.take method, the events associated with the key can be retrieved using
        // WatchKey.pollEvents method
        //
        // 2. WatchKey.pollEvents method will return all the events that happened between the time key was registered with the WatchService
        // and the call to pollEvents.
        //
        // 3. After the take method has been called and while processing the events retrieved using pollEvents, if any more events
        // happen on the same key, these events can still be retrieved by calling the pollEvents method again.
        // Thus, the pollEvents method can be called multiple times.
        //
        // 4. After take method has been called, the call to watchService.take() again will simply block and will not return until
        // the key is reset by calling the reset() method.
        // In other words, WatchKey.reset must be called before calling WatchService.take again.
        // Any events that happen after calling pollEvents and before calling reset will be lost.
        //
        // Now, in the situation given in the problem statement, if a file is created and deleted too quickly, it is possible for the
        // pollEvents method to return two events. But if there is enough time elapsed between the two events, the second event will be
        // lost because the code doesn't call reset on the key before calling watchService.take.
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        class Book {

            private int id;

            private String title;

            private String genre;

            private String author;

            private double price;

            public Book(String title, double price) {
                super();
                this.title = title;
                this.price = price;
            }

            public int getId() {
                return id;
            }

            public String getGenre() {
                return genre;
            }

            public String getAuthor() {
                return author;
            }

            public double getPrice() {
                return price;
            }

            public String getTitle() {
                return title;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }

        // Given that Book is a valid class with appropriate constructor and getTitle and getPrice methods that return a String and
        // a Double respectively, what will the following code print?

        List<Book> books = Arrays.asList(new Book("Gone with the wind", 5.0), //
                new Book("Gone with the wind", 10.0), //
                new Book("Atlas Shrugged", 15.0));

        books.stream() //
                .collect(Collectors.toMap((b -> b.getTitle()), b -> b.getPrice())) //
                .forEach((a, b) -> System.out.println(a + " " + b));

        // It will throw an exception at run time.

        // The Collector created by Collectors.toMap throws java.lang.IllegalStateException if an attempt is made to store a key
        // that already exists in the Map.
        //
        // If you want to collect items in a Map and if you expect duplicate entries in the source, you should use
        // Collectors.toMap(Function, Function, BinaryOperator) method.
        // The third parameter is used to merge the duplicate entries to produce one entry. For example, in this case, you can do:

        books.stream() //
                .collect(Collectors.toMap(b -> b.getTitle(), b -> b.getPrice(), (v1, v2) -> v1 + v2)) //
                .forEach((a, b) -> System.out.println(a + " " + b));

        // This Collector will sum the values of the entries that have the same key. Therefore, it will print :
        // Gone with the wind 15.0
        // Atlas Shrugged 15.0
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {
        // Identify the correct statements regarding DateFormat and NumberFormat classes.

        // The following line of code will work on a machine in any locale :
        double x = 12345.123;
        String str = NumberFormat.getInstance().format(x);

        // If you don't pass a Locale in getInstance() methods of NumberFormat and DateFormat, they are set to default Locale of the machine.
        // If you run this code on a French machine, it will format the number in French format ( 12 345,123 ) and if you run it on a US machine,
        // it is format the number in US format ( 12,345.123 ).
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // Identify the correct statements regarding the WatchService API provided by Java NIO.
        //
        // * You do not need to specify the OVERFLOW event while registering a watchable with a WatchService  to receive this event.
        // As per the JavaDoc API description of Watchable.register methods, Objects are automatically registered for the OVERFLOW event.
        // This event is not required to be present in the array of events.
        //
        // * The counts for ENTRY_CREATE and ENTRY_DELETE are always 1.
        // This is as per the API description of these events. However, the count for  ENTRY_MODIFY and OVERFLOW can be 1 or greater.
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // Given:
        LocalDate d1 = LocalDate.parse("2015-02-05", DateTimeFormatter.ISO_DATE);
        LocalDate d2 = LocalDate.of(2015, 2, 5);
        LocalDate d3 = LocalDate.now();
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);

        // Assuming that the current date on the system is 5th Feb, 2015, which of the following will be a part of the output?
        //
        // None of the above.
        //
        // All the three printlns will produce 2015-02-05.
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // Identify the correct statements regarding the following program?

        class Device implements AutoCloseable {

            String header = null;

            public Device(String name) throws IOException {
                header = name;
                if ("D2".equals(name))
                    throw new IOException("Unknown");

                System.out.println(header + " Opened");
            }

            public String read() throws IOException {
                return "";
            }

            public void close() {
                System.out.println("Closing device " + header);
                throw new RuntimeException("RTE while closing " + header);
            }
        }

        try (Device d1 = new Device("D1"); Device d2 = new Device("D2")) {
            throw new Exception("test");
        }

        // It will end up with an IOException containing message "Unknown" and a suppressed RuntimeException containing message "RTE while closing D1".

        // Explanation
        // The following output obtained after running the program explains what happens: D1 Opened Closing device D1
        /**
         * <pre>
         * Exception in thread "main" java.io.IOException: Unknown     
         *     at trywithresources.Device.<init>(Device.java:9)     
         *     at trywithresources.Device.main(Device.java:24)     
         *     Suppressed: java.lang.RuntimeException: RTE while closing D1         
         *         at trywithresources.Device.close(Device.java:19)         
         *         at trywithresources.Device.main(Device.java:26) Java Result: 1
         * </pre>
         */

        // Device D1 is created successfully but an IOException is thrown while creating Device D2. Thus, the control never enters the try
        // block and throw new Exception("test") is never executed.
        // Since one resource was created, its close method will be called (which prints Closing device D1). Any exception that is thrown
        // while closing a resource is added to the list of suppressed exceptions of the exception thrown while
        // opening a resource (or thrown from the try block.)
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // Assume that Shape is a valid non-final class. 
        // Identify valid methods:
    }

    static class Shape {

    }

    // Here, list is a List of Shapes and strList is a List of some class that extends from Shape.
    // Any class that extends from Shape IS-A Shape, therefore, you can add elements in strList to list.
    public static List<? extends Shape> m4(List<? extends Shape> strList) {
        List<Shape> list = new ArrayList<>();
        list.add(new Shape());
        list.addAll(strList);
        return list;
    }

    // Same as above
    public void m5(ArrayList<? extends Shape> strList) {
        List<Shape> list = new ArrayList<>();
        list.add(new Shape());
        list.addAll(strList);
    }

    // Errors
    //
    // strList and list both are Lists of some class that extends from Shape. However, the compiler does not know which class(es).
    // Therefore, you cannot add contents of strList to list or vice-versa
    /**
     * <pre>
     * 
     * public List<Shape> m3(ArrayList<? extends Shape> strList) {
     *     List<? extends Shape> list = new ArrayList<>();
     *     list.addAll(strList); // The method addAll is not applicable for the arguments (ArrayList<? extends Shape>)
     *     return list; // Type mismatch: cannot convert from List<? extends Shape> to List<Shape>
     * }
     * 
     * </pre>
     */

    // list is a List of some class that extends from Shape. It may not necessarily be Shape. 
    // Therefore, you cannot add a Shape to list. But you can add all the elements of list to strList.
    /**
     * <pre>
     * 
     * public void m6(ArrayList<Shape> strList) {
     *     List<? extends Shape> list = new ArrayList<>();
     *     list.add(new Shape()); // The method add(? extends Shape) in the type List<? extends Shape> is not applicable for the arguments (Shape)
     *     strList.addAll(list);
     * }
     * 
     * </pre>
     */

    // =========================================================================================================================================
    static void test01_19() throws Exception {
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
    static void test01_20() throws Exception {
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
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
