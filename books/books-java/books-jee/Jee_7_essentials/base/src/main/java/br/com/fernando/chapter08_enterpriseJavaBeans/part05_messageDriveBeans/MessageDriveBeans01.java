package br.com.fernando.chapter08_enterpriseJavaBeans.part05_messageDriveBeans;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.jeecontainer.resources.JmsType;
import org.myembedded.jeecontainer.resources.MyEmbeddedJmsConnectionFactory.MyEmbeddedJmsConnectionFactoryBuilder;
import org.myembedded.jeecontainer.resources.MyEmbeddedJmsDestination.MyEmbeddedJmsDestinationBuilder;
import org.myembedded.pack.EmbeddedEar;
import org.myembedded.pack.EmbeddedEjb;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;
import org.myembedded.pack.JeeVersion;

public class MessageDriveBeans01 {

    // ==================================================================================================================================================================
    // A message-driven bean (MDB) is a container-managed bean that is used to process messages asynchronously.
    // An MDB can implement any messaging type, but is most commonly used to process Java Message Service (JMS) messages.
    //
    // These beans are stateless and are invoked by the container when a JMS message arrives at the destination.
    // A session bean can receive a JMS message synchronously, but a message-driven bean can receive a message asynchronously.
    //
    // A message selector allows a JMS consumer to be more selective about the messages that it receives from a particular topic or queue.
    //
    // A message selector uses message properties and headers as criteria in conditional expressions.
    // These expressions use Boolean logic to declare which messages should be delivered to a client.
    //
    @MessageDriven(mappedName = Resources.CONTAINER_MANAGED_DESTINATION_JNDI, activationConfig = { //

	    // For example, if you want to get only those messages whose order amount is > 10000, you can use messageSelector as follows.
	    @ActivationConfigProperty( //
		    propertyName = "messageSelector", //
		    propertyValue = "OrderAmount > 10000"), //
	    //
	    // The mappedName attribute specifies the JNDI name of the JMS destination from which the bean will consume the message.
	    //
	    @ActivationConfigProperty( //
		    propertyName = "destinationLookup", //
		    propertyValue = Resources.CONTAINER_MANAGED_DESTINATION_JNDI), //
	    //
	    @ActivationConfigProperty( //
		    propertyName = "destinationType", //
		    propertyValue = "javax.jms.Queue"), //
	    //
	    // Transaction attributes
	    // Use an annotation @ActivationConfigProperty(propertyName="transactionTimeout", propertyValue="xxx") to specify custom transaction timeout for MDB like
	    @ActivationConfigProperty( //
		    propertyName = "transactionTimeout", //
		    propertyValue = "500"),

	    // Message-driven bean ActivationConfig properties
	    //
	    // acknowledgeMode -> Specifies JMS acknowledgment mode for the message delivery when bean-managed transaction demarcation is used.
	    // Supported values are Auto_acknowledge (default) or Dups_ok_acknowledge.
	    //
	    // messageSelector -> Specifies the JMS message selector to be used in determining which messages an MDB receives.
	    //
	    // destinationType -> Specifies whether the MDB is to be used with a Queue or Topic. Supported values are javax.jms.Queue or javax.jms.Topic.
	    //
	    // subscriptionDurability -> If MDB is used with a Topic, specifies whether a durable or nondurable subscription is used.
	    // Supported values are Durable or NonDurable
	    //
	    // int acknowledgeMode -> The type of acknowledgement when using non-transacted JMS – valid values are, AUTO_ACKNOWLEDGE, DUPS_OK_ACKNOWLEDGE.
	    // Default is AUTO_ACKNOWLEDGE
	    //
	    // String clientID -> the client id of the connection
	    //
	    // boolean subscriptionDurability -> Whether topic subscriptions are durable – valid values Durable or NonDurable.
	    // Default is NonDurable
    })
    //
    // The bean must implement the MessageListener interface, which provides only one method, onMessage.
    // EJB 3.2 allows a message-driven bean to implement a listener interface with no methods.
    // A bean that implements a no-method interface exposes as message listener methods all public methods of the bean class and of any superclasses
    // except the java.lang.Object.

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public static class MessageReceiverAsync implements MessageListener {
	// A single message-driven bean can process messages from multiple clients concurrently.
	//
	// Just like stateless session beans, the container can pool the instances and allocate enough bean instances to handle the number of messages at a given time.
	// All instances of the bean are treated equally.
	//
	// MessageDrivenContext may be injected in a message-driven bean.
	// This provides access to the runtime message-driven context that is associated with the instance for its lifetime:
	@Resource
	private MessageDrivenContext mdc;

	// Even though a message-driven bean cannot be invoked directly by a session bean, it can still invoke other session beans.
	//
	// A message-driven bean can also send JMS messages.
	//
	// A message is delivered to a message-driven bean within a transaction context, so all operations within the onMessage method are part of a single transaction.
	//
	// The transaction context is propagated to the other methods invoked from within onMessage.
	//
	// This method is called by the container whenever a message is received by the message-driven bean and contains the application-specific business logic.
	// This code shows how a text message is received by the onMessage method and how the message body can be retrieved and displayed:
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

    // ==================================================================================================================================================================
    public static class Resources {

	public static final String CONTAINER_MANAGED_DESTINATION_NAME = "myContainerQueue";

	public static final String CONTAINER_MANAGED_DESTINATION_JNDI = "jms/myContainerQueue"; // java:app/

	public static final String CONTAINER_MANAGED_CONNECTION_FACTORY_NAME = "myContainerQueueFactory";

	public static final String CONTAINER_MANAGED_CONNECTION_FACTORY_JNDI = "jms/myContainerQueueFactory"; // java:app/

	public static final int MESSAGE_RECEIVE_TIMEOUT_MILILS = 10000;
    }

    // ==================================================================================================================================================================
    @Stateless
    public static class MessageSenderSync {

	@Inject
	@JMSConnectionFactory(Resources.CONTAINER_MANAGED_CONNECTION_FACTORY_JNDI)
	private JMSContext context;

	@Resource(lookup = Resources.CONTAINER_MANAGED_DESTINATION_JNDI)
	private Destination syncQueue;

	public void sendMessage(String msg) throws Exception {
	    final JMSProducer jmsProducer = context.createProducer();

	    jmsProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	    jmsProducer.setPriority(Message.DEFAULT_PRIORITY);

	    // message 01
	    final Message message01 = context.createTextMessage(msg);
	    message01.setIntProperty("OrderAmount", 12000);
	    jmsProducer.send(syncQueue, message01); // send it!

	    // message 02
	    final Message message02 = context.createTextMessage(msg);
	    message02.setIntProperty("OrderAmount", 9000);
	    jmsProducer.send(syncQueue, message02); // send it!

	    // message 03
	    final Message message03 = context.createTextMessage(msg);
	    message03.setIntProperty("OrderAmount", 19000);
	    jmsProducer.send(syncQueue, message03); // send it!
	}
    }

    // ==================================================================================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private MessageSenderSync messageSenderSync;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    try {
		messageSenderSync.sendMessage("It's a text message.");
	    } catch (final Exception e) {
		throw new ServletException(e);
	    }
	}
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
	startVariables();

	try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

	    final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
	    war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
	    war.addClasses(ServletPrincipal.class);

	    final EmbeddedEjb ejb = new EmbeddedEjb(EMBEDDED_JEE_TEST_APP_NAME);
	    ejb.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
	    ejb.addClasses(MessageReceiverAsync.class, MessageSenderSync.class, Resources.class);

	    final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
	    ear.addModules(war);
	    ear.addModules(ejb);

	    final File earFile = ear.exportToFile(APP_FILE_TARGET);

	    final MyEmbeddedJmsConnectionFactoryBuilder connectionFactoryBuilder = new MyEmbeddedJmsConnectionFactoryBuilder(Resources.CONTAINER_MANAGED_CONNECTION_FACTORY_NAME, JmsType.QUEUE);
	    embeddedJeeServer.addJmsConnectionFactory(connectionFactoryBuilder.build());

	    final MyEmbeddedJmsDestinationBuilder destinationBuilder = new MyEmbeddedJmsDestinationBuilder(Resources.CONTAINER_MANAGED_DESTINATION_NAME, JmsType.QUEUE);
	    embeddedJeeServer.addJmsDestination(destinationBuilder.build());

	    embeddedJeeServer.start(HTTP_PORT);

	    embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, earFile.getAbsolutePath());

	    final HttpClient httpClient = HttpClientBuilder.create().build();
	    final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));

	    System.out.println(response);

	} catch (final IOException ex) {
	    System.out.println(ex);
	}

	downVariables();
    }

}
