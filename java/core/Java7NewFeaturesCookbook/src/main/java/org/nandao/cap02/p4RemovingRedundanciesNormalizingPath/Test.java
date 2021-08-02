package org.nandao.cap02.p4RemovingRedundanciesNormalizingPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

// These contain redundancies or extraneous parts. In the first example, the path starts at home
// and then goes down a directory level to docs directory. The .. notation then leads back up
// to the home directory. This then proceeds down the music directory and to the mp3 file. The
// docs "/.." element is extraneous.
//
// In the second example, the path starts at home and then encounters a single period. This
// represents the current directory, that is, the home directory. Next, the path goes down the
// music directory and then encounters the mp3 file. The "/." is redundant and is not needed.

public class Test {

    public static void main(String[] args) {
	test02();
    }

    public static void test01() {
	final ClassLoader classLoader = Test.class.getClassLoader();
	final File file = new File(classLoader.getResource("Home/Docs/../Music/Space Machine A.mp3").getFile());

	// Path path = Paths.get("/Home/Docs/../Music/Space Machine A.mp3");

	Path path = Paths.get(file.getAbsolutePath());

	System.out.println("Absolute path: " + path.toAbsolutePath());
	System.out.println("URI: " + path.toUri());
	System.out.println("Normalized Path: " + path.normalize());
	System.out.println("Normalized URI: " + path.normalize().toUri());
	System.out.println();

	path = Paths.get("/home/./Music/ Robot Brain A.mp3");
	System.out.println("Absolute path: " + path.toAbsolutePath());
	System.out.println("URI: " + path.toUri());

	// The normalize method does not check to see if the files or path are valid.
	// The method simply performs a syntactic operation against the path.
	// If a symbolic link was part of the original path, then the normalized
	// path may no longer be valid.
	System.out.println("Normalized Path: " + path.normalize());
	System.out.println("Normalized URI: " + path.normalize().toUri());
    }

    public static void test02() {

	try {
	    Path path = Paths.get("/home/docs/../music/NonExistentFile.mp3");
	    System.out.println("Absolute path: " + path.toAbsolutePath());
	    System.out.println("Real path: " + path.toRealPath());
	} catch (IOException ex) {
	    System.out.println("The file does not exist!");
	}
    }
}
