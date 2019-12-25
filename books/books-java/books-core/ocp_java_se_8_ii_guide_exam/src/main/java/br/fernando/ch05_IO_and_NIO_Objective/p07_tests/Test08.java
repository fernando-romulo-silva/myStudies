package br.fernando.ch05_IO_and_NIO_Objective.p07_tests;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test08 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
        // Which of the following is a valid event type for StandardWatchEventKinds?

        // ENTRY_CREATE

        // Explanation
        // When you get WatchEvents from a WatchKey, you can get the event type for each WatchEvent using its kind() method.
        // The return type of this method is WatchEvent.Kind<T> and it returns the one of the following constants defined by class
        // java.nio.file.StandardWatchEventKinds:

        // static WatchEvent.Kind<Path> ENTRY_CREATE Directory entry created.

        // static WatchEvent.Kind<Path> ENTRY_DELETE Directory entry deleted.

        // static WatchEvent.Kind<Path> ENTRY_MODIFY Directory entry modified.

        // static WatchEvent.Kind<Object> OVERFLOW A special event to indicate that events may have been lost or discarded.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
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
    static void test01_04() throws Exception {
        // Which method do you need to implement if you want to implement your own java.nio.file.PathMatcher?

        // boolean matches(Path path)

        PathMatcher pm = new PathMatcher() {

            @Override
            public boolean matches(Path path) {
                return false;
            }
        };

        Path path = Paths.get("D:/cp/testFile.t");

        pm.matches(path);

        // it's an alternative to

        FileSystem fileSystem = FileSystems.getDefault();
        PathMatcher pathMatcher = fileSystem.getPathMatcher("glob:**.{java,class,txt}");
        pathMatcher.matches(path);
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What will the following code print?

        Path p1 = Paths.get("c:\\temp\\test.txt");
        Path p2 = Paths.get("c:\\temp\\report.pdf");

        System.out.println(p1.resolve(p2));

        // c:\temp\report.pdf

        // When the argument to resolve starts with the root (such as c: or, on *nix, a /), the result is same as the argument.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
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
    static void test01_07() throws Exception {
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
    static void test01_08() throws Exception {
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
    static void test01_09() throws Exception {
        // Consider the following code:

        Path p1 = Paths.get("\\temp\\records");
        Path p2 = p1.resolve("clients.dat");
        System.out.println(p2 + " " + isValid(p2));
        //
        // \temp\records\clients.dat false
        //
        // p2 will be set to \temp\records\clients.dat. Since it starts with \temp and not with temp, the method isValid will return false.
    }

    static boolean isValid(Path p) {
        return p.startsWith("temp") && p.endsWith("clients.dat");
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Consider the directory structure shown in the attached image.
        // (context root is a directory, which contains two files Home.htm, index.html and a directory web-inf, which in turn contains a file web.xml)
        // How will you create a PathMatcher that will match web.xml, Home.htm, and index.html?

        // context root (web-inf(web.xml), Home.htm, index.html)

        PathMatcher pm = FileSystems.getDefault().getPathMatcher("glob:**.{htm*,xml}");

        // **.{htm*, xml} recursive extension .{htm* or xml}
        //

        pm.matches(Paths.get(""));
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
