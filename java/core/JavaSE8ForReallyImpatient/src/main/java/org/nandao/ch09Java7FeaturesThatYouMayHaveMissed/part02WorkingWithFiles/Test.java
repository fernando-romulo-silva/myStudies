package org.nandao.ch09Java7FeaturesThatYouMayHaveMissed.part02WorkingWithFiles;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Test {

    // Paths
    // A Path is a sequence of directory names, optionally followed by a file name. The
    // first component of a path may be a root component, such as / or C:\.
    public static void test1() throws Exception {

        final Path absolute = Paths.get("/", "home", "cay");
        final Path myProgPath = Paths.get("myprog", "conf", "user.properties");

        // You can also provide a string with separators to the Paths.get method:
        final Path homeDirectory = Paths.get("/home/cay");

        // It is very common to combine or resolve paths. The call p.resolve(q) returns a path according to these rules:
        //
        // • If q is absolute, then the result is q.
        // • Otherwise, the result is “p then q,” according to the rules of the file system.
        //
        // For example, suppose your application needs to find its configuration file relative
        // to the home directory. Here is how you can combine the paths:

        final Path configPath = homeDirectory.resolve("myprog/conf/user.properties");
        // Same as homeDirectory.resolve(Paths.get("myprog/conf/user.properties"));

        // There is a convenience method resolveSibling that resolves against a path’s parent,
        // yielding a sibling path. For example, if workPath is /home/cay/myprog/work, the call

        final Path workPath = Paths.get(" /home/cay/myprog/work");

        final Path tempPath = workPath.resolveSibling("temp");
        // yields /home/cay/myprog/temp.
        // The opposite of resolve is relativize. The call p.relativize(r) yields the path q which,
        //  when resolved with p, yields r. For example,
        Paths.get("/home/cay").relativize(Paths.get("/home/fred/myprog"));

        // The Path interface has many useful methods for taking paths apart and combining
        // them with other paths. This code sample shows some of the most useful ones:
        final Path p = Paths.get("/home", "cay", "myprog.properties");
        final Path parent = p.getParent(); // The path /home/cay
        final Path file = p.getFileName(); // The last element, myprog.properties
        final Path root = p.getRoot(); // The initial segment / (null for a relative path)
    }

    // Reading and Writing Files
    public static void test2() throws Exception {

        final Path homeDirectory = Paths.get("/home");

        // The Files class makes quick work of common file operations. For example, you
        // can easily read the entire contents of a file:
        //
        final byte[] bytes = Files.readAllBytes(homeDirectory);
        //If you want to read the file as a string, call readAllBytes followed by
        final String content = new String(bytes, StandardCharsets.UTF_8);
        //But if you want the file as a sequence of lines, call
        final List<String> lines = Files.readAllLines(homeDirectory);
        // Conversely, if you want to write a string, call
        Files.write(homeDirectory, content.getBytes(StandardCharsets.UTF_8));
        //You can also write a collection of lines with
        Files.write(homeDirectory, lines);
        // To append to a given file, use
        Files.write(homeDirectory, lines, StandardOpenOption.APPEND);
        //

        final Path workPath = Paths.get(" /home/cay/myprog/work");

        final Path tempPath = workPath.resolveSibling("temp");

        // When you work with text files of moderate length, it is usually simplest to process
        // the contents as a single string or list of strings. If your files are large or binary,
        // you can still use the familiar streams or readers/writers:

        final InputStream in = Files.newInputStream(homeDirectory);
        final OutputStream out = Files.newOutputStream(homeDirectory);
        final Reader reader = Files.newBufferedReader(homeDirectory);
        final Writer writer = Files.newBufferedWriter(homeDirectory);

        //Occasionally, you may have an InputStream (for example, from a URL) and you
        // want to save its contents to a file. Use
        Files.copy(in, tempPath);
        // Conversely,
        Files.copy(workPath, out);
        // copies a file to an output stream.
    }

    // Creating Files and Directories
    public static void test3() throws Exception {

        final Path homeDirectory = Paths.get("/home");

        // To create a new directory, call, throw a exception if already exists
        Files.createDirectory(homeDirectory);
        //
        // All but the last component in the path must already exist. To create intermediate
        // directories as well, use
        Files.createDirectories(homeDirectory);
        //
        // You can create an empty file with
        Files.createFile(homeDirectory);
        // The call throws an exception if the file already exists. The check for existence and
        // the creation are atomic. 
        //
        // There are convenience methods for creating a temporary file or directory in a
        // given or system-specific location.
        // Path newPath1 = Files.createTempFile(dir, prefix, suffix);
        // Path newPath2 = Files.createTempFile(prefix, suffix);
        // Path newPath3 = Files.createTempDirectory(dir, prefix);
        // Path newPath4 = Files.createTempDirectory(prefix);
        // Here, dir is a Path, and prefix/suffix are strings which may be null. 
        // For example, the call Files.createTempFile(null, ".txt") might return a path such as
        // /tmp/1234405522364837194.txt.
    }

    public static void main(final String[] args) {

    }
}
