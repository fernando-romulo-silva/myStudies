package org.example.java_aws_lessons.ec2;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Collections;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

public class Ec2Instances {

    public static void main(String[] args) {

        // try (final var client = Ec2Client.builder().build()) {
        // out.println("Condition update successful: " + response);
        // } catch (final Ec2Exception ex) {
        // out.println("Error:" + ex.awsErrorDetails().errorMessage());
        // }
    }

    static void createPairKey() {

        try (final var client = Ec2Client.builder().build()) {

            final var keyPairName = "MyKeyPair";

            final var request = CreateKeyPairRequest.builder()
                    .keyName(keyPairName)
                    .build();

            final var response = client.createKeyPair(request);

            out.println("Condition update successful: " + response);

        } catch (final Ec2Exception ex) {
            out.println("Error:" + ex.awsErrorDetails().errorMessage());
        }
    }

    static void createSecurityGroup() {

        final var secName = "JavaSecGroup";
        final var description = "My SG for Amazon Ec2 instances";

        try (final var client = Ec2Client.builder().build()) {

            final var createSecurityGroupRequest = CreateSecurityGroupRequest.builder()
                    .groupName(secName)
                    .description(description)
                    .build();

            final var response = client.createSecurityGroup(createSecurityGroupRequest);

            final var secGrpID = response.groupId();

            final var sshPermission = IpPermission.builder()
                    .ipProtocol("tcp")
                    .fromPort(22)
                    .toPort(22)
                    .build();

            final var authorizeSecurityGroupIngressRequest = AuthorizeSecurityGroupIngressRequest.builder()
                    .groupId(secGrpID)
                    .ipPermissions(sshPermission)
                    .build();

            client.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);

            out.println("Security Group ID : " + secGrpID);

        } catch (final Ec2Exception ex) {
            out.println("Error:" + ex.awsErrorDetails().errorMessage());
        }
    }

    static void createInBoundRule() {

        final var securityGroupId = "sg-06eac4218d639bbab";

        try (final var client = Ec2Client.builder().build()) {

            final var ipRanges = new ArrayList<IpRange>();

            ipRanges.add(IpRange.builder().cidrIp("0.0.0.0/0").build());

            final var allowAllPermission = IpPermission.builder()
                    .ipProtocol("-1")
                    .ipRanges(ipRanges)
                    .build();

            final var request = AuthorizeSecurityGroupIngressRequest.builder()
                    .groupId(securityGroupId)
                    .ipPermissions(allowAllPermission)
                    .build();

            client.authorizeSecurityGroupIngress(request);

        } catch (final Ec2Exception ex) {
            out.println("Error:" + ex.awsErrorDetails().errorMessage());
        }
    }

    static void createE2Instance() {

        final var amiid = "ami-000aa26b054f3a383";

        final var instanceType = "t2.micro";
        final var keyPairName = "MyKeyPair";
        final var securityGroupId = "sg-06eac4218d639bbab";
        final var instanceName = "MyJavaEC2Server";

        try (final var client = Ec2Client.builder().build()) {
            final var runInstancesRequest = RunInstancesRequest.builder()
                    .imageId(amiid)
                    .instanceType(instanceType)
                    .keyName(keyPairName)
                    .securityGroupIds(securityGroupId)
                    .maxCount(1)
                    .minCount(1)
                    .tagSpecifications(
                            TagSpecification.builder()
                                    .resourceType(ResourceType.INSTANCE)
                                    .tags(Tag.builder()
                                            .key("Name")
                                            .value(instanceName)
                                            .build())
                                    .build())
                    .build();

            final var response = client.runInstances(runInstancesRequest);

            final var instanceId = response.instances().get(0).instanceId();

            out.println("EC2 Instance With Id : " + instanceId + " is launcing");
        } catch (final Ec2Exception ex) {
            out.println("Error:" + ex.awsErrorDetails().errorMessage());
        }
    }

    static void getInstanceIP() {

        final var instanceId = "i-091db440da6ce00b4";

        try (final var client = Ec2Client.builder().build()) {

            final var request = DescribeInstancesRequest.builder()
                    .instanceIds(instanceId)
                    .build();

            final var response = client.describeInstances(request);
            final var instances = response.reservations().get(0).instances();

            if (!instances.isEmpty()) {
                final var publicIp = instances.get(0).publicIpAddress();
                out.println("Public IP Addess of the Instance : " + publicIp);
            } else {
                out.println("Instance not found");
            }

        } catch (final Ec2Exception ex) {
            out.println("Error:" + ex.awsErrorDetails().errorMessage());
        }
    }

    static void listInstances() {
        try (final var client = Ec2Client.builder().build()) {

            final var request = DescribeInstancesRequest.builder().build();

            final var response = client.describeInstances(request);

            for (final var reservation : response.reservations()) {
                final var instances = reservation.instances();

                for (final var instance : instances) {
                    if ("running".equals(instance.state().nameAsString())) {
                        out.println("Instance ID : " + instance.instanceId());
                        out.println("Instance State : " + instance.state().nameAsString());
                        out.println("Public IP : " + instance.publicIpAddress());
                        out.println("Private IP : " + instance.privateIpAddress());
                        out.println("---------------------");
                    }
                }
            }
        } catch (final Ec2Exception ex) {
            out.println("Error:" + ex.awsErrorDetails().errorMessage());
        }
    }

    static void stopInstance() {

        final var instanceId = "i-091db440da6ce00b4";

        try (final var client = Ec2Client.builder().build()) {

            final var request = StopInstancesRequest.builder()
                    .instanceIds(Collections.singletonList(instanceId))
                    .build();

            client.stopInstances(request);

            out.println("Stopping Instance : " + instanceId);

        } catch (final Ec2Exception ex) {
            out.println("Error:" + ex.awsErrorDetails().errorMessage());
        }
    }

    static void terminateInstance() {
        final var instanceId = "i-091db440da6ce00b4";

        try (final var client = Ec2Client.builder().build()) {

            final var request = TerminateInstancesRequest.builder()
                    .instanceIds(instanceId)
                    .build();

            client.terminateInstances(request);

            out.println("Terminating Instance : " + instanceId);

        } catch (final Ec2Exception ex) {
            out.println("Error:" + ex.awsErrorDetails().errorMessage());
        }
    }
}
