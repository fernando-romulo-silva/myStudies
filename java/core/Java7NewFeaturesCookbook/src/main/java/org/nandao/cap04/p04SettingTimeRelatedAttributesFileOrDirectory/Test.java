package org.nandao.cap04.p04SettingTimeRelatedAttributesFileOrDirectory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;

// The timestamp for a file can be critical for some applications. For example, the order in which
// operations execute may be dependent on the time a file was last updated. There are three
// dates supported by the BasicFileAttributeView:
//
// 1 The last modified time
// 2 The last access time
// 3 The creation time
//
// They can be set using the BasicFileAttributeView interface's setTimes method. As we
// will see in the There's more... section, the Files class can be used to set or get only the last
// modified time.
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home/Docs").getFile()).getAbsolutePath());

        Path path = Paths.get(pathSource + "/users.txt");

        BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);

        BasicFileAttributes attributes = view.readAttributes();

        FileTime lastModifedTime = attributes.lastModifiedTime();

        FileTime createTime = attributes.creationTime();

        long currentTime = Calendar.getInstance().getTimeInMillis();

        FileTime lastAccessTime = FileTime.fromMillis(currentTime);

        view.setTimes(lastModifedTime, lastAccessTime, createTime);
        System.out.println(attributes.lastAccessTime());
    }

    // Understanding the FileTime class
    //
    //    The java.nio.file.attribute.FileTime class represents the time for use with several
    //    of the java.nio package methods. To create a FileTime object, we need to use either of
    //    the following two static FileTime methods:
    //
    //    1 The from method, which accepts a long number representing a duration and a
    //    TimeUnit object representing a unit of time measurement
    //
    //    2 The fromMillis method, which accepts a long argument representing the number of milliseconds based on the epoch
    //
    //    TimeUnit is an enumeration found in the java.util.concurrent package. It represents
    //    a time duration as defined in the following table. 
    public static void test1() throws Exception {

    }
}
