package org.example.java_aws_lessons.ses;

import static java.lang.System.out;

import java.util.HashMap;

import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.CreateTemplateRequest;
import software.amazon.awssdk.services.ses.model.DeleteTemplateRequest;
import software.amazon.awssdk.services.ses.model.DeleteVerifiedEmailAddressRequest;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.GetTemplateRequest;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;
import software.amazon.awssdk.services.ses.model.Template;
import software.amazon.awssdk.services.ses.model.VerifyEmailAddressRequest;

public class Identity {

    private static final String EMAIL = "mytestmail@gmail.com";

    public static void main(final String[] args) {
        deleteEmailAddress();
    }

    static void verifyEmail() {

        final var emailAddress = EMAIL;

        try (final var client = SesClient.builder().build()) {

            final var verifyEmailAddressRequest = VerifyEmailAddressRequest.builder()
                    .emailAddress(emailAddress)
                    .build();

            final var verifyEmailAddressResponse = client.verifyEmailAddress(verifyEmailAddressRequest);

            out.println("Email Address Verification Request Sent : Request ID : "
                    + verifyEmailAddressResponse.responseMetadata().requestId());

            // a17c2c66-c6df-4856-9ecd-16f960ffeaf8

        } catch (final SesException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void listIdentity() {

        try (final var client = SesClient.builder().build()) {

            final var response = client.listVerifiedEmailAddresses();

            response.verifiedEmailAddresses().forEach(emailAddress -> out.println("Email Address : " + emailAddress));

        } catch (final SesException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void createCustomTemplate() {

        final var templateName = "MyCustomTemplate";
        final var templateSubject = "Welcome to the course";

        final var templateHtmlContent = "<html><body><h1>Hello {{name}},</h1><p>Thanks for buying the course, Please give rating and review</p></body></html>";
        final var templateTextContent = "Thanks for buying the course.";

        final var templateData = new HashMap<String, String>();
        templateData.put("name", "John Doe");

        try (final var client = SesClient.builder().build()) {

            final var request = CreateTemplateRequest.builder()
                    .template(Template.builder()
                            .templateName(templateName)
                            .subjectPart(templateSubject)
                            .htmlPart(templateHtmlContent)
                            .textPart(templateTextContent)
                            .build())
                    .build();

            final var response = client.createTemplate(request);

            out.println("Custom Email Template Created with the name : " + templateName + " response: "
                    + response.responseMetadata());

        } catch (final SesException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void getTemplateInfo() {

        final var templateName = "MyCustomTemplate";

        try (final var client = SesClient.builder().build()) {

            final var request = GetTemplateRequest.builder()
                    .templateName(templateName)
                    .build();

            final var response = client.getTemplate(request);

            final var template = response.template();

            out.println("Template Name : " + template.templateName());
            out.println("Subject : " + template.subjectPart());
            out.println("HTML Content : " + template.htmlPart());
            out.println("Text Content : " + template.textPart());

        } catch (final SesException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void sendEmail() {

        final var senderEmail = EMAIL;
        final var recipientEmail = senderEmail;

        final var emailSubject = "Hello from Amazon SES";

        final var emailHtmlContent = "<b>Please give review and rating for the course</b>";

        final var emailTextContent = "Please give review and rating for the course";

        try (final var client = SesClient.builder().build()) {

            final var request = SendEmailRequest.builder()
                    .source(senderEmail)
                    .destination(Destination.builder()
                            .toAddresses(recipientEmail)
                            .build())
                    .message(Message.builder()
                            .subject(Content.builder()
                                    .data(emailSubject)
                                    .build())
                            .body(Body.builder()
                                    .html(Content.builder()
                                            .data(emailHtmlContent)
                                            .build())
                                    .text(Content.builder()
                                            .data(emailTextContent)
                                            .build())
                                    .build())
                            .build())
                    .build();

            final var response = client.sendEmail(request);

            out.println("Email sent with message ID : " + response.messageId());

        } catch (final SesException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void sendCustomEmail() {

        final var senderEmail = EMAIL;
        final var recipientEmail = senderEmail;

        final var templateName = "MyCustomTemplate";

        try (final var client = SesClient.builder().build()) {

            final var request = SendTemplatedEmailRequest.builder()
                    .source(senderEmail)
                    .destination(Destination.builder()
                            .toAddresses(recipientEmail)
                            .build())
                    .template(templateName)
                    .templateData("{\"name\":\"John Doe\"}")
                    .build();

            final var response = client.sendTemplatedEmail(request);
            out.println("Email sent with message id : " + response.messageId());

        } catch (final SesException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void deleteCustomTemplate() {

        final var templateName = "MyCustomTemplate";

        try (final var client = SesClient.builder().build()) {

            final var request = DeleteTemplateRequest.builder()
                    .templateName(templateName)
                    .build();

            final var response = client.deleteTemplate(request);
            out.println("Custom email template deleted." + response);

        } catch (final SesException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

    static void deleteEmailAddress() {

        final var emailAddress = EMAIL;

        try (final var client = SesClient.builder().build()) {

            final var request = DeleteVerifiedEmailAddressRequest.builder()
                    .emailAddress(emailAddress)
                    .build();

            final var response = client.deleteVerifiedEmailAddress(request);

            out.println("Verified Email Address Deleted : " + emailAddress + " " + response);

        } catch (final SesException ex) {
            out.println(ex.awsErrorDetails().errorMessage());
        }
    }

}
