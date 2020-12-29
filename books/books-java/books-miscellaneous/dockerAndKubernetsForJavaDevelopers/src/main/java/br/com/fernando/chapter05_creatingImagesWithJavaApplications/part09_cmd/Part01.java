package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part09_cmd;

public class Part01 {

	// The purpose of a CMD instruction is to provide defaults for an executing container.
	//
	// This can be an executable, or, if you specify the ENTRYPOINT instruction,
	// you can omit the executable and provide the default parameters only.
	//
	// The CMD instruction syntax can have two forms:
	//
	// CMD ["executable","parameter1","parameter2"]
	//
	// This is a so called exec form. It's also the preferred and recommended form.
	// The parameters are JSON array, and they need to be enclosed in square brackets.
	// The important note is that the exec form does not invoke a command shell when the container is run.
	// It just runs the executable provided as the first parameter.
	// If the ENTRYPOINT instruction is present in the Dockerfile, CMD provides a default set of parameters for the ENTRYPOINT instruction.
	//
	// CMD command parameter1 parameter2
	//
	// This a shell form of the instruction.
	// This time, the shell (if present in the image) will be processing the provided command.
	// The specified binary will be executed with an invocation of the shell using /bin/sh -c.
	// It means that if you display the container's hostname, for example, using CMD echo $HOSTNAME, you should use the shell form of the instruction.
	//
	// 
	// The command supplied through the RUN instruction is executed during the build time, whereas the command specified through the CMD instruction 
	// is executed when the container is launched by executing docker run on the newly created image.
	//
	// Unlike CMD, the RUN instruction is actually used to build the image, by creating a new layer on top of the previous one which is committed.
	//
	// We are going to run it from within the Docker container. Let's write the basic Dockerfile using the command we already know and place it in the root of our project:
	//
	// FROM jeanblanchard/java:8
	// COPY target/rest-example-0.1.0.jar rest-example-0.1.0.jar
	// CMD java -jar rest-example-0.1.0.jar
	//
	// Create the image with:
	//
	// $ docker build . -t rest-example
	//
	// And run the container:
	// 
	// $ docker run -it rest-example 

}
