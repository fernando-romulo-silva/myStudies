package br.com.fernando.chapter06_running_container_java_applications.part03_monitoring_containers;

public class Part01 {
	
	// Most applications output their log entries to the standard stdout stream. 
	// If the container is being run in the foreground mode, you will just see it in the console.
	// However, when running a container in detached mode, you will see nothing but the container ID on the console. 
	// 
	// You can display it by using the docker logs command.
	//
	// $ docker logs -f <container name or ID>  
	//
	// The docker logs command will output just a few last lines of the log into the console. As the container still works in the background (in detached mode).
	//
	// The -f flag acts as the same flag in Linux tail command, it will continuously display new log entries on the console
	//
	//
	// The log file is permanent and available even after the container stops, as long as its file system is still present on disk (until it is removed with the docker rm command). 
	// By default, the log entries are stored in a JSON file located in the /var/lib/docker directory.

}
