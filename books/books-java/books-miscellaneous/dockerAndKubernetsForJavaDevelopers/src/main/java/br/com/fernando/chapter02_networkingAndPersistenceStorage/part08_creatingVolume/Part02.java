package br.com.fernando.chapter02_networkingAndPersistenceStorage.part08_creatingVolume;

public class Part02 {

    // Another option to share the volume between containers is the -volumes-from switch.
    // If one of your containers has volumes mounted already, by using this option we can instruct Docker to use the volume mapped in some other container,
    // instead of providing the name of the volume. Consider this example:
    //
    // $ docker run -it -volumes-from myBusybox4 --name myBusybox5 busybox
    //
    //
    // Volumes that are no longer used by any container can be easily removed by using the docker volumes prune command:
    //
    // $ docker volume prune
    //
    //
    // Last but not least, another way of creating a volume is the VOLUME CREATE instruction in a Dockerfile.
    // Creating volumes using the VOLUME CREATE instruction has one but very important difference in comparison to using the -v option during
    // the container startup: you cannot specify a host directory when using VOLUME CREATE.
    //
    //
    // The host directory is 100% host-dependent and will break on any other machine, which is a little bit off from the Docker's idea.
    // Because of this, it is only possible to use portable instructions within a Dockerfile.

}
