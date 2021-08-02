package br.com.fernando.chapter02_deploying_java_app_to_kubernetes.part02_running_your_first_java_application;

public class Part01 {

    // Running a Java application in Kubernetes requires you to create different Kubernetes resources
    //
    // fernando/oreilly-hello-java (docker-images/hello-java) is a Docker image that uses openjdk as the base image.
    //
    // Dockerfile
    //
    // FROM openjdk
    // CMD ["java", "-version"]
    //
    //
    // Create a pod using the kubectl run command:
    //
    // $ kubectl run --generator=run-pod/v1 hello-java --image=arungupta/oreilly-hello-java
    //
    //
    // Check the status of deployment as follows:
    //
    // $ kubectl get deployments
    //
    //
    // Check the pods in the deployment like so:
    //
    // $ kubectl get pods
    /**
     * <pre>
     *  fernando@pc02:~$ kubectl get pods
     *  NAME                            READY   STATUS             RESTARTS   AGE
     *  hello-java-75c9445ff9-g4sd9     0/1     CrashLoopBackOff   7          15m
     *  rest-example-6dd89cf8cd-qfmfr   1/1     Running            4          23d
     * </pre>
     */
    //
    // By default, a pod’s restart policy is set to Always.
    // This means that if the pod terminates, then Kubernetes will restart the pod with an exponential backoff. (7 restarts)
    // The pod in our case just needs to print the Java version and terminate. 
    // Kubernetes attempts to restart the terminated pod. 
    // One way to tell Kubernetes to not restart the pod is to start the pod with a restart policy of Never. 
    // You can do so by modifying the kubectl run command and specifying the restart policy.
    //
    // $ kubectl run --generator=run-pod/v1 hello-java --image=arungupta/oreilly-hello-java
    /**
     * <pre>
     *  fernando@pc02:~$ kubectl get pods
     *  NAME                            READY   STATUS             RESTARTS   AGE
     *  hello-java-75c9445ff9-g4sd9     0/1     CrashLoopBackOff   8          15m
     *  rest-example-6dd89cf8cd-qfmfr   1/1     Running            4          23d
     * </pre>
     */    

}
