package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part16_arg;

public class Part01 {

	// The ARG instruction is being used to pass an argument to the Docker daemon during the docker build command. 
	// By using the --build-arg switch, you can assign a value to the defined variable:
	//
	// $ docker build --build-arg <variable name>=<value> .
	//
	// The value from the --build-arg will be passed to the daemon building the image. 
	// You can specify multiple arguments using multiple ARG instructions.
	//
	// You specify the default argument value this way:
	//
	// FROM ubuntu
	// ARG user=jarek
	//
	// If no argument will be specified before starting the build, the default value will be used
	
}
