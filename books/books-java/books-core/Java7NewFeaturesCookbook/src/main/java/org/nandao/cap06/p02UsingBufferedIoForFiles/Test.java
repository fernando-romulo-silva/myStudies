package org.nandao.cap06.p02UsingBufferedIoForFiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// Buffered IO provides a more efficient technique for accessing files. Two methods of the java.nio.file
// package's Files class return either a java.io package BufferedReader or a BufferedWriter object.
//
// These classes provide an easy to use and efficient technique for working with text files.
// We will illustrate the read operation first. In the There's more section, we will demonstrate how
// to write to a file.

public class Test {
    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        final Path path = Paths.get(pathSource + "/Docs/users.txt");
        final Charset charset = Charset.forName("ISO-8859-1");

        try (final BufferedReader reader = Files.newBufferedReader(path, charset)) {

            String line = null;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        // -----------------------------------------------------------------
        final String newName = "Vivian";

        final Path file1 = Paths.get(pathSource + "/Docs/users.txt");

        try (final BufferedWriter writer = Files.newBufferedWriter(file1, Charset.defaultCharset(), StandardOpenOption.APPEND)) {
            
            writer.newLine();
            
            writer.write(newName, 0, newName.length());
        }

        // ------------------------------------------------------------------
        // Un-buffered IO support in the Files class
        final Path file2 = Paths.get(pathSource + "/Docs/users.txt");

        final Path newFile = Paths.get(pathSource + "/Docs/newUsers.txt");

        try ( //
            final InputStream in = Files.newInputStream(file2); //
            OutputStream out = Files.newOutputStream(newFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            int data = in.read();
            
            while (data != -1) {
                out.write(data);
                data = in.read();
            }
        }

    }
}
