package br.fernando.ch05_IO_and_NIO_Objective.p07_tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;

public class Test03 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // What will the following code fragment print?

        Path p1 = Paths.get("\\personal\\readme.txt");
        Path p2 = Paths.get("\\index.html");
        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        // ..\..\index.html

        // Observe that if you append this path to p1, you will get p2. Therefore, this is the right answer. p1 + ..\..\index.html
        // =>\personal\readme.txt + ..\..\index.html
        // =>\personal + ..\index.html
        // =>\index.html

        // A ".." implies parent folder, therefore imagine that you are taking off one ".." from the right side of the plus sign and removing
        // the last name of the path on the left side of the plus sign.
        // For example, .. appended to personal makes it personal\.., which cancels out.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // You need to traverse through a given directory. After visiting the directory, you want to print the number of files visited in that directory.
        // Which three methods of FileVisitor will be helpful in implementing this requirement?

        // postVisitDirectory
        // visitFile
        // preVisitDirectory

    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // You have a file named customers.dat in c:\company\records directory. You want to copy all the lines in this file to another
        // file named clients.dat in the same directory and you have the following code to do it:

        Path p1 = Paths.get("c:\\company\\records\\customers.dat");

        // LINE 20 - INSERT CODE HERE

        Path p2 = null;

        try (BufferedReader br = new BufferedReader(new FileReader(p1.toFile())); BufferedWriter bw = new BufferedWriter(new FileWriter(p2.toFile()))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Which of the following options can be inserted independent of each other at //LINE 20 to make it work?
        // Assume that the current directory for the program when it runs is c:\code.

        // Path p2 = p1.resolveSibling("clients.dat");

        // or

        // Path p2 = Paths.get("c:", p1.subpath(0, 2).toString(), "clients.dat");

    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // What will the following code fragment print?
        Path p1 = Paths.get("photos\\..\\beaches\\.\\calangute\\a.txt");

        Path p2 = p1.normalize();
        System.out.println(p2);

        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        Path p4 = p2.relativize(p1);
        System.out.println(p4);

        System.out.println(p1.getNameCount() + " " + p2.getNameCount() + " " + p3.getNameCount() + " " + p4.getNameCount());

        // 6 3 9 9

        // 1. p1 has 6 components and so p1.getNameCount() will return 6.
        //
        // 2. normalize applies all the .. and . contained in the path to the path. Therefore, p2 contains beaches\calangute\a.txt,
        // that is 3 components.
        //
        // 3. p3 contains ..\..\..\..\..\..\beaches\calangute\a.txt, that is 9 components.
        //
        // 4. p4 contains ..\..\..\photos\..\beaches\.\calangute\a.txt, that is 9 components.
        //
        // You need to understand how relativize works for the purpose of the exam. The basic idea of relativize is to determine a path, which,
        // when applied to the original path will give you the path that was passed.
        //
        // For example, "a/b" relativize "c/d" is "../../c/d" because if you are in directory b, you have to go two steps back and then one step
        // forward to c and another step forward to d to be in d. However, "a/c" relativize "a/b" is "../b" because you have to go only one step
        // back to a and then one step forward to b.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Complete the following code fragment so that it will print owner's name of a file:

        Path path = Paths.get("c:\\temp\\test.txt");
        // INSERT CODE HERE
        PosixFileAttributeView pfav = Files.getFileAttributeView(path, PosixFileAttributeView.class);
        PosixFileAttributes attrs = pfav.readAttributes();

        // Pay Attention with attrs."property", don't use java beans pattern
        String ownername = attrs.owner().getName();

        System.out.println(ownername);

        // Ex:
        attrs.creationTime();
        attrs.group();
        // etc

        // But we have:
        attrs.isDirectory();
        attrs.isSymbolicLink();
        attrs.isOther();
        attrs.isRegularFile();
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Consider the following code and the directory structure of c:\works\pathtest shown in the image.

        class MyFileChecker extends SimpleFileVisitor<Path> {

            private final PathMatcher matcher;

            public MyFileChecker() { // * Attention *
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

        // pathtest(dir1(c.java, dir12(d.java)), dir2(b.java), a.java)

        MyFileChecker mfc = new MyFileChecker();
        Files.walkFileTree(Paths.get("c:\\works\\pathtest"), mfc);
        System.out.println(mfc.getCount());

        // 4

        // Note that Files.walkFileTree method will cause each subdirectory under the given directory to be travelled.
        // For each file in each directory, the FileVisitor's visitFile will be invoked. This particular visitor simply tries to
        // match the full file name with the given glob pattern.
        // The glob pattern will match only the files ending with .java.
    }

    static int count;

    // =========================================================================================================================================
    static void test01_07() throws Exception {
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

    static private class PathTest {

        static Path p1 = Paths.get("c:\\a\\b\\c");

        public static String getValue() {
            String x = p1.getName(1).toString();
            String y = p1.subpath(1, 2).toString();
            return x + " : " + y;
        }
    }

    static void test01_08() throws Exception {
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
    static void test01_09() throws Exception {
        // What will the following code fragment print?
        Path p1 = Paths.get("c:\\personal\\.\\photos\\..\\readme.txt");
        Path p2 = Paths.get("c:\\personal\\index.html");
        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        // ..\..\..\..\index.html
        //
        // Observe that if you append this path to p1, you will get p2. Therefore, this is the right answer.  p1 + ..\..\..\..\index.html
        // =>c:\\personal\\.\\photos\\..\\readme.txt + ..\..\..\..\index.html
        // =>c:\\personal\\.\\photos\\.. + ..\..\..\index.html
        // =>c:\\personal\\.\\photos + ..\..\index.html
        // =>c:\\personal\\. + ..\index.html
        // =>c:\\personal + index.html =>c:\\personal\index.html

        // A ".." implies parent folder, therefore imagine that you are taking off one ".." from the right side of the plus sign and
        // removing the last name of the path on the left side of the plus sign.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Which of the following code fragments allow you to hide the file test.txt?

        Path p01 = Paths.get("c:\\temp\\test.txt");
        Files.setAttribute(p01, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);

        Path p02 = Paths.get("c:\\temp\\test.txt");
        Files.setAttribute(p02, "dos:hidden", true);
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_04();
    }
}
