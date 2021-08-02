package org.nandao.cap03.p03ObtainingMapFileAttributes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

// An alternative way of accessing file attributes is to use the Files class' readAttributes
// method. There are two overloaded versions of this method, and they differ in their second
// argument and their return data types. In this recipe, we will explore the version that returns
// a java.util.Map object as it allows more flexibility in what attributes it can return. The
// second version of the method is discussed in a series of recipes, each devoted to a specific
// class of attributes

public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();

        final Path path = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

        // In this example, we used the string literal, "*", as the second argument. This value instructs
        // the method to return all available attributes of the file. As we will see shortly, other string
        // values can be used to get different results.
        final Map<String, Object> attrsMap = Files.readAttributes(path, "*");
        final Set<String> keys = attrsMap.keySet();

        for (final String attribute : keys) {
            System.out.println(attribute + ": " + Files.getAttribute(path, attribute));
        }
    }

    public static void test1() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();

        final Path path = Paths.get(new File(classLoader.getResource("Home/Docs/users.txt").getFile()).getAbsolutePath());

        // To direct the method to not follow symbolic links, use the java.nio.file
        // package's LinkOption.NOFOLLOW_LINKS enumeration constant, shown as follows:
        final Map<String, Object> attrsMap = Files.readAttributes(path, "*", LinkOption.NOFOLLOW_LINKS);

        // "*" - All of the basic file attributes
        // "basic:*" - All of the basic file attributes
        // "basic:isDirectory,lastAccessTime" - Only the isDirectory and lastAccessTime attributes
        // "isDirectory,lastAccessTime" - Only the isDirectory and lastAccessTime attributes
        // "" None - a java.lang.IllegalArgumentException is generated

        final Set<String> keys = attrsMap.keySet();

        for (final String attribute : keys) {
            System.out.println(attribute + ": " + Files.getAttribute(path, attribute));
        }

    }
}
