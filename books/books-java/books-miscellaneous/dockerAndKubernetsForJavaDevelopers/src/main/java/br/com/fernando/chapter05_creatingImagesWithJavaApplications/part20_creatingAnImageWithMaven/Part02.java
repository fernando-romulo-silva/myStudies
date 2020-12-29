package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part20_creatingAnImageWithMaven;

public class Part02 {

	// As you can see, we no longer deliver a Dockerfile.
	// Instead, we just provide the Dockerfile instructions as plugin configuration elements.
	// It's very convenient because we no longer need to hardcode an executable jar name, version, and so on.
	// It will be taken from the Maven build scope.
	// For example, the name of the jar will be provided for the <cmd> element.
	// It will result in the generation of a valid CMD instruction in the Dockerfile automatically.
	// If we now build the project using the mvn clean package docker:build command, Docker will build an image with our application.

	/**
	 * <pre>
	 * 
	 * 
	 * <plugin>
	 *   <groupId>io.fabric8</groupId>
	 *   <artifactId>docker-maven-plugin</artifactId>
	 *   <version>0.20.1</version>
	 *   <configuration>
	 *     <images>
	 *       <image>
	 *         <name>rest-example:${project.version}</name>
	 *         <alias>rest-example</alias>
	 *         <build>
	 *           <from>openjdk:latest</from>
	 *           <assembly>
	 *             <descriptorRef>artifact</descriptorRef>
	 *           </assembly>
	 *           <cmd>java -jar maven/${project.name}-${project.version}.jar</cmd>
	 *           <healthCheck>--interval=5m --timeout=2s --retries=3 CMD curl -f http://localhost/ping || exit 1</healthCheck>
	 *         </build>
	 *         <run>
	 *           <wait>
	 *             <log>Hello World!</log>
	 *           </wait>
	 *         </run>
	 *       </image>
	 *     </images>
	 *   </configuration>
	 * </plugin>
	 * 
	 * 
	 * </pre>
	 */
}
