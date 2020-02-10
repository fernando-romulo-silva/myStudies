package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part20_creatingAnImageWithMaven;

public class Part01 {

	// This can be especially useful, if you have continuous integration flow set up, using Jenkins for example.
	// Delegating the image build process to Maven gives you a lot of flexibility and also saves a lot of time.
	//
	// https://github.com/fabric8io/docker-maven-plugin:
	//
	// Fabric8 is an integrated open source DevOps and integration platform which works out of the box on any Kubernetes or OpenShift environment and
	// provides continuous delivery, management, ChatOps, and a Chaos Monkey.
	//
	// The fabric8 Docker plugin provides a couple of Maven goals:
	//
	// docker:build: This uses the assembly descriptor format from the maven-assembly-plugin to specify the content which will be added from a
	// sub-directory in the image (/maven by default)
	//
	// docker:push: Images that are built with this plugin can be pushed to public or private Docker registries
	//
	// docker:start and docker:stop: For or starting and stopping the container
	//
	// docker:watch: This will execute docker:build and docker:run sequentially.
	// It can run forever in the background (separate console), unless you stop it with CTRL+C.
	// It can watch for assembly files changing and re-run the build. It saves a lot of time
	//
	// docker:remove: This is for cleaning up the images and containers
	//
	// docker:logs: This prints out the output of the running containers
	//
	// docker:volume-create and docker:volume-remove: For creating and removing volumes, respectively.
	//
	//
	// Here is a simplest example of the configuration for the fabric8 Maven plugin for Docker:

	/**
	 * <pre>
	 *   <plugin>
	 *     <groupId>io.fabric8</groupId>
	 *     <artifactId>docker-maven-plugin</artifactId>
	 *     <version>0.20.1</version>
	 *     <configuration>
	 *         <dockerHost>http://127.0.0.1:2375</dockerHost>
	 *         <verbose>true</verbose>
	 *         <images>
	 *            <image>
	 *               <name>rest-example:${project.version}</name>
	 *               <build>
	 *               <dockerFile>Dockerfile</dockerFile>
	 *               <assembly>
	 *                 <descriptorRef>artifact</descriptorRef>
	 *               </assembly>
	 *               </build>
	 *            </image>
	 *         </images>
	 *    </configuration>
	 *   </plugin>
	 * </pre>
	 */

}
