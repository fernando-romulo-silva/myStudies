package br.com.fernando.chapter02_deploying_java_app_to_kubernetes.part05_deployments;

public class Part01 {

    // Kubernetes deployments are essentially just a wrapper around ReplicaSets.
    // The ReplicaSet manages the number of running pods, and the Deployment implements features on top of that to allow rolling updates, health checks on pods, and easy roll-back of updates.
    //
    //
    /**
     * <pre>
     *                      Deployment
     *                           |
     *                           |
     *                           V
     *                      Replica Set
     *                           |     
     *                           | 
     *                   -----------------
     *                   |       |       |
     *                   |       |       |
     *                  pod 1   pod 2   pod 3
     * </pre>
     */
    //
    // When using Deployments, you should not directly manage the ReplicaSet that is created by the Deployment.
    // All operations that you would perform on a ReplicaSet should be performed on the Deployment instead, which then manages the process for updating the ReplicaSet.
    //
    // Use Deployment always!
}
