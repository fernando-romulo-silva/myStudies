package org.nandao.cap03.p11MaintainingUserDefinedFileAttributesUsingTheUserDeinedFileAttributeView;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserDefinedFileAttributeView;

// The java.nio.file.attribute.UserDefinedFileAttributeView interface permits
// the attachment of a non-standard attribute to a file or directory. These types of attributes are
// sometimes called extended attributes. Typically, a user-defined attribute stores metadata
// about a file. This data is not necessarily understood or used by the filesystem.
// These attributes are stored as a name/value pair. The name is a String and the
// value is stored as a ByteBuffer object. The size of this buffer should not exceed
// Integer.MAX_VALUE.
public class Test {
    public static void main(String[] args) throws Exception {
        test0();
        test1();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();

        final Path path = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

        try {
            final UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
            view.write("publishable", Charset.defaultCharset().encode("true"));
            System.out.println("Publishable set");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void test1() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();

        final Path path = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

        try {
            final UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
            view.write("publishable", Charset.defaultCharset().encode("true"));

            final String name = "publishable";
            final ByteBuffer buffer = ByteBuffer.allocate(view.size(name));
            view.read(name, buffer);
            buffer.flip();
            final String value = Charset.defaultCharset().decode(buffer).toString();
            System.out.println(value);
            
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
