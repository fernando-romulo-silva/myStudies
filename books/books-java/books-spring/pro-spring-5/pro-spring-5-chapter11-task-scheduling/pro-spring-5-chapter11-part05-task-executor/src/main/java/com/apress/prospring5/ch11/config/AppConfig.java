package com.apress.prospring5.ch11.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by iuliana.cosmina on 6/5/17.
 */
@Configuration
@EnableAsync
@ComponentScan(basePackages = { "com.apress.prospring5.ch11" })
public class AppConfig {

    @Bean
    TaskExecutor taskExecutor() {
	return new SimpleAsyncTaskExecutor();
    }
}
