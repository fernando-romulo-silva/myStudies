package br.fernando.ch05_IO_and_NIO_Objective.p07_tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test04 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
        // Which of the following is true about the PathMatcher returned by FileSystem.getPathMatcher()?

        // It can match Paths using a regular expression or a glob pattern depending on how it is acquired.
        //
        // You can get a PathMatcher using either of the expressions. For example:

        FileSystems.getDefault().getPathMatcher("glob:*.java"); // Uses a glob pattern
        // or
        FileSystems.getDefault().getPathMatcher("regex:a.*b\\.txt"); // uses a regular expression pattern
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Which of the following are valid enum values defined in java.nio.file.FileVisitResult?
        //
        // SKIP_SIBLINGS;
        //
        // SKIP_SUBTREE;

        // Explanation
        // ava.nio.file.FileVisitResult defines the following four enum constants :

        // Continue
        FileVisitResult fvr01 = FileVisitResult.CONTINUE;

        // Continue without visiting the siblings of this file or directory.
        FileVisitResult fvr02 = FileVisitResult.SKIP_SIBLINGS;

        // Continue without visiting the entries in this directory.
        FileVisitResult fvr03 = FileVisitResult.SKIP_SUBTREE;

        // Terminate.
        FileVisitResult fvr04 = FileVisitResult.TERMINATE;
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
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
    static void test01_05() throws Exception {
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
    static void test01_06() throws Exception {
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
    static void test01_07() throws Exception {
        // Which of the following statements correctly creates a PathMatcher?

        PathMatcher pm = FileSystems.getDefault().getPathMatcher("glob:**.{htm,xml}");

        // FYI, the given glob pattern matches all the files with extension .htm or .xml. ** means, the pattern will match across directory boundaries.
        // For example, It will match index.htm as well as c:\temp\test\index.htm
        // But if you replace ** with *, it will match only index.htm.

        // Explanation:
        // java.nio.file.FileSystem provides an interface to a file system and is the factory for objects to access files and other
        // objects in the file system.
        // A file system is the factory for several types of objects:
        //
        // The getPath method converts a system dependent path string, returning a Path object that may be used to locate and access a file.
        FileSystems.getDefault().getPath(""); // like Paths.get(" ");
        //
        // The getPathMatcher method is used to create a PathMatcher that performs match operations on paths.
        FileSystems.getDefault().getPathMatcher("glob:*.java");
        //
        // The getFileStores method returns an iterator over the underlying file-stores.
        FileSystems.getDefault().getFileStores();
        //
        // The getUserPrincipalLookupService method returns the UserPrincipalLookupService to lookup users or groups by name.
        FileSystems.getDefault().getUserPrincipalLookupService();
        //
        // The newWatchService method creates a WatchService that may be used to watch objects for changes and events.
        FileSystems.getDefault().newWatchService();
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // What will the following code fragment print?

        Path p1 = Paths.get("\\personal\\readme.txt");
        Path p2 = Paths.get("\\index.html");
        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        // ..\..\index.html
        //
        // Observe that if you append this path to p1, you will get p2. Therefore, this is the right answer.     
        // =>p1 + ..\..\index.html
        // =>\personal\readme.txt + ..\..\index.html
        // =>\personal + ..\index.html
        // =>\index.html
        //
        // A ".." implies parent folder, therefore imagine that you are taking off one ".." from the right side of the plus sign and removing
        // the last name of the path on the left side of the plus sign.
        // For example, .. appended to personal makes it personal\.., which cancels out.
        //
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
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
    static void test01_10() throws Exception {
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
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
