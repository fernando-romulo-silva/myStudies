package org.nandao.cap02.p1CreatingPathObject;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

// A Path object is used extensively by classes in the java.nio packageand is composed of several parts that are as follows:
// 1 A root which is the base of the path, such as a C drive
// 2 A separator used to separate the names that make up directories and files ofthe path
// 4 The names of the intermediate directories
// 5 A terminal element, which can be a file or directory
//
// These are discussed and illustrated in the Understanding paths recipe. The following are the
// classes dealing with files and directories:
// 1 java.nio. file.Paths contains static methods for the creation of a Path object
// 2 java.nio. file.Path interface contains numerous methods for working with paths
// 3 java.nio. file.FileSystems is the primary class used to access a filesystem
// 4 java.nio. file.FileSystem represents a filesystem, such as the /on a UNIX system or the C drive on a Windows platform
// 5 java.nio. file.FileStore represents the actual storage device and provides device-specific information
// 6 java.nio. file.attribute.FileStoreAttributeView provides access to file information
public class Test {

    public static void main(String[] args) {
        test01();
    }

    public static void test01() {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Home/Docs/status.txt").getFile());

        // final Path path = FileSystems.getDefault().getPath(file.getPath());

        // or

        Path path = null;
        try {
            path = Paths.get("/home", "Docs", "users.txt");
            System.out.printf("Absolute path: %s", path.toAbsolutePath());
        } catch (final InvalidPathException ex) {
            System.out.printf("Bad path: [%s] at position %s", ex.getInput(), ex.getIndex());
        }

        System.out.println();
        System.out.printf("toString: %s\n", path.toString());
        System.out.printf("getFileName: %s\n", path.getFileName());
        System.out.printf("getRoot: %s\n", path.getRoot());
        System.out.printf("getNameCount: %d\n", path.getNameCount());

        for (int index = 0; index < path.getNameCount(); index++) {
            System.out.printf("getName(%d): %s\n", index, path.getName(index));
        }

        final Iterator<Path> iterator = path.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.printf("subpath(0,2): %s\n", path.subpath(0, 2));
        System.out.printf("getParent: %s\n", path.getParent());
        System.out.println(path.isAbsolute());
    }

    // The conversion of the input arguments to a path is system-dependent. If the characters
    // used in the creation of the path are invalid for the filesystem, then a java.nio.file.
    // InvalidPathException is thrown. For example, in most filesystems a null value is an
    // illegal character. To illustrate this, add a back slash 0 to the path string
    public static void test02() {
        final Path path = Paths.get("/home\0", "docs", "users.txt");
    }
}
