package br.com.fernando.chapter03_advanced_concepts.part07_rolling_updates;

public class Part01 {
    // A rolling update is a method of deploying a new version of the application with zero downtime.
    // You achieve this by updating pods one by one instead of updating all the pods at once.
    //
    // The most important option to configure rolling updates is the update strategy:
    //
    // * RollingUpdate: New pods are added gradually, and old pods are terminated gradually
    // * Recreate: All old pods are terminated before any new pods are added
    //
    //
    // In most cases, RollingUpdate is the preferable update strategy for Deployments.
    //
    // When using the RollingUpdate strategy, there are two more options that let you fine-tune the update process:
    //
    // * maxSurge: The number of pods that can be created above the desired amount of pods during an update
    // * maxUnavailable: The number of pods that can be unavailable during the update process
    //
    // The 'maxSurge' is the maximum number of new pods that will be created at a time, and 'maxUnavailable' is the maximum number of old pods that will be deleted at a time.
    //
    // Both maxSurge and maxUnavailable can be specified as either an integer (e.g. 2) or a percentage (e.g. 50%), and they cannot both be zero.
    // When specified as an integer, it represents the actual number of pods.
    // When specifying a percentage, that percentage of the desired number of pods is used, rounded down.
    //
    // It is important to note that when considering the number of pods a Deployment should run during an update,
    // it will be using the number of replicas specified in the updated version of the deployment, not the existing version.
    //
    // Let’s step through the process for updating a Deployment with 3 replicas from “v1” to “v2” using the following update strategy:
    /**
     * <pre>
     * replicas: 3
     * strategy:
     *    type: RollingUpdate
     *    rollingUpdate:
     *       maxSurge: 1
     *       maxUnavailable: 0
     * </pre>
     */
    // This strategy says that we want to add pods one at a time, and that there must always be 3 pods ready in the deployment.
}
