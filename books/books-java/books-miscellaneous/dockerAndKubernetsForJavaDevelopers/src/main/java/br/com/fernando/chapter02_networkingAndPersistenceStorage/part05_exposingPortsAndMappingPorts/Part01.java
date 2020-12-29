package br.com.fernando.chapter02_networkingAndPersistenceStorage.part05_exposingPortsAndMappingPorts;

public class Part01 {

    // An image can expose ports.
    // Exposing ports means that your containerized application will listen on an exposed port.
    // As an example, the Tomcat application server will listen on the port 8080 by default.
    // All containers running on the same host and on the same network can communicate with Tomcat on this port.
    //
    // It can be either in the Dockerfile with the EXPOSE instruction or in the docker run command using the --expose option.
    // Take this official Tomcat image Dockerfile fragment (note that it has been shortened for clarity of the example):

    /**
     * <pre>
     * FROM openjdk:8-jre-alpine
     * ENV CATALINA_HOME /usr/local/tomcat
     * ENV PATH $CATALINA_HOME/bin:$PATH
     * RUN mkdir -p "$CATALINA_HOME"
     * WORKDIR $CATALINA_HOME
     * EXPOSE 8080
     * CMD ["catalina.sh", "run"]
     * </pre>
     */

    // As you can see, there's an EXPOSE 8080 instruction near the end of the Dockerfile.
    // It means that we could expect that the container, when run, will listen on port number 8080.
    // Let's run the latest Tomcat image again.
    // Let's run the latest Tomcat image again. This time, we will also give our container a name, myTomcat.
    // Start the application server using the following command:
    //
    // $ docker run -it --name myTomcat --net=myNetwork tomcat
    //
    //
    // For the purpose of checking if containers on the same network can communicate, we will use another image, busybox.
    // BusyBox is software that provides several stripped-down Unix tools in a single executable file.
    // Let's run the following command in the separate shell or command prompt window:
    //
    // $ docker run -it --net container:myTomcat busybox
    // 
    // As you can see, we have instructed Docker that we want our busybox container to use the same network as Tomcat uses. 
    // As an alternative, we could of course go with specifying a network name explicitly, using the --net myNetwork option.
    //
    // $ wget localhost:8080
    //
    // This command on host or another network don't work
}
