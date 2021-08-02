package br.com.fernando.chapter02_networkingAndPersistenceStorage.part02_networkingCommands;

public class Part01 {

    // Networking commands
    //
    // This is the command we have been using previously, it simply lists networks available for your containers.
    // It will output the network identifier, its name, the driver being used, and a scope of the network.
    //
    // $ docker network ls
    //
    //
    // Creates new network. The full syntax of the command is, docker network create [OPTIONS] NETWORK.
    // We will use the command in a short while
    //
    // $ docker network create nameNetWork
    //
    //
    // The rm command simply removes the network
    //
    // $ docker network rm myNetwork
    //
    //
    // Connects the container to the specific network
    //
    // $ docker network connect myNetwork myContainer
    //
    //
    // As the name suggests, it will disconnect the container from the network
    //
    // $ docker network disconnect myNetwork myContainer
    //
    //
    // The docker network inspect command displays detailed information about the network.
    // It's very useful, if you have network issues. We are going to create and inspect our network now
    //
    // $ docker network inspect
    //
    //

}
