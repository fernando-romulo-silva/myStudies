package org.nandao.cap05.p05CopyingDirectoryUsingTheSimpleFileVisitor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

// The ability to copy a directory is a requirement of some applications. This can be achieved
// using the walkFileTree method and a java.nio.file.SimpleFileVisitor derived
// class. This recipe builds on the foundation provided in the Using the SimpleFileVisitor class to
// traverse filesystems recipe.

public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        Path source = Paths.get(path + "/Docs");
        Path target = Paths.get(path + "/Backup");
        
        Files.walkFileTree(source, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new CopyDirectory(source, target));
    }

    static class CopyDirectory extends SimpleFileVisitor<Path> {
        private Path source;
        private Path target;

        public CopyDirectory(Path source, Path target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
            System.out.println("Copying " + source.relativize(file));
            Files.copy(file, target.resolve(source.relativize(file)));
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attributes) throws IOException {
            Path targetDirectory = target.resolve(source.relativize(directory));
            try {
                System.out.println("Copying " + source.relativize(directory));
                Files.copy(directory, targetDirectory);
            } catch (FileAlreadyExistsException e) {
                if (!Files.isDirectory(targetDirectory)) {
                    throw e;
                }
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
