package org.nandao.cap04.p10ManagingSymbolicLinks;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Symbolic links are files, which are not real files, but rather links to or points to the real
// file
// typically called the target file. These are useful when it is desirable to have a file appearing
// to
// be in more than one directory without actually having to duplicate the file. This saves space
// and keeps all of the updates isolated to a single file.
//
// The Files class possesses the following three methods for working with symbolic links:
//
// 1 The createSymbolicLink method, which creates a symbolic link to a target file that may not
// exist
// 2 The createLink method creates a hard link to an existing file
// 3 The readSymbolicLink retrieves a Path to the target file
//
// Links are typically transparent to the users of the file. Any access to the symbolic link is
// redirected to the referenced file. Hard links are similar to symbolic links, but have more
// restrictions.
public class Test {

    public static void main(String[] args) throws Exception {
        test3();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        Path targetFile = Paths.get(pathSource + "/Docs/users.txt");
        Path linkFile = Paths.get(pathSource + "/Music/users.txt");

        Files.createSymbolicLink(linkFile, targetFile);
    }

    // Creating a hard link
    // Hard links behave like a regular file. There are no overt properties of the file that indicate that
    // it is a link file, as opposed to a symbolic link file which has a shortcut tab. All of the attributes
    // of the hard link are identical to that of the target file.    
    public static void test1() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        Path targetFile = Paths.get(pathSource + "/Docs/users.txt");
        Path linkFile = Paths.get(pathSource + "/Music/users.txt");

        Files.createLink(linkFile, targetFile);
    }

    // Creating a symbolic link to a directory
    // Creating a symbolic link to a directory uses the same methods as it did for files. In the following
    // example, a new directory tmp is created, which is a symbolic link to the docs directory: 
    public static void test2() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        Path targetFile = Paths.get(pathSource + "/Docs");
        Path linkFile = Paths.get(pathSource + "/tmp");
        
        Files.createSymbolicLink(linkFile, targetFile);

        Files.createLink(linkFile, targetFile);
    }
    
    // Determining the target of a link file
    // The isSymbolicLink method, as discussed in the Managing symbolic links recipe in
    // Chapter 2, Locating Files and Directories Using Paths determines whether a file is a symbolic
    // link or not. The readSymbolicLink method accepts a Path object representing the link file
    // and returns a Path object representing the target of the link.
    public static void test3() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        Path linkFile = Paths.get(pathSource + "/Music/users.txt");
        
        System.out.println("Target file is: " + Files.readSymbolicLink(linkFile));
        
        // However, if the users.txt link file is a hard link, as created with the createLink method,
        // we get the following exception when the code is executed:
        // java.nio.file.NotLinkException: The file or directory is not a reparse point.
    }
    

}
