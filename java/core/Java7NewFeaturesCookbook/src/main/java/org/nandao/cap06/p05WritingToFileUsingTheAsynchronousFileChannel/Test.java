package org.nandao.cap06.p05WritingToFileUsingTheAsynchronousFileChannel;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

// The java.nio.channels package's AsynchronousFileChannel class permits file IO
// operations to be performed in an asynchronous manner. When an IO method is invoked, it will
// return immediately. The actual operation may occur at some other time (and potentially using
// a different thread). In this recipe, we will explore how the AsynchronousFileChannel
// class performs asynchronous write operations. Read operations will be demonstrated in the
// Reading from a file using the AsynchronousFileChannel class recipe.

public class Test {

    public static void main(String[] args) throws Exception {
        test1();
    }

    public static void test0() throws Exception {
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        try (final AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get(pathSource + "/Docs/asynchronous.txt"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {

            final CompletionHandler<Integer, Object> handler = new CompletionHandler<Integer, Object>() {

                @Override
                public void completed(Integer result, Object attachment) {
                    System.out.println("Attachment: " + attachment + " " + result + " bytes written");
                    System.out.println("CompletionHandler Thread ID: " + Thread.currentThread().getId());
                }

                @Override
                public void failed(Throwable e, Object attachment) {
                    System.err.println("Attachment: " + attachment + " failed with:");
                    e.printStackTrace();
                }
            };

            System.out.println("Main Thread ID: " + Thread.currentThread().getId());

            fileChannel.write(ByteBuffer.wrap("Sample".getBytes()), 0, "First Write", handler);
            fileChannel.write(ByteBuffer.wrap("Box".getBytes()), 0, "Second Write", handler);
        }
    }

    public static void test1() throws Exception {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        try (final AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get(pathSource + "/Docs/asynchronous.txt"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {

            final Future<Integer> writeFuture1 = fileChannel.write(ByteBuffer.wrap("Sample".getBytes()), 0);
            final Future<Integer> writeFuture2 = fileChannel.write(ByteBuffer.wrap("Box".getBytes()), 0);
            
            int result = writeFuture1.get();
            System.out.println("Main Thread ID: " + Thread.currentThread().getId());
            
            System.out.println("Sample write completed with " + result + " bytes written");
            result = writeFuture2.get();
            
            System.out.println("Box write completed with " + result + " bytes written");            
        }
    }
}
