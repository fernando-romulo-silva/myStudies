package br.com.fernando.chapter14_jms.part00_intro;

public class Intro {
    // Java Message Service (JMS) is a MOM that provides a way for Java programs to create, send, receive, and read an enterprise messaging system's messages.
    //
    // JMS defines the following concepts:
    //
    // JMS provider
    // An implementation of the JMS interfaces, included in a Java EE implementation.
    //
    // JMS client
    // A Java program that produces and/or receives messages. Any Java EE application component can act as a JMS client.
    //
    // JMS message
    // An object that contains the data transferred between JMS clients. A JMS producer/publisher creates and sends messages.
    // A JMS consumer/subscriber receives and consumes messages.
    //
    // Administered objects
    // Objects created and preconfigured by an administrator.
    // They typically refer to ConnectionFactory and Destination, and are identified by a JNDI name.
    // The ConnectionFactory is used to create a connection with the provider. The Destination is the object used by the client to specify
    // the destination of messages it is sending and the source of messages it is receiving.
    //
    // ---------------------------------------------------------------------------------------
    //
    // JMS supports two messaging models: Point-to-Point and Publish-Subscribe
    //
    // In the Point-to-Point model, a publisher sends a message to a specific destination, called a queue, targeted to a subscriber.
    // Multiple publishers can send messages to the queue, but each message is delivered and consumed by one consumer only.
    // Queues retain all messages sent to them until the messages are consumed or expire.
    //
    // In the Publish-Subscribe model, a publisher publishes a message to a particular destination, called a topic, and a subscriber registers interest by
    // subscribing to that topic. Multiple publishers can publish messages to the topic, and multiple subscribers can subscribe to the topic.
    // By default, a subscriber will receive messages only when it is active.
    // However, a subscriber may establish a durable connection, so that any messages published while the subscriber is not active are redistributed
    // whenever it reconnects.
    //
    // -----------------------------------------------------------------------------------------
    //
    // The publisher and subscriber are loosely coupled from each other; in fact, they have no knowledge of each other's existence.
    // They only need to know the destination and the message format.
    //
    // Different levels of quality of service, such as missed or duplicate messages or deliveronce, can be configured.
    // The messsages may be received synchronously or asynchronously.
    //
    // -----------------------------------------------------------------------------------------
    //
    // A JMS message is composed of three parts:
    //
    // * Header *
    //
    // This is a required part of the message and is used to identify and route messages. All messages have the same set of header fields
    // JMS header fields:
    //
    // JMSDestination - Destination to which the message is sent.
    //
    // JMSDeliveryMode - Delivery mode is PERSISTENT (for durable topics) or NON_PERSISTENT.
    //
    // JMSMessageID - String value with the prefix ID: that uniquely identifies each message sent by a provider.
    //
    // JMSTimestamp - Time the message was handed off to a provider to be sent. This value may be different from the time the message was actually transmitted.
    //
    // JMSCorrelationID - Used to link one message to another (e.g., a response message with its request message).
    //
    // JMSReplyTo - Destination supplied by a client where a reply message should be sent.
    //
    // JMSRedelivered - Set by the provider if the message was delivered but not acknowledged in the past.
    //
    // JMSType - Message type identifier; may refer to a message definition in the provider's respository.
    //
    // JMSExpiration - Expiration time of the message.
    //
    // JMSPriority - Priority of the message.
    //
    // * Properties *
    //
    // These are optional header fields added by the client. Just like standard header fields, these are name/value pairs.
    // The producer/publisher can set these values and the consumer/subscriber can use these values as selection criteria to
    // fine-tune the selection of messages to be processed.
    //
    //
    // * Body *
    //
    // This is the actual payload of the message, which contains the application data.
    //
    // JMS message types
    //
    // StreamMessage - Payload is a stream of Java primitive types, written and read sequentially.
    //
    // MapMessage - Payload is a set of name/value pairs; order of the entries is undefined, and can be accessed randomly or sequentially
    //
    // TextMessage - Payload is a String.
    //
    // ObjectMessage - Payload is a serializable Java object.
    //
    // ByteMessage - Payload is a stream of uninterpreted bytes
    //
    // ================================================================================================================================================
    // Real world use of JMS/message queues?
    //
    // JMS (ActiveMQ is a JMS broker implementation) can be used as a mechanism to allow asynchronous request processing.
    // You may wish to do this because the request take a long time to complete or because several parties may be interested in the actual request.
    //
    // Using it is to allow multiple clients (potentially written in different languages) to access information via JMS.
    // ActiveMQ is a good example here because you can use the STOMP protocol to allow access from a C#/Java/Ruby client.
    //
    // One of the use cases is to splitup long running processes into stages using JMS.
    // This is particularly usefull when some of the stages have a heavy piece of work to do, that you don't want to be redone if in a later stage an error occurs.
    //
    // In your case JMS is suitable if you want to read in a file and distribute the processing over several workers.
}
