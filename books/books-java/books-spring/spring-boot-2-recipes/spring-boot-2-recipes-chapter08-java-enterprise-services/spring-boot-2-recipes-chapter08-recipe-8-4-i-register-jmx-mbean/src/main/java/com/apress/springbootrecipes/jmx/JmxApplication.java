package com.apress.springbootrecipes.jmx;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JmxApplication {

    public static void main(String[] args) throws IOException {
	SpringApplication.run(JmxApplication.class, args);

	System.out.print("Press [ENTER] to quit:");
	System.in.read();
    }
}
