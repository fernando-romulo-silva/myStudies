package org.example.java_aws_lessons.dynamodb;

import static java.lang.System.out;

import java.util.List;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

public class BatchOperations {

    public static void main(String[] args) {
        conditionalWrite();
    }

    static void writeItems() {

        final var item1 = Map.<String, AttributeValue>of(
                "UserID", AttributeValue.builder().s("user666").build(),
                "FirstName", AttributeValue.builder().s("Jana").build(),
                "LastName", AttributeValue.builder().s("Smith").build(),
                "Email", AttributeValue.builder().s("jana@gmail.com").build() //
        );

        final var item2 = Map.<String, AttributeValue>of(
                "UserID", AttributeValue.builder().s("user777").build(),
                "FirstName", AttributeValue.builder().s("Hope").build(),
                "LastName", AttributeValue.builder().s("Fasin").build(),
                "Email", AttributeValue.builder().s("hope@gmail.com").build() //
        );

        try (final var client = DynamoDbClient.builder().build()) {

            final var writeRequests = List.of(
                    WriteRequest.builder().putRequest(PutRequest.builder().item(item1).build()).build(),
                    WriteRequest.builder().putRequest(PutRequest.builder().item(item2).build()).build());

            final var batchRequest = BatchWriteItemRequest.builder()
                    .requestItems(Map.of("UserTable", writeRequests))
                    .build();

            final var response = client.batchWriteItem(batchRequest);

            out.println("Batch successful: " + response);

        } catch (final DynamoDbException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void conditionalWrite() {
        final var conditionsExpression = "Age < :maxAge";

        final var expressionAttributeValues = Map.of(
                ":maxAge", AttributeValue.builder().n("42").build(),
                ":newAge", AttributeValue.builder().n("50").build());

        try (final var client = DynamoDbClient.builder().build()) {

            final var updateItemRequest = UpdateItemRequest.builder()
                    .tableName("UserTable")
                    .key(Map.of("UserID", AttributeValue.builder().s("user123").build()))
                    .updateExpression("SET Age = :newAge")
                    .expressionAttributeValues(expressionAttributeValues)
                    .conditionExpression(conditionsExpression)
                    .build();

            final var response = client.updateItem(updateItemRequest);

            out.println("Condition update successful: " + response);

        } catch (final DynamoDbException ex) {
            out.println("Error:" + ex.awsErrorDetails().errorMessage());
        }

    }
}
