package org.nandao.cap04.p07ManagingPosixAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Set;

// The POSIX attributes available include a group owner, a user owner, and a set of permissions.
// In this recipe, we will investigate how to maintain these attributes. The management of these
// attributes makes it easier to develop applications designed to execute on multiple operating
// systems. While the number of attributes is limited, they may be sufficient for many applications.
// There are three approaches that can be used to manage POSIX attributes:
//
// 1 The java.nio.file.attribute.PosixFileAttributeView interface
// 2 The Files class' set/get POSIX file permission methods
// 3 The Files class' setAttribute method
//
// The approach used to gain access to the PosixFileAttributes object using the
// PosixFileAttributeView interface is detailed in the Chapter 3 recipe Using the
// PosixFileAttributeView to maintain POSIX file attributes.

public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home/Docs").getFile()).getAbsolutePath());

        Path path = Paths.get(pathSource + "/users.txt");

        final FileSystem fileSystem = path.getFileSystem();
        PosixFileAttributeView view = Files.getFileAttributeView(path, PosixFileAttributeView.class);

        PosixFileAttributes attributes = view.readAttributes();
        Set<PosixFilePermission> permissions = attributes.permissions();

        listPermissions(permissions);

        permissions.add(PosixFilePermission.OTHERS_WRITE);
        view.setPermissions(permissions);

        System.out.println();
        listPermissions(permissions);

        permissions.remove(PosixFilePermission.OTHERS_WRITE);
        view.setPermissions(permissions);

        // setting permissions with Files
        Files.setPosixFilePermissions(path, permissions);
    }

    public static void test1() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home/Docs").getFile()).getAbsolutePath());

        Path path = Paths.get(pathSource + "/users.txt");

        try {
            FileSystem fileSystem = path.getFileSystem();
            PosixFileAttributeView view = Files.getFileAttributeView(path, PosixFileAttributeView.class);

            PosixFileAttributes attributes = view.readAttributes();
            Set<PosixFilePermission> permissions = attributes.permissions();

            // Get permissions by Files
            Set<PosixFilePermission> permissionsShow = Files.getPosixFilePermissions(path);

            System.out.print("Permissions: ");
            for (PosixFilePermission permission : permissionsShow) {
                System.out.print(permission.name() + " ");
                System.out.println();
            }

            System.out.println("Old Group: " + attributes.group().getName());
            System.out.println("Old Owner: " + attributes.owner().getName());
            System.out.println();

            UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
            UserPrincipal userPrincipal = lookupService.lookupPrincipalByName("fernando");
            GroupPrincipal groupPrincipal = lookupService.lookupPrincipalByGroupName(("fernando"));

            view.setGroup(groupPrincipal);
            view.setOwner(userPrincipal);
            attributes = view.readAttributes();

            System.out.println("New Group: " + attributes.group().getName());
            System.out.println("New Owner: " + attributes.owner().getName());
            System.out.println();

            // set attribute
            try {
                Files.setAttribute(path, "posix:permission", PosixFilePermission.OTHERS_WRITE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Using the PosixFilePermissions class to create PosixFilePermissions
    public static void test2() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home/Docs").getFile()).getAbsolutePath());

        Path path = Paths.get(pathSource + "/users.txt");

        FileSystem fileSystem = path.getFileSystem();
        PosixFileAttributeView view = Files.getFileAttributeView(path, PosixFileAttributeView.class);
        PosixFileAttributes attributes = view.readAttributes();
        
        Set<PosixFilePermission> permissions = attributes.permissions();

        for (PosixFilePermission permission : permissions) {
            System.out.print(permission.toString() + ' ');
        }

        System.out.println();
        FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(permissions);
        Set<PosixFilePermission> fileAttributeSet = fileAttributes.value();
        
        for (PosixFilePermission posixFilePermission : fileAttributeSet) {
            System.out.print(posixFilePermission.toString() + ' ');
        }

        System.out.println();
        System.out.println(PosixFilePermissions.toString(permissions));
        permissions = PosixFilePermissions.fromString("rw-rw-r--");
        
        for (PosixFilePermission permission : permissions) {
            System.out.print(permission.toString() + ' ');
        }

        System.out.println();

    }

    private static void listPermissions(final Set<PosixFilePermission> permissions) {
        System.out.print("Permissions: ");

        for (PosixFilePermission permission : permissions) {
            System.out.print(permission.name() + " ");
        }

        System.out.println();
    }
}
