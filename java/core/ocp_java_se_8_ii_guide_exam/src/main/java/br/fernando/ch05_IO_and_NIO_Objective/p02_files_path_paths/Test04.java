package br.fernando.ch05_IO_and_NIO_Objective.p02_files_path_paths;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("unused")
public class Test04 {

    // =========================================================================================================================================
    // Absolute Path
    static void test01() {
        // Creating an absolute path is done by calling the Paths.get() factory method with the absolute file as parameter.
        // Here is an example of creating a Path instance representing an absolute path:

        Path path = Paths.get("c:\\data\\myfile.txt");
    }

    // =========================================================================================================================================
    // Relative Path
    static void test02() {
        // A relative path is a path that points from one path (the base path) to a directory or file. The full path (the absolute path) of
        // a relative path is derived by combining the base path with the relative path.

        // The Java NIO Path class can also be used to work with relative paths.
        // You create a relative path using the Paths.get(basePath, ...relativePath) method.
        // Here are two relative path examples in Java:

        Path projects = Paths.get("c:\\data", "projects");

        Path file = Paths.get("c:\\data", "projects\\a-project\\myfile.txt"); // c:\data\projects\a-project\myfile.txt

        Path path01 = Paths.get("myDirectory"); // myDirectory

        Path path02 = Paths.get("./myDirectory"); // one dot means current directory

        Path path03 = Paths.get("anotherDirectory", "..", "myDirectory");// two dots means go up one directory

    }

    // =========================================================================================================================================
    // Normalizing a Path
    static void test03() {
        // Normalizing means that it removes all the . and .. codes in the middle of the path string, and resolves what path the path string
        // refers to. Here is a Java Path.normalize() example:

        // paths in relation to themselves
        String buildProject = "/Build_Project/scripts"; // build scripts to express

        String upTwoDirectories = "../.."; // remember what .. means? go up one directory

        String myProjet = "/My_Project/source";

        Path path05 = Paths.get(buildProject, upTwoDirectories, myProjet); // build path from variables

        System.out.println("Original: " + path05);

        System.out.println("Normalize: " + path05.normalize()); // /My_Project/source

        // Be careful when using this normalize() ! It just looks at the String equivalent of
        // the path and doesn’t check the file system to see whether the directories or files
        // actually exist.
    }

    // =========================================================================================================================================
    // Resolving a Path
    static void test04() throws URISyntaxException {
        // What if you need to combine two paths? You might want to do this if you have one Path representing your home directory and
        // another containing the Path within that directory.

        Path dir01 = Paths.get("/home/java"); // *look at the dir01, it has root path '/' (linux)
        Path file01 = Paths.get("models/Model.pdf");

        Path result = dir01.resolve(file01);

        System.out.println("result = " + result); // home/java/models/Model.pdf

        // Keeping this definition in mind, let’s look at some more complex examples:

        Path absolute = Paths.get("/home/java");

        Path relative = Paths.get("dir");

        Path file = Paths.get("Model.pdf");

        System.out.println("1: " + absolute.resolve(relative));
        // 1: \home\java\dir

        System.out.println("2: " + absolute.resolve(file));
        // 2: \home\java\Model.pdf

        System.out.println("3: " + relative.resolve(file)); // relative resolve relative ...
        // 3: dir\Model.pdf

        System.out.println("4: " + relative.resolve(absolute)); // BAD
        // 4: \home\java

        System.out.println("5: " + file.resolve(absolute)); // BAD
        // 5: \home\java

        System.out.println("6: " + file.resolve(relative)); // BAD
        // 6: Model.pdf\dir

        // Careful with methods that come in two flavors: one with a Path parameter
        // and the other with a String parameter such as resolve() . The tricky part here
        // is that null is a valid value for both a Path and a String . What will happen if
        // you pass just null as a parameter? Which method will be invoked?
        //
        // Path path100 = Paths.get("/usr/bin/zip");
        // path100.resolve(null); // don't compile
    }

