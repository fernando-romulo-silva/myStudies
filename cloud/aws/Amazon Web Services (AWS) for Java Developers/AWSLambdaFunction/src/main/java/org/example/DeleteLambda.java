package org.example;

import static java.lang.System.err;
import static java.lang.System.out;

import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.DeleteFunctionRequest;
import software.amazon.awssdk.services.lambda.model.LambdaException;

public class DeleteLambda {

    public static void main(String[] args) {

        final var functionName = "JavaLambda";

        try (final var lambdaClient = LambdaClient.builder().build()) {

            final var deleteFunctionRequest = DeleteFunctionRequest.builder()
                    .functionName(functionName)
                    .build();

            lambdaClient.deleteFunction(deleteFunctionRequest);

            out.println("Lambda Function Deleted");
        } catch (LambdaException e) {

            err.println("Error : " + e.getLocalizedMessage());
        }

    }
}
