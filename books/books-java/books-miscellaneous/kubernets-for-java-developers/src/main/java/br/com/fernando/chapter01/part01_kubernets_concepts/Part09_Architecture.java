package br.com.fernando.chapter01.part01_kubernets_concepts;

public class Part09_Architecture {

    // A Kubernetes cluster is a set of physical or virtual machines and other infrastructure resources that are used to run your applications.
    //
    // Each machine is called a node.
    //
    // The machines that manage the cluster are called master nodes, and the machines that run the containers are called worker nodes.
    //
    // Master
    //
    // kubectl: This is a command-line tool that send commands to the master node to create, read, update, and delete resources.
    //
    // API server: Each command from kubectl is translated into a REST API and issued to the API server running inside the master node.
    // The API server processes REST operations, validates them, and persists the state in a distributed watchable storage.
    // This is implemented using etcd for Kubernetes
    //
    // Scheduler: The scheduler works with the API server to schedule pods to the nodes.
    //
    // Controller manager: The controller manager is a daemon that watches the state of the cluster using the API server for different
    // controllers and reconciles the actual state with the desired
    //
    // etcd: This is a simple, distributed, watchable, and consistent key/value store.
    // It stores the persistent state of all REST API objects—for example, how many pods are deployed on each worker node,
    // labels assigned to each pod, and namespaces for different resources.
    //
    //
    // Worker Nodes
    //
    // Kubelet: This is a service running on each node that manages containers and is managed by the master. I
    //
    // Proxy: This runs on each node, acting as a network proxy and load balancer for a service on a worker node.
    //
    // Docker Container: Docker Engine is the container runtime running on each node.
    // It understands the Docker image format and knows how to run Docker containers.

    /**
     * <pre>
     *  
     *                                                                                       Internet
     *                                                                                           |
     *                                                                                           |
     *                                                                                           V                                                                          
     *                                                                                |=====================|
     *                                                                                |                     |
     *                                                                                |    Load Balancer    |
     *                                                                                |                     | 
     *                                                                                |=====================|  
     *                                                                                             +    +
     *                                                                                             +    +++++++++++++++++++++++++++++++++++
     *                                                                                             +                                      +
     *                                                                                             +                                      +
     *  |=========================================================|                                +                                      +
     *  | Master                                                  |                                ++++++++++++++++++++++++++++++++++++   +
     *  |                    API Server  ***********************************                                                          +   +
     *  |                     |   |  |                            |        *                                                          +   +
     *  |                     |   |  |                            |        *                                                          +   +
     *  |   |------------------   |  --------------|              |        *                                                          +   +
     *  |   |                     |                |              |        *                                                          +   +
     *  |   V                     |                V              |        *                                                          +   +
     *  | etcd                Scheduler     Controller Manager    |        *                                                          +   +
     *  |=========================================================|        *                                                          +   +
     *                                                                     *                                                          +   +
     *                                                                     *                                                          +   +
     *                                             ********************************************************************************   +   +                     
     *                                             *                                                                              *   +   +
     *                                             *                                                                              *   +   +
     *                                             *                                                                              *   +   +
     *                                             *                                                                              *   +   +
     *                                             *                                                                              *   +   +
     *                                             *                                                                              *   +   +
     * |======================================|    *                                  |======================================|    *   +   +          
     * | Node 1 (worker)                      |    *                                  | Node n (worker)                      |    *   +   +
     * |                                      |    *                                  |                                      |    *   +   +
     * |  Kubelet***********************************                                  |  Kubelet***********************************   +   +
     * |                                      |                                       |                                      |        +   +
     * |  Proxy++++++++++++++++++++++++++++++++++++++++++                             |  Proxy+++++++++++++++++++++++++++++++++++++++++   +
     * |                                      |         +                             |                                      |            +
     * |  Pod1 (container1, container2 ...)   |         +                             |  Pod1 (container1, container2 ...)   |            +
     * |                                      |         +    ...                      |                                      |            +
     * |  Pod2 (container1, container2 ...)   |         +                             |  Pod2 (container1, container2 ...)   |            +
     * |                                      |         +                             |                                      |            +
     * |======================================|         +                             |======================================|            +
     *                                                  +                                                                                 +
     *                                                  +                                                                                 +
     *                                                  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     *
     * </pre>
     **/
}
