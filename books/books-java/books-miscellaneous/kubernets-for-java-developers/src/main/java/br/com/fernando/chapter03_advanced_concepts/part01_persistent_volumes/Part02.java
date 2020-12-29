package br.com.fernando.chapter03_advanced_concepts.part01_persistent_volumes;

public class Part02 {
    
    // You can run a stateful application by creating a Kubernetes Deployment and connecting it to an existing PersistentVolume using a PersistentVolumeClaim.
    //
    // For example, this YAML file describes a Deployment that runs MySQL and references the PersistentVolumeClaim.
    //
    // The file defines a volume mount for /var/lib/mysql, and then creates a PersistentVolumeClaim that looks for a 1G volume.
    //
    // This claim is satisfied by any existing volume that meets the requirements, or by a dynamic provisioner.
    //
    // $ kubectl apply -f mysql-deployment.yaml
    //
    //
    // Display information about the Deployment:
    //
    // $ kubectl describe deployment mysql
    //
    //
    // List the pods created by the Deployment:
    //
    // $ kubectl get pods -l app=mysql
    //
    //
    // Inspect the PersistentVolumeClaim:
    //
    // $ kubectl describe pvc mysql-pv-claim
}
