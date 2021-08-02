package org.nandao.cap05.p08WritingYourOwnDirectoryFilter;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// A directory filter is used to control which directory elements are returned, when using the
// java.nio.file.Files class' newDirectoryStream method. This is useful when we
// need to limit the stream's output.
// For example, we may only be interested in those files that exceed a certain size or were last modified after a certain date.
// The java.nio.file.DirectoryStream.Filter interface, as described in this recipe will restrict the stream's
// output. It is more powerful than using globbing as described in the Filtering a directory using
// globbing recipe because decisions can be based on factors other than the filename.

public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {

        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {

            public boolean accept(Path file) throws IOException {
                return (Files.isHidden(file));
            }

        };

        Path directory = Paths.get("/");

        try (final DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory, filter)) {

            for (Path file : directoryStream) {
                System.out.println(file.getFileName());
            }

        }
    }

}
