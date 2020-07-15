package br.com.fernando.myExamCloud.createJavaApplicationsWebSockets;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

public class Question02 {

    @ServerEndpoint("/welcome")
    public class MyWebSocket {

	// Code here
	public void handleNewConnection() {
	    System.out.print("New connection recieved");
	}

	@OnMessage
	public String handleMessage(String message) {
	    return "Your message (" + message + ") has been recieved. Thanks !";
	}
    }

    // Which annotation inserted at // Code here
    // will enabled handleNewConnection method must be called when there is a new connection?
    //
    // Choice A - @OnOpen
    //
    // Choice B - @OnClose
    //
    // Choice C - @OnNewConnection
    //
    // Choice D - @Init
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
    // Choice A is correct.
    //
    // The method level @OnOpen and @OnClose annotations allow the developers to decorate methods on their @ServerEndpoint annotated
    // Java class to specify that they must be called by the implementation when the resulting endpoint receives a new connection
    // from a peer or when a connection from a peer is closed, respectively.
}
