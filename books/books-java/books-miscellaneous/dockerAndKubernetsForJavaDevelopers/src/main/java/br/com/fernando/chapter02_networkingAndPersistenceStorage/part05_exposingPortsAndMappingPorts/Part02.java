package br.com.fernando.chapter02_networkingAndPersistenceStorage.part05_exposingPortsAndMappingPorts;

public class Part02 {

    // So far so good, containers running on the same host and the same network can communicate with each other.
    // But what about communicating with our container from the outside?
    //
    // Mapping ports comes in handy. We can map a port, exposed by the Docker container, into the port of
    // the host machine, which will be a localhost in our case.
    //
    // To bind a port (or group of ports) from a host to the container, we use the -p flag of the docker run command, as in the following example:
    //
    // $ docker run -it --name myTomcat2 --net=myNetwork -p 8080:8080 tomcat
    //
    // Then, we can simply enter the following address in our favorite web browser: http://localhost:8080.
    //
    //
    // The syntax of the -p switch is quite straightforward: you just enter the host port number, a colon, and then a port number
    // in the container you would like to be mapped:
    //
    // $ docker run -p <hostPort>:<containerPort> <image ID or name>
    //
    //
    // The Docker image can expose a whole range of ports to other containers using either the EXPOSE instruction in a
    // Dockerfile (the same as EXPOSE 7000-8000, for example) or the docker run command, for example:
    //
    // $ docker run --expose=7000-8000 <container ID or name>
    //
    //
    // You can then map a whole range of ports from the host to the container by using the docker run command:
    //
    // $ docker run -p 7000-8000:7000-8000 <container ID or name>
    //
    //
    // You may ask, what is the difference between exposing and mapping ports, that is, between --expose switch and -p switches?
    // Well, the --expose will expose a port at runtime but will not create any mapping to the host.
    // Exposed ports will be available only to another container running on the same network, on the same Docker host.
    // The -p option, on the other hand, is the same as publish: it will create a port mapping rule, mapping a port on the container with the port on the host system.
    // The mapped port will be available from outside Docker. Note that if you do -p, but there is no EXPOSE in the Dockerfile, Docker will do an implicit EXPOSE.
    // This is because, if a port is open to the public, it is automatically also open to other Docker containers.

}
