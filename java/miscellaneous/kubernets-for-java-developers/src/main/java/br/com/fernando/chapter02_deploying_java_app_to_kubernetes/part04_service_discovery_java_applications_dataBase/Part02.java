package br.com.fernando.chapter02_deploying_java_app_to_kubernetes.part04_service_discovery_java_applications_dataBase;

public class Part02 {

    // A basic service generates a random IP address referred to as ClusterIP that occupies a defined range (10.32.0.0/16 by default).
    //
    // To make these names resolvable you need a tool that watches the Kubernetes API so as to allow you to know your container IPs, and then answer your DNS requests with the correct IP.
    // At present the most common tool to achieve this is Kube-DNS.
    //
    /**
     * <pre>
     * apiVersion: v1
     * kind: Service
     * metadata:
     *    name: mysql-service
     *    namespace: default
     * spec:
     *    ports:
     *    -  name: mysql
     *       port: 3306
     *       protocol: TCP
     *       targetPort: 3306
     *    selector:
     *       app: mysql 
     *    type: NodePort
     * </pre>
     */
    // Therefore from any other pod you may directly talk to mysql if you are in the same namespace.
    // Or mysql-service.YOUR-NAMESPACE.svc.cluster.local from any other namespace.
    //
    // Example:
    // spring.datasource.url=jdbc:mysql://mysql-service/test?autoReconnect=true
}
