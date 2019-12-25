package org.nandao.cap03.p09MaintainingFileOwnershipAttributesUsingTheFileOwnerAttributeView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;

// If we are only interested in accessing information about the owners of a file or directory, then
// the java.nio.file.attribute.FileOwnerAttributeView interface provides methods
// for retrieving and setting this type of information.
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();

        final Path path = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());
        
        try {
            final FileOwnerAttributeView view = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
            final UserPrincipal userPrincipal = view.getOwner();
            System.out.println(userPrincipal.getName());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
