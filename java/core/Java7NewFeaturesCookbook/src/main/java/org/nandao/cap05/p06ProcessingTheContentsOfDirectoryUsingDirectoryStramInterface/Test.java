package org.nandao.cap05.p06ProcessingTheContentsOfDirectoryUsingDirectoryStramInterface;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Determining the contents of a directory is a fairly common requirement. There are several
// approaches to doing this. In this recipe, we will examine the use of the java.nio.file.
// DirectoryStream interface in support of this task.
//
// A directory will consist of files or subdirectories. These files may be regular files or possibly
// linked or hidden. The DirectoryStream interface will return all of these element types.
// We will use the java.nio.file.Files class' newDirectoryStream method to obtain a
// DirectoryStream object.
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        Path directory = path;

        try (final DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {

            for (Path file : directoryStream) {
                System.out.println(file.getFileName());
            }
        }
    }
}
