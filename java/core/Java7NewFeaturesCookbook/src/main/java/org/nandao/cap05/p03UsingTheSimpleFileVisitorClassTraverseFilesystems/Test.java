package org.nandao.cap05.p03UsingTheSimpleFileVisitorClassTraverseFilesystems;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

// When working with directory systems, a common need is to traverse the filesystem examining
// each subdirectory within a file hierarchy. This task has been made easy with the java.nio.file.SimpleFileVisitor class.
//
// This class implements methods that execute before and after a directory is visited. In addition, callback methods
// are invoked for each instance a file is visited in a directory and if an exception occurs.
//
// The SimpleFileVisitor class or a derived class is used in conjunction with the java.nio.file.Files class' walkFileTree method.
// It performs a depth first traversal, starting at a specific root directory.
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        ListFiles listFiles = new ListFiles();
        Files.walkFileTree(path, listFiles);
    }
}

class ListFiles extends SimpleFileVisitor<Path> {
    private final int indentionAmount = 3;
    private int indentionLevel;

    public ListFiles() {
        indentionLevel = 0;
    }

    private void indent() {
        for (int i = 0; i < indentionLevel; i++) {
            System.out.print(' ');
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
        indent();
        System.out.println("Visiting file:" + file.getFileName());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path directory, IOException e) throws IOException {
        indentionLevel -= indentionAmount;
        indent();
        System.out.println("Finished with the directory: " + directory.getFileName());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attributes) throws IOException {
        indent();
        System.out.println("About to traverse the directory: " + directory.getFileName());
        indentionLevel += indentionAmount;
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("A file traversal error ocurred");
        return super.visitFileFailed(file, exc);
    }
}
