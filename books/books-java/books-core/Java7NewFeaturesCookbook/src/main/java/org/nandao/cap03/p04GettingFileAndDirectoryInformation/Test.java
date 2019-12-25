package org.nandao.cap03.p04GettingFileAndDirectoryInformation;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

// It is frequently necessary to retrieve basic information about a file or directory. This recipe
// examines how the java.nio.file.Files class provides the direct support. These methods
// provide only partial access to file and directory information and are typified by methods such
// as the isRegularFile method.
// page 88
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        
        final Path path = FileSystems.getDefault().getPath(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

        displayFileAttributes(path);
    }

    private static void displayFileAttributes(Path path) throws Exception {
        final String format = "Exists: %s %n" //
            + "notExists: %s %n" //
            + "Directory: %s %n" //
            + "Regular: %s %n" //
            + "Executable: %s %n" //
            + "Readable: %s %n" //
            + "Writable: %s %n" //
            + "Hidden: %s %n" //
            + "Symbolic: %s %n" //
            + "Last Modified Date: %s %n" //
            + "Size: %s %n"; //

        System.out.printf( //
                           format, //
                           Files.exists(path, LinkOption.NOFOLLOW_LINKS), //
                           Files.notExists(path, LinkOption.NOFOLLOW_LINKS), //
                           Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS), //
                           Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS), //
                           Files.isExecutable(path), //
                           Files.isReadable(path), //
                           Files.isWritable(path), //
                           Files.isHidden(path), //
                           Files.isSymbolicLink(path), //
                           Files.getLastModifiedTime(path, LinkOption.NOFOLLOW_LINKS), //
                           Files.size(path));
    }
}
