package br.com.fernando.chapter14_jms.part01_sendingAndReceivingMessage;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.TransactionSynchronizationRegistry;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.jeecontainer.resources.JmsType;
import org.myembedded.jeecontainer.resources.MyEmbeddedJmsConnectionFactory.MyEmbeddedJmsConnectionFactoryBuilder;
import org.myembedded.jeecontainer.resources.MyEmbeddedJmsDestination.MyEmbeddedJmsDestinationBuilder;
import org.myembedded.pack.EmbeddedEar;
import org.myembedded.pack.EmbeddedEjb;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.JeeVersion;

public class SendingMessage03Asyn {

    // ==================================================================================================================================================================
    public static class Resources {

        public static final String ASYNC_CONTAINER_MANAGED_DESTINATION_NAME = "mySyncContainerQueue";

        public static final String ASYNC_CONTAINER_MANAGED_DESTINATION_JNDI = "jms/mySyncContainerQueue"; // java:app/

        public static final String ASYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_NAME = "mySyncContainerQueueFactory";

        public static final String ASYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_JNDI = "jms/mySyncContainerQueueFactory"; // java:app/

        public static final int MESSAGE_RECEIVE_TIMEOUT_MILILS = 10000;
    }

    // ==================================================================================================================================================================
    //
    // You can receive a JMS message asynchronously using a message-driven bean:
    //
    // @MessageDriven defines the bean to be a message-driven bean.
    @MessageDriven(
            // The mappedName attribute specifies the JNDI name of the JMS destination from which the bean will consume the message.
            // This is the same destination to which the message was targeted from the producer.
            mappedName = Resources.ASYNC_CONTAINER_MANAGED_DESTINATION_JNDI, //
            activationConfig = { //
                    @ActivationConfigProperty( //
                            propertyName = "destinationLookup", //
                            propertyValue = Resources.ASYNC_CONTAINER_MANAGED_DESTINATION_JNDI), //
                    //
                    @ActivationConfigProperty( //
                            propertyName = "destinationType", //
                            propertyValue = "javax.jms.Queue"), //
            }) //
               // The bean must implement the MessageListener interface, which provides only one method, onMessage.
               // This method is called by the container whenever a message is received by the message-driven bean and contains the application-specific business logic.
    public static class MessageReceiverAsync implements MessageListener {

        @Override
        public void onMessage(final Message message) {
            try {
                TextMessage tm = (TextMessage) message;
                System.out.println("Message received async (from MessageReceiverAsync): " + tm.getText());
            } catch (JMSException ex) {
                Logger.getLogger(MessageReceiverAsync.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Even though a message-driven bean cannot be invoked directly by a session bean, it can still invoke other session beans.
        // A message-driven bean can also send JMS messages.
    }

    // ==================================================================================================================================================================
    // Interceptor
    public static class ReceptionSynchronizer {

        private final static Map<Method, CountDownLatch> barrier = new HashMap<>();

        @Resource
        private TransactionSynchronizationRegistry txRegistry;

        @AroundInvoke
        public Object invoke(final InvocationContext ctx) throws Exception {
            boolean transactional = false;
            try {
                System.out.println("Intercepting " + ctx.getMethod().toGenericString());
                transactional = txRegistry != null && txRegistry.getTransactionStatus() != Status.STATUS_NO_TRANSACTION;
                if (transactional) {
                    txRegistry.registerInterposedSynchronization(new Synchronization() {

                        @Override
                        public void beforeCompletion() {

                        }

                        @Override
                        public void afterCompletion(int i) {
                            registerInvocation(ctx.getMethod());
                        }
                    });
                }
                return ctx.proceed();
            } finally {
                if (!transactional) {
                    registerInvocation(ctx.getMethod());
                }
            }
        }

        void registerInvocation(Method m) {
            CountDownLatch latch = null;
            synchronized (barrier) {
                if (barrier.containsKey(m)) {
                    latch = barrier.get(m);
                } else {
                    barrier.put(m, new CountDownLatch(0));
                }
            }
            if (latch != null) {
                latch.countDown();
            }
        }

        public static void waitFor(Class<?> clazz, String methodName, int timeoutInMillis) throws InterruptedException {
            Method method = null;
            for (Method declaredMethod : clazz.getDeclaredMethods()) {
                if (methodName.equals(declaredMethod.getName())) {
                    if (method == null) {
                        method = declaredMethod;
                    } else {
                        throw new IllegalArgumentException(methodName + " is not unique in " + clazz.getSimpleName());
                    }
                }
            }
            if (method == null) {
                throw new IllegalArgumentException(methodName + " not found in " + clazz.getSimpleName());
            }
            waitFor(method, timeoutInMillis);
        }

        private static void waitFor(Method method, int timeoutInMillis) throws InterruptedException {
            CountDownLatch latch;
            synchronized (barrier) {
                if (barrier.containsKey(method)) {
                    latch = barrier.get(method);
                } else {
                    latch = new CountDownLatch(1);
                    barrier.put(method, latch);
                }
            }
            if (!latch.await(timeoutInMillis, TimeUnit.MILLISECONDS)) {
                throw new AssertionError("Expected method has not been invoked in " + timeoutInMillis + "ms");
            }
        }
    }

    // =======================================================================================================================================================
    // Java SE sender
    public static void javaSE_Sender() throws Exception {

        final int messageReceiveTimeoutInMillis = 10000;

        final Context context = new InitialContext();
        final ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(Resources.ASYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_JNDI);
        final Queue queue = (Queue) context.lookup(Resources.ASYNC_CONTAINER_MANAGED_DESTINATION_JNDI);

        // send a message asynchronously
        try (final JMSContext jmsContext = connectionFactory.createContext();) {

            final JMSProducer producer = jmsContext.createProducer();

            producer.send(queue, "Hello world");

            try {
                ReceptionSynchronizer.waitFor(MessageReceiverAsync.class, "onMessage", messageReceiveTimeoutInMillis);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            System.out.println("Message sent, now waiting for reply");
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedEjb ejb = new EmbeddedEjb(EMBEDDED_JEE_TEST_APP_NAME);
            ejb.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter14_jms/beans.xml"));
            ejb.addMetaInfFiles(EmbeddedResource.add("ejb-jar.xml", "src/main/resources/chapter14_jms/part01_sendingAndReceivingMessage/ejb-jar.xml"));
            ejb.addClasses(MessageReceiverAsync.class, Resources.class);

            final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
            ear.addModules(ejb);

            final File earFile = ear.exportToFile(APP_FILE_TARGET);

            final MyEmbeddedJmsConnectionFactoryBuilder connectionFactoryBuilder = new MyEmbeddedJmsConnectionFactoryBuilder(Resources.ASYNC_CONTAINER_MANAGED_CONNECTION_FACTORY_NAME, JmsType.QUEUE);

            final MyEmbeddedJmsDestinationBuilder destinationBuilder = new MyEmbeddedJmsDestinationBuilder(Resources.ASYNC_CONTAINER_MANAGED_DESTINATION_NAME, JmsType.QUEUE);

            embeddedJeeServer.addJmsConnectionFactory(connectionFactoryBuilder.build());
            embeddedJeeServer.addJmsDestination(destinationBuilder.build());

            embeddedJeeServer.start();

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, earFile.getAbsolutePath());

            javaSE_Sender();

            System.out.println("New");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
