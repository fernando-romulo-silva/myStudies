package br.com.fernando.chapter08_using_kubernets_with_java.part02_DeployingOnTheKubernetsCluster;

public class Part04_InteractingContainersViewingLogs {

	// Interacting with containers and viewing logs
	//
	//
	// The easiest and most simple logging method for containerized applications is just to write to the standard output and standard error streams.
	// Kubernetes supports this out of the box.
	//
	// First, you need the pod's name:
	//
	// $ kubectl get pods
	//
	//
	// You can show logs of the specified Pod:
	//
	// $ kubectl logs -f rest-example-6dd89cf8cd-qfmfr
	//
	//
	// -f is Stream pod logs (stdout)
	//
	//
	// As you already know, Pods and containers are fragile.
	// They can crash or be killed.
	// You can use kubectl logs to retrieve logs from a previous instantiation of a container with the --previous flag, in case the container has crashed.
	//
	//
	// Get a shell to the running Container:
	//
	// $ kubectl exec -it rest-example-6dd89cf8cd-qfmfr -- /bin/bash
	//
	//
	// If a Pod has more than one Container, use --container or -c to specify a Container in the kubectl exec command. 
	// For example, suppose you have a Pod named my-pod, and the Pod has two containers named main-app and helper-app. 
	// The following command would open a shell to the main-app Container.
	//
	// $ kubectl exec -it my-pod --container main-app -- /bin/bash
}
