package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part03_from;

public class Part01 {

    // The syntax for the FROM instruction is straightforward. It's just:
    //
    // FROM <image>, or FROM <image>:<tag>, or FROM <image>@<digest>
    //
    // The FROM instruction takes a tag or digest as a parameter.
    // If you decide to skip them, Docker will assume you want to build your image from the latest tag.
    //
    // Layers are identified by a digest, which takes the form
    //
    // The latest tag assigned to an image simply means that it's the image that was last built and executed WITHOUT a specific tag provided.
    //
    // Docker will not take care of checking if you are getting the newest version of the software when pulling the image tagged latest.
    // Docker will throw an error during the build if it cannot find a tag or digest you provide.
    // You should choose the base image wisely.
    // My recommendation would be to always prefer the official repositories that can be found on Docker Hub.
    //
    // For containerizing a Java application, we have two options.
    //
    // * The first one is to use a base Linux image and install Java using the RUN instruction (we will cover RUN in a while).
    // 
    // * The second option will be to pull an image containing the Java runtime already installed. Here you have a lot more to choose from. 
    // For our purposes, let's choose jeanblanchard/java. It's the official Oracle Java running on top of the Alpine Linux distribution. 
    // The base image is small and fast to download. Our FROM instruction will look the same as this:
    //
    // FROM jeanblanchard/java:8
    
    
    // You can run it with:
    // docker build -t some_name .
}
