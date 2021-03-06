package com.apress.prospring5.ch11.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by iuliana.cosmina on 6/5/17.
 */
@Configuration
@Import({ DataServiceConfig.class })
@ImportResource("classpath:spring/task-namespace-app-context.xml")
public class AppConfig {
}
