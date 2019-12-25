package org.nandao.cap03.p01DeterminingTheFileContentType;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// The type of a file can often be derived from its extension. However this can be misleading,
// and files with the same extension may contain different types of data. The Files class'
// probeContentType method is used to determine the content type of a file, if possible. This
// is useful when the application needs some indication of what is in a file in order to process it.

public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        
        final ClassLoader classLoader = Test.class.getClassLoader();
        
        displayContentType(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());
        displayContentType(new File(classLoader.getResource("Home/Music/users.txt").getFile()).getAbsolutePath());
        displayContentType(new File(classLoader.getResource("Home/Music/Future Setting A.mp3").getFile()).getAbsolutePath());
    }

    public static void displayContentType(String pathText) throws Exception {
        final Path path = Paths.get(pathText);
        
        // The implementation of the probeContentType method is system-dependent. The method
        // will use a java.nio.file.spi.FileTypeDetector implementation to determine the
        // content type. It may examine the filename or possibly access file attributes to determine the
        // file content type. Most operating systems will maintain a list of file detectors. A detector from
        // this list is loaded and used to determine the file type. The FileTypeDetector class is not
        // extended, and it is not currently possible to determine which file detectors are available.        
        
        final String type = Files.probeContentType(path);
        System.out.println(type);
    }
}
