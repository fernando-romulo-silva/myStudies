package com.apress.springbootrecipes.scheduling;

import java.io.IOException;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ThreadingApplication implements AsyncConfigurer {

    public static void main(String[] args) throws IOException {
	SpringApplication.run(ThreadingApplication.class, args);

	System.out.println("Press [ENTER] to quit:");
	System.in.read();
    }

    @Bean
    public TaskExecutor taskExecutor(TaskExecutorBuilder builder) {
	return builder //
			.corePoolSize(4) //
			.maxPoolSize(16) //
			.queueCapacity(125) //
			.threadNamePrefix("sbr-exec-") //
			.build();
    }

// OR
//
//    @Override
//    public Executor getAsyncExecutor() {
//
//	final var executor = new TaskExecutorBuilder().corePoolSize(4) //
//			.maxPoolSize(16) //
//			.queueCapacity(125) //
//			.threadNamePrefix("sbr-exec-") //
//			.build();
//
//	return executor;
//    }
    
    @Bean
    public ApplicationRunner startupRunner(HelloWorld hello) {
	return (args) -> hello.printMessage();
    }
}
