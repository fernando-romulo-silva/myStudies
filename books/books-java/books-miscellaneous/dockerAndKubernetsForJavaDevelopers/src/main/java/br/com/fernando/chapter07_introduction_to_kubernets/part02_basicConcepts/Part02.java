package br.com.fernando.chapter07_introduction_to_kubernets.part02_basicConcepts;

public class Part02 {

	// Pods
	// The Pod consists of one or more Docker containers. 
	// This is the basic unit of the Kubernetes platform and an elementary piece of execution that Kubernetes works with.
	//
	// Containers running in the same Pod share the same common network namespace, disk, and security context. 
	// In fact, the communication over localhost is recommended between containers running on the same Pod. 
	// Each container can also communicate with any other Pod or service within the cluster.
	//
	/**
	 * <pre>
	 * |====================================================|
	 * |  Pod                                               |
	 * |                                                    |
	 * |  |========================================|        |
	 * |  | Labels                                 |        |
	 * |  |         key1=value1    key2=value2 ... |        |
	 * |  |========================================|        |
	 * |                                                    |
	 * |  |============================|                    |
	 * |  | Annotations                |                    |
	 * |  |             a1  a2  a3 ... |                    |
	 * |  |============================|                    |
	 * |                                                    |
	 * |  |===========|          |=============|            |               
	 * |  | Ip Addres |          | Container 1 |            |
	 * |  |===========|          |=============|            |
	 * |                                                    |
	 * |                         |=============|            |
	 * |                         | Container 2 |            |
	 * |                         |=============|            |
	 * |                                                    |
	 * |                         |=============|            |
	 * |                         | Container 3 |            |
	 * |                         |=============|            |
	 * |                                                    |
	 * |====================================================|
	 * </pre>
	 */
	// Kubernetes also supports the concept of a volume. Volumes that are attached to the Pod may be mounted inside of one or more containers running on this Pod.
	//
	// Kubernetes supports a lot of different volume types as a native support for mounting GitHub repositories, network disks, local hard drives, and so on.
	//
	// Pod can have labels and annotations attached. 
	// Labels are very important in Kubernetes. They are key/value pairs that are attached to objects, in this case to Pods. 
	// The idea behind labels is that they can be used to identify objects--labels are meaningful and relevant to users. An example of the label may be:
	// 
	// app=my-rest-service 
	// layer=backend  
	//
	// Annotations, on the other hand, are a kind of metadata you can attach to Pods. 
	// They are not intended to be identifying attributes; they are such properties that can be read by tools of libraries. 
	//
	//
	// Pod contain multiple containers. 
	// A real-life example of having a Pod with more than one Docker container could be our Java REST microservice Pod. 
	// In real life, the data should probably go to the real database.
	//
	// Our Pod would probably have a container with Java JRE and the Spring Boot application itself, together with the second container with a PostgreSQL database,
	// which the microservice uses to store its data.
	//
	// Two of those containers makes a Pod--a single, decoupled unit of execution that contains everything our REST service needs to operate.
}
