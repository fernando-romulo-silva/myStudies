package br.com.fernando.chapter03_advanced_concepts.part03_horizontal_pod_autoscaling;

public class Part01 {

    // Horizontal pod autoscaling automatically scales the numbers of pods in a replication controller, deployment, or replica based on observed CPU utilization.
    //
    // By default, the autoscaler checks every 30 seconds and adjusts the number of replicas to match the observed average CPU utilization to the target specified by the user.
    //
    // Heapster provides container cluster monitoring and performance analysis.
    // The autoscaler uses Heapster to collect CPU utilization information, so it must be installed in the cluster for autoscaling to work.
    //
    // These status conditions indicate whether or not the HorizontalPodAutoscaler is able to scale, and whether or not it is currently restricted in any way.
    // The conditions appear in the status.conditions field. To see the conditions affecting a HorizontalPodAutoscaler, we can use kubectl describe hpa:
    //
    // $ kubectl describe hpa wildfly-scaler
}
