package br.fernando.ch05_IO_and_NIO_Objective.p02_files_path_paths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Test03 {

    // =========================================================================================================================================
    // Copying, Moving, and Deleting Files
    static void test01() throws IOException {
        Path source = Paths.get("/tem/test1.txt"); // exists
        Path target = Paths.get("/temp/test2.txt"); // doesn't exists

        Files.copy(source, target);// now two copies of the file

        Files.delete(target); // back to one copy, if file doesn't exists, will throw a NoSuchFileException

        // There is an alternative:
        Files.deleteIfExists(source);

        Files.move(source, target); // still one copy

        // This is all pretty self-explanatory. We copy a file, delete the copy, and then move the file.

        Path one = Paths.get("/temp/test1.txt"); // exists

        Path two = Paths.get("/temp/test2.txt"); // exists too

        Path targ = Paths.get("/temp/test23.txt"); // doesn't yet exist

        Files.copy(one, targ); // now two copies of the file

        Files.copy(two, targ); // oops, FileAlreadyExistsException, targ already exists now!

        // Java sees it is about to overwrite a file that already exists. Java doesn’t want us to lose
        // the file, so it “asks” if we are sure by throwing an exception. copy() and move()
        // actually take an optional third parameter—zero or more CopyOptions.

        Files.copy(two, targ, StandardCopyOption.REPLACE_EXISTING); // ok, You know what you are doing
    }

    // =========================================================================================================================================
    // Retrieving Information about a Path
    static void test02() {
        // In the following code listing, a Path is created referring
        // to a directory and then we output information about the Path instance:
        Path path = Paths.get(System.getProperty("user.home"));

        // Returns the file name or the last element of the sequence of name elements.
        System.out.println("getFileName: " + path.getFileName());

        // returns the path element correponding to the specified index. The 0th elemnt is the one closest to the root
        // ex: On windows, the root is usually C:\ and Unix, the root is /.
        System.out.println("getName(1): " + path.getName(1));

        // Returns the number of elements in this path, excluing the root.
        System.out.println("getNameCount: " + path.getNameCount());

        // Return the parent path, or null if this path does not have a parent.
        System.out.println("getParent: " + path.getParent());

        // Returns the root's path, or null if this path doesn't have a root.
        System.out.println("getRoot: " + path.getRoot());

        // Returns a subsequence of this path (not including a root element) as specified by the beginning (included)
        // and ending (not included) indexes.
        System.out.println("subpath(0, 2): " + path.subpath(0, 2));

        // Returns the string representation of this path.
        System.out.println("toString: " + path.toString());

        // getFileName: fernando.romulo
        // getName(1): fernando.romulo
        // getNameCount: 2
        // getParent: C:\Users
        // getRoot: C:\
        // subpath(0, 2): Users\fernando.romulo
        // toString: C:\Users\fernando.romulo

        // Here is yet another interesting fact about the Path interface: It extends from Iterable<Path> .
        int spaces = 1;

        System.out.println("");

        for (Path p : path) {
            System.out.format("%" + spaces + "s%s%n", "", p);
            spaces += 2;
        }
    }

    // =========================================================================================================================================
    static void summary() throws Exception {
        // *******************************************************************************
        // Copying, Moving, and Deleting Files
        // *******************************************************************************
        Path source = Paths.get("/tem/test1.txt"); // exists
        Path target = Paths.get("/temp/test2.txt"); // doesn't exists

        Files.copy(source, target);// now two copies of the file, it can throw a FileAlreadyExistsException

        // Java sees it is about to overwrite a file that already exists. Java doesn’t want us to lose
        // the file, so it “asks” if we are sure by throwing an exception. copy() and move()
        // actually take an optional third parameter—zero or more CopyOptions.

        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING); // ok, You know what you are doing

        Files.delete(target); // back to one copy, if file doesn't exists, will throw a NoSuchFileException

        // There is an alternative:
        Files.deleteIfExists(source);

        Files.move(source, target); // still one copy

        // *******************************************************************************
        // Retrieving Information about a Path
        // *******************************************************************************

        // In the following code listing, a Path is created referring
        // to a directory and then we output information about the Path instance:
        Path path = Paths.get(System.getProperty("user.home")); //

        // Returns the file name or the last element of the sequence of name elements.
        System.out.println("getFileName: " + path.getFileName()); // getFileName: C:\Users\fernando.romulo

        // returns the path element correponding to the specified index. The 0th elemnt is the one closest to the root
        // ex: On windows, the root is usually C:\ and Unix, the root is /.
        System.out.println("getName(1): " + path.getName(1)); // getName(1): fernando.romulo

        // Returns the number of elements in this path, excluidng the root.
        System.out.println("getNameCount: " + path.getNameCount()); // getNameCount: 2

        // Return the parent path, or null if this path does not have a parent.
        System.out.println("getParent: " + path.getParent()); // getParent: C:\Users

        // Returns the root's path, or null if this path doesn't have a root.
        System.out.println("getRoot: " + path.getRoot()); // getRoot: C:\

        // Returns a subsequence of this path (not including a root element) as specified by the beginning (included)
        // and ending (not included) indexes.
        System.out.println("subpath(0, 2): " + path.subpath(0, 2)); // subpath(0, 2): Users\fernando.romulo

        // Returns the string representation of this path.
        System.out.println("toString: " + path.toString()); // toString: C:\Users\fernando.romulo
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        Path path = Paths.get(System.getProperty("user.home")); //

        // Returns the file name or the last element of the sequence of name elements.
        System.out.println("getFileName: " + path.getFileName()); // getFileName: C:\Users\fernando.romulo

        // returns the path element correponding to the specified index. The 0th elemnt is the one closest to the root
        // ex: On windows, the root is usually C:\ and Unix, the root is /.
        System.out.println("getName(1): " + path.getName(1)); // getName(1): fernando.romulo

        // Returns the number of elements in this path, excluidng the root.
        System.out.println("getNameCount: " + path.getNameCount()); // getNameCount: 2

        // Return the parent path, or null if this path does not have a parent.
        System.out.println("getParent: " + path.getParent()); // getParent: C:\Users

        // Returns the root's path, or null if this path doesn't have a root.
        System.out.println("getRoot: " + path.getRoot()); // getRoot: C:\

        // Returns a subsequence of this path (not including a root element) as specified by the beginning (included)
        // and ending (not included) indexes.
        System.out.println("subpath(0, 2): " + path.subpath(0, 2)); // subpath(0, 2): Users\fernando.romulo

        // Returns the string representation of this path.
        System.out.println("toString: " + path.toString()); // toString: C:\Users\fernando.romulo	
    }

}
