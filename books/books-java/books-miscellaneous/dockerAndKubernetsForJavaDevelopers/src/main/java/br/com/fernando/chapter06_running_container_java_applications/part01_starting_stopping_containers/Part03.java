package br.com.fernando.chapter06_running_container_java_applications.part01_starting_stopping_containers;

public class Part03 {
	
	// Listing the running containers
	//
	// To list the running containers, simply execute the docker ps command:
	//
	// $ docker ps
	//
	// To include all containers present on your Docker host, include the -a option:
	//
	// $ docker ps -a
	//
	// You can also filter the list using -f option to specify a filter. 
	// The filter needs to be provided as a key=value format. Currently available filters include:
	//
	// 
	// * id: Filters by the container's id
	//
	// * label: Filters by label
	//
	// * name: Filters by the container's name
	//
	// * exited: Filters by the container's exit code
	//
	// * status: Filters by status, which can be created, restarting, running, removing, paused, exited or dead
	//
	// * volume: When specified with volume name or mount point will include containers that mount specified volumes
	//
	// * network: When specified with ...
	//
	// Ex:
	//
	// $ docker ps -a -f status=dead
	
}
