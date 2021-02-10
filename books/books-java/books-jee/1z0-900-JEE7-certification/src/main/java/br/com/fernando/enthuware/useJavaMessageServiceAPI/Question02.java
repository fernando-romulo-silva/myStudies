package br.com.fernando.enthuware.useJavaMessageServiceAPI;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class Question02 {

    // Given the code fragment:
    public void onMessage(Message msg) {
	try {
	    // get property names
	} catch (Exception e) {
	}
    }

    // How can you get all property names of a JMS message in the JMS consumer
    // onMessage operation?
    // You had to select 1 option
    //
    // A
    // String[] props = msg.getPropertyNames();
    //
    // B
    // Enumeration props = msg.getPropertyNames();
    //
    // C
    // Iterator props = msg.getPropertyNames();
    //
    // D
    // List<String> props = msg.getProperties();
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
    // The correct answer is B
    //
    // Enumeration getPropertyNames() Returns an Enumeration of all the property names.
    // This is only method in JMSMessage that provides you a way to go through all the properties.
    public static final String CONTAINER_MANAGED_DESTINATION_JNDI = "jms/myContainerQueue"; // java:app/

    @MessageDriven(mappedName = CONTAINER_MANAGED_DESTINATION_JNDI, activationConfig = { //

	    @ActivationConfigProperty( //
		    propertyName = "messageSelector", //
		    propertyValue = "OrderAmount > 10000"), //
	    //
	    @ActivationConfigProperty( //
		    propertyName = "destinationLookup", //
		    propertyValue = CONTAINER_MANAGED_DESTINATION_JNDI), //
	    //
	    @ActivationConfigProperty( //
		    propertyName = "destinationType", //
		    propertyValue = "javax.jms.Queue"), //
	    //
	    @ActivationConfigProperty(propertyName = "transactionTimeout", propertyValue = "500"), })
    //
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public static class MessageReceiverAsync implements MessageListener {

	@Resource
	private MessageDrivenContext mdc;

	@Override
	public void onMessage(final Message message) {
	    try {
		TextMessage tm = (TextMessage) message;
		System.out.println("Message received async (from MessageReceiverAsync): " + tm.getText());

		@SuppressWarnings("unchecked")
		Enumeration<String> props = message.getPropertyNames();

		System.out.println(props);

		System.out.println(message.getStringProperty("messageSelector"));

	    } catch (JMSException ex) {
		mdc.setRollbackOnly();
		Logger.getLogger(MessageReceiverAsync.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

}
