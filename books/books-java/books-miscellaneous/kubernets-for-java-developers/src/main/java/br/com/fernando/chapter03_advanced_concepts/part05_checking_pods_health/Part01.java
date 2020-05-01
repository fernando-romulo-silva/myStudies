package br.com.fernando.chapter03_advanced_concepts.part05_checking_pods_health;

public class Part01 {

    // Kubernetes provides diagnostic probes to perform a health check on pods.
    // There are two types of probes: liveness and readiness.
    //
    // A liveness probe indicates whether the container is live.
    // If the liveness probe fails, Kubernetes will kill the container and the container will be subjected to its RestartPolicy.
    //
    /**
     * <pre>
     *   livenessProbe:
     *      httpGet:
     *         path: /index.html
     *         port: 8080
     *      initialDelaySeconds: 30
     *      timeoutSeconds: 1
     * </pre>
     */
    //
    //
    // Alternatively, a readiness probe is identified by readinessProbe and indicates whether the container is ready to service requests.
    // If the readiness probe fails, Kubernetes will remove the pod’s IP address from the endpoints of all services that match the pod.
    //
    /**
     * <pre>
     *   readinessProbe:
     *      exec:
     *        command:
     *          - /bin/sh
     *          - -c
     *          - /opt/jboss/wildfly/bin/jboss-cli.sh --connect --commands='ls deployment' | grep 'hello.war'
     * </pre>
     */

    // Handlers used by the probe to check the health of a pod
    //
    // exec
    // Diagnostic: Executes a command inside the container.
    // Sucess criteria: Command exits with status code 0
    //
    // tcpSocket
    // Diagnostic: Runs a TCP check against the container’s IP address on a specified port.
    // Sucess criteria: Port is open and connection can be established
    //
    // httpGet
    // Diagnostic: Performs an HTTP GET against the container’s IP address on a specified port and path
    // Sucess criteria: Status code is between 200 and 399

}
