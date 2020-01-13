package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part06_add;

public class Part01 {

    // What ADD basically does is copy the files from the source into the container's own filesystem at the desired destination.
    //
    // It takes two arguments: the source (<source path or URL>) and a destination (<destination path>):
    //
    // ADD <source path or URL> <destination path >
    //
    // The source can have two forms: it can be a path to a file, a directory, or the URL.
    //
    // The path is relative to the directory in which the build process is going to be started
    //
    // The source and destination paths can contain wildcards.
    //
    // Those are the same as in a conventional file system: * for any text string, or ? for any single character.
    //
    // If you need, you can specify multiple source paths, and separate them with a comma.
    // All of them must be relative to the build context, the same as if you have just a single source path.
    //
    // If your source or destination paths contain spaces, you will need to use a special syntax, adding the square brackets around:
    //
    // ADD ["<source path or URL>" "<destination path>"]
    //
    // The ADD command is not only about copying files from the local file system, you can use it to get the file from the network.
    //
    //
    // The <destination directory> is either an absolute path or a path which is relative to the directory specific by the WORKDIR instructions.
    // Examples:
    //
    // ADD config.json projectRoot/ -> will add the config.json file to <WORKDIR>/projectRoot/
    //
    // ADD config.json /absoluteDirectory/ -> will add the config.json file to the /absoluteDirectory/
    //
}
