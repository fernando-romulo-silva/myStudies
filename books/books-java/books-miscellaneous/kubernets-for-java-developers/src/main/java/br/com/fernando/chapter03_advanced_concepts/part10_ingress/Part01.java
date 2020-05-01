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
    // echo "$(minikube ip) wildfly.info jetty.info" | sudo tee -a /etc/hosts
    /**
     * <pre>
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * </pre>
     */
    
    
    
    
    
    
    
    
    
    
    
    // As you might have guessed, the rule is:
    // all requests to myminikube.info/ should be routed to the service in the cluster named echoserver.
    // requests mapping to cheeses.all/stilton should be routed to the stilton-cheese service.
    // and finally, requests mapping to cheeses.all/cheddar should be routed to the cheddar-cheese service.
}
