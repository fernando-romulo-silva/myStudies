package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part17_onbuild;

public class Part01 {

	// The ONBUILD instruction adds to the image a trigger instruction to be executed at a later time, when the image is used as the base for another build. 
	// The trigger will be executed in the context of the child build, as if it had been inserted immediately after the FROM instruction in the child Dockerfile.
	//
	// The syntax of the ONBUILD instruction is as follows:
	//
	// ONBUILD <INSTRUCTION>
	//
	// Consider the following example with Maven and building Java applications.
	// Basically, all your project's Dockerfile needs to do is reference the base container containing the ONBUILD instructions:
	//
	// FROM maven:3.3-jdk-8-onbuild 
	// CMD ["java","-jar","/usr/src/app/target/app-1.0-SNAPSHOT-jar-with-dependencies.jar"]  
	// 
	// There's no magic, and everything becomes clear if you look into the parent's Dockerfile.
	// Run this Dockerfile:
	//
	// $ docker build -t maven:3.3-jdk-8-onbuild .
	// 
	// FROM maven:3-jdk-8 
	// RUN mkdir -p /usr/src/app
	// WORKDIR /usr/src/app
	// ONBUILD ADD . /usr/src/app
	// ONBUILD RUN mvn --version
	//
	// When Docker encounters an ONBUILD instruction during the build process, the builder adds a kind of trigger to the metadata of the image being built. 
	// But this is the only way this image is being affected. 
	// At the end of the build, a list of all triggers is stored in the image manifest, under the key OnBuild. 
	// You can see them using the docker inspect  command, which we already know.
}
