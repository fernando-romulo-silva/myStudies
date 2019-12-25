package br.fernando.ch14_tests.part02_easy_tests;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test01 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following code fragments will you use to create an ExecutorService?

        // Executors

        // .newSingleThreadExecutor();

        // Explanation
        //
        // You need to remember the following points about a few important classes in java.util.concurrent package:
        //
        // 1. ExecutorService interface extends Executor interface. While Executor allows you to execute a Runnable, ExecutorService
        // allows you to execute a Callable.
        //
        // 2. Executors is a utility class that provides several static methods to create instances of ExecutorService. All such methods start
        // with new e.g. newSingleThreadExecutor().
        // You should at least remember the following methods: newFixedThreadPool(int noOfThreads), newSingleThreadExecutor(), newCachedThreadPool(),
        // newSingleThreadScheduledExecutor(), newScheduledThreadPool(int corePoolSize).

        ExecutorService es01 = Executors.newFixedThreadPool(2);

        ExecutorService es02 = Executors.newSingleThreadExecutor();

        ExecutorService es03 = Executors.newCachedThreadPool();

        ExecutorService es04 = Executors.newSingleThreadScheduledExecutor();

        ExecutorService es05 = Executors.newScheduledThreadPool(10);
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Assuming that the directory /works/ocpjp/code exists but /works/ocpjp/code/sample does not exist, what will the following code output?

        Path d1 = Paths.get("/works");
        Path d2 = d1.resolve("ocpjp/code"); // 1

        d1.resolve("ocpjp/code/sample"); // 2

        d1.toAbsolutePath(); // 3

        System.out.println(d1);
        System.out.println(d2);

        // \works
        // \works\ocpjp\code
        //
        // 1. The first println for d1 is straight forward.
        //
        // 2. d2 is created by resolving "ocpjp/code" with "/works". Since "ocpjp/code" is a relative path, d2 will contain "/works/ocpjp/code"

        // Explanation
        // 1. Path operations don't really care about whether the path actually exists on the file system. A Path object just represents an
        // abstract path that may or may not exist. It is only when you actually try to create or write something to the given path that its
        // existence is required.
        //
        // 2. As per JavaDoc description of Path interface, its implementations are immutable and safe for use by multiple concurrent
        // threads. This implies that the operations such as resolve and toAbsolutePath don't change the Path object itself.
        // They return a new Path object.
        //
        // In this case, lines //2 and //3 will actually produce Path objects representing "\works\ocpjp\code\sample"
        // and "C:\works" respectively. (Assuming that C:\ is the root of the file system.)
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
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
    static void test01_04() throws Exception {
        // Given classes
        // What should be inserted in the following code such that run method of Merger will be executed only after the thread started at 4 and
        // the main thread have both invoked await?

        // Make ItemProcessor extend Thread instead of implementing Runnable
        class ItemProcessor extends Thread { // implements Runnable { // LINE 1

            CyclicBarrier cb;

            public ItemProcessor(CyclicBarrier cb) {
                this.cb = cb;
            }

            public void run() {
                System.out.println("processed");
                try {
                    cb.await();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        class Merger implements Runnable { // LINE 2

            public void run() {
                System.out.println("Value Merged");
            }
        }

        Merger m = new Merger();

        CyclicBarrier cb = new CyclicBarrier(2, m); // LINE 3

        ItemProcessor ip = new ItemProcessor(cb);

        ip.start(); // LINE 4

        cb.await();

        // 1. ItemProcessor needs to extend Thread otherwise ip.start() will not compile.
        //
        // 2. Since there are a total two threads that are calling cb.await ( one is the ItemProcessor thread and another one is the main thread),
        // you need to create a CyclicBarrier with number of parties parameter as 2.
        // If you specify the number of parties parameter as 1, Merger's run will be invoke as soon as the any thread invokes await but that is not
        // what the problem statement wants.
        //
        // Explanation:
        // Briefly, a CyclicBarrier allows multiple threads to run independently but wait at one point until all of the coordinating threads arrive
        // at that point. Once all the threads arrive at that point, all the threads can then proceed.
        // It is like multiple cyclists taking different routes to reach a particular junction. They may arrive at different times but they will wait
        // there until everyone arrives. Once everyone is there, they can go on futher independent of each other.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Given that your code is being run in a locale where the language code is fr and country code is CA, which of the following file names 
        // represents a valid resource bundle file name?

        // MyResource_fr_CA.properties
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Given:
        Path p = Paths.get("c:\\temp\\test.txt");

        // Which of the following statements will make the file test.txt hidden?

        Files.setAttribute(p, "dos:hidden", true);
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // What will the following code print?

        Path p1 = Paths.get("\\photos\\vacation");
        Path p2 = Paths.get("\\yellowstone");
        System.out.println(p1.resolve(p2) + "  " + p1.relativize(p2));

        // \yellowstone  ..\..\yellowstone
        //
        // 1. Since the argument to resolve starts with \\, the result will be the same as the argument. If the argument doesn't start with a \
        // and it doesn't start with a root such as c:, the output is the result on appending the argument to the path on which the
        // method is invoked.
        //
        // 2. To arrive at \\yellowstone from \\photos\\vacation, you have to first go two directories up and then down to yellowstone.
        // Therefore, p1.relativize(p2) will be ..\..\yellowstone
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Consider the following code that is a part of a big application:
        ResourceBundle rb = ResourceBundle.getBundle("appmessages", Locale.US);
        String msg = rb.getString("greetings");
        System.out.println(msg);

        // This application is developed by multiple teams in multiple locations and runs in many regions but you want the above code to always
        // print the message in US English. What should be the name of the resource bundle file that contains the actual English text to be
        // displayed for "greetings" and where should that file be placed?
        //
        // appmessages_en_US.properties in CLASSPATH.
        // A resource bundle file should always be present somewhere in the CLASSPATH.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // What will the following code print?

        // ReentrantLock rlock01 = new ReentrantLock();
        // boolean f1 = rlock01.lock();
        // System.out.println(f1);
        // boolean f2 = rlock01.lock();
        // System.out.println(f2);

        //
        // It will not compile.
        ReentrantLock rlock02 = new ReentrantLock();

        // Lock.lock() returns void.
        rlock02.lock();

        // Lock.tryLock() returns boolean. Had the code been:         
        boolean f1 = rlock02.tryLock();
        System.out.println(f1);
        boolean f2 = rlock02.tryLock();
        System.out.println(f2);

        // It would have printed: true true
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
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
    static void test01_11() throws Exception {
        // What will the following code print?

        Path p1 = Paths.get("c:\\temp\\test.txt");
        Path p2 = Paths.get("c:\\temp\\report.pdf");

        System.out.println(p1.resolve(p2));

        // c:\temp\report.pdf

        // When the argument to resolve starts with the root (such as c: or, on *nix, a /), the result is same as the argument.
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // Which of the following code fragments correctly prints all the roots of the default file system?

        FileSystem fs = FileSystems.getDefault();

        Iterable<Path> roots = fs.getRootDirectories();

        for (Path p : roots) {
            System.out.println(p);
        }
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        // Consider the Student code:

        class Student {

            private Map<String, Integer> marksObtained = new HashMap<String, Integer>();

            private ReadWriteLock lock = new ReentrantReadWriteLock();

            public void setMarksInSubject(String subject, Integer marks) {
                // 1 INSERT CODE HERE
                lock.writeLock().lock();

                try {
                    marksObtained.put(subject, marks);
                } finally {
                    // 2 INSERT CODE HERE
                    lock.writeLock().unlock();
                }

            }

            public double getAverageMarks() {

                lock.readLock().lock(); // 3

                double sum = 0.0;
                try {
                    for (Integer mark : marksObtained.values()) {
                        sum = sum + mark;
                    }
                    return sum / marksObtained.size();
                } finally {
                    lock.readLock().unlock();// 4
                }
            }
        }

        // What should be inserted at //1 and //2?

        // lock.writeLock().lock();
        // and
        // lock.writeLock().unlock();

        // Explanation
        //
        // From a ReadWriteLock, you can get one read lock (by calling lock.readLock() ) and one write lock (by calling lock.writeLock()).
        //
        // Even if you call these methods multiple times, the same lock is returned.
        // A read lock can be locked by multiple threads simultaneously (by calling lock.readLock().lock() ), if the write lock is free.
        //
        // If the write lock is not free, a read lock cannot be locked. The write lock can be locked (by calling lock.writeLock().lock() )
        // only by only one thread and only when no thread already has a read lock or the write lock. In other words, if one thread is reading,
        // other threads can read, but no thread can write. If one thread is writing, no other thread can read or write.
        //
        // Methods that do not modify the collection (i.e. the threads that just "read" a collection) should acquire a read lock and threads
        // that modify a collection should acquire a write lock.
        //
        // The benefit of this approach is that multiple reader threads can run without blocking if the write lock is free.
        // This increases performance for read only operations. The following is the complete code that you should try to run:

        final Student s = new Student();

        // create one thread that keeps adding marks
        new Thread() {

            public void run() {
                int x = 0;
                while (true) {
                    int m = (int) (Math.random() * 100);
                    s.setMarksInSubject("Sub " + x, m);
                    x++;
                }
            }
        }.start();

        // create 5 threads that get average marks
        for (int i = 0; i < 5; i++) {
            new Thread() {

                public void run() {
                    while (true) {
                        double av = s.getAverageMarks();
                        System.out.println(av);
                    }
                }
            }.start();
        }

        // Note that if you remove the line //1, //2, //3, and //4, (i.e. if you don't use any locking), you will see a ConcurrentModificationException.
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {
        // Consider the following code.

        Date d = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        // df.setLocale(new Locale("fr", "FR")); // error here

        String s = null; // 1 insert code here.          

        System.out.println(s);

        // What should be inserted at //1 above so that it will print the date in French format?

        // None of these.

        // Observe that the code is doing df.setLocale(...). There is no such setLocale method in DateFormat or NumberFormat.
        // You cannot change the Locale of these objects after they are created. So this code will not compile.

    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // Which of the following texts can occur in a valid resource bundle file?

        // greetings=bonjour
        //
        // and
        //
        // greetings1=bonjour
        // greetings2=no bonjour
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // Identify the correct statements regarding DateFormat and NumberFormat classes.
        // The following line of code will work on a machine in any locale :
        double x = 12345.123;
        String str = NumberFormat.getInstance().format(x);

        // If you don't pass a Locale in getInstance() methods of NumberFormat and DateFormat, they are set to default Locale of the machine.
        // If you run this code on a French machine, it will format the number in French format ( 12 345,123 ) and if you run it on a US machine,
        // it is format the number in US format ( 12,345.123 ).
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // Which of the following classes will you use to generate random int values from multiple threads without degrading performance?

        // java.util.concurrent.ThreadLocalRandom

        // int nextInt(int least, int bound) Returns a pseudorandom, uniformly distributed value between the given least value (inclusive) and bound (exclusive).
        // Example usage:

        int r = ThreadLocalRandom.current().nextInt(1, 11); // This will return a random int from 1 to 10

        // Explanation
        // Consider instead using ThreadLocalRandom in multithreaded designs.
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
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
        ;

        int ia[] = new int[]{ 1, 2, 3, 4, 5, 6, 7 };
        ForkJoinPool fjp = new ForkJoinPool();
        ComplicatedAction st = new ComplicatedAction(ia, 0, 6);
        fjp.invoke(st);
        System.out.print("New Array Values = ");

        for (int i : ia) {
            System.out.print(i + " ");
        }
    }

    static class UtilityClass {

        public static int utilityMethod(int i) {
            return i + 1;
        }
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {
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
    static void test01_20() throws Exception {
        // Which of the following are required to construct a Locale?

        // language
        new Locale("fr"); // language is French
        new Locale("fr", "FR"); // language is French, Country is France.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
