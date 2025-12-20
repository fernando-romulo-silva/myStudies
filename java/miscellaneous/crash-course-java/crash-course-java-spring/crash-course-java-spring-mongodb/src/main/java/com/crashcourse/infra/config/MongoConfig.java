package com.crashcourse.infra.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private final MongoProperties mongoProperties;

    MongoConfig(final MongoProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
    }

    // @Bean
    // ReactiveMongoTransactionManager
    // reactiveTransactionManager(@Qualifier("reactiveMongoDbFactory") final
    // ReactiveMongoDatabaseFactory dbFactory) {
    // return new ReactiveMongoTransactionManager(dbFactory);
    // }

    @Bean
    MongoTransactionManager transactionManager(@Qualifier("mongoDbFactory") final MongoDatabaseFactory mongoDbFactory) {
        return new MongoTransactionManager(mongoDbFactory);
    }

    @Override
    protected String getDatabaseName() {
        return "hoteldb";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(mongoProperties.getUri());
    }
}
