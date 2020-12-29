package br.fernando.ch05_IO_and_NIO_Objective.p01_file_navigation_and_IO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

@SuppressWarnings({ "unused", "resource" })
class Test00 {

    // =========================================================================================================================================
    // File Navigation and I/O (OCP Objectives 8.1 and 8.2)
    static void test01() {
        // Here’s a summary of the I/O classes you’ll need to understand for the exam:
        //
        // * File : The API says that the File class is “an abstract representation of file and
        // directory pathnames.” The File class isn’t used to actually read or write data;
        // it’s used to work at a higher level, making new empty files, searching for files,
        // deleting files, making directories, and working with paths
        //
        // * FileReader : This class is used to read character files. Its read() methods are
        // fairly low-level, allowing you to read single characters, the whole stream of
        // characters, or a fixed number of characters. FileReader s are usually wrapped
        // by higher-level objects such as BufferedReader s, which improve performance
        // and provide more convenient ways to work with the data.
        //
        // * BufferedReader : This class is used to make lower-level Reader classes like
        // FileReader more efficient and easier to use. Compared to FileReader s,
        // BufferedReader s read relatively large chunks of data from a file at once and
        // keep this data in a buffer. When you ask for the next character or line of data, it
        // is retrieved from the buffer, which minimizes the number of times that time-
        // intensive file-read operations are performed. In addition, BufferedReader
        // provides more convenient methods, such as readLine() , that allow you to get
        // the next line of characters from a file.
        //
        // * FileWriter : This class is used to write to character files. Its write() methods
        // allow you to write character(s) or strings to a file. FileWriter s are usually
        // wrapped by higher-level Writer objects, such as BufferedWriter s or
        // PrintWriter s, which provide better performance and higher-level, more
        // flexible methods to write data.
        //
        // * BufferedWriter : This class is used to make lower-level classes like
        // FileWriter s more efficient and easier to use. Compared to FileWriter s,
        // BufferedWriter s write relatively large chunks of data to a file at once,
        // minimizing the number of times that slow file-writing operations are
        // performed. The BufferedWriter class also provides a newLine() method to
        // create platform-specific line separators automatically.
        //
        // * PrintWriter : This class has been enhanced significantly in Java 5.
        // Because of newly created methods and constructors (like building a PrintWriter
        // with a File or a String ), you might find that you can use PrintWriter in places
        // where you previously needed a Writer to be wrapped with a FileWriter
        // and/or a BufferedWriter . New methods like format() , printf() , and
        // append() make PrintWriter s quite flexible and powerful.
        //
        // * FileInputStream : This class is used to read byte s from files and can be used
        // for binary as well as text. Like FileReader , the read() methods are low-level,
        // allowing you to read single byte s, a stream of byte s, or a fixed number of
        // byte s. We typically use FileInputStream with higher-level objects such as
        // ObjectInputStream
        //
        // * ObjectInputStream : This class is used to read an input stream and deserialize
        // objects. We use ObjectInputStream with lower-level classes like
        // FileInputStream to read from a file. ObjectInputStream works at a higher
        // level so that you can read objects rather than characters or bytes.
        //
        // * FileOutputStream : This class is used to write byte s to files. We typically use
        // FileOutputStream with higher-level objects such as ObjectOutputStream .
        //
        // * ObjectOutputStream : This class is used to write objects to an output stream
        // and is used with classes like FileOutputStream to write to a file. This is called
        // serialization. Like ObjectInputStream , ObjectOutputStream works at a
        // higher level to write objects, rather than characters or bytes.
        //
        // * Console This Java 6 convenience class provides methods to read input from
        // the console and write formatted output to the console.
    }

    // =========================================================================================================================================
    static void summary() throws Exception {
        // *****************************************************************************************************************************
        // File : higher level class - Create empty files, delete files, searching for files
        // *****************************************************************************************************************************
        File file01 = new File("");

        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Reader String $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

        // *****************************************************************************************************************************
        // File -> FileReader : For read characteres, read a whole file
        // *****************************************************************************************************************************
        FileReader fr01 = new FileReader(new File(""));

        // *****************************************************************************************************************************
        // File -> FileReader -> BufferedReader : For read characteres with a buffer
        // *****************************************************************************************************************************
        BufferedReader br01 = new BufferedReader(new FileReader(new File("")));

        // *****************************************************************************************************************************
        // Console : For read characteres from console
        // *****************************************************************************************************************************
        Console c01 = System.console();

        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Writer String $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

        // *****************************************************************************************************************************
        // File -> FileWriter : For write characteres, write a whole file
        // *****************************************************************************************************************************
        FileWriter fw01 = new FileWriter(new File(""));

        // *****************************************************************************************************************************
        // File -> FileWriter -> BufferedWriter : For write characteres with a buffer
        // *****************************************************************************************************************************
        BufferedWriter bw01 = new BufferedWriter(new FileWriter(new File("")));

        // *****************************************************************************************************************************
        // File -> FileWriter -> PrintWriter : For write characteres with a buffer, with format
        // *****************************************************************************************************************************
        PrintWriter pw01 = new PrintWriter(new FileWriter(new File("")));

        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Reader Bytes $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

        // *****************************************************************************************************************************
        // File -> FileInputStream : For read bytes, a whole binary file
        // *****************************************************************************************************************************
        FileInputStream fis01 = new FileInputStream(new File(""));

        // *****************************************************************************************************************************
        // File -> FileInputStream -> BufferedInputStream : Read bytes with buffer
        // *****************************************************************************************************************************
        BufferedInputStream bis01 = new BufferedInputStream(new FileInputStream(new File("")));

        // *****************************************************************************************************************************
        // File -> FileInputStream -> ObjectInputStream : Read bytes to Objects
        // *****************************************************************************************************************************
        ObjectInputStream ois01 = new ObjectInputStream(new FileInputStream(new File("")));

        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Writer Bytes $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

        // *****************************************************************************************************************************
        // File -> FileOutputStream : For write bytes, a whole binary file
        // *****************************************************************************************************************************
        FileOutputStream fos01 = new FileOutputStream(new File(""));

        // *****************************************************************************************************************************
        // File -> FileOutputStream -> BufferedOutputStream : Write bytes with buffer
        // *****************************************************************************************************************************
        BufferedOutputStream bos01 = new BufferedOutputStream(new FileOutputStream(new File("")));

        // *****************************************************************************************************************************
        // File -> FileOutputStream -> ObjectOutputStream : Read bytes to Objects
        // *****************************************************************************************************************************
        ObjectOutputStream oos01 = new ObjectOutputStream(new FileOutputStream(new File("")));

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
