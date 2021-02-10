package br.com.fernando.myExamCloud.createJavaApplicationsWebSockets;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

public class Question10 {

    // Which are valid WebSocket Endpoint Lifecycle Annotations?
    // [ Choose four ]
    //
    // Choice A - Init
    //
    // Choice B - OnOpen
    //
    // Choice C - Service
    //
    // Choice D - OnMessage
    //
    // Choice E - Destroy
    //
    // Choice F - OnError
    //
    // Choice G - OnClose
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
    // Choice B, D, F and G are correct answers.

    @ServerEndpoint(value = "/websocket")
    public static class MyEndpoint {

	@OnOpen // Connection opened
	public void open(Session session, EndpointConfig conf) {
	}

	@OnMessage(maxMessageSize = 6) // Message received
	public String echoText(String data) {
	    return data;
	}
	
	@OnError // Connection closed
	public void onError(Throwable t) {
	    t.printStackTrace();
	}
	
	@OnClose // Connection error
	public void onClose(CloseReason reason) {
	    System.out.println("CLOSED: " + reason.getCloseCode() + ", " + reason.getReasonPhrase());
	}	
    }

}
