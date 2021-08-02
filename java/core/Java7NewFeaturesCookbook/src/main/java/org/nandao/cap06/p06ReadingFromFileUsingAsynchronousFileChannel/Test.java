package org.nandao.cap06.p06ReadingFromFileUsingAsynchronousFileChannel;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// Asynchronous read operations are also possible using either of two overloaded read methods. 
// We will demonstrate how this is accomplished using a java.nio.channels
// package's AsynchronousChannelGroup object. This will provide us with a way of observing
// these methods in action and provide an example of an AsynchronousChannelGroup.
public class Test {

    public static void main(String[] args) throws Exception {
        test0();
    }

    public static void test0() throws Exception {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        final ExecutorService pool = new ScheduledThreadPoolExecutor(3);

        try (final AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get(pathSource + "/Docs/items.txt"), EnumSet.of(StandardOpenOption.READ), pool)) {

            System.out.println("Main Thread ID: " + Thread.currentThread().getId());

            final CompletionHandler<Integer, ByteBuffer> handler = new CompletionHandler<Integer, ByteBuffer>() {

                @Override
                public synchronized void completed(Integer result, ByteBuffer attachment) {

                    for (int i = 0; i < attachment.limit(); i++) {
                        System.out.print((char) attachment.get(i));
                    }

                    System.out.println("");
                    System.out.println("CompletionHandler Thread ID: " + Thread.currentThread().getId());
                    System.out.println("");
                }

                @Override
                public void failed(Throwable e, ByteBuffer attachment) {
                    System.out.println("Failed");
                }
            };

            final int bufferCount = 5;
            final ByteBuffer buffers[] = new ByteBuffer[bufferCount];

            for (int i = 0; i < bufferCount; i++) {
                buffers[i] = ByteBuffer.allocate(10);
                fileChannel.read(buffers[i], i * 10, buffers[i], handler);
            }

            pool.awaitTermination(1, TimeUnit.SECONDS);
            System.out.println("Byte Buffers");
            
            for (final ByteBuffer byteBuffer : buffers) {
                
                for (int i = 0; i < byteBuffer.limit(); i++) {
                    System.out.print((char) byteBuffer.get(i));
                }
                
                System.out.println();
            }
        }
    }

}
