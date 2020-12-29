package br.fernando.ch14_tests.part03_tough_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Test01 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following code fragments correctly prints all the roots of the default file system?

        FileSystem fs = FileSystems.getDefault();

        Iterable<Path> roots = fs.getRootDirectories();

        for (Path p : roots) {
            System.out.println(p);
        }
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
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
    static void test01_03() throws Exception {
        // Which of the following File Attribute views support manipulating the owner of a file?

        // PosixFileAttributeView

        // Unix based file systems provide this view. This view supports the following attributes in addition to BasicFileAttributeView:
        // "permissions" : Set<PosixFilePermission>
        // "group" : GroupPrincipal

        // The permissions attribute is used to update the permissions, owner, or group-owner as by invoking the setPermissions,
        // setOwner, and setGroup methods respectively.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
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
    static private class PathTest {

        static Path p1 = Paths.get("c:\\a\\b\\c");

        public static String getValue() {
            String x = p1.getName(1).toString();
            String y = p1.subpath(1, 2).toString();
            return x + " : " + y;
        }
    }

    static void test01_05() throws Exception {
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

        // What will be printed when the following code is compiled and run?
        try (Device d = new Device()) {
            d.open();
            d.writeHeader("TEST");
            d.close();
        } catch (IOException e) {
            System.out.println("Got Exception");
        }

        // Device Opened
        // Writing : TEST
        // Device closed
        // Device closed

        // Explanation
        //
        // 1. d.open() will print Device Opened
        // 2. d.writeHeader("TEST") will print Writing : TEST
        // 3. d.close() will print Device Closed
        //
        // Since Device is instantiated in try-with-resources block, d.close() will be called automatically at the end of the try block.
        // Thus, Device Closed will be printed again. The fact that the code explicitly calls d.close() is irrelevant.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Which of the following statements will print /test.txt when executed on a *nix system?

        System.out.println(Paths.get("/", "test.txt"));

        // Note that this will throw an exception on Windows. It is not clear if the exam explicitly mentions the platform on which the code
        // is run in the problem statement of questions that depend on platform specifics.
        //
        // In case it does not, our recommendation is to assume that the code is run on a *nix system unless the paths used in the code are
        // clearly for a windows system (such as paths starting with c:\> ).
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Complete the following code fragment so that it will print owner's name of a file:

        Path path = Paths.get("c:\\temp\\test.txt");
        // INSERT CODE HERE
        PosixFileAttributeView pfav = Files.getFileAttributeView(path, PosixFileAttributeView.class);
        PosixFileAttributes attrs = pfav.readAttributes();

        // Pay Attention with attrs."property", don't use java beans pattern
        String ownername = attrs.owner().getName();

        System.out.println(ownername);

        // Ex:
        attrs.creationTime();
        attrs.group();
        // etc

        // But we have:
        attrs.isDirectory();
        attrs.isSymbolicLink();
        attrs.isOther();
        attrs.isRegularFile();
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
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
    public static void test01_11(String records1, String records2) throws Exception {
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
    private static Path p1 = Paths.get("c:\\main\\project\\Starter.java");

    static void test01_13() throws Exception {
        // What will the following code print when run?

        System.out.println(getData());

        // main

        // Explanation 
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

    private static String getData() {
        String data = p1.getName(0).toString();
        return data;
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
    static void test01_16() throws Exception {
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
    static void test01_17() throws Exception {
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
    static void test01_18() throws Exception {
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

        int ia[] = new int[]{ 1, 2, 3, 4, 5, 6, 7 };

        ForkJoinPool fjp = new ForkJoinPool();
        ComplicatedTask st = new ComplicatedTask(ia, 0, 6);

        int sum = fjp.invoke(st);
        System.out.println("sum = " + sum);

    }

    static class ComplicatedTask extends RecursiveTask<Integer> {

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

    // =========================================================================================================================================
    static void test01_19() throws Exception {
        // You have a collection (say, an ArrayList) which is read by multiple reader threads and which is modified by a single writer thread.
        // The collection allows multiple concurrent reads but does not tolerate concurrent read and write.
        // Which of the following strategies will you use to obtain best performance?

        //
        // Encapsulate the collection into another class and use ReadWriteLock to manage read and write access.
    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
        // Given that test1.txt exists but test2.txt doesn't exist, consider the following code?

        Path p1 = Paths.get("c:\\temp\\test1.txt");
        Path p2 = Paths.get("c:\\temp\\test2.txt");
        copy1(p1, p2);

        // Which file attributes will be copied over to test2.txt?

        // Copying of the attributes is platform and system dependent.

        // Attempts to copy the file attributes associated with source file to the target file. The exact file attributes that are copied is
        // platform and file system dependent and therefore unspecified. Minimally, the last-modified-time is copied to the target file if
        // supported by both the source and target file store. Copying of file timestamps may result in precision loss.
    }

    public static void copy1(Path p1, Path p2) throws Exception {
        Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES);
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
