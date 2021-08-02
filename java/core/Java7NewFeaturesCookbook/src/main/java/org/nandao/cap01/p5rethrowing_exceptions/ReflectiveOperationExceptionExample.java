package org.nandao.cap01.p5rethrowing_exceptions;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReflectiveOperationExceptionExample {

    public static void main(String[] args) {
        try {
            deleteFile(Paths.get(new URI("file:///tmp.txt")));
        } catch (final URISyntaxException ex) {
            ex.printStackTrace();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    
    // In order to rethrow exceptions in Java, you must first catch them. From within the catch block,
    // use the throw keyword with the exception to be thrown. The new rethrow technique in Java 7
    // requires that you:
    // 1 Use a base class exception class in the catch block
    // 2 Use the throw keyword to throw the derived class exception from the catch block
    // 3 Modify the method's signature to throw the derived exceptions
    private static void deleteFile(Path path) throws NoSuchFileException, DirectoryNotEmptyException {
        try {
            Files.delete(path);
        } catch (final IOException ex) {
            if (path.toFile().isDirectory()) {
                throw new DirectoryNotEmptyException(null);
            } else {
                throw new NoSuchFileException(null);
            }
        }
    }
}
