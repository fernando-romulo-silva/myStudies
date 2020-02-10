package br.com.fernando.chapter06_running_container_java_applications.part01_starting_stopping_containers;

public class Part01 {

	// Starting
	//
	// Every single docker run command creates a new container and executes a command specified in the Dockerfile, CMD, or ENTRYPOINT.
	//
	// The syntax of the docker run command is as follows:
	//
	// $ docker run [OPTIONS] IMAGE[:TAG|@DIGEST] [COMMAND] [ARG...]
	//
	// The command takes the image name, with the optional TAG or DIGEST.
	// If you skip the TAG and DIGEST command parameters, Docker will run the container based on the image tagged latest.
	//
	//
	// The COMMAND parameter is not mandatory, the author of the image may have already provided a default COMMAND using the CMD instruction in the Dockerfile.
	// The CMD occurs only once in a Dockerfile and it's usually the last instruction. 
	// When starting the container from an image, we can override the CMD instruction, simply by providing our own command or parameters as the COMMAND parameter for the docker run.
	// Anything that appears after the image name in the docker run command will be passed to the container and treated as CMD arguments.
}
