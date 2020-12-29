package br.com.fernando.chapter06_running_container_java_applications.part05_runtime_contraints_on_resources;

public class Part02 {

	// Processors
	//
	// By using the -c (or --cpu-shares as an equivalent) for the docker run command switch, it's possible to specify a value of shares of the CPU that a container can allocate. 
	//
	// By default, every new container has 1024 shares of CPU and all containers get the same part of CPU cycles.
	//
	// This is a relative weight and has nothing to do with the real processor speed. 
	// In fact, there is no way to say precisely that a container should have the right to use only 2 GHz of the host's processor.
	//
	//
	// If we start two containers and both will use 100% CPU, the processor time will be divided equally between the two containers. 
	// The reason for that is two containers will have the same number of processor shares. 
	// But if you constrain one container's processor shares to 512, it will receive just a half of the CPU time. 
	// This does not mean that it can use only half of the CPU; the proportion will only apply when CPU-intensive processes are running. 
	// If the other container (with 1024 shares) is idle, our container will be allowed to use 100% of the processor time. 
	// The real amount of CPU time will differ depending on the number of containers running on the system. 
	// It's easier to understand on a tangible example.
	//
	// While the -c or --cpu_shares flag for the docker run command modifies the container's CPU share weighting relative to the weighting of all other running containers, 
	// it does not restrict the container's use of CPU from the host machine
	//
	//
	// But there's another flag to limit the CPU usage for the container: --cpu-quota. 
	// Its default value is 100000 which means an allowance of 100% of the CPU usage. 
	// We can use the --cpu-quota to limit CPU usage, for example:
	//
	// $ docker run -it  --cpu-quota=50000 ubuntu /bin/bash  
	//
	// In the preceding command, the limit for the container will be 50% of a CPU resource.
	//
	// The --cpu-quota is usually used in conjunction with the --cpu-period flag for the docker run.
	//
	// This is the setting for the CPU CFS (Completely Fair Scheduler) period. 
	// The default period value is 100000 which is 100 milliseconds. 
	// Take a look at the example:
	//
	// $ docker run -it --cpu-quota=25000 --cpu-period=50000  ubuntu /bin/bash
	//
	// It means that the container can get 50% of the CPU usage every 50 ms.
	//
	//
	// We can also assign the container's processes to a particular processor or processor core. 
	// The --cpuset switch of the docker run command comes in handy when we want to do this. Consider the following example:
	//
	// $ docker run -it --cpuset 4 ubuntu  
	
	
	
}
