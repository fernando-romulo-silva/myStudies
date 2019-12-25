package org.nandao.cap02.p2InteroperabilityBetweenFileAndNioFile;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

// While most of the capability of the java.io package has been supplemented by the newer
// packages, it is still possible to work with the older classes, in particular the java.io.File
// class. This recipe discusses how this can be accomplished
public class Test {

    public static void main(String[] args) {
        test01();
    }

    public static void test01() {

        try {
            final Path path = Paths.get(new URI("file:///home/docs/users.txt"));
            final File file = new File("/home/docs/users.txt");
            
            final Path toPath = file.toPath();
            
            System.out.println(toPath.equals(path));
            
        } catch (final URISyntaxException e) {
            System.out.println("Bad URI");
        }
    }
}
