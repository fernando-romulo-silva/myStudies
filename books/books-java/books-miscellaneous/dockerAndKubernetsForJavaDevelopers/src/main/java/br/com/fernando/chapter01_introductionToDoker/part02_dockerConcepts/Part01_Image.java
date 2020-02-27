package br.com.fernando.chapter01_introductionToDoker.part02_dockerConcepts;

public class Part01_Image {

    // Think of an image as a read-only template which is a base foundation to create a container from.
    // Every image starts from a base image; for example, Ubuntu; a Linux image.
    // Although you can begin with a simple image and build your application stack on top of it, you can also pick an already prepared image from the hundreds available on the Internet.
    //
    // Images are created using a series of commands, called instructions. Instructions are placed in the Dockerfile.
    // The Dockerfile is just a plain text file, containing an ordered collection of root filesystem changes
    // (the same as running a command that starts an application server, adding a file or directory, creating environmental variables, and so on.)

    // Images are created using a series of commands, called instructions.
    // Instructions are placed in the Dockerfile.
    // The Dockerfile is just a plain text file, containing an ordered collection of root filesystem changes
    // (the same as running a command that starts an application server, adding a file or directory, creating environmental variables, and so on.)
    // and the corresponding execution parameters for use within a container runtime later on.
    // Docker will read the Dockerfile when you start the process of building an image and execute the instructions one by one.
    // The result will be the final image.
    //
    //
}
