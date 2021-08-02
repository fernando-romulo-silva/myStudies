package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part20_creatingAnImageWithMaven;

public class Part04 {

	// Creating and removing volumes
	//
	// The Fabric8 Maven Docker plugin couldn't be a complete solution without the possibility of managing volumes.
	// Indeed, it provides two ways to handle volumes: docker:volume-create and docker:volume-remove.
	//
	// Consider the following fragment of the plugin configuration:
	//
	/**
	 * <pre>
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
	 *     
	 *     <volumes>
	 *       <volume>
	 *         <name>myVolume</name>
	 *         <driver>local</driver>
	 *         <opts>
	 *           <type>tmpfs</type>
	 *           <device>tmpfs</device>
	 *           <o>size=100m,uid=1000</o>
	 *         </opts>
	 *         <labels>
	 *           <volatileData>true</volatileData>
	 *         </labels>
	 *       </volume>
	 *     </volumes>
	 *   </configuration>
	 * </plugin>
	 * 
	 * </pre>
	 */
}
