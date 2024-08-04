package org.example.java_aws_lessons.cloudformation;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import software.amazon.awssdk.services.cloudformation.model.Stack;
import software.amazon.awssdk.services.cloudformation.CloudFormationClient;
import software.amazon.awssdk.services.cloudformation.model.CloudFormationException;
import software.amazon.awssdk.services.cloudformation.model.CreateStackRequest;
import software.amazon.awssdk.services.cloudformation.model.DeleteStackRequest;
import software.amazon.awssdk.services.cloudformation.model.DescribeStacksRequest;
import software.amazon.awssdk.services.cloudformation.model.GetTemplateRequest;
import software.amazon.awssdk.services.cloudformation.model.GetTemplateResponse;

public class CloudFormation {

    public static void main(final String[] args) {
        // try (final var client = CloudFormationClient.builder().build()) {
        // } catch (final CloudFormationException ex) {
        // out.println(ex.awsErrorDetails().errorMessage());
        // }

        describeStack();
    }

    static void createCloudFormation() {

        final var templateFilePath = "/home/fernando/Development/workspaces/VS Code Workspace/personal/myStudies/cloud/aws/Amazon Web Services (AWS) for Java Developers/java-aws-lessons/src/main/resources/EC2Example.yml";
        String templateContent = null;

        try {
            templateContent = new String(Files.readAllBytes(Paths.get(templateFilePath)));

        } catch (IOException e) {
            err.println("Error reading file : " + e.getMessage());
            return;
        }

        final var stackName = "MyJavaStack";

        try (final var client = CloudFormationClient.builder().build()) {

            final var createStackRequest = CreateStackRequest.builder()
                    .stackName(stackName)
                    .templateBody(templateContent).build();

            final var createStackResponse = client.createStack(createStackRequest);
            final var stackId = createStackResponse.stackId();

            out.println("Stack creation initiated. Stack ID : " + stackId);

        } catch (final CloudFormationException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void describeStack() {

        final var stackName = "MyJavaStack";

        final var describeStacksRequest = DescribeStacksRequest.builder()
                .stackName(stackName)
                .build();

        try (final var client = CloudFormationClient.builder().build()) {

            final var describeStacksResponse = client.describeStacks(describeStacksRequest);

            for (Stack stack : describeStacksResponse.stacks()) {
                out.println("Stack Name : " + stack.stackName());
                out.println("Stack ID : " + stack.stackId());
                out.println("Stack Status : " + stack.stackStatus());
            }

        } catch (final CloudFormationException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void deleteStack() {

        final var stackName = "MyJavaStack";

        try (final var client = CloudFormationClient.builder().build()) {

            final var deleteStackRequest = DeleteStackRequest.builder()
                    .stackName(stackName)
                    .build();

            client.deleteStack(deleteStackRequest);

            out.println("Stack Deletion initiated : " + stackName);

        } catch (final CloudFormationException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void templateInfo() {

        final var stackName = "MyJavaStack";

        try (final var client = CloudFormationClient.builder().build()) {

            final var getTemplateRequest = GetTemplateRequest.builder()
                    .stackName(stackName)
                    .build();

            final var getTemplateResponse = client.getTemplate(getTemplateRequest);

            final var templateBody = getTemplateResponse.templateBody();

            out.println("Cloudformation Template : \n " + templateBody);

        } catch (final CloudFormationException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }
}
