package br.com.fernando.chapter03_advanced_concepts.part01_persistent_volumes;

public class Part01 {

    // The lifecycle of a volume is tied to the pod that created it.
    // Pods can store data on this volume and preserve data across container restarts.
    // But the volume ceases to exist along with the pod. Moreover, pods are ephemeral and so may be rescheduled on a different host.
    // This means the data cannot be stored on a host as well.
    //
    // Creating and using a persistent volume is a three-step process:
    //
    // Provision
    // The administrator provisions a networked storage solution in the cluster, such as AWS ElasticBlockStore (EBS) volumes.
    // This is called a persistent volume (PV).
    //
    // Request storage
    // The user requests storage for pods by using claims.
    // Claims specify levels of resources (CPU and memory), sizes and access modes (e.g., can be mounted once read/write or many times write-only).
    // This is called PersistentVolumeClaim (PVC).
    //
    // Use claim
    // Claims are mounted as volumes and used in pods for storage.
    //
    //
    // The attribute VolumeId is the unique volume identifier.
    // It is used to create a persistent volume using the configuration file shown in
    //
    // You can show the persistent volume claim
    // 
    // $ kubectl get pvc 

}