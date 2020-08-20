package br.com.fernando.myExamCloud.useJavaMessageServiceAPI;

public class Question01 {

    // Which of the following statements are true about Java EE 7 JMS message-driven bean with bean-managed transactions?
    // [ Choose two ]
    //
    //
    // Choice A - The message receipt is part of the user transaction.
    //
    // Choice B - Message acknowledgement is automatically handled by the container.
    //
    // Choice C - Messages are always processed in the order they were sent to the queue
    //
    // Choice D - Two messages read from the same queue may be processed at the same time within the same EJB container.
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
    // Choice B and D are correct answers.
    //
    // A message-driven bean is an enterprise bean that allows Java EE applications to process messages asynchronously.
    // It acts as a JMS message listener, which is similar to an event listener except that it receives messages instead of events.
    //
    // The messages may be sent by any Java EEcomponent--an application client, another enterprise bean, or a Web component--or by a JMS application or system
    // that does not use Java EE technology.
    //
    // Message acknowledgement is automatically handled by the container.
    //
    // Two messages read from the same queue may be processed at the same time within the same EJB container.
    //
    // Exam Objective: Use transactions with JMS API

}
