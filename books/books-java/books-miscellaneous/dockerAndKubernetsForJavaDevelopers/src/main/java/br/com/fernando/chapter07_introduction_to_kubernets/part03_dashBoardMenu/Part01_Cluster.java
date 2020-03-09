package br.com.fernando.chapter07_introduction_to_kubernets.part03_dashBoardMenu;

public class Part01_Cluster {

    // -------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Cluster
    //
    // Kubernetes cluster consists of a Master node and a number of worker nodes with some components inside:
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
    //
    // * Cluster Roles: Defines a set of resource types and operations that can be assigned to a user or group of users in a cluster (ClusterRole), or a Namespace (Role), 
    // but does not specify the user or group of users.
    //
    //
    // * Namespaces: Pods, volumes, ReplicaSets, and services can easily cooperate within a namespace, but the namespace provides an isolation from the other parts of the cluster.
    // Well, namespaces let you manage different environments within the same cluster.
    // For example, you can have different test and staging environments in the same cluster of machines.
    //
    //
    // * Nodes: A node is the smallest unit of computing hardware in Kubernetes. It is a representation of a single machine in your cluster.(Master note, worker nodes)
    //
    //
    // * Persistent Volumes: If a program tries to save data to a file for later, but is then relocated onto a new node, the file will no longer be where the program expects it to be.
    // For this reason, the traditional local storage associated to each node is treated as a temporary cache to hold programs, but any data saved locally can not be expected to persist.
    // Persistent Volumes provide a file system that can be mounted to the CLUSTER, without being associated with any particular node.
    //
    //
    // * Storage Classes:A StorageClass provides a way for administrators to describe the “classes” of storage they offer.
    // Different classes might map to quality-of-service levels, or to backup policies, or to arbitrary policies determined by the cluster administrators.
    // This concept is sometimes called “profiles” in other storage systems.
    // You can either change the default StorageClass or disable it completely to avoid dynamic provisioning of storage.
    //

}
