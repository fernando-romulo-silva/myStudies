package br.fernando.ch05_IO_and_NIO_Objective.p04_DirectoryStream;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;

public class Test03 {

    // =========================================================================================================================================
    // WatchService
    static void test01() throws IOException {

        // Notice that we had to watch the directory that contains the files or directories we are
        // interested in. This is why we watched /dir instead of /dir/directoryToDelete . This
        // is also why we had to check the context to make sure the directory we were actually
        // interested in is the one that was deleted.

        Path dir = Paths.get("C:\\vb\\workspace-oxygen\\ocp_java_se_8_ii_guide_exam\\src\\main\\resources\\dir"); // get directory containing file/directory we care about

        WatchService watcher = FileSystems.getDefault().newWatchService();

        dir.register(watcher, StandardWatchEventKinds.ENTRY_DELETE); // start watching for deletions

        while (true) { // loop until say to stop
            WatchKey key;

            try {

                // key = watcher.take(); // wait "forever" for an event
                // key = watcher.poll(); // get event if present right NOW
                key = watcher.poll(10, TimeUnit.SECONDS); // wait up to 10 seconds for an event

            } catch (final InterruptedException ex) {
                return; // give up if something goes wrong
            }

            for (WatchEvent<?> event : key.pollEvents()) {

                WatchEvent.Kind<?> kind = event.kind();

                System.out.println(kind.name()); // create/delete/modify
                System.out.println(kind.type()); // always a path for us
                System.out.println(event.context());

                String name = event.context().toString();

                if (name.equals("directoryToDelete")) { // only delete right directory
                    System.out.println("Directory deleted, now we can proceed");
                    return; // end program, we found what we were witing for
                }
            }

            // This is very important. If you forget to call reset, the program will work for the first event,
            // but then you will not be notified of any other events.
            key.reset(); // keep looking for events
        }

        // ENTRY_DELETE means you want your program to be informed when a file or directory has been deleted.
        // Similarly, ENTRY_CREATE means a new file or directory has been created.
        //
        // ENTRY_MODIFY means a file has been edited in the directory.
        //
        // Renaming a file or directory is interesting, as it does not show up as ENTRY_MODIFY .
        // From Java’s point of view, a rename is equivalent to creating a new file and deleting
        // the original. This means that two events will trigger for a rename—both ENTRY_CREATE
        // and ENTRY_DELETE . Actually editing a file will show up as ENTRY_MODIFY
    }

    // =========================================================================================================================================
    // WatchService
    static void test02() throws IOException {

        WatchService watcher = FileSystems.getDefault().newWatchService();

        Path dir = Paths.get("C:\\vb\\workspace-oxygen\\ocp_java_se_8_ii_guide_exam\\src\\main\\resources\\dir\\parent");
        dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);

        Path child = Paths.get("C:\\vb\\workspace-oxygen\\ocp_java_se_8_ii_guide_exam\\src\\main\\resources\\dir\\parent\\child");
        child.register(watcher, StandardWatchEventKinds.ENTRY_DELETE);

        // This works. You can type in all the directories you want to watch. If we had a lot of
        // child directories, this would quickly get to be too much work.
        //
        // Instead, we can have Java do it for us:
        Path myDir = Paths.get("/dir/parent");

        Files.walkFileTree(myDir, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY); // wach each directory

                return FileVisitResult.CONTINUE;
            }
        });
    }

    // ==========================================================================================================================================
    static void summary() throws IOException {

        // *********************************************************************************************************
        // WatchService - watch the directory that contains the files or directories we are interested in.
        // *********************************************************************************************************
        WatchService watcher = FileSystems.getDefault().newWatchService();

        Path myDir = Paths.get("/dir/parent");

        Files.walkFileTree(myDir, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY); // wach each directory

                return FileVisitResult.CONTINUE;
            }
        });

        // *********************************************************************************************************
        // WatchService Control Events
        // *********************************************************************************************************
        while (true) { // loop until say to stop
            WatchKey key;

            try {

                // key = watcher.take(); // wait "forever" for an event
                // key = watcher.poll(); // get event if present right NOW
                key = watcher.poll(10, TimeUnit.SECONDS); // wait up to 10 seconds for an event

            } catch (final InterruptedException ex) {
                return; // give up if something goes wrong
            }

            for (WatchEvent<?> event : key.pollEvents()) {

                WatchEvent.Kind<?> kind = event.kind();

                System.out.println(kind.name()); // create/delete/modify
                System.out.println(kind.type()); // always a path for us
                System.out.println(event.context());

                String name = event.context().toString();

                if (name.equals("directoryToDelete")) { // only delete right directory
                    System.out.println("Directory deleted, now we can proceed");
                    return; // end program, we found what we were witing for
                }
            }

            // This is very important. If you forget to call reset, the program will work for the first event,
            // but then you will not be notified of any other events.
            key.reset(); // keep looking for events
        }

        // *********************************************************************************************************
        // WatchService Events
        // *********************************************************************************************************
        // ENTRY_DELETE means you want your program to be informed when a file or directory has been deleted.
        // 
        // ENTRY_CREATE means a new file or directory has been created.
        //
        // ENTRY_MODIFY means a file has been edited in the directory. Renaming a file or directory is interesting, as it does not show up as ENTRY_MODIFY .
        //
        // From Java’s point of view, a rename is equivalent to creating a new file and deleting
        // the original. This means that two events will trigger for a rename—both ENTRY_CREATE
        // and ENTRY_DELETE . Actually editing a file will show up as ENTRY_MODIFY
        //
        // OVERFLOW A special event to indicate that events may have been lost or discarded. 
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws IOException {
        test01();
    }
}
