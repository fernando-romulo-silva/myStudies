package br.com.fernando.chapter01_kubernets_concepts.part04_replicaSets;

public class Part04_ReplicaSets {

    // Replica Sets
    //
    // Replica sets are the next-generation replication controllers. Just like a replication controller, a replica set ensures that a specified number of pod replicas are running at any one time. 
    // The only difference between a replication controller and a replica set is the selector support.
    //
    // For replication controllers, matching pods must satisfy all of the specified label constraints. 
    // The supported operators are =, ==, and !=. 
    //
    // For replica sets, filtering is done according to a set of values. 
    // The supported operators are in, notin, and exists (only for the key). 
    // For example, a replication controller can select pods such as environment = dev. 
    // A replica set can select pods such as environment in ["dev", "test"].
    
   /**
    * apiVersion: extensions/v1beta1 
    * kind: ReplicaSet 
    * metadata:  
    *   name: wildfly-rs
    * spec:  
    *   replicas: 2  
    *   selector:    
    *	  matchLabels:       
    *         app: wildfly-rs-pod     
    *     matchExpressions:       
    *       - {key: tier, operator: In, values: ["backend"]}       
    *       - {key: environment, operator: NotIn, values: ["prod"]}   
    * 	template:    
    * 	  metadata:      
    *       labels:        
    * 	      app: wildfly-rs-pod        
    * 	      tier: backend        
    * 	      environment: dev    
    *     spec:      
    *       containers:      
    *        - name: wildfly        
    *          image: jboss/wildfly:10.1.0.Final        
    *          ports:        
    *          - containerPort: 8080
    *       
    */      
    //
    // The key differences between ReplicationController and Replica sets are as follows:
    //
    // The apiVersion property value is extensions/v1beta1. 
    // This means that this object is not part of the “core” API at this time, but is only a part of the extensions group.
    //
    // The value of kind is Replicaset and indicates the type of this resource.
    //
    // matchLabels defines the list of labels that must be on the selected pod. Each label is a key/value pair.
    //
    // wildfly-rs-pod is the exact label that must be on the selected pod.
    //
    // matchExpressions defines the list of pod selector requirements.
    //
    // Replica sets are generally never created on their own. 
    // Deployments own and manage replica sets to orchestrate pod creation, deletion, and updates. 
    // See the following section for more details about deployments.
}
