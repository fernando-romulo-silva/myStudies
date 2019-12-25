package org.nandao.cap02.p9ManagingSymbolicLinks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

// Symbolic links are used to create a reference to a file that actually exists in a different
// directory. In the introduction, a file hierarchy was detailed that listed the file, users.txt,
// twice; once in the docs directory and a second time in the music directory. The actual file
// is located in the docs directory.

public class Test {

    public static void main(String[] args) {
        test0();
    }

    public static void test0() {
        
        final ClassLoader classLoader = Test.class.getClassLoader();

        final Path path1 = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());
        final Path path2 = Paths.get(new File(classLoader.getResource("Home/Music/users.txt").getFile()).getAbsolutePath());
        
        System.out.println(Files.isSymbolicLink(path1));
        System.out.println(Files.isSymbolicLink(path2));
        
        try {
            final Path path = Paths.get(new File(classLoader.getResource("Home/./Music/users.txt").getFile()).getAbsolutePath());
            
            
            System.out.println("Normalized: " + path.normalize());
            System.out.println("Absolute path: " + path.toAbsolutePath());
            System.out.println("URI: " + path.toUri());
            System.out.println("toRealPath (Do not follow links): " + path.toRealPath(LinkOption.NOFOLLOW_LINKS));
            System.out.println("toRealPath: " + path.toRealPath());
            
            final Path firstPath = Paths.get("/home/music/users.txt");
            final Path secondPath = Paths.get("/docs/status.txt");
            
            System.out.println("From firstPath to secondPath: " + firstPath.relativize(secondPath));
            System.out.println("From secondPath to firstPath: " + secondPath.relativize(firstPath));
            System.out.println("exists (Do not follow links): " + Files.exists(firstPath, LinkOption.NOFOLLOW_LINKS));
            System.out.println("exists: " + Files.exists(firstPath));
            System.out.println("notExists (Do not follow links): " + Files.notExists(firstPath, LinkOption.NOFOLLOW_LINKS));
            System.out.println("notExists: " + Files.notExists(firstPath));
            
        } catch (final IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (final InvalidPathException ex) {
            System.out.println("Bad path: [" + ex.getInput() + "] at position " + ex.getIndex());
        }

    }

    public static void test1() {

    }
}
