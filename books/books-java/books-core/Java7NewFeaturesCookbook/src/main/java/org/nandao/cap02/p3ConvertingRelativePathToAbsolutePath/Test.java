package org.nandao.cap02.p3ConvertingRelativePathToAbsolutePath;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

// A relative path is useful for specifying the location of a file or directory in relationship to the
// current directory location. Typically, a single dot or two dots are used to indicate the current
// directory or next higher level directory respectively. However, the use of a dot is not required
// when creating a relative path.
//
// An absolute path starts at the root level and lists each directory separated by either forward
// slashes or backward slashes, depending on the operating system, until the desired directory
// or file is reached.

public class Test {

    public static void main(String[] args) {
        test02();
    }

    public static void test01() {

        final String separator = FileSystems.getDefault().getSeparator();
        System.out.println("The separator is " + separator);

        try {
            Path path = Paths.get(new URI("file:///C:/home/docs/users.txt"));
            System.out.println("subpath: " + path.subpath(0, 3));

            path = Paths.get("/home", "docs", "users.txt");
            System.out.println("Absolute path: " + path.toAbsolutePath());
            System.out.println("URI: " + path.toUri());

        } catch (final URISyntaxException ex) {
            System.out.println("Bad URI");
        } catch (final InvalidPathException ex) {
            System.out.println("Bad path: [" + ex.getInput() + "] at position " + ex.getIndex());
        }
    }

    // Bear in mind that the toAbsolutePath method works regardless of whether the path
    // references a valid file or directory. The file used in the previous example does not need to
    // exist. Consider the use of a bogus file as shown in the following code. The assumption is
    // that the file, bogusfile.txt, does not exist in the specified directory:
    public static void test02() {
        try {
            Path path = Paths.get(new URI("file:///C:/home/docs/bogusfile.txt"));
            System.out.println("File exists: " + Files.exists(path));

            path = Paths.get("/home", "docs", "bogusfile.txt");
            System.out.println("File exists: " + Files.exists(path));
        } catch (final URISyntaxException ex) {
            System.out.println("Bad URI");
        } catch (final InvalidPathException ex) {
            System.out.println("Bad path: [" + ex.getInput() + "] at position " + ex.getIndex());
        }
    }
}
