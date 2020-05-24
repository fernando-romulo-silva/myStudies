package br.com.fernando.chapter03_advanced_concepts.part08_configMap;

public class Part01 {

    // How do you manage your application's configuration?
    // For a Java, Python or Node.js application, where do you store configuration?
    // How do you set connection strings, analytics keys, and service URLs?
    // If you're using Kubernetes, the answer is ConfigMaps.
    //
    // What is a ConfigMap in Kubernetes?
    //
    // A ConfigMap is a dictionary of configuration settings.
    // This dictionary consists of key-value pairs of strings.
    // Kubernetes provides these values to your containers.
    // Like with other dictionaries (maps, hashes, ...) the key lets you get and set the configuration value.
    //
    // Use a ConfigMap to keep your application code separate from your configuration.
    //
    //
    // There are four different ways that you can use a ConfigMap to configure a container inside a Pod:
    //
    // Command line arguments to the entrypoint of a container
    // Environment variables for a container
    // Add a file in read-only volume, for the application to read
    // Write code to run inside the Pod that uses the Kubernetes API to read a ConfigMap

}
