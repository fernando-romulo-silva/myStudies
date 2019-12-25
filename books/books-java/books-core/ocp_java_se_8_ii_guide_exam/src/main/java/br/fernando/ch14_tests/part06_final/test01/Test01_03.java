package br.fernando.ch14_tests.part06_final.test01;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test01_03 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Given:

        Stream<String> names = Stream.of("Sarah Adams", "Suzy Pinnell", "Paul Basgall");

        // Which of the following options will correctly assign a stream of just first names to firstNames?

        // Returns a stream consisting of the results of applying the given function to the elements of this stream.
        Stream<String> firstNames = names.map(e -> e.split(" ")[0]);

        System.out.println(firstNames.collect(Collectors.toList()));
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // You have been given an instance of an Executor and you use that instance to execute tasks.
        // How many threads will be created for executing these tasks by the Executor?

        // Number of threads created by the Executor depends on how the Executor instance was created.

        // Explanation:
        // Thus, the number of threads used by an Executor instance depends on the class of that instance and how that instance was created.
        // For example, if an instance was created using Executors.newSingleThreadExecutor(), only one thread will be created, but if it was
        // created using Executors.newFixedThreadPool(5), five threads will be created.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What will the following code fragment print when compiled and run?

        Locale myloc = new Locale.Builder().setLanguage("en").setRegion("UK").build(); // L1
        ResourceBundle msgs = ResourceBundle.getBundle("mymsgs", myloc);

        Enumeration<String> en = msgs.getKeys();
        while (en.hasMoreElements()) {
            String key = en.nextElement();
            String val = msgs.getString(key);
            System.out.println(key + " : " + val);
        }

        // Assume that only the following two properties files (contents of the file is shown below the name of the file) are accessible to the code.
        //
        // 1. mymsgs.properties
        // okLabel=OK
        // cancelLabel=Cancel
        //
        // 2. mymsgs_en_UK.properties
        // okLabel=YES
        // noLabel=NO

        // noLabel : NO
        // okLabel : YES
        // cancelLabel : Cancel
        //
        //
        // mymsgs.properties is the base file for this resource bundle. Therefore, it will be loaded first. Since the language and region
        // specific file is also present (_en_UK), it will also be loaded and the values in this file will be superimposed on the values
        // of the base file.
        // Remember that if there were another properties file named mymsgs_en.properties also present, then that file would have been loaded
        // before mymsgs_en_UK.properties.

    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
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
        //
        // Since one resource was created, its close method will be called (which prints Closing device D1). Any exception that is thrown
        // while closing a resource is added to the list of suppressed exceptions of the exception thrown while
        // opening a resource (or thrown from the try block.)
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Consider the following code.

        Date d = new Date();
        DateFormat df = null; // 1 INSERT CODE HERE
        String s = null; // 2 INSERT CODE HERE
        System.out.println(s);

        // What should be inserted at //1 and //2 above so that it will print the date in default date format for the UK Locale?

        df = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.UK);
        // and
        df.format(d);

        // Explanation
        // There are several getXXXInstance() methods in DateFormat class. For the purpose of the exam, you should remember the following:
        //
        // static DateFormat getDateInstance()
        // Gets the date formatter with the default formatting style for the default locale.
        //
        // static DateFormat getDateInstance(int style)
        // Gets the date formatter with the given formatting style for the default locale.
        //
        // static DateFormat getDateInstance(int style, Locale aLocale)
        // Gets the date formatter with the given formatting style for the given locale.
        //
        // The formatting styles include DEFAULT, FULL, LONG, MEDIUM, and SHORT.
        //
        // Errors
        //
        // The getInstance() method doesn't take any parameter. static getInstance() - Get a default date/time formatter that uses the
        // SHORT style for both the date and the time.
        DateFormat.getInstance();
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Consider the following piece of code:
        Locale.setDefault(new Locale("fr", "CA")); // Set default to French Canada
        Locale l = new Locale("jp", "JP");
        ResourceBundle rb = ResourceBundle.getBundle("appmessages", l);
        String msg = rb.getString("greetings");
        System.out.println(msg);

        // You have created two resource bundle files with the following contents:

        // #In appmessages.properties:
        // greetings=Hello

        // #In appmessages_fr_FR.properties:
        // greetings=bonjour

        // Given that this code is run on machines all over the world. Which of the following statements are correct?

        // It will run without any exception all over the world.

        // Explanation
        // While retrieving a message bundle, you are passing a locale explicitly (jp/JP). Therefore, it will first try to load
        // appmessages_jp_JP.properties.
        //
        // Since this file is not present, it will look for a resource bundle for default locale. Since you are changing the default locale to
        // "fr", "CA", it will look for appmessages_fr_CA.properties, which is also not present.
        //
        // Remember that when a resource bundle is not found for a given locale, the default locale is used to load the resource bundle.

    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Assuming that the local time when the following code is executed is 9.30 AM, what will it print?

        LocalTime now = LocalTime.of(9, 30); // LocalTime.now();
        LocalTime gameStart = LocalTime.of(10, 15); // it's 10 am = 10h

        long timeConsumed = 0;
        long timeToStart = 0;

        if (now.isAfter(gameStart)) {
            timeConsumed = gameStart.until(now, ChronoUnit.HOURS);
        } else {
            timeToStart = now.until(gameStart, ChronoUnit.HOURS);
        }

        System.out.println(timeToStart + " " + timeConsumed);

        // 0 0
        //
        // Explanation
        // 1. The isAfter method of LocalTime returns true only if this LocalTime is after the passed LocalTime.
        // (I.e. if both are same, then it will return false) In this case, it is not. Therefore, timeConsumed will remain 0.

        // 2. The until method return the difference between the two time periods in given units. Here, the difference is 45 minutes but the
        // unit is HOURS, therefore, it will return 0.
        //
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {

        // Which of the following options correctly replaces the line marked //1 with code that uses method reference?

        process(() -> new MyProcessor()); // 1 REPLACE THIS LINE OF CODE

        process(MyProcessor::new); // This is the correct syntax for referring to a constructor

        // Explanation
        // Observe that the TestClass's static method process takes Supplier<MyProcessor> as an argument.
        // The lambda expression // ()->new MyProcessor() // creates a Supplier with the implementation of its functional method named get
        // that just returns a new MyProcessor object.
        // If it makes it easier to understand, you can think of this lambda expression in terms of an anonymous class like this:

        new Supplier<MyProcessor>() {

            public MyProcessor get() {
                return new MyProcessor(); // the body of a lambda expression becomes the body of the functional method of the functional interface.    
            }
        };

        // The expression new MyProcessor() in the lambda expression can be replaced by MyProcessor::new.
    }

    private static class MyProcessor {

        public void process() {
            System.out.println("Processing ");
        }
    }

    public static void process(Supplier<MyProcessor> s) {
        s.get().process();
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Which of the following code fragments correctly prints all the roots of the default file system?

        FileSystem fs = FileSystems.getDefault();

        Iterable<Path> roots = fs.getRootDirectories();

        for (Path p : roots) {
            System.out.println(p);
        }
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Which interface do you need to implement if you want to recursively walk a directory tree?

        // FileVisitor

        // Explanation
        // java.nio.file.FileVisitor is an important interface for the exam. It declares the following methods:
        //
        // FileVisitResult postVisitDirectory(T dir, IOException exc) : Invoked for a directory after entries in the directory, and all of
        // their descendants, have been visited.
        //
        // FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs) : Invoked for a directory before entries in the directory are visited.
        //
        // FileVisitResult visitFile(T file, BasicFileAttributes attrs) : Invoked for a file in a directory.
        //
        // FileVisitResult visitFileFailed(T file, IOException exc) : Invoked for a file that could not be visited.
        //
        //
        // java.nio.file.SimpleFileVisitor is a basic implementation class for the above interface.
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // What will the following code print when compiled and run?
        List<Integer> ls = Arrays.asList(1, 2, 3);

        // ls.stream()
        // .forEach(System.out::print).map(a->a*2)
        // .forEach(System.out::print);

        // Compilation failure
        // Remember that forEach is a terminal operation. It returns void. This means that you cannot chain methods after calling forEach.
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // Given that test1.txt exists but test2.txt doesn't exist, consider the following code?

        Path p1 = Paths.get("c:\\temp\\test1.txt");
        Path p2 = Paths.get("c:\\temp\\test2.txt");

        Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES);

        // Copying of the attributes is platform and system dependent.
        //
        // Attempts to copy the file attributes associated with source file to the target file.
        //
        // The exact file attributes that are copied is platform and file system dependent and therefore unspecified.
        // Minimally, the last-modified-time is copied to the target file if supported by both the source and target file store.
        // Copying of file timestamps may result in precision loss.

        // Files.copy method will copy the file test1.txt into test2.txt. If test2.txt doesn't exist, it will be created.
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
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
    static void test01_14() throws Exception {
        // It is required that //1 must compile and //2 must NOT compile.
        // Which of the following declarations of class Transaction will satisfy the requirement?

        class Transaction<T, S extends T> {

            public Transaction(T t, S s) {
            }
        }

        // Given the following code:

        Transaction<Number, Integer> t1 = new Transaction<>(1, 2); // 1

        // Transaction<Number, String> t2 = new Transaction<>(1, "2"); // 2

        // Putting the upper bound for the second type as S extends T, you are forcing that the second type must be a subclass of the first type.
        // Thus, if the first type is Number, the second cannot be a String because String doesn't extend Number.
        // This will make the //1 compile but not //2.
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // You have to complete a big task that operates on a large array of integers. The task has to look at each element of the array and
        // update that element. The new value of the element is generated by a utility class's static method, which takes in the existing
        // value as a parameter and returns the new value. This method is computationally very intensive.
        //
        // What would be a good approach to solve this problem?
        //
        // Subclass RecursiveAction and implement the compute() method that computes the new value but does not return anything.
        //
        // Create a RecursiveAction that subdivides the task into two, then forks one of the tasks and computes another.
        // This is a standard way of using the Fork/Join framework. You create a RecursiveTask or RecursiveAction
        // (depending on where you need to return a value or not) and in that RecursiveTask, you subdivide the task into two equal parts.
        // You then fork out one of the halfs and compute the second half.
        //
        // Explanation
        // Since there is no requirement to do anything with the newly computed value (such as summing them up), you don't need to
        // return that value to anybody. You just need to update the array element with the new value. Therefore, you don't need RecursiveTask,
        // you need RecursiveAction. The following is a possible implementation:

        class ComplicatedAction extends RecursiveAction {

            private static final long serialVersionUID = 1L;

            int[] ia;

            int from;

            int to;

            public ComplicatedAction(int[] ia, int from, int to) {
                this.ia = ia;
                this.from = from;
                this.to = to;
            }

            protected void compute() {

                if (from == to) { // Update the value using logic implemented somewhere else.
                    ia[from] = UtilityClass.utilityMethod(ia[from]);
                } else {
                    int mid = (from + to) / 2;
                    ComplicatedAction newtask1 = new ComplicatedAction(ia, from, mid);
                    ComplicatedAction newtask2 = new ComplicatedAction(ia, mid + 1, to);
                    newtask2.fork();
                    newtask1.compute();
                    newtask2.join();
                }
            }
        }

        int ia[] = new int[]{ 1, 2, 3, 4, 5, 6, 7 };
        ForkJoinPool fjp = new ForkJoinPool();
        ComplicatedAction st = new ComplicatedAction(ia, 0, 6);
        fjp.invoke(st);
        System.out.print("New Array Values = ");

        for (int i : ia) {
            System.out.print(i + " ");
        }
    }

    private static class UtilityClass {

        public static int utilityMethod(int i) {
            return i + 1;
        }
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // Which of the following are valid ways to create a LocalDateTime?

        java.time.LocalDateTime.of(2015, 10, 1, 10, 10);
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // Given that your code is being run in a locale where the language code is fr and country code is CA, which of the following file names 
        // represents a valid resource bundle file name?

        // MyResource_fr_CA.properties
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
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
    static void test01_19() throws Exception {
        // Given Course Class

        class Course {

            private String id;

            private String category;

            public Course(String id, String category) {
                this.id = id;
                this.category = category;
            }

            public String toString() {
                return id + " " + category;
            }

            public String getId() {
                return id;
            }

            public String getCategory() {
                return category;
            }
        }

        // What will the following code print?
        List<Course> s1 = Arrays.asList(new Course("OCAJP", "Java"), new Course("OCPJP", "Java"), //  
                new Course("C#", "C#"), new Course("OCEJPA", "Java"));

        s1.stream() //
                .collect(Collectors.groupingBy(c -> c.getCategory())) //
                .forEach((m, n) -> System.out.println(n));

        // [C# C#]
        // [OCAJP Java, OCPJP Java, OCEJPA Java]
        //
        // 1. Collectors.groupingBy(Function<? super T,? extends K> classifier) returns a Collector that groups elements of a Stream into multiple groups.
        // Elements are grouped by the value returned by applying a classifier function on an element.
        //
        // 2. It is important to understand that the return type of the collect method depends on the Collector that is passed as an argument.
        // In this case, the return type would be Map<K, List<T> because that is the type specified in the Collector returned by the groupingBy method.
        //
        // 3. Java 8 has added a default forEach method in Map interface. This method takes a BiConsumer function object and applies this function to each
        // key-value pair of the Map. In this case, m is the key and n is the value.
        //
        // 4. The given code provides a trivial lambda expression for BiConsumer that just prints the second parameter, which happens to be the value part
        // of of the key-value pair of the Map.
        //
        // 5. The value is actually an object of type List<Course>, which is printed in the output. Since there are two groups, two lists are printed.
        // First list has only one Course element and the second list has three.
    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
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
    static void test01_21() throws Exception {
        // What will the following code print when compiled and run?
        for (String s : sa) {
            switch (s) { // A NullPointerException will be thrown here if "s" is null.
            case "a":
                System.out.println("Got a");
                break;
            case "b":
                System.out.println("Got b");
                break;
            // case null : System.out.println("null"); //This line will not even compile
            }
        }

        // Got a
        // Got b

        // Explanation
        // There is only one important point that you need to know about String in a switch statement:
        //
        // The String in the switch expression is compared with the expressions associated with each case label as if the String.equals method were
        // being used. This means if the string in the switch expression turns out to be null, it will cause a NullPointerException to be thrown.
    }

    static String[] sa = new String[]{ "a", "b", "c" };

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
