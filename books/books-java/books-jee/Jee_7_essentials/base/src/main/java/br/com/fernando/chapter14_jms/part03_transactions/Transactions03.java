package br.com.fernando.chapter14_jms.part03_transactions;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sun.messaging.jms.JMSException;

public class Transactions03 {

    // In a Java EE web or EJB container, when there is an active JTA transaction in progress:
    //
    // * The argument sessionMode is ignored. The session will participate in the JTA transaction and will be committed or rolled back when
    // that transaction is committed or rolled back, not by calling the JMSContext’s commit or rollback methods.
    //
    // * Since the argument is ignored, developers are recommended to use createContext() instead of this method.
    //
    //
    //
    //
    // In the Java EE web or EJB container, when there is no active JTA transaction in progress:
    //
    // * The argument acknowledgeMode must be set to either of JMSContext.AUTO_ACKNOWLEDGE or JMSContext.DUPS_OK_ACKNOWLEDGE.
    // The session will be non-transacted and messages received by this session will be acknowledged automatically according to the value of acknowledgeMode.
    //
    public static class Producer implements Runnable {

	private Destination destination;

	private ConnectionFactory connectionFactory;

	public Producer(Destination destination, ConnectionFactory connectionFactory) {
	    this.destination = destination;
	    this.connectionFactory = connectionFactory;
	}

	@Override
	public void run() {
	    JMSContext jmsContext = null;

	    try {
		// We create an instance of JMSContext which internally creates a transacted session since
		// we pass JMSContext.SESSION_TRANSACTED as session mode while creating JMSContext.
		jmsContext = connectionFactory.createContext(JMSContext.SESSION_TRANSACTED);

		// we are calling “getTransacted” method on JMSContext instance.
		// This method will return true if a transacted session is created otherwise false
		System.out.println("Producer:" + jmsContext.getTransacted());

		JMSProducer jmsProducer = jmsContext.createProducer();

		// The messages send by the JMSProducer is stored by the JMSProvider untill commit is called.
		// Once commit is called JMSProvider will send the message to the destination.
		//
		// Between the start of the transaction and call of commit, we can send any number of messages.
		jmsProducer.send(destination, "Hello World 1");

		// we send a message and line 26 we finalize the send by calling “commit” method on JMSContext.
		jmsContext.commit();

		// We send another message in a new transaction but this time we didn’t called the commit and closed the JMSContext.
		// The JMSProvider will discard this message.
		jmsProducer.send(destination, "Hello World 2");

		// We can also call rollback by calling the “rollback” method on JMSContext.
		// When rollback is called, the JMSProvider will discard the messages.

	    } catch (Exception excep) {
		excep.printStackTrace();
	    } finally {
		if (jmsContext != null) {
		    jmsContext.close();
		}
	    }
	}
    }

    public static class Consumer implements Runnable {
	private Destination destination;
	private ConnectionFactory connectionFactory;

	public Consumer(Destination destination, ConnectionFactory connectionFactory) {
	    this.destination = destination;
	    this.connectionFactory = connectionFactory;
	}

	@Override
	public void run() {
	    JMSContext jmsContext = null;

	    try {
		jmsContext = connectionFactory.createContext();
		JMSConsumer jmsConsumer = jmsContext.createConsumer(destination);
		Message message = jmsConsumer.receive();
		System.out.println(((TextMessage) message).getText());
		jmsConsumer.close();
	    } catch (Exception excep) {
		excep.printStackTrace();
	    } finally {
		if (jmsContext != null) {
		    jmsContext.close();
		}
	    }
	}
    }

    public static void main(String[] args) throws NamingException, JMSException {
	Properties env = new Properties();
	
	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
	env.put(Context.PROVIDER_URL, "file:///C:/openmq5_1_1/temp");
	
	InitialContext initialContext = new InitialContext(env);
	ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("MyConnectionFactory");
	Destination destination = (Destination) initialContext.lookup("MyQueue");

	Producer producer = new Producer(destination, connectionFactory);
	Consumer consumer = new Consumer(destination, connectionFactory);

	Thread producerThread = new Thread(producer);
	Thread consumerThread = new Thread(consumer);

	producerThread.start();
	consumerThread.start();

	initialContext.close();
    }

}
