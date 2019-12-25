package br.fernando.ch05_IO_and_NIO_Objective.p07_tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

public class Test02 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
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
    static void test01_03() throws Exception {
        // You want to walk through a directory structure recursively. Which interface do you need to implement for this purpose and which
        // class can you extend from to avoid implementing all the methods of the interface?

        // FileVisitor and SimpleFileVisitor
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {

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
    static void test01_05() throws Exception {

        // You have a file named customers.dat in c:\company\records directory. You want to copy all the lines in this file to another
        // file named clients.dat in the same directory and you have the following code to do it:

        Path p1 = Paths.get("c:\\company\\records\\customers.dat");

        Path p2 = null; // LINE 20 - INSERT CODE HERE

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

        // 1º
        p2 = p1.resolveSibling("clients.dat");

        // You already have the absolute path to customers.dat in p1. Further, it is given that you want to copy the data to a file in
        // the same directory i.e. both the files - old and new, are siblings.

        // 2º
        p2 = Paths.get("c:", p1.subpath(0, 2).toString(), "clients.dat");
        // This is very straight forward. You should go through the JavaDoc API description for Path.subpath and Paths.get methods.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Which of the following File Attribute views support manipulating the owner of a file?

        // PosixFileAttributeView

        // Unix based file systems provide this view. This view supports the following attributes in addition to BasicFileAttributeView:
        // "permissions" : Set<PosixFilePermission>
        // "group" : GroupPrincipal

        // The permissions attribute is used to update the permissions, owner, or group-owner as by invoking the setPermissions,
        // setOwner, and setGroup methods respectively.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Which of the following attributes are supported by BasicFileAttributeView?

        // isDirectory
        // size
        // creationTime

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

        // Attributes supported by DosFileAttributeView (which extends BasicFileAttributeView) are:
        // "readOnly"-----------------Boolean
        // "hidden"-------------------Boolean
        // "system"-------------------Boolean
        // "archive"------------------Boolean

    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Which of the following statements correctly creates a PathMatcher?

        PathMatcher pm = FileSystems.getDefault().getPathMatcher("glob:**.{htm,xml}");

        // FYI, the given glob pattern matches all the files with extension .htm or .xml. ** means, the pattern will match across
        // directory boundaries. For example, It will match index.htm as well as c:\temp\test\index.htm But if you replace ** with *,
        // it will match only index.htm.

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
    static void test01_09() throws Exception {
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
    static void test01_10() throws Exception {
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
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
