package br.com.fernando.chapter14_jms.part01_sendingAndReceivingMessage;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
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

import br.com.fernando.chapter14_jms.part01_sendingAndReceivingMessage.SendingMessage02Asyn.MyCompletionListener;

public class SendingMessage04Classic {

    public static class Resources {

        public static final String CLASSIC_CONTAINER_MANAGED_DESTINATION_QUEUE_NAME = "myClassicContainerQueue";

        public static final String CLASSIC_CONTAINER_MANAGED_DESTINATION_QUEUE_JNDI = "jms/myClassicContainerQueue"; // java:app/

        public static final String CLASSIC_CONTAINER_MANAGED_CONNECTION_QUEUE_FACTORY_NAME = "myClassicContainerQueueFactory";

        public static final String CLASSIC_CONTAINER_MANAGED_CONNECTION_QUEUE_FACTORY_JNDI = "jms/myClassicContainerQueueFactory"; // java:app/

        public static final int MESSAGE_RECEIVE_TIMEOUT_MILILS = 10000;
    }

    // =======================================================================================================================================================
    @Stateless
    public static class ClassicMessageSender {

        // ConnectionFactory is a JMS-administered object and is used to create a connection with a JMS provider.
        // QueueConnectionFactory or TopicConnectionFactory may be injected instead to perform Queue or Topic specific operations, respectively.
        @Resource(lookup = Resources.CLASSIC_CONTAINER_MANAGED_CONNECTION_QUEUE_FACTORY_JNDI)
        private ConnectionFactory connectionFactory;

        //
        // Destination (Queue and Topic) is also an administered object and encapsulates a provider-specific address.
        // A Queue or Topic may be injected here instead. You inject both of these objects by using @Resource and specifying the JNDI name of the resource.
        @Resource(mappedName = Resources.CLASSIC_CONTAINER_MANAGED_DESTINATION_QUEUE_JNDI)
        private Queue queue;

        public void sendMessage(String payload) throws JMSException {

            // A Connection represents an active connection to the provider and must be explicitly closed.
            try (final Connection connection = connectionFactory.createConnection()) {

                connection.start();

                // A Session object is created from the Connection that provides a transaction in which the producers and consumers send and receive messages as
                // an atomic unit of work.
                //
                // The first argument to the method indicates whether the session is transacted; the second argument indicates whether the consumer or the client
                // will acknowledge any messages it receives, and is ignored if the session is transacted.
                //
                // If the session is transacted, as indicated by a true value in the first parameter, then an explicit call to Session.commit is required in order
                // for the produced messages to be sent and for the consumed messages to be acknowledged.
                //
                // A transaction rollback, initiated by Session.rollback, means that all produced messages are destroyed, and consumed messages are recovered and
                // redelivered unless they have expired.
                //
                // The session must be explicitly closed
                try (final Session session = connection.createSession(Session.AUTO_ACKNOWLEDGE)) {
                    // Acknowledgment mode Description:
                    //
                    // Session.AUTO_ACKNOWLEDGE -> Session automatically acknowledges a client's receipt of a message either when the session has successfully returned
                    // from a call to receive or when the MessageListener session has called to process the message returns successfully.
                    //
                    // Session.CLIENT_ACKNOWLEDGE -> Client explicitly calls the Message.acknowledge method to acknowledge all consumed messages for a session.
                    //
                    // Session.DUPS_OK_ACKNOWLEDGE -> Instructs the session to lazily acknowledge the delivery of messages. This will likely result in the delivery of
                    // some duplicate messages (with theJMSRedelivered message header set to true).
                    // However, it can reduce the session overhead by minimizing the work the session does to prevent duplicates
                    //
                    // Use the session and the injected Destination object, inboundQueue in this case, to create a MessageProducer to send messages to the specified
                    // destination. A Topic or Queue may be used as the parameter to this method, as both inherit from Destination.
                    final MessageProducer messageProducer = session.createProducer(queue);

                    // Use one of the Session.createXXXMessage methods to create an appropriate message.
                    final TextMessage textMessage = session.createTextMessage(payload);

                    // You can use this code to send messages via both messaging models.
                    messageProducer.send(textMessage);
                    // 
                    // By default, a JMS provider ensures that a message is not lost in transit in case of a provider failure. 
                    // This is called a durable publisher/producer. 
                    // The messages are logged to stable storage for recovery from a provider failure. 
                    // However, this has performance overheads and requires additional storage for persisting the messages.
                    //
                    // If a receiver can afford to miss the messages, NON_PERSISTENT delivery mode may be specified. 
                    // A JMS provider must deliver a NON_PERSISTENT message at most once. This means it may lose the message, but it must not deliver it twice. 
                    // This does not require the JMS provider to store the message or otherwise guarantee that it is not lost if the provider fails.
                    //
                    // Using the JMS 1.1 API, this delivery mode can be specified:
                    messageProducer.send(textMessage, DeliveryMode.NON_PERSISTENT, 6, 5000);
                    // In this code, textMessage is the message to be sent with the NON_PERSISTENT delivery mode. 
                    // The third argument defines the priority of the message and the last argument defines the expiration time.
                }
            }
        }
    }

