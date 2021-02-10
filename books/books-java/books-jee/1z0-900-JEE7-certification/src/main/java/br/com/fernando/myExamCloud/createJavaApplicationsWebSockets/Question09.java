package br.com.fernando.myExamCloud.createJavaApplicationsWebSockets;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

public class Question09 {

    // Given:

    @ServerEndpoint("/orders/{cust-id}")
    public class OrderServer {

	@OnMessage
	public void processOrder(@PathParam("cust-id") String custID, String message, Session session) {
	    // process order from the given customer here
	}
    }

    // Which of the following URIs connect to this endpoint? [ Choose three ]
    //
    //
    // Choice A - /orders/1234
    //
    // Choice B - /orders/abcd
    //
    // Choice C - /order/xyzz
    //
    // Choice D - /orders/null
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
    // Choice A, B and D are correct answers.
    //
    // If URI-templates are used in the value attribute, the developer may retrieve the variable path segments using the @PathParam annotation.

}
