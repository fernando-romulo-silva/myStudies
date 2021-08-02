package br.com.fernando.chapter02_deploying_java_app_to_kubernetes.part05_service_load_balancer;

public class Part01 {

    // Creating Load Balancers to Distribute HTTP Traffic
    //
    // When you create a deployment, you can optionally create a load balancer service to distribute traffic between the nodes assigned to the deployment.
    // The key fields in the configuration of a load balancer service are the type of service being created and the ports that the load balancer will listen to.
    //
    // Please look at application.yml
    //
    /**
     * <pre>
     * apiVersion: v1
     * kind: Service
     * metadata:
     *    name: couchbase-service
     * spec:
     *    selector:
     *       app: couchbase-rc-pod
     *    type: LoadBalancer
     * </pre>
     */
    //
    // Support to SSL
    //
    // You can create a load balancer with SSL termination, allowing https traffic to an app to be distributed among the nodes in a cluster.
    /**
     * <pre>
     * apiVersion: v1
     * kind: Service
     * metadata:
     *    name: couchbase-service
     *    annotations:
     *      #Oracle Cloud Infrastructure
     *      service.beta.kubernetes.io/oci-load-balancer-ssl-ports: "443"
     *      service.beta.kubernetes.io/oci-load-balancer-tls-secret: ssl-certificate-secret
     * spec:
     *    selector:
     *       app: couchbase-rc-pod
     *    type: LoadBalancer
     * </pre>
     */

    // Specifying Load Balancer Connection Timeout
    //
    // You can specify the maximum idle time (in seconds) allowed between two successive receive or two successive send operations between the client and backend servers.
    // To explicitly specify a maximum idle time, add the following annotation in the metadata section of the manifest file:
    /**
     * <pre>
     *       apiVersion: v1
     *       kind: Service
     *       metadata:
     *          name: couchbase-service
     *          annotations:
     *            #Oracle Cloud Infrastructure
     *            service.beta.kubernetes.io/oci-load-balancer-connection-idle-timeout: 100
     *            #AWS
     *            service.beta.kubernetes.io/aws-load-balancer-connection-idle-timeout: 1200
     *            #Azure
     *            service.beta.kubernetes.io/azure-load-balancer-tcp-idle-timeout: '3600'
     *       spec:
     *          selector:
     *             app: couchbase-rc-pod
     *          type: LoadBalancer
     * </pre>
     */
    //
    // Google Cloud
    /**
     * <pre>
     *       apiVersion: cloud.google.com/v1beta1
     *       kind: BackendConfig
     *       metadata:
     *         name: my-bsc-backendconfig
     *       spec:
     *         timeoutSec: 40
     * </pre>
     */
    // then
    /**
     * <pre>
     *       apiVersion: v1
     *       kind: Service
     *       metadata:
     *         name: my-bsc-service
     *         labels:
     *           purpose: bsc-config-demo
     *         annotations:
     *           beta.cloud.google.com/backend-config: '{"ports": {"80":"my-bsc-backendconfig"}}'
     * </pre>
     */
}
