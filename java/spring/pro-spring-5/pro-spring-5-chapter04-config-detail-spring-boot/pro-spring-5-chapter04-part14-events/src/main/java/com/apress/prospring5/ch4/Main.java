package com.apress.prospring5.ch4;

public class Main {

    // Typically, we use events for reactionary logic that executes quickly and is not part of the main application logic.
    //
    // For processes that are long running and form part of the main business logic, it is recommended to use JMS or similar
    // messaging systems such as RabbitMQ. The main benefits of using JMS are that it is more suited to long-running processes,
    // and as the system grows, you can, if necessary, factor the JMS-driven processing of messages containing business
    // information onto a separate machine .
}
