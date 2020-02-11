package br.com.fernando.chapter06_running_container_java_applications.part02_container_running_modes;

public class Part01 {

	// Foreground
	//
	// In the foreground mode, the console you are using to execute docker run will be attached to standard input, output, and error streams.
	// This is the default;  Docker will attach STDIN, STDOUT and STDERR streams to your shell console.
	//
	//
	// The useful docker run options are the -i or --interactive (for keeping STDIN stream open, even if not attached) and -t or -tty (for attaching a pseudo-tty) switches.
	// Commonly used together as -it which you will need to use to allocate a pseudo-tty console for the process running in the container.
	//
	// docker run -it -p 8080:8080 jboss/wildfly
	//
}
