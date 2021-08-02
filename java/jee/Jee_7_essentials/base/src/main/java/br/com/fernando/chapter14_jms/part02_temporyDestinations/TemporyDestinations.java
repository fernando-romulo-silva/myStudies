package br.com.fernando.chapter14_jms.part02_temporyDestinations;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
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

public class TemporyDestinations {

    public static class Resources {

        public static final String REQUEST_QUEUE_NAME = "requestQueue";

        public static final String REQUEST_QUEUE_JNDI = "jms/requestQueue";

        public static final String CONNECTION_FACTORY_NAME = "requestQueueFactory";

        public static final String CONNECTION_FACTORY_JNDI = "jms/requestQueueFactory"; // java:app/
    }

    // ==================================================================================================================================================================
    @Stateless
    public static class JmsClient {

        @Resource(lookup = Resources.REQUEST_QUEUE_JNDI)
        private Queue requestQueue;

        @Inject
        @JMSConnectionFactory(Resources.CONNECTION_FACTORY_JNDI)
        private JMSContext jmsContext;

        // <1> we need to send message in the middle of the method, therefore we cannot be transactional
        @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
        public String process(final String request) {

            final TextMessage requestMessage = jmsContext.createTextMessage(request);

            // Typically, JMS Destination objects (i.e., Queue and Topic) are administered objects and identified by a JNDI name.
            // These objects can also be created dynamically, where their scope is bound to the JMSContext or Connection from which they are created.
            //
            // These temporary destinations are automatically closed and deleted, and their contents  are lost when the connection is closed. 
            // You can also explicitly delete them by calling the TemporaryQueue.delete or TemporaryTopic.delete method.
            //
            // You can use these temporary destinations to simulate a request-reply design pattern by using the JMSReplyTo and JMSCorrelationID header fields.

            final TemporaryQueue responseQueue = jmsContext.createTemporaryQueue();
            // or
            // TemporaryTopic tempTopic = jmsContext.createTemporaryTopic();

            final JMSProducer jmsProducer = jmsContext.createProducer();

            jmsProducer //
                    .setJMSReplyTo(responseQueue) // <2> set the temporary queue as replyToDestination
                    .send(requestQueue, requestMessage); // <3> immediately send the request message

            try (final JMSConsumer consumer = jmsContext.createConsumer(responseQueue)) { // <4> listen on the temporary queue

                final String response = consumer.receiveBody(String.class, 20000); // <5> wait for a +TextMessage+ to arrive

                if (response == null) { // <6> +receiveBody+ returns +null+ in case of timeout
                    throw new IllegalStateException("Message processing timed out");
                }

                return response;
            }
        }
    }

    // ==================================================================================================================================================================
    @MessageDriven( //
            activationConfig = { //
                    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = Resources.REQUEST_QUEUE_JNDI), //
                    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"), //
            } //
    ) //
    public static class RequestResponseOverJMS implements MessageListener {

        @Inject
        @JMSConnectionFactory(Resources.CONNECTION_FACTORY_JNDI)
        private JMSContext jms;

        @Override
        public void onMessage(final Message message) {
            try {

                final Destination replyTo = message.getJMSReplyTo(); // <1> get the destination for the response

                if (replyTo == null) {
                    return;
                }

                final TextMessage request = (TextMessage) message;

                final String payload = request.getText(); // <2> read the payload

                System.out.println("Got request: " + payload);

                final String response = "Processed: " + payload; // <3> process the request

                final JMSProducer producer = jms.createProducer();

                producer.send(replyTo, response); // <4> send the response

            } catch (final JMSException e) {
                e.printStackTrace();
            }
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private JmsClient jmsClient;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            final String processResult = jmsClient.process("It's a text message.");

            System.out.println(processResult);
        }
    }

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter14_jms/beans.xml"));
            war.addClasses(ServletPrincipal.class);

            final EmbeddedEjb ejb = new EmbeddedEjb(EMBEDDED_JEE_TEST_APP_NAME);
            ejb.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter14_jms/beans.xml"));
            ejb.addClasses(JmsClient.class, RequestResponseOverJMS.class, Resources.class);

            final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
            ear.addModules(ejb);
            ear.addModules(war);

            final File earFile = ear.exportToFile(APP_FILE_TARGET);

            final MyEmbeddedJmsConnectionFactoryBuilder connectionFactoryBuilder = new MyEmbeddedJmsConnectionFactoryBuilder(Resources.CONNECTION_FACTORY_NAME, JmsType.QUEUE);

            final MyEmbeddedJmsDestinationBuilder destinationBuilder = new MyEmbeddedJmsDestinationBuilder(Resources.REQUEST_QUEUE_NAME, JmsType.QUEUE);

            embeddedJeeServer.addJmsConnectionFactory(connectionFactoryBuilder.build());
            embeddedJeeServer.addJmsDestination(destinationBuilder.build());

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, earFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));

            System.out.println(response);

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
