package org.nandao.cap03.p05DeterminigOperatingSystemSupportForAttributeViews;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Set;

// An operating system may not support all the attribute views found in Java. There are three
// basic techniques for determining which views are supported. Knowing which views are
// supported allows the developer to avoid exceptions that can occur when trying to use a view
// that is not supported.
public class Test {
    public static void main(String[] args) throws Exception {
        test2();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();

        final Path path = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

        final FileSystem fileSystem = path.getFileSystem();

        final Set<String> supportedViews = fileSystem.supportedFileAttributeViews();

        for (final String view : supportedViews) {
            System.out.println(view);
        }
    }

    // The overloaded supportsFileAttributeView method accepts a class object representing
    // the view in question. Add the following code to the previous example's main method. In this
    // code, we determine which of the several views are supported:
    public static void test1() {
        try {
            final ClassLoader classLoader = Test.class.getClassLoader();

            final Path path = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

            final FileStore fileStore = Files.getFileStore(path);

            System.out.println("FileAttributeView supported: " + fileStore.supportsFileAttributeView(FileAttributeView.class));
            System.out.println("BasicFileAttributeView supported: " + fileStore.supportsFileAttributeView(BasicFileAttributeView.class));
            System.out.println("FileOwnerAttributeView supported: " + fileStore.supportsFileAttributeView(FileOwnerAttributeView.class));
            System.out.println("AclFileAttributeView supported: " + fileStore.supportsFileAttributeView(AclFileAttributeView.class));
            System.out.println("PosixFileAttributeView supported: " + fileStore.supportsFileAttributeView(PosixFileAttributeView.class));
            System.out.println("UserDefinedFileAttributeView supported: " + fileStore.supportsFileAttributeView(UserDefinedFileAttributeView.class));
            System.out.println("DosFileAttributeView supported: " + fileStore.supportsFileAttributeView(DosFileAttributeView.class));
        } catch (final IOException ex) {
            System.out.println("Attribute view not supported");
        }
    }

    public static void test2() {
        try {
            final ClassLoader classLoader = Test.class.getClassLoader();

            final Path path = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

            final FileStore fileStore = Files.getFileStore(path);

            System.out.println("FileAttributeView supported: " + fileStore.supportsFileAttributeView("file"));
            System.out.println("BasicFileAttributeView supported: " + fileStore.supportsFileAttributeView("basic"));
            System.out.println("FileOwnerAttributeView supported: " + fileStore.supportsFileAttributeView("owner"));
            System.out.println("AclFileAttributeView supported: " + fileStore.supportsFileAttributeView("acl"));
            System.out.println("PosixFileAttributeView supported: " + fileStore.supportsFileAttributeView("posix"));
            System.out.println("UserDefinedFileAttributeView supported: " + fileStore.supportsFileAttributeView("user"));
            System.out.println("DosFileAttributeView supported: " + fileStore.supportsFileAttributeView("dos"));
        } catch (final IOException ex) {
            System.out.println("Attribute view not supported");
        }
    }
}
