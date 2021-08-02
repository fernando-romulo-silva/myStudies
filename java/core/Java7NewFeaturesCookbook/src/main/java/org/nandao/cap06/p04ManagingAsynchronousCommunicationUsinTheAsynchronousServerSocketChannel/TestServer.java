package org.nandao.cap06.p04ManagingAsynchronousCommunicationUsinTheAsynchronousServerSocketChannel;

import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

// Create a new console application that will be on the server and add the following
// main method. The server will simply display any messages sent to it. It opens a
// server socket and binds it to an address. It will then use the accept method
// with a CompletionHandler to process any requests from a client.
public class TestServer {

    public static void main(String[] args) throws Exception {
        test1();
    }

    public static void test0() throws Exception {

        final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open();
        final InetSocketAddress address = new InetSocketAddress("localhost", 5000);
        listener.bind(address);

        listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

            @Override
            public void completed(AsynchronousSocketChannel channel, Void attribute) {
                try {
                    System.out.println("Server: completed method executing");

                    while (true) {
                        final ByteBuffer buffer = ByteBuffer.allocate(32);

                        final Future<Integer> readFuture = channel.read(buffer);
                        final Integer number = readFuture.get();

                        System.out.println("Server: Message received: " + new String(buffer.array()));
                    }

                } catch (InterruptedException | ExecutionException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable ex, Void atttribute) {
                System.out.println("Server: CompletionHandler exception");
                ex.printStackTrace();
            }

        });

        while (true) {
            // wait – Prevents the program from

            // terminating before the client can connect
        }
    }

    // Using the Future object in a server
    // The AsynchronousServerSocketChannel class' accept method is overloaded. There
    // is a no argument method that accepts a connection and returns a Future object for the
    // channel. The Future object's get method will return an AsynchronousSocketChannel
    // object for the connection. The advantage of this approach is that it returns an
    // AsynchronousSocketChannel object, which can be used in other contexts.
    // Instead of using the accept method, which uses a CompletionHandler, we can use the
    // following sequence to do the same thing.
    public static void test1() throws Exception {
        final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open();
        final InetSocketAddress address = new InetSocketAddress("localhost", 5000);
        listener.bind(address);

        final Future<AsynchronousSocketChannel> future = listener.accept();

        // The supportedOptions method returns a set of options used by the
        // AsynchronousServerSocketChannel class. 
        // The getOption method will return the value of the option.
        final Set<SocketOption<?>> options = listener.supportedOptions();
        for (final SocketOption<?> socketOption : options) {
            System.out.println(socketOption.toString() + ": " + listener.getOption(socketOption));
        }
        
        // The options can be set using the setOption method. This method takes the name of the
        // option and a value. The following illustrates how to set the receive buffer size to 16,384 bytes:
        listener.setOption(StandardSocketOptions.SO_RCVBUF, 16384);

        final AsynchronousSocketChannel worker = future.get();

        while (true) {
            // Wait
            System.out.println("Server: Receiving ...");
            final ByteBuffer buffer = ByteBuffer.allocate(32);
            final Future<Integer> readFuture = worker.read(buffer);
            final Integer number = readFuture.get();
            System.out.println("Server: Message received: " + new String(buffer.array()));
        }
    }

}
