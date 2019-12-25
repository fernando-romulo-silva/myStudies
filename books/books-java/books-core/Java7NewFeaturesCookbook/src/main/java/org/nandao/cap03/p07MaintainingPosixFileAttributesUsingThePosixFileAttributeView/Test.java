package org.nandao.cap03.p07MaintainingPosixFileAttributesUsingThePosixFileAttributeView;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

// Many operating systems support the Portable Operating System Interface (POSIX) standard.
// This provides a more portable way of writing applications that can be ported across operating
// systems. Java 7 supports access to file attributes using the java.nio.file.attribute.
// PosixFileAttributeView interface.
// Not all operating systems support the POSIX standard. The Determining operating system
// support for attribute views recipe illustrates how to determine whether a specific operating
// system supports POSIX or not.

public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();

        final Path path = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

        final FileSystem fileSystem = path.getFileSystem();
        System.out.println("FileSystem: " + fileSystem);
        
        final PosixFileAttributeView view = Files.getFileAttributeView(path, PosixFileAttributeView.class);
        final PosixFileAttributes attributes = view.readAttributes();
        System.out.println("Group: " + attributes.group());

        System.out.println("Owner: " + attributes.owner().getName());
        
        final Set<PosixFilePermission> permissions = attributes.permissions();
        
        for (final PosixFilePermission permission : permissions) {
            System.out.print(permission.name() + " ");
        }
    }

}
