package br.com.fernando.enthuware.implementBusinessLogicUsingEJBs;

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

public class Question03 {
    
    // What is true about Message-Driven Beans (MDBs)?
    // 	Select 1 option(s):
    //
    // A
    // MDBs can participate in transactions.
    //
    // B
    // MDBs are invoked synchronously.
    //
    // C
    // Each MDBs can process messages only from a single client.
    //
    // D
    // MDBs retain data caches between client calls.
    //
    //
    //
    //
    //
    // 
    //
    //
    // The correct answer is A
    // Only the NOT_SUPPORTED and REQUIRED transaction attributes may be used for message-driven bean message listener methods. 
    // The use of the other transaction attributes is not meaningful for message driven bean message listener methods because 
    // there is no pre-existing client transaction context (REQUIRES_NEW, SUPPORTS) and no client to handle exceptions (MANDATORY, NEVER).
    
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
	    
	    // Use an annotation @ActivationConfigProperty(propertyName="transactionTimeout", propertyValue="xxx") to specify custom transaction timeout for MDB like
	    @ActivationConfigProperty(propertyName = "transactionTimeout", propertyValue="500"),

    })
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
	    } catch (JMSException ex) {
		mdc.setRollbackOnly();
		Logger.getLogger(MessageReceiverAsync.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
}
