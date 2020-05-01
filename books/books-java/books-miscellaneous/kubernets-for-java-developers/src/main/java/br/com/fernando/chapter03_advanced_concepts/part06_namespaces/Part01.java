package br.com.fernando.chapter03_advanced_concepts.part06_namespaces;

public class Part01 {

    // All resources in a Kubernetes cluster are created in a default namespace.
    //
    // A Kubernetes namespace allows you to partition created resources into a logically named group.
    //
    // * A unique scope for resources to avoid name collisions
    // * Policies to ensure appropriate authority is granted to trusted users
    // * Ability to specify constraints for resource consumption
    //
    //
    // This list of namespaces can be obtained using the following command:
    //
    // $ kubectl get namespace
    //
    // Use-cases covered
    //
    // Roles and Responsibilities in an enterprise for namespaces
    // Partitioning landscapes: dev vs. test vs. prod
    // Customer partitioning for non-multi-tenant scenarios
    // When not to use namespaces
    //
    // In Kubernetes you run your applications in a namespace; inside the same namespace you can discover the other applications by service name. The isolation namespaces provide allow you to reuse the same service name in different namespaces, resolving to the applications running in those namespaces. This allows you to create your different “environments” in the same cluster if you wish to do so. For development, test, acceptance and production you would create 4 separate namespaces.
    //
    //
    // Understanding namespaces and DNS
    //
    // When you create a Service, it creates a corresponding DNS entry.
    // This entry is of the form <service-name>.<namespace-name>.svc.cluster.local, which means that if a container just uses <service-name>
    // it will resolve to the service which is local to a namespace.
    // This is useful for using the same configuration across multiple namespaces such as Development, Staging and Production.
    // If you want to reach across namespaces, you need to use the fully qualified domain name (FQDN).
    //
    //
    // Each namespace can be assigned a resource quota. As mentioned, by default, a pod will run with unbounded CPU and memory requests/limits.
    //
    // Specifying a quota allows you to restrict how much of the cluster resources can be consumed across all pods in a namespace.

    /**
     * <pre>
     *  apiVersion: v1
     *  kind: ResourceQuota
     *  metadata:
     *    name: quota
     *  spec:
     *    hard:
     *      cpu: "20" # Total requested CPU usage (number of cores)
     *      memory: 10Gi # Total requested memory usage
     *      pods: "10" # Total number of active pods where the phase is pending or active
     *      replicationcontrollers: "20" # Total number of replication controllers
     *      services: "5" # Total number of services
     * </pre>
     */

    // You can view the allocated quota using the command:
    //
    // $ kubectl describe quota
    
}
