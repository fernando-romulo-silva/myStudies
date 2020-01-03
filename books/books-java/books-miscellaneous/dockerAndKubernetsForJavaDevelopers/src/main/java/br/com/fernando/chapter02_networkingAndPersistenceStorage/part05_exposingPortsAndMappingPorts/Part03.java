package br.com.fernando.chapter02_networkingAndPersistenceStorage.part05_exposingPortsAndMappingPorts;

public class Part03 {

    // The -P switch (capital P this time) will map a dynamically allocated random host port to all container ports that
    // have been exposed in the Dockerfile by the EXPOSE instruction.
    //
    // If you run the following command, Docker will map a random port on the host to Tomcat's exposed port number 8080:
    //
    // $ docker run -it --name myTomcat3 --net=myNetwork -P tomcat
    //
    // As you can see in the previous screenshot, our myTomcat3 container will have the 8080 port mapped to port number 32772 on the host.
    // Again, executing the HTTP GET method on the http://localhost:32772 address will give us myTomcat3's welcome page.
    //
    // An alternative to the docker ps command is the docker port command, used with the container ID or with a name as a parameter
    // (this will give you information about what ports have been mapped). In our case, this will be:
    //
    // $ docker port myTomcat3
    //
    // As a result, Docker will output the mapping, saying that port number 80 from the container has been mapped
    // to port number 8080 on the host machine
    //
    // Information about all the port mappings is also available in the result of the docker inspect command.
    // Execute the following command:
    //
    //
}
