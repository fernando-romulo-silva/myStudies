package br.com.fernando.myExamCloud.useJavaMessageServiceAPI;

public class Question03 {

    // You are working with JMS publish-subscribe operations.
    // What happens when a producer publishes a message to a topic for which a durable subscription exists but there are no subscribers available?
    //
    // Choice A
    // The publisher waits for a subscriber, who then consumes it. 
    // However, the publisher will time out if no consumer arrives within the given timeout period.
    //
    // Choice B
    // The publisher sends the message. 
    // However, it is never consumed because there wasn't anything listening when it arrived, regardless of the message timeout length.
    //
    // Choice C
    // The publisher successfully sends a message, which will be consumed later, once there is a subscriber, assuming the message hasn't timed out.
    //
    // Choice D
    // The message publisher is immediately notified about the lack of subscribers and can decide for itself if, and when, to resend.
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
    // Choice C is correct.
    //
    // A durable subscriber registers a durable subscription with a unique identity that is retained by the JMS provider.
    // Subsequent subscriber objects with the same identity resume the subscription in the state in which it was left by the previous subscriber.
    // If a durable subscription has no active subscriber, the JMS provider retains the subscription's messages until they
    // are received by the subscription or until they expire.
}
