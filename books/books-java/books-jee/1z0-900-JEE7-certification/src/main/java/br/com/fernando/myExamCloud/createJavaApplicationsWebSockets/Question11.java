package br.com.fernando.myExamCloud.createJavaApplicationsWebSockets;

import java.io.IOException;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.OnMessage;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

public class Question11 {

    // You are assigned to develop WebSocket server endpoint to accept text messages.
    //
    // Which of the following are valid server endpoint to accept text messages? [ Choose two ]
    //
    //
    // Choice A
    /**
     * <pre>
     *        public class TextWebSocketA extends Endpoint {
     *        
     *    	@Override
     *    	public void onOpen(Session session, EndpointConfig ec) {
     *    	    final RemoteEndpoint.Basic remote = session.getBasicRemote();
     *    
     *    	    session.addMessageHandler(String.class, new MessageHandler.Whole<String>() {
     *    		public void onMessage(String text) {
     *    		    try {
     *    			remote.sendText("Got your message (" + text + "). Thanks !");
     *    		    } catch (IOException ioe) {
     *    			ioe.printStackTrace();
     *    		    }
     *    		}
     *    	    });
     *    	}
     *        }
     * </pre>
     */
    //
    // Choice B
    /**
     * <pre>
     *      @WebSocket("/hello")
     *      public class TextWebSocketB {
     *  	
     *          @OnMessage
     *  	public String handleMessage(String message) {
     *  	    return "Got your message (" + message + "). Thanks !";
     *  	}
     *      }
     * </pre>
     */
    //
    // Choice C
    /**
     * <pre>
     *       @ServerEndpoint("/hello")
     *       public class TextWebSocketC {
     *       
     *   	@OnMessage
     *   	public String handleMessage(String message) {
     *   	    return "Got your message (" + message + "). Thanks !";
     *   	}
     *       }
     * </pre>
     */
     //
     // Choice D
    /**
     * <pre>
     *       public class TextWebSocketD extends WebSocket {
     *   	
     *          @Override
     *   	public void onOpen(Session session, EndpointConfig ec) {
     *   	    final RemoteEndpoint.Basic remote = session.getBasicRemote();
     *   	    
     *              session.addMessageHandler(String.class, new MessageHandler.Whole<String>() {
     *   		public void onMessage(String text) {
     *   		    try {
     *   			remote.sendText("Got your message (" + text + "). Thanks !");
     *   		    } catch (IOException ioe) {
     *   			ioe.printStackTrace();
     *   		    }
     *   		}
     *   	    });
     *   	}
     *       }
     * </pre>
     */
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
    // Choice A and C are correct answers.
    //
    // Both are valid answers.
    //
    // Choice A uses only the API classes.
    //
    // Choice B uses annotations in the API.
    //
    // The class level @ServerEndpoint annotation indicates that a Java class is to become a websocket endpoint at runtime.
    public class TextWebSocketA extends Endpoint {

	@Override
	public void onOpen(Session session, EndpointConfig ec) {
	    final RemoteEndpoint.Basic remote = session.getBasicRemote();

	    session.addMessageHandler(new MessageHandler.Whole<String>() {
		public void onMessage(String text) {
		    try {
			remote.sendText("Got your message (" + text + "). Thanks !");
		    } catch (IOException ioe) {
			ioe.printStackTrace();
		    }
		}
	    });
	}
    }                                 
    
    @ServerEndpoint("/hello")
    public class TextWebSocketC {
	
	@OnMessage
	public String handleMessage(String message) {
	    return "Got your message (" + message + "). Thanks !";
	}
    }
    
}
