package org.example.java_aws_lessons.ec2;

import static java.lang.System.out;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

public class Ec2Storage {

    public static void main(String[] args) {
        // try (final var client = Ec2Client.builder().build()) {
        // out.println("Condition update successful: " + response);
        // } catch (final Ec2Exception ex) {
        // out.println("Error:" + ex.awsErrorDetails().errorMessage());
        // }
    }

    static void createEbsVolume() {

        int volumeSize = 3;
        String availabilityZone = "sa-east-1a";

        try (final var client = Ec2Client.builder().build()) {

            final var request = CreateVolumeRequest.builder()
                    .availabilityZone(availabilityZone)
                    .size(volumeSize)
                    .build();

            final var response = client.createVolume(request);

            final var volumeID = response.volumeId();

            out.println("Created EBS Volume with ID : " + volumeID);

        } catch (final Ec2Exception ex) {
            out.println("Error:" + ex.awsErrorDetails().errorMessage());
        }
    }

    static void deleteEbsVolume() {

        final var volumeId = "vol-01967d435ec5c8034";

        try (final var client = Ec2Client.builder().build()) {
            final var deleteVolumeRequest = DeleteVolumeRequest.builder()
                    .volumeId(volumeId)
                    .build();

            final var deleteVolumeResponse = client.deleteVolume(deleteVolumeRequest);

            out.println("Deleting EBS Volume : " + volumeId + " : " + deleteVolumeResponse.toString());

        } catch (final Ec2Exception ex) {
            out.println("Error:" + ex.awsErrorDetails().errorMessage());
        }
    }

    static void attachEbsVolume() {

        final var instanceID = "i-0b5f650803ce63f7d";
        final var volumeId = "vol-01967d435ec5c8034";

        try (final var client = Ec2Client.builder().build()) {
            final var attachVolumeRequest = AttachVolumeRequest.builder()
                    .instanceId(instanceID)
                    .volumeId(volumeId)
                    .device("/dev/xvdf")
                    .build();

            final var attachVolumeResponse = client.attachVolume(attachVolumeRequest);

            final var attachment = VolumeAttachment.builder()
                    .attachTime(attachVolumeResponse.attachTime())
                    .device(attachVolumeResponse.device())
                    .instanceId(attachVolumeResponse.instanceId())
                    .state(VolumeAttachmentState.fromValue(attachVolumeResponse.stateAsString()))
                    .volumeId(attachVolumeResponse.volumeId())
                    .build();

            out.println("EBS Volume " + volumeId + " attched to instance " + instanceID + "as device "
                    + attachment.device() + " with state " + attachment.state());

        } catch (final Ec2Exception ex) {
            out.println("Error:" + ex.awsErrorDetails().errorMessage());
        }
    }

}
