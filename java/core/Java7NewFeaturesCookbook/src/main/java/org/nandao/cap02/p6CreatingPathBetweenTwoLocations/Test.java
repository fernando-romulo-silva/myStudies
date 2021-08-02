package org.nandao.cap02.p6CreatingPathBetweenTwoLocations;

import java.nio.file.Path;
import java.nio.file.Paths;

// To relativize a path means to create a path based on two other paths such that the new path
// represents a way of navigating from one of the original paths to the other. This technique finds
// a relative path from one location to another. For example, the first path could represent an
// application default directory. The second path could represent a target directory. A relative
// path created from these directories could facilitate operations against the target.

public class Test {

    public static void main(String[] args) {
        test1();
    }

    public static void test0() {

        // The music and docs directories are assumed to be siblings. The .. notation
        // means to move up one directory. This chapter's introduction illustrated the assumed directory
        // structure for this example        

        Path firstPath = Paths.get("music/Future Setting A.mp3");
        Path secondPath = Paths.get("docs");

        System.out.println("From firstPath to secondPath: " + firstPath.relativize(secondPath));
        System.out.println("From secondPath to firstPath: " + secondPath.relativize(firstPath));
        System.out.println();

        firstPath = Paths.get("music/Future Setting A.mp3");
        secondPath = Paths.get("music");

        System.out.println("From firstPath to secondPath: " + firstPath.relativize(secondPath));
        System.out.println("From secondPath to firstPath: " + secondPath.relativize(firstPath));
        System.out.println();

        firstPath = Paths.get("music/Future Setting A.mp3");
        secondPath = Paths.get("docs/users.txt");

        System.out.println("From firstPath to secondPath: " + firstPath.relativize(secondPath));
        System.out.println("From secondPath to firstPath: " + secondPath.relativize(firstPath));
        System.out.println();

        firstPath = Paths.get("music/Future Setting A.mp3");
        // The relative path created by this method may not be a valid path. This is illustrated by using
        // the potentially non-existent tmp directory, shown as follows:
        secondPath = Paths.get("docs/tmp/users.txt");

        System.out.println("From firstPath to secondPath: " + firstPath.relativize(secondPath));
        System.out.println("From secondPath to firstPath: " + secondPath.relativize(firstPath));

        firstPath = Paths.get("c:/music/Future Setting A.mp3");
        secondPath = Paths.get("c:/docs/users.txt");
        
        System.out.println("From firstPath to secondPath: " + firstPath.relativize(secondPath));
        System.out.println("From secondPath to firstPath: " + secondPath.relativize(firstPath));
        System.out.println();
    }

    public static void test1() {
        Path firstPath = Paths.get("c:/music/Future Setting A.mp3");
        Path secondPath = Paths.get("docs/users.txt");

        System.out.println("From firstPath to secondPath: " + firstPath.relativize(secondPath));
        System.out.println("From secondPath to firstPath: " + secondPath.relativize(firstPath));
        System.out.println();
    }
}
