package br.com.fernando.chapter06_running_container_java_applications.part02_container_running_modes;

public class Part03 {

	// Attaching to running containers
	//
	// To retain control over a detached container, use docker attach command. 
	// The syntax for docker attach is quite simple:
	//
	// $ docker attach [OPTIONS] <container ID or name>  
	//
	// Ex:
	//
	// $ docker attach 36fe8acfdf96b5b1c0004b7b4e773c43082c4be5460c42adbd48e47cf477027b
	//
	// It will basically reattach your console to the process running in the container.
	//
	// In other words, it will stream the stdout into your screen and map the stdin to your keyboard, allowing you to enter the commands and see their output.
	//
	// Note that pressing the CTRL + C keyboard sequence while being attached to the container would kill the running process of the container, not detach from the console.
	//
	//
	// If you would like to be able to detach using CTRL + C, you may tell Docker not to send sig-term to the process running in the container 
	// by using the sig-proxy parameter set to false:
	// 
	// $ docker attach --sig-proxy=false 36fe8acfdf96b5b1c0004b7b4e773c43082c4be5460c42adbd48e47cf477027b
}
