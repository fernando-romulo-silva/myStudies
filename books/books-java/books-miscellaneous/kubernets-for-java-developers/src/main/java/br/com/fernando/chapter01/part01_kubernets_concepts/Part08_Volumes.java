package br.com.fernando.chapter01.part01_kubernets_concepts;

public class Part08_Volumes {

    // Pods are ephemeral and work well for a stateless container.
    // They are restarted automatically when they die, but any data stored in their filesystem is lost with them.
    //
    // A volume is a directory that is accessible to the containers in a pod. The directory, the medium that backs it, and the contents within it are determined by the particular volume type used.
    // A volume outlives any containers that run within the pod, and the data is preserved across container restarts.
    //
    // Multiple types of volumes are supported (hostpath, nfs, awsElesticBlockStore, gcePersitentDisk, etc)
    //
    // Two properties need to be defined for a volume to be used inside a pod: spec.volumes to define the volume type, 
    // and spec.containers.volumeMounts to specify where to mount the volume.
    // Multiple volumes in a pod and multiple mount points in a container can be easily defined.
    //
    //
    /**
     * <pre>
     *     
     *    apiVersion: v1
     *    kind: Pod
     *    metadata:
     *      name: couchbase-pod
     *      labels:
     *        name: couchbase-pod
     *    spec:
     *      containers:
     *      - name: couchbase
     *        image: arungupta/couchbase-oreilly:k8s
     *        ports:
     *        - containerPort: 8091
     *        volumeMounts:
     *        - mountPath: /var/couchbase/lib
     *          name: couchbase-data
     *      volumes:
     *      - name: couchbase-data
     *        hostPath:
     *          path: /opt/data
     * </pre>
     */
}
