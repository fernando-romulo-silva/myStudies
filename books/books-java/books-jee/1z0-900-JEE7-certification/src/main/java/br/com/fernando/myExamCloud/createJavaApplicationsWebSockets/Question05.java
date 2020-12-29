package br.com.fernando.myExamCloud.createJavaApplicationsWebSockets;

import javax.websocket.server.ServerEndpoint;

public class Question05 {

    // Which of the following classes are valid?
    // Choose two
    //
    // Choice A
    @ServerEndpoint("/hello")
    public abstract class HelloServerA {
	public HelloServerA() {
	}
    }

    //
    // Choice B
    @ServerEndpoint("/hello")
    public class HelloServerB {
	private HelloServerB() {
	}
    }

    //
    // Choice C
    @ServerEndpoint("/hello")
    public final class HelloServerC {
	public HelloServerC() {
	}
    }

    //
    // Choice D
    @ServerEndpoint("/hello")
    public class HelloServerD {
	public HelloServerD() {
	}
    }

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
    // Choice C and D are correct answers.
    //
    // @ServerEndpoint
    //
    // This class level annotation signifies that the Java class it decorates must be deployed by the implementation as a websocket server endpoint and
    // made available in the URI-space of the websocket implementation. The class must be public, concrete, and have a public no-args constructor.
    //
    // The class may or may not be final, and may or may not have final methods.
}
