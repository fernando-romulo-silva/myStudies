package br.com.fernando.chapter02_deploying_java_app_to_kubernetes.part03_startint_wildfly;

public class Part1 {

    //
    //
    // Start the WildFly application server container:
    //
    // $ kubectl run --generator=run-pod/v1 hello-wildfly --image=jboss/wildfly:10.1.0.Final --port=8080 *deprecated
    // 
    // or
    //
    // $ kubectl create deployment hello-wildfly --image=jboss/wildfly:10.1.0.Final 
    //
    // 
    // Get the list of deployments like so:
    //
    // $ kubectl get deployments
    //
    //
    // To get more details about the deployment, use the kubectl describe command:
    //
    // $ kubectl describe deployments hello-wildfly
    //
    //
    // A single replica of WildFly pod is created as part of this deployment. Show the pods:
    //
    // $ kubectl get -w pods 
    //
    //
    // To see the pods log:
    //
    // $ kubectl logs hello-wildfly-65bbc6dc87-zvswc
    //
    //
    // The container started by this deployment is accessible only inside the cluster. 
    // Expose the deployment as a service using the kubectl expose command:
    // 
    // $ kubectl expose deployment hello-wildfly --name=hello-wildfly-service --port=8080 --target-port=8080 --name=hello-wildfly-service --type=NodePort
    //
    //
    // The --port defines the port on which the service should listen. --target-port is the port on the container that the service should direct traffic to. In our case, service is listening on port 8080 and will redirect to port 8080 on the container.
    // All the exposed resources are available on the Kubernetes API server. 
    // You can access them by starting a proxy to the API server using the kubectl proxy command.
    //  
    // 
    // $ kubectl get services hello-wildfly-service
    //
    // If the external IP address is shown as <pending>, In this case, there is no LoadBalancer integrated (unlike AWS or Google Cloud).  
    // With this default setup, you can only use 'NodePort' or an 'Ingress Controller'.
    // With the Ingress Controller you can setup a domain name which maps to your pod; you don't need to give your Service the 'LoadBalancer' type if you use an Ingress Controller.
    // 
    // $ minikube service hello-wildfly-service
    //
    //
    // if don't work... 
    //
    // $ minikube tunnel
    //
    // 
    // Cleaning up
    // 
    // To delete the Service, enter this command:
    // 
    // $ kubectl delete services my-service
    //
    //
    // To delete the Deployment, the ReplicaSet, and the Pods that are running the Hello World application, enter this command:
    //
    // $ kubectl delete deployment hello-world
}
