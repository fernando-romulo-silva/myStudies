package br.com.fernando.chapter14_jms.part01_sendingAndReceivingMessage;

public class SendingMessage04Topic {

    // @Resource(lookup = "myTopicConnection")
    // TopicConnectionFactory topicConnectionFactory;
    //
    // @Resource(lookup = "myTopic")
    // Topic myTopic;
    //
    // public void receiveMessage() {
    // TopicConnection connection = topicConnectionFactory.createTopicConnection();
    // TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
    // TopicSubscriber subscriber = session.createDurableSubscriber(myTopic, "myID");
    // // . . .
    // }

}
