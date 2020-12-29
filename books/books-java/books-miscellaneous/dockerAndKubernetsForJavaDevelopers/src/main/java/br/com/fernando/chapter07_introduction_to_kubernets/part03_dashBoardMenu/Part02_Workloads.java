package br.com.fernando.chapter07_introduction_to_kubernets.part03_dashBoardMenu;

public class Part02_Workloads {

    // -------------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Workloads
    // Workloads are objects that set deployment rules for pods. Based on these rules, Kubernetes performs the deployment and updates
    // the workload with the current state of the application.
    // Workloads let you define the rules for application scheduling, scaling, and upgrade.
    //
    // For every workload created, a complementing Service Discovery entry is created.
    // This Service Discovery entry enables DNS resolution for the workload’s pods using the following naming convention: <workload>.<namespace>.svc.cluster.local.
    //
    //
    // * Cron Jobs: A Cron Job creates Jobs on a time-based schedule.
    // One CronJob object is like one line of a crontab (cron table) file. It runs a job periodically on a given schedule, written in Cron format.
    //
    //
    // * Daemon Sets: A DaemonSet ensures that all (or some) Nodes run a copy of a Pod.
    // As nodes are added to the cluster, Pods are added to them.
    // As nodes are removed from the cluster, those Pods are garbage collected.
    // Deleting a DaemonSet will clean up the Pods it created.
    //
    //
    // * Deployments: A deployment’s primary purpose is to declare how many REPLICAS of a pod should be running at a time.
    // When a deployment is added to the cluster, it will automatically spin up the requested number of pods, and then monitor them.
    // If a pod dies, the deployment will automatically re-create it.
    // Deployments are best used for stateless applications (i.e., when you don’t have to maintain the workload’s state).
    // Pods managed by deployment workloads are treated as independent and disposable.
    // If a pod encounters disruption, Kubernetes removes it and then recreates it.
    // An example application would be an Nginx web server.
    //
    //
    // * Jobs: A job creates one or more pods and ensures that a specified number of them successfully complete.
    // When the specified number of pods has successfully completed, the job itself is complete.
    // The job will start a new pod if the pod fails or is deleted due to hardware failure.
    // This is different from a replication controller or a deployment, which ensure that a certain number of pods are always running.
    // If a pod in a replication controller or deployment terminates, it is restarted.
    // This makes replication controllers and deployments both long-running processes, which is well suited for an application server such as WildFly.
    // But a job is completed only when the specified number of pods successfully completes, which is well suited for tasks that need to run only once.
    //
    //
    // * Pods: Unlike other systems you may have used in the past, Kubernetes doesn’t run containers directly; instead it wraps one or more containers into a higher-level
    // structure called a pod. Any containers in the same pod will share the same resources and local network.
    //
    //
    // * Replica Sets: Replica Set ensures how many replica of pod should be running.
    // It can be considered as a replacement of replication controller.
    // The key difference between the replica set and the replication controller is, the replication controller only supports equality-based selector whereas the replica
    // set supports set-based selector.
    //
    //
    // * Replication Controllers: Ensures that a specified number of pod “replicas” are running at any one time.
    // Unlike manually created pods, the pods maintained by a replication controller are automatically replaced if they fail, get deleted, or are terminated.
    //
    //
    // * Stateful Sets: StatefulSets, in contrast to deployments, are best used when your application needs to maintain its identity and store data.
    // An application would be something like Zookeeper—an application that requires a database for storage.
}
