package org.nandao.cap05.p02GettingFilesystemInformation;

import java.io.File;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;

// A filesystem is composed of a hierarchy of directories and files. There is a limited amount of
// information regarding a filesystem that is normally useful. For example, we may want to know
// whether the filesystem is read-only or who the provider is. In this recipe we will examine the
// methods available to retrieve filesystem attributes.

public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        FileSystem fileSystem = FileSystems.getDefault();
        FileSystemProvider provider = fileSystem.provider();

        System.out.println("Provider: " + provider.toString());
        System.out.println("Open: " + fileSystem.isOpen());
        System.out.println("Read Only: " + fileSystem.isReadOnly());

        Iterable<Path> rootDirectories = fileSystem.getRootDirectories();
        System.out.println();
        System.out.println("Root Directories");

        for (Path path : rootDirectories) {
            System.out.println(path);
        }

        Iterable<FileStore> fileStores = fileSystem.getFileStores();
        System.out.println();
        System.out.println("File Stores");

        for (FileStore fileStore : fileStores) {
            System.out.println(fileStore.name());
        }
    }
}
