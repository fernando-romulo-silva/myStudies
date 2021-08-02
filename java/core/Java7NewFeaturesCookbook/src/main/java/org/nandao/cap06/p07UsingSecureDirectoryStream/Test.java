package org.nandao.cap06.p07UsingSecureDirectoryStream;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SecureDirectoryStream;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;


// The java.nio.file package's SecureDirectoryStream class is designed to be used
// with applications that depend on tighter security than that provided by other IO classes. 
// It supports race-free (sequentially consistent) operations on a directory, where the operations
// are performed concurrently with other applications.
//
// This class requires support from the operating system. An instance of the class is obtained
// by casting the return value of the Files class' newDirectoryStream method to a
// SecureDirectoryStream object. If the cast fails, then the underlying operating system
// does not support this type of stream
public class Test {
    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        final Path path = Paths.get(pathSource + "/Docs");
        
        final SecureDirectoryStream<Path> sds = (SecureDirectoryStream<Path>) Files.newDirectoryStream(path);

        final PosixFileAttributeView view = sds.getFileAttributeView(PosixFileAttributeView.class);

        final PosixFileAttributes attributes = view.readAttributes();

        final Set<PosixFilePermission> permissions = attributes.permissions();

        for (final PosixFilePermission permission : permissions) {
            System.out.print(permission.toString() + ' ');
        }

        System.out.println();
    }
}
