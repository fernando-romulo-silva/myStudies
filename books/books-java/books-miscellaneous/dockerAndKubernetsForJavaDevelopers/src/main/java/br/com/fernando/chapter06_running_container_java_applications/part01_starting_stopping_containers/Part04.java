package br.com.fernando.chapter06_running_container_java_applications.part01_starting_stopping_containers;

public class Part04 {

	// Removing the containers
	//
	// To remove the container from the host, we use the docker rm command. The syntax is as follows: 
	// 
	// $ docker rm [OPTIONS] CONTAINER [CONTAINER...]  
	// 
	// Ex:
	// 
	// $ docker rm container f44a5259c034
	//
	//
	// If you are running short-term foreground processes over and over many times, these file systems can grow rapidly in size
	//
	// Tell Docker to automatically clean up the container and remove the file system when the container exits.
	// 
	// You do this by adding the --rm flag, so that the container data is removed automatically after the process has finished.
	//
	// $ docker run --rm -it ubuntu /bin/bash  
	//
	// The preceding command tells Docker to remove the container if it's shut down.
	
}
