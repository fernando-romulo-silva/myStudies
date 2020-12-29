package br.com.fernando.enthuware.createJavaApplicationsWebSockets;

import java.net.URI;
import java.util.logging.SocketHandler;

import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.RemoteEndpoint;
import javax.websocket.WebSocketContainer;
import javax.websocket.Session;

public class Question05 {

    public static void test01() throws Exception {

	URI uri = new URI("ws://www.example.com/api/employee");
	WebSocketContainer container = ContainerProvider.getWebSocketContainer();
	Session session = container.connectToServer(new SocketHandler(), uri);

	RemoteEndpoint.Async remote = session.getAsyncRemote();

	Employee employee = new Employee("Nina Taylor");
	employee.setStatus(Status.FULLTIME);
	
	// send employee object

	session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Goodbye"));

	// Which client-side Java method will send the employee object to the WebSocket Server Endpoint?
    }

    // A - session.post(employee);
    //
    // B - container.send(employee);
    //
    // C - remote.sendObject(employee);
    //
    // D - session.send(employee);
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
    // The correct answer is C

    public static enum Status {
	FULLTIME
    }

    public static class Employee {

	public Employee(String string) {
	}

	public void setStatus(Status fulltime) {
	}
    }
}
