package org.nandao.cap05.p01GettingFileStoreInformation;

import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.text.NumberFormat;


// Each filesystem supports a file storage mechanism. This may be a device, such as a C drive,
// a partition of a drive, a volume, or some other way of organizing a filesystem's space. The
// java.nio.file.FileStore class represents one of these storage divisions. This recipe
// details the methods available to obtain information about the file store.
public class Test {

    static final long kiloByte = 1024;

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {
        String format = "%-16s %-20s %-8s %-8s %12s %12s %12s\n";
        
        System.out.printf(format, "Name", "Filesystem", "Type", "Readonly", "Size(KB)", "Used(KB)", "Available(KB)");
       
        FileSystem fileSystem = FileSystems.getDefault();

        for (final FileStore fileStore : fileSystem.getFileStores()) {

            long totalSpace = fileStore.getTotalSpace() / kiloByte;
            long usedSpace = (fileStore.getTotalSpace() - fileStore.getUnallocatedSpace()) / kiloByte;
            long usableSpace = fileStore.getUsableSpace() / kiloByte;

            String name = fileStore.name();
            String type = fileStore.type();

            boolean readOnly = fileStore.isReadOnly();
            NumberFormat numberFormat = NumberFormat.getInstance();

            System.out.printf(format, name, fileStore, type, readOnly, numberFormat.format(totalSpace), numberFormat.format(usedSpace), numberFormat.format(usableSpace));
        }
    }

}
