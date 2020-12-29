package br.com.fernando.chapter09_working_with_the_kubernets_api;

public class KubernetsAPI {

	// The API server is technically a process named kube-apiserver that accepts and responds to HTTP REST requests using JSON.
	// The API server's main purpose is to validate and process data of cluster resources, such as Pods, services, or deployments.
	// The API Server is the central management entity.
	// It's also the only Kubernetes component that directly connects to etcd, a distributed key-value data store where Kubernetes stores all its cluster state.
	//
	// When can the REST API be useful? Well, you can create a REST call in every programming or scripting language.
	// This creates a whole new level of flexibility, you can manage Kubernetes from your own Java application, from your continuous delivery flow in Jenkins,
	// or from the build tool you are using, let it be Maven for example.

}
