package br.fernando.ch05_IO_and_NIO_Objective.p02_files_path_paths;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Test02 {

    // =========================================================================================================================================
    // Creating Files and Directories
    static void test01() throws IOException {

        Path path = Paths.get("fileWrite1.txt"); // it's only an object

        System.out.println(Files.exists(path)); // look for a real file

        Files.createFile(path); // create a file!

        System.out.println(Files.exists(path)); // look again

        // IO Approach x NIO2 Approach

        // ---------------------------------------------------------
        // Create an Empty file:
        File file01 = new File("test");
        file01.createNewFile();

        Path path01 = Paths.get("dir");
        Files.createFile(path01);

        // ---------------------------------------------------------
        // Create an empty directory
        File file02 = new File("/a/b/c");
        file02.mkdir();

        Path path02 = Paths.get("/a/b/c");
        Files.createDirectories(path02);

        // ---------------------------------------------------------
        // Create a directory, including any missing parent directories
        File file03 = new File("/a/b/c");
        file03.mkdirs();

        Path path03 = Paths.get("/a/b/c");
        Files.createDirectories(path03);

        // ---------------------------------------------------------
        // Check if a file or directory exists
        File file = new File("test");
        file.exists();

        Path path04 = Paths.get("test");
        Files.exists(path04);

        // The method Files.notExists() supplements Files.exists(). In some incredibly
        // rare situations, Java won’t have enough permissions to know whether the file
        // exists. When this happens, both methods return false.
    }

    static void test02() throws IOException {
        // Suppose we have a directory named /java and we want to create the file
        // /java/source/directory/Program.java . We could do this one at a time:

        Path path01 = Paths.get("/java/source");
        Path path02 = Paths.get("/java/source/directory");

        Path file = Paths.get("/java/source/directory/Program.java");

        Files.createDirectory(path01); // create first level of directory

        Files.createDirectory(path02); // create second level of directory

        Files.createDirectories(file);

        // Or we could create all the directories in one go:
        Files.createDirectories(path02); // create all levels of directories

        Files.createFile(file); // create file

        // And remember that the directory needs to exist by the time the file is created
    }

    // =========================================================================================================================================
    // Writing in
    @SuppressWarnings("unused")
    static void test03() throws IOException {
        Path path01 = Paths.get("/java/source");

        String content = "Test File Content";

        Files.write(path01, content.getBytes(), //
                StandardOpenOption.CREATE, //
                StandardOpenOption.TRUNCATE_EXISTING);

        //

        OpenOption[] options = new OpenOption[]{ StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW };

        Charset cs = Charset.defaultCharset();

        // no Options anyway
        BufferedReader newBufferedReader = Files.newBufferedReader(path01, cs);

        // default: CREATE, TRUNCATE_EXISTING, and WRITE not allowed: READ
        BufferedWriter newBufferedWriter = Files.newBufferedWriter(path01, cs, options);

        // default: READ not allowed: WRITE
        InputStream newInputStream = Files.newInputStream(path01, options);

        // default: CREATE, TRUNCATE_EXISTING, and WRITE not allowed: READ
        OutputStream newOutputStream = Files.newOutputStream(path01, options);

        // default: READ do whatever you want
        SeekableByteChannel newByteChannel = Files.newByteChannel(path01, options);
    }

    static void summary() throws Exception {
        // ***************************************************************************************************
        // Creating Files and Directories
        // ***************************************************************************************************
        Path path01 = Paths.get("fileTemp.txt"); // it's only an object

        System.out.println(Files.exists(path01)); // look for a real file

        Path path02 = Files.createFile(path01); // create a file!

        System.out.println(Files.exists(path02)); // look again

        // ***************************************************************************************************
        // IO Approach x NIO2 Approach
        // ***************************************************************************************************
        // ------------------------------------------------
        // Create an Empty file:
        File file03 = new File("test");
        file03.createNewFile();

        Path path03 = Paths.get("dir");
        Files.createFile(path03);

        // Create a directory, including any missing parent directories
        File file05 = new File("/a/b/c");
        file05.mkdirs();

        Path path05 = Paths.get("/a/b/c");
        Files.createDirectories(path05);

        // ------------------------------------------------
        // Check if a file or directory exists
        File file06 = new File("test");
        file06.exists();

        Path path06 = Paths.get("test");
        Files.exists(path06);

        Files.notExists(path06);

        // ***************************************************************************************************
        // NIO2 Create Level
        // ***************************************************************************************************

        Path path011 = Paths.get("/java/source");
        Path path012 = Paths.get("/java/source/directory");

        Files.createDirectory(path011); // create first level of directory

        Files.createDirectory(path012); // create second level of directory
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws IOException {
        test01();
    }
}
