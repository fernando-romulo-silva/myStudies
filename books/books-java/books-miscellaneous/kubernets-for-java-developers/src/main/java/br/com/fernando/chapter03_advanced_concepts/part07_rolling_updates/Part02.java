package br.com.fernando.chapter03_advanced_concepts.part07_rolling_updates;

public class Part02 {

    // Ready Pods in Kubernetes
    //
    // Deployments implement the concept of ready pods to aide rolling updates.
    //
    // Readiness probes allow the deployment to gradually update pods while giving you the control to determine when the rolling update can proceed;
    //
    // A pod will be considered ready if its readiness probe is successful and spec.minReadySeconds have passed since the pod was created.
    // The default for these options will result in a pod that is ready as soon as its containers start.
    /**
     * <pre>
     *   readinessProbe:
     *     httpGet:
     *        path: /
     *        port: 8080
     *        initialDelaySeconds: 5
     *        periodSeconds: 5
     *        successThreshold: 1
     * </pre>
     */
    // Here are the detailed information of the fields I have added above.
    //
    // * initialDelaySeconds: Number of seconds after the container has started before readiness probes are initiated.
    //
    // * periodSeconds: How often (in seconds) to perform the probe. Default to 10 seconds. Minimum value is 1.
    //
    // * timeoutSeconds: Number of seconds after which the probe times out. Defaults to 1 second. Minimum value is 1.
    //
    // * successThreshold: Minimum consecutive successes for the probe to be considered successful after having failed.
    // Defaults to 1. Must be 1 for liveness. Minimum value is 1.
    //
    // * failureThreshold: When a Pod starts and the probe fails, Kubernetes will try failureThreshold times before giving up. 
    // Giving up in case of liveness probe means restarting the Pod. 
    // In case of readiness probe the Pod will be marked Unready. Defaults to 3. Minimum value is 1.

}
