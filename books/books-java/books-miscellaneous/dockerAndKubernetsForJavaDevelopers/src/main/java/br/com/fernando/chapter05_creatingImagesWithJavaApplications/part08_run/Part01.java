package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part08_run;

public class Part01 {

    // The RUN instruction is the central executing instruction for the Dockerfile.
    // In essence, the RUN instruction will execute a command (or commands) in a new layer on top of the current image and then commit the results.
    // Remember, layering is the core concept in Docker. RUN, takes a command as its argument and runs it to create the new layer.
    // Each RUN instruction creates a new layer in the image.
    //
    // Let's say we have the following Dockerfile, installing Java runtime:
    //
    // FROM ubuntu
    // RUN apt-get update
    // RUN apt-get install -y openjdk-8-jre
    //
    //
    // If we build an image from this Dockerfile, all layers from two RUN instructions will be put into the layers cache.
    // But, after a while you decide you want the node.js package in your image, so now the Dockerfile looks the same as this:
    //
    // FROM ubuntu
    // RUN apt-get update
    // RUN apt-get install -y openjdk-8-jre
    // RUN apt-get install -y nodejs
    //
    // If you run the docker build for the second time, Docker will reuse the layers by taking them from the cache. As a result,
    // the apt-get update will not be executed, because the cached version will be used.
    //
    // In effect, your newly created image will potentially have an outdated version of the java and node.js packages.
    //
    // We should always combine RUN apt-get update with apt-get install in the same RUN statement, which will create just a single layer; for example:
    //
    // RUN apt-get update \
    // && apt-get install -y openjdk-8-jre \
    // && apt-get install -y nodejs \
    // && apt-get clean
}
