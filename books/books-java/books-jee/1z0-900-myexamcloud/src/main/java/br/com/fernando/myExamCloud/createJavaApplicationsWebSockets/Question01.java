package br.com.fernando.myExamCloud.createJavaApplicationsWebSockets;

import java.io.IOException;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.OnMessage;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

public class Question01 {

    // A developer want to create a server endpoint to wait for text messages.
    // Which of the following code can achieve this goal?
    //
    //
    // Choice A
    public class MyWebSocketA extends Endpoint {
	@Override
	public void onOpen(Session session, EndpointConfig ec) {
	    final RemoteEndpoint.Basic remote = session.getBasicRemote();

	    session.addMessageHandler(new MessageHandler.Whole<String>() {

		public void onMessage(String text) {
		    try {
			remote.sendText("Your message (" + text + ") has been recieved. Thanks !");
		    } catch (IOException ioe) {
			ioe.printStackTrace();
		    }
		}
	    });
	}
    }

    //
    // Choice B
    @ServerEndpoint("/welcome")
    public class MyWebSocketB {
	@OnMessage
	public String handleMessage(String message) {
	    return "Your message (" + message + ") has been recieved. Thanks !";
	}
    }
    //
    // Choice C
    // Both A and B
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // Choice C is correct.
    //
    // The endpoint class can be implemented by API classes or by annotations.
    // The Choice A code uses Websocket API classes and Choice B uses WebSocket API annotations.
    //
    // The class level @ServerEndpoint annotation indicates that a Java class is to become a websocket endpoint at runtime.
    //
    // Developers may use the value attribute to specify a URI mapping for the endpoint.
}
