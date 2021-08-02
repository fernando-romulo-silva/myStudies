package br.com.fernando.chapter07_introduction_to_kubernets.part02_basicConcepts;

public class Part10_TheMasterNode {

	// The Master node does not run any containers--it just handles and manages the cluster. 
	// The Master is the central control point that provides a unified view of the cluster.
	//
	// Master (etcd, API Server, Scheduler and controller manager)
	//
	// etcd
	//
	// Kubernetes stores all of its cluster state in etcd, a distributed data store with a strong consistency model.
	//
	//
	// The API server
	//
	// It's main purpose is to validate and configure data for the API objects which are Pods, services, ReplicaSets, and others. 
	// The API server provides the frontend to the cluster's shared state through which all other components interact.
	//
	//
	// The scheduler
	//
	// Once the application instances are up and running, the Deployment Controller will be continuously monitoring those instances. 
	// 
	
}
