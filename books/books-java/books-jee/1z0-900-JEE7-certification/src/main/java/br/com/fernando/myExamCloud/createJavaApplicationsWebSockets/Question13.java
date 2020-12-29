package br.com.fernando.myExamCloud.createJavaApplicationsWebSockets;

import java.net.URI;
import java.util.logging.SocketHandler;

import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class Question13 {
    static class Customer {
	Customer(String name) {
	}

	void setActive(boolean b) {
	}
    }

    // Given:

    public static void test() throws Exception {

	URI uri = new URI("ws://www.myexamcloud.com/api/customer");
	WebSocketContainer container = ContainerProvider.getWebSocketContainer();
	Session session = container.connectToServer(new SocketHandler(), uri);
	RemoteEndpoint.Async remote = session.getAsyncRemote();
	Customer customer = new Customer("Brian Christoper");
	customer.setActive(true);
	
	// send customer object here
	
	session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Goodbye"));
    }

    // Which client-side Java method will send the customer object to the WebSocket Server Endpoint?
    //
    // Choice A
    // session.post(customer);
    //
    // Choice B
    // container.send(customer);.
    //
    // Choice C
    // session.send(customer);
    //
    // Choice D
    // remote.sendObject(customer);
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
    // Choice D is correct.
    //
    // The RemoteEndpoint.Async.sendObject() initiates the asynchronous transmission of Customer object.
    //
    // Choice A is incorrect. There is no method named post in javax.websocket.Session.
    //
    // Choice B is incorrect. There is no method named send in javax.websocket.WebSocketContainer.
    //
    // Choice C is incorrect. There is no method named send in javax.websocket.Session.
}
