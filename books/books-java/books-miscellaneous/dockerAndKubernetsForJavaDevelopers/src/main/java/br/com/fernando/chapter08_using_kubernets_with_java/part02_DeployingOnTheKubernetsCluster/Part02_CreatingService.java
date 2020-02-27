package br.com.fernando.chapter08_using_kubernets_with_java.part02_DeployingOnTheKubernetsCluster;

public class Part02_CreatingService {

	// By default, each Pod is only accessible by its internal IP address within the Kubernetes cluster.
	// To make the container accessible from outside the Kubernetes virtual network, we need to expose the Pod as a Kubernetes Service.
	// To create a service, we are going to use the simple .yaml file, with a service manifest:
	//
	/**
	 * <pre>
	 *     apiVersion: v1
	 *     kind: Service
	 *     metadata:
	 *       name: rest-example
	 *       labels:
	 *         app: rest-example
	 *         tier: backend
	 *     spec:
	 *       type: NodePort
	 *       ports:
	 *       - port: 8080
	 *         
	 *         
	 *      # Every node in a Kubernetes cluster runs a kube-proxy process.    
	 *      # The kube-proxy plays a crucial role in Kubernetes services. Its purpose is to expose a virtual IP for them.   
	 *       selector:
	 *         app: rest-example
	 *         tier: backend
	 * </pre>
	 */
	//
	// Note that the manifest of a service doesn't refer to a Docker image.
	// This is because a service in Kubernetes is just an abstraction which provides a network connection to one or more Pods.
	// Each service is given its own IP address and port, which remains constant for the lifetime of the service.
	// Each Pod needs to have a specific label, to be discovered by the service, services find Pods to group using and labels selectors.
	// In our previous example, the selector will pick up all Pods having a label app with the value of rest-example and a label named tier with a value of backend.
	//
	// The service type can have the following values:
	//
	// NodePort: By specifying a service type of NodePort, we declare to expose the service outside the cluster.
	// The Kubernetes master will allocate a port from a flag-configured range (default: 30000-32767),
	// and each node of the cluster will proxy that port (the same port number on every node) into your service
	//
	// Load balancer: This would create a load balancer on cloud providers which support external load balancers (for example, on Amazon AWS cloud).
	// This feature is not available when using Minikube
	//
	// Cluster IP: This would expose the service only within the cluster. This is the default value which will be used if you don't provide another
	//
	//
	//
	// Having our service.yml file ready, we can create our first Kubernetes service, by executing the following kubectl command:
	//
	// $ kubectl create -f service.yml
	//
	// To see if our service is created properly:
	//
	// $ kubectl get services
	//
	// To see the details of a specific service, we use the describe command. Execute the following to see the details of our rest-example Java service:
	//
	// $ kubectl describe service rest-example
}
