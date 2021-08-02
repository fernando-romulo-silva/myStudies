package br.com.fernando.chapter14_jms.part03_transactions;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_DATA_SOURCE_NAME;
import static br.com.fernando.Util.HSQLDB_XA_JDBC_DRIVER;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;
import static br.com.fernando.chapter14_jms.part03_transactions.Transactions01.DeliveryStats.countDownLatch;
import static java.util.logging.Level.SEVERE;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
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
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource;
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource.MyEmbeddedDataSourceBuilder;
import org.myembedded.jeecontainer.resources.MyEmbeddedJmsConnectionFactory;
import org.myembedded.jeecontainer.resources.MyEmbeddedJmsConnectionFactory.MyEmbeddedJmsConnectionFactoryBuilder;
import org.myembedded.jeecontainer.resources.MyEmbeddedJmsDestination;
import org.myembedded.jeecontainer.resources.MyEmbeddedJmsDestination.MyEmbeddedJmsDestinationBuilder;
import org.myembedded.pack.EmbeddedEar;
import org.myembedded.pack.EmbeddedEjb;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;
import org.myembedded.pack.JeeVersion;
import org.myembedded.rdbms.MyEmbeddedRdbms;

public class Transactions01 {

    public static class Resources {

        public static final String REQUEST_QUEUE_NAME = "requestQueue";

        public static final String REQUEST_QUEUE_JNDI = "jms/requestQueue";

        public static final String CONNECTION_FACTORY_NAME_XA = "requestQueueFactoryXA";

        public static final String CONNECTION_FACTORY_JNDI_XA = "jms/requestQueueFactoryXA";
    }

    // ==================================================================================================================================================================
    @Entity
    @Table(name = "T_USERS")
    public static class User {

        @Id
        private String email;

        public User() {
        }

        public User(final String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(final String email) {
            this.email = email;
        }
    }

    @Stateless
    public static class UserManager {

        @PersistenceContext(name = "embeddedJeeContainerTestPU")
        private EntityManager entityManager;

        @Inject
        @JMSConnectionFactory(Resources.CONNECTION_FACTORY_JNDI_XA)
        private JMSSender jmsSender;

        public User register(final String email) {
            final User user = new User(email);

            entityManager.persist(user);

            final String msg = "Hello " + email + " " + System.currentTimeMillis();

            System.out.println("Sending JMS message " + msg);
            jmsSender.sendMessage(msg);

            return user;
        }
    }

    @Singleton
    public static class DeliveryStats {

        public static CountDownLatch countDownLatch = new CountDownLatch(1);

        private long deliveredMessagesCount;

        public long getDeliveredMessagesCount() {
            return deliveredMessagesCount;
        }

        public void messageDelivered() {
            deliveredMessagesCount++;
        }

        public void reset() {
            deliveredMessagesCount = 0L;
        }
    }

    // ==================================================================================================================================================================
    @Singleton
    public static class JMSSender {

        @Inject
        @JMSConnectionFactory(Resources.CONNECTION_FACTORY_JNDI_XA)
        private JMSContext context;

        @Resource(lookup = Resources.REQUEST_QUEUE_JNDI)
        private Queue queue;

        public void sendMessage(final String payload) {

            context.createProducer().send(queue, payload);

        }
    }

    @MessageDriven( //
            activationConfig = { //
                    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = Resources.REQUEST_QUEUE_JNDI), //
                    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"), // 
            } //
    )
    // The default transaction attribute is REQUIRED
    public static class JMSListener implements MessageListener {

        private static final Logger logger = Logger.getLogger(JMSListener.class.getName());

        @EJB
        private DeliveryStats deliveryStats;

