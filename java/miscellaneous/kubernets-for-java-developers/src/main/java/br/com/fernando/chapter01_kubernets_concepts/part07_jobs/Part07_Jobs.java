package br.com.fernando.chapter01_kubernets_concepts.part07_jobs;

public class Part07_Jobs {

    // A job creates one or more pods and ensures that a specified number of them successfully complete.
    //
    // This is different from a replication controller or a deployment, which ensure that a certain number of pods are always running.
    // If a pod in a replication controller or deployment terminates, it is restarted.
    //
    // This makes replication controllers and deployments both long-running processes, which is well suited for an application server such as WildFly.
    // But a job is completed only when the specified number of pods successfully completes, which is well suited for tasks that need to run only once.

    /**
     * <pre>
     * apiVersion: batch/v1
     * kind: Job
     * metadata:
     *   name: wait
     * spec:
     *   template:
     *     metadata:
     *       name: wait
     *     spec:
     *       containers:
     *       - name: wait
     *         image: ubuntu
     *         command: ["sleep",  "20"]
     *       restartPolicy: Never
     * 
     * </pre>
     */

    // By default, running the ubuntu image starts the shell. In this case, command overrides the default command and waits for 20 seconds.
    // Note, this is only an example usage. The actual task would typically be done in the image itself

    // There are two main types of jobs:
    //
    // Nonparallel jobs: Job specification consists of a single pod. The job completes when the pod successfully terminates.
    //
    // Parallel jobs: A predefined number of pods successfully completes. 
    // Alternatively, a work queue pattern can be implemented where pods can coordinate among themselves or with an external service to determine what each should work on.
    //
    // Cron Jobs: CronJob allows you to manage time-based jobs. Run jobs once at a specified point in time and Run jobs repeatedly at a specified point in time.


}
