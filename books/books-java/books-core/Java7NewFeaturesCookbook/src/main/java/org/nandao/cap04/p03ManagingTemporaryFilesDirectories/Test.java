package org.nandao.cap04.p03ManagingTemporaryFilesDirectories;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// The process of creating temporary files and directories can be an essential part of many
// applications. Temporary files may be used for intermediate data or as a temporary store
// to be cleaned up later. The process of managing temporary files and directories can be
// accomplished simply via the Files class. In this recipe, we will cover how to create temporary
// files and directories using the createTempDirectory and createTempFile methods.
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    //    This createTempDirectory method requires at least two parameters, namely, the Path
    //    object directing the location for the new directory, and a String variable specifying the
    //    directory prefix. In our previous example, we left the prefix blank. However, if we had wanted to
    //    specify text to precede the filename assigned by the system, the second variable could have
    //    been populated with this prefix string.
    //    The createTempFile method works in a similar manner as the createTempDirectory
    //    method, and had we wanted to assign a prefix to our temporary file, we could have used the
    //    second parameter to specify the string. The third parameter of this method could have also
    //    been used to specify a suffix, or file type, for our file, such as .txt.    
    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        try {
            Path rootDirectory = FileSystems.getDefault().getPath(path + "/Docs");
            Path tempDirectory = Files.createTempDirectory(rootDirectory, "");

            System.out.println("Temporary directory createdsuccessfully!");
            String dirPath = tempDirectory.toString();
            
            System.out.println(dirPath);
            Path tempFile = Files.createTempFile(tempDirectory, "", "");
            
            System.out.println("Temporary file created successfully!");
            String filePath = tempFile.toString();
            System.out.println(filePath);
            
        } catch (IOException e) {
            System.out.println("IO Exception.");
        }
    }
}
