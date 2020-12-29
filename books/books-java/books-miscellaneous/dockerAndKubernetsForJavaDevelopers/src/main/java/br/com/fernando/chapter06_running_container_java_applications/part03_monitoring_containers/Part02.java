package br.com.fernando.chapter06_running_container_java_applications.part03_monitoring_containers;

public class Part02 {

	// The docker ps command we have been using to list the running containers gives us a lot of information about containers, such as their IDs, uptime, mapped ports, and so on.
	//
	// To display more details about the running container, we can user docker inspect.
	//
	// The syntax of the command is as follows:
	//
	// $ docker inspect [OPTIONS] CONTAINER|IMAGE|TASK [CONTAINER|IMAGE|TASK...]  
	//
	// If we know what we are looking for, we can provide a template for processing the output, using the -f (or --format) option. 
	// 
	// Ex:
	// 
	// $ docker inspect a6c1fe393b59
	//
	//
	// By default, the docker inspect command will output information about the container or image in a JSON array format. 
	// Because there are many properties, it may not be very readable. 
	// If we know what we are looking for, we can provide a template for processing the output, using the -f (or --format) option.
	//
	// $ docker inspect -f '{{.State.ExitCode}}' a6c1fe393b59
	// 
	// $ docker inspect -f '{{.State.ExitCode}}' $(docker ps -aq)
	
}
