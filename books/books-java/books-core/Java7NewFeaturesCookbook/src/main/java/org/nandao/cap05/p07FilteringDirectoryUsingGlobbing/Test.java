package org.nandao.cap05.p07FilteringDirectoryUsingGlobbing;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

// A globbing pattern is similar to a regular expression but it is simpler. Like a regular expression
// it can be used to match specific character sequences. We can use globbing in conjunction
// with the newDirectoryStream method to filter the contents of a directory.

public class Test {

    public static void main(String[] args) throws Exception {
        test1();
    }

    public static void test0() throws Exception {
        Path directory = Paths.get(System.getProperty("java.home") + "/bin");

        try (final DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory, "java*.exe")) {

            for (Path file : directoryStream) {
                System.out.println(file.getFileName());
            }
        }
    }

    public static void test1() throws Exception {
        Path directory = Paths.get(System.getProperty("java.home") + "/bin");

        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:java?.exe");

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory, "java*.exe")) {

            for (Path file : directoryStream) {

                if (pathMatcher.matches(file.getFileName())) {
                    System.out.println(file.getFileName());
                }
            }

        } catch (IOException | DirectoryIteratorException ex) {
            ex.printStackTrace();
        }
    }

}
