package br.com.fernando.chapter01.part01_kubernets_concepts;

public class Part03_ReplicationControllers {

    // A replication controller (RC) ensures that a specified number of pod “replicas” are running at any one time.
    //
    // Unlike manually created pods, the pods maintained by a replication controller are automatically replaced if they fail, get deleted, or are terminated. A replication controller ensures the recreation of a pod when the worker node fails or reboots.
    // It also allows for both upscaling and downscaling the number of replicas.
    //
    // It also allows for both upscaling and downscaling the number of replicas.

    /**
     * <pre>
     *            
     *  apiVersion: v1
     *  kind: ReplicationController 
     *  metadata:
     *    name: wildfly-rc
     *  spec:  
     *    replicas: 2
     *    selector:
     *        app: wildfly-rc-pod  
     *    template:
     *        metadata:      
     *    	labels:        
     *            app: wildfly-rc-pod    
     *        spec:      
     *    	containers:      
     *    	- name: wildfly        
     *    	  image: jboss/wildfly:10.1.0.Final        
     *    	  ports:        
     *    	  - containerPort: 8080
     * </pre>
     */

    // The apiVersion, kind, metadata, and spec properties serve the same purpose in all configuration files.
    //
    // The value of kind is ReplicationController, which indicates that this resource is a replication controller.
    //
    // Replicas: Replicas defines the number of replicas of the pod that should concurrently run. 
    // By default, only one replica is created.
    //
    // Selector: selector is an optional property. 
    // The replication controller manages the pods that contain the labels defined by the spec.selector property. 
    // If specified, this value must match spec.template.metadata.labels (in pod).
    // All labels specified in the selector must match the labels on the selected pod.
    //
    // Template: template is the only required field of spec in this case. 
    // The value of this field is exactly the same as a pod, except it is nested and does not have an apiVersion or kind. 
    // Note that spec.template.metadata.labels matches the value specified in spec.selector. 
    // This ensures that all pods started by this replication controller have the required metadata in order to be selected.
    //
    // Each pod started by this replication controller has a name in the format <name-of-the-RC>-<hash-value-of-pod-template>. 
    // In our case, all names will be wildfly-rc-xxxxx, where xxxxx is the hash value of the pod template.
}
