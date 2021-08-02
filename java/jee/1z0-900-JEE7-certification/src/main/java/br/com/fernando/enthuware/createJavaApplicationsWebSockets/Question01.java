package br.com.fernando.enthuware.createJavaApplicationsWebSockets;

import java.nio.ByteBuffer;

import javax.websocket.OnMessage;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

public class Question01 {
    // Identify valid WebSocket message types for which you can register handlers.
    // You had to select 2 options
    //
    // A - Error Message
    //
    // B - Binary Message
    //
    // C - Pong Message
    //
    // D - Ping Message
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
    //
    // The B and C are correct
    //
    // The three valid websocket message types are TEXT, BINARY, and PONG.
    //
    // The WebSocket API limits the registration of MessageHandlers per Session to be one MessageHandler per native websocket message type.
    //
    // In other words, the developer can only register at most one MessageHandler for incoming text messages, one MessageHandler for incoming binary messages,
    // and one MessageHandler for incoming pong messages.
    
    @ServerEndpoint("/receive")
    public class ReceiveEndpoint {
	
	@OnMessage // TEXT
	public void textMessage(Session session, String msg) {
	    System.out.println("Text message: " + msg);
	}

	@OnMessage // BINARY
	public void binaryMessage(Session session, ByteBuffer msg) {
	    System.out.println("Binary message: " + msg.toString());
	}

	@OnMessage // PONG
	public void pongMessage(Session session, PongMessage msg) {
	    System.out.println("Pong message: " + msg.getApplicationData().toString());
	}
    }
}
