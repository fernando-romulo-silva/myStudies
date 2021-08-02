package br.fernando.ch05_IO_and_NIO_Objective.p07_tests;

import java.io.BufferedWriter;
import java.net.URI;
import java.nio.charset.Charset;
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
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test07 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
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
    static void test01_03() throws Exception {
        // Assuming that a file named "a.java" exists in c:\pathtest\ as well as in c:\pathtest\dir2, what will happen when the following
        // code is compiled and run?

        Path p1 = Paths.get("c:\\pathtest\\a.java");
        Path p2 = Paths.get("c:\\pathtest\\dir2\\a.java");
        Files.move(p1, p2, StandardCopyOption.ATOMIC_MOVE);
        Files.delete(p1);
        System.out.println(p1.toFile().exists() + " " + p2.toFile().exists());

        // It will throw an exception at run time.

        // There are two contradictory factors at play here.
        //
        // 1. By default, Files.move method attempts to move the file to the target file, failing if the target file exists except
        // if the source and target are the same file, in which case this method has no effect. Therefore, this code should throw an exception
        // because a.java exists in the target directory.
        //
        // 2. However, when the CopyOption argument of the move method is StandardCopyOption.ATOMIC_MOVE, the operation is implementation
        // dependent if the target file exists. The existing file could be replaced or an IOException could be thrown.
        // If the exiting file at p2 is replaced, Files.delete(p1) will throw java.nio.file.NoSuchFileException
        //
        // Therefore, in this case, the given code on the whole will end up with an exception.
        //
        // Some candidates have reported being tested on StandardCopyOption.ATOMIC_MOVE. Unfortunately, it is not clear whether the
        // exam tests on the implementation dependent aspect of this option. If you get a question on it and the options do not contain any
        // option referring to its implementation dependent nature, we suggest the following: Assume that the move operation will succeed and
        // then the delete operation will throw a java.nio.file.NoSuchFileException.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Given that test1.txt exists but test2.txt doesn't exist, consider the following code?

        Path p1 = Paths.get("c:\\temp\\test1.txt");
        Path p2 = Paths.get("c:\\temp\\test2.txt");
        copy1(p1, p2);

        // Which file attributes will be copied over to test2.txt?

        // Copying of the attributes is platform and system dependent.

        // Attempts to copy the file attributes associated with source file to the target file. The exact file attributes that are copied is
        // platform and file system dependent and therefore unspecified. Minimally, the last-modified-time is copied to the target file if
        // supported by both the source and target file store. Copying of file timestamps may result in precision loss.
    }

    public static void copy1(Path p1, Path p2) throws Exception {
        Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES);
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What will the following code print?

        Path p1 = Paths.get("\\photos\\vacation");
        Path p2 = Paths.get("\\yellowstone");
        System.out.println(p1.resolve(p2) + "  " + p1.relativize(p2));

        // \yellowstone  ..\..\yellowstone
        //
        // 1. Since the argument to resolve starts with \\, the result will be the same as the argument. If the argument doesn't start with a \
        // and it doesn't start with a root such as c:, the output is the result on appending the argument to the path on which the
        // method is invoked.
        //
        // 2. To arrive at \\yellowstone from \\photos\\vacation, you have to first go two directories up and then down to yellowstone.
        // Therefore, p1.relativize(p2) will be ..\..\yellowstone
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What is the state of the WatchKey at the end of the following code?

        Path path = Paths.get("C:/temp");
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
        WatchKey key = watchService.take();

        // status of key here

        // Signaled
        //
        // Explanation
        // When initially created the key is said to be ready. When an event is detected then the key is signaled and queued so that it can be
        // retrieved by invoking the watch service's poll or take methods. Once signalled, a key remains in this state until its reset method
        // is invoked to return the key to the ready state
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Which of the following is true about the PathMatcher returned by FileSystem.getPathMatcher()?

        // It can match Paths using a regular expression or a glob pattern depending on how it is acquired.
        //
        // You can get a PathMatcher using either of the expressions. For example,
        FileSystems.getDefault().getPathMatcher("glob:*.java"); // Uses a glob pattern
        // or
        FileSystems.getDefault().getPathMatcher("regex:a.*b\\.txt"); // uses a regular expression pattern
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // What will the following code fragment print?

        Path p1 = Paths.get("photos\\goa");
        Path p2 = Paths.get("\\index.html");
        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        // Note that if one path has a root (for example, if a path starts with a // or c:) and the other does not, relativize cannot work
        // and it will throw an IllegalArgumentException.
        //
        // Explanation
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
    static void test01_09() throws Exception {
        // What will the following code fragment print?

        Path p1 = Paths.get("\\personal\\readme.txt");
        Path p2 = Paths.get("\\index.html");
        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        // ..\..\index.html
        //
        // Observe that if you append this path to p1, you will get p2. Therefore, this is the right answer.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Consider the following code:

        Path p1 = Paths.get("\\temp\\records");
        Path p2 = p1.resolve("clients.dat");
        System.out.println(p2 + " " + isValid(p2));
        //
        // \temp\records\clients.dat false
        //
        // p2 will be set to \temp\records\clients.dat. Since it starts with \temp and not with temp, the method isValid will return false.
    }

    static boolean isValid(Path p) {
        return p.startsWith("temp") && p.endsWith("clients.dat");
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
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
    static void test01_12() throws Exception {
        // Which of the following statements is true about the take method of WatchService?

        // Retrieves and removes next watch key, waiting if none are yet present.

        // WatchKey take() throws InterruptedException -> Retrieves and removes next watch key, waiting if none are yet present.
        //
        // Explanation
        // WatchService has two types of methods for retrieving the WatchKeys - poll and take. The two poll methods return null if no key 
        // is present and do not wait for ever. The take method doesn't return null but keeps waiting until a key is available.

    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        // The following line of code has thrown java.nio.file.FileSystemNotFoundException when run.
        // What might be the reason(s)?

        Path p1 = Paths.get(new URI("file://e:/temp/records"));

        // The file system, identified by the URI, does not exist.
        //
        // The provider identified by the URI's scheme component is not installed.
    }

    // =========================================================================================================================================

    static int count;

    static void test01_14() throws Exception {
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
    static void test01_15() throws Exception {
        // Given the following code for monitoring a directory:

        Path path = Paths.get("");
        WatchService watchService = FileSystems.getDefault().newWatchService();

        path.register(watchService, //
                StandardWatchEventKinds.ENTRY_CREATE, //
                StandardWatchEventKinds.ENTRY_MODIFY, //
                StandardWatchEventKinds.ENTRY_DELETE);

        while (true) {
            WatchKey key = watchService.take(); // waits until a key is available

            System.out.println(key.isValid());

            for (WatchEvent<?> watchEvent : key.pollEvents()) {
                Kind<?> kind = watchEvent.kind();
                System.out.println(watchEvent);
            }

            // key.reset(); if you don't reset, you will lost the other events. That's because you catch one event
        }

        // A file is created and then deleted from the monitored directory (with substantial time elapsed between the two actions).
        // How many events will be printed by the above code?

        // 1 (one event)

        // Explanation
        // Here is how watch service works -
        //
        // 1. After getting a WatchKey from WatchService.take method, the events associated with the key can be retrieved using
        // WatchKey.pollEvents method
        //
        // 2. WatchKey.pollEvents method will return all the events that happened between the time key was registered with the WatchService
        // and the call to pollEvents.
        //
        // 3. After the take method has been called and while processing the events retrieved using pollEvents, if any more events
        // happen on the same key, these events can still be retrieved by calling the pollEvents method again.
        // Thus, the pollEvents method can be called multiple times.
        //
        // 4. After take method has been called, the call to watchService.take() again will simply block and will not return until
        // the key is reset by calling the reset() method.
        // In other words, WatchKey.reset must be called before calling WatchService.take again.
        // Any events that happen after calling pollEvents and before calling reset will be lost.
        //
        // Now, in the situation given in the problem statement, if a file is created and deleted too quickly, it is possible for the
        // pollEvents method to return two events. But if there is enough time elapsed between the two events, the second event will be
        // lost because the code doesn't call reset on the key before calling watchService.take.
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // You want to create a new file. If the file already exists, you want the new file to overwrite the existing one (the content of
        // the existing file, if any, should go away). Which of the following code fragments will accomplish this?

        Path myfile = Paths.get("c:\\temp\\test.txt");
        BufferedWriter br = Files.newBufferedWriter(myfile, Charset.forName("UTF-8"), // 
                new OpenOption[]{ StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE });

        // If the file already exists, TRUNCATE_EXISTING will take care of the existing content. 
        // If the file does not exist, CREATE will ensure that it is created.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_02();
    }
}
