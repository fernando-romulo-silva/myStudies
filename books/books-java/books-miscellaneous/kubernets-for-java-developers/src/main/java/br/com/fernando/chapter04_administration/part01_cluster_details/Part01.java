package br.com.fernando.chapter04_administration.part01_cluster_details;

public class Part01 {

    // Once the cluster is up and running, often you’ll want to get more details about it.
    // You can obtain these details using kubectl. In addition, you can use the Kubernetes dashboard, a general-purpose, web-based UI, to view this information as well.
    // It can be used to manage the cluster and applications running in the cluster.
    //
    // $ minikube dashboard
    //
    //
    // The kubectl cluster-info command displays the addresses of the master and services with the label kubernetes.io/cluster-service=true.
    //
    // $ kubectl cluster-info
    //
    //
    // As the output states, complete details about the cluster can be obtained with the command:
    //
    // $ kubectl cluster-info dump 
    //
    // 
    // The command above provides basic information—name, status, and age—about each node in the cluster:
    // 
    // $ kubectl get nodes

}
