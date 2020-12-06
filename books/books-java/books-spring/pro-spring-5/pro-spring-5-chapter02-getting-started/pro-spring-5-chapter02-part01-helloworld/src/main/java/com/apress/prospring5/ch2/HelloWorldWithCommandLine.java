package com.apress.prospring5.ch2;

public class HelloWorldWithCommandLine {

    // A better solution is to externalize the message content and read it in at runtime, perhaps from the command-line arguments
    // shown in the following code snippet:
    public static void main(String... args) {
	if (args.length > 0) {
	    System.out.println(args[0]);
	} else {
	    System.out.println("Hello World!");
	}
    }
}
