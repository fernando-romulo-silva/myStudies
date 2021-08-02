package org.nandao.cap02.p7ConvertingBetweenPathTypes;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

// The Path interface represents a path within a filesystem. This path may or may not be a
// valid path. There are times when we may want to use an alternative representation of a path.
// For example, a file can be loaded into most browsers using a URI for the file. The toUri
// method provides this representation of a path. In this recipe we will also see how to obtain an
// absolute path and a real path for a Path object.

public class Test {

    public static void main(String[] args) {
        test0();
    }

    public static void test0() {
        try {
            
            // Since it is possible that a Path object may not actually represent a file, the use of the
            // toRealPath method may throw a java.nio.file.NoSuchFileException if the file
            // does not exist. Use an invalid file name, shown as follows            
            
            Path path = Paths.get("users.txt");

            System.out.println("URI path: " + path.toUri());
            System.out.println("Absolute path: " + path.toAbsolutePath());
            System.out.println("Real path: " + path.toRealPath(LinkOption.NOFOLLOW_LINKS));

        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
