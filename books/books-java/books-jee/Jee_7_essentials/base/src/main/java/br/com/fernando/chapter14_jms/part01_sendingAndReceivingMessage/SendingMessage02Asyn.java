package br.com.fernando.chapter14_jms.part01_sendingAndReceivingMessage;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.CompletionListener;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
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

public class SendingMessage02Asyn {

    public static class Resources {

        public static final String ASYNC_CONTAINER_MANAGED_DESTINATION_NAME = "mySyncContainerQueue";

        public static final String ASYNC_CONTAINER_MANAGED_DESTINATION_JNDI = "jms/mySyncContainerQueue"; // java:app/

        public static final String ASYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_NAME = "mySyncContainerQueueFactory";

        public static final String ASYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_JNDI = "jms/mySyncContainerQueueFactory"; // java:app/

        public static final int MESSAGE_RECEIVE_TIMEOUT_MILILS = 10000;
    }

    // =======================================================================================================================================================
    @Stateless
    public static class MessageSenderAsync {

        @Inject
        @JMSConnectionFactory(Resources.ASYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_JNDI)
        private JMSContext context;

        @Resource(lookup = Resources.ASYNC_CONTAINER_MANAGED_DESTINATION_JNDI)
        private Queue asyncQueue;

        public void sendMessageAsync(String message) throws JMSRuntimeException {
            JMSProducer producer = context.createProducer();

            try {
                // You can send messages asynchronously by calling setAsync(CompletionListener listener) on the JMSProducer.
                //
                // This is not permitted in the Java EE or EJB container because it registers a callback method that is executed in a separate thread:
                producer.setAsync(new CompletionListener() {

                    // For an asynchronous message, part of the work involved in sending the message will be performed in a separate thread, and
                    // the specified CompletionListener will be notified when the operation has completed.
                    @Override
                    public void onCompletion(Message msg) {
                        try {
                            System.out.println(msg.getBody(String.class));
                        } catch (JMSException ex) {
                            Logger.getLogger(MessageSenderAsync.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    // If an exception occurs during the attempt to send the message, then the onException callback method is called.
                    @Override
                    public void onException(Message msg, Exception e) {
                        try {
                            System.out.println(msg.getBody(String.class));
                        } catch (JMSException ex) {
                            Logger.getLogger(MessageSenderAsync.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            } catch (Exception ex) {
                System.out.println("Caught RuntimeException trying to invoke setAsync - not permitted in Java EE. Resorting to synchronous sending...");
            }

            producer.send(asyncQueue, message);
        }

        public void sendMessage(final String message) throws JMSRuntimeException {
            final JMSProducer jmsProducer = context.createProducer();

            jmsProducer.send(asyncQueue, message);
        }
    }

    // =======================================================================================================================================================
    // Java SE sender
    public static void javaSE_Sender() throws Exception {

        final Context context = new InitialContext();
        final ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(Resources.ASYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_JNDI);
        final Queue queue = (Queue) context.lookup(Resources.ASYNC_CONTAINER_MANAGED_DESTINATION_JNDI);

        // send a message asynchronously
        try (final JMSContext jmsContext = connectionFactory.createContext();) {

            final CountDownLatch latch = new CountDownLatch(1);

            final MyCompletionListener myCompletionListener = new MyCompletionListener(latch);

            final JMSProducer producer = jmsContext.createProducer();

            producer //
                    .setAsync(myCompletionListener) //
                    .send(queue, "Hello world");

            System.out.println("Message sent, now waiting for reply");

            // at this point we can do something else before waiting for the reply this is not shown here

            latch.await();

            if (myCompletionListener.getException() == null) {
                System.out.println("Reply received from server");
            } else {
                throw myCompletionListener.getException();
            }
        }
    }

    static class MyCompletionListener implements CompletionListener {

        private final CountDownLatch latch;

        private Exception exception;

        public MyCompletionListener(final CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onCompletion(Message message) {
            latch.countDown();
        }

        @Override
        public void onException(Message message, Exception exception) {
            latch.countDown();
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }

    // =======================================================================================================================================================
    // Synchronous message reception with container-managed JMSContext
    @Stateless
    public static class MessageReceiverSync {

        @Inject
        @JMSConnectionFactory(Resources.ASYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_JNDI)
        private JMSContext context;

        @Resource(mappedName = Resources.ASYNC_CONTAINER_MANAGED_DESTINATION_JNDI)
        private Queue myQueue;

        public String receiveMessage(int timeoutInMillis) throws JMSRuntimeException, TimeoutException {

            final String message = context //
                    .createConsumer(myQueue) // return JMSConsumer
                    .receiveBody(String.class, timeoutInMillis);

            if (message == null) {
                throw new TimeoutException("No message received after " + timeoutInMillis + "ms");
            }

            return message;
        }

        public List<String> receiveAllMessages(int timeoutInMillis) throws JMSException {

            final List<String> result = new ArrayList<>();

            System.out.println("--> Receiving redundant messages ...");

            QueueBrowser browser = context.createBrowser(myQueue);

            while (browser.getEnumeration().hasMoreElements()) {
                System.out.println("--> here is one");

                final String message = context //
                        .createConsumer(myQueue) //
                        .receiveBody(String.class, timeoutInMillis);

                result.add(message);

            }

            return result;
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private MessageSenderAsync messageSenderAsync;

        @EJB
        private MessageReceiverSync messageReceiverSync;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            messageSenderAsync.sendMessageAsync("It's a text message for async.");

            try {
                System.out.println("The message:" + messageReceiverSync.receiveMessage(Resources.MESSAGE_RECEIVE_TIMEOUT_MILILS));
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

            messageSenderAsync.sendMessage("It's a text message.");

            try {
                final List<String> result = messageReceiverSync.receiveAllMessages(Resources.MESSAGE_RECEIVE_TIMEOUT_MILILS);

                for (final String string : result) {
                    System.out.println("The messages:" + string);
                }
            } catch (Exception e) {
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
            ejb.addClasses(MessageReceiverSync.class, MessageSenderAsync.class, Resources.class);

            final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
            ear.addModules(war);
            ear.addModules(ejb);

            final File earFile = ear.exportToFile(APP_FILE_TARGET);

            final MyEmbeddedJmsConnectionFactoryBuilder connectionFactoryBuilder = new MyEmbeddedJmsConnectionFactoryBuilder(Resources.ASYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_NAME, JmsType.QUEUE);

            final MyEmbeddedJmsDestinationBuilder destinationBuilder = new MyEmbeddedJmsDestinationBuilder(Resources.ASYNC_CONTAINER_MANAGED_DESTINATION_NAME, JmsType.QUEUE);

            embeddedJeeServer.addJmsConnectionFactory(connectionFactoryBuilder.build());
            embeddedJeeServer.addJmsDestination(destinationBuilder.build());

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, earFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));

            try {
                javaSE_Sender();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println(response);

        } catch (final IOException ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
