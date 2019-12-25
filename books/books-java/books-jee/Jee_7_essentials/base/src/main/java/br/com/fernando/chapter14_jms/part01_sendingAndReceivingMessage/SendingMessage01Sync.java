package br.com.fernando.chapter14_jms.part01_sendingAndReceivingMessage;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConnectionFactoryDefinition;
import javax.jms.JMSConnectionFactoryDefinitions;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.JMSException;
import javax.jms.JMSPasswordCredential;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
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

public class SendingMessage01Sync {

    //
    // JMS refers to the API introduced in JMS 1.1 as the classic API.
    // It provides a single set of interfaces that could be used for both point-to-point and pub/sub messaging.
    // JMS 2.0 introduces a simplified API that offers all the features of classic API but requires fewer interfaces and is simpler to use.
    // The following sections will explain the key JMS concepts using both the classic API and the simplified API.
    //
    // =======================================================================================================================================================
    // The following portable JNDI namespaces are available. Which ones you can use depends on how your application is packaged.
    //
    // java:global: Makes the resource available to all deployed applications
    //
    // java:app: Makes the resource available to all components in all modules in a single application
    //
    // java:module: Makes the resource available to all components within a given module (for example, all enterprise beans within an EJB module)
    //
    // java:comp: Makes the resource available to a single component only (except in a web application, where it is equivalent to java:module)
    //
    public static class Resources {

        public static final String SYNC_CONTAINER_MANAGED_DESTINATION_NAME_QUEUE = "mySyncContainerQueue";

        public static final String SYNC_CONTAINER_MANAGED_DESTINATION_JNDI_QUEUE = "jms/mySyncContainerQueue"; // java:app/

        public static final String SYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_NAME_QUEUE = "mySyncContainerQueueFactory";

        public static final String SYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_JNDI_QUEUE = "jms/mySyncContainerQueueFactory"; // java:app/

        public static final String NAME = "guest";

        public static final String PASSWORD = "guest";

        public static final int MESSAGE_RECEIVE_TIMEOUT_MILILS = 10000;
    }

    // =======================================================================================================================================================
    @Singleton // technique to start definitions
    @Startup
    //
    // Connection factory encapsulates a set of configuration properties (XA, security, transacional, etc) for connections to the JMS server (Destinations).
    // A JMS client must use the connection factory to obtains a connection to a JMS server before it can perform work.
    //
    @JMSConnectionFactoryDefinitions({ // @@JMSConnectionFactoryDefinitions is used to define one or more @JMSConnectionFactoryDefinition.
            //
            @JMSConnectionFactoryDefinition( //
                    name = "java:/MyConnectionFactory", //
                    className = "javax.jms.ConnectionFactory" // javax.jms.ConnectionFactory, javax.jms.QueueConnectionFactory, javax.jms.TopicConnectionFactory
            // user = "guest", //
            // password = "guest" //
            ) //
    })
    //
    // JMS destinations serve as the repositories for messages.
    // Various connection factories open a connection on this repository, in different manner.
    // Client JMS applications that either produce messages to destinations or consume messages from destinations.
    // Each JEE server create JMS resources own your way, but we can use annotations (don't work with embbeded-glassfish)
    //
    @JMSDestinationDefinitions({ // @JMSDestinationDefinitions is used to define one or more @JMSDestinationDefinition.
            //
            @JMSDestinationDefinition( // @JMSDestinationDefinition defines a JMS Destination required in the operational environment.
                    // This annotation provides information that can be used at the application's deployment to provision the required resource
                    // and allows an application to be deployed into a Java EE environment with less administrative configuration.
                    //
                    name = "java:global/jms/myQueue", // Required JNDI name of the destination resource being defined
                    interfaceName = "javax.jms.Queue" // Required fully qualified name of the JMS destination interface. Permitted values are javax.jms.Queue or javax.jms.Topic.
            // others attributes:
            // className - Optional fully qualified name of the JMS destination implementation class.
            // description - Optional description of this JMS destination.
            // destinationName - Optional name of the queue or topic.
            // properties - Optional properties of the JMS destination. Properties are specified in the format property Name=propertyValue with one property per array element.
            // resourceAdapter - Optional resource adapter name.
            ) //
    }) //
    public static class JmsResources {

