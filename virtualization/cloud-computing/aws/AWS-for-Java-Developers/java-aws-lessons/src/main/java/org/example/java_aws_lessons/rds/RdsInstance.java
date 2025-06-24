package org.example.java_aws_lessons.rds;

import static java.lang.System.out;

import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceRequest;
import software.amazon.awssdk.services.rds.model.DeleteDbInstanceRequest;
import software.amazon.awssdk.services.rds.model.DescribeDbInstancesRequest;
import software.amazon.awssdk.services.rds.model.RdsException;

public class RdsInstance {

    public static void main(String[] args) {

        deleteInstance();
        // try (final var client = RdsClient.builder().build()) {
        // } catch (final RdsException ex) {
        // System.out.println(ex.awsErrorDetails().errorMessage());
        // }
    }

    static void createInstance() {

        final var dbName = "myjavadb";
        final var instanceIdentifier = "myjavadb";
        final var masterUsername = "admin";
        final var masterPassword = "password";
        final var instanceClass = "db.t3.micro";
        final var port = 3306;
        final var allocatedStorage = "5";

        try (final var client = RdsClient.builder().build()) {

            final var request = CreateDbInstanceRequest.builder()
                    .dbName(dbName)
                    .dbInstanceIdentifier(instanceIdentifier)
                    .dbInstanceClass(instanceClass)
                    .masterUsername(masterUsername)
                    .masterUserPassword(masterPassword)
                    .allocatedStorage(Integer.parseInt(allocatedStorage))
                    .engine("mysql")
                    .port(port)
                    .build();

            final var response = client.createDBInstance(request);

            out.println("DB instance created: " + response.dbInstance().dbInstanceArn());

        } catch (final RdsException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void describeInstance() {

        final var instanceIdentifier = "myjavadb";

        try (final var client = RdsClient.builder().build()) {

            final var request = DescribeDbInstancesRequest.builder()
                    .dbInstanceIdentifier(instanceIdentifier)
                    .build();

            final var response = client.describeDBInstances(request);

            for (final var dbInstance : response.dbInstances()) {
                out.println("Db Instance Identifier: " + dbInstance.dbInstanceIdentifier());
                out.println("Db Instance Class: " + dbInstance.dbInstanceClass());
                out.println("Db Instance Engine: " + dbInstance.engine());
            }

        } catch (final RdsException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void deleteInstance() {

        final var instanceIdentifier = "myjavadb";

        try (final var client = RdsClient.builder().build()) {

            final var request = DeleteDbInstanceRequest.builder()
                    .dbInstanceIdentifier(instanceIdentifier)
                    .skipFinalSnapshot(true)
                    .build();

            final var response = client.deleteDBInstance(request);

            out.println("Delete successful: " + response);

        } catch (final RdsException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }
}
