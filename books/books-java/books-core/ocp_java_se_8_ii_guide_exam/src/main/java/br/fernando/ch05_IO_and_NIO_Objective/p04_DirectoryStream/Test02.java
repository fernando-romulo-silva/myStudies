package br.fernando.ch05_IO_and_NIO_Objective.p04_DirectoryStream;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Test02 {

    // =========================================================================================================================================
    // PathMatcher
    static void test01_01() {
        Path path01 = Paths.get("/home/One.txt");

        Path path02 = Paths.get("One.txt");

        PathMatcher matcher = FileSystems.getDefault() // get the PathMatcher
                .getPathMatcher( // for the right file system
                        "glob:*.txt"); // wait. What's a glob?

        System.out.println(matcher.matches(path01));

        System.out.println(matcher.matches(path02));

        // We can see that the code checks if a Path consists of any characters followed by “.txt.”
        // To get a PathMatcher , you have to call FileSystems.getDefault().getPathMatcher because
        // matching works differently on different operating systems
        //
        // Globs are not regular expressions, although they might look similar at first.
        //
        // In the world of globs, one asterisk means “match any character except for a directory boundary.”
        // Two asterisks means “match any character, including a directory boundary.“

        System.out.println("-------------------------------------------");

        Path path03 = Paths.get("/com/java/One.java");

        matches(path03, "glob:*.java"); // win: false ; linux: true
        matches(path03, "glob:**/*.java"); // win: true ; linux: false
        matches(path03, "glob:*"); // win: false ; linux: true
        matches(path03, "glob:**"); // win: true ; linux: true

        // Why? Because UNIX doesn’t see the backslash as a directory boundary.
        // The lesson here is to use / instead of \\ so your code behaves more predictably
        // across operating systems.

        System.out.println("-------------------------------------------");

        // A question mark matches any character. A character could be a letter or a number or anything else.

        Path path04 = Paths.get("One.java");
        Path path05 = Paths.get("One.ja^a");

        matches(path04, "glob:*.????"); // true
        matches(path04, "glob:*.???"); // false

        matches(path05, "glob:*.????"); // true
        matches(path05, "glob:*.???"); // false

        System.out.println("-------------------------------------------");

        // Globs also provide a nice way to match multiple patterns. Suppose we want to
        // match anything that begins with the names Kathy or Bert:

        Path path06 = Paths.get("Bert-book");
        Path path07 = Paths.get("kathy-horse");

        // The first glob shows we can put wildcards inside braces to have multiple glob expressions.
        matches(path06, "glob:{Bert*, Kathy*}"); // true

        // The second glob shows that we can put common wildcards outside the braces to share them.
        matches(path07, "glob:{Bert, Kathy}*"); // true

        // The third glob shows that without the wildcard, we will only
        // match the literal strings “Bert” and “Kathy.“
        matches(path06, "glob:{Bert, Kathy}"); // false

    }

    static void test01_02() {
        System.out.println("-------------------------------------------");

        // You can also use sets of characters like [a-z] or [#$%] in globs just like in regular
        // expressions. You can also escape special characters with a backslash. Let’s put this all
        // together with a tricky example:

        Path path01 = Paths.get("0*b/test/1");
        Path path02 = Paths.get("9\\*b/test/1");
        Path path03 = Paths.get("01b/test/1");
        Path path04 = Paths.get("0*b/1");

        final String glob = "glob:[0-9]\\*{A*, b}/**/1";

        matches(path01, glob); // true
        matches(path02, glob); // false
        matches(path03, glob); // false
        matches(path04, glob); // false

        // Spelling out what the glob does, we have the following:
        // [0-9] : One single digit. Can also be read as any one character from 0 to 9.
        //
        // \\* : The literal character asterisk rather than the asterisk that means to match anything.
        // A single backslash before * escapes it. However, Java won’t let you type a single backslash,
        // so you have to escape the backslash itself with another backslash.
        //
        // {A*,b} : Either a capital A followed by anithing or the single character "b"
        //
        // /**/ : One or more directories with any name.
        //
        // 1 : The single character 1.
        //
        // Globs tend to be simple expressions like {*.txt,*.html} when used for real.
    }

    // =========================================================================================================================================
    // Glob vs. Regular Expression
    static void test01_03() throws IOException {
        //
        // Zero or more of any character, including a directory boundary Glob -> ** RE -> .*
        //
        // Zero or more of any character, not including a directory boundary Glob -> * RE -> N/A no special syntax
        //
        // Exactly one character Glob -> ? RE -> .
        //
        // Any digit Glob -> [0-9] RE -> [0-9]
        // Begins with cat or dog Glob -> {cat, dog}* RE -> (cat|dog).*
        //
        // we can combine the power of PathMatcher s with what we already know about walking the
        // file tree to accomplish this.
        //

        MyPathMatcher dirs = new MyPathMatcher();
        Files.walkFileTree(Paths.get("/"), dirs); // starts with root
    }

    static class MyPathMatcher extends SimpleFileVisitor<Path> {

        private PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/password/**.txt"); // ** means any subdirectory

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

            if (matcher.matches(file)) {
                System.out.println(file);
            }

            return FileVisitResult.CONTINUE;
        }
    }

    static void matches(Path path, String glob) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(glob);
        System.out.println(matcher.matches(path));
    }

    // =========================================================================================================================================
    static void summary() {

        // ***********************************************************************************
        // PathMatcher
        // ***********************************************************************************
        Path path01 = Paths.get("/home/One.txt");

        Path path02 = Paths.get("One.txt");

        PathMatcher matcher = FileSystems.getDefault() // get the PathMatcher
                .getPathMatcher( // for the right file system
                        "glob:*.txt"); //

        System.out.println(matcher.matches(path01));

        System.out.println(matcher.matches(path02));

        // ***********************************************************************************
        // Glob - Globs are not regular expressions, although they might look similar at first.
        // ***********************************************************************************
        Path path03 = Paths.get("/com/java/One.java");

        matches(path03, "glob:*.java"); // win: false ; linux: true
        matches(path03, "glob:**/*.java"); // win: true ; linux: false
        matches(path03, "glob:*"); // win: false ; linux: true
        matches(path03, "glob:**"); // win: true ; linux: true

        // Why? Because UNIX doesn’t see the backslash as a directory boundary.
        // The lesson here is to use / instead of \\ so your code behaves more predictably
        // across operating systems.

        // ***********************************************************************************
        // Glob vs. Regular Expression
        // ***********************************************************************************
        // Zero or more of any character, including a directory boundary Glob -> ** RE -> .*
        //
        // Zero or more of any character, not including a directory boundary Glob -> * RE -> N/A no special syntax
        //
        // Exactly one character Glob -> ? RE -> .
        //
        // Any digit Glob -> [0-9] RE -> [0-9]
        // Begins with cat or dog Glob -> {cat, dog}* RE -> (cat|dog).*
        //
        // we can combine the power of PathMatcher s with what we already know about walking the
        // file tree to accomplish this.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws IOException {
        test01_03();
    }
}
