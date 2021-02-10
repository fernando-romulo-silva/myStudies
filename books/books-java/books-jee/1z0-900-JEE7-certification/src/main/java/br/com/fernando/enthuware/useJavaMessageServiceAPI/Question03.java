package br.com.fernando.enthuware.useJavaMessageServiceAPI;

import javax.annotation.Resource;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;

public class Question03 {

    // You want to send a message to a queue in a transaction.
    // Which of the following calls would you use to create a session with default acknowledgement mode?
    //
    // A
    // QueueSession queueSession = queueConnection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
    //
    // B
    // QueueSession queueSession = queueConnection.createQueueSession(Session.TRANSACTED);
    //
    // C
    // QueueSession queueSession = queueConnection.createQueueSession(true);
    //
    // D
    // QueueSession queueSession = queueConnection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
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
    // A is wrong because, Since the question mentions that the message needs to be sent in a transaction, the first parameter has to be true.
    //
    // B not compile
    //
    // C is wrong because There is no createQueueSession method that takes only one parameter.
    //
    //
    //
    // Note that there is only one method in QueueConnection interface to create QueueSession:
    //
    // public QueueSession createQueueSession(boolean transacted, int acknowledgeMode) throws JMSException
    //
    // Parameters:
    // transacted - if true, the session is transacted.
    // acknowledgeMode - indicates whether the consumer or the client will acknowledge any messages it receives.
    // This parameter will be ignored if the session is transacted. Legal values are Session.AUTO_ACKNOWLEDGE, Session.CLIENT_ACKNOWLEDGE and Session.DUPS_OK_ACKNOWLEDGE.
    //
    // Returns:
    // a newly created queue session.
    //
    // In this question, since the message needs to be sent in a transaction, the first parameter is has to be 'true' and so the second parameter will be ignored.
    @Resource(mappedName = "java:/JmsXA")
    private QueueConnectionFactory queueConnectionFactory;

    public QueueSession getQueueSessionA() {

	try {
	    final QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
	    final QueueSession qsession = queueConnection.createQueueSession(true, Session.CLIENT_ACKNOWLEDGE);

	    return qsession;

	} catch (Exception e) {
	    throw new IllegalStateException(e);
	}
    }

}