    // =======================================================================================================================================================
    // Java SE sender
    public static void javaSE_Sender() throws Exception {

        final Context context = new InitialContext();

        final ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(Resources.CLASSIC_CONTAINER_MANAGED_CONNECTION_QUEUE_FACTORY_JNDI);

        final Queue queue = (Queue) context.lookup(Resources.CLASSIC_CONTAINER_MANAGED_DESTINATION_QUEUE_JNDI);

        try (final Connection connection = connectionFactory.createConnection()) {

            final Session session = connection.createSession();

            final MessageProducer messageProducer = session.createProducer(queue);

            final TextMessage textMessage = session.createTextMessage("Hello world");

            final CountDownLatch latch = new CountDownLatch(1);

            final MyCompletionListener myCompletionListener = new MyCompletionListener(latch);

            messageProducer.send(textMessage, new MyCompletionListener(latch));

            System.out.println("Message sent, now waiting for reply");

            // at this point we can do something else before waiting for the reply this is not shown here
            //
            // now wait for the reply from the server
            latch.await();

            if (myCompletionListener.getException() == null) {
                System.out.println("Reply received from server");
            } else {
                throw myCompletionListener.getException();
            }
        } catch (final Exception e) {
            System.out.println("asyncSendClassic: " + e.getMessage());
        }
    }

    // =======================================================================================================================================================
    @Stateless
    public static class ClassicMessageReceiver {

        @Resource(lookup = Resources.CLASSIC_CONTAINER_MANAGED_CONNECTION_QUEUE_FACTORY_JNDI)
        private ConnectionFactory connectionFactory;

        @Resource(mappedName = Resources.CLASSIC_CONTAINER_MANAGED_DESTINATION_QUEUE_JNDI)
        private Queue queue;

        public String receiveMessage(int timeoutInMillis) throws JMSException, TimeoutException {
            String response = null;

            // ConnectionFactory and Destination are administered objects and are injected by the container via the specified JNDI name. 
            // This is similar to what was done duringthe message sending
            try (final Connection connection = connectionFactory.createConnection()) {

                connection.start();

                final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // As was the case during message sending, a Connection object and a Session object are created.
                // Instead of MessageProducer, a MessageConsumer is created from ses sion and is used for receiving a message
                final MessageConsumer messageConsumer = session.createConsumer(queue);

                final Message message = messageConsumer.receive(timeoutInMillis);

                if (message == null) {
                    throw new TimeoutException("No message received after " + timeoutInMillis + "ms");
                }

                response = message.getBody(String.class);

            }

            return response;
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private ClassicMessageSender messageSenderAsync;

        @EJB
        private ClassicMessageReceiver messageReceiverSync;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            try {

                // send a message
                messageSenderAsync.sendMessage("It's a text message.");

                System.out.println("The message:" + messageReceiverSync.receiveMessage(Resources.MESSAGE_RECEIVE_TIMEOUT_MILILS));
            } catch (TimeoutException | JMSException e) {
                e.printStackTrace();
            }
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter14_jms/beans.xml"));
            war.addClasses(ServletPrincipal.class);

            final EmbeddedEjb ejb = new EmbeddedEjb(EMBEDDED_JEE_TEST_APP_NAME);
            ejb.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter14_jms/beans.xml"));
            ejb.addClasses(ClassicMessageSender.class, ClassicMessageReceiver.class, Resources.class);

            final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
            ear.addModules(war);
            ear.addModules(ejb);

            final File earFile = ear.exportToFile(APP_FILE_TARGET);

            final MyEmbeddedJmsConnectionFactoryBuilder connectionFactoryBuilder = new MyEmbeddedJmsConnectionFactoryBuilder(Resources.CLASSIC_CONTAINER_MANAGED_CONNECTION_QUEUE_FACTORY_NAME, JmsType.QUEUE);

            final MyEmbeddedJmsDestinationBuilder destinationBuilder = new MyEmbeddedJmsDestinationBuilder(Resources.CLASSIC_CONTAINER_MANAGED_DESTINATION_QUEUE_NAME, JmsType.QUEUE);

            embeddedJeeServer.addJmsConnectionFactory(connectionFactoryBuilder.build());
            embeddedJeeServer.addJmsDestination(destinationBuilder.build());

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, earFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));

            javaSE_Sender();

            System.out.println(response);

        } catch (final IOException ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
