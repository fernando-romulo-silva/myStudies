package br.com.fernando.chapter03_advanced_concepts.part09_secrets;

public class Part01 {

    // Secrets
    //
    // A Secret is an object that contains a small amount of sensitive data such as a password, a token, or a key.
    // Such information might otherwise be put in a Pod specification or in an image.
    // Users can create secrets and the system also creates some secrets.
    //
    // Creating a Secret manually
    //
    // You can also create a Secret in a file first, in JSON or YAML format, and then create that object.
    // The name of a Secret object must be a valid DNS subdomain name.
    // The Secret contains two maps: data and stringData. The data field is used to store arbitrary data, encoded using base64.
    // The stringData field is provided for convenience, and allows you to provide secret data as unencoded strings.
    //
    // For example, to store two strings in a Secret using the data field, convert the strings to base64 as follows:
    //
    // $ echo -n 'admin' | base64
    //
    // YWRtaW4=
    //
    // Write a Secret that looks like this:
    /**
     * <pre>
     *  apiVersion: v1
     *  kind: Secret
     *  metadata:
     *    name: mysecret
     *  type: Opaque
     *  data:
     *    username: YWRtaW4=
     *    password: MWYyZDFlMmU2N2Rm
     * </pre>
     */

}
