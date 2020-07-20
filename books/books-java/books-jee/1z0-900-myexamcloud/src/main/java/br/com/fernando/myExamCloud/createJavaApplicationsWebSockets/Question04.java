package br.com.fernando.myExamCloud.createJavaApplicationsWebSockets;

import java.io.IOException;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
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
    // Explanation :
    // Choice D is correct.
    //
    // This method level annotation can be used to make a Java method receive incoming web socket messages.
    // Each websocket endpoint may only have one message handling method for each of the native websocket
    // message formats: text, binary and pong.
    //
    // Methods using this annotation are allowed to have parameters of types described below, otherwise
    // the container will generate an error at deployment time.

    public class Message {
	private String from;
	private String to;
	private String content;
	private byte[] image;
	// standard constructors, getters, setters
    }

    public class MessageEncoder implements Encoder.Text<Message> {

	@Override
	public String encode(Message message) throws EncodeException {
	    return ""; // encode message to string
	}

	@Override
	public void init(EndpointConfig endpointConfig) {
	    // Custom initialization logic
	}

	@Override
	public void destroy() {
	    // Close resources
	}
    }

    public class MessageDecoder implements Decoder.Text<Message> {

	@Override
	public Message decode(String s) throws DecodeException {
	    return null; // decode string to message object
	}

	@Override
	public boolean willDecode(String s) {
	    return (s != null);
	}

	@Override
	public void init(EndpointConfig endpointConfig) {
	    // Custom initialization logic
	}

	@Override
	public void destroy() {
	    // Close resources
	}
    }

    @ServerEndpoint( //
	    value = "/chat/{username}", //
	    decoders = MessageDecoder.class, //
	    encoders = MessageEncoder.class //
    )
    public static class MyEndpointText {

	@OnMessage
	public void onMessage(final Session session, final Message message) throws IOException {

	}
    }
}
