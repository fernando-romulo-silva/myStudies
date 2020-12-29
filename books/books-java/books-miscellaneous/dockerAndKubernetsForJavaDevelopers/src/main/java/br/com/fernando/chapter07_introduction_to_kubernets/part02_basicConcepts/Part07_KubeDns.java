package br.com.fernando.chapter07_introduction_to_kubernets.part02_basicConcepts;

public class Part07_KubeDns {

	// Kubernetes offers a DNS cluster add-on, started automatically each time the cluster is started up
	//
	// It utilizes DNS queries to discover available services. 
	// It supports forward lookups (A records), service lookups (SRV records), and reverse IP address lookups (PTR records). 
	// Actually, the service is the only type of object to which Kubernetes assigns DNS names; 
	// Kubernetes generates an internal DNS entry that resolves to a service's IP address. 
}
