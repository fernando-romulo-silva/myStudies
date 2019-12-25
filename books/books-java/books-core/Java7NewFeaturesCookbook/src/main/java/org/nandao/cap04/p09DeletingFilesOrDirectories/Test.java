package org.nandao.cap04.p09DeletingFilesOrDirectories;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Deleting files or directories when they are no longer needed is a common operation. It will
// save space on a system and result in a cleaner filesystem. There are two methods of the
// Files class that can be used to delete a file or directory: delete and deleteIfExists.
// They both take a Path object as their argument and may throw an IOException.
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home/Docs").getFile()).getAbsolutePath());

        Path sourceFile = Paths.get(pathSource + "/users.txt");
        Files.delete(sourceFile);
        Files.deleteIfExists(sourceFile);
        
        
        // If we try to delete a directory, the directory must first be empty. If the directory is not empty,
        // then a DirectoryNotEmptyException exception will be thrown. 
        
        //
        // If a directory is not empty and needs to be deleted, then it will be necessary to delete its
        // entries first using the walkFileTree method as illustrated in the Using the SimpleFileVisitor
        // class to traverse file systems recipe in Chapter 5, Managing File Systems.
    }

}
