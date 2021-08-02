package org.nandao.cap05.p10UnderstandingTheZipFilesystemProvider;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// Handling ZIP files is much simpler than it was prior to Java 7. The ZIP filesystem provider
// introduced in this release handles ZIP and JAR files as though they were filesystems and, as a
// result, you can easily access the contents of the file. You can manipulate the file as you would
// do ordinary files, including copying, deleting, moving, and renaming the file. You also have
// the ability to modify certain attributes of the file. This recipe will show you how to create an
// instance of a ZIP filesystem and add directories to the system.

public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathOrigem = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        final Map<String, String> attributes = new HashMap<>();
        attributes.put("create", "true");

        final Path pathJarFile = Paths.get(pathOrigem + "foo.jar");
        
        try {
            final URI zipFile = new URI("jar", pathJarFile.toUri().toString(), null);

            try (final FileSystem zipFileSys = FileSystems.newFileSystem(zipFile, attributes);) {

                Path pathFolder = zipFileSys.getPath(pathOrigem + "/Docs/Temp");

                Files.createDirectory(pathFolder);
                
                try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(zipFileSys.getPath("/"));) {

                    for (Path file : directoryStream) {
                        System.out.println(file.getFileName());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
