package org.nandao.cap03.p06MaintainingBasicFileAttributesUsingTheBasicFileAttributeView;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

// The java.nio.file.attribute.BasicFileAttributeView provides a series of
// methods that obtain basic information about a file such as its creation time and size. The
// view possesses a readAttributes method, which returns a BasicFileAttributes
// object. The BasicFileAttributes interface possesses several methods for accessing
// file attributes. This view provides an alternative means of obtaining file information than that
// supported by the Files class. The results of this method may be more reliable at times than
// those of the Files class.

public class Test {
    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();

        final Path path = FileSystems.getDefault().getPath(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

        try {
            final BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);

            System.out.println("Creation Time: " + attributes.creationTime());
            System.out.println("Last Accessed Time: " + attributes.lastAccessTime());
            System.out.println("Last Modified Time: " + attributes.lastModifiedTime());
            // The fileKey method returns an object that uniquely identifies that file. In UNIX, the device
            // id or inode is used for this purpose. The file key will not necessarily be unique if the filesystem
            // and its files are changed. They can be compared using the equals method, and can be used
            // in collections.
            System.out.println("File Key: " + attributes.fileKey());
            System.out.println("Directory: " + attributes.isDirectory());
            // if the isOther method returns true, it means that the file is not a regular file, directory, or a symbolic link
            System.out.println("Other Type of File: " + attributes.isOther());
            System.out.println("Regular File: " + attributes.isRegularFile());
            System.out.println("Symbolic File: " + attributes.isSymbolicLink());
            System.out.println("Size: " + attributes.size());
        } catch (final IOException ex) {
            System.out.println("Attribute error");
        }
    }

    public static void test1() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();

        final Path path = FileSystems.getDefault().getPath(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

        try {
            // This approach is longer, but we now have access to three additional methods, which are shown as follows:
            // 1 name: This returns the name of the attribute view
            // 2 readAttributes: This returns a BasicFileAttributes object
            // 3 setTimes: This is used to set the file's time attributes

            final BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);
            System.out.println("Name: " + view.name());

            final BasicFileAttributes attributes = view.readAttributes();

            System.out.println("Creation Time: " + attributes.creationTime());
            System.out.println("Last Accessed Time: " + attributes.lastAccessTime());
            System.out.println("Last Modified Time: " + attributes.lastModifiedTime());
            // The fileKey method returns an object that uniquely identifies that file. In UNIX, the device
            // id or inode is used for this purpose. The file key will not necessarily be unique if the filesystem
            // and its files are changed. They can be compared using the equals method, and can be used
            // in collections.
            System.out.println("File Key: " + attributes.fileKey());
            System.out.println("Directory: " + attributes.isDirectory());
            // if the isOther method returns true, it means that the file is not a regular file, directory, or a symbolic link
            System.out.println("Other Type of File: " + attributes.isOther());
            System.out.println("Regular File: " + attributes.isRegularFile());
            System.out.println("Symbolic File: " + attributes.isSymbolicLink());
            System.out.println("Size: " + attributes.size());

        } catch (final IOException ex) {
            System.out.println("Attribute error");
        }
    }
}
