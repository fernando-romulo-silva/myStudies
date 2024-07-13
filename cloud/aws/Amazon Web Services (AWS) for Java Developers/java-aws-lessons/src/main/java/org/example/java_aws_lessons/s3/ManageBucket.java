package org.example.java_aws_lessons.s3;

import static java.lang.System.out;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class ManageBucket {

    public static void main(String[] args) {
        coptyFromToAnotherBuckt();

        // try (final var client = S3Client.builder().build()) {
        // } catch (final S3Exception ex) {
        // System.out.println(ex.awsErrorDetails().errorMessage());
        // }
    }

    static void createBucket() {

        final var bucketName = "fernando-silva-new2-2345";

        try (final var client = S3Client.builder().build()) {

            final var request = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            final var response = client.createBucket(request);

            out.println("Create response: " + response);

        } catch (final S3Exception ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void listingAllBuckets() {

        try (final var client = S3Client.builder().build()) {

            final var response = client.listBuckets();

            for (final var bucket : response.buckets()) {
                out.println("Create response: " + bucket.name());
            }

        } catch (final S3Exception ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void deleteBucket() {

        final var bucketName = "elasticbeanstalk-sa-east-1-368719219994";

        try (final var client = S3Client.builder().build()) {

            // delete objects
            final var listObjectsResponse = client.listObjects(ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .build());

            for (final var s3Object : listObjectsResponse.contents()) {
                final var deleteObjectRequest = DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(s3Object.key())
                        .build();

                client.deleteObject(deleteObjectRequest);
            }

            // delete bucket
            final var request = DeleteBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            final var response = client.deleteBucket(request);

            out.println("Delete response: " + response);

        } catch (final S3Exception ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void upLoadFileToBucket() {

        final var bucketName = "fernando-silva-2345";

        final var objectKey = "file.txt";

        final var localPath = ManageBucket.class.getClassLoader().getResource("file.txt").getPath();

        try (final var client = S3Client.builder().build()) {

            final var request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            final var response = client.putObject(request, Paths.get(localPath));

            out.println("File Uploaded successful. Etag :" + response.eTag());

        } catch (final S3Exception ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void downloadFileFromBucket() {
        final var bucketName = "fernando-silva-2345";

        final var objectKey = "aws.png";

        final var downloadPath = "aws-downloaded.png";

        try (final var client = S3Client.builder().build()) {

            final var request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            final var response = client.getObjectAsBytes(request);

            final var inputStream = response.asInputStream();

            final var localPath = Paths.get(downloadPath);

            Files.copy(inputStream, localPath);

        } catch (final S3Exception ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        } catch (final IOException ex) {
            out.println(ex.getMessage());
        }
    }

    static void listFiles() {

        final var bucketName = "fernando-silva-2345";

        try (final var client = S3Client.builder().build()) {

            final var response = client.listObjects(ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .build());

            for (final var s3Object : response.contents()) {
                out.println(s3Object);
            }

        } catch (final S3Exception ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void getSummary() {
        final var bucketName = "fernando-silva-2345";

        final var objectKey = "aws.png";

        try (final var client = S3Client.builder().build()) {

            final var request = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            final var response = client.headObject(request);

            out.println(response.eTag());
            out.println(response.contentType());

        } catch (final S3Exception ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void coptyFromToAnotherBuckt() {
        final var sourceBucket = "fernando-silva-2345";

        final var objectKey = "aws.png";

        final var destinationBucket = "fernando-silva-2345-new";

        try (final var client = S3Client.builder().build()) {

            final var request = CopyObjectRequest.builder()
                    .sourceBucket(sourceBucket)
                    .sourceKey(objectKey)

                    .destinationBucket(destinationBucket)
                    .destinationKey(objectKey)

                    .build();

            final var response = client.copyObject(request);

            out.println(response);

        } catch (final S3Exception ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }
}
