package br.fernando.ch14_tests.part06_final.test03;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test03_01 {

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
        // What will the following code print when compiled and run?

        List<StringBuilder> messages = Arrays.asList(new StringBuilder(), new StringBuilder());
        messages.stream().forEach(s -> s.append("helloworld"));
        messages.forEach(s -> {
            s.insert(5, ",");
            System.out.println(s);
        });

        // StringBuilder is mutable. It has several overloaded insert methods (one for each data type such as boolean, char, int, and String)
        // that insert the given object into a given position.

        // hello,world
        // hello,world
        //
        // Explanation
        // Java 8 has added a default method default void forEach(Consumer<? super T> action) in java.lang.Iterable interface (which is extended by java.util.List interface).
        //
        // java.util.Stream interface also contains the same void forEach(Consumer<? super T> action) method that applies the given action to each element of the stream.
        // This is a terminal operation
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What will the following code do?

        class MyFileVisitor implements FileVisitor<Path> {

            private final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/t*");

            public FileVisitResult visitFile(Path p, BasicFileAttributes attr) {
                System.out.println("Visited " + p);
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (matcher.matches(dir)) {
                    return FileVisitResult.CONTINUE;
                } else {
                    return FileVisitResult.SKIP_SUBTREE;
                }
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        }

        MyFileVisitor mfv = new MyFileVisitor();
        Files.walkFileTree(Paths.get("c:\\temp"), mfv);

        // It prints the name of each file in c:\temp and visits each directory under c:\temp if the directory name starts
        // with a t and prints the name of each file in the visited directory.
        //
        //
        // Explanation
        //
        // 1. The glob pattern glob:**/t* matches a path whose last component starts with a t. ** indicates that the Path may consist
        // of multiple directories. Thus, it matches, c:\temp or c:\temp\test but not c:\temp\sample.
        //
        // 2. The path name is matched only in preVisitDirectory. So once it is decided that the directory will be visited, each
        // file under that directory will be visited.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Identify correct statement(s) about the following code:

        Optional<String> stro = Optional.of(getValue());// 1
        System.out.println(stro.isPresent());// 2
        System.out.println(stro.get());// 3
        System.out.println(stro.orElse(null));// 4

        // It will throw a NullPointerException at //1
        // Optional.of method throws NullPointerException if you try to create an Optional with a null value.
    }

    static String getValue() {
        return null;
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What is the state of the WatchKey at the end of the following code?

        Path path = Paths.get("C:/temp");

        WatchService watchService = FileSystems.getDefault().newWatchService();

        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

        WatchKey key = watchService.take();

        // status of key here

        // Signaled

        // Explanation
        // When initially created the key is said to be ready. When an event is detected then the key is signaled and queued so that it can be
        // retrieved by invoking the watch service's poll or take methods. Once signalled, a key remains in this state until its reset method
        // is invoked to return the key to the ready state.
        // Cancels the registration with the watch service. Upon return the watch key will be invalid.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
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
    static void test01_07() throws Exception {

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
    static void test01_08() throws Exception {
        // Which of the following interface definitions can use Lambda expressions?
        // AA
        // It has one abstract method.

        // C
        // It has one abstract method.
        //
        // Explanation
        // To take advantage of lambda expressions, an interface must be a "functional" interface, which basically means that the interface must
        // have exactly one abstract method. A lambda expression essentially provides the implementation for that abstract method.
        // It does not matter whether the abstract method is declared in this interface or a super interface.
        //
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
    static void test01_09() throws Exception {
        // Which of the following is a valid event type for StandardWatchEventKinds?

        // ENTRY_CREATE

        // Explanation
        // When you get WatchEvents from a WatchKey, you can get the event type for each WatchEvent using its kind() method.
        // The return type of this method is WatchEvent.Kind<T> and it returns the one of the following constants defined by class
        // java.nio.file.StandardWatchEventKinds:

        // static WatchEvent.Kind<Path> ENTRY_CREATE Directory entry created.

        // static WatchEvent.Kind<Path> ENTRY_DELETE Directory entry deleted.

        // static WatchEvent.Kind<Path>ENTRY_MODIFY Directory entry modified.

        // static WatchEvent.Kind<Object> OVERFLOW A special event to indicate that events may have been lost or discarded.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
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
    static void test01_11() throws Exception {
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
    static void test01_12() throws Exception {
        // Given:
        Path p1 = Paths.get("c:\\temp\\test1.txt");
        Path p2 = Paths.get("c:\\temp\\test2.txt");

        // Which of the following code fragments moves the file test1.txt to test2.txt, even if test2.txt exists?

        Files.move(p1, p2, StandardCopyOption.REPLACE_EXISTING);

        // and

        Files.copy(p1, p2, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(p1);
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
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
    static void test01_14() throws Exception {
        // What will the following code print when run?

        Path path = Paths.get("");
        WatchService watchService = FileSystems.getDefault().newWatchService();

        path.register(watchService, //
                StandardWatchEventKinds.ENTRY_CREATE, //
                StandardWatchEventKinds.ENTRY_MODIFY, //
                StandardWatchEventKinds.ENTRY_DELETE);

        WatchKey key = watchService.take(); // waits until a key is available

        System.out.println(key.isValid());

        for (WatchEvent<?> watchEvent : key.pollEvents()) {
            Kind<?> kind = watchEvent.kind();
            System.out.println(watchEvent);
        }

        System.out.println(key.isValid());

        // true
        // true

        // Explanation
        // A WatchKey does not become invalid until it is cancelled or the WatchService is closed.
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // Given :
        ArrayList<Data> al = new ArrayList<>();

        Data d = new Data(1);
        al.add(d);
        d = new Data(2);
        al.add(d);
        d = new Data(3);
        al.add(d);

        // Which of the following options can be inserted above so that it will print [1, 3]?

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
    static void test01_16() throws Exception {
        // What changes can be made to the following code so that it will print :

        // Old Rating A
        // New Rating R

        List<Character> ratings = Arrays.asList('U', 'R', 'A');

        ratings.stream() //
                .filter(x -> x == 'A') // 1
                .peek(x -> System.out.println("Old Rating " + x)) // 2
                .map(x -> x == 'A' ? 'R' : x) // 3
                .peek(x -> System.out.println("New Rating " + x)); // 4

        // Replace //4 with
        // .forEach(x->System.out.println("New Rating "+x))

        ratings.stream() //
                .filter(x -> x == 'A') // 1
                .peek(x -> System.out.println("Old Rating " + x)) // 2
                .map(x -> x == 'A' ? 'R' : x) // 3
                .forEach(x -> System.out.println("New Rating " + x)); // 4

        // Explanation
        //
        // To answer this question, you need to know two things - distinction between "intermediate" and "terminal" operations and which
        // operations of Stream are "intermediate" operations.
        //
        // The distinction between an intermediate operation and a termination operation is that an intermediate operation is lazy
        // while a terminal operation is not.
        //
        // When you invoke an intermediate operation on a stream, the operation is not executed immediately.
        // It is executed only when a terminal operation is invoked on that stream.
        //
        // In a way, an intermediate operation is memorized and is recalled as soon as a terminal operation is invoked.
        //
        // You can chain multiple intermediate operations and none of them will do anything until you invoke a terminal operation, at
        // which time, all of the intermediate operations that you invoked earlier will be invoked along with the terminal operation.
        //
        // It is easy to identify which operations are intermediate and which are terminal.
        // All intermediate operations return Stream (that means, they can be chained), while terminal operations don't.
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // What can be inserted in the code below so that it will print 1 2 3?
        class MyProcessor {

            Integer value;

            public MyProcessor(Integer value) {
                this.value = value;
            }

            public void process() {
                System.out.println(value + " ");
            };
        }

        List<Integer> ls = Arrays.asList(1, 2, 3);

        ls.stream() //
                .map(MyProcessor::new) //
                .forEach(MyProcessor::process);

        // Here, map method does have an implicit Integer object in the context, which is in fact the current element of the list.
        // This element will be passed as an argument to the MyProcessor constructor.
        //
        // Similarly, forEach has a MyProcessor object in the context while invoking the process method. Since process is an instance
        // method of MyProcessor, the MyProcessor instance that is available in the context will be used to invoke the process method.
        //
        // An important point to understand with method or constructor references is that you can never pass arguments while referring
        // to a constructor or a method.
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // Given the following code:

        List<Integer> al = Arrays.asList(100, 200, 230, 291, 43);

        // Which of the following options will correctly print the number of elements that are less than 200?

        long count = al.stream().filter((i) -> i < 200).count(); // count return a long

        System.out.println(count);
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {

        // Given

        class Item {

            private int id;

            private String name;

            public Item(int id, String name) {
                this.name = name;
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public int getId() {
                return id;
            }
        }

        List<Item> l = Arrays.asList(new Item(1, "Screw"), new Item(2, "Nail"), new Item(3, "Bolt"));

        // Which of the following options can be inserted in the above code independent of each other, so that the code will print BoltNailScrew?

        l.stream() //
                // 1. This option uses Comparator's comparing method that accepts a function that extracts a Comparable sort key, and returns a
                // Comparator that compares by that sort key. Note that this is helpful only if the type of the object returned by the function
                // implements Comparable. Here, it returns a String, which does implement Comparable and so it is ok.
                //
                // 2. Although the map part is not required because Item class overrides the toString method to print the name anyway, it is valid.
                .sorted(Comparator.comparing(a -> a.getName())) //
                .map((i) -> i.getName())
                //
                .forEach(System.out::print);

        // or

        l.stream()
                // 1. The call to map converts the stream of Items to a stream of Strings.
                //
                // 2. The call to sorted() sorts the stream of String by their natural order, which is what we want here.
                //
                .map((i) -> i.getName()) //
                .sorted()
                //
                .forEach(System.out::print);
    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
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
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
