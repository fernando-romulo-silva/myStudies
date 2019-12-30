package br.com.fernando.chapter01_introductionToDoker.part02_dockerConcepts;

public class Part04RegisterAndRepositoryAndIndex {

    // The Docker registry is a service (an application, in fact) that is storing your Docker images.
    // The Docker Hub is an example of the publicly available registry; it's free and serves a huge, constantly growing collection of existing images.
    //
    // The repository, on the other hand, is a collection (namespace) of related images, usually providing different versions of the same application or service.
    // It's a collection of different Docker images with the same name and different tags.
    //
    // The command downloads the image tagged 16.04 within the ubuntu repository from the Docker Hub registry.
    // The official ubuntu repository doesn't use a username, so the namespace part is omitted in this example.
    //
    // docker pull ubuntu:16.04
    //
    // An index manages searching and tagging and also user accounts and permissions.
    // In fact, the registry delegates authentication to the index.
    // When executing remote commands, such as push or pull, the index first will look at the name of the image and then check to see if it has a corresponding repository.
    // If so, the index verifies if you are allowed to access or modify the image.
    // If you are, the operation is approved and the registry takes or sends the image.
    //
}
