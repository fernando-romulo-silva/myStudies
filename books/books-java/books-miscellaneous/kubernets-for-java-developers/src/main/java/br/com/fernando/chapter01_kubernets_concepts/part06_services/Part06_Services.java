package br.com.fernando.chapter01_kubernets_concepts.part06_services;

public class Part06_Services {

    // A service is an abstraction that defines a logical set of pods and a policy by which to access them. 
    // The IP address assigned to a service does not change over time, and thus can be relied upon by other pods.
    // Typically, the pods belonging to a service are defined by a label selector. 
    // This is similar to how pods belong to a replication controller.
    //
    // Multiple resources, such as a service and a replication controller, may be defined in the same configuration file. 
    // In this case, each resource definition in the configuration file needs to be separated by ---.
    /**
     * <pre>
     *     apiVersion: v1
     *     kind: Service
     *     metadata: 
     *       name: wildfly-service
     *     spec: 
     *       selector: 
     *         app: wildfly-rc-pod
     *       ports:
     *         - name: web
     *           port: 8080
     *     ---
     *     apiVersion: v1
     *     kind: ReplicationController
     *     metadata:
     *       name: wildfly-rc
     *     spec:
     *       replicas: 2
     *       template:
     *         metadata:
     *           labels:
     *             app: wildfly-rc-pod
     *         spec:
     *           containers:
     *           - name: wildfly
     *             image: jboss/wildfly:10.1.0.Final
     *             ports:
     *             - containerPort: 8080
     * </pre>
     */
    
    // As long as kube-dns is running (which I believe is "always unless you disable it"), all Service objects have an in cluster DNS name of 
    // service_name +"."+ service_namespace + ".svc.cluster.local" so all other things would address your backendapi in the default 
    // namespace as (to use your port numbered example) Ex: 
    //
    // http://backendapi.default.svc.cluster.local:8080 
    //
    // That fact is the very reason Kubernetes forces all identifiers to be a "dns compatible" name (no underscores or other goofy characters).
}
