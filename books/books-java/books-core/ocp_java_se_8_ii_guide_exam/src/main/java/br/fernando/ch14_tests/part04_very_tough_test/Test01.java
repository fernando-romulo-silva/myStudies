package br.fernando.ch14_tests.part04_very_tough_test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test01 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // You want to create a new file. If the file already exists, you want the new file to overwrite the existing one (the content of
        // the existing file, if any, should go away). Which of the following code fragments will accomplish this?

        Path myfile = Paths.get("c:\\temp\\test.txt");

        BufferedWriter br = Files.newBufferedWriter(myfile, Charset.forName("UTF-8"), // 
                new OpenOption[]{ StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE });

        // If the file already exists, TRUNCATE_EXISTING will take care of the existing content. 
        // If the file does not exist, CREATE will ensure that it is created.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Given

        ConcurrentHashMap<String, Object> chm = new ConcurrentHashMap<String, Object>();

        chm.put("a", "aaa");
        chm.put("b", "bbb");
        chm.put("c", "ccc");
        new Thread() {

            public void run() {
                Iterator<Entry<String, Object>> it = chm.entrySet().iterator();
                while (it.hasNext()) {
                    Entry<String, Object> en = it.next();
                    if (en.getKey().equals("a") || en.getKey().equals("b")) {
                        it.remove();
                    }
                }
            }
        }.start();

        new Thread() {

            public void run() {
                Iterator<Entry<String, Object>> it = chm.entrySet().iterator();
                while (it.hasNext()) {
                    Entry<String, Object> en = it.next();
                    System.out.print(en.getKey() + ", ");
                }
            }
        }.start();

        // Which of the following are possible outputs when the above program is run?
        //
        // It may print any combination except: a, or b, or a, b, or b, a,
        //
        // This is correct because the order of iteration is not known and so the thread that removes "a" and "b", may remove them in any order.
        // Thus, the iterator thread may or may not see "a" and/or "b" through its Iterator. However, "c" is never removed from the map and so c,
        // will always be printed.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // The following line of code has thrown java.nio.file.FileSystemNotFoundException when run.
        // What might be the reason(s)?

        Path p1 = Paths.get(new URI("file://e:/temp/records"));

        // The file system, identified by the URI, does not exist.
        //
        // The provider identified by the URI's scheme component is not installed.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Which of the following statements are valid usages of StandardOpenOption options that determine how the file is opened?
        // You had to select three options.

        // 1º Option
        OpenOption[] op1 = new OpenOption[]{ StandardOpenOption.WRITE, StandardOpenOption.DSYNC };

        // 2º Option
        OpenOption[] op2 = new OpenOption[]{ StandardOpenOption.APPEND, StandardOpenOption.SYNC };

        // 3º Option
        OpenOption[] op3 = new OpenOption[]{ StandardOpenOption.READ, StandardOpenOption.SYNC };

        // Explanation
        // Observe that some combinations such as CREATE and READ do not make sense if put together and therefore an IllegalArgumentException
        // will be thrown in such cases. There are questions on the exam that expect you to know such combinations.
        //
        // if you want to truncate a file, then you must open it with an option that allows writing. Thus, READ and TRUNCATE_EXISTING
        // (or WRITE, APPEND, or DELETE_ON_CLOSE) cannot go together.  READ and SYNC (or DSYNC) cannot go together either because when a file is
        // opened for READ, there is nothing to synch, but on some platforms it works.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What will the following code fragment print?
        Path p1 = Paths.get("photos\\goa");
        Path p2 = Paths.get("\\index.html"); // When the path is "\\" it's root windows path
        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        // java.lang.IllegalArgumentException will be thrown

        // Note that if one path has a root (for example, if a path starts with a // or c:) and the other does not, relativize cannot work 
        // and it will throw an IllegalArgumentException.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
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
    static void test01_07(final String records1, String records2) throws Exception {
        // Consider the following method code:

        try (InputStream is = new FileInputStream(records1); //
                OutputStream os = new FileOutputStream(records2);) {

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
                System.out.println("Read and written bytes " + bytesRead);
            }
        } catch (/* *INSERT CODE HERE* */ IOException | RuntimeException e) {
            // LINE 100
        }

        // What can be inserted at //LINE 100 to make the method compile?

        // Correct Answer: IOException | RuntimeException

        // My Answer: IOException|RuntimeException
        // FileNotFountException is a subclass of IOException. You cannot include classes that are related by inheritance in the same multi-catch block.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // What will the following code print
        SimpleDateFormat sdf = new SimpleDateFormat("zzzz");
        System.out.println(sdf.format(new Date())); // Horário de Verão de Brasília

        // Full text time zone name.
        // For example, Eastern Standard Time (If the Locale is US, UK, or any other English based Locale)
        // Heure normale de l'Est (If the Locale is France)

        // Letter---------Date or Time Component------Presentation---------------Examples
        // G--------------Era designator--------------Text-----------------------AD

        // y--------------Year------------------------Year-----------------------1996; 96
        // Y--------------Week year-------------------Year-----------------------2009; 09

        // M--------------Month in year---------------Number---------------------July; Jul; 07

        // w--------------Week in year----------------Number---------------------27
        // W--------------Week in month---------------Number---------------------2
        // D--------------Day in year-----------------Number---------------------189
        // d--------------Day in Month----------------Number---------------------10
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Which of the following statements are valid usages of StandardOpenOption options that control how the file is opened?

        OpenOption[] options01 = new OpenOption[]{ StandardOpenOption.WRITE, StandardOpenOption.DELETE_ON_CLOSE };

        OpenOption[] options02 = new OpenOption[]{ StandardOpenOption.READ, StandardOpenOption.DELETE_ON_CLOSE };

        OpenOption[] options03 = new OpenOption[]{ StandardOpenOption.DELETE_ON_CLOSE, StandardOpenOption.TRUNCATE_EXISTING };
        // This is a valid combination but will throw java.nio.file.NoSuchFileException if the file does not exist.

        OpenOption[] options04 = new OpenOption[]{ StandardOpenOption.READ, StandardOpenOption.SYNC };
        // Ideally, this should be an invalid combination (because when a file is opened for READ, there is nothing to synch) but it works.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Given that the user account under which the following code is run does not have the permission to access a.java, what will the the
        // following code print when run?
        try (BufferedReader bfr = new BufferedReader(new FileReader("c:\\works\\a.java"))) {
            String line = null;
            while ((line = bfr.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) { // } catch (NoSuchFileException | IOException | AccessDeniedException e) {
            e.printStackTrace();
        }

        // It will not compile because the catch clause is invalid.

        // NoSuchFileException is a subclass of IOException. In the same multi-catch block, you cannot include classes that are related
        // by inheritance.   
        // Remember that BufferedReader.close (which is called automatically at the end of the try-with-resources block)
        // and BufferedReader.readLine methods throw java.io.IOException.
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // What will the following code fragment print?
        Path p1 = Paths.get("photos\\..\\beaches\\.\\calangute\\a.txt");
        Path p2 = p1.normalize();
        Path p3 = p1.relativize(p2);
        Path p4 = p2.relativize(p1);

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
    static void test01_12() throws Exception {
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
    static void test01_13() throws Exception {
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
    static void test01_14() throws Exception {
        class Student {
        }

        // Given:
        List<Student> sList = new CopyOnWriteArrayList<Student>();

        // Which of the following statements are correct?

        // Multiple threads can safely add and remove objects from sList simultaneously.

        // Explanation
        // A thread-safe variant of ArrayList in which all mutative operations (add, set, and so on) are implemented by making
        // a fresh copy of the underlying array.
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // Given:

        class Counter {

            // 1
            AtomicInteger count = new AtomicInteger(0);

            public void increment() {
                // 2
                count.incrementAndGet();
            }

            // other valid code
        }

        // This class is supposed to keep an accurate count for the number of times the increment method is called. Several classes share an
        // instance of this class and call its increment method. What should be inserted at //1 and //2?

        // AtomicInteger count = new AtomicInteger(0); at //1
        // count.incrementAndGet(); at //2
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // Consider the following piece of code:
        Locale.setDefault(new Locale("fr", "CA")); // Set default to French Canada
        Locale l = new Locale("jp", "JP");
        ResourceBundle rb = ResourceBundle.getBundle("appmessages", l);
        String msg = rb.getString("greetings");
        System.out.println(msg); // print Hello

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
    static void test01_17() throws Exception {
        // What will the following code print when run?

        Path p1 = Paths.get("c:\\code\\java\\PathTest.java");
        System.out.println(p1.getName(3).toString());

        // It will throw IllegalArgumentException

        // Explanation
        //
        // Thus, for example, If your Path is "c:\\code\\java\\PathTest.java",
        //
        // p1.getRoot() is c:\  ((For Unix based environments, the root is usually / ).
        // p1.getName(0) is code
        // p1.getName(1) is java
        // p1.getName(2) is PathTest.java
        // p1.getName(3) will cause IllegalArgumentException to be thrown.
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // How will you initialize a SimpleDateFormat object so that the following code will print the full text time zone of the given date?

        SimpleDateFormat sdf = new SimpleDateFormat("zzzz", Locale.FRANCE);
        // Remember that if the number of pattern letters is 4 or more, the full form is used; otherwise a short or abbreviated form 
        // is used if available. For parsing, both forms are accepted, independent of the number of pattern letters.

        System.out.println(sdf.format(new Date()));
    }

    // =========================================================================================================================================
    //
    static void test01_19() throws Exception {
        // You have multiple threads in your application that need to generate random numbers between 1 to 10 (both inclusive) frequently.
        // Which of the following statements would you use considering overhead and contention as the main criteria?

        int r = ThreadLocalRandom.current().nextInt(1, 11);
    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
        // What will the following code print when run?

        System.out.println(getRoot());

        // c:\

        // Explanation 
        // Path getRoot()
        // Returns the root component of this path as a Path object, or null if this path does not have a root component.
        //
        // Path getName(int index) 
        // Returns a name element of this path as a Path object. The index parameter is the index of the name element to return. 
        // The element that is closest to the root in the directory hierarchy has index 0. The element that is farthest from the root has index count-1.

        // for example, If your Path is "c:\\code\\java\\PathTest.java",  
        // p1.getRoot()  is c:\  ((For Unix based environments, the root is usually / ). 
        // p1.getName(0)  is code 
        // p1.getName(1)  is java 
        // p1.getName(2)  is PathTest.java 
        // p1.getName(3)  will cause IllegalArgumentException to be thrown.
    }

    static Path p1 = Paths.get("c:\\main\\project\\Starter.java");

    public static String getRoot() {
        String root = p1.getRoot().toString();
        return root;
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_05();
    }
}
