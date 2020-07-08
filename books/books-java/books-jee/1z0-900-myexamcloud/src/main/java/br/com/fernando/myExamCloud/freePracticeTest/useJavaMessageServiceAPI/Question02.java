package br.com.fernando.myExamCloud.freePracticeTest.useJavaMessageServiceAPI;

public class Question02 {

    // Which statement about message-driven beans is correct? 
    //
    // Choice A - Each message-driven bean instance will be invoked only one thread at a time.
    //
    // Choice B - When dispatching messages to message bean instances the container must preserve the order in which messages arrive.
    //
    // Choice C - Each message-driven bean is associated with a JMS queue, each bean instance in the pool will receive each message sent to the queue.
    //
    // Choice D - If a message-driven bean is associated with a JMS durable subscription, each bean instance in the pool will receive each message 
    // sent to the durable subscription.
    //
    //
    //
    // Explanation :
    //
    // Choice A is correct
    // 
    // Message-driven beans (MDBs) support concurrent processing for both topics and queues. 
    // Previously, only concurrent processing for queues was supported.
    //
    // To ensure concurrency, the container uses threads from the execute queue.
    //
    // The maximum number of MDBs configured—via the max-beans-in-free-pool deployment descriptor element—to receive messages at one time cannot exceed the maximum number of execution threads. 
    // For example, if max-beans-in-free-pool is set to 50 but 25 is the maximum number of execution threads allowed, only 25 of the MDBs will actually receive messages.
    //
    // Each message-driven bean instance will be invoked only one thread at a time.
}
