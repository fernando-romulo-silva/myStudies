package org.nandao.cap04.p02ControllingHowFileIsCopied;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

// The process of copying files is also simplified in Java 7, and allows for control over the manner
// in which they are copied. The Files class' copy method supports this operation and is
// overloaded providing three techniques for copying which differ by their source or destination.
public class Test {

    public static void main(String[] args) throws Exception {
        test5();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home/Docs").getFile()).getAbsolutePath());

        final Path newFile = FileSystems.getDefault().getPath(path + "/newFile.txt");
        final Path copiedFile = FileSystems.getDefault().getPath(path + "/copiedFile.txt");

        try {

            Files.createFile(newFile);
            System.out.println("File created successfully!");
            Files.copy(newFile, copiedFile);
            System.out.println("File copied successfully!");

        } catch (IOException e) {
            System.out.println("IO Exception.");
        }
    }

    public static void test1() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home/Docs").getFile()).getAbsolutePath());

        final Path newFile = FileSystems.getDefault().getPath(path + "/newFile.txt");
        final Path copiedFile = FileSystems.getDefault().getPath(path + "/copiedFile.txt");

        try {

            Files.createFile(newFile);
            System.out.println("File created successfully!");

            // The three enumeration values for StandardCopyOption are listed in the following table:
            //    
            //  ATOMIC_MOVE - Perform the copy operation atomically
            //  COPY_ATTRIBUTES - Copy the source file attributes to the destination file
            //  REPLACE_EXISTING - Replace the existing file if it already exists            

            Files.copy(newFile, copiedFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully!");

        } catch (IOException e) {
            System.out.println("IO Exception.");
        }
    }

    // Copying a symbolic link file
    public static void test2() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        Path originalLinkedFile = FileSystems.getDefault().getPath(path + "/Music/users.txt");
        Path newLinkedFile = FileSystems.getDefault().getPath(path + "/Music/users2.txt");
        try {
            Files.copy(originalLinkedFile, newLinkedFile);
            System.out.println("Symbolic link file copied successfully!");
        } catch (IOException e) {
            System.out.println("IO Exception.");
        }
    }

    // Copying a symbolic link file
    // When a directory is copied, an empty directory is created. 
    // The files in the original directory are not copied.
    public static void test3() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        Path originalDirectory = FileSystems.getDefault().getPath(path + "/Docs");
        Path newDirectory = FileSystems.getDefault().getPath(path + "/tmp");

        try {
            Files.copy(originalDirectory, newDirectory);
            System.out.println("Directory copied successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // The copy method has a convenient overloaded version that permits the creation of a new file
    // based on the input from an InputStream. The first argument of this method differs from the
    // original copy method, in that it is an instance of an InputStream.
    public static void test4() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        Path newFile = FileSystems.getDefault().getPath(path + "/Docs/java7WebSite.html");
        URI url = URI.create("http://jdk.java.net/");

        try (InputStream inputStream = url.toURL().openStream()) {
            Files.copy(inputStream, newFile);
            System.out.println("Site copied successfully!");
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // The third overloaded version of the copy method will open a file and write its contents to an
    // OutputStream. This can be useful when the content of a file needs to be copied to a non-file
    // object such as a PipedOutputStream. It can also be useful when communicating to other
    // threads or writing to an array of bytes as illustrated here. In this example, the content of the
    // users.txt file is copied to an instance of a ByteArrayOutputStream. Its toByteArray
    // method is then used to populate an array as follows:
    public static void test5() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        Path sourceFile = FileSystems.getDefault().getPath(path + "/Docs/users.txt");

        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Files.copy(sourceFile, outputStream);
            byte arr[] = outputStream.toByteArray();
            
            System.out.println("The contents of " + sourceFile.getFileName());
            for (byte data : arr) {
                System.out.print((char) data);
            }
            
            System.out.println();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
