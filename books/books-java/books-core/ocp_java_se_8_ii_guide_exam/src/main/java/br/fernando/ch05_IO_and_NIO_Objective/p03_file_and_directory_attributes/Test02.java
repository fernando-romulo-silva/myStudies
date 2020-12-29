package br.fernando.ch05_IO_and_NIO_Objective.p03_file_and_directory_attributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class Test02 {

    // =========================================================================================================================================
    // Types of Attribute Interfaces
    static void test01() throws IOException {

        // The attributes you set by calling methods on Files are the most straightforward ones.
        // Beyond that, Java NIO.2 added attribute interfaces so you could read attributes that
        // might not be on every operating system.

        // * BasicFileAttributes : Basic attributes include things like creation date
        //
        // * PosixFileAttributes : POSIX stands for Portable Operating System Interface.
        // This interface is implemented by both UNIX- and Linux-based operating
        // systems. You can remember this because POSIX ends in “x,” as do UNIX and Linux
        //
        // * DosFileAttributes : DOS stands for Disk Operating System. It is part of all
        // Windows operating systems.

        // There are also separate interfaces for setting or updating attributes. While the details
        // aren’t in scope for the exam, you should be familiar with the purpose of each one.

        // * BasicFileAttributeView : Used to set the last updated, last accessed, and creation dates.
        //
        // * PosixFileAttributeView : Used to set the groups or permissions on UNIX/Linux systems.
        // There is an easier way to set these permissions though, so you won’t be using the attribute view.
        //
        // * DosFileAttributeView : Used to set file permissions on DOS/Windows systems. Again, there is an
        // easier way to set these, so you won’t be using the attribute view.
        //
        // * FileOwnerAttributeView : Used to set the primary owner of a file or directory.
        //
        // * AclFileAttributeView : Sets more advanced permissions on a file or directory.
    }

    // =========================================================================================================================================
    // Working with BasicFileAttributes
    static void test02() throws IOException {
        Path path = Paths.get("c:/temp/file");
        Files.createFile(path); // create another file

        BasicFileAttributes basic = Files.readAttributes(path, BasicFileAttributes.class);

        System.out.println("create time: " + basic.creationTime());
        System.out.println("access time: " + basic.lastAccessTime());
        System.out.println("modify time: " + basic.lastModifiedTime());
        System.out.println("directory time: " + basic.isDirectory());

        // So far, you’ve noticed that all the attributes are read only. That’s because Java
        // provides a different interface for updating attributes. Let’s write code to update the last
        // accessed time:

        FileTime lastUpdated = basic.creationTime();

        FileTime created = basic.creationTime(); // values

        FileTime now = FileTime.fromMillis(System.currentTimeMillis());

        BasicFileAttributeView basicView = Files.getFileAttributeView(path, BasicFileAttributeView.class); // "view" this time

        basicView.setTimes(lastUpdated, now, created); // set all three

        // The key takeaways here are that the “ XxxFileAttributes ” classes are read only
        // and the “ XxxFileAttributeView ” classes allow updates.

        // PosixFileAttributes and DosFileAttributes inherit from BasicFileAttributes .
        // This means you can call Basic methods on a POSIX or DOS subinterface.
    }

    // =========================================================================================================================================
    // Working with DosFileAttributes
    static void test03() throws IOException {
        // Hidden files typically begin with a dot and don’t show up when you type dir to list the
        // contents of a directory. Read-only files are what they sound like—files that can’t be updated.
        // (The other two attributes are “archive” and “system,” which you are quite unlikely to ever use.)

        Path path = Paths.get("C:/test");
        Files.createFile(path); // create a file

        Files.setAttribute(path, "dos:hidden", true); // set a attribute

        Files.setAttribute(path, "dos:readonly", true);

        DosFileAttributes dos = Files.readAttributes(path, DosFileAttributes.class); // dos attributes

        System.out.println(dos.isHidden());
        System.out.println(dos.isReadOnly());

        Files.setAttribute(path, "dos:hidden", false);
        Files.setAttribute(path, "dos:readonly", false);

        dos = Files.readAttributes(path, DosFileAttributes.class); // get attributes again

        System.out.println(dos.isHidden());
        System.out.println(dos.isReadOnly());

        Files.delete(path);

        // The first tricky thing in this code is that the String “readonly” is lowercase even
        // though the method name is mixed case. If you forget and use the String “readOnly”,
        // an IllegalArgumentException will be thrown at runtime.
        //
        // The other tricky thing is that you cannot delete a read-only file. That’s why the code
        // calls setAttribute a second time with false as a parameter, to make it no longer
        // “read only” so the code can clean up after itself. And you can see that we had to call
        // readAttributes again to see those updated values.

        // It is good to know both ways, though.

        DosFileAttributeView dosView = Files.getFileAttributeView(path, DosFileAttributeView.class);
        dosView.setHidden(true);
        dosView.setReadOnly(true);
    }

    // =========================================================================================================================================
    // Working with PosixFileAttributes
    static void test04() throws IOException {
        // UNIX permissions are also more elaborate than the basic ones. Each file or
        // directory has nine permissions set in a String . A sample is “rwxrw-r--.” Breaking this
        // into groups of three, we have “rwx”, “rw-,” and “r--.” These sets of permissions
        // correspond to who gets them. In this example, the “user” (owner) of the file has read,
        // write, and execute permissions. The “group” only has read and write permissions.
        // UNIX calls everyone who is not the owner or in the group “other.” “Other” only has read access in this example.

        Path path = Paths.get("/tmp/test");

        Files.createFile(path); // get the Posix type

        PosixFileAttributes posix = Files.readAttributes(path, PosixFileAttributes.class);

        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-r--r--"); // Unix styel

        Files.setPosixFilePermissions(path, perms); // set permissions

        System.out.println(posix.permissions()); // get permissions

        System.out.println(posix.group()); // get group
    }

    // =========================================================================================================================================
    static void summary() throws Exception {
        Path path = Paths.get("c:/temp/file");
        Files.createFile(path); // create another file

        // The attributes you set by calling methods on Files are the most straightforward ones.
        // Beyond that, Java NIO.2 added attribute interfaces so you could read attributes that
        // might not be on every operating system.

        // * BasicFileAttributes : Basic attributes include things like creation date
        //
        // * PosixFileAttributes : POSIX stands for Portable Operating System Interface.
        // This interface is implemented by both UNIX- and Linux-based operating
        // systems. You can remember this because POSIX ends in “x,” as do UNIX and Linux
        //
        // * DosFileAttributes : DOS stands for Disk Operating System. It is part of all
        // Windows operating systems.

        // There are also separate interfaces for setting or updating attributes. While the details
        // aren’t in scope for the exam, you should be familiar with the purpose of each one.

        // * BasicFileAttributeView : Used to set the last updated, last accessed, and creation dates.
        //
        // * PosixFileAttributeView : Used to set the groups or permissions on UNIX/Linux systems.
        // There is an easier way to set these permissions though, so you won’t be using the attribute view.
        //
        // * DosFileAttributeView : Used to set file permissions on DOS/Windows systems. Again, there is an
        // easier way to set these, so you won’t be using the attribute view.
        //
        // * FileOwnerAttributeView : Used to set the primary owner of a file or directory.
        //
        // * AclFileAttributeView : Sets more advanced permissions on a file or directory.

        // ************************************************************************************************
        // BasicFileAttributes - Basic file attributes.
        // ************************************************************************************************
        BasicFileAttributes basicFileAttributes01 = Files.readAttributes(path, BasicFileAttributes.class);

        System.out.println("create time: " + basicFileAttributes01.creationTime());
        System.out.println("access time: " + basicFileAttributes01.lastAccessTime());
        System.out.println("modify time: " + basicFileAttributes01.lastModifiedTime());
        System.out.println("directory time: " + basicFileAttributes01.isDirectory());

        // ************************************************************************************************
        // BasicFileAttributeView - Used to set the last updated, last accessed, and creation dates
        // ************************************************************************************************
        BasicFileAttributeView basicView = Files.getFileAttributeView(path, BasicFileAttributeView.class); // "view" this time
        basicView.readAttributes();

        // ************************************************************************************************
        // DosFileAttributes - Win file attributes.
        // ************************************************************************************************
        Files.setAttribute(path, "dos:hidden", true); // set a attribute
        Files.setAttribute(path, "dos:readonly", true);

        // or

        DosFileAttributes dosAttributes = Files.readAttributes(path, DosFileAttributes.class); // dos attributes

        // don't have "set" methods
        System.out.println(dosAttributes.isHidden());
        System.out.println(dosAttributes.isReadOnly());

        // ************************************************************************************************
        // DosFileAttributeView
        // ************************************************************************************************
        DosFileAttributeView dosView = Files.getFileAttributeView(path, DosFileAttributeView.class);
        dosView.setHidden(true);
        dosView.setReadOnly(true);

        // ************************************************************************************************
        // PosixFileAttributes
        // ************************************************************************************************
        Files.getAttribute(path, "posix:permissions");
        Files.getAttribute(path, "posix:owner");
        Files.getAttribute(path, "posix:group");

        // or

        PosixFileAttributes posix = Files.readAttributes(path, PosixFileAttributes.class);

        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-r--r--"); // Unix styel

        Files.setPosixFilePermissions(path, perms); // set permissions

        System.out.println(posix.permissions()); // get permissions

        System.out.println(posix.group()); // get group

        // All Attributes
        //
        // The following attributes are supported by BasicFileAttributeView:
        // Name ----------------------Type
        //
        // "lastModifiedTime"---------FileTime
        // "lastAccessTime"-----------FileTime
        // "creationTime"-------------FileTime
        // "size"---------------------Long
        // "isRegularFile"------------Boolean
        // "isDirectory---------------Boolean
        // "isSymbolicLink"-----------Boolean
        // "isOther"------------------Boolean
        // "fileKey"------------------Object

        // Attributes supported by DosFileAttributeView (which extends BasicFileAttributeView) are:
        // "readOnly"-----------------Boolean
        // "hidden"-------------------Boolean
        // "system"-------------------Boolean
        // "archive"------------------Boolean

        // Attributes supported by PosixFileAttributeView (which extends BasicFileAttributeView) are:
        // "permissions"--------------Boolean
        // "owner"--------------------Boolean
        // "group"--------------------Boolean
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws IOException {
        test01();
    }
}
