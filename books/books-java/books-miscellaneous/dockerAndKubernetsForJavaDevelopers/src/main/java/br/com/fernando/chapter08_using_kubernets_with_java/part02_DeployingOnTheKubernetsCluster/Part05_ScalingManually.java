package br.com.fernando.chapter08_using_kubernets_with_java.part02_DeployingOnTheKubernetsCluster;

public class Part05_ScalingManually {

	// Scaling manually
	//
	// When the deployment has been created, the new ReplicaSet has also been created, automatically.
	// ReplicaSet ensures that a specified number of Pod clones, known as replicas, are running at any given time.
	// It there are too many, some of them will be shut down. If there is a need for more, Pods will be created. 
	//
	// But if the deployment controller sees that you have modified the replica set to three, since it knows that it is supposed to be one, it will reset it back to one.
	// By manually modifying the replica set that was created for you, you are, kind of, dealing against the system controller.
	//
	// To scale up our rest-example deployment from one up to three Pods, execute the following kubectl scale command:
	// 
	// $ kubectl scale deployment rest-example --replicas=3 
	//
	// After a short while, in order to check, execute the following commands, you will see that now three Pods are running in the deployment:
	//
	// $ kubectl get deployments
	// $ kubectl get pods      
	 

}
