package org.nandao.ch08MiscellaneousGoodies.part05WorkingWithFiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import java.util.stream.Stream;

public class Test {

    // Streams of Lines
    public static void test1() throws IOException {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("users.txt").getFile()).getAbsolutePath());

        // To read the lines of a file lazily, use the Files.lines method. It yields a stream of strings, one per line of input:
        try (final Stream<String> lines = Files.lines(path)) {
            final Optional<String> bobEntry = lines.filter(s -> s.contains("Bob")).findFirst();
            System.out.println(bobEntry.get());
        } // The stream, and hence the file, will be closed here

        // As soon as the first line containing 'Bob' is found, no further lines are read from the underlying file.

        // When filteredLines is closed, it closes the underlying stream, which closes the underlying file.
        try (Stream<String> filteredLines = Files.lines(path).onClose(() -> System.out.println("Closing")).filter(s -> s.contains("password"))) {
            final Optional<String> bobEntry = filteredLines.findFirst();
            System.out.println(bobEntry.get());
        }

        // If an IOException occurs as the stream fetches the lines, that exception is wrapped
        // into an UncheckedIOException which is thrown out of the stream operation. (This
        // subterfuge is necessary because stream operations are not declared to throw any
        // checked exceptions.)

        // If you want to read lines from a source other than a file, use the BufferedReader.lines method instead:
        final URL url = new URL("www.google.com");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            final Stream<String> lines = reader.lines();
            final Optional<String> findFirst = lines.findFirst();
            System.out.println(findFirst);
        }
        // With this method, closing the resulting stream does not close the reader. For that
        // reason, you must place the BufferedReader object, and not the stream object, into
        // the header of the try statement.
    }

    // Streams of Directory Entries
    public static void test2() throws Exception {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("images").getFile()).getAbsolutePath());

        // The static Files.list method returns a Stream<Path> that reads the entries of a directory.
        // The directory is read lazily, making it possible to efficiently process
        // directories with huge numbers of entries.
        //
        // The list method does not enter subdirectories.
        try (Stream<Path> entries = Files.list(path)) {

            entries
                // .filter( //
                // (p) -> !p.toFile().isDirectory() && //
                // p.toFile().getAbsolutePath().endsWith(".txt") //
                // )
                .forEach(p -> System.out.println("File:" + p.getFileName()));

        }

        System.out.println();

        // To process all descendants of a directory, use the Files.walk method instead.
        try (Stream<Path> entries = Files.walk(path)) {
            // Contains all descendants, visited in depth-first order

            entries
                // .filter( //
                // (p) -> !p.toFile().isDirectory() && //
                // p.toFile().getAbsolutePath().endsWith(".txt") //
                // )
                .forEach(p -> System.out.println("File:" + p.getFileName()));
        }

        // You can limit the depth of the tree that you want to visit by calling
        // Files.walk(pathToRoot, depth). Both walk methods have a varargs parameter of type
        // FileVisitOption..., but there is currently only one option you can supply: FOLLOW_LINKS
        // to follow symbolic links
    }

    // Base64 Encoding
    public static void test3() {

        // For many years, the JDK had a nonpublic (and therefore unusable) class java.util.prefs.Base64
        // and an undocumented class/ sun.misc.BASE64Encoder. Finally, Java 8 provides a standard encoder
        // and decoder.
        // The Base64 encoding uses 64 characters to encode six bits of information:
        // • 26 uppercase letters A . . . Z
        // • 26 lowercase letters a . . . z
        // • 10 digits 0 . . . 9
        // • 2 symbols, + and / (basic) or - and _ (URL- and filename-safe variant)

        final String username = "user";
        final String password = "123456";

        final Base64.Encoder encoder = Base64.getEncoder();
        final String original = username + ":" + password;
        final String encoded = encoder.encodeToString(original.getBytes(StandardCharsets.UTF_8));

        System.out.println("Original:" + original + " encoded:" + encoded);
    }

    public static void main(final String[] args) throws Exception {
        test3();
    }
}
