package br.com.fernando.chapter07_introduction_to_kubernets.part02_basicConcepts;

public class Part06_Services {

    // Services
    //
    // Kubernetes services group one or more Pods into an internal or external process that needs to be long-running and externally accessible
    //
    // We use label selectors to select Pods with particular labels and apply services or ReplicaSets to them.
    // Other applications can find our service through Kubernetes service discovery.
    //
    // In Kubernetes, cluster Pods can communicate with other Pods, regardless of which host they land on. This is possible because of the services.
    // Each service is given its own IP address and port which remains constant for the lifetime of the service.
    // Services have an integrated load-balancer that will distribute network traffic to all Pods.
    //
    // Let's summarize the Kubernetes service features:
    //
    // * Services are persistent and permanent
    // * They provide discovery
    // * They offer load balancing
    // * They expose a stable network IP address
    // * They find Pods to group by usage of labels

}
