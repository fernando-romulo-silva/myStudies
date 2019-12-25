package org.nandao.cap03.p10MaintainingFileACLUsingclFileAttributeView;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.List;
import java.util.Set;

// The java.nio.file.attribute.AclFileAttributeView interface provides access
// to ACL attributes of a file or directory. These attributes include the user principal, the type of
// attribute, and flags and permissions for the file. The ability to use this interface allows the user
// to determine what permissions are available and to modify these attributes.
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();

        final Path path = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

        try {

            // AclFileAttributeView is supported on Windows & Solaris and doesn't work on Linux
            // Check before if support ACL attribute View

            System.out.println(Files.getFileStore(path).supportsFileAttributeView(AclFileAttributeView.class));

            final AclFileAttributeView view = Files.getFileAttributeView(path, AclFileAttributeView.class);
            final List<AclEntry> aclEntryList = view.getAcl();
            for (final AclEntry entry : aclEntryList) {
                System.out.println("User Principal Name: " + entry.principal().getName());
                System.out.println("ACL Entry Type: " + entry.type());
                displayEntryFlags(entry.flags());
                displayPermissions(entry.permissions());
                System.out.println();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void test1() throws Exception {
        try {

            final UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
            final GroupPrincipal groupPrincipal = lookupService.lookupPrincipalByGroupName("Administrators");
            final UserPrincipal userPrincipal = lookupService.lookupPrincipalByName("Richard");
            System.out.println(groupPrincipal.getName());
            System.out.println(userPrincipal.getName());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayEntryFlags(Set<AclEntryFlag> flagSet) {
        if (flagSet.isEmpty()) {
            System.out.println("No ACL Entry Flags present");
        } else {
            System.out.println("ACL Entry Flags");
            for (final AclEntryFlag flag : flagSet) {
                System.out.print(flag.name() + " ");
            }
            System.out.println();
        }
    }

    private static void displayPermissions(Set<AclEntryPermission> permissionSet) {
        if (permissionSet.isEmpty()) {
            System.out.println("No Permissions present");
        } else {
            System.out.println("Permissions");
            for (final AclEntryPermission permission : permissionSet) {
                System.out.print(permission.name() + " ");
            }
            System.out.println();
        }
    }
}
