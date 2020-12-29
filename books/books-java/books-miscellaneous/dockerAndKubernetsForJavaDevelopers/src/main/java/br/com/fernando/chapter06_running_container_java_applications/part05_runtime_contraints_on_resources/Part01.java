package br.com.fernando.chapter06_running_container_java_applications.part05_runtime_contraints_on_resources;

public class Part01 {

	// It may be useful to restrict the Docker container usage of resources when running. 
	// Docker gives you a many possibilities to set constraints on the memory, CPU usage or disk access usage.
	//
	// Memory
	//
	// It's worth knowing that, by default, that is, if you use the default settings without any constraints, the running container can use all of the host memory. 
	// To change this behavior we can use the --memory (or -m for short) switch for the docker run command. 
	// It takes the usual suffixes k, m, or g for kilobytes, megabytes and gigabytes, respectively.
	//
	// $ docker run -it -m 512m ubuntu
	//
	// The preceding command will execute the Ubuntu image with the maximum memory that can be used by the container of half of a gigabyte.
	//
	//
	// Memory Reservation Limit
	//
	// Under normal working conditions, a running container can, and probably will, use as much of the memory as needed, up to the limit 
	// you have set using the --memory (-m) switch for the docker run command.
	//
	// When memory reservation is applied, Docker will detect a low memory situation and will try to force the container to restrict its consumption up to a reservation limit.
	//
	// Consider the following example:
	//
	// $ docker run -it -m 1G --memory-reservation 500M ubuntu /bin/bash 
	//
	// The preceding command sets the hard memory limit to 1g, and then sets the memory reservation to half a gig.
	// With those constraints set, when the container consumes memory more than 500M and less than 1G, Docker will attempt to shrink container memory less than 500M.
	// 
	//
	// kernel Memory
	//
	// The main difference is that kernel memory can't be swapped out to disk. It includes stack pages, slab pages, sockets memory pressure and TCP memory pressure. 
	// By restricting kernel memory, you can prevent new processes from being started when the kernel memory usage is too high. 
	// In addition, because the host cannot swap the kernel memory to disk, the container can block the whole host service by consuming too much kernel memory.
	//
	//
	// Setting the kernel memory limit is straightforward:
	//
	// $ docker run -it --kernel-memory 100M ubuntu  /bin/bash
	//
	//
	// Swappiness
	// 
	// One more constraint related to the memory which can be useful when running containers, is the swappines constraint. 
	// We apply the constraint by using the --memory-swappiness switch to the docker run command.
	// 
	// $ docker run -it --memory-swappiness=0 ubuntu /bin/bash 
	
}
