package org.example.java_aws_lessons.dynamodb;

import static java.lang.System.out;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.TimeToLiveSpecification;
import software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveRequest;

// TTL : Time to live
public class DynamoDbTTL {

    public static void main(String[] args) {
        updateTimeToLive();

        // try (final var client = DynamoDbClient.builder().build()) {
        // } catch (final DynamoDbException ex) {
        // System.out.println(ex.awsErrorDetails().errorMessage());
        // }
    }

    static void updateTimeToLive() {
        try (final var client = DynamoDbClient.builder().build()) {

            final var request = UpdateTimeToLiveRequest.builder()
                    .tableName("UserTable")
                    .timeToLiveSpecification(TimeToLiveSpecification.builder()
                            .enabled(true)
                            .attributeName("July 10, 2024").build())
                    .build();

            final var response = client.updateTimeToLive(request);

            out.println("Update time to live successful " + response);

        } catch (final DynamoDbException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }
}
