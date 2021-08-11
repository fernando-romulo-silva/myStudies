/*
Freeware License, some rights reserved

Copyright (c) 2019 Iuliana Cosmina

Permission is hereby granted, free of charge, to anyone obtaining a copy 
of this software and associated documentation files (the "Software"), 
to work with the Software within the limits of freeware distribution and fair use. 
This includes the rights to use, copy, and modify the Software for personal use. 
Users are also allowed and encouraged to submit corrections and modifications 
to the Software for the benefit of other users.

It is not allowed to reuse,  modify, or redistribute the Software for 
commercial use in any way, or for a user's educational materials such as books 
or blog articles without prior permission from the copyright holder. 

The above copyright notice and this permission notice need to be included 
in all copies or substantial portions of the software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS OR APRESS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.apress.cems.mongo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Configuration
@EnableTransactionManagement
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
    PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
	return new MongoTransactionManager(dbFactory);
    }

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

    protected String getDatabaseName() {
	return dbName;
    }

}