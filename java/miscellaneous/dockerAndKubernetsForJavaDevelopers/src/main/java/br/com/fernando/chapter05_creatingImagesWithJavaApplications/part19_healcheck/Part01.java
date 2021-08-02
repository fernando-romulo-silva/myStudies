package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part19_healcheck;

public class Part01 {
	
	// The HEALTHCHECK instruction can be used to inform Docker how to test a container to check that it is still working. 
	// This can be checking if our rest service responds to HTTP calls or just listens on a specified port.
	//
	// A container can have several statuses which can be listed using the docker ps command. 
	// These can be created, restarting, running, paused, exited, or dead.
	//
	// The HEALTHCHECK status is initially starting. 
	// Whenever a health check passes, it becomes healthy (whatever state it was previously in). 
	// After a certain number of consecutive failures, it becomes unhealthy.
	//
    // The syntax for a HEALTHCHECK instruction is as follows:
	//
	// HEALTHCHECK --interval=<interval> --timeout=<timeout> CMD <command>
	//
	// The <interval> (the default value is 30 seconds) and <timeout> (again, the default is 30 seconds) are time values, specifying the checking interval and timeout accordingly. 
	// The <command> is the command actually being used to check if the application is still running. 
	// The exit code of the <command> is being used by Docker to determine if a health check failed or succeeded. 
	// The values can be 0, meaning the container is healthy and ready for use and 1 meaning that something is wrong and the container is not working correctly.
	//
	// The Java microservice healthcheck implementation could be just a simple /ping REST endpoint, returning whatever (as a timestamp) or 
	// even returning an empty response with HTTP 200 status code proving it's alive.
	//
	// HEALTHCHECK --interval=5m --timeout=2s --retries=3 CMD curl -f http://localhost/ping || exit 1

}
