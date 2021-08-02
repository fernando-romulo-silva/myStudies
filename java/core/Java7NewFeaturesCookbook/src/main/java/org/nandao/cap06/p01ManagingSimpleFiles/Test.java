package org.nandao.cap06.p01ManagingSimpleFiles;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

// Some files are small and contain simple data. This is usually true for text files. When it is
// feasible to read or write the entire contents of the file at one time, there are a few Files
// class methods that will work quite well.
//
// In this recipe, we will examine techniques for processing simple files. Initially, we will
// examine how to read the contents of these types of files.
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        final Path path = Paths.get(pathSource + "/Docs/users.txt");
        final byte[] contents = Files.readAllBytes(path);

        for (final byte b : contents) {
            System.out.print((char) b);
        }

        final Path newPath = Paths.get(pathSource + "/Docs/newUsers.txt");
        final byte[] newContent = "Christopher".getBytes();

        Files.write(newPath, contents, StandardOpenOption.CREATE);
        Files.write(newPath, newContent, StandardOpenOption.APPEND);

        final List<String> newContents = Files.readAllLines(newPath, Charset.defaultCharset());
        
        for (final String b : newContents) {
            System.out.println(b);
        }
    }

}
