package br.fernando.ch05_IO_and_NIO_Objective.p03_file_and_directory_attributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Test01 {

    // =========================================================================================================================================
    // Reading and Writing Attributes the Easy Way
    // Use Files class to check, read, delete, copy, move, manage metadata of a file or directory.
    static void test01() throws IOException {

        // The following example creates a file, changes the last modified date,
        // prints it out, and deletes the file using both the old and new method names.

        // create a date
        final ZonedDateTime janFirstDateTime = ZonedDateTime.of( //
                LocalDate.of(2017, 1, 1), //
                LocalTime.of(10, 0), //
                ZoneId.of("US/Pacific"));

        Instant januaryFirst = janFirstDateTime.toInstant();

        // old way
        File file = new File("c:/temp/file");
        file.createNewFile(); // create the file

        file.setLastModified(januaryFirst.getEpochSecond() * 1000); // set time

        System.out.println(file.lastModified()); // get time
        file.delete(); // delete the file

        // new way
        Path path = Paths.get("c:/temp/file2");
        Files.createFile(path); // create another file

        FileTime fileTime = FileTime.fromMillis(januaryFirst.getEpochSecond()); // convert to the new FileTime Object
        Files.setLastModifiedTime(path, fileTime); // set the time

        System.out.println(Files.getLastModifiedTime(path)); // get the time
        Files.delete(path); // delete the file

        // The other common type of attribute you can set are file permissions. Both Windows
        // and UNIX have the concept of three types of permissions. Here’s what they mean:
        //
        // * Read : You can open the file or list what is in that directory.
        // * Write : You can make a change to the file or add a file to that directory.
        // * Execute : You can run the file if it is a runnable program or go into that directory.
        //
        // Printing out the file permissions is easy. Note that these permissions are just for the
        // user who is running the program—you! There are other types of permissions as well,
        // but these can’t be set in one line.
        //
        // Old way
        System.out.println(file.canExecute());
        // New way
        System.out.println(Files.isExecutable(path));

        // Old way
        System.out.println(file.canRead());
        // New way
        System.out.println(Files.isReadable(path));

        // Old way
        System.out.println(file.canWrite());
        // New way
        System.out.println(Files.isWritable(path));
    }

    // =========================================================================================================================================
    static void summary() throws Exception {

        final ZonedDateTime janFirstDateTime = ZonedDateTime.of( //
                LocalDate.of(2017, 1, 1), //
                LocalTime.of(10, 0), //
                ZoneId.of("US/Pacific"));

        Instant januaryFirst = janFirstDateTime.toInstant();

        // *******************************************************************************
        // Reading and Writing Attributes the Easy Way
        // *******************************************************************************
        // old way ------------------------------------------
        File file = new File("c:/temp/file");
        file.createNewFile(); // create the file

        file.setLastModified(januaryFirst.getEpochSecond() * 1000); // set time
        System.out.println(file.lastModified());

        file.setExecutable(true);
        System.out.println(file.canExecute());

        file.setReadable(true);
        System.out.println(file.canRead());

        file.setWritable(true);
        System.out.println(file.canWrite());

        // New Way ------------------------------------------
        Path path = Paths.get("c:/temp/file2");
        Files.createFile(path); // create another file

        FileTime fileTime = FileTime.fromMillis(januaryFirst.getEpochSecond()); // convert to the new FileTime Object

        Files.setLastModifiedTime(path, fileTime); // set the time
        System.out.println(Files.getLastModifiedTime(path)); // get the time

        System.out.println(Files.isExecutable(path));

        System.out.println(Files.isReadable(path));

        System.out.println(Files.isWritable(path));

        // ---------------------------------------------------
        Files.delete(path); // delete the file
        file.delete(); // delete the file
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws IOException {
        test01();
    }
}