        @PostConstruct
        public void confirm() {
            System.out.println("Jms resource configured");
        }
    }

    // =======================================================================================================================================================
    // Synchronous message sending with container-managed JMSContext.
    @Stateless
    public static class MessageSenderSync {

        // JMSContext is the main interface in the simplified JMS API. This combines in a single object the functionality of two separate objects from the JMS 1.1 API:
        // a Connection and a Session. It provides a physical link to the JMS server and a singlethreaded context for sending and receiving messages
        //
        // A container-managed JMSContext is injected via the @Inject annotation. Such a context is created and closed by the container, not the application.
        // The annotation @JMSConnectionFactory may be used to specify the JNDI lookup name of the connection factory used to create the JMSContext:
        //
        @Inject
        // The @JMSPasswordCredential annotation can be used to specify the username and password that will be used when the JMSContext is created.
        // Passwords may be specified as an alias, which allows the password to be defined in a secure manner separately from the application.
        @JMSPasswordCredential(userName = Resources.NAME, password = Resources.PASSWORD)
        //
        // A preconfigured and a default JMSConnectionFactory are accessible under theJNDI name java:comp/DefaultJMSConnectionFactory.
        // If the @JMSConnection Factory annotation is omitted, then the platform default connection factory is used.
        @JMSConnectionFactory(Resources.SYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_JNDI_QUEUE)
        private JMSContext context;

        // The interface Destination (Queue and Topic) encapsulates a provider-specific address. A Queue or Topic may be injected here instead.
        // You inject both of these objects by using @Resource and specifying the JNDI name of the resource.
        @Resource(lookup = Resources.SYNC_CONTAINER_MANAGED_DESTINATION_JNDI_QUEUE)
        // or
        // @Resource(mappedName = Resources.SYNC_CONTAINER_MANAGED_QUEUE)
        private Destination syncQueue;

        public void sendMessage(String message) throws JMSRuntimeException {
            // When an application needs to send messages, it uses the createProducer method to create a JMSProducer, which provides methods to configure and send messages.
            // It provides various send methods to send a message to a specified destination.
            final JMSProducer jmsProducer = context.createProducer();

            // By default, a JMS provider ensures that a message is not lost in transit in case of a provider failure.
            // This is called a durable publisher/producer.
            // The messages are logged to stable storage for recovery from a provider failure.
            // However, this has performance overheads and requires additional storage for persisting the messages.
            //
            // If a receiver can afford to miss the messages, NON_PERSISTENT delivery mode may be specified.
            // A JMS provider must deliver a NON_PERSISTENT message at most once. This means it may lose the message, but it must not deliver it twice.
            // This does not require the JMS provider to store the message or otherwise guarantee that it is not lost if the provider fails.
            //
            // Using the JMS 2 simplified API, this delivery mode can be specified:
            jmsProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // JMS defines the priority of a message on a scale of 0 (lowest) to 9 (highest).
            // By default, the priority of a message is 4 (Message.DEFAULT_PRIORITY). You can also change message priority by invoking the Message.setJMSPriority method.
            jmsProducer.setPriority(Message.DEFAULT_PRIORITY);
            //
            jmsProducer.send(syncQueue, message); // send it!
            // By default, a message never expires, as defined by Message.DEFAULT_TIME_TO_LIVE.
            // You can change this by calling the Message.setJMSExpiration method.

            //
            // Various setter methods on JMSProducer all return the JMSProducer object.
            // This allows method calls to be chained together, allowing a fluid programming style:
            //
            // context
            // .createProducer()
            // .setProperty(...)
            // .setTimeToLive(...)
            // .setDeliveryMode(...)
            // .setPriorty(...)
            // .send(...);
        }
    }

