package org.nandao.cap05.p09MonitoringFileEventsUsingWatchEvents;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

// When an application needs to be aware of changes in a directory, a watch service can listen
// to the changes and then inform the application of these changes. The service will register
// a directory to be monitored based on the type of event that is of interest. When the event
// occurs, a watch event is queued and can subsequently be processed as dictated by the needs
// of the application.

public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        final FileSystem fileSystem = FileSystems.getDefault();
        final WatchService watchService = fileSystem.newWatchService();
        final Path directory = Paths.get(path + "/Docs");

        // Events
        WatchEvent.Kind<?>[] events = { StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY };

        directory.register(watchService, events);

        while (true) {
            System.out.println("Waiting for a watch event");
            WatchKey watchKey = watchService.take();
            System.out.println("Path being watched: " + watchKey.watchable());
            System.out.println();

            if (watchKey.isValid()) {
                
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    System.out.println("Kind: " + event.kind());
                    System.out.println("Context: " + event.context());
                    System.out.println("Count: " + event.count());
                    System.out.println();
                }
                
                boolean valid = watchKey.reset();
                
                if (!valid) {
                    // The watchKey is not longer registered
                }
            }
        }
    }

}
