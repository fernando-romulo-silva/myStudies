package br.com.fernando.chapter02_networkingAndPersistenceStorage.part04_connectingContainerNetwork;

public class Part01 {

    // Now we have our myNetwork ready, we can run the Docker container and attach it to the network.
    // To launch containers, we are going to user the docker run --net=<NETWORK> option, where the <NETWORK> is the name of one of the
    // default networks or the one you have created yourself.
    //
    // $ docker run -d --net=myNetwork --name myPostgre postgres
    //
    // $ docker run -it --net container:myTomcat busybox
    //
    // The next command will run the busybox image, using the same network as myTomcat uses.
    // This is a very common scenario; your application will run on the same network as the database and this will allow them to communicate.
    // Of course, the containers you launch into the same network must be run on the same Docker host.
    // Each container in the network can directly communicate with other containers in the network.
}
