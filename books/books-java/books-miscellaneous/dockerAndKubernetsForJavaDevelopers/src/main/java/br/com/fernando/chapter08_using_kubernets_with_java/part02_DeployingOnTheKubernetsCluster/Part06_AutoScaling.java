package br.com.fernando.chapter08_using_kubernets_with_java.part02_DeployingOnTheKubernetsCluster;

public class Part06_AutoScaling {

	// Autoscaling
	//
	// With horizontal Pod auto scaling, Kubernetes automatically scales the number of Pods in a deployment or ReplicaSet based on observed CPU utilization.
	//
	// The Kubernetes controller periodically adjusts the number of Pod replicas in a deployment to match the observed average CPU utilization to the target you specified.
	//
	// List autoscalers
	// $ kubectl get hpa
	//
	// Get detailed description
	// $ kubectl describe hpa
	//
	// Delete an autoscaler
	// $ kubectl delete hpa
	//
	// Additionally, there is a special kubectl autoscale command for easy creation of a Horizontal Pod Autoscaler. An example could be:
	//
	// $ kubectl autoscale deployment rest-example --cpu-percent=50 --min=1 --max=10
	//
	// The previous command will create an autoscaler for our rest-example deployment, with the target CPU utilization set to 50% and the number of replicas between 1 and 10.
	//
	//
	// Viewing cluster events
	// 
	// To view cluster events, type the following command:
	//
	// $ kubectl get events  

}
