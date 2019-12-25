package br.fernando.ch05_IO_and_NIO_Objective.p07_tests;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test10 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Consider the directory structure shown in the attached image.
        // (context root is a directory, which contains two files Home.htm, index.html and a directory web-inf, which in turn contains a file web.xml)
        // How will you create a PathMatcher that will match web.xml, Home.htm, and index.html?

        // context root (web-inf(web.xml), Home.htm, index.html)

        PathMatcher pm = FileSystems.getDefault().getPathMatcher("glob:**.{htm*,xml}");

        // **.{htm*, xml} recursive extension .{htm* or xml}
        //

        pm.matches(Paths.get(""));
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // You need to recursively traverse through a given directory structure but under that directory structure, you need to traverse
        // sub directories only if the sub directory name starts with the name data.
        //
        // Which method of FileVisitor will be helpful in implementing this requirement?
        //
        // preVisitDirectory
        //
        // Explanation
        //
        // While walking a directory structure, the return value of preVisitDirectory determines if the files in that directory will be visited
        // or not. For example, the following code shows how this requirement can be implemented:
        //

        final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/data*");

        class MyFileChecker extends SimpleFileVisitor<Path> {

            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (matcher.matches(dir)) {
                    System.out.println("Will Visit files in " + dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    System.out.println("Will Skip files in " + dir);
                    return FileVisitResult.SKIP_SUBTREE;
                }
            }
        }

        MyFileChecker mfc = new MyFileChecker();
        Files.walkFileTree(Paths.get("c:\\works\\pathtest"), mfc);
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // You need to recursively traverse through a given directory structure. Some files in that directory structure may not be accessible
        // to your application because of insufficient access rights. In such a case, you want to skip visiting files and directories in
        // that sub directory. Which method of FileVisitor will be helpful in implementing this requirement?

        // visitFileFailed

        // In case of an error while visiting a file, this method is called. From this method,
        // if you return FileVisitResult.SKIP_SIBLINGS, none of the remaining files or subdirectories in that directory will be visited.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Which of the following statements are valid usages of StandardOpenOption options that determine how the file is opened?
        // You had to select three options.

        // 1º Option
        OpenOption[] op1 = new OpenOption[]{ StandardOpenOption.WRITE, StandardOpenOption.DSYNC };

        // 2º Option
        OpenOption[] op2 = new OpenOption[]{ StandardOpenOption.APPEND, StandardOpenOption.SYNC };

        // 3º Option
        OpenOption[] op3 = new OpenOption[]{ StandardOpenOption.READ, StandardOpenOption.SYNC };

        // Explanation
        // Observe that some combinations such as CREATE and READ do not make sense if put together and therefore an IllegalArgumentException
        // will be thrown in such cases. There are questions on the exam that expect you to know such combinations.
        //
        // if you want to truncate a file, then you must open it with an option that allows writing. Thus, READ and TRUNCATE_EXISTING
        // (or WRITE, APPEND, or DELETE_ON_CLOSE) cannot go together.  READ and SYNC (or DSYNC) cannot go together either because when a file is
        // opened for READ, there is nothing to synch, but on some platforms it works.

    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
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
    static void test01_06() throws Exception {
        // Given:
        Path p1 = Paths.get("c:\\a\\b\\c.java");

        // What will the code below return?

        System.out.println(p1.getName(2).toString());

        // c.java
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Which method do you need to implement if you want to implement your own java.nio.file.PathMatcher?

        // boolean matches(Path path)

        PathMatcher pm = new PathMatcher() {

            @Override
            public boolean matches(Path path) {
                return false;
            }
        };

        Path path = Paths.get("D:/cp/testFile.t");

        pm.matches(path);

        // it's an alternative to

        FileSystem fileSystem = FileSystems.getDefault();
        PathMatcher pathMatcher = fileSystem.getPathMatcher("glob:**.{java,class,txt}");
        pathMatcher.matches(path);
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
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
    static void test01_09() throws Exception {
        // Given:
        Path p1 = Paths.get("c:\\temp\\test1.txt");
        Path p2 = Paths.get("c:\\temp\\test2.txt");

        // Which of the following code fragments moves the file test1.txt to test2.txt, even if test2.txt exists?

        Files.move(p1, p2, StandardCopyOption.REPLACE_EXISTING);

        // and

        Files.copy(p1, p2, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(p1);
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // What will the following code do?

        class MyFileVisitor implements FileVisitor<Path> {

            private final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/t*");

            public FileVisitResult visitFile(Path p, BasicFileAttributes attr) {
                System.out.println("Visited " + p);
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (matcher.matches(dir)) {
                    return FileVisitResult.CONTINUE;
                } else {
                    return FileVisitResult.SKIP_SUBTREE;
                }
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        }

        MyFileVisitor mfv = new MyFileVisitor();
        Files.walkFileTree(Paths.get("c:\\temp"), mfv);

        // It prints the name of each file in c:\temp and visits each directory under c:\temp if the directory name starts 
        // with a t and prints the name of each file in the visited directory.
        //
        //
        // Explanation
        //
        // 1. The glob pattern glob:**/t* matches a path whose last component starts with a t. ** indicates that the Path may consist 
        // of multiple directories. Thus, it matches, c:\temp or c:\temp\test but not c:\temp\sample.
        //
        // 2. The path name is matched only in preVisitDirectory. So once it is decided that the directory will be visited, each 
        // file under that directory will be visited.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
