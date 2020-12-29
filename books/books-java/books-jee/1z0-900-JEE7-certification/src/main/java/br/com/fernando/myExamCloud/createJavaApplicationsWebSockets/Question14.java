package br.com.fernando.myExamCloud.createJavaApplicationsWebSockets;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import javax.websocket.server.ServerEndpoint;

public class Question14 {

    // Which method determine if the Decoder will match the incoming message?
    //
    // Choice A
    // isDecode()
    //
    // Choice B
    // willDecode()
    //
    // Choice C
    // canDecode()
    //
    // Choice D
    // acceptDecode()
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
    // Choice B is correct.
    //
    // The decoders attribute contains a (possibly empty) list of Java classes that are to act as decoder components for this endpoint.
    // These classes must implement some form of the Decoder interface, and have public no-arg constructors and be visible within the
    // classpath of the application that this websocket endpoint is part of.
    //
    // On Decoder implementations that have it, the implementation must use the willDecode() method on the decoder to determine if the Decoder will
    // match the incoming message.

    public class Message {

    }

    public class MessageTextDecoder implements Decoder.Text<Message> {
	@Override
	public void init(EndpointConfig ec) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public Message decode(String string) throws DecodeException {
	    // Read message...

	    return new Message();
	}

	@Override
	public boolean willDecode(String string) {
	    // Determine if the message can be converted into either a
	    // MessageA object or a MessageB object...
	    return true;
	}
    }

    @ServerEndpoint(value = "/myendpoint", decoders = { MessageTextDecoder.class })
    public class EncDecEndpoint {

    }

}
