package murach;


import java.io.File;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Main {

    public static void main(String[] args) throws Exception {

	String webappDirLocation = "src/main/webapp/";
	Tomcat tomcat = new Tomcat();
	tomcat.enableNaming();
	Connector connector = tomcat.getConnector();
	connector.setPort(8080);

	StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());

	System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

	// Declare an alternative location for your "WEB-INF/classes" dir
	// Servlet 3.0 annotation will work
	File additionWebInfClasses = new File("target/classes");
	WebResourceRoot resources = new StandardRoot(ctx);

	resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));

	ctx.setResources(resources);

	tomcat.start();
	tomcat.getServer().await();
    }
}
