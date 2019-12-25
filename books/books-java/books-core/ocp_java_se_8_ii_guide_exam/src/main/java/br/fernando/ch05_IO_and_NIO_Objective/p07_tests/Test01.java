package br.fernando.ch05_IO_and_NIO_Objective.p07_tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({ "unused" })
public class Test01 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
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
    static void test01_03() throws Exception {
        // You want to walk through a directory structure recursively. Which interface do you need to implement for this purpose and which
        // class can you extend from to avoid implementing all the methods of the interface?

        // FileVisitor and SimpleFileVisitor
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // You need to traverse through a given directory. After visiting the directory, you want to print the number of
        // files visited in that directory.
        // Which three methods of FileVisitor will be helpful in implementing this requirement? You had to select 3 options
        //
        // 1º postVisitDirectory
        //
        // 2º visitFile
        //
        // 3º preVisitDirectory
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Which of the following statements is true about DSYNC constant defined in StandardOpenOption enum?
        //
        // DSYNC keeps only the file content and not the file meta data synchronized with the underlying storage device.
        //
        // D in DSYNC is for data. When you open a file with this attribute, it means that every update to the file's content will be written
        // synchronously to the underlying storage device.
        //
        // The meta data may still remain unsynchronized. In other words, any change in the data of the file will be written to the storage
        // device synchronously, but any change in the meta data may be batched and written to the storage device later.
        // Thus, it makes file operations a slower as compared to when you open the file without any option.
        //
        // SYNC makes sure that both - the data and meta data are synchronized with the storage device. Thus, it makes files operations even
        // slower than DSYNC option.
        //
        // Explanation:
        // The following is a list of constants provided by java.nio.file.StandardOpenOption:
        //
        // APPEND - If the file is opened for WRITE access then bytes will be written to the end of the file rather than the beginning.
        //
        // CREATE - Create a new file if it does not exist.
        //
        // CREATE_NEW - Create a new file, failing if the file already exists.
        //
        // DELETE_ON_CLOSE - Delete on close.
        //
        // DSYNC - Requires that every update to the file's content be written synchronously to the underlying storage device.
        //
        // READ - Open for read access.
        //
        // SPARSE - Sparse file.
        //
        // SYNC - Requires that every update to the file's content or metadata be written synchronously to the underlying storage device.
        //
        // TRUNCATE_EXISTING - If the file already exists and it is opened for WRITE access, then its length is truncated to 0.
        //
        // WRITE - Open for write access.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // You have a file named customers.dat in c:\company\records directory. You want to copy all the lines in this file to another file named
        // clients.dat in the same directory and you have the following code to do it:

        Path p1 = Paths.get("c:\\company\\records\\customers.dat");

        Path p2 = null; // LINE 20 - INSERT CODE HERE

        try (BufferedReader br = new BufferedReader(new FileReader(p1.toFile())); // 
                BufferedWriter bw = new BufferedWriter(new FileWriter(p2.toFile()))) {

            String line = null;

            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Which of the following options can be inserted independent of each other at //LINE 20 to make it work? Assume that the current directory
        // for the program when it runs is c:\code. You had to select two options.

        // 1º Option
        p2 = p1.resolveSibling("clients.dat");

        // You already have the absolute path to customers.dat in p1. Further, it is given that you want to copy the data to a file in the same
        // directory i.e. both the files - old and new, are siblings.
        // So, to open clients.dat, you need to determine the absolute path for clients.dat using the absolute path for customers.dat.
        // In other words, you are trying to get the absolute path for a file that exists in the same directory as the original file.
        // The method resolveSibling is meant exactly for this purpose.
        //

        // 2º Option
        p2 = Paths.get("c:", p1.subpath(0, 2).toString(), "clients.dat");
        // This is very straight forward. You should go through the JavaDoc API description for Path.subpath and Paths.get methods.

    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
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
    static void test01_08() throws Exception {

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
    static void test01_09() throws Exception {
        // Consider the following code:
        Path p1 = Paths.get("\\temp\\records");
        Path p2 = p1.resolve("clients.dat");
        System.out.println(p2 + " " + isValid(p2));

        // What will be printed when the method writeData() is executed?
        //
        // \temp\records\clients.dat false

        // Explanation
        // p2 will be set to \temp\records\clients.dat. Since it starts with \temp and not with temp, the method isValid will return false.
    }

    public static boolean isValid(Path p) {
        return p.startsWith("temp") && p.endsWith("clients.dat");
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
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
        test01_01();
    }
}
