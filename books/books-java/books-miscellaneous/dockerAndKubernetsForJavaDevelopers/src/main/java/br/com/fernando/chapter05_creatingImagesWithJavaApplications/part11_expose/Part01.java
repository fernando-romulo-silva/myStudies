package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part11_expose;

public class Part01 {

	// The EXPOSE instruction informs Docker that the container listens on the specified network ports at runtime.
	// Docker uses the EXPOSE command followed by a port number to allow incoming traffic to the container.
	//
	// We already know that EXPOSE does not make the ports of the container automatically accessible on the host.
	// To do that, you must use either the -p flag to publish a range of ports or the -P flag to publish all of the exposed ports at once.
	//
	// Let's get back to our Dockerfile and expose a port:
	//
	// FROM jeanblanchard/java:8
	// COPY target/rest-example-0.1.0.jar rest-example-0.1.0.jar
	// CMD java -jar rest-example-0.1.0.jar
	// EXPOSE 8080
	//
	// You'll notice that Docker outputs the fourth layer, saying that port 8080 has been exposed. 
	//
	// Exposed ports will be available for the other containers on this Docker host, and, if you map them during runtime, also for the external world. 
	// Well, let's try it, using the following docker run command:
	//
	// $ docker run -p 8080:8080 -it rest-example



}
