package br.fernando.ch05_IO_and_NIO_Objective.p07_tests;

import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test05 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following attributes are supported by BasicFileAttributeView?

        // lastModifiedTime
        // isSymbolicLink

        // Explanation
        // The following attributes are supported by BasicFileAttributeView:
        // "lastModifiedTime"
        // "lastAccessTime"
        // "creationTime"
        // "size"
        // "isRegularFile"
        // "isDirectory"
        // "isSymbolicLink"
        // "isOther"
        // "fileKey"

    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // What will the following code print when run?

        Path p1 = Paths.get("c:\\code\\java\\PathTest.java");
        System.out.println(p1.getName(3).toString());

        // It will throw IllegalArgumentException

        // Explanation
        //
        // Thus, for example, If your Path is "c:\\code\\java\\PathTest.java",
        //
        // p1.getRoot() is c:\  ((For Unix based environments, the root is usually / ).
        // p1.getName(0) is code
        // p1.getName(1) is java
        // p1.getName(2) is PathTest.java
        // p1.getName(3) will cause IllegalArgumentException to be thrown.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // You want to walk through a directory structure recursively. Which interface do you need to implement for this purpose and which class
        // can you extend from to avoid implementing all the methods of the interface?

        // FileVisitor and SimpleFileVisitor
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // What is the state of the WatchKey at the end of the following code?

        Path path = Paths.get("C:/temp");
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
        WatchKey key = watchService.take(); // status of key here

        // Signaled

        // When initially created the key is said to be ready. When an event is detected then the key is signaled and queued so that it can be
        // retrieved by invoking the watch service's poll or take methods. Once signalled, a key remains in this state until its reset method is
        // invoked to return the key to the ready state.

    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Which of the following statements are valid usages of StandardOpenOption options that control how the file is opened?

        OpenOption[] op01 = new OpenOption[]{ StandardOpenOption.WRITE, StandardOpenOption.DELETE_ON_CLOSE }; // ok

        OpenOption[] op02 = new OpenOption[]{ StandardOpenOption.READ, StandardOpenOption.DELETE_ON_CLOSE }; // ok

        OpenOption[] op03 = new OpenOption[]{ StandardOpenOption.DELETE_ON_CLOSE, StandardOpenOption.TRUNCATE_EXISTING };
        // TRUNCATE_EXISTING : If the file already exists and it is opened for WRITE access, then its length is truncated to 0.
        // if you want to truncate a file, then you must open it with an option that allows writing.
        // Thus, READ and TRUNCATE_EXISTING (or WRITE, APPEND, or DELETE_ON_CLOSE) should not go together.

        OpenOption[] op04 = new OpenOption[]{ StandardOpenOption.READ, StandardOpenOption.SYNC };
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Which of the following File Attribute views support manipulating the owner of a file?

        // PosixFileAttributeView

        // Unix based file systems provide this view. This view supports the following attributes in addition to BasicFileAttributeView:
        // "permissions" : Set<PosixFilePermission>
        // "group" : GroupPrincipal
        //
        // The permissions attribute is used to update the permissions, owner, or group-owner as by invoking the setPermissions,
        // setOwner, and setGroup methods respectively.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Assuming that the directory /works/ocpjp/code exists but /works/ocpjp/code/sample does not exist, what will the following code output?

        Path d1 = Paths.get("/works");
        Path d2 = d1.resolve("ocpjp/code"); // 1

        d1.resolve("ocpjp/code/sample"); // 2

        d1.toAbsolutePath(); // 3

        System.out.println(d1);
        System.out.println(d2);

        // \works
        // \works\ocpjp\code
        //
        // 1. The first println for d1 is straight forward.
        //
        // 2. d2 is created by resolving "ocpjp/code" with "/works". Since "ocpjp/code" is a relative path, d2 will contain "/works/ocpjp/code"

        // Explanation
        // 1. Path operations don't really care about whether the path actually exists on the file system. A Path object just represents an
        // abstract path that may or may not exist. It is only when you actually try to create or write something to the given path that its
        // existence is required.
        //
        // 2. As per JavaDoc description of Path interface, its implementations are immutable and safe for use by multiple concurrent
        // threads. This implies that the operations such as resolve and toAbsolutePath don't change the Path object itself.
        // They return a new Path object.
        //
        // In this case, lines //2 and //3 will actually produce Path objects representing "\works\ocpjp\code\sample"
        // and "C:\works" respectively. (Assuming that C:\ is the root of the file system.)
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // What will the following code fragment print?
        Path p1 = Paths.get("photos\\goa");
        Path p2 = Paths.get("\\index.html");
        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        // java.lang.IllegalArgumentException will be thrown

        // Note that if one path has a root (for example, if a path starts with a // or c:) and the other does not, relativize cannot work 
        // and it will throw an IllegalArgumentException.

    }

    // =========================================================================================================================================

    static int count;

    static void test01_09() throws Exception {
        // Consider the following code and the directory structure of c:\works\pathtest shown in the image.

        // pathtest(dir1(c.java, dir12(d.java)), dir2(b.java), a.java)

        class MyFileChecker extends SimpleFileVisitor<Path> {

            private final PathMatcher matcher;

            public MyFileChecker() { //** ATTENTION ** 
                matcher = FileSystems.getDefault().getPathMatcher("glob:**.java");
            }

            void check(Path p) {
                Path name = p.getFileName(); // * Attention *
                if (name != null && matcher.matches(name)) {
                    count++;
                }
            }

            public int getCount() {
                return count;
            }

            public FileVisitResult visitFile(Path p, BasicFileAttributes attr) {
                check(p);
                return FileVisitResult.CONTINUE;
            }
        }

        MyFileChecker mfc = new MyFileChecker();
        Files.walkFileTree(Paths.get("c:\\works\\pathtest"), mfc);
        System.out.println(mfc.getCount());

        // 4

        // Note that Files.walkFileTree method will cause each subdirectory under the given directory to be travelled. 
        // For each file in each directory, the FileVisitor's visitFile will be invoked. This particular visitor simply tries to 
        // match the full file name with the given glob pattern. 
        // The glob pattern will match only the files ending with .java.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Which of the following statements is true about DSYNC constant defined in StandardOpenOption enum?

        // DSYNC keeps only the file content and not the file meta data synchronized with the underlying storage device.

        // D in DSYNC is for data. When you open a file with this attribute, it means that every update to the file's content will be written 
        // synchronously to the underlying storage device. The meta data may still remain unsynchronized. 
        // In other words, any change in the data of the file will be written to the storage device synchronously, but any change in the meta 
        // data may be batched and written to the storage device later. Thus, it makes file operations a slower as compared to when you open 
        // the file without any option.
        //
        // SYNC makes sure that both - the data and meta data are synchronized with the storage device. 
        // Thus, it makes files operations even slower than DSYNC option.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
