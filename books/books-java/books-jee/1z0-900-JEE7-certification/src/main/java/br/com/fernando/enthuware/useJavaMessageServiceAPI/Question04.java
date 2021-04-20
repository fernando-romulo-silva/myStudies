package br.com.fernando.enthuware.useJavaMessageServiceAPI;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class Question04 {

    // You need to develop a JMS message-driven bean that responds to javax.jms.TextMessage messages.
    // Which of the following statements are true? [ Choose two ]
    //
    // Choice A
    // The developer must implement the ejbCreate method.
    //
    // Choice B
    // The developer does NOT need to create a business interface for the bean.
    //
    // Choice C
    // The developer must implement a method that declares javax.jms.TextMessage as an argument.
    //
    // Choice D
    // The message-driven bean class must implement methods of the javax.jms.MessageListener interface
    //
    // Choice E
    // The message-driven bean class must implement methods of the javax.ejb.MessageDrivenBean interface.
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
    // Choice B and D are correct answers.
    //
    // The requirements of a message-driven bean class:
    //
    // 1 It must implement the MessageDrivenBean and MessageListener interfaces.
    // 2 The class must be defined as public.
    // 3 The class cannot be defined as abstract or final.
    // 4 It must implement one onMessage method.
    // 5 It must contain a public constructor with no arguments.
    // 6 It must not define the finalize method.
    //
    // Unlike session and entity beans, message-driven beans do not have the remote or local interfaces that define client access.
    // Client components do not locate message-driven beans and invoke methods on them.
    // Although message-driven beans do not have business methods, they may contain helper methods that are invoked internally by the onMessage method.
    //
    public static final String CONTAINER_MANAGED_DESTINATION_JNDI = "jms/myContainerQueue"; // java:app/

    @MessageDriven(mappedName = CONTAINER_MANAGED_DESTINATION_JNDI)
    public static class MessageReceiverAsync implements MessageListener { // 1, 2 & 3

	// 5
	
	// 6

	@Override
	public void onMessage(final Message message) { // 4
	    try {
		TextMessage tm = (TextMessage) message;
		System.out.println("Message received async (from MessageReceiverAsync): " + tm.getText());

	    } catch (JMSException ex) {
		Logger.getLogger(MessageReceiverAsync.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

}
