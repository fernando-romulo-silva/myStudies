package br.com.fernando.myExamCloud.createJavaApplicationsWebSockets;

import java.nio.ByteBuffer;

import javax.websocket.OnMessage;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

public class Question04 {

    // You need to develop a chat application, which allows the display of emoticons and images together with text messages.
    // How should you configure a WebSocket endpoints to receive both text and binary messages?
    //
    // Choice A
    // Create two @onMessage methods in the same endpoint with appropriate parameter types.
    //
    // Choice B
    // Define the @onMessage methods in your endpoint with Object as parameter and check the actual type in your code.
    //
    // Choice C
    // You can achieve this only by creating separate WebSocket endpoints for each message type.
    //
    // Choice D
    // Create two @onMessage methods, each with appropriate decoder attribute in the same endpoint
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
    // Choice A is correct
    //
    // The OnMessage annotation designates methods that handle incoming messages. 
    // You can have at most three methods annotated with @OnMessage in an endpoint, one for each message type: text, binary, and pong. 
    // The following example demonstrates how to designate methods to receive all three types of messages:
    @ServerEndpoint("/receive")
    public class ReceiveEndpoint {
	@OnMessage
	public void textMessage(Session session, String msg) {
	    System.out.println("Text message: " + msg);
	}

	@OnMessage
	public void binaryMessage(Session session, ByteBuffer msg) {
	    System.out.println("Binary message: " + msg.toString());
	}

	@OnMessage
	public void pongMessage(Session session, PongMessage msg) {
	    System.out.println("Pong message: " + msg.getApplicationData().toString());
	}
    }
}
