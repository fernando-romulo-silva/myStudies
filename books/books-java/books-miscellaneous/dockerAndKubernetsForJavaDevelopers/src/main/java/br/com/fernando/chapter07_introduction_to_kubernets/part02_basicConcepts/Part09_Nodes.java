package br.com.fernando.chapter07_introduction_to_kubernets.part02_basicConcepts;

public class Part09_Nodes {
	
	// Node
	// It may be a virtual or physical machine, depending on your infrastructure. 
	// A worker node runs the tasks as instructed by the Master node.
	//
	//
	// Node -> Kublet, proxy, Pod01 (docker container01, docker container02) 
	//
	//
	//
	// Kuberlet
	//
	// Kubelet is probably the most important controller in Kubernetes. 
	// It's a process that responds to the commands coming from the Master node.
	//
	// Each node has this process listening. The Master calls it to manage Pods and their containers. 
	//
	// The Kubelet runs Pods, is responsible for what's running on an individual machine and it has one job: given a set of containers to run, to make sure they are all running.
	//
	//
	// Proxy
	//
	// A proxy is a network proxy that creates a virtual IP address which clients can access. 
	// The network calls will be transparently proxied to the Pods in a Kubernetes service. 
	// A service, as you already know, provides a way to group Pods into kind of a single business process, which can be reached under a common access policy.
	//
	//
	// Docker
	//
	// Finally, each node needs something to run. It will be a Docker container runtime, which is responsible for pulling the images and running containers.

}
