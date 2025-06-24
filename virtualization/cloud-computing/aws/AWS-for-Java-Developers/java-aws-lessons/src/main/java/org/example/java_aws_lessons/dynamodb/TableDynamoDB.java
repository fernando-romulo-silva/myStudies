package org.example.java_aws_lessons.dynamodb;

import static java.lang.System.out;

import java.util.Map;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

public class TableDynamoDB {

    public static void main(String[] args) {
        retrieveData();

        // try (final var client = DynamoDbClient.builder().build()) {
        // } catch (final DynamoDbException ex) {
        // System.out.println(ex.awsErrorDetails().errorMessage());
        // }
    }

    static void createTable() {

        try (final var client = DynamoDbClient.builder().build()) {

            final var partitionKey = AttributeDefinition.builder()
                    .attributeName("UserID")
                    .attributeType(ScalarAttributeType.S)
                    .build();

            final var keySchemaElement = KeySchemaElement.builder()
                    .attributeName("UserID")
                    .keyType(KeyType.HASH)
                    .build();

            final var provisinedThroughput = ProvisionedThroughput.builder()
                    .readCapacityUnits(5L)
                    .writeCapacityUnits(5L)
                    .build();

            final var createTableRequest = CreateTableRequest.builder()
                    .tableName("UserTable")
                    .attributeDefinitions(partitionKey)
                    .keySchema(keySchemaElement)
                    .provisionedThroughput(provisinedThroughput)
                    .build();

            final var createTableResponse = client.createTable(createTableRequest);

            out.println("Table created: " + createTableResponse.tableDescription().tableName());

        } catch (final DynamoDbException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void insertItem() {

        try (final var client = DynamoDbClient.builder().build()) {

            final var item = Map.<String, AttributeValue>of(
                    "UserID", AttributeValue.builder().s("user124").build(),
                    "FirstName", AttributeValue.builder().s("Parwiz").build(),
                    "LastName", AttributeValue.builder().s("Forogh").build(),
                    "Email", AttributeValue.builder().s("par@gmail.com").build() //
            );

            final var putItemRequest = PutItemRequest.builder()
                    .tableName("UserTable")
                    .item(item)
                    .build();

            final var response = client.putItem(putItemRequest);

            final var requestId = response.responseMetadata().requestId();

            out.println("User Id" + requestId);

        } catch (final DynamoDbException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void retrieveData() {

        try (final var client = DynamoDbClient.builder().build()) {

            final var request = QueryRequest.builder()
                    .tableName("UserTable")
                    .keyConditionExpression("UserID = :userId")
                    .filterExpression("Age > :ageLimit")
                    .expressionAttributeValues(Map.of(
                            ":userId", AttributeValue.builder().s("user123").build(),
                            ":ageLimit", AttributeValue.builder().n("25").build()))
                    .build();

            final var response = client.query(request);

            out.println("Query result: ");
            response.items().forEach(out::println);

        } catch (final DynamoDbException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void getData() {

        try (final var client = DynamoDbClient.builder().build()) {

            final var request = GetItemRequest.builder()
                    .tableName("UserTable")
                    .key(Map.of("UserID", AttributeValue.builder().s("user123").build()))
                    .build();

            final var response = client.getItem(request);

            out.println("Get result: ");
            out.println(response.item());

        } catch (final DynamoDbException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void updateItem() {
        try (final var client = DynamoDbClient.builder().build()) {

            final var request = UpdateItemRequest.builder()
                    .key(Map.of("UserID", AttributeValue.builder().s("user123").build()))
                    .updateExpression("SET Age = :newAge")
                    .expressionAttributeValues(Map.of(":newAge", AttributeValue.builder().n("45").build()))
                    .build();

            final var response = client.updateItem(request);
            out.println("Item Updated: " + response);

        } catch (final DynamoDbException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void deleteItem() {
        try (final var client = DynamoDbClient.builder().build()) {

            final var request = DeleteItemRequest.builder()
                    .tableName("UserTable")
                    .key(Map.of("UserID", AttributeValue.builder().s("user123").build()))
                    .build();

            final var response = client.deleteItem(request);

            out.println("Item Deleted: " + response);

        } catch (final DynamoDbException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

}
