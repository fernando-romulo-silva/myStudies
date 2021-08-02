package br.com.fernando.chapter02_networkingAndPersistenceStorage.part08_creatingVolume;

public class Part01 {

    // There are two ways to create volumes.
    //
    // * The ﬁrst one is to specify the -v option when running an image.
    //
    // $ docker run -v $DOCKER_VOLUMES_HOME/volume1:/volume -it busybox
    //
    // In the previous command, we have created a volume using the -v switch and instructed Docker that the host directory
    // $DOCKER_VOLUMES_HOME/volume1 should be mapped into the /volume directory in the running container.
    //
    // The parameters in the -v options are the directory on the host (your own operating system in this case, it is $DOCKER_VOLUMES_HOME/volume1 in our example),
    // a colon (:), and a path at which it will be available for the container, /volume1 in our example.
    //
    // The volume created is a kind of mapped directory.
    // It will be available for the container and also available from the host operating system.
    //
    // The -v option can be used not only for directories but for a single file as well.
    // This can be very useful if you want to have configuration files available in your container.
    // The best example for this is the example from the official Docker documentation:
    //
    // $ docker run -it -v ~/.bash_history:/root/.bash_history ubuntu
    //
    //
    // * The simplest form of creating a nameless volume will be just:
    //
    // $ docker volume create
    //
    // As the output, Docker will give you the volume identiﬁer, which you can later use to refer to this volume.
    // It's better to give a volume a meaningful name.
    // To create a standalone, named volume, execute the following command:
    //
    // $ docker volume create --name myVolume
    //
    //
    // To list the volumes we now have available, execute the docker volume ls command:
    //
    // $ docker volume ls
    //
    // Volumes created this way will not be mapped explicitly with a path on the host.
    // If the container's base image contains data at the specified mount point (as a result of Dockerfile processing),
    // this data will be copied into the new volume upon volume initialization.
    //
    //
    // Volumes created this way will not be mapped explicitly with a path on the host.
    // If the container's base image contains data at the specified mount point (as a result of Dockerfile processing),
    // this data will be copied into the new volume upon volume initialization.
    //
    // This is different in comparison to specifying a host directory explicitly.
    //
    // The idea behind it is that when creating your image, you should not care about the location of the volume on the host system,
    // making the image portable between different hosts.

}
