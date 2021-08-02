package br.com.fernando.chapter04_administration.part03_debugging;

public class Part01 {

    // The "kubectl get" command is used to display basic information about one or more resources.
    //
    // $ kubectl get pod // pods
    //
    // $ kubectl get rs // replicasets
    //
    // $ kubectl get svc // services
    //
    // To target only pods in a specific namespace, use the namespace flag:
    //
    // $ kubectl get deploy --namespace my-namespace // deployments
    //
    // 
    // kubectl --help shows the complete list of resource names that can be used with this command.
    //
    // To display only the name for each resource, use the -o name option. 
    // 
    // $ kubectl get pods -o name --namespace my-namespace
    //
    //
    // To see a complete JSON or YAML representation of the resource, use the -o json and -o yaml options, respectively:
    //
    // $ kubectl get pods -o yaml --namespace my-namespace
    //
    //
    // The "kubectl describe" command can be used to get more details about a specific resource or a group of resources: 
    //
    // $ kubectl describe pods wildfly-65cbbddcd7-68q4r --namespace my-namespace
    //
    // You can use -w to watch for state changes of a resource. 
    // This is particularly useful when you’re creating pods using replication controllers. 
    // It allows you to see the pod going through different stages.
    //
    // You can use the kubectl get events command to see all events in the cluster. 
    // These events provide a high-level view of what is happening in the cluster.
}
