package org.example;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.CreateFunctionRequest;
import software.amazon.awssdk.services.lambda.model.FunctionCode;
import software.amazon.awssdk.services.lambda.model.Runtime;
import software.amazon.awssdk.core.exception.SdkClientException;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UploadLambda {

    public static void main(String[] args) {
        final var functionName = "NewLambdaFunc";
        final var handler = "org.example.LambdaHandler::handleRequest";
        final var roleName = "arn:aws:iam::121456538223:role/LambdaS3Access";

        final var codePath = "/home/fernando/Development/workspaces/VS Code Workspace/personal/myStudies/cloud/aws/Amazon Web Services (AWS) for Java Developers/AWSLambdaFunction/target/AWSLambdaFunction-1.0-SNAPSHOT-jar-with-dependencies.jar";

        try (final var lambdaClient = LambdaClient.builder().build()) {

            final var functionCode = Files.readAllBytes(Paths.get(codePath));

            final var code = FunctionCode.builder()
                    .zipFile(SdkBytes.fromByteArray(functionCode))
                    .build();

            final var createFunctionRequest = CreateFunctionRequest.builder()
                    .functionName(functionName)
                    .handler(handler)
                    .role(roleName)
                    .runtime(Runtime.JAVA21)
                    .code(code)
                    .build();

            lambdaClient.createFunction(createFunctionRequest);

            out.println("Lambda Function is created");

        } catch (final IOException e) {
            err.println("Error reaing lambda function code");
        } catch (final SdkClientException e) {
            err.println("Error reaing lambda function : " + e.getMessage());
        }
    }
}