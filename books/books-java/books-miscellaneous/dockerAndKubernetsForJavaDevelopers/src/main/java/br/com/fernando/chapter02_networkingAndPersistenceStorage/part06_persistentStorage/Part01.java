package br.com.fernando.chapter02_networkingAndPersistenceStorage.part06_persistentStorage;

public class Part01 {
    //
    // You can create, modify, and delete files as you wish; if you commit the changes back into the image, they will become persisted.
    // This is a great feature if you want to create a complete setup of your application in the image, altogether with all its environment.
    // But, this is not very convenient when it comes to storing and retrieving data.
    //
    // Volumes are not part of the union filesystem, and so the write operations are instant and as fast as possible, there is
    // no need to commit any changes.
    //
    // Volumes live outside of the union filesystem and exist as normal directories and files on the host filesystem.
    //
    // There are three main use cases for Docker data volumes:
    //
    // To share data between the host filesystem and the Docker container
    // To keep data when a container is removed
    // To share data with other Docker containers
}