    // =========================================================================================================================================
    // Relativizing a Path
    static void test05() {
        // Now suppose we want to do the opposite of resolve. The term relativizing simply means creating a direct path between two known paths.

        Path dir = Paths.get("/home/java");

        Path music = Paths.get("/home/java/country/Swift.mp3");

        Path mp3 = dir.relativize(music);

        System.out.println(mp3);
        // Java recognized that the "/home/java" part is the same and returned a path of just the remainder. It'll print "country/Swift.mp3"

        System.out.println("-----------------------------------------------------------------------");
        // In this example, we determined that music is a file in a directory named country within dir .
        // Keeping this definition in mind, let’s look at some more complex examples:

        Path absolute01 = Paths.get("/home/java");

        Path absolute02 = Paths.get("/usr/local");

        Path absolute03 = Paths.get("/home/java/temp/music.mp3");

        // ---
        Path relative01 = Paths.get("temp");

        Path relative02 = Paths.get("temp/music.pdf");

        // ----------------------------
        // The first example is straightforward. It tells us how to
        // get to absolute3 from absolute1 by going down two directories.
        System.out.println("1: " + absolute01.relativize(absolute03)); // 1: temp/music.mp3

        // We get to absolute1 from absolute3 by doing the opposite—going up two
        // directories. Remember from normalize() that a double dot means to go up a directory.
        System.out.println("2: " + absolute02.relativize(absolute01)); // 2: ../../home/java

        // The third output statement says that we have to go up two directories and then
        // down two directories to get from absolute1 to absolute2 . Java knows this because
        // we provided absolute paths. The worst possible case is to have to go all the way up to
        // the root like we did here.
        System.out.println("3: " + absolute03.relativize(absolute02)); // 3: ../../../../usr/local

        // The fourth output statement is okay. Even though they are both relative paths, there
        // is enough in common for Java to tell what the difference in the path is.
        System.out.println("4: " + relative01.relativize(relative02)); // 4: music.pdf

        // The fifth example throws an exception. Java can’t figure out how to make a relative
        // path out of one absolute path and one relative path.
        System.out.println("5: " + absolute01.relativize(relative01)); // BAD Exception in thread "main" java.lang.IllegalArgumentException: 'other' is different type of Path

        // Where this path and the given path do not have a root component, then a relative path can be constructed.
        //
        // A relative path cannot be constructed if only one of the paths have a root component. 
        //
        // Where both paths have a root component then it is implementation dependent if a relative path can be constructed.
        //
        // If this path and the given path are equal then an empty path is returned.
        //
        // For any two normalized paths p and q, where q does not have a root component, p.relativize(p.resolve(q)).equals(q)	
    }

    // =========================================================================================================================================
    static void summary() {
        // *******************************************************************************
        // Normalizing a Path - Remove the dots
        // *******************************************************************************
        Path path01 = Paths.get("myDirectory");
        System.out.println(path01); // myDirectory

        Path path02 = Paths.get("./myDirectory"); // one dot means current directory
        System.out.println(path02); // ./myDirectory

        Path path03 = Paths.get("anotherDirectory", "..", "myDirectory");// two dots means go up one directory
        System.out.println(path03); // anotherDirectory\..\myDirectory

        // ----------------------------------------------------

        String buildProject = "/Build_Project/scripts"; // build scripts to express

        String upTwoDirectories = "../.."; // remember what .. means? go up one directory

        String myProjet = "/My_Project/source";

        Path path05 = Paths.get(buildProject, upTwoDirectories, myProjet); // build path from variables

        System.out.println("Original: " + path05); // \Build_Project\scripts\..\..\My_Project\source

        System.out.println("Normalize: " + path05.normalize()); // \My_Project\source

        // Be careful when using this normalize() ! It just looks at the String equivalent of
        // the path and doesn’t check the file system to see whether the directories or files
        // actually exist.

        // *******************************************************************************
        // Resolving a Path - if you need to combine two paths?
        // *******************************************************************************
        Path absolute01 = Paths.get("/home/java");

        Path relative01 = Paths.get("dir");

        Path file01 = Paths.get("Model.pdf");

        System.out.println("1: " + absolute01.resolve(relative01));
        // 1: /home/java/dir

        System.out.println("2: " + absolute01.resolve(file01));
        // 2: /home/java/Model.pdf

        System.out.println("3: " + relative01.resolve(file01));
        // 3: dir/Model.pdf

        // ------------------------------------------------------
        Path absolute02 = Paths.get("/home/java");

        Path absolute03 = Paths.get("/home/java/utils");

        Path file02 = Paths.get("Model.pdf");

        // ------------------------------------------------------
        // /home/java  + /home/java/utils
        System.out.println("4: " + absolute02.resolve(absolute03));
        // 4: /home/java/utils

        // /home/java  + Model.pdf
        System.out.println("5: " + absolute02.resolve(file02));
        // 5: /home/java/Model.pdf

        // /home/java/utils + Model.pdf
        System.out.println("6: " + absolute03.resolve(file02));
        // 6: /home/java/utils/Model.pdf

        // ------------------------------------------------------
        Path absolute04 = Paths.get("/home/java");

        Path absolute05 = Paths.get("/etc/bin/utils");

        System.out.println("7: " + absolute04.resolve(absolute05));
        // 7: /etc/bin/utils

        // *******************************************************************************
        // Relativizing a Path - Access a path from your location
        // *******************************************************************************
        Path dir = Paths.get("/home/java");

        Path music = Paths.get("/home/java/country/Swift.mp3");

        Path mp3 = dir.relativize(music);

        System.out.println(mp3); // country/Swift.mp3, "home/java" was removed because I'm already in "/home/java"

        Path p1 = Paths.get("/personal/readme.txt");

        Path p2 = Paths.get("/index.html");

        Path p3 = p1.relativize(p2);

        System.out.println(p3); // ../../index.html
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        // test02();

        Path absolute04 = Paths.get("/home/java");

        Path absolute05 = Paths.get("/etc/bin/utils");

        System.out.println("7: " + absolute04.resolve(absolute05));
    }
}
