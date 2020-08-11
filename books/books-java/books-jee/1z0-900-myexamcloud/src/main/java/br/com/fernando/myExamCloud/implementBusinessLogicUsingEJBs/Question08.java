package br.com.fernando.myExamCloud.implementBusinessLogicUsingEJBs;

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

public class Question08 {
    // You are developing a message-listener method of an EJB 3.x message-driven bean.
    // You need to make sure that the message receipt is immediately rolled back in case the message listener method is aborted with a runtime exception.
    // Select the best design approach
    //
    // Choice A
    // Use Container-Managed Transactions with transaction attribute REQUIRED.
    //
    // Choice B
    // Use Container-Managed Transactions with transaction attribute NOT_SUPPORTED.
    //
    // Choice C
    // Use Bean-Managed Transactions and the JMS API for message acknowledgement.
    //
    // Choice D
    // Use Bean-Managed Transactions and write a try-catch-finally block that calls UserTransaction.rollback
    // in case of a RuntimeException.
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
    // Choice A is correct.
    //
    // Based on the requirements, CMT with REQUIRED transaction attribute is the correct answer.
    // REQUIRED transaction attribute run a method within the transaction and rollback if there is any runtime exception.

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
