package br.com.fernando.enthuware.createJavaApplicationsWebSockets;

import java.nio.ByteBuffer;

import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

public class Question03 {

    // You need to exchange large binary messages using chunks in a WebSocket application.
    //
    // Identify two ways in which you can receive partial messages.
    // [ Choose two ]
    //
    // Choice A
    // Define an @OnMessage method with a single MimePart parameter.
    //
    // Choice B
    // Use a ChunkListener interface implementation.
    //
    // Choice C
    // Use a MessageHandler.Partial<ByteBuffer> interface implementation.
    //
    // Choice D
    // Define an @OnMessage method with byte [] as the first parameter and a boolean as the second parameter.
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // Choice C and D are correct answers.
    //
    // @OnMessage
    //
    // This method level annotation can be used to make a Java method receive incoming web socket messages.
    // Each websocket endpoint may only have one message handling method for each of the native websocket message formats: text, binary and pong.
    // Methods using this annotation are allowed to have parameters of types described below, otherwise the container will generate an error at deployment time.
    //
    // MessageHandler
    //
    // Developers implement MessageHandlers in order to receive incoming messages during a web socket conversation. Each web socket session uses no more
    // than one thread at a time to call its MessageHandlers.
    //
    // This means that, provided each message handler instance is used to handle messages for one web socket session, at most one thread at a time
    // can be calling any of its methods.
    //
    // Developers who wish to handle messages from multiple clients within the same message handlers may do so by adding the same instance as a
    // handler on each of the Session objects for the clients.
    //
    // In that case, they will need to code with the possibility of their MessageHandler being called concurrently by multiple threads, each one arising
    // from a different client session. The nested classes:
    //
    // MessageHandler.Partial<T>
    //
    // This kind of handler is notified by the implementation as it becomes ready to deliver parts of a whole message.
    //
    //
    // MessageHandler.Whole<T>
    //
    // This kind of handler is notified by the container on arrival of a complete message.

    @ServerEndpoint(value = "/encoder")
    public static class MyEndpoint {

	@OnOpen
	public void onOpen(final Session session, EndpointConfig ec) {

	    final MessageHandler.Partial<ByteBuffer> handler = new MessageHandler.Partial<ByteBuffer>() {

		@Override
		public void onMessage(ByteBuffer partialMessage, boolean last) {

		}
	    };

	    session.addMessageHandler(handler);
	}

	// (The Boolean parameter is true if the part received is the last part, and false otherwise).:
	@OnMessage
	public void processUpload(byte[] b, boolean last, Session session) {
	    // process partial data here, which check on last to see if these is more on the way
	}
    }

}
