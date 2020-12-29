package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part12_volume;

public class Par01 {

	// We can do the same in the Dockerfile, using the VOLUME instruction.
	//
	// The syntax couldn't be simpler: it's just VOLUME ["/volumeName"].
	//
	// VOLUME ["/var/lib/tomcat8/webapps/"]
	// VOLUME /var/log/mongodb /var/log/tomcat
	//
	//
	// The VOLUME instruction creates a mount point with the specified name and marks it as holding
	// externally mounted volumes from a native host or other containers.
	//
	//
	// The VOLUME command will mount a directory inside your container and store any files created or edited inside that directory
	// on your host's disk outside the container file structure.
	//
	// Using VOLUME in the Dockerfile let's Docker know that a certain directory contains permanent data. Docker will create a volume
	// for that data and never delete it, even if you remove all the containers that use it.
	//
	// Basically, VOLUME and -v are almost equal. 
	// The difference between VOLUME and -v is that you can use -v dynamically and mount your host directory on your container when starting it by executing docker run.
}
