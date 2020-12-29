package br.com.fernando.chapter06_running_container_java_applications.part05_runtime_contraints_on_resources;

public class Part03 {
	
	// Updating constraints on a running container
	//
	// As with the restart policies, the constraints can also be updated when the container is already running. 
	//
	// As with restart policies, the syntax for the docker update command will be the same as when starting the container, you specify the desired constraints 
	// as an argument for the docker update command and then give the container ID or its name. 
	//
	// $ docker update --cpu-shares 512 abbdef1231677
	//
	// The preceding command will limit the CPU shares to the value of 512. 
	// Of course, you can apply CPU and memory constraints at the same time, to more than one container:
	//
	// $ docker update --cpu-shares 512 -m 500M abbdef1231677 dabdff1231678  
	//
	// The preceding command will update CPU shares and memory limits to two containers, identified by abbdef1231677 and dabdff1231678.
	//
	// Of course, when updating the runtime constraints, you can also apply the desired restart policy in one single command, as in the following example:
	//
	// $ docker update --restart=always -m 300M aabef1234716
	//
	// As you can see, the ability to set constraints gives you a lot of flexibility when running Docker containers. 
	// But it's worth noting, that applying constraints is not always possible. 
	// The reason for that is the constraint setting features depend heavily of the internals of the Docker host, especially its kernel.


}
