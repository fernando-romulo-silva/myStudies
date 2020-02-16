package br.com.fernando.chapter06_running_container_java_applications.part04_restart_policies;

public class Part02 {

	// Updating a restart policy on a running container
	//
	// Sometimes, there's a need to update the Docker runtime parameters after the container has already started, on the fly.
	//
	// Apart from other runtime parameters (such as memory or CPU constraints for example, which we are going to discuss later in this chapter), 
	// the docker update command gives you the option to update the restart policy on a running container. 
	//
	// The syntax is quite straightforward, you just need to provide the new restart policy that you would like the container to have and the container's ID or name:
	//
	// $ docker update --restart=always <CONTAINER_ID or NAME>
	//
	//  if you execute the update command on a container that is stopped, the policy will be used when you start the container later on. 
	// The possible options are exactly the same as those you can specify when starting the container:
	//
	// * no (which is default)
	// * always
	// * on-failure
	// * unless-stopped
	// 


	
}
