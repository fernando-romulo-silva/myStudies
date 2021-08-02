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
import java.nio.file.attribute.BasicFileAttributes;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test09 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following attributes are supported by BasicFileAttributeView?

        // isDirectory
        // size
        // creationTime

        // Explanation
        // The following attributes are supported by BasicFileAttributeView:
        //
        // Name ----------------------Type
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
    }

    // =========================================================================================================================================
    static class PathTest {

        static Path p1 = Paths.get("c:\\a\\b\\c");

        public static String getValue() {
            String x = p1.getName(1).toString();
            String y = p1.subpath(1, 2).toString();
            return x + " : " + y;
        }
    }

    static void test01_02() throws Exception {
        // What will the following code print when run?

        System.out.println(PathTest.getValue());

        // b : b
        //
        // Explanation
        // Remember the following points about Path.subpath(int beginIndex, int endIndex)
        //
        // 1. Indexing starts from 0. 
        //
        // 2. Root (i.e. c:\) is not considered as the beginning. 
        //
        // 3. name at beginIndex is included but name at endIndex is not. 
        //
        // 4. paths do not start or end with \.
        //
        // Thus, if your path is "c:\\a\\b\\c",
        //
        // subpath(1,1) will cause IllegalArgumentException to be thrown. 
        //
        // subpath(1,2) will correspond to b. 
        //
        // subpath(1,3) will correspond to b/c.
        //
        // Remember the following 4 points about Path.getName() method :
        //
        // 1. Indices for path names start from 0.
        //
        // 2. Root (i.e. c:\) is not included in path names.
        //
        // 3. \ is NOT a part of a path name.
        //
        // 4. If you pass a negative index or a value greater than or equal to the number of elements, or this path has zero name elements,
        // java.lang.IllegalArgumentException is thrown. It DOES NOT return null.
        //
        // Thus, for example, If your Path is "c:\\code\\java\\PathTest.java", p1.getRoot() is c:\  ((For Unix based environments, the root is usually / ):
        // p1.getName(0) is code
        // p1.getName(1) is java
        // p1.getName(2) is PathTest.java
        // p1.getName(3) will cause IllegalArgumentException to be thrown.
    }

    // =========================================================================================================================================
    static int count;

    static void test01_03() throws Exception {
        // Consider the following code and the directory structure of c:\works\pathtest shown in the image.

        // pathtest(dir1(c.java, dir12(d.java)), dir2(b.java), a.java)

        class MyFileChecker extends SimpleFileVisitor<Path> {

            private final PathMatcher matcher;

            public MyFileChecker() { // ** ATENTION **
                matcher = FileSystems.getDefault().getPathMatcher("glob:*.java");
            }

            void check(Path p) {
                // ** ATENTION **
                Path name = p.getFileName();
                if (p != null && matcher.matches(name)) {
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

        // 4
        //
        // Note that Files.walkFileTree method will cause each subdirectory under the given directory to be travelled.
        // For each file in each directory, the FileVisitor's visitFile will be invoked.
        // This particular visitor simply tries to match the full file name with the given glob pattern.
        // The glob pattern will match only the files ending with .java.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // What will the following code print when run?

        System.out.println(getRoot());

        // c:\

        // Explanation 
        // Path getRoot()
        // Returns the root component of this path as a Path object, or null if this path does not have a root component.
        //
        // Path getName(int index) 
        // Returns a name element of this path as a Path object. The index parameter is the index of the name element to return. 
        // The element that is closest to the root in the directory hierarchy has index 0. The element that is farthest from the root has index count-1.

        // for example, If your Path is "c:\\code\\java\\PathTest.java",  
        // p1.getRoot()  is c:\  ((For Unix based environments, the root is usually / ). 
        // p1.getName(0)  is code 
        // p1.getName(1)  is java 
        // p1.getName(2)  is PathTest.java 
        // p1.getName(3)  will cause IllegalArgumentException to be thrown.
    }

    static Path p1 = Paths.get("c:\\main\\project\\Starter.java");

    public static String getRoot() {
        String root = p1.getRoot().toString();
        return root;
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Which of the following File Attribute views support manipulating the owner of a file?

        // PosixFileAttributeView

        // Unix based file systems provide this view. This view supports the following attributes in addition to BasicFileAttributeView:
        // "permissions" : Set<PosixFilePermission>
        // "group" : GroupPrincipal

        // The permissions attribute is used to update the permissions, owner, or group-owner as by invoking the setPermissions,
        // setOwner, and setGroup methods respectively.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Which of the following statements are valid usages of StandardOpenOption options that control how the file is opened?

        OpenOption[] options01 = new OpenOption[]{ StandardOpenOption.WRITE, StandardOpenOption.DELETE_ON_CLOSE };

        OpenOption[] options02 = new OpenOption[]{ StandardOpenOption.READ, StandardOpenOption.DELETE_ON_CLOSE };

        OpenOption[] options03 = new OpenOption[]{ StandardOpenOption.DELETE_ON_CLOSE, StandardOpenOption.TRUNCATE_EXISTING };
        // This is a valid combination but will throw java.nio.file.NoSuchFileException if the file does not exist.

        OpenOption[] options04 = new OpenOption[]{ StandardOpenOption.READ, StandardOpenOption.SYNC };
        // Ideally, this should be an invalid combination (because when a file is opened for READ, there is nothing to synch) but it works.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Which interface do you need to implement if you want to recursively walk a directory tree?

        // FileVisitor

        // Explanation
        // java.nio.file.FileVisitor is an important interface for the exam. It declares the following methods:
        //
        // FileVisitResult postVisitDirectory(T dir, IOException exc) : Invoked for a directory after entries in the directory, and all of
        // their descendants, have been visited.
        //
        // FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs) : Invoked for a directory before entries in the directory are visited.
        //
        // FileVisitResult visitFile(T file, BasicFileAttributes attrs) : Invoked for a file in a directory.
        //
        // FileVisitResult visitFileFailed(T file, IOException exc) : Invoked for a file that could not be visited.
        //
        //
        // java.nio.file.SimpleFileVisitor is a basic implementation class for the above interface.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // You need to recursively traverse through a given directory structure. Some files in that directory structure may not be accessible
        // to your application because of insufficient access rights. In such a case, you want to skip visiting files and directories in
        // that sub directory. Which method of FileVisitor will be helpful in implementing this requirement?

        // visitFileFailed

        // In case of an error while visiting a file, this method is called. From this method,
        // if you return FileVisitResult.SKIP_SIBLINGS, none of the remaining files or subdirectories in that directory will be visited.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Which of the following statements will print /test.txt when executed on a *nix system?

        System.out.println(Paths.get("/", "test.txt"));

        // Note that this will throw an exception on Windows. It is not clear if the exam explicitly mentions the platform on which the code 
        // is run in the problem statement of questions that depend on platform specifics.
        //
        // In case it does not, our recommendation is to assume that the code is run on a *nix system unless the paths used in the code are 
        // clearly for a windows system (such as paths starting with c:\> ).
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Given:
        Path p = Paths.get("c:\\temp\\test.txt");

        // Which of the following statements will make the file test.txt hidden?

        Files.setAttribute(p, "dos:hidden", true);
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
