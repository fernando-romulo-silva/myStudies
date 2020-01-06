package br.com.fernando.chapter02_networkingAndPersistenceStorage.part09_removingVolume;

public class Part01 {

    // You can remove a volume by referencing a container's name and executing the docker rm -v command:
    //
    // $ docker rm -v <containerName or ID>
    //
    // Docker will not warn you, when removing a container without providing the -v option, to delete its volumes.
    // As a result, you will have dangling volumes—volumes that are no longer referenced by a container
    //
    // Another option to remove the volume is by using the docker volume rm command:
    //
    // $ docker volume rm <volumeName or ID>
    //
    // If the volume happens to be in use by the container, Docker Engine will not allow you to delete it and will give you a warning message
}
