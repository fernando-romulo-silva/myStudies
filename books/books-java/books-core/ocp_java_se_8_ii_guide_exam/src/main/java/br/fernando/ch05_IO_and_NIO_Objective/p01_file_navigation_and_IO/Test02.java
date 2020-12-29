package br.fernando.ch05_IO_and_NIO_Objective.p01_file_navigation_and_IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Test02 {

    // =========================================================================================================================================
    // Using FileInputStream and FileOutputStream
    static void test01() {
        // Using FileInputStream and FileOutputStream is similar to using FileReader and
        // FileWriter , except you’re working with byte data instead of character data. That
        // means you can use FileInputStream and FileOutputStream to read and write binary
        // data as well as text data.

        byte[] in = new byte[50]; // bytes, not chars!

        int size = 0;

        File file = new File("FileWriter3");

        try {

            FileOutputStream fos = new FileOutputStream(file); // create a FileOutputStream

            fos.write("howdy\nfolks\n".getBytes("UTF-8")); // writer characters (bytes) to file

            fos.flush(); // flush before closing

            fos.close(); // close file when done

            //
            // Reading ...

            FileInputStream fis = new FileInputStream(file); // create a FileInputStream

            size = fis.read(in); // read the file into in

            System.out.println(size + " "); // how many bytes read

            for (byte c : in) { // print array
                System.out.println((char) c);
            }

            fis.close(); // again, close

        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    // =========================================================================================================================================
    // Combining I/O Classes
    static void test02() {
        // Java’s entire I/O system was designed around the idea of using several classes in
        // combination. Combining I/O classes is sometimes called wrapping and sometimes called chaining.
        //
        // Obs* Whenever you create an instance of one of these classes, you automatically create a file,
        // unless one already exists, for instance
        //
        //
        // ---------------------
        // File
        //
        // (File, String)
        // (String)
        // (String, String)
        //
        // createNewFile()
        // delete()
        // exists()
        // isDirectory()
        // isFile()
        // list()
        // mkdir()
        // renameTo()
        //
        // -----------------------
        // FileWriter -> Writer
        //
        // File
        // String
        //
        // close
        // flush
        // write
        //
        // -----------------------
        // BufferedWriter -> Writer
        //
        // (Writer)
        //
        // close
        // flush
        // newLine
        // write
        //
        // -----------------------
        // PrintWriter -> Writer
        //
        // (File)
        // (String)
        // (OutpuStream)
        // (Writer)
        //
        // close
        // flush
        // format, printf
        // print, println
        // write
        //
        // -----------------------
        // FileOutputStream -> OutputStream
        //
        // (File)
        // (String)
        //
        // close
        // write
        //
        // -----------------------
        // FileReader -> Reader
        //
        // (File)
        // (String)
        //
        // read
        //
        // -----------------------
        // BufferedReader -> Reader
        //
        // (Read)
        //
        // read
        // readLine
        //
        // -----------------------
        // FileInputStream -> InputStream
        //
        // (File)
        // (String)
        //
        // read
        // close
    }

    // =========================================================================================================================================
    // Working with Files and Directories
    static void test03() {
        // Creating a directory is similar to creating a file. Again, we’ll use the convention of referring to an object of type File
        // that represents an actual directory as a Directory object, with a capital D (even though it’s of type File ).
        // We’ll call an actual directory on a computer a directory, with a small d. Phew! As with creating a file, creating a
        // directory is a two-step process; first we create a Directory ( File ) object; then we create an actual directory using
        // the following mkdir() method
        try {
            File myDir01 = new File("myDir01"); // create an object

            myDir01.mkdir(); // create an actual directory

            File myFile01 = new File(myDir01, "myFile.txt");
            myFile01.createNewFile();

            PrintWriter printWriter01 = new PrintWriter(myFile01);

            printWriter01.println("new stuff");
            printWriter01.flush();
            printWriter01.close();

            // Be careful when you’re creating new directories! As we’ve seen, constructing a
            // Writer or a Stream will often create a file for you automatically if one doesn’t exist,
            // but that’s not true for a directory.

            File myDir02 = new File("myDir02");
            // myDir02.mkdir(); // call to mkdir() omitted!
            File myFile02 = new File(myDir02, "myFile02");

            myFile02.createNewFile(); // exception if no mkdir!

            // --------------------
            // You can refer a File object to an existing file or directory

            File existingDir = new File("existingDid"); // assing a dir

            System.out.println(existingDir.isDirectory()); // must be true

            File existingDirFile = new File(existingDir, "existingDirFile.txt"); // assign a file
            System.out.println(existingDirFile.isFile());

            FileReader fr = new FileReader(existingDirFile);

            BufferedReader br = new BufferedReader(fr); // make a reader

            String s;

            while ((s = br.readLine()) != null) { // read data
                System.out.println(s);
            }
            // Take special note of what the readLine() method returns. When there is no more
            // data to read, readLine() returns a null —this is our signal to stop reading the file.
            //
            // When reading a file, no flushing is required, so you won’t even find a flush() method in a Reader kind of class.
            //
            // -------------------------
            // The following code demonstrates a few of the most common ins and outs of deleting files and directories (via delete() )
            // and renaming files and directories (via renameTo() ):

            File delDir = new File("delDir"); // make a directory
            delDir.mkdirs();

            File delFile01 = new File(delDir, "delFile01.txt"); // add file to directory
            delFile01.createNewFile();

            File delFile02 = new File(delDir, "delFile02.txt"); // add file to directory
            delFile02.createNewFile();

            delFile01.delete(); // delete a file
            System.out.println("delDir is " + delDir.delete()); // attempt to delete the directory

            File newName = new File(delDir, "newName.txt"); // a new object rename file

            delFile02.renameTo(newName);

            File newDir = new File("newDie"); // rename directory
            delDir.renameTo(newDir);

            // Here are some rules that we can deduce from this result:
            // * delete() : You can’t delete a directory if it’s not empty, which is why the
            // invocation delDir.delete() failed.
            //
            // * renameTo() : You must give the existing File object a valid new File object
            // with the new name that you want. (If newName had been null , we would have
            // gotten a NullPointerException .)
            //
            // * renameTo() : It’s okay to rename a directory, even if it isn’t empty.

        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    // ======================================================================================================================================================
    // Search for a File
    static void test04() {
        File search = new File("searchThis");

        String[] files = search.list(); // creat the list

        for (String file : files) {
            System.out.println(file);
        }
    }

    // ======================================================================================================================================================
    static void summary() {

        byte[] in = new byte[50]; // bytes, not chars!

        int size = 0;

        File file = new File("FileWriter3");

        // **********************************************************************************************************************************************
        // FileInputStream - Using FileInputStream and FileOutputStream is similar to using FileReader and FileWriter, except you’re working with byte 
        // data instead of character data.
        // **********************************************************************************************************************************************
        try {

            FileOutputStream fos = new FileOutputStream(file); // create a FileOutputStream

            fos.write("howdy\nfolks\n".getBytes("UTF-8")); // writer characters (bytes) to file

            fos.flush(); // flush before closing

            fos.close(); // close file when done

            //
            // Reading ...

            FileInputStream fis = new FileInputStream(file); // create a FileInputStream

            size = fis.read(in); // read the file into in

            System.out.println(size + " "); // how many bytes read

            for (byte c : in) { // print array
                System.out.println((char) c);
            }

            fis.close(); // again, close

        } catch (IOException e) {
            // TODO: handle exception
        }

        // **********************************************************************************************************************************************
        // Working with Files and Directories
        // **********************************************************************************************************************************************

        try {

            //---------- Create a new File ------------------------------------
            File myDir01 = new File("myDir01"); // create an object

            myDir01.mkdir(); // create an actual directory

            File myFile01 = new File(myDir01, "myFile.txt");
            myFile01.createNewFile();

            //---------- Create a new Dir ------------------------------------
            File delDir = new File("delDir"); // make a directory
            delDir.mkdirs();

            File delFile01 = new File(delDir, "delFile01.txt"); // add file to directory
            delFile01.createNewFile();

            File delFile02 = new File(delDir, "delFile02.txt"); // add file to directory
            delFile02.createNewFile();

            //---------- Delete a file --------------------------------------
            // You can’t delete a directory if it’s not empty, which is why the invocation delDir.delete() failed.
            delFile01.delete(); // delete a file
            System.out.println("delDir is " + delDir.delete()); // attempt to delete the directory

            //---------- Rename file ----------------------------------------
            // You must give the existing File object a valid new File object with the new name that you want. (If newName had been null , we would have
            // gotten a NullPointerException.
            File newName = new File(delDir, "newName.txt"); // a new object rename file

            // renameTo() : It’s okay to rename a directory, even if it isn’t empty.
            delFile02.renameTo(newName);

            File newDir = new File("newDie"); // rename directory
            delDir.renameTo(newDir);

        } catch (IOException ex) {

        }

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }

}
