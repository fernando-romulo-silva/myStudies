package br.com.fernando.chapter08_using_kubernets_with_java.part02_DeployingOnTheKubernetsCluster;

public class Part03_CreatingDeployment {

    // Before creating a deployment, we need to have our Docker image ready and published to a registry.
    //
    // Of course, it can also be a private repository hosted in your organization.
    // Each Docker container in a Pod has its own image.
    // By default, the kubectl process in a Pod will try to pull each image from the specified registry.
    //
    // It is important that you provide a tag in the image name.
    // Otherwise, Kubernetes will use the latest tag when looking for your image in a repository, the same as Docker does.
    //
    // Using locally built images gets a little bit tricky when working with a local Kubernetes cluster.
    // Minikube runs in a separate VM, hence it will not see the images you've built locally using Docker on your machine.
    // There's a workaround for that. You can execute the following command:
    //
    // $ eval $(minikube docker-env)
    //
    // The previous command will actually utilize the Docker daemon running on minikube, and build your image on the Minikube's Docker.
    //
    // This way, the locally built image will be available to the Minikube without pulling from the external registry.
    //
    // To confirm, just execute the command:
    //
    // $ docker images
    //
    // It shows the minikube images, not the host.
    //
    // Okay, let's do the image:
    //
    // $ docker build -t jotka/rest-example .
    //
    // Now that we have an image available in the registry, we need a deployment manifest. It's again a .yaml file, which can look the same as this:
    /**
     * <pre>
     * apiVersion: apps/v1 
     * kind: Deployment    
     * metadata:           
     *   name: rest-exampl e
     * spec:               
     *   replicas: 1       
     *   selector:         
     *      matchLabels:   
     *          app: rest- example
     *          role: mast er
     *          tier: back end  
     *   template:         
     *     metadata:       
     *       labels:       
     *         app: rest-e xample
     *         role: maste r
     *         tier: backe nd
     *     spec:           
     *       containers:   
     *       - name: rest- example
     *         image: jotk a/rest-example
     *         imagePullPo licy: IfNotPresent
     *         resources:  
     *           requests: 
     *             cpu: 10 0m
     *             memory:  100Mi
     *         env:        
     *         - name: GET _HOSTS_FROM
     *           value: dn s
     *         ports:      
     *         - container Port: 8080
     * </pre>
     */

    // To create this deployment on the cluster using kubectl, you will need to execute the following command,
    // which is exactly the same as when creating a service, with a difference in the filename:
    //
    // $ kubectl create -f deployment.yml
    //
    //
    // You can look at the deployment properties with:
    //
    // $ kubectl describe deployment rest-example
    //
    //
    // You can also look at the Pods with:
    //
    // $ kubectl get pods
    //
    //
    // Calling the service
    //
    // However, to be able to execute the service from the outside world, we have used the NodePort mapping, and we know that it was given the port 31141.
    // All we need to call the service is the IP of the cluster. We can get it using the following command:
    //
    // $ minikube ip
    //
    //
    // There's a shortcut for getting to know the externally accessible service URL and a port number.
    // We can use a minikube service command to tell us the exact service address:
    //
    // $ minikube service rest-example --url
    //
    //
    // http://192.168.99.100:32702/book

}
