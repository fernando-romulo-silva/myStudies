package br.com.fernando.chapter07_introduction_to_kubernets.part02_basicConcepts;

public class Part05_Deployment {

	// Deployment
	//
	// The Deployment is responsible for creating and updating instances of your application.
	// Once the Deployment has been created, the Kubernetes Master schedules the application instances onto individual nodes in the cluster.
	//
	// You just specify the number of replicas you need and the container to run within each Pod and the Deployment controller will spin them up.
	// The example Deployment manifest definition in the YAML file looks the same as the following:
	//

	/**
	 * <pre>
	 * 
	 * apiVersion: 1.0
	 * kind: Deployment
	 * metadata:
	 *   name: rest_service-deployment
	 * spec:
	 *   replicas: 3
	 *   template:
	 *     metadata:
	 *       labels:
	 *         app: rest_service
	 *     spec:
	 *       containers:
	 *       - name: rest_service
	 *         image: rest_service
	 *         ports:
	 *         - containerPort: 8080
	 *         
	 * </pre>
	 */
	
	// The Deployment is a kind of control structure that takes care of the spinning up or down of Pods. 
	// A Deployment takes care of the state of a Pod or group of pods by creating or shutting down replicas. 
	// Deployments also manage updates to Pods. 
	//
	// The main purpose of Deployments is to do rolling updates and rollbacks. A rolling update is the process of updating an application to a newer version, in a serial, one-by-one fashion. 
	// By updating one instance at a time, you are able to keep the application up and running. 
	// If you were to just update all instances at the same time, your application would likely experience downtime.
	//
	// Rolling updates are does by Services
}
