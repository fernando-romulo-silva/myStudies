package org.nandao.cap04.p05ManagingFileOwnership;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;

// The owner of a file or directory can be modified after the file has been created.
// This is accomplished by using the java.nio.file.attribute.FileOwnerAttributeView
// interface's setOwner method, which can be useful when ownerships change and need to be
// controlled programmatically.
//
// A java.nio.file.attribute.UserPrincipal object is used to represent a user. A
// Path object is used to represent a file or directory. Using these two objects with the Files
// class' setOwner method enables us to maintain file ownerships.

public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home/Docs").getFile()).getAbsolutePath());

        Path path = Paths.get(pathSource + "/users.txt");

        final FileOwnerAttributeView view = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
        final UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();

        // UserPrincipal
        final UserPrincipal userPrincipal = lookupService.lookupPrincipalByName("fernando.romulo");
        view.setOwner(userPrincipal);

        System.out.println("Owner: " + view.getOwner().getName());
    }

    public static void test1() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home/Docs").getFile()).getAbsolutePath());

        Path path = Paths.get(pathSource + "/users.txt");
        
        try {
            UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
            UserPrincipal userPrincipal = lookupService.lookupPrincipalByName("jennifer");

            // You can do it as well
            Files.setOwner(path, userPrincipal);

            final FileOwnerAttributeView view = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
            System.out.println("Owner: " + view.getOwner().getName());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
