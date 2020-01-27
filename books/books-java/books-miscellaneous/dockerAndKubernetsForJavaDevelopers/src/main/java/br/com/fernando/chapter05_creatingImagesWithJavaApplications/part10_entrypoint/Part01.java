package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part10_entrypoint;

public class Part01 {

	// The ENTRYPOINT instruction allows you to configure a container that will run as an executable.
	// It's not very clear, at least for the first time.
	// The ENTRYPOINT instruction is related to the CMD instruction. In fact, it can be confusing at the beginning.
	//
	// The reason for that is simple: CMD was developed first, then ENTRYPOINT was developed for more customization, and some functionality overlaps between those two instructions.
	//
	// The ENTRYPOINT specifies a command that will always be executed when the container starts.
	// The CMD, on the other hand, specifies the arguments that will be fed to the ENTRYPOINT.
	// Docker has a default ENTRYPOINT which is /bin/sh -c but does not have a default CMD.
	// For example, consider this Docker command:
	//
	// $ docker run ubuntu "echo" "hello world"
	//
	// In this case, the image will be the latest ubuntu, the ENTRYPOINT will be the default /bin/sh -c, and the command passed to the ENTRYPOINT will be: echo "hello world"
	//
	//
	// The syntax for the ENTRYPOINT instruction can have two forms, similar to CMD:
	//
	// 1º ENTRYPOINT ["executable", "parameter1", "parameter2"] is the exec form, preferred and recommended.
	//
	// If you want shell processing then you need either to use the shell form or execute a shell directly.
	// For example:
	//
	// ENTRYPOINT [ "sh", "-c", "echo $HOSTNAME" ]
	//
	//
	// 2º ENTRYPOINT command parameter1 parameter2 is a a shell form.
	// Normal shell processing will occur. This form will also ignore any CMD or docker run command line arguments.
	// Also, your command will not be PID 1, because it will be executed by the shell.
	// As a result, if you then run docker stop <container>, the container will not exit cleanly, and the stop command will be forced to send a SIGKILL after the timeout.
	//
	//  Overriding the ENTRYPOINT in the Dockerfile allows you to have a different command processing your arguments when the container is run. 
	// If you need to change the default shell in your image, you can do this by changing an ENTRYPOINT:
	//
	// FROM ubuntu
	// ENTRYPOINT ["/bin/bash"]
	
}
