package br.fernando.ch05_IO_and_NIO_Objective.p07_tests;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test06 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // A WatchKey can be in which of the following states?

        // Ready
        // Signaled

        // The states can be:
        // Ready
        // Singaled
        // Invalid
        // Overflow
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // You need to recursively traverse through a given directory structure. Some files in that directory structure may not be accessible
        // to your application because of insufficient access rights. In such a case, you want to skip visiting files and directories in
        // that sub directory. Which method of FileVisitor will be helpful in implementing this requirement?

        // visitFileFailed

        // In case of an error while visiting a file, this method is called. From this method,
        // if you return FileVisitResult.SKIP_SIBLINGS, none of the remaining files or subdirectories in that directory will be visited.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // You want to walk through a directory structure recursively. Which interface do you need to implement for this purpose and which
        // class can you extend from to avoid implementing all the methods of the interface?

        // FileVisitor and SimpleFileVisitor
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Which of the following code fragments correctly prints all the roots of the default file system?
        // You had to select 2 options:
        //
        // 1º Option
        File[] roots01 = File.listRoots();
        for (File f : roots01) {
            System.out.println(f);
        }

        // 2º Option
        Iterable<Path> roots02 = FileSystems.getDefault().getRootDirectories();
        for (Path p : roots02) {
            System.out.println(p);
        }

        // Explanation
        // To find all the roots of the default file system, you first need to get the default FileSystem. This is accomplished using:

        FileSystem fs = FileSystems.getDefault();

        Iterable<Path> rootDirectories = fs.getRootDirectories();

        // Next, use the FileSystem.getRootDirectories() method to get all the roots. Note that getRootDirectories() returns an Iterable<Path> object
        // and not an Iterator<Path> object.
        // You can get an Iterator from an Iterable by calling Iterable.iterator() method and then iterate using the Iterator.

        Iterator<Path> it = rootDirectories.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
        // However, if you want to use the enhanced for loop (also called as foreach loop) to go over each element, you don't need an Iterator.
        // Iterable is all you need.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Given that test1.txt exists but test2.txt doesn't exist, consider the following code?

        Path p1 = Paths.get("c:\\temp\\test1.txt");
        Path p2 = Paths.get("c:\\temp\\test2.txt");

        Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES);

        // Copying of the attributes is platform and system dependent.
        //
        // Attempts to copy the file attributes associated with source file to the target file.
        //
        // The exact file attributes that are copied is platform and file system dependent and therefore unspecified.
        // Minimally, the last-modified-time is copied to the target file if supported by both the source and target file store.
        // Copying of file timestamps may result in precision loss.

        // Files.copy method will copy the file test1.txt into test2.txt. If test2.txt doesn't exist, it will be created.

        // The options parameter may include any of the following:
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // You want to retrieve a WatchKey from a WatchService such that if no key is available, you want to wait for at most 1 minute.
        // Which of the following statements will you use?

        WatchService watchService = FileSystems.getDefault().newWatchService();

        WatchKey key = watchService.poll(1, TimeUnit.MINUTES);
        //
        //
        // Explanation
        // WatchService has two types of methods for retrieving the WatchKeys - poll and take. The two poll methods return null if no key
        // is present and do not wait for ever. The take method doesn't return null but keeps waiting until a key is available.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // What will the following code fragment print?

        Path p1 = Paths.get("photos\\goa");
        Path p2 = Paths.get("\\index.html");
        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        // Note that if one path has a root (for example, if a path starts with a // or c:) and the other does not, relativize cannot work
        // and it will throw an IllegalArgumentException.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Consider the directory structure shown in the attached image.
        // (context root is a directory, which contains two files Home.htm, index.html and a directory web-inf, which in turn contains a file web.xml)
        // How will you create a PathMatcher that will match web.xml, Home.htm, and index.html?

        // context root (web-inf(web.xml), Home.htm, index.html)

        PathMatcher pm = FileSystems.getDefault().getPathMatcher("glob:**.{htm*,xml}");

        // **.{htm*, xml} recursive extension .{htm* ou xml}
        //

        pm.matches(Paths.get(""));
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Consider the following code and the directory structure of c:\works\pathtest shown in the image.

        // pathtest(dir1(c.java, dir12(d.java)), dir2(b.java), a.java)

        class MyFileChecker extends SimpleFileVisitor<Path> {

            private final PathMatcher matcher;

            public MyFileChecker() { //** ATENTION **   
                matcher = FileSystems.getDefault().getPathMatcher("glob:*.java");
            }

            void check(Path p) {
                //** ATENTION ** 
                if (p != null && matcher.matches(p)) {
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

        // Assuming appropriate imports, what will be the output when this program is run?

        // 0
        //
        // Observe that in the glob pattern we have used a single * (which means it won't cross directory boundaries) and the Path objects 
        // that we are checking contain the complete path including directories (such as c:\works\pathtest\a.java and not just a.java).
        //
        // Therefore, nothing will match. We should either get a path with just the last name using Path lastName = p.getFileName(p) 
        // or change the glob pattern to **.java.
    }

    static int count;

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Which of the following statements are valid usages of StandardOpenOption options that control how the file is opened?

        OpenOption[] options01 = new OpenOption[]{ StandardOpenOption.WRITE, StandardOpenOption.DELETE_ON_CLOSE };

        OpenOption[] options02 = new OpenOption[]{ StandardOpenOption.READ, StandardOpenOption.DELETE_ON_CLOSE };

        OpenOption[] options03 = new OpenOption[]{ StandardOpenOption.DELETE_ON_CLOSE, StandardOpenOption.TRUNCATE_EXISTING };
        // This is a valid combination but will throw java.nio.file.NoSuchFileException if the file does not exist.

        OpenOption[] options04 = new OpenOption[]{ StandardOpenOption.READ, StandardOpenOption.SYNC };
        // Ideally, this should be an invalid combination (because when a file is opened for READ, there is nothing to synch) but it works.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
