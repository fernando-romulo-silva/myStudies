package org.nandao.cap02.p8.DeterminingWhetherTwoPathsAre;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// At times it may be necessary to compare paths. The Path class allows you to test the paths
// for equality using the equals method. You can also use the compareTo method to compare
// two paths lexicographically using an implementation of the Comparable interface. Finally, the
// isSameFile method can be used to determine if two Path objects will locate the same file.

public class Test {

    public static void main(String[] args) {
        test0();
    }

    public static void test0() {
        Path path1 = null;
        Path path2 = null;
        Path path3 = null;
        
        final ClassLoader classLoader = Test.class.getClassLoader();
        
        path1 = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());
        path2 = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());
        path3 = Paths.get(new File(classLoader.getResource("Home/Music/Future Setting A.mp3").getFile()).getAbsolutePath());
        
        testEquals(path1, path2);
        testEquals(path1, path3);
        
        testCompareTo(path1, path2);
        testCompareTo(path1, path3);
        
        testSameFile(path1, path2);
        testSameFile(path1, path3);
    }

    private static void testEquals(Path path1, Path path2) {
        if (path1.equals(path2)) {
            System.out.printf("%s and %s are equal\n", path1, path2);
        } else {
            System.out.printf("%s and %s are NOT equal\n", path1, path2);
        }
    }

    private static void testCompareTo(Path path1, Path path2) {
        if (path1.compareTo(path2) == 0) {
            System.out.printf("%s and %s are identical\n", path1, path2);
        } else {
            System.out.printf("%s and %s are NOT identical\n", path1, path2);
        }
    }

    private static void testSameFile(Path path1, Path path2) {
        try {
            if (Files.isSameFile(path1, path2)) {
                System.out.printf("%s and %s are the same file\n", path1, path2);
            } else {
                System.out.printf("%s and %s are NOT the same file\n", path1, path2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
