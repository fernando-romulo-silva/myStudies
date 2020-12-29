package br.fernando.ch05_IO_and_NIO_Objective.p02_files_path_paths;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

// Files, Path, and Paths
@SuppressWarnings("unused")
class Test01 {

    // =========================================================================================================================================
    // Files, Path, and Paths (OCP Objectives 9.1 and 9.2
    static void test00() {
        // * Path : This interface replaces File as the representation of a file or a directory
        // when working in NIO.2. It is a lot more powerful than a File .
        //
        // * Paths : This class contains static methods that create Path objects.
        //
        // * Files : This class contains static methods that work with Path objects. You’ll
        // find basic operations in here like copying or deleting files.
        //
        // The interface java.nio.file.Path is one of the key classes of file-based I/O under
        // NIO.2. Just like the good old java.io.File , a Path represents only a location in the
        // file system, like C:\java\workspace\ocpjp7 (a Windows directory) or
        // /home/nblack/docs (the docs directory of user nblack on UNIX)
    }

    // =========================================================================================================================================
    // Creating a Path
    static void test01() throws Exception {
        // A Path object can be easily created by using the get methods from the Paths helper class.
        // Taking a look at two simple examples, we have:

        Path p1 = Paths.get("/tmp/file1.txt"); // on Unix

        Path p2 = Paths.get("c:\\temp\\test"); // on Windows

        // from resources 
        Paths.get(Test04.class.getResource("/META-INF/mp3/audio.mp3").toURI());

        // The actual method we just called is Paths.get(String first, String... more) .
        // This means we can write it out by separating the parts of the path.

        Path p3 = Paths.get("/tmp", "file1.txt"); // same as p1
        Path p4 = Paths.get("c:", "temp", "test"); // same as p2
        Path p5 = Paths.get("c:\\temp", "tet"); // same as p2

        // The previous examples are absolute paths since they begin with the root (/ on UNIX or c: on Windows).
        // When you don’t begin with the root, the Path is considered a relative path, which means Java looks
        // from the current directory.

        Path p6 = Paths.get("tmp", "file1.txt"); // relative path = NOT same as p1

        // -------------------------
        // The file:// is a protocol just like http:// is. This syntax allows you to browse to a folder in
        // Internet Explorer. Your program might have to deal with such a String that a user
        // copied/pasted from the browser. No problem, right? We learned to code:

        Path p7 = Paths.get("file:///c:/temp/test"); // this doesn't work, and you get an exception about the colon being

        // Paths provides another method that solves this problem. Paths.get(URI uri) lets
        // you (indirectly) convert the String to a URI (Uniform Resource Identifier) before
        // trying to create a Path:

        Path p8 = Paths.get(URI.create("file:///C:/temp"));

        // First, Java finds out what the default file system is. For example, it might be WindowsFileSystemProvider .
        // Then Java gets the path using custom logic for that file system.
    }

    // =========================================================================================================================================
    static void summary() throws Exception {
        // *******************************************************************************************
        // Path : Path replace File, as the representation of a file or a directory
        // *******************************************************************************************
        // -> Paths : This class contains static methods that create Path objects.

        Path p1 = Paths.get("/tmp/file1.txt"); // on Unix

        Path p2 = Paths.get("c:\\temp\\test"); // on Windows

        // Parts of path
        Path p3 = Paths.get("/tmp", "file1.txt"); // same as p1

        Path p4 = Paths.get("c:", "temp", "test"); // same as p2

        Path p5 = Paths.get("c:\\temp", "tet"); // same as p2

        // Relative path
        Path p6 = Paths.get("tmp", "file1.txt"); // relative path = NOT same as p1

        // File protocol
        Path p8 = Paths.get(URI.create("file:///C:/temp"));

        // from resources 
        Paths.get(Test01.class.getResource("/META-INF/mp3/audio.mp3").toURI());
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01();
    }
}
