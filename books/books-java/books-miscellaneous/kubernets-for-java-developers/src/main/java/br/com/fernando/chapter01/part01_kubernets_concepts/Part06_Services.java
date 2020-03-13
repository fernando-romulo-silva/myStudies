package br.com.fernando.chapter01.part01_kubernets_concepts;

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
}
