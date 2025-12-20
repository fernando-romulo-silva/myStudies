package org.crashcourse.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;

@Configuration
public class GraphQlConfig {
    
    GraphQLScalarType localDateType() {
        return GraphQLScalarType.newScalar()
                .name("LocalDate")
                .description("LocalDate type")
                .coercing(new LocalDateScalar())
                .build();
    }

    GraphQLScalarType localDateTimeType() {
        return GraphQLScalarType.newScalar()
                .name("LocalDateTime")
                .description("LocalDateTime type")
                .coercing(new LocalDateTimeScalar())
                .build();
    }
    
    GraphQLScalarType voidType() {
	
	return GraphQLScalarType.newScalar()
	      .name("Void")
	      .description("A custom scalar that represents the null value")
	      .coercing(new Coercing<>() {

	          @Override
	          public Object serialize(Object dataFetcherResult) {
	              return null;
	          }

	          @Override
	          public Object parseValue(Object input) {
	              return null;
	          }

	          @Override
	          public Object parseLiteral(Object input) {
	              return null;
	          }
	      }).build();
    }

    @Bean
    RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
        			.scalar(voidType())
        			.scalar(localDateTimeType())
        			.scalar(localDateType());
    }
    
}
