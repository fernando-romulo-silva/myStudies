package br.fernando.ch05_IO_and_NIO_Objective.p04_DirectoryStream;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Test01 {

    // =========================================================================================================================================
    // DirectoryStream
    static void test01() throws IOException {
        // You might need to loop through a directory. Let’s say you were asked to
        // list out all the users with a home directory on this compute

        Path dir = Paths.get(System.getProperty("user.home")).getParent();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) { // user try-with-resorces, so we don't have to close

            for (Path path : stream) { // loop through the stream
                System.out.println(path.getFileName());
            }
        }

        System.out.println("------------------------------------------------------------------------------");

        // Let’s say we have hundreds of users and each day we want to only report on a few of them.
        // The first day, we only want the home directories of users whose names begin with either the letter "t" or the letter "f"

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "[tf]*")) { // user try-with-resorces, so we don't have to close

            for (Path path : stream) { // loop through the stream
                System.out.println(path.getFileName());
            }
        }

        // There is one limitation with DirectoryStream . It can only look at one directory.
        // [tf] means either of the characters "t" or "f". The "*" is a wildcard that means zero or more of any character.
    }

    // =========================================================================================================================================
    // FileVisitor
    static void test02_01() throws IOException {

        RemoveClassFiles dirs = new RemoveClassFiles();

        Files.walkFileTree( // kick off recursive check
                Paths.get("/home/src") // starting point
                , dirs); // the visitor

    }

    // need to extend visitor called "automatically"
    static class RemoveClassFiles extends SimpleFileVisitor<Path> {

        // Invoked for a file in a directory.
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

            if (file.getFileName().toString().endsWith(".class")) {
                Files.delete(file); // delete the file
            }

            return FileVisitResult.CONTINUE; // go on to next file
        }

        // Invoked for a file that could not be visited.
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return super.visitFileFailed(file, exc);
        }

        // Invoked for a directory before entries in the directory are visited.
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return super.preVisitDirectory(dir, attrs);
        }

        // Invoked for a directory after entries in the directory, and all of their descendants, have been visited.
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return super.postVisitDirectory(dir, exc);
        }
    }

    static void test02_02() throws IOException {

        PrintDirs dirs = new PrintDirs();

        Files.walkFileTree(Paths.get(System.getProperty("user.home")), dirs);

        // Note that Java goes down as deep as it can before returning back up the tree. This is
        // called a depth-first search. We said “might” because files and directories at the same
        // level can get visited in either order.

    }

    static class PrintDirs extends SimpleFileVisitor<Path> {

        // Called before drilling down into the directory
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.println("pre: " + dir);

            String name = dir.getFileName().toString();

            if (name.equals("child")) {
                return FileVisitResult.SKIP_SUBTREE;
            } else if (name.equals("min")) {
                return FileVisitResult.TERMINATE;
            } else if (name.equals("max")) {
                // SKIP_SIBLINGS is a combination of SKIP_SUBTREE and “don’t look in any folders at
                // the same level.” This means we skip everything under child and also skip emptyChild
                return FileVisitResult.SKIP_SIBLINGS;
            }

            return FileVisitResult.CONTINUE;
        }

        // Called once for each file (but not for directories)
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            System.out.println("file: " + file);
            return FileVisitResult.CONTINUE;
        }

        // Called only if there was an error acessing a file, usually a permissions issue
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        // Called when finished with the directory on the way back
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            System.out.println("post: " + dir);
            return FileVisitResult.CONTINUE;
        }
    }

    // =========================================================================================================================================
    static void summary() throws Exception {
        Path dir = Paths.get(System.getProperty("user.home")).getParent();

        // ***********************************************************************************
        // DirectoryStream - To through the directories
        // ***********************************************************************************
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) { // user try-with-resorces, so we don't have to close

            for (Path path : stream) { // loop through the stream
                System.out.println(path.getFileName());
            }
        }

        // Using "glob" - Only files starts with "t" and "f" 
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "[tf]*")) {

            for (Path path : stream) { // loop through the stream
                System.out.println(path.getFileName());
            }
        }

        // ***********************************************************************************
        // FileVisitor - 
        // ***********************************************************************************
        // SimpleFileVisitor<Path> - visitFile(Path file, BasicFileAttributes attrs) 
        RemoveClassFiles dirs = new RemoveClassFiles();

        Files.walkFileTree( // kick off recursive check
                dir // starting point
                , dirs); // the visitor

        // ***********************************************************************************
        // FileVisitResult - 
        // ***********************************************************************************
        FileVisitResult v1 = FileVisitResult.CONTINUE;

        FileVisitResult v2 = FileVisitResult.SKIP_SUBTREE;

        FileVisitResult v3 = FileVisitResult.TERMINATE;

        // SKIP_SIBLINGS is a combination of SKIP_SUBTREE and “don’t look in any folders at the same level.”
        FileVisitResult v4 = FileVisitResult.SKIP_SIBLINGS;
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws IOException {
        test02_02();
    }
}
