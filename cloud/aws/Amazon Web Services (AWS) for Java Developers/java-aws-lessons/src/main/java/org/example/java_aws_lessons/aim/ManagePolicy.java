package org.example.java_aws_lessons.aim;

import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.AttachUserPolicyRequest;

import software.amazon.awssdk.services.iam.model.CreatePolicyRequest;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.ListPoliciesRequest;

public class ManagePolicy {

    public static void main(String[] args) {
        attachPolicy();
    }

    public static void createPolicy() {

        final var customPolicy = """
                {
                    "Version": "2012-10-17",
                    "Statement": [
                        {
                            "Effect": "Allow",
                            "Action": [
                                "s3:Get*",
                                "s3:List*",
                                "s3:Describe*",
                                "s3-object-lambda:Get*",
                                "s3-object-lambda:List*"
                            ],
                            "Resource": "*"
                        }
                    ]
                }
                """;

        try (final var iam = IamClient.builder().build()) {

            final var request = CreatePolicyRequest.builder()
                    .policyName("customPolicy")
                    .policyDocument(customPolicy)
                    .description("My custom description")
                    .build();

            final var response = iam.createPolicy(request);

            System.out.println("Custom Policy is created with ARN: " + response.policy().arn());

        } catch (final IamException ex) {
            System.out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    public static void listPolicies() {

        try (final var iam = IamClient.builder().build()) {

            final var request = ListPoliciesRequest.builder().build();

            final var response = iam.listPolicies(request);

            for (final var policy : response.policies()) {
                System.out.println("Policy name: " + policy.policyName());
            }

        } catch (final IamException ex) {
            System.out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    public static void attachPolicy() {

        try (final var iam = IamClient.builder().build()) {

            final var user = "goiaba";

            final var policyArn = "arn:aws:iam::368719219994:policy/customPolicy";

            final var request = AttachUserPolicyRequest.builder()
                    .userName(user)
                    .policyArn(policyArn)
                    .build();

            final var response = iam.attachUserPolicy(request);

            System.out.println("Policy attached to user: " + response.toString());

        } catch (final IamException ex) {
            System.out.println(ex.awsErrorDetails().errorMessage());
        }
    }
}
