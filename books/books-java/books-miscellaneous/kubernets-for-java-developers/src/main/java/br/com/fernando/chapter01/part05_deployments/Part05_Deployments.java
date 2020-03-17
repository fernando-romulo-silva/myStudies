package br.com.fernando.chapter01.part05_deployments;

public class Part05_Deployments {
    
    // Deployments provide declarative updates for pods and replica sets. 
    // You can easily achieve the following functionality using deployment:
    // 
    // Start a replication controller or replica set.
    // Check the status of deployment.
    // Update deployment to use a new image, without any outages.
    // Roll back deployment to an earlier revision.

    
    /**
     * <pre>
     * 
     *   apiVersion: extensions/v1beta1
     *   kind: Deployment
     *   metadata:
     *     name: wildfly-deployment
     *   spec:
     *     replicas: 3
     *     template:
     *       metadata:
     *         labels:
     *           app: wildfly
     *       spec:
     *         containers:
     *         - name: wildfly
     *           image: jboss/wildfly:10.1.0.Final
     *           ports:
     *           - containerPort: 8080
     *           
     * </pre>          
     */
}
