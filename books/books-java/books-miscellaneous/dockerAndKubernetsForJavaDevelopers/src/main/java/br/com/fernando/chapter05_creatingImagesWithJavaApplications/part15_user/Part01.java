package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part15_user;

public class Part01 {

	// The USER instruction sets the username or UID to use when running the image.
	// It will affect the user for any RUN, CMD, and ENTRYPOINT instructions that will come next in the Dockerfile.
	//
	// The syntax of the instruction is just USER <user name or UID>; for example:
	//
	// USER tomcat
	//
	// You can use the USER command if an executable can be run without privileges. 
	// The Dockerfile can contain the user and group creation instruction the same as this one:
	// 
	// RUN groupadd -r tomcat && useradd -r -g tomcat tomcat
}
