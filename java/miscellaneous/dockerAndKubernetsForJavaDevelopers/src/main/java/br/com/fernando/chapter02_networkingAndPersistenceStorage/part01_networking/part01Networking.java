package br.com.fernando.chapter02_networkingAndPersistenceStorage.part01_networking;

public class part01Networking {

    // Docker container, Docker provides different ways of configuring networking.
    //
    // There are three different network types Docker delivers out of the box. To list them, execute the docker network ls command:
    //
    // $ docker network ls
    //
    //
    // There are tree types:
    //
    // Bridge
    //
    // This is the default network type in Docker. When the Docker service daemon starts, it configures a virtual bridge, named docker0.
    //
    // If you do not specify a network with the docker run -net=<NETWORK> option, the Docker daemon will connect the container to the bridge network by default.
    //
    // For each container that Docker creates, it allocates a virtual Ethernet device which will be attached to the bridge.
    //
    // The in-container eth0 interface is given an IP address from the bridge's address range.
    // In other words, Docker will find a free IP address from the range available on the bridge and will configure the container's eth0 interface with that IP address.
    //
    // Host
    //
    // This type of network just puts the container in the host's network stack.
    // That is, all of the network interfaces defined on the host will be accessible to the container
    //
    // If you start your container using the -net=host option, then the container will use the host network.
    // It will be as fast as normal networking: there is no bridge, no translation, nothing.
    //
    //
    // None
    //
    // There is no driver being used by this network type. It's useful when you don't need your container to have network access;
    // the -net=none switch to docker run command completely disables networking.
}
