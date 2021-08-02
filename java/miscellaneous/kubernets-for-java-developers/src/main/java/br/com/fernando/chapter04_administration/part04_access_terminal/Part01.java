package br.com.fernando.chapter04_administration.part04_access_terminal;

public class Part01 {
    
    // kubectl exec invokes Kubernetes API Server and it “asks” a Kubelet “node agent” to run an exec command against CRI (Container Runtime Interface), 
    // most frequently it is a Docker runtime.
    //
    // Debugging Workloads by running commands within the Container. Commands may be a Shell with a tty.
    //
    //
    // Exec Command
    //
    // Run a command in a Container in the cluster by specifying the Pod name. You can specific namespace, use the namespace flag:
    //
    // $ kubectl exec wildfly-65cbbddcd7-68q4r ls --namespace my-namespace
    //
    //
    //
    // Exec Shell
    //
    // To get a Shell in a Container, use the -t -i options to get a tty and attach STDIN. You can specific namespace, use the namespace flag:
    //
    // $ kubectl exec -t -i  wildfly-65cbbddcd7-68q4r bash --namespace my-namespace
    //
}
