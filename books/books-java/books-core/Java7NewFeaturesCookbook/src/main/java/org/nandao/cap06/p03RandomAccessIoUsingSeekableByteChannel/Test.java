package org.nandao.cap06.p03RandomAccessIoUsingSeekableByteChannel;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// Random access to a file is useful for more complex files. It allows access to specific
// positions within the file in a non-sequential fashion. The java.nio.channels
// package's SeekableByteChannel interface provides this support, based on channel IO.
// Channels provide a low-level approach for bulk data transfers. In this recipe we will use a
// SeekableByteChannel to access a file.
public class Test {

    public static void main(String[] args) throws Exception {
        test2();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        final int bufferSize = 8;

        final Path path = Paths.get(pathSource + "/Docs/users.txt");

        try (SeekableByteChannel sbc = Files.newByteChannel(path)) {

            final ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            sbc.position(4);
            sbc.read(buffer);

            for (int i = 0; i < 5; i++) {
                System.out.print((char) buffer.get(i));
            }

            System.out.println();

            buffer.clear();
            sbc.position(0);

            sbc.read(buffer);

            for (int i = 0; i < 4; i++) {
                System.out.print((char) buffer.get(i));
            }

            System.out.println();
        }
    }

    // Processing the contents of the entire file
    // Add the following code to the application. The purpose is to demonstrate how to
    // process the entire file in a sequential fashion and to gain an understanding of various
    // SeekableByteChannel interface methods.
    public static void test1() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        final int bufferSize = 8;

        final Path path = Paths.get(pathSource + "/Docs/users.txt");

        try (SeekableByteChannel sbc = Files.newByteChannel(path)) {

            // Read the entire file
            System.out.println("Contents of File");
            sbc.position(0);

            final ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            final String encoding = System.getProperty("file.encoding");

            int numberOfBytesRead = sbc.read(buffer);

            System.out.println("Number of bytes read: " + numberOfBytesRead);

            while (numberOfBytesRead > 0) {
                buffer.rewind();
                System.out.print("[" + Charset.forName(encoding).decode(buffer) + "]");
                buffer.flip();
                numberOfBytesRead = sbc.read(buffer);
                System.out.println("\nNumber of bytes read: " + numberOfBytesRead);
            }
        }
    }

    // Writing to a file using the SeekableByteChannel interface
    // The write method takes a java.nio package's ByteBuffer object and writes it to the
    // channel. The operation starts at the current position in the file. For example, if the file was
    // opened with an append option, the first write will be at the end of the file. The method returns
    // the number of bytes written.
    public static void test2() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        final Path path = Paths.get(pathSource + "/Docs/users.txt");

        final String newLine = System.getProperty("line.separator");

        try (SeekableByteChannel sbc = Files.newByteChannel(path, StandardOpenOption.APPEND)) {

            final String output = newLine + "Paul" + newLine + "Carol" + newLine + "Fred";
            final ByteBuffer buffer = ByteBuffer.wrap(output.getBytes());
            sbc.write(buffer);

        }
    }

    // Query the position
    // The overloaded position method returns a long value indicating the position within the
    // file. This is complemented by a position method that takes a long argument and sets the
    // position to that value. If the value exceeds the size of the stream, then the position is set to
    // the end of the stream. The size method will return the size of the file used by the channel.
    public static void test3() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        final Path path = Paths.get(pathSource + "/Docs/users.txt");

        final String newLine = System.getProperty("line.separator");

        try (SeekableByteChannel sbc = Files.newByteChannel(path, StandardOpenOption.WRITE)) {
            ByteBuffer buffer = null;
            final long position = sbc.size();
            sbc.position(position);
            System.out.println("Position: " + sbc.position());

            buffer = ByteBuffer.wrap((newLine + "Paul").getBytes());
            sbc.write(buffer);
            System.out.println("Position: " + sbc.position());

            buffer = ByteBuffer.wrap((newLine + "Carol").getBytes());
            sbc.write(buffer);
            System.out.println("Position: " + sbc.position());

            buffer = ByteBuffer.wrap((newLine + "Fred").getBytes());
            sbc.write(buffer);
            System.out.println("Position: " + sbc.position());
        }
    }
}
