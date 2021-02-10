package br.com.fernando.enthuware.implementBusinessLogicUsingEJBs;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;

public class Question05 {

    // Given the code fragment:

    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private ConnectionFactory cf;

    @Resource(lookup = "jms/MyTopic")
    private Topic topic;

    public void action() {
	try (final JMSContext ctx = cf.createContext(JMSContext.SESSION_TRANSACTED)) {
	    JMSConsumer cl = ctx.createConsumer(topic, "(grade > 5O)");
	    JMSConsumer c2 = ctx.createConsumer(topic, "(grade <= 50)");
	}
    }

    // How are transactions managed? You had to select 1 option(s)
    //
    // A
    // through a single shared transaction across the connection factory
    //
    // B
    // through a separate transaction per JMS Consumer
    //
    // C
    // through a single transaction for the entire JMS Topic
    //
    // D
    // through a single shared transaction in the JMS Context
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
    // The correct answer is D
    //
    // If sessionMode is set to JMSContext.SESSION_TRANSACTED then the session will use a local transaction which may subsequently be committed
    // or rolled back by calling the JMSContext's commit or rollback methods.
    //
    // SESSION_TRANSACTED
    // This session mode instructs the JMSContext's session to deliver and consume messages in a local transaction which will be subsequently
    // committed by calling commit or rolled back by calling rollback.
    //
    // A is wrong because is not connection factory determine the transaction

}
