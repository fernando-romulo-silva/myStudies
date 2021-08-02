package org.nandao.cap02.p5CombiningPathsUsingPathResolution;

import java.nio.file.Path;
import java.nio.file.Paths;

// The resolve method is used to combine two paths, where one contains a root element and
// the other is a partial path. This is useful when creating paths that can vary, such as those
// used in the installation of an application. For example, there may be a default directory where
// an application is installed. However, the user may be able to select a different directory or
// drive. Using the resolve method to create a path allows the application to be configured
// independent of the actual installation directory.
//
// Improper use of the resolve method
// There are three uses of the resolve method that can result in unexpected behavior:
// 1 Incorrect order of the root and partial paths
// 2 Using a partial path twice
// 3 Using the root path twice

public class Test {

    public static void main(String[] args) {
        test2();
    }

    public static void test0() {

        final Path rootPath = Paths.get("/home/docs");
        final Path partialPath = Paths.get("users.txt");

        // The resolved path was created by using the partialPath variable as an argument to the
        // resolve method executed against the rootPath variable. 
        final Path resolvedPath = rootPath.resolve(partialPath);

        System.out.println("rootPath: " + rootPath);
        System.out.println("partialPath: " + partialPath);
        System.out.println("resolvedPath: " + resolvedPath);
        System.out.println("Resolved absolute path: " + resolvedPath.toAbsolutePath());
    }

    public static void test1() {
        final Path rootPath = Paths.get("/home/docs");
        final Path partialPath = Paths.get("users.txt");

        // The resolve methods are overloaded, one using a String argument and the second using
        // a Path argument. The resolve method can also be misused. In addition, there is also an
        // overloadedresolveSibling method that works similar to the resolve method except
        // it removes the last element of the root path. These issues are addressed here.
        final Path resolvedPath = rootPath.resolve("users.txt");

        System.out.println("rootPath: " + rootPath);
        System.out.println("partialPath: " + partialPath);
        System.out.println("resolvedPath: " + resolvedPath);
        System.out.println("Resolved absolute path: " + resolvedPath.toAbsolutePath());
    }

    public static void test2() {
        // The resolveSibling method is overloaded taking either a String or a Path object.
        // With the resolve method, the partial path is appended to the end of the root path. The
        // resolveSibling method differs from the resolve method in that the last element
        // of the root path is removed before the partial path is appended. Consider the following
        // code sequence:

        Path rootPath = Paths.get("/home/music/");
        Path resolvedPath = rootPath.resolve("tmp/Robot Brain A.mp3");
        System.out.println("rootPath: " + rootPath);
        System.out.println("resolvedPath: " + resolvedPath);
        
        System.out.println();
        resolvedPath = rootPath.resolveSibling("tmp/Robot Brain A.mp3");
        System.out.println("rootPath: " + rootPath);
        System.out.println("resolvedPath: " + resolvedPath);
    }
}
