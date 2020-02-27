package br.com.fernando.chapter01_introductionToDoker.part02_dockerConcepts;

public class Part03_Container {

    // A running instance of an image is called a container. Docker launches them using the Docker images as read-only templates.
    // If you start an image, you have a running container of this image.
    // Naturally, you can have many running containers of the same image.
    //
    // To run a container, we use the docker 'run' command:
    //
    // $ docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
    //
    // Docker will check if the image that you would like to run is available on your local machine.
    // If not, it will be pulled down from the remote repository.
    // The Docker engine takes the image and adds a writable layer on top of the image's layers stack.
    // Next, it initializes the image's name, ID, and resource limits, such as CPU and memory.
    // In this phase, Docker will also set up a container's IP address by finding and attaching an available IP address from a pool.
    // The last step of the execution will be the actual command, passed as the last parameter of the docker run command.
    //
    // You can now do things you would normally do when preparing an operating system to run your applications.
    // This can be installing packages (via apt-get, for example), pulling source code with Git, building your Java application using Maven, and so on.
    //
    // To list all containers you have on your system, either running or stopped, execute the docker ps command:
    //
    // $ docker ps -a
    //
    // To stop a container, you can use the docker stop command:
    //
    // $ docker stop $(docker ps -a -q)
    //
    // To remove a container, you can just use the docker rm command:
    //
    // $ docker rm $(docker ps -a -q -f status=exited)
    //
    // When the container is started, the writable layer on top of the layers stack is for our disposal.
    // We can actually make changes to a running container; this can be adding or modifying files, the same as installing a software package,
    // configuring the operating system, and so on.
    //
    // Our changes are only possible in the top layer. The union filesystem will then cover the underlying file.
    // The original, underlying file will not be modified; it still exists safely in the underlying, read-only layer.
    //
    // $ docker commit <container-id> <image-name>
    //
    //
    // The docker commit command saves changes you have made to the container in the writable layer.
    // To avoid data corruption or inconsistency, Docker will pause a container you are committing changes into
    //
    //
    // Create A Container
    //
    // $ docker create --name myTomcat tomcat
    //
    //
    // Run a already existed container
    //
    // $ docker start myPostgre
    //
    // $ docker restart myPostgre
    //
    //
    // Create and Run A Container
    //
    // $ docker run -d --name myTomcat tomcat
}
