package br.fernando.ch05_IO_and_NIO_Objective.p01_file_navigation_and_IO;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings({ "unused", "resource" })
class Test01 {

    // =========================================================================================================================================
    // Creating Files Using the File Class
    static void test02() {
        // Objects of type File are used to represent the actual files (but not the data in the files)
        // or directories that exist on a computer’s physical disk
        final File file01 = new File("fileWriter1.txt"); // There's no file yet!
        // When you make a new instance of the class File , you’re not yet making an actual file;
        // you’re just creating a filename.

        try {

            boolean newFile = false;

            final File file02 = new File("fileWrite01"); // it's only an object

            System.out.println(file02.exists()); // look for a real file

            newFile = file02.createNewFile(); // maybe create a file!

            System.out.println(newFile); // already there?

            System.out.println(file02.exists()); // look again

        } catch (IOException e) {
            // TODO: handle exception
        }
        // This produces the output
        // false
        // true
        // true
        //
        // And also produces an empty file in your current directory. If you run the code a second time, you get the output:
        // true
        // false
        // true
        //
        // Let’s examine these sets of output:
        //
        // First execution : The first call to exists() returned false , which we
        // expected… remember, new File() doesn’t create a file on the disk!
        // The createNewFile() method created an actual file and returned true , indicating
        // that a new file was created and that one didn’t already exist. Finally, we called
        // exists() again, and this time it returned true , indicating the file existed on the disk.
        //
        // Second execution : The first call to exists() returns true because we built the
        // file during the first run. Then the call to createNewFile() returns false since
        // the method didn’t create a file this time through. Of course, the last call to
        // exists() returns true
    }

    // =========================================================================================================================================
    // Using FileWriter and FileReader
    static void test03() {
        char[] in = new char[50];

        int size = 0;

        try {
            File file = new File("FileWriter2"); // just an object

            FileWriter fw = new FileWriter(file);// create an actual empty file & a FileWriter object

            fw.write("howdy\nfolks\n"); // write characters to the file

            fw.flush(); // flush before closing

            fw.close(); // close file when done

            //
            // Reading ...

            FileReader fr = new FileReader(file); // create a FileReader object

            size = fr.read(in); // read the whole file!

            System.out.println("size " + size + " "); // how many characters read

            for (char c : in) { // print the aray
                System.out.println(c);
            }

            fr.close(); // again, always close

            // When you write data out to a stream, some amount of buffering will occur, and you never know for
            // sure exactly when the last of the data will actually be sent. You might perform many
            // write operations on a stream before closing it, and invoking the flush() method
            // guarantees that the last of the data you thought you had already written actually gets
            // out to the file. Whenever you’re done using a file, either reading it or writing to it, you
            // should invoke the close() method. When you are doing file I/O, you’re using
            // expensive and limited operating system resources, and so when you’re done, invoking
            // close() will free up those resource
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    // =========================================================================================================================================
    // Using PrintWriter
    static void test04() {
        File file = new File("foo"); // no file yet

        try {
            // make a PrintWriter oject AND make a file, "foo" to which 'file' 
            // is assinged, AND assign 'pw' to the PrintWriter
            PrintWriter pw = new PrintWriter(file);

        } catch (IOException e) {
            // TODO: handle exception
        }

    }

    // =========================================================================================================================================
    static void summary() throws Exception {
        // ********************************************************************************************************************
        // File Class 
        // ********************************************************************************************************************

        final File file01 = new File("fileWriter1.txt"); // represents a file or directory

        boolean newFile = false;

        final File file02 = new File("fileWrite01"); // it's only an object

        System.out.println(file02.exists()); // look for a real file

        newFile = file02.createNewFile(); // maybe create a file!

        System.out.println(newFile); // already there?

        System.out.println(file02.exists()); // look again

        // ********************************************************************************************************************
        // FileWriter
        // ********************************************************************************************************************
        char[] in = new char[50];

        int size = 0;

        File file = new File("FileWriter2"); // just an object

        try {

            FileWriter fw = new FileWriter(file);// create an actual empty file & a FileWriter object

            fw.write("howdy\nfolks\n"); // write characters to the file

            fw.write("hello\n"); // write again?

            fw.flush(); // flush before closing

            fw.close(); //

        } catch (IOException e) {
        }

        // ********************************************************************************************************************
        // FileReader
        // ********************************************************************************************************************
        try {
            FileReader fr = new FileReader(file); // create a FileReader object

            size = fr.read(in); // read the whole file!

            System.out.println("size " + size + " "); // how many characters read

            for (char c : in) { // print the aray
                System.out.println(c);
            }

            fr.close(); // again, always close
        } catch (IOException e) {
        }

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test02();
    }
}