        // The REQUIRED transaction attribute, is the default for the onMessage() 
        @Override
        public void onMessage(final Message message) {
            try {
                logger.info("Message received (async): " + message.getBody(String.class));

                deliveryStats.messageDelivered();
                countDownLatch.countDown();
            } catch (final JMSException ex) {
                logger.log(SEVERE, null, ex);
            }
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/ServletPrincipal")
    public static class ServletPrincipal extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private UserManager userManager;

        @EJB
        private DeliveryStats deliveryStats;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

            try {
                // This email is already in the DB so we should get an exception trying to register it.
                userManager.register("jack@itcrowd.pl");
            } catch (final Exception e) {
                System.out.println("Got expected exception " + e);
            }

            System.out.println("Please wait...");

            // Wait for at most 30 seconds for the JMS method to NOT be called, since we're testing for something
            // to NOT happen we can never be 100% sure, but 30 seconds should cover almost all cases.
            try {
                countDownLatch.await(30, TimeUnit.SECONDS);
            } catch (final InterruptedException e) {
            }

            // Wait for at most 90 seconds for the JMS method to be called
            try {
                countDownLatch.await(90, TimeUnit.SECONDS);
            } catch (final InterruptedException e) {
            }

            userManager.register("bernard@itcrowd.pl"); // Okay, it isn't in the DB

            System.out.println("Count: " + DeliveryStats.countDownLatch.getCount());
            System.out.println("Message " + deliveryStats.getDeliveredMessagesCount());
        }
    }

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try ( //
                final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer(); //
                final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms(); //
        ) {

            // ------------ Database -----------------------------------------------------------
            final String dataBaseName = EMBEDDED_JEE_TEST_APP_NAME + "DB";
            final String dataBaseUrl = "jdbc:hsqldb:hsql://localhost:" + DATA_BASE_SERVER_PORT + "/" + dataBaseName;
            myEmbeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
            myEmbeddedRdbms.start(DATA_BASE_SERVER_PORT);

            // ------------ Packages -----------------------------------------------------------
            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter14_jms/beans.xml"));
            war.addClasses(ServletPrincipal.class);

            final EmbeddedEjb ejb = new EmbeddedEjb(EMBEDDED_JEE_TEST_APP_NAME);
            ejb.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter14_jms/beans.xml"));
            ejb.addMetaInfFiles(EmbeddedResource.add("persistence.xml", "src/main/resources/chapter14_jms/part03_transactions/persistence.xml"));
            ejb.addMetaInfFiles(EmbeddedResource.add("load.sql", "src/main/resources/chapter14_jms/part03_transactions/load.sql"));
            ejb.addClasses(JMSSender.class, JMSListener.class, DeliveryStats.class, UserManager.class, User.class);

            final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
            ear.addModules(ejb);
            ear.addModules(war);

            final File earFile = ear.exportToFile(APP_FILE_TARGET);

            // ------------ jee Server -----------------------------------------------------------
            final MyEmbeddedDataSource dataSource = new MyEmbeddedDataSourceBuilder(EMBEDDED_JEE_TEST_DATA_SOURCE_NAME, dataBaseUrl) //
                    .withDataBaseDataSourceClass(HSQLDB_XA_JDBC_DRIVER) //
                    .withXaTransaction(true)//
                    .build();
            embeddedJeeServer.addDataSource(dataSource);

            final MyEmbeddedJmsConnectionFactory connectionFactoryXA = new MyEmbeddedJmsConnectionFactoryBuilder(Resources.CONNECTION_FACTORY_NAME_XA, JmsType.QUEUE)//
                    .withXaTransaction(true) //
                    .build();
            embeddedJeeServer.addJmsConnectionFactory(connectionFactoryXA);

            final MyEmbeddedJmsDestination jmsDestination = new MyEmbeddedJmsDestinationBuilder(Resources.REQUEST_QUEUE_NAME, JmsType.QUEUE).build();
            embeddedJeeServer.addJmsDestination(jmsDestination);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, earFile.getAbsolutePath());

            // ------------ Client -----------------------------------------------------------

            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));

            System.out.println(response);

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
