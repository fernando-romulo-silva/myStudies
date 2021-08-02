package org.nandao.cap04.p08MovingFileAndDirectory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

// Moving a file or directory can be useful when reorganizing the structure of a user space. This
// operation is supported by the Files class' move method. When moving a file or directory
// there are several factors to consider. These include whether the symbolic link files are present,
// whether the move should replace existing files, and whether the move should be atomic.
//
// A move may result in the renaming of the resource if the move occurs on the same file
// store. The use of this method will sometimes use the Path interface's resolveSibling
// method. This method will replace the last part of a path with its argument. This is useful when
// renaming files.
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home/Docs").getFile()).getAbsolutePath());

        Path sourceFile = Paths.get(pathSource + "/users.txt");
        Path destinationFile = Paths.get(pathSource + "/../users.txt");

        Files.move(sourceFile, destinationFile, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);

        // ATOMIC_MOVE - The move operation is atomic in nature
        // COPY_ATTRIBUTES - The source file attributes are copied to the new file
        // REPLACE_EXISTING  - The destination file is replaced if it exists
    }

    // Using the resolveSibling method with the move method to affect a rename operation
    public static void test1() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home/Docs").getFile()).getAbsolutePath());

        Path sourceFile = Paths.get(pathSource + "/users.txt");

        // A more sophisticated approach might use the following sequence:
        String newFileName = sourceFile.getFileName().toString();
        newFileName = newFileName.substring(0, newFileName.indexOf('.')) + ".bak";
        Files.move(sourceFile, sourceFile.resolveSibling(newFileName));
    }

    // Moving a directory
    // When a directory is moved on the same file store, then the directory and subdirectories are
    // moved. The following will move the docs directory, its files, and its subdirectories to the
    // music directory as follows:
    public static void test2() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        // Moving a directory across file stores will result in an exception if the directory is not empty.
        // If the docs directory had been empty in the previous example, the move method would have
        // executed successfully. If you need to move a non-empty directory across file stores, then this
        // will normally involve a copy operation followed by a delete operation.
        
        Path sourceFile = Paths.get(pathSource + "/Docs");
        Path destinationFile = Paths.get(pathSource + "/Music/Docs");
        Files.move(sourceFile, destinationFile);
    }
}
