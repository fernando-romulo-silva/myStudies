package org.example.java_aws_lessons.aim;

import static java.lang.System.out;

import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.CreateUserRequest;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.ListUsersRequest;
import software.amazon.awssdk.services.iam.model.UpdateUserRequest;

public class ManageIAMUser {

    public static void main(String[] args) {
        updateUser();
    }

    static void listUsers() {
        try (final var iam = IamClient.builder().build()) {

            final var request = ListUsersRequest.builder().build();

            final var response = iam.listUsers(request);

            final var users = response.users();

            for (final var user : users) {
                out.println(user.userName());
            }

        } catch (final IamException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void createUser() {
        try (final var iam = IamClient.builder().build()) {

            final var request = CreateUserRequest.builder()
                    .userName("javauser2")
                    .build();

            final var response = iam.createUser(request);

            out.println(response);

        } catch (final IamException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void updateUser() {
        try (final var iam = IamClient.builder().build()) {

            final var request = UpdateUserRequest.builder()
                    .userName("javauser2")
                    .newUserName("goiaba")
                    .build();

            final var response = iam.updateUser(request);

            out.println(response);

        } catch (final IamException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

}