    // =======================================================================================================================================================
    // Synchronous message reception with container-managed JMSContext
    @Stateless
    public static class MessageReceiverSync {

        @Inject
        @JMSPasswordCredential(userName = Resources.NAME, password = Resources.PASSWORD)
        @JMSConnectionFactory(Resources.SYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_JNDI_QUEUE)
        // JMSContext provides the main entry to the simplified API. It provides a combination of Connection and Session from the classic API.
        private JMSContext context;

        @Resource(mappedName = Resources.SYNC_CONTAINER_MANAGED_DESTINATION_JNDI_QUEUE)
        // Destination is an administered object and encapsulates a provider-specific address. A Queue or Topic may be injected here instead.
        // You inject both of these objects by using @Resource and specifying the JNDI name of the resource.
        private Queue myQueue;

        // Waits to receive a message from the JMS queue. Times out after a given number of milliseconds.
        public String receiveMessage(int timeoutInMillis) throws JMSRuntimeException, TimeoutException {

            // When an application needs to receive messages, it uses one of several createCon sumer or createDurableConsumer methods to create a JMSConsumer.
            // A JMSConsum er provides methods to receive messages either synchronously or asynchronously
            final JMSConsumer consumer = context.createConsumer(myQueue);

            // The receiveBody method is used to receive the next message produced for this JMSConsumer within the specified timeout period and returns its
            // body as an object of the specified type. This method does not give access to the message headers or properties (such as the JMSRedelivered message
            // header field or the JMSXDeliveryCount message property) and should only be used if the application has no need to access them.
            String message = consumer.receiveBody(String.class, timeoutInMillis);

            // This call blocks until a message arrives, the timeout expires, or this JMSConsumer is closed.
            // A timeout of zero never expires, and the call blocks indefinitely.
            // If this method is called within a transaction, the JMSConsumer retains the message until the transaction commits.
            if (message == null) {
                throw new TimeoutException("No message received after " + timeoutInMillis + "ms");
            }

            return message;
        }

        public void receiveAll(int timeoutInMillis) throws JMSException {

            System.out.println("--> Receiving redundant messages ...");

            QueueBrowser browser = context.createBrowser(myQueue);

            while (browser.getEnumeration().hasMoreElements()) {
                System.out.println("--> here is one");
                context //
                        .createConsumer(myQueue) //
                        .receiveBody(String.class, timeoutInMillis);
            }
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private MessageSenderSync messageSenderSync;

        @EJB
        private MessageReceiverSync messageReceiverSync;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            messageSenderSync.sendMessage("It's a text message.");

            try {
                System.out.println("The message:" + messageReceiverSync.receiveMessage(Resources.MESSAGE_RECEIVE_TIMEOUT_MILILS));
            } catch (TimeoutException e) {
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
            ejb.addClasses(MessageReceiverSync.class, MessageSenderSync.class, Resources.class);

            final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
            ear.addModules(war);
            ear.addModules(ejb);

            final File earFile = ear.exportToFile(APP_FILE_TARGET);

            final MyEmbeddedJmsConnectionFactoryBuilder connectionFactoryBuilder = new MyEmbeddedJmsConnectionFactoryBuilder(Resources.SYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_NAME_QUEUE, JmsType.QUEUE);
            connectionFactoryBuilder.withCredential(Resources.NAME, Resources.PASSWORD);

            final MyEmbeddedJmsDestinationBuilder destinationBuilder = new MyEmbeddedJmsDestinationBuilder(Resources.SYNC_CONTAINER_MANAGED_DESTINATION_NAME_QUEUE, JmsType.QUEUE);
            destinationBuilder.withCredential(Resources.NAME, Resources.PASSWORD);

            embeddedJeeServer.addJmsConnectionFactory(connectionFactoryBuilder.build());
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
