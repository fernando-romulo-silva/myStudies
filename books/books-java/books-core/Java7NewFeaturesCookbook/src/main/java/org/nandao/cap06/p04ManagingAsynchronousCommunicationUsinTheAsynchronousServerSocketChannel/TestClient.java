package org.nandao.cap06.p04ManagingAsynchronousCommunicationUsinTheAsynchronousServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;
import java.util.concurrent.Future;

// Next, create a second console application that will act as a client. It will use the open
// method to create an AsynchronousSocketChannel object and then connect to the
// server. A java.util.concurrent package's Future object's get method is used to
// block until the connection is complete, and then a message is sent to the server.

public class TestClient {

    public static void main(String[] args) throws Exception {
        final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        final InetSocketAddress address = new InetSocketAddress("localhost", 5000);
        final Future<Void> future = client.connect(address);

        System.out.println("Client: Waiting for the connection to complete");
        future.get();
        String message;

        do {
            System.out.print("Enter a message: ");
            final Scanner scanner = new Scanner(System.in);
            message = scanner.nextLine();

            System.out.println("Client: Sending ...");
            final ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            System.out.println("Client: Message sent: " + new String(buffer.array()));
            client.write(buffer);

        } while (!"quit".equals(message));
    }
}