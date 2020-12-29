package br.com.fernando.chapter06_running_container_java_applications.part02_container_running_modes;

public class Part02 {

	// You can start a Docker container in detached mode with a -d option. It's the opposite of the foreground mode.
	// The container starts up and runs in background, the same as a daemon or a service.
	//
	// $ docker run -d -p 8080:8080 jboss/wildfly
	//
	// After the container starts, you will be given a control and can use a shell or command line for executing other commands.
	//
	// Docker will jus ouput the container ID:
	//
	// c0b5ee0286fd6af9966d94002b7d54839c56bb157dbd42e58ba09fd0fb3cac5c
	//
	//
	// You can use the container ID to reference the container in other docker commands, 
	// for example, if you need to stop the container or attach to it.
	//
	//
	//
}
