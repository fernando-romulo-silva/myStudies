package br.com.fernando.chapter01_kubernets_concepts.part02_Pods;

public class Part02_Pods {

    // Pod
    //
    // A pod is the smallest deployable unit that can be created, scheduled, and managed.
    // It’s a logical collection of containers that belong to an application.
    // Pods are created in a namespace.
    // All containers in a pod share the namespace, volumes, and networking stack.
    // This allows containers in the pod to “find” each other and communicate using localhost.
    //
    // Each resource in Kubernetes can be defined using a configuration file.
    // For example, a WildFly pod can be defined with the configuration file shown in:
    /**
     * <pre>
     * 
     * apiVersion: v1
     * kind: Pod
     * metadata:
     *   name: wildfly-pod  
     *   labels:
     *     name: wildfly-pod
     * spec:
     *   containers:
     *   - name: wildfly
     *     image: jboss/wildfly:10.1.0.Final
     *     ports:
     *     - containerPort: 8080
     * 
     * </pre>
     */

    /**
     * <pre>
     * 
     * apiVersion: v1 -> defines the version of the Kubernetes API. This is now fixed at v1 and allows for the API to evolve in the future.
     * 
     * kind: Pod -> defines the type of this resource—in this example, that value is Pod.
     * 
     * metadata: -> allows you to attach information about the resource.
     *   
     *   name: wildfly-pod -> resource must have a name attribute. If this attribute is not set, then you must specify the generateName attribute
     *                        Optionally, you can use a namespace property to specify a namespace for the pod. 
     *                        Namespaces provide a scope for names and are explained further in “Namespaces”.  
     *   labels: -> Labels are designed to specify identifying attributes of the object that are meaningful and relevant to the users, but which do not directly imply semantics to 
     *              the core system. Multiple labels can be attached to a resource.
     *              
     *     name: wildfly-pod
     *     
     * spec: -> defines the specification of the resource, pod in our case.
     *
     *   containers:  -> defines all the containers within the pod.
     *   
     *   - name: wildfly  -> Each container must have a uniquely identified name and image property. name defines the name of the container, 
     *                       and image defines the Docker image used for that contain
     *     image: jboss/wildfly:10.1.0.Final
     *     
     *     ports: ->  define the list of ports to expose from the container. WildFly runs on port 8080, and thus that port is listed here. 
     *                This allows other resources in Kubernetes to access this container on this port.
     *     - containerPort: 8080
     * 
     * </pre>
     */
    //
    // In addition, restartPolicy can be used to define the restart policy of all containers within the pod.
    // volumes[] can be used to list volumes that can be mounted by containers belonging to the pod.
}
