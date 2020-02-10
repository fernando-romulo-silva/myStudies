package br.com.fernando.chapter06_running_container_java_applications.part01_starting_stopping_containers;

public class Part02 {

	// Stopping
	//
	// To stop one or more running Docker containers we use the docker stop command. The syntax is simple:
	// 
	// $ docker stop [OPTIONS] CONTAINER [CONTAINER...]  
	//
	// You can specify one or more container to stop. The only option for docker stop is -t (--time) which allows us to specify a time to wait before stopping a container. 
	// 10 seconds is the default value, which is supposed to be enough for the container to gracefully stop.
	//
	// To stop the container in a more brutal way, you can execute the following command:
	//
	// $ docker kill  CONTAINER [CONTAINER...]  
	//
	//
	// There's an important difference though:
	//
	// docker stop: The main process inside the container will first receive a SIGTERM, and after a grace period, a SIGKILL
	//
	// docker kill: The main process inside the container will be sent SIGKILL (by default) or any signal specified with option --signal
	//
	// In other words, docker stop attempts to trigger a graceful shutdown by sending the standard POSIX signal SIGTERM, 
	// whereas docker kill just brutally kills the process and, therefore, shuts down the container.
}
