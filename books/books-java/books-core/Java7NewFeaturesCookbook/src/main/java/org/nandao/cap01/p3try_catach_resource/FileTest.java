package org.nandao.cap01.p3try_catach_resource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Prior to Java 7, the code required for properly opening and closing resources, such as a
// java.io.InputStream or java.nio.Channel, was quite verbose and prone to errors.
// The try-with-resources block has been added in an effort to simplify error-handling and
// make the code more concise. The use of the try-with-resources statement results in all of its
// resources being automatically closed when the try block exits. Resources declared with the
// try-with-resources block must implement the interface java.lang.AutoCloseable.
//
// Page 18
public class FileTest {

    public static void main(String[] args) {
        test2();
    }

    // In support of this approach, a new constructor was added to the java.lang.Exception class
    // along with two methods: addSuppressed and getSuppressed. Suppressed exceptions are
    // those exceptions that are not explicitly reported. In the case of the try-with-resources try block,
    // exceptions may be thrown from the try block itself or when the resources created by the try block
    // are closed. When more than one exception is thrown, exceptions may be suppressed.
    public static void test0() {
        try ( // inputReader and outputWriter will close automatically
            BufferedReader inputReader = Files.newBufferedReader(Paths.get(new URI("file:///C:/home/docs/users.txt")), Charset.defaultCharset()); //
            BufferedWriter outputWriter = Files.newBufferedWriter(Paths.get(new URI("file:///C:/home/docs/users.bak")), Charset.defaultCharset())) {

            String inputLine = null;

            while ((inputLine = inputReader.readLine()) != null) {
                outputWriter.write(inputLine);
                outputWriter.newLine();
            }

            System.out.println("Copy complete!");

        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }

    // Structuring issues when using the try-with-resources technique
    // It may not be desirable to use this technique when a single resource is used. We will show
    // three different implementations of a sequence of code to display the contents of the
    // users.txt file. The first, as shown in the following code, uses the try-with-resources block.
    // However, it is necessary to precede this block with a try block to capture the java.net.URISyntaxException:
    public static void test1() {

        Path path = null;

        try {
            path = Paths.get(new URI("file:///C:/home/docs/users.txt"));
        } catch (final URISyntaxException e) {
            System.out.println("Bad URI");
        }

        try (BufferedReader inputReader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String inputLine;

            while ((inputLine = inputReader.readLine()) != null) {
                System.out.println(inputLine);
            }

        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    // Within the close method, one of the following three actions is possible:
    // 1 Do nothing if there is nothing to close or the resource will always close
    // 2 Close the resource and return without error
    // 3 Attempt to close the resource, but throw an exception upon failure
    public static void test2() {
        try ( //
            // The close method is not required to be idempotent. An idempotent method is the one where
            // repeated execution of the method will not cause problems. As an example, reading from the
            // same file twice will not necessarily cause problems. Whereas, writing the same data twice to
            // the file may. The close method does not have to be idempotent, however, it is recommended
            // that it should be.            
            
            FirstAutoCloseableResource resource1 = new FirstAutoCloseableResource(); //
            
            SecondAutoCloseableResource resource2 = new SecondAutoCloseableResource()) {
            
            resource1.manipulateResource();
            resource2.manipulateResource();
        } catch (final Exception e) {
            e.printStackTrace();
            for (final Throwable throwable : e.getSuppressed()) {
                System.out.println(throwable);
            }
        }
    }
}
