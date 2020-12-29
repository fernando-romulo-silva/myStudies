package br.com.fernando.chapter07_introduction_to_kubernets.part02_basicConcepts;

public class Part03_Pods {

	// The Pod's definition is a JSON or YAML file called a Pod manifest.
	// Take a look at a simple example with one container:
	/**
	 * <pre>
	 * 
	 *  apiVersion: v1
	 *  kind: Pod
	 *  metadata:
	 *     name: rest_service
	 *     labels:
	 *        name: rest_service 
	 *  spec:
	 *    containers:
	 *    - name: rest_service
	 *      image: rest_service
	 *      ports:
	 *      - containerPort: 8080
	 * 
	 * </pre>
	 */

	// The same pod manifest in a JSON file will look the same as the following:
	/**
	 * <pre>
	 * 
	 * {
	 *    "apiVersion": "v1", 
	 *    "kind": "Pod",
	 *    "metadata":{
	 *       "name": "rest_service",
	 *       "labels": {
	 *          "name": "rest_service"
	 *       }
	 *    },
	 *    "spec": {
	 *       "containers": [{
	 *          "name": "rest_service",
	 *          "image": "rest_service",
	 *          "ports": [{"containerPort": 8080}],
	 *        }]
	 *    }
	 * }
	 * 
	 * </pre>
	 */
	
	// The container's image is a Docker image name. 
	// The containerPort exposes that port from the REST service container so we can connect to the service at the Pod's IP.
	//
	// It's very important to be aware that a Pod's life is fragile.
	//
	// Because the Pods are treated as stateless, independent units, if one of them is unhealthy or is just being replaced with a newer version, 
	// the Kubernetes Master doesn't have mercy on it--it just kills it and disposes of it.
	//
	// In fact, Pods have a strictly defined lifecycle. The following list describes the phases of a Pod's life:
	// 
	// * pending: This phase means that the Pod has been accepted by the Kubernetes system, but one or more of the Docker container images has not been created.
	//
	// * running: The Pod has been put onto a node and all of the Pod's Docker containers have been created.
	//
	// * succeeded: All Docker containers in the Pod have been terminated with a success status.
	//
	// * failed: All Docker containers in the Pod have been terminated, but at least one container has terminated with a failure status or was terminated by the system.
	// 
	// * unknown: This typically indicates a problem with communication to the host of the Pod; for some reason, the state of the Pod could not be retrieved.
}

