package br.com.fernando.chapter14_jms.part04_jms_and_batch;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Session;
import javax.jms.Topic;
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
import org.myembedded.jeecontainer.resources.MyEmbeddedJmsConnectionFactory;
import org.myembedded.jeecontainer.resources.MyEmbeddedJmsConnectionFactory.MyEmbeddedJmsConnectionFactoryBuilder;
import org.myembedded.jeecontainer.resources.MyEmbeddedJmsDestination;
import org.myembedded.jeecontainer.resources.MyEmbeddedJmsDestination.MyEmbeddedJmsDestinationBuilder;
import org.myembedded.pack.EmbeddedEar;
import org.myembedded.pack.EmbeddedEjb;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;
import org.myembedded.pack.JeeVersion;

import br.com.fernando.utils.BatchTestHelper;

public class JmsWithBatch {

    // ==================================================================================================================================================================
    public class Resources {

        public static final String SUBSCRIPTION = "BatchJob"; // <3> Durable consumer is uniquely identified with its +clientId+ and +subscriptionName+.

        public static final String TOPIC_NAME = "topic";

        public static final String TOPIC_JNDI = "jms/topic";

        public static final String CONNECTION_FACTORY_NAME = "topicFactory";

        public static final String CONNECTION_FACTORY_JNDI = "jms/topicFactory";
    }

    // ==================================================================================================================================================================
    @Named
    public static class JmsItemReader extends AbstractItemReader {

        @Resource(lookup = Resources.CONNECTION_FACTORY_JNDI)
        private ConnectionFactory factory;

        private JMSContext jms;

        @Resource(lookup = Resources.TOPIC_JNDI)
        private Topic topic;

        private JMSConsumer subscription;

        @Override
        public void open(Serializable checkpoint) throws Exception {
            // <1> Since we're not using default connection factory, we use app managed +JMSContext+
            jms = factory.createContext();
            subscription = jms.createDurableConsumer(topic, Resources.SUBSCRIPTION);
        }

        @Override
        public Object readItem() throws Exception {
            // <2> When there is no message ready to be received, +null+ is returned, fulfilling +readItem+ contract
            Integer item = subscription.receiveBodyNoWait(Integer.class);
            return item;
        }

        @Override
        public void close() throws Exception {
            subscription.close(); // <3> Free resources at end of run
            jms.close();
        }

        public int sendMessages(int count) {
            int sum = 0;
            Random r = new Random();
            try (JMSContext jms = factory.createContext(Session.AUTO_ACKNOWLEDGE)) {
                JMSProducer producer = jms.createProducer();
                for (int i = 0; i < count; i++) {
                    int payload = r.nextInt();
                    producer.send(topic, payload);
                    sum += payload;
                }
            }
            return sum;
        }
    }

    // ==================================================================================================================================================================
    @Named
    public static class SummingItemWriter extends AbstractItemWriter {

        @Inject
        private ResultCollector collector;

        private int numItems;

        private int sum;

        @Override
        public void open(Serializable checkpoint) throws Exception {
            numItems = 0; // <1> Reset the computation
            sum = 0;
        }

        @Override
        public void writeItems(List<Object> objects) throws Exception {
            numItems += objects.size(); // <2> Perform the computation. Note that this may be called multiple times within single job run
            sum += computeSum(objects);
        }

        @Override
        public void close() throws Exception {
            collector.postResult(sum, numItems); // <3> Post results
        }

        private int computeSum(List<Object> objects) {
            int subTotal = 0;
            for (Object o : objects) {
                subTotal += (Integer) o;
            }
            return subTotal;
        }
    }

    // ==================================================================================================================================================================
    @Singleton
    public static class ResultCollector {

        private int numberOfJobs;

        private int lastItemCount;

        private int lastSum;

        public void postResult(int sum, int numItems) {
            numberOfJobs++;
            lastItemCount = numItems;
            lastSum = sum;
        }

        public int getNumberOfJobs() {
            return numberOfJobs;
        }

        public int getLastItemCount() {
            return lastItemCount;
        }

        public int getLastSum() {
            return lastSum;
        }
    }

    // ==================================================================================================================================================================
    @Singleton
    @Startup
    public static class SubscriptionCreator {

        @Resource(lookup = Resources.TOPIC_JNDI)
        private Topic topic;

        @Resource(lookup = Resources.CONNECTION_FACTORY_JNDI)
        private ConnectionFactory factory;

        void createSubscription() {
            try (final JMSContext jms = factory.createContext()) { // <1> This is factory with clientId specified
                JMSConsumer consumer = jms.createDurableConsumer(topic, Resources.SUBSCRIPTION); // <2> creates durable subscription on the topic
                consumer.close();
            }
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private JmsItemReader jmsItemReader;

        @EJB
        private ResultCollector collector;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            try {
                System.out.println("=================================================================================================================================");
                int sum = jmsItemReader.sendMessages(10);
                runJob();
                System.out.println("Must be 10 -> " + collector.getLastItemCount());
                System.out.println("Last sum " + sum);
                System.out.println("Number of Jobs " + collector.getNumberOfJobs());

                System.out.println("=================================================================================================================================");
                sum = jmsItemReader.sendMessages(14);
                runJob();
                System.out.println("Must be 14 -> " + collector.getLastItemCount());
                System.out.println("Last sum " + sum);
                System.out.println("Number of Jobs " + collector.getNumberOfJobs());

                System.out.println("=================================================================================================================================");
                sum = jmsItemReader.sendMessages(8); // <1> Sending messages from separate connections makes no difference
                sum += jmsItemReader.sendMessages(4);
                runJob();
                System.out.println("Must be 12 -> " + collector.getLastItemCount());
                System.out.println("Last sum " + sum);
                System.out.println("Number of Jobs " + collector.getNumberOfJobs());

            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        }

        public void runJob() throws InterruptedException {
            JobOperator jobOperator = BatchRuntime.getJobOperator();
            Long executionId = jobOperator.start("jms-job", new Properties());
            JobExecution jobExecution = jobOperator.getJobExecution(executionId);

            BatchTestHelper.keepTestAlive(jobExecution);
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
            ejb.addMetaInfFiles(EmbeddedResource.add("jms-job.xml", "src/main/resources/chapter14_jms/part04_jms_batch/jms-job.xml", "batch-jobs"));
            ejb.addClasses(SubscriptionCreator.class, ResultCollector.class, JmsItemReader.class, SummingItemWriter.class, Resources.class);

            final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
            ear.addModules(war);
            ear.addModules(ejb);

            final File earFile = ear.exportToFile(APP_FILE_TARGET);

            final MyEmbeddedJmsConnectionFactory connectionFactory = new MyEmbeddedJmsConnectionFactoryBuilder(Resources.CONNECTION_FACTORY_NAME, JmsType.TOPIC).build();
            embeddedJeeServer.addJmsConnectionFactory(connectionFactory);

            final MyEmbeddedJmsDestination destination = new MyEmbeddedJmsDestinationBuilder(Resources.TOPIC_NAME, JmsType.TOPIC).build();
            embeddedJeeServer.addJmsDestination(destination);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, earFile.getAbsolutePath());

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));

            System.out.println(response);
            
            Thread.sleep(5000); // 5 seconds only for the server finishs its jobs...

        } catch (final IOException ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
