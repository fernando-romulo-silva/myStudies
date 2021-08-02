package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part01_dockerfile;

public class Part01 {

    // It's a plain text file containing instructions which are executed by Docker in the order they are placed.
    // Each Dockerfile has a base image that the Docker engine will use to build upon.
    // A resulting image will be a specific state of a file system: a read-only, frozen immutable snapshot of a live container,
    // composed of layers representing changes in the filesystem at various points in time.
    //
    // If you create an image containing a Java application, you can also skip the second step and utilize one of the Docker Maven plugins available.
    // After we learn how to build images using the docker build command, we will also create our image using Maven.
    // When building using Maven, the context to the docker build command (or a build process, in this case) will be provided automatically by Maven itself.
    // Actually, there is no need for the Dockerfile at all, it will be created automatically during the build process.

}
