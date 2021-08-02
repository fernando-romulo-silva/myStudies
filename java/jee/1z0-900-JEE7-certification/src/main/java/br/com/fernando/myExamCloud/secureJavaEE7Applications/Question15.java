package br.com.fernando.myExamCloud.secureJavaEE7Applications;

public class Question15 {

    // Which of the following authentication mechanisms are more secure than HTTP Basic authentication mechanism?
    // [ Choose two ]
    //
    //
    // Choice A
    // Form Based authentication
    //
    // Choice B
    // HTTP Digest authentication
    //
    // Choice C
    // HTTPS Client authentication
    //
    // Choice D
    // HTTP Base64 authentication
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // Choice B and C are correct answers.
    //
    // In HTTP Basic authentication, the user password is sent as base64 encoded clear text and the target server is not authenticated.
    // So anyone can decode the password as it is not encrypted rather just encoded.
    //
    // HTTP Digest authentication is very similar to HTTP Basic authentication where a user is authenticated with username and password.
    // The only difference is that the password is sent in encrypted format whereas in Basic authentication,
    // it is sent in base64 encoded format. So it is a stronger form of authentication.
    //
    // HTTP Client authentication is a stronger method of authentication than Basic, Form and Digest authentication.
    // It uses HTTP over Secure Sockets Layer (SSL), in which the server and, optionally, the client authenticate one another with Public Key Certificates.
    // SSL provides data encryption, server authentication, message integrity, and optional client authentication for a TCP/IP connection.
    //
    // HTTP Form Based authentication is exactly similar to HTTP Basic authentication except for the feature that login screen can be customized using custom pages.
}
