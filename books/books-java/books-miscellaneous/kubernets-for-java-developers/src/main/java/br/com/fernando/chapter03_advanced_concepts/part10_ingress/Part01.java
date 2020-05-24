package br.com.fernando.chapter03_advanced_concepts.part10_ingress;

public class Part01 {

    // What’s Ingress ?
    // An ingress is a set of rules that allows inbound connections to reach the kubernetes cluster services.
    //
    // Typically, the kubernetes cluster is firewalled from the internet.
    // It has edge routers enforcing the firewall.
    // Kubernetes resources like services, pods, have IPs only routable by the cluster network and are not (directly) accessible outside the cluster.
    //
    // For minikube users: minikube addons enable ingress
    //
    // The ingress controller
    //
    // In order for the Ingress resource to work, the cluster must have an Ingress controller running.
    //
    // When a user requests an ingress by POSTing an Ingress resource (such as the one above) to the API server, the Ingress controller is responsible for fulfilling the Ingress, usually with a loadbalance
    //
    // There are readily available third-party ingress controllers like the Nginx, Traefik, HAproxy controllers which you could easily leverage.
    //
    // Execute this command:
    //
    // $ echo "$(minikube ip) wildfly.info jetty.info" | sudo tee -a /etc/hosts
    //
    //  
    //
    /**
     * <pre>
     * apiVersion: networking.k8s.io/v1beta1
     * kind: Ingress
     * metadata:
     *    name: example-ingress
     *    annotations:
     *       nginx.ingress.kubernetes.io/rewrite-target: /
     * spec:
     *    backend:
     *       serviceName: default-http-backend # some whatever service ...
     *       servicePort: 80
     *    rules:
     *    -  host: wildfly.info
     *       http:
     *          paths:
     *          -  path: /
     *             backend:
     *                serviceName: wildfly-service
     *                servicePort: 8080
     *    -  host: jetty.info
     *       http:
     *          paths:
     *          -  path: /
     *             backend:
     *                serviceName: jetty-service
     *                servicePort: 8080
     * </pre>
     */

    // As you might have guessed, the rule is:
    //
    // All requests to "wildfly.info/" should be routed to the service in the cluster named "wildfly-service".
    //
    // Requests mapping to "jetty.info/" should be routed to the "jetty-service".
    //
    // Like the backend tag which implies that unmatched requests should be routed to the "default-http-backend" service
    //
    //
    // Note the annotation: ingress.kubernetes.io/rewrite-target: /
    // This is necessary if the target services expect requests from the root URL i.e wildfly.info and not jetty.info . 
    // The ingress mapping by default will pass along the trailing path on to the service (e.g /stilton) and 
    // if the service doesn’t accept request on that path you get a 403 error response. 
    // 
    // Thus with rewrite-target annotation, the request path is rewritten with the given path before the request get’s forwarded to the target backend. 
}
