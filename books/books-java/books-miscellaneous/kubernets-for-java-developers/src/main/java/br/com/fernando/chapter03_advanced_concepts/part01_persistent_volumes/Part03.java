package br.com.fernando.chapter03_advanced_concepts.part01_persistent_volumes;

public class Part03 {

    // StorageClass Resource
    //
    // Also, you can get confused here because there is also a Persistent Volume or PV.
    // If you have a default Storage Class or you specify which storage class to use when creating a PVC, PV creation is automatic.
    // PV holds information about physical storage. PVC is just a request for PV.
    // Another way and less desirable is to create a PV manually and attach PVC to it, skipping storage class altogether.
    //
    // Each StorageClass contains the fields provisioner, parameters, and reclaimPolicy, which are used when a PersistentVolume
    // belonging to the class needs to be dynamically provisioned.
    //
    // Why change the default storage class?
    // Depending on the installation method, your Kubernetes cluster may be deployed with an existing StorageClass that is marked as default.
    // This default StorageClass is then used to dynamically provision storage for PersistentVolumeClaims that do not require any specific storage class

}
