package br.com.fernando.chapter07_introduction_to_kubernets.part03_dashBoardMenu;

public class Part03_Discovery_And_LoadBalancing {

    // -------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Discovery And Load Balancing
    //
    // * Ingresses: By default, Kubernetes provides isolation between pods and the outside world.
    // If you want to communicate with a service running in a pod, you have to open up a channel for communication.
    // This is referred to as ingress. There are multiple ways to add ingress to your cluster.
    // The most common ways are by adding either an Ingress controller, or a LoadBalancer.
    //
    //
    // * Services: A kubernetes service is an abstraction which defines a logical set of Pods and a policy by which to acess them (clusterIP, NotePort, LoadBalancer)
    // Each service is given its own IP address and port which remains constant for the lifetime of the service.
    // Services have an integrated load-balancer that will distribute network traffic to all Pods.
    // Services are mapped to the underlying workload’s pods using a selector/label approach (view the code samples).

}
