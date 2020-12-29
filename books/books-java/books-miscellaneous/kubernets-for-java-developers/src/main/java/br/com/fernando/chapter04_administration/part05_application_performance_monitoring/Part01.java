package br.com.fernando.chapter04_administration.part05_application_performance_monitoring;

public class Part01 {

    // You can monitor the performance of an application in a Kubernetes cluster at multiple levels: 
    // whole cluster, services, pods, and containers.
    //
    // The key components are:
    //
    // * metrics-server (minikube addons enable metrics-server)
    //
    // It's a cluster-wide aggregator of monitoring and event data. 
    // It supports Kubernetes natively and works on all Kubernetes setups.
    // 
    // $ kubectl top nodes
    // $ kubectl top pods
    //
    // 
    // * Grafana
    //
    // Grafana is an open source metric analytics and visualization suite. 
    // It is most commonly used for visualizing time series data for infrastructure and application analytics. 
    // It is available out of the box in a Kubernetes cluster. 
    //
    // The default dashboard for Kubernetes contains an example dashboard that monitors resource usage of the cluster and the pods within it. 
    // This dashboard can easily be customized and expanded.
    
}
