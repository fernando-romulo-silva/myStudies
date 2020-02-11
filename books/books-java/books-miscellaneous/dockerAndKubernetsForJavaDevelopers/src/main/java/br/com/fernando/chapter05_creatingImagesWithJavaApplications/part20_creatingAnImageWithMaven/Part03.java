package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part20_creatingAnImageWithMaven;

public class Part03 {

	// assembly:
	// The <assembly> element defines how to build artifacts and other files that can enter the Docker image.
	// You can use targetDir element to provide a directory under which the files and artifacts contained in the assembly will be copied into the image.
	// The default value for this is /maven.
	// The <descriptorRef> is kind of a handy shortcut, which can take the following values:
	//
	// * artifact-with-dependencies:
	// Attaches a project's artifact and all its dependencies.
	// Also, when a classpath file exists in the target directory, this will be added to.
	//
	// * artifact: Attaches only the project's artifact but no dependencies.
	//
	// * project: Attaches the whole Maven project but without the target/ directory.
	//
	// * rootWar: Copies the artifact as ROOT.war to the exposed directory.
	// For example, Tomcat can then deploy the war under root context.
	//
	//
	// buildArgs:
	// Allows for providing a map specifying the value of Docker buildArgs, which should be used when building the image with an external Dockerfile which uses build arguments
	//
	// buildOptions:
	// A map specifying the build options to provide to the Docker daemon when building the image.
	//
	// cleanup:
	// This is useful to clean up untagged images after each build (including any containers created from them). 
	// The default value is try which tries to remove the old image, but doesn't fail the build if this is not possible because, 
	// for example, the image is still used by a running container.
	// 
	// As you can see, the plugin configuration is very flexible, it contains a complete set of equivalents for Dockerfile instructions.
	//
}
