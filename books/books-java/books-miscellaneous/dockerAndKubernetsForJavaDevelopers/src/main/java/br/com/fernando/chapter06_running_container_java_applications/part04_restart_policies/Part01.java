package br.com.fernando.chapter06_running_container_java_applications.part04_restart_policies;

public class Part01 {

	// By using the --restart option with the docker run command you can specify a restart policy.
	//
	// This tells Docker how to react when a container shuts down. 
	// The container then can be restarted to minimize downtime, for example if running on a production server.
	//
	// When the docker run command ends with a non-zero code, the exit codes follow the chroot standard, as you can see here:
	//
	// * exit code 125: The docker run command fails by itself
	//
	// * exit code126: The supplied command cannot be invoked
	//
	// * exit code 127: The supplied command cannot be found
	//
	// * Other, non-zero, application dependent exit code
	//
	//  The exit code can be found in the output of the docker ps -a command in a Status column when a container completes. 
	//
	// It's possible to automatically restart crashed containers by specifying a restart policy when starting the container.
	//
	// Specifying the desired restart policy is done by the -restart switch for the docker run command, as in the example:
	//
	// $ docker run --restart=always jboss/wildfly
	//
	// 
	// No Policy
	// 
	// The no policy is the default restart policy and simply will not restart a container under any case. 
	// Actually, you do not have to specify this policy, because this is the default behavior. 
	// Unless you have some configurable setup to run Docker containers, then the no policy can be used as an off switch.
	//
	// Always
	//
	// If we wanted the container to be restarted no matter what exit code the command has, we can use the always restart policy.
	// This is true, even if the container has been stopped before the reboot.
	//
	// On-failure
	//
	// This is a kind of special restart policy and probably the most often used. 
	// By using the on-failure restart policy, you instruct Docker to restart your container whenever it exits with a non-zero exit status and not restart otherwise. 
	// That's the reason we have begun explaining restart policies with the exit codes. 
	// You can optionally provide a number of times for Docker to attempt to restart the container.
	//
	// Example:
	//
	// $ docker run --restart=on-failure:5 jboss/wildfly
	//
	//
	// You should know that Docker uses a delay between restarting the container, to prevent flood-like protection.
	//
	// This is an increasing delay; it starts with the value of 100 milliseconds, then Docker will double the previous delay.
	//
	// In effect, the daemon will wait for 100 ms, then 200 ms, 400, 800 and so on, until either the on-failure limit is reached, or when 
	// you stop the container using docker stop, or execute the force removal by executing the docker rm -f command.
	//
	//
	// Unless-stopped
	//
	// The unless-stopped restart policy acts the same as always with one exception, it will restart the container regardless 
	// of the exit status, but do not start it on daemon startup if the container has been put to a stopped state before
	// 
	// Before you apply the restart policy to your container, it's good to think first what kind of work the container will be used to do. 

	
}
