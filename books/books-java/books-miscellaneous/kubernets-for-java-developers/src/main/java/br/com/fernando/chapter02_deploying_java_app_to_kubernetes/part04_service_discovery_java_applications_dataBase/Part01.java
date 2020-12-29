package br.com.fernando.chapter02_deploying_java_app_to_kubernetes.part04_service_discovery_java_applications_dataBase;

public class Part01 {

    // A typical application consists of an application server, a web server, a messaging server, a database, and possibly some other components.
    //
    // These components need to dynamically scale and be able to discover each other with a well-defined name.
    //
    // Pods in the replication controller can also be exposed as a service.
    //
    // This allows different components of the application to interact with each other.
    //
    // We uses the images fernando/oreilly-couchbase and fernando/bootiful-mysql.
    //
    // Start the Couchbase service using the configuration file in app-couchbase-service.yml
    //
    /**
     * <pre>
     *  apiVersion: v1 (1)
     *  kind: Service
     *  metadata: 
     *    name: couchbase-service
     *  spec: 
     *    selector: 
     *      app: couchbase-rc-pod (2)
     *    ports:
     *      - name: admin
     *        port: 8091
     *      - name: views
     *        port: 8092
     *      - name: query
     *        port: 8093
     *      - name: memcached
     *        port: 11210
     *  ---
     *  apiVersion: v1
     *  kind: ReplicationController (1)
     *  metadata:
     *    name: couchbase-rc
     *  spec:
     *    replicas: 1 (3)
     *    template:
     *      metadata:
     *        labels:
     *          app: couchbase-rc-pod (2)
     *      spec:
     *        containers:
     *        - name: couchbase
     *          image: fernando/oreilly-couchbase
     *          imagePullPolicy: IfNotPresent (9)
     *          ports:
     *          - containerPort: 8091
     *          - containerPort: 8092
     *          - containerPort: 8093
     *          - containerPort: 11210
     *  ---
     *  apiVersion: batch/v1
     *  kind: Job (1)
     *  metadata:
     *    name: bootiful-couchbase
     *    labels:
     *      name: bootiful-couchbase-pod
     *  spec:
     *    template:
     *      metadata:
     *        name: bootiful-couchbase-pod
     *      spec:
     *        containers:
     *        - name: bootiful-couchbase
     *          image: fernando/bootiful-couchbase (5)
     *          imagePullPolicy: IfNotPresent (9)
     *        restartPolicy: Never (6)
     * </pre>
     */
    //
    // (1) There are three resources: one service, replication controller with one replica of the pod and a job. This resource is a run-once job.
    //
    // (2) The label specified in the selector app: couchbase-rc-pod matches the label on the pod created by the replication controller.
    // This enables all pods created by the replication controller to be part of the service.
    //
    // (3) One replica of the pods
    //
    // (4) The arungupta/oreilly-couchbase image is built using the Dockerfile defined in this GitHub repo.
    // This image configures the Couchbase database using Couchbase REST API and creates a sample bucket.
    //
    // (5) The Spring Boot application that accesses the Couchbase database, and stores a JSON document in it, is packaged as a Docker image.
    //
    // (6) The Java application is executed once, so the restart policy for the container needs to be set to Never.
    // This type of resource ensures one successful completion of the pod.
    //
    // (9) Set your deployment to not pull IfNotPresent, K8S default is set to "Always" Change to "IfNotPresent"
    //
    // This creates the replication controller, the pod for the replication controller, and the service that includes this pod. Execute:
    //
    // $ kubectl create -f application.yml
    //
    //
    // Find the pod’s name using the command:
    //
    // $ kubectl get -w pods
    //
    //
    // The option "-w" watches for changes in the requested object.
    // Check logs for the pod using the command:
    //
    // $ kubectl logs couchbase-rc-w6k4r
    //
    //
    // In Application
    //
    // The last missing piece that connects the Java application and Couchbase is application.properties.
    // This file is used by Spring Boot to bootstrap the application configuration.
    //
    // Create this resource like so:
    //
    // $ kubectl create -f application.yml
    //
    //
    // Get the status of the pods using the command kubectl get pods:
    //
    // $ kubectl get pods
    //
    // Note how the pod bootiful-couchbase-rzzgq is not shown in this list.
    // This is because the pod has likely completed successfully.
    //
    //
    // To see the complete list of pods, use the command:
    //
    // 1º Describe the job:
    //
    // $ kubectl describe job bootiful-couchbase
    //
    // 2º Pod name is shown under "Events"
    //
    // 3º Execute:
    //
    // $ kubectl logs $POD
}
