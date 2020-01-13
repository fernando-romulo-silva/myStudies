package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part07_copy;

public class Part01 {

    // The COPY instruction will copy new files or directories from <source path> and add them to the file system of the container at the path <destination path>.
    //
    // It's very similar to the ADD instruction, even the syntax is no different:
    //
    // COPY <source path or URL> <destination path >
    //
    // Of course, as in ADD, you can have multiple source paths. If source or destination paths contain spaces, you will need to wrap them in square brackets:
    //
    // COPY ["<source path or URL>" "<destination path>"]
    //
    // As you can see, the functionality of COPY is almost the same as the ADD instruction, with one difference. 
    // COPY supports only the basic copying of local files into the container. 
    // On the other hand, ADD gives some more features, such as archive extraction, downloading files through URL, and so on. 
    // Docker's best practices say that you should prefer COPY if you do not need those additional features of ADD. 
    // The Dockerfile will be cleaner and easier to understand thanks to the transparency of the COPY command.
}
