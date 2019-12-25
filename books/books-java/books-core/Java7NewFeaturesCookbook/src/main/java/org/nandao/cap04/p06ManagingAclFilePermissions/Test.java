package org.nandao.cap04.p06ManagingAclFilePermissions;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.List;
import java.util.Set;

// In this recipe, we will examine how ACL permissions can be set. The ability to set these
// permissions is important for many applications. For example, when we need to control who
// can modify or execute a file, we can affect this change programmatically. What we can change
// is indicated by the AclEntryPermission enumeration values listed later.
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home/Docs").getFile()).getAbsolutePath());

        Path path = Paths.get(pathSource + "/users.txt");

        AclFileAttributeView view = Files.getFileAttributeView(path, AclFileAttributeView.class);

        List<AclEntry> aclEntryList = view.getAcl();

        displayAclEntries(aclEntryList);

        //-----------------------------------------------

        final UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
        final UserPrincipal userPrincipal = lookupService.lookupPrincipalByName("users");

        final AclEntry.Builder builder = AclEntry.newBuilder();
        builder.setType(AclEntryType.ALLOW);
        builder.setPrincipal(userPrincipal);
        builder.setPermissions(AclEntryPermission.WRITE_ACL, AclEntryPermission.DELETE);
        
    }

    private static void displayAclEntries(List<AclEntry> aclEntryList) {
        System.out.println("ACL Entry List size: " + aclEntryList.size());

        for (AclEntry entry : aclEntryList) {
            System.out.println("User Principal Name: " + entry.principal().getName());
            System.out.println("ACL Entry Type: " + entry.type());
            displayEntryFlags(entry.flags());
            displayPermissions(entry.permissions());
            System.out.println();
        }

    }

    private static void displayPermissions(Set<AclEntryPermission> permissionSet) {
        if (permissionSet.isEmpty()) {
            System.out.println("No Permissions present");
        } else {
            System.out.println("Permissions");
            for (AclEntryPermission permission : permissionSet) {
                System.out.print(permission.name() + " ");
            }
            System.out.println();
        }
    }

    private static void displayEntryFlags(Set<AclEntryFlag> flagSet) {
        if (flagSet.isEmpty()) {
            System.out.println("No ACL Entry Flags present");
        } else {
            System.out.println("ACL Entry Flags");
            for (AclEntryFlag flag : flagSet) {
                System.out.print(flag.name() + " ");
            }
            System.out.println();
        }
    }
}
