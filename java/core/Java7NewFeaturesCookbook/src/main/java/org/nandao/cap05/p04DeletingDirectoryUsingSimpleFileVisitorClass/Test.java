package org.nandao.cap05.p04DeletingDirectoryUsingSimpleFileVisitorClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

// The ability to delete a directory is a requirement of some applications. This can be achieved
// using the walkFileTree method and a java.nio.file.SimpleFileVisitor derived
// class.
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        Files.walkFileTree(path, new DeleteDirectory());

    }
}

class DeleteDirectory extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
        System.out.println("Deleting " + file.getFileName());
        Files.delete(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path directory, IOException exception) throws IOException {
        if (exception == null) {
            System.out.println("Deleting " + directory.getFileName());
            Files.delete(directory);
            return FileVisitResult.CONTINUE;
        } else {
            throw exception;
        }
    }
}
