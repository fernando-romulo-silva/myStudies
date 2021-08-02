package org.nandao.cap03.p02ObtainingSingleAttributeUsingTheGetAttributeMethod;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

// If you are interested in getting a single file attribute, and you know the name of the attribute,
// then the Files class' getAttribute method is simple and easy to use. It will return
// information about the file based upon a String representing the attribute. The first part of
// this recipe illustrates a simple use of the getAttribute method.

public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();

        final Path path = FileSystems.getDefault().getPath(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

        System.out.println(Files.getAttribute(path, "size"));

        // The Files class' getAttribute method possesses the following three arguments:
        // 1 A Path object representing the file
        // 2 A String containing the name of the attribute
        // 3 An optional LinkOption to use when dealing with symbolic files
    }
}
