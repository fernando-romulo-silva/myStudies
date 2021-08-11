package com.apress.cems.mongo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.apress.cems.mongo.repos")
@ComponentScan(basePackages = { "com.apress.cems.mongo.services" })
@PropertySource("classpath:mongo.properties")
public class AppConfig {

    @Value("${db.name}")
    private String dbName;

    @Value("${db.host}")
    private String host;

    @Value("${db.port}")
    private Integer port;

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String password;

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {

// db.name=test
// db.host=127.0.0.1
// db.port=27017
// db.user=user
// db.password=user

	// docker run --name mongodb -e MONGO_INITDB_DATABASE=test -e MONGO_INITDB_ROOT_USERNAME=user -e MONGO_INITDB_ROOT_PASSWORD=user -d -p 27017:27017 mongo:latest
	// #spring.data.mongodb.uri: mongodb://<user>:<passwd>@<host>:<port>/<dbname>
	final var url = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + dbName + "?authSource=admin";

	return new SimpleMongoClientDatabaseFactory(url);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
	return new MongoTemplate(mongoDatabaseFactory());
    }
}