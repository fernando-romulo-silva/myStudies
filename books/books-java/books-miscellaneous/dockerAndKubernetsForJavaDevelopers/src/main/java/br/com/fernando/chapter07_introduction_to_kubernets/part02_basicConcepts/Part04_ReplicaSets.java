package br.com.fernando.chapter07_introduction_to_kubernets.part02_basicConcepts;

public class Part04_ReplicaSets {

	// ReplicaSets
	//
	// ReplicaSets is the concept used in scaling your application by using replication.
	// Typically, you would want to replicate your containers (which are, in fact, your application) for several reasons, including:
	//
	//
	//
	// * Scaling: When load increases and becomes too heavy for the number of existing instances, Kubernetes enables you to easily scale up your application,
	// creating additional instances as needed.
	//
	// * Load balancing: We can easily distribute traffic to different instances to prevent overloading of a single instance or node.
	// Load balancing comes out of the box because of Kubernetes' architecture and it's very convenient.
	//
	// * Reliability and fault tolerance: By having multiple versions of an application, you prevent problems if one or more fail.
	// This is particularly true if the system replaces any containers that fail.
	//
	//
	// A ReplicaSet ensures that a specified number of Pod clones, known as replicas, are running at any given time. 
	// It there are too many, they will be shut down.
	// If there is a need for more, for example some of them died because of an error or crash, or maybe there's a higher load, some more Pods will be brought to life.
	//

}
