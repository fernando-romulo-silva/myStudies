package br.com.fernando.chapter07_introduction_to_kubernets.part02_basicConcepts;

public class Part01_BasicConcepts {

	// Basic Concepts
	//
	// A cluster is a group of nodes; they can be physical servers or virtual machines that have the Kubernetes platform installed. 
	// The basic Kubernetes architecture is presented in the following diagram:
	//
	// As you can see, the Kubernetes cluster consists of a Master node and a number of worker nodes with some components inside
	
	/**
	 * <pre>
	 * 
	 *                                           |========================================================|
	 *                                           |                      Master                            |
	 *                                           |                                                        | 
	 *                                           | etcd      API server   Schedule and Controller Manager |
	 *                                           |========================================================|
	 *                             
	 *         
	 * |======================================|            |======================================|            |======================================|                    
	 * |                 Node                 |            |                 Node                 |            |                 Node                 |     
	 * |                                      |            |                                      |            |                                      |     
	 * |  Kubelet    Proxy                    |            |  Kubelet    Proxy                    |            |  Kubelet    Proxy                    |     
	 * |                                      |            |                                      |            |                                      |     
	 * |  Pod1 (container1, container2 ...)   |            |  Pod1 (container1, container2 ...)   |            |  Pod1 (container1, container2 ...)   |     
	 * |                                      |            |                                      |            |                                      |     
	 * |  Pod2 (container1, container2 ...)   |            |  Pod2 (container1, container2 ...)   |            |  Pod2 (container1, container2 ...)   |     
	 * |                                      |            |                                      |            |                                      |     
	 * |======================================|            |======================================|            |======================================|
	 * 
	 * </pre>
	 */
}
