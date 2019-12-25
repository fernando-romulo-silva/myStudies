package org.nandao.cap03.p08MaintainingFatTableAttributesUsingTheDosFileAttributeView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;

// The java.nio.file.attribute.DosFileAttributeView is concerned with the older
// Disk Operating System (DOS) files. It has limited value on most computers today. However,
// this is the only interface that can be used to determine if a file is marked for archive or is a
// system file.
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();

        final Path path = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

        try {
            
            final DosFileAttributeView view = Files.getFileAttributeView(path, DosFileAttributeView.class);
            final DosFileAttributes attributes = view.readAttributes();
            
            System.out.println("isArchive: " + attributes.isArchive());
            System.out.println("isHidden: " + attributes.isHidden());
            System.out.println("isReadOnly: " + attributes.isReadOnly());
            System.out.println("isSystem: " + attributes.isSystem());
            
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }
}
