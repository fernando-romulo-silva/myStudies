package org.nandao.cap04.p01CreatingFilesAndDirectories;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// The process of creating new files and directories is greatly simplified in Java 7. The methods
// implemented by the Files class are relatively intuitive and easy to incorporate into your code.
// In this recipe, we will cover how to create new files and directories using the createFile and
// createDirectory methods.

public class Test {

    public static void main(String[] args) throws Exception {
        test1();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        final Path testDirectoryPath = Paths.get(path.toString() + "/Temp");

        try {
            final Path testDirectory = Files.createDirectory(testDirectoryPath);
            System.out.println("Directory created successfully!");

            final Path newFilePath = Paths.get(path + "/Temp/newFile.txt");
            final Path testFile = Files.createFile(newFilePath);

            System.out.println("File created successfully!");

        } catch (FileAlreadyExistsException a) {
            System.out.println("File or directory already exists!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // The createDirectories method is used to create a directory and potentially other
    // intermediate directories. In this example, we build upon the previous directory structure by
    // adding a subtest and a subsubtest directory to the test directory. Comment out the
    // previous code that created the directory and file and add the following code sequence:

    public static void test1() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        final Path testDirectoryPath = Paths.get(path.toString() + "/Temp");

        try {
            final Path testDirectory = Files.createDirectory(testDirectoryPath);
            System.out.println("Directory created successfully!");

            Path testSubDirectoryPath = Paths.get(testDirectory + "/subsubtest");

            final Path testDirectory2 = Files.createDirectories(testSubDirectoryPath);
            System.out.println("Subdirectory created successfully!");

        } catch (final FileAlreadyExistsException a) {
            System.out.println("File or directory already exists!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
